package example;

import co.josh.engine.Main;
import co.josh.engine.render.joshshade.JShader;
import co.josh.engine.render.lights.Light;
import co.josh.engine.util.annotations.hooks.PostTick;
import co.josh.engine.util.texture.TexturePreloader;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.glfw.GLFW;

public class Example {
    public static JShader setwhite = new JShader("/josh/shaders/resetcolor.jcsl");

    public static JShader colbynorm = new JShader("/josh/shaders/colorbynormal.jcsl");

    public static Light light0;

    @co.josh.engine.util.annotations.hooks.Startup
    public static void onStart(){
        System.out.println("Look at me! I'm a startup script!");

        //FontLoader.generateTextureAtlas(new Font("Courier", Font.PLAIN, 24));
        //System.out.println("abcdefg");
        TexturePreloader.load("/josh/img/");

        light0 = new Light();
        //light stays w camera every frame. check end of movement()
        light0.create(new Vector3f(), true,
                new Vector4f(0.4f, 0.4f, 0.4f, 1f), new Vector4f( 1f, 1f, 1f, 1f));

        Main.gameObjects.add(new Object(0, -3f, -5));
        Main.gameObjects.get(0).addComponent(new RotateComponent(Main.gameObjects.get(0)));

        Main.gameObjects.add(new Object2(-5f, -2f, -5));

        Main.gameObjects.add(new Object2Dtest(100f, 100f, 0f));
    }

    public static Float moveSpeed = 8f;

    @PostTick
    public static void movement(){
        Main.camera.updateLast();
        moveSpeed *= Main.deltaTime;
        //Movement
        if (Main.keyboard.isKeyDown(GLFW.GLFW_KEY_W)){
            Main.camera.moveWithRotation(new Vector3f(0, 0, -1 * moveSpeed));
        }
        if (Main.keyboard.isKeyDown(GLFW.GLFW_KEY_S)){
            Main.camera.moveWithRotation(new Vector3f(0, 0, 1 * moveSpeed));
        }
        if (Main.keyboard.isKeyDown(GLFW.GLFW_KEY_A)){
            Main.camera.moveWithRotation(new Vector3f(-1 * moveSpeed, 0, 0));
        }
        if (Main.keyboard.isKeyDown(GLFW.GLFW_KEY_D)){
            Main.camera.moveWithRotation(new Vector3f(1 * moveSpeed, 0, 0));
        }
        if (Main.keyboard.isKeyDown(GLFW.GLFW_KEY_SPACE)){
            Main.camera.transform.position.add(new Vector3f(0, 1 * moveSpeed, 0));
        }
        if (Main.keyboard.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)){
            Main.camera.transform.position.add(new Vector3f(0, -1 * moveSpeed, 0));
        }

        //Rotation
        if (Main.keyboard.isKeyDown(GLFW.GLFW_KEY_UP)){
            Main.camera.rotate(new Vector3f(-16 * moveSpeed, 0, 0));
        }
        if (Main.keyboard.isKeyDown(GLFW.GLFW_KEY_DOWN)){
            Main.camera.rotate(new Vector3f(16 * moveSpeed, 0, 0));
        }
        if (Main.keyboard.isKeyDown(GLFW.GLFW_KEY_RIGHT)){
            Main.camera.rotate(new Vector3f(0, 16 * moveSpeed, 0));
        }
        if (Main.keyboard.isKeyDown(GLFW.GLFW_KEY_LEFT)){
            Main.camera.rotate(new Vector3f(0, -16 * moveSpeed, 0));
        }
        moveSpeed /=Main.deltaTime;

        Example.light0.vector3f = Main.camera.transform.position;
    }

}
