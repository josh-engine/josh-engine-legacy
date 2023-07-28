package example;

import co.josh.engine.Main;
import co.josh.engine.components.Component;
import co.josh.engine.objects.GameObject;
import co.josh.engine.render.drawbuilder.DrawBuilder;
import co.josh.engine.util.Transform;
import co.josh.engine.util.model.ModelReader;
import co.josh.engine.util.model.jmodel.JoshModel;
import co.josh.engine.util.model.obj.OBJModel;
import org.joml.Vector3f;

import java.util.ArrayList;

public class Object3 implements GameObject {

    ArrayList<Component> components = new ArrayList<>();

    Transform transform;

    Transform lastTransform;

    public DrawBuilder db;

    OBJModel model;

    public Object3(float x, float y, float z){
        this.transform = new Transform(new Vector3f(x, y, z));
        this.lastTransform = new Transform(new Vector3f(x, y, z));

        this.model = ModelReader.loadObj(Main.dir + "/josh/models/f16.obj", Main.dir + "/josh/materials/", true, true);
        db = new DrawBuilder(Main.camera, model.GL_MODE);
        //db.addShader(Example.colbynorm);
    }

    @Override
    public void addComponent(Component c) {
        components.add(c);
    }

    public String getName() {
        return "Object3";
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
        db.render(model.drawBuilderCommands(transform, lastTransform), (float)Main.tpsCount / Main.tps);
    }

    public void render2d(){

    }

    @Override
    public ArrayList<Component> getComponents() {
        return components;
    }

}