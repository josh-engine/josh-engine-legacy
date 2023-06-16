package example;

import co.josh.engine.GameObject;
import co.josh.engine.render.drawbuilder.commands.*;
import co.josh.engine.render.drawbuilder.DrawBuilder;
import co.josh.engine.Main;
import co.josh.engine.Component;
import co.josh.engine.util.texture.TexturePreloader;
import org.joml.Vector3f;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL33.GL_QUADS;

public class TestTexturedQuad implements GameObject {

    float size;

    ArrayList<Component> components = new ArrayList<>();

    Vector3f position;
    Vector3f lastPosition;


    public int textureId;

    public TestTexturedQuad(float x, float y, float z){
        this.position = new Vector3f(x, y, z);
        this.lastPosition = new Vector3f(x, y, z);
        //this.textureId = TextureLoader.loadTexture(Main.dir + "/josh/e.png");
        this.textureId = TexturePreloader.textures.get("e");
        this.size = 1f;
        System.out.println("Quad1:"+textureId);
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
        Main.renderSystem.mainShaderBuilder.push(Main.renderSystem.mainShaderBuilder.next()
                .pos(getPosition().x - 50f, getPosition().y - 50f, getPosition().z)
                .lastpos(getLastPosition().x - 50f, getLastPosition().y - 50f, getLastPosition().z)
                .uv(0f, 0f));
        Main.renderSystem.mainShaderBuilder.push(Main.renderSystem.mainShaderBuilder.next()
                .pos(getPosition().x + 50f, getPosition().y - 50f, getPosition().z)
                .lastpos(getLastPosition().x + 50f, getLastPosition().y - 50f, getLastPosition().z)
                .uv(1f, 0f));
        Main.renderSystem.mainShaderBuilder.push(Main.renderSystem.mainShaderBuilder.next()
                .pos(getPosition().x + 50f, getPosition().y + 50f, getPosition().z)
                .lastpos(getLastPosition().x + 50f, getLastPosition().y + 50f, getLastPosition().z)
                .uv(1f, 1f));
        Main.renderSystem.mainShaderBuilder.push(Main.renderSystem.mainShaderBuilder.next()
                .pos(getPosition().x - 50f, getPosition().y + 50f, getPosition().z)
                .lastpos(getLastPosition().x - 50f, getLastPosition().y + 50f, getLastPosition().z)
                .uv(0f, 1f));
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
