package co.josh.engine.objects.o2d;

import co.josh.engine.components.TestKeyboardMovement;
import co.josh.engine.render.DrawBuilder;
import co.josh.engine.util.vector.Vector3f;
import co.josh.engine.Main;
import co.josh.engine.components.Component;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;

public class TestGradientTriangle implements GameObject {

    float size;
    ArrayList<Component> components = new ArrayList<>();

    Vector3f vector3F;

    public TestGradientTriangle(float x, float y, float z){
        this.vector3F = new Vector3f(x, y, z);
        components.add(new TestKeyboardMovement(this));
        this.size = 1f;
    }



    public String getName() {
        return "TestGradientTriangle";
    }

    public Vector3f getPosition() {
        return vector3F;
    }

    @Override
    public Vector3f getNextPosition() {
        return getPosition();
    }

    public void movePosition(int x, int y, int z) {
        this.vector3F.move(x, y, z);
    }

    public void setPosition(Vector3f vector3F1){
        this.vector3F = vector3F1;
    }


    public void render() {
        DrawBuilder db = new DrawBuilder(Main.camera, GL_TRIANGLES);

        db.push(db.next()
                .pos(getPosition().x - 50f, getPosition().y - 50f, getPosition().z)
                .col(1f, 0f, 0f, 0f));
        db.push(db.next()
                .pos(getPosition().x, getPosition().y + 50f, getPosition().z)
                .col(0f, 1f, 0f, 0f));
        db.push(db.next()
                .pos(getPosition().x + 50f, getPosition().y - 50f, getPosition().z)
                .col(0f, 0f, 1f, 0f));

        db.render(Main.tpsCount / 20f);
    }

    public ArrayList<Component> getComponents() {
        return components;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    @Override
    public void addComponent(Component c) {
        components.add(c);
    }
}
