package example;

import co.josh.engine.Main;
import co.josh.engine.components.Component;
import co.josh.engine.objects.GameObject;
import co.josh.engine.render.drawbuilder.DrawBuilder;
import co.josh.engine.util.model.JoshModel;
import co.josh.engine.util.model.ModelReader;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public class Object implements GameObject {

    float size = 1f;

    ArrayList<Component> components = new ArrayList<>();

    Vector3f position;
    Vector3f lastPosition;

    public DrawBuilder db;

    JoshModel model;

    public Object(float x, float y, float z){
        this.position = new Vector3f(x, y, z);
        this.lastPosition = new Vector3f(x, y, z);
        this.model = ModelReader.loadObjToJosh(Main.dir + "/josh/models/stanfordbunny.obj", "", true, false);
        this.size = 20f;
        this.model = ModelReader.loadJoshFormat(Main.dir + "/josh/models/cube.josh");
        db = new DrawBuilder(Main.camera, model.GL_MODE);
        //db.addShader(Example.setwhite);
        //db.addShader(Example.colbynorm);
    }

    @Override
    public void addComponent(Component c) {
        components.add(c);
    }

    public String getName() {
        return "Object";
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
        model.scale = size;
        float[] light = {Main.camera.position.x, Main.camera.position.y, Main.camera.position.z, 1f};
        GL11.glLightfv(GL11.GL_LIGHT0, GL11.GL_POSITION, light);
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