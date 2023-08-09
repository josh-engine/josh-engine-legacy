package example;

import co.josh.engine.Main;
import co.josh.engine.components.builtin.LightComponent;
import co.josh.engine.render.joshshade.JShader;
import co.josh.engine.render.lights.Light;
import co.josh.engine.util.annotations.hooks.OnKey;
import co.josh.engine.util.annotations.hooks.PostRenderNP;
import co.josh.engine.util.texture.TexturePreloader;
import co.josh.engine.util.tile.TileGrid;
import co.josh.engine.util.tile.draw.TileGridRendererComponent;
import co.josh.engine.util.tile.procedural.wfc.WFCObject;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.glfw.GLFW;


public class Example {
    public static JShader setwhite = new JShader(Main.gameFolder + "/shaders/resetcolor.jcsl");

    public static JShader colbynorm = new JShader(Main.gameFolder + "/shaders/colorbynormal.jcsl");

    public static Light light0;

    @co.josh.engine.util.annotations.hooks.Startup
    public static void onStart(){
        System.out.println("Look at me! I'm a startup script!");
        TileGrid tileGrid = new TileGrid(5, 5);

        tileGrid.setTile(0, 2, 1, "Test");
        tileGrid.setTile(1, 2, 1, "Test");
        tileGrid.setTile(2, 2, 1, "Test");
        tileGrid.setTile(3, 2, 1, "Test");
        tileGrid.setTile(4, 2, 1, "Test");

        tileGrid.setTile(2, 0, 2, "Test");
        tileGrid.setTile(2, 1, 2, "Test");
        tileGrid.setTile(2, 2, 3, "Test");
        tileGrid.setTile(2, 3, 2, "Test");
        tileGrid.setTile(2, 4, 2, "Test");

        WFCObject a = new WFCObject();
        a.wfcParse(tileGrid);

        TileGrid generated = new TileGrid(1, 1);
        boolean generatedSuccessfully = false;
        while (!generatedSuccessfully){
            try{
                generated = a.wfcGen(20, 20);
                generatedSuccessfully = true;
            } catch (Exception ignored){
                //redo
            }
        }


        Main.ambient = new float[]{0.15f, 0.15f, 0.20f, 1f};
        TexturePreloader.load(Main.gameFolder + "/img/", Main.gameFolder + "/skybox/");

        Main.runEvents = true;
        Main.renderSystem.skyboxEnabled = true;


        //This creates a new light object, but doesn't register it to be rendered or do anything to set up the values
        light0 = new Light();
        //This actually registers the light with the renderer and returns a boolean telling you if it registered successfully.
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

        Main.gameObjects.get(2).addComponent(new TileGridRendererComponent(Main.gameObjects.get(2), generated, 10f));
    }

    public static Float moveSpeed = 8f;

    @OnKey
    public static void key(int key, int action){
        if (key == GLFW.GLFW_KEY_P && action == GLFW.GLFW_PRESS){
            Main.runEvents = !Main.runEvents; //Toggle pause
        }
    }
    @PostRenderNP
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
