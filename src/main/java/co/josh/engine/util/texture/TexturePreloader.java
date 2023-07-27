package co.josh.engine.util.texture;

import co.josh.engine.Main;
import co.josh.engine.util.exceptions.TextureLoadFailure;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class TexturePreloader {
    public static HashMap<String, Integer> textures = new HashMap<>();

    public static HashMap<String, Integer> skyboxTextures = new HashMap<>();

    public static void load(String textureFolder, String skybox){
        List<Path> cubemapFiles;
        boolean skip = false;
        try{
            cubemapFiles = Files.list(Path.of(Main.dir + skybox)).toList();
            if (cubemapFiles.size() == 0){
                skip = true;
                System.out.println("(TexturePreloader) Provided skybox folder is empty! Not loading anything to index 0!");
            }
            else if (cubemapFiles.size() != 6){
                throw new TextureLoadFailure("Not right amount of textures in skybox folder! (Expected 6 got " + (cubemapFiles.size()) + ")");
            }
        }catch (Exception e){
            System.out.println("// Whoopsies...");
            System.out.println("TexturePreloader failure (cubemap)!");
            e.printStackTrace();
            return;
        }

        if (!skip){
            Main.renderSystem.skyboxEnabled = true;

            for (Path file : cubemapFiles){
                if (file.toFile().getName().equals(".DS_Store")){
                    continue;
                }

                String name = file.toFile().getName().substring(0, file.toFile().getName().indexOf("."));
                Integer textureId = TextureLoader.loadTexture(Main.dir + skybox + file.toFile().getName());
                TexturePreloader.skyboxTextures.put(name, textureId);
            }
            System.out.println(TexturePreloader.skyboxTextures);
        }

        List<Path> files;
        try{
            files = Files.list(Path.of(Main.dir + textureFolder)).toList();
        }catch (Exception e){
            System.out.println("// Whoopsies...");
            System.out.println("TexturePreloader failure (main textures)!");
            e.printStackTrace();
            return;
        }
        for (Path file : files){
            if (file.toFile().getName().equals(".DS_Store")){
                continue;
            }

            String name = file.toFile().getName().substring(0, file.toFile().getName().indexOf("."));
            Integer textureId = TextureLoader.loadTexture(Main.dir + textureFolder + file.toFile().getName());
            TexturePreloader.textures.put(name, textureId);
        }
        System.out.println(TexturePreloader.textures);

    }
}
