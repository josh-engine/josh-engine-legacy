package example;

import co.josh.engine.Main;
import co.josh.engine.components.Component;
import co.josh.engine.objects.GameObject;
import co.josh.engine.render.drawbuilder.DrawBuilder;
import co.josh.engine.util.Transform;
import co.josh.engine.util.model.JoshModel;
import co.josh.engine.util.model.ModelReader;
import org.joml.Vector3f;

import java.util.ArrayList;

public class Object2Dtest implements GameObject {

    ArrayList<Component> components = new ArrayList<>();

    Transform transform;

    Transform lastTransform;

    public DrawBuilder db;

    JoshModel model;

    public Object2Dtest(float x, float y, float z){
        this.transform = new Transform(new Vector3f(x, y, z));
        this.lastTransform = new Transform(new Vector3f(x, y, z));

        this.model = ModelReader.loadJoshFormat(Main.dir + "/josh/models/e.josh", false);
        db = new DrawBuilder(Main.camera, model.GL_MODE);
        //db.addShader(Example.colbynorm);
    }

    @Override
    public void addComponent(Component c) {
        components.add(c);
    }

    public String getName() {
        return "Object2D";
    }

    public Transform getTransform() {
        return transform;
    }

    public Transform getLastTransform() {
        return lastTransform;
    }

    public void setTransform(Transform t) {
        this.transform = t;
    }

    public void setLastTransform(Transform t) {
        this.lastTransform = t;
    }

    public void render3d() {
    }

    public void render2d(){
        db.render(model.drawBuilderCommands(transform, lastTransform), (float)Main.tpsCount / Main.tps);
    }

    @Override
    public ArrayList<Component> getComponents() {
        return components;
    }

}