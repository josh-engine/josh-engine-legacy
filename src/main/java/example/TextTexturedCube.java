package example;

import co.josh.engine.objects.GameObject;
import co.josh.engine.render.drawbuilder.commands.*;
import co.josh.engine.render.drawbuilder.DrawBuilder;
import co.josh.engine.Main;
import co.josh.engine.components.Component;
import co.josh.engine.util.model.JoshModel;
import co.josh.engine.util.model.ModelReader;
import co.josh.engine.util.texture.TexturePreloader;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL12;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL12.GL_QUADS;

public class TextTexturedCube implements GameObject {

    float size;

    ArrayList<Component> components = new ArrayList<>();

    Vector3f position;
    Vector3f lastPosition;

    public DrawBuilder db = new DrawBuilder(Main.camera, GL_QUADS);

    JoshModel model;

    public TextTexturedCube(float x, float y, float z){
        this.position = new Vector3f(x, y, z);
        this.lastPosition = new Vector3f(x, y, z);
        this.model = ModelReader.loadJoshFormat(Main.dir + "/josh/models/cube.josh");
        this.size = 1f;
        db.addShader(Example.setwhite);
        //db.addShader(Example.colbynorm);
    }

    @Override
    public void addComponent(Component c) {
        components.add(c);
    }

    public String getName() {
        return "TextTexturedCube";
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getLastPosition() {
        return lastPosition;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setLastPosition(Vector3f lastPosition) {
        this.lastPosition = lastPosition;
    }

    public void movePosition(int x, int y, int z) {
        this.position.add(x, y, z);
    }

    public void render() {
        float[] camerapos = {Main.camera.position.x, Main.camera.position.y, Main.camera.position.z, 1f};
        GL12.glLightfv(GL12.GL_LIGHT0, GL12.GL_POSITION, camerapos);

        db.render(model.drawBuilderCommands(position, lastPosition), (float)Main.tpsCount / Main.tps);
    }

    @Override
    public ArrayList<Component> getComponents() {
        return components;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }
}