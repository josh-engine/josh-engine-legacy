package co.josh.engine.util.model;

import co.josh.engine.util.render.Vertex3F;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;

public class ModelReader {

    public static JoshModel loadJoshFormat(String filename){
        ArrayList<Vertex3F> vertices = new ArrayList<>();
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            int GL_MODE = GL_TRIANGLE_FAN;
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
            if (version == "version 10"){
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
                return new JoshModel(textureName, GL_MODE, vertices);
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
                return new JoshModel(textureName, GL_MODE, vertices);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
