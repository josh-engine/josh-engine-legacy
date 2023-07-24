package co.josh.engine.util.model;

import co.josh.engine.util.render.Vertex3F;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

import static org.lwjgl.opengl.GL11.*;

public class ModelReader {

    public static JoshModel loadObjToJosh(String fileName, String textureName, boolean lit, boolean textured){
        ArrayList<Vertex3F> vertices = new ArrayList<>();
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            ArrayList<Vector3f> vertexPositions = new ArrayList<>();
            ArrayList<Vector2f> vertexTexcoords = new ArrayList<>();
            ArrayList<Vector3f> vertexNormals = new ArrayList<>();
            ArrayList<OBJPreVertex> objPreVertexArrayList = new ArrayList<>();
            //
            // PROCESSING THE FILE
            // this is the part where it reads the raw file data and
            // turns it into usable numbers.
            //
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] split;
                Float[] floats;
                if (!line.startsWith("#")){

                    switch (line.split(" ")[0]) {
                        case ("v") -> {
                            //positions
                            line = line.substring(2); // "v 1.0 1.0 1.0" to "1.0 1.0 1.0"
                            split = line.split(" ");
                            floats = Arrays.stream(split).map(Float::valueOf).toArray(Float[]::new);
                            vertexPositions.add(new Vector3f(floats[0], floats[1], floats[2]));
                        }
                        case ("vt") -> {
                            //texcoords
                            /*
                            I know technically it only requires one coordinate to be valid in the official specs
                            but JoshEngine (although with modification it could) doesn't support 1d textures.

                            I'll probably remember to write this down in the docs eventually when I get around to
                            making those.
                            */
                            line = line.substring(3); // "vt 1.0 1.0" to "1.0 1.0"
                            split = line.split(" ");
                            floats = Arrays.stream(split).map(Float::valueOf).toArray(Float[]::new);
                            vertexTexcoords.add(new Vector2f(floats[0], floats[1]));
                        }

                        case ("vn") -> {
                            //normals
                            line = line.substring(3); // "vn 0.0 0.0 1.0" to "0.0 0.0 1.0"
                            split = line.split(" ");
                            floats = Arrays.stream(split).map(Float::valueOf).toArray(Float[]::new);
                            vertexNormals.add(new Vector3f(floats[0], floats[1], floats[2]));
                        }
                        case ("f") -> {
                            //faces
                            line = line.substring(2); //"f 1//2 3//2 2//2" to "1//2 3//2 2//2"
                            split = line.split(" ");
                            for (String vertex : split) {
                                String e0 = vertex.split("//")[0]; //entry 0
                                if (Objects.equals(e0, vertex)) {
                                    // vertex/texcoord or vertex/texcoord/normal or vertex
                                    String[] splitVertex = vertex.split("/");
                                    Integer[] vertexParts = Arrays.stream(splitVertex).map(Integer::valueOf).toArray(Integer[]::new);
                                    if (vertexParts.length == 1) {
                                        objPreVertexArrayList.add(new OBJPreVertex(vertexParts[0] - 1));
                                    } else if (vertexParts.length == 2) {
                                        objPreVertexArrayList.add(new OBJPreVertex(vertexParts[0] - 1, vertexParts[1] - 1, true));
                                    } else if (vertexParts.length == 3) {
                                        objPreVertexArrayList.add(new OBJPreVertex(vertexParts[0] - 1, vertexParts[1] - 1, vertexParts[2] - 1));
                                    }
                                } else {
                                    // vertex//normal
                                    String[] splitVertex = vertex.split("//");
                                    Integer[] vertexParts = Arrays.stream(splitVertex).map(Integer::valueOf).toArray(Integer[]::new);
                                    if (vertexParts.length > 1) {
                                        objPreVertexArrayList.add(new OBJPreVertex(vertexParts[0] - 1, vertexParts[1] - 1, false));
                                    }
                                }
                            }
                        }
                    }
                }
            }
            scanner.close();
            //
            // CREATING MODEL INSTANCE
            // this is where stuff actually gets loaded into a list
            // of Vertex3F objects.
            //
            for (OBJPreVertex preVertex : objPreVertexArrayList){
                switch ((preVertex.texcoord ? 2 : 0) + (preVertex.normal ? 1 : 0)) {
                    case (0) ->
                        //pos only
                            vertices.add(new Vertex3F(
                                    vertexPositions.get(preVertex.positionID),
                                    new Vector4f(1f, 1f, 1f, 1f)));
                    case (1) ->
                        //pos + normal
                            vertices.add(new Vertex3F(
                                    vertexPositions.get(preVertex.positionID),
                                    new Vector4f(1f, 1f, 1f, 1f))
                                    .normal(vertexNormals.get(preVertex.normalID).x,
                                            vertexNormals.get(preVertex.normalID).y,
                                            vertexNormals.get(preVertex.normalID).z));
                    case (2) ->
                        //pos + texcoord
                            vertices.add(new Vertex3F(
                                    vertexPositions.get(preVertex.positionID),
                                    new Vector4f(1f, 1f, 1f, 1f))
                                    .uv(vertexTexcoords.get(preVertex.texcoordID).x,
                                            vertexTexcoords.get(preVertex.texcoordID).y));
                    case (3) ->
                        // contains all info
                            vertices.add(new Vertex3F(
                                    vertexPositions.get(preVertex.positionID),
                                    new Vector4f(1f, 1f, 1f, 1f))
                                    .uv(vertexTexcoords.get(preVertex.texcoordID).x,
                                            vertexTexcoords.get(preVertex.texcoordID).y)
                                    .normal(vertexNormals.get(preVertex.normalID).x,
                                            vertexNormals.get(preVertex.normalID).y,
                                            vertexNormals.get(preVertex.normalID).z));
                }
            }
            return new JoshModel(textureName, GL_TRIANGLES, vertices, lit, textured);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static JoshModel loadJoshFormat(String fileName, boolean lit){
        ArrayList<Vertex3F> vertices = new ArrayList<>();
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            int GL_MODE;
            String version = "version 10";
            try{
                GL_MODE = Integer.parseInt(scanner.nextLine()); //COMPAT FOR 1.0
            } catch (Exception e) {
                scanner.close();    //EVERYTHING AFTERWARDS
                scanner = new Scanner(file);
                version = scanner.nextLine();
                GL_MODE = Integer.parseInt(scanner.nextLine());
            }
            String textureName = scanner.nextLine();
            if (Objects.equals(version, "version 10")){
                while (scanner.hasNextLine()) {
                    String raw = scanner.nextLine();
                    String[] split = raw.split(" ");
                    Float[] floats = Arrays.stream(split).map(Float::valueOf).toArray(Float[]::new);
                    // X Y Z R G B A S T
                    vertices.add(new Vertex3F(
                            new Vector3f(floats[0], floats[1], floats[2]),
                            new Vector4f(floats[3], floats[4], floats[5], floats[6]))
                            .uv(floats[7], floats[8]));
                }
                scanner.close();
                return new JoshModel(textureName, GL_MODE, vertices, lit, true);
            } else if (version.startsWith("version 11")) {
                while (scanner.hasNextLine()) {
                    String raw = scanner.nextLine();
                    String[] split = raw.split(" ");
                    Float[] floats = Arrays.stream(split).map(Float::valueOf).toArray(Float[]::new);
                    // X Y Z R G B A S T NX NY NZ
                    vertices.add(new Vertex3F(
                            new Vector3f(floats[0], floats[1], floats[2]),
                            new Vector4f(floats[3], floats[4], floats[5], floats[6]))
                            .uv(floats[7], floats[8])
                            .normal(floats[9], floats[10], floats[11]));
                }
                scanner.close();
                return new JoshModel(textureName, GL_MODE, vertices, lit, true);
            } else if (version.startsWith("version 12")) {
                boolean specifiedLit = Objects.equals(scanner.nextLine(), "1");
                while (scanner.hasNextLine()) {
                    String raw = scanner.nextLine();
                    String[] split = raw.split(" ");
                    Float[] floats = Arrays.stream(split).map(Float::valueOf).toArray(Float[]::new);
                    // X Y Z R G B A S T NX NY NZ
                    vertices.add(new Vertex3F(
                            new Vector3f(floats[0], floats[1], floats[2]),
                            new Vector4f(floats[3], floats[4], floats[5], floats[6]))
                            .uv(floats[7], floats[8])
                            .normal(floats[9], floats[10], floats[11]));
                }
                scanner.close();
                return new JoshModel(textureName, GL_MODE, vertices, specifiedLit && lit, true);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
