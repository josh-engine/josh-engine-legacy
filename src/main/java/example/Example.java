package example;

import co.josh.engine.Main;
import co.josh.engine.render.joshshade.JShader;
import co.josh.engine.util.annotations.hooks.OnClick;
import co.josh.engine.util.texture.TexturePreloader;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;

public class Example {
    public static JShader setwhite = new JShader("/josh/shaders/resetcolor.jcsl");

    public static JShader colbypos = new JShader("/josh/shaders/colorbyposition.jcsl");

    @co.josh.engine.util.annotations.hooks.Startup
    public static void onStart(){
        System.out.println("Look at me! I'm a startup script!");
        Main.renderSystem.doPerspectiveDraw = false;
        TexturePreloader.load("/josh/img/");

        Main.gameObjects.add(new TestTexturedQuad(Main.currentWidth/2f, Main.currentHeight/2f, 0.1f)); //0.1 is behind 0, so it appears behind it
        Main.gameObjects.add(new TestTexturedQuad2((Main.currentWidth/2f)-50, (Main.currentHeight/2f)-50, 0f));
        Main.gameObjects.get(0).addComponent(new TestKeyboardMovement(Main.gameObjects.get(0)));
    }

    @OnClick
    public static void onClick(int button, int action){
        //Create a new movable texture quad on click at mouse
        if (button == GLFW_MOUSE_BUTTON_LEFT && action == GLFW_PRESS){
            Main.gameObjects.add(new TestTexturedQuad(Main.mouse.relativeCurX, Main.mouse.relativeCurY, 0.1f));
            Main.gameObjects.get(Main.gameObjects.size()-1).addComponent(new TestKeyboardMovement(Main.gameObjects.get(Main.gameObjects.size()-1)));
        }
    }
}
