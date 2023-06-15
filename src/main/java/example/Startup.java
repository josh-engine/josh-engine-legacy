package example;

import co.josh.engine.Main;
import co.josh.engine.util.texture.TexturePreloader;

public class Startup {
    @co.josh.engine.util.annotations.hooks.Startup
    public static void onStart(){
        System.out.println("Look at me! I'm a startup script!");
        Main.renderSystem.doPerspectiveDraw = false;
        TexturePreloader.load("/josh/img/");

        Main.gameObjects.add(new TestTexturedQuad(Main.currentWidth/2f, Main.currentHeight/2f, 0.1f)); //0.1 is behind 0, so it appears behind it
        Main.gameObjects.add(new TestTexturedQuad2((Main.currentWidth/2f)-50, (Main.currentHeight/2f)-50, 0f));
        Main.gameObjects.get(0).addComponent(new TestKeyboardMovement(Main.gameObjects.get(0)));
    }
}
