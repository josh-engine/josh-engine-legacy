package example;

import co.josh.engine.Main;
import co.josh.engine.components.Component;
import co.josh.engine.objects.o2d.GameObject;
import co.josh.engine.render.drawbuilder.DrawBuilder;
import co.josh.engine.util.model.JoshModel;
import co.josh.engine.util.model.ModelReader;
import org.joml.Vector3f;

import java.util.ArrayList;

public class TestTexturedQuad3 implements GameObject {

    float size;

    ArrayList<Component> components = new ArrayList<>();

    Vector3f position;
    Vector3f lastPosition;

    public JoshModel model;

    public TestTexturedQuad3(float x, float y, float z){
        this.position = new Vector3f(x, y, z);
        this.lastPosition = new Vector3f(x, y, z);
        this.model = ModelReader.loadJoshFormat(Main.dir + "/josh/models/e.josh");
        this.size = 1f;
    }

    @Override
    public void addComponent(Component c) {
        components.add(c);
    }

    public String getName() {
        return "TestTexturedQuad";
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
        DrawBuilder db = new DrawBuilder(Main.camera, model.GL_MODE);
        db.render(model.drawBuilderCommands(position, lastPosition), Main.tpsCount / (float)Main.tps);
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
