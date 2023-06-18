package example;

import co.josh.engine.Main;
import co.josh.engine.render.joshshade.JShader;
import co.josh.engine.util.annotations.hooks.Gameloop;
import co.josh.engine.util.annotations.hooks.OnClick;
import co.josh.engine.util.texture.TexturePreloader;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;

public class Example {
    public static JShader setwhite = new JShader("/josh/shaders/resetcolor.jcsl");

    public static JShader colbynorm = new JShader("/josh/shaders/colorbynormal.jcsl");

    @co.josh.engine.util.annotations.hooks.Startup
    public static void onStart(){
        System.out.println("Look at me! I'm a startup script!");
        Main.renderSystem.doPerspectiveDraw = true;
        TexturePreloader.load("/josh/img/");

        Main.gameObjects.add(new TestTexturedQuad(0, 0, -5));
    }

    public static Float movespeed = 0.25f;

    @Gameloop
    public static void movement(){
        //Movement
        if (Main.keyboard.isKeyDown(GLFW.GLFW_KEY_W)){
            Main.camera.moveWithRotation(new Vector3f(0, 0, -1*movespeed));
        }
        if (Main.keyboard.isKeyDown(GLFW.GLFW_KEY_S)){
            Main.camera.moveWithRotation(new Vector3f(0, 0, 1*movespeed));
        }
        if (Main.keyboard.isKeyDown(GLFW.GLFW_KEY_A)){
            Main.camera.moveWithRotation(new Vector3f(-1*movespeed, 0, 0));
        }
        if (Main.keyboard.isKeyDown(GLFW.GLFW_KEY_D)){
            Main.camera.moveWithRotation(new Vector3f(1*movespeed, 0, 0));
        }
        if (Main.keyboard.isKeyDown(GLFW.GLFW_KEY_SPACE)){
            Main.camera.position.add(new Vector3f(0, 1*movespeed, 0));
        }
        if (Main.keyboard.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)){
            Main.camera.position.add(new Vector3f(0, -1*movespeed, 0));
        }

        //Rotation
        if (Main.keyboard.isKeyDown(GLFW.GLFW_KEY_UP)){
            Main.camera.rotate(new Vector3f(-3, 0, 0));
        }
        if (Main.keyboard.isKeyDown(GLFW.GLFW_KEY_DOWN)){
            Main.camera.rotate(new Vector3f(3, 0, 0));
        }
        if (Main.keyboard.isKeyDown(GLFW.GLFW_KEY_RIGHT)){
            Main.camera.rotate(new Vector3f(0, 3, 0));
        }
        if (Main.keyboard.isKeyDown(GLFW.GLFW_KEY_LEFT)){
            Main.camera.rotate(new Vector3f(0, -3, 0));
        }
    }

}
