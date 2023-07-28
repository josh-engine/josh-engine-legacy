package example;

import co.josh.engine.Main;
import co.josh.engine.components.builtin.LightComponent;
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

        Main.ambient = new float[]{0.15f, 0.15f, 0.20f, 1f};

        TexturePreloader.load("/josh/img/", "/josh/skybox/");

        light0 = new Light();

        light0.create(new Vector3f(-1f, 1f, 1f), false,
                new Vector4f(0.47f, 0.47f, 0.45f, 1f), new Vector4f( 0.7f, 0.7f, 0.6f, 1f));


        Main.gameObjects.add(new Object(0, -3f, -5));

        Main.gameObjects.get(0).addComponent(new RotateComponent(Main.gameObjects.get(0), 15f));

        Main.gameObjects.add(new Object2(-7f, -2f, -5));

        Main.gameObjects.get(1).addComponent(new RotateComponent(Main.gameObjects.get(1), 20f));

        Main.gameObjects.get(1).addComponent(new LightComponent(Main.gameObjects.get(1),
                new Vector3f(0, -0.25f, -5f),
                new Vector4f(0.3f, 0, 0.7f, 1f),
                new Vector4f(0.3f, 0, 0.7f, 1f)));

        //Main.gameObjects.add(new Object3(0f, 0f, 0f));

        //2D rendering over 3D (UI example)
        Main.gameObjects.add(new Object2Dtest(100f, 100f, 0f));

    }

    public static Float moveSpeed = 8f;

    @PostTick
    public static void movement(){
        Main.camera.updateLast();
        moveSpeed *= Main.deltaTime;
        Vector3f movement = new Vector3f();

        //Rotation
        if (Main.keyboard.isKeyDown(GLFW.GLFW_KEY_UP))
            Main.camera.rotate(new Vector3f(-16 * moveSpeed, 0, 0));

        if (Main.keyboard.isKeyDown(GLFW.GLFW_KEY_DOWN))
            Main.camera.rotate(new Vector3f(16 * moveSpeed, 0, 0));

        if (Main.keyboard.isKeyDown(GLFW.GLFW_KEY_RIGHT))
            Main.camera.rotate(new Vector3f(0, 16 * moveSpeed, 0));

        if (Main.keyboard.isKeyDown(GLFW.GLFW_KEY_LEFT))
            Main.camera.rotate(new Vector3f(0, -16 * moveSpeed, 0));


        //Movement
        if (Main.keyboard.isKeyDown(GLFW.GLFW_KEY_W))
            movement.add(new Vector3f(0, 0, -1));

        if (Main.keyboard.isKeyDown(GLFW.GLFW_KEY_S))
            movement.add(new Vector3f(0, 0, 1));

        if (Main.keyboard.isKeyDown(GLFW.GLFW_KEY_A))
            movement.add(new Vector3f(-1, 0, 0));

        if (Main.keyboard.isKeyDown(GLFW.GLFW_KEY_D))
            movement.add(new Vector3f(1, 0, 0));

        if (Main.keyboard.isKeyDown(GLFW.GLFW_KEY_SPACE))
            Main.camera.transform.position.add(new Vector3f(0, 1 * moveSpeed, 0));

        if (Main.keyboard.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT))
            Main.camera.transform.position.add(new Vector3f(0, -1 * moveSpeed, 0));

        Main.camera.moveWithRotation(movement.mul(moveSpeed));

        moveSpeed /=Main.deltaTime;
    }

}
