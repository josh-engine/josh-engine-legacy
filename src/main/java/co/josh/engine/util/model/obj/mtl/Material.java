package co.josh.engine.util.model.obj.mtl;

import org.joml.Vector3f;
import org.joml.Vector4f;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Material {

    public Vector3f ambient, diffuse, specular;
    public float shininess;
    public int textureID;

    public String name;

    public Material(String name, int textureID, Vector3f ambient, Vector3f diffuse, Vector3f specular, float shininess){
        this.name = name;
        this.textureID = textureID;
        this.ambient = ambient;
        this.diffuse = diffuse;
        this.specular = specular;
        this.shininess = shininess;
    }

    public Material(){
        this.name = "";
        this.textureID = 0;
        this.ambient = new Vector3f();
        this.diffuse = new Vector3f();
        this.specular = new Vector3f();
        this.shininess = 0.0f;
    }

    public Material clone(){
        return new Material(
                name,
                textureID,
                new Vector3f(ambient.x, ambient.y, ambient.z),
                new Vector3f(diffuse.x, diffuse.y, diffuse.z),
                new Vector3f(specular.x, specular.y, specular.z),
                shininess);
    }

    public static List<Material> load(String fileName){
        try{
            /*
            newmtl cruiser                (Material 1)
            Ka 0.300000 0.300000 0.300000 (Ambient)
            Kd 0.800000 0.800000 0.800000 (Diffuse)
            Ks 0.000000 0.000000 0.000000 (Specular)
            Ns 0.000000                   (Shininess)
            map_Kd cruiser.bmp            (Main texture)
             */
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            ArrayList<Material> mats = new ArrayList<>();
            Material active = null;
            Float[] floats;
            String[] split;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.startsWith("newmtl ")){
                    if (active != null) mats.add(active);
                    active = new Material();
                    active.name = line.substring(7);
                }
                if (line.startsWith("Ka ") && active != null){
                    line = line.substring(3);
                    split = line.split(" ");
                    floats = Arrays.stream(split).map(Float::valueOf).toArray(Float[]::new);
                    active.ambient = new Vector3f(floats[0], floats[1], floats[2]);
                }
                if (line.startsWith("Kd ") && active != null){
                    line = line.substring(3);
                    split = line.split(" ");
                    floats = Arrays.stream(split).map(Float::valueOf).toArray(Float[]::new);
                    active.diffuse = new Vector3f(floats[0], floats[1], floats[2]);
                }
                if (line.startsWith("Ks ") && active != null){
                    line = line.substring(3);
                    split = line.split(" ");
                    floats = Arrays.stream(split).map(Float::valueOf).toArray(Float[]::new);
                    active.specular = new Vector3f(floats[0], floats[1], floats[2]);
                }
                if (line.startsWith("Ns ") && active != null){
                    line = line.substring(3);
                    active.shininess = Float.parseFloat(line)/7.8125f;
                }
            }

            return mats;
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
