package example;

import co.josh.engine.objects.o2d.GameObject;
import co.josh.engine.util.TextureLoader;
import co.josh.engine.Main;
import co.josh.engine.components.Component;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL12;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL12.GL_QUADS;

public class TestTexturedQuad implements GameObject {

    float size;

    ArrayList<Component> components = new ArrayList<>();

    Vector3f vector3F;

    public int textureId;

    public TestTexturedQuad(float x, float y, float z){
        this.vector3F = new Vector3f(x, y, z);
        this.textureId = TextureLoader.loadTexture(Main.dir + "/josh/e.png");
        //this.textureId = TexturePreloader.textures.get("e");
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
        return vector3F;
    }

    @Override
    public Vector3f getNextPosition() {
        return getPosition();
    }

    public void movePosition(int x, int y, int z) {
        this.vector3F.add(x, y, z);
    }

    public void setPosition(Vector3f vector3F1){
        this.vector3F = vector3F1;
    }

    public void debug_vertex3f(float x, float y, float z){
        GL12.glVertex3f((x - Main.camera.position.x)*((float) Main.currentWidth/(float)Main.width), (y - Main.camera.position.y)*((float)Main.currentHeight/(float)Main.height), (z - Main.camera.position.z)*((float)Main.currentHeight/(float)Main.height));
    }

    public void render() {
        GL12.glBegin(GL_QUADS);
        GL12.glBindTexture(GL12.GL_TEXTURE_2D, this.textureId);

        GL12.glTexCoord2f(0f, 0f);
        debug_vertex3f(getPosition().x - 50f, getPosition().y - 50f, getPosition().z);
        GL12.glTexCoord2f(1f, 0f);
        debug_vertex3f(getPosition().x + 50f, getPosition().y - 50f, getPosition().z);
        GL12.glTexCoord2f(1f, 1f);
        debug_vertex3f(getPosition().x + 50f, getPosition().y + 50f, getPosition().z);
        GL12.glTexCoord2f(0f, 1f);
        debug_vertex3f(getPosition().x - 50f, getPosition().y + 50f, getPosition().z);

        GL12.glBindTexture(GL12.GL_TEXTURE_2D, 0);
        GL12.glEnd();
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
