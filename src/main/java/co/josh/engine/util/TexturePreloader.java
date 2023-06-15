package co.josh.engine.util;

import co.josh.engine.render.TextureLoader;
import co.josh.engine.Main;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TexturePreloader {
    public static HashMap<String, Integer> textures = new HashMap<>();
    public static void load(String directory){
        List<Path> files;
        try{
            files = Files.list(Path.of(Main.dir + directory)).toList();
        }catch (Exception e){
            System.out.println("// Whoopsies...");
            System.out.println("TexturePreloader failure!");
            e.printStackTrace();
            return;
        }
        List<String> fileNames = new ArrayList<>();
        for (Path file : files){
            if (file.toFile().getName().equals(".DS_Store")){
                continue;
            }

            String name = file.toFile().getName().substring(0, file.toFile().getName().indexOf("."));
            fileNames.add(name);
            Integer textureId = TextureLoader.loadTexture(Main.dir + directory + file.toFile().getName());
            textures.put(name, textureId);
        }
        System.out.println(textures);

    }
}
