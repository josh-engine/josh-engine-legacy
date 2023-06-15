package example;

import co.josh.engine.Main;
import co.josh.engine.util.TexturePreloader;
import co.josh.engine.util.annotations.hooks.startup;

public class Startup {
    @startup
    public static void onStart(){
        System.out.println("Look at me! I'm a startup script!");
        Main.renderSystem.doPerspectiveDraw = false;

        Main.gameObjects.add(new TestGradientTriangle(Main.currentWidth/2f, Main.currentHeight/2f, 0f));
        Main.gameObjects.add(new TestTexturedQuad(Main.currentWidth/2f, Main.currentHeight/2f, 0f));
        Main.gameObjects.get(0).addComponent(new TestKeyboardMovement(Main.gameObjects.get(0)));
    }
}
