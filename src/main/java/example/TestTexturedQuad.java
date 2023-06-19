package example;

import co.josh.engine.objects.GameObject;
import co.josh.engine.render.drawbuilder.commands.*;
import co.josh.engine.render.drawbuilder.DrawBuilder;
import co.josh.engine.Main;
import co.josh.engine.components.Component;
import co.josh.engine.util.texture.TexturePreloader;
import org.joml.Vector3f;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL12.GL_QUADS;

public class TestTexturedQuad implements GameObject {

    float size;

    ArrayList<Component> components = new ArrayList<>();

    Vector3f position;
    Vector3f lastPosition;

    public DrawBuilder db = new DrawBuilder(Main.camera, GL_QUADS);


    public int textureId;

    public TestTexturedQuad(float x, float y, float z){
        this.position = new Vector3f(x, y, z);
        this.lastPosition = new Vector3f(x, y, z);
        this.textureId = TexturePreloader.textures.get("dirt");
        this.size = 1f;
        db.addShader(Example.setwhite);
        db.addShader(Example.colbynorm);
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
        db.push(new UnbindTexturesCommand());
        db.push(new BindTextureCommand(this.textureId));
        db.push(new GlBeginCommand());

        //Z+ face
        db.push(db.next()
                .pos(getPosition().x - 1f, getPosition().y - 1f, getPosition().z + 1)
                .lastpos(getLastPosition().x - 1f, getLastPosition().y - 1f, getLastPosition().z + 1)
                .uv(0f, 0f));
        db.push(db.next()
                .pos(getPosition().x + 1f, getPosition().y - 1f, getPosition().z + 1)
                .lastpos(getLastPosition().x + 1f, getLastPosition().y - 1f, getLastPosition().z + 1)
                .uv(1f, 0f));
        db.push(db.next()
                .pos(getPosition().x + 1f, getPosition().y + 1f, getPosition().z + 1)
                .lastpos(getLastPosition().x + 1f, getLastPosition().y + 1f, getLastPosition().z + 1)
                .uv(1f, 1f));
        db.push(db.next()
                .pos(getPosition().x - 1f, getPosition().y + 1f, getPosition().z + 1)
                .lastpos(getLastPosition().x - 1f, getLastPosition().y + 1f, getLastPosition().z + 1)
                .uv(0f, 1f));

        //X- Face
        db.push(db.next()
                .pos(getPosition().x - 1, getPosition().y - 1f, getPosition().z - 1f)
                .lastpos(getLastPosition().x - 1, getLastPosition().y - 1f, getLastPosition().z - 1f)
                .uv(0f, 0f)
                .normal(-1f, 0f, 0f));
        db.push(db.next()
                .pos(getPosition().x - 1, getPosition().y - 1f, getPosition().z + 1f)
                .lastpos(getLastPosition().x - 1, getLastPosition().y - 1f, getLastPosition().z + 1f)
                .uv(1f, 0f)
                .normal(-1f, 0f, 0f));
        db.push(db.next()
                .pos(getPosition().x - 1, getPosition().y + 1f, getPosition().z + 1f)
                .lastpos(getLastPosition().x - 1, getLastPosition().y + 1f, getLastPosition().z + 1f)
                .uv(1f, 1f)
                .normal(-1f, 0f, 0f));
        db.push(db.next()
                .pos(getPosition().x - 1, getPosition().y + 1f, getPosition().z - 1f)
                .lastpos(getLastPosition().x - 1, getLastPosition().y + 1f, getLastPosition().z - 1f)
                .uv(0f, 1f)
                .normal(-1f, 0f, 0f));

        //Y+ Face
        db.push(db.next()
                .pos(getPosition().x - 1f, getPosition().y + 1f, getPosition().z - 1f)
                .lastpos(getLastPosition().x - 1, getLastPosition().y + 1f, getLastPosition().z - 1f)
                .uv(0f, 0f)
                .normal(0f, 1f, 0f));
        db.push(db.next()
                .pos(getPosition().x - 1f, getPosition().y + 1f, getPosition().z + 1f)
                .lastpos(getLastPosition().x - 1, getLastPosition().y + 1f, getLastPosition().z + 1f)
                .uv(1f, 0f)
                .normal(0f, 1f, 0f));
        db.push(db.next()
                .pos(getPosition().x + 1f, getPosition().y + 1f, getPosition().z + 1f)
                .lastpos(getLastPosition().x + 1, getLastPosition().y + 1f, getLastPosition().z + 1f)
                .uv(1f, 1f)
                .normal(0f, 1f, 0f));
        db.push(db.next()
                .pos(getPosition().x + 1f, getPosition().y + 1f, getPosition().z - 1f)
                .lastpos(getLastPosition().x + 1, getLastPosition().y + 1f, getLastPosition().z - 1f)
                .uv(0f, 1f)
                .normal(0f, 1f, 0f));

        //X+ Face
        db.push(db.next()
                .pos(getPosition().x + 1, getPosition().y + 1f, getPosition().z - 1f)
                .lastpos(getLastPosition().x + 1, getLastPosition().y + 1f, getLastPosition().z - 1f)
                .uv(0f, 1f)
                .normal(1f, 0f, 0f));
        db.push(db.next()
                .pos(getPosition().x + 1, getPosition().y + 1f, getPosition().z + 1f)
                .lastpos(getLastPosition().x + 1, getLastPosition().y + 1f, getLastPosition().z + 1f)
                .uv(1f, 1f)
                .normal(1f, 0f, 0f));
        db.push(db.next()
                .pos(getPosition().x + 1, getPosition().y - 1f, getPosition().z + 1f)
                .lastpos(getLastPosition().x + 1, getLastPosition().y - 1f, getLastPosition().z + 1f)
                .uv(1f, 0f)
                .normal(1f, 0f, 0f));
        db.push(db.next()
                .pos(getPosition().x + 1, getPosition().y - 1f, getPosition().z - 1f)
                .lastpos(getLastPosition().x + 1, getLastPosition().y - 1f, getLastPosition().z - 1f)
                .uv(0f, 0f)
                .normal(1f, 0f, 0f));

        //Y- Face
        db.push(db.next()
                .pos(getPosition().x + 1f, getPosition().y - 1f, getPosition().z - 1f)
                .lastpos(getLastPosition().x + 1, getLastPosition().y - 1f, getLastPosition().z - 1f)
                .uv(0f, 1f)
                .normal(0f, -1f, 0f));
        db.push(db.next()
                .pos(getPosition().x + 1f, getPosition().y - 1f, getPosition().z + 1f)
                .lastpos(getLastPosition().x + 1, getLastPosition().y - 1f, getLastPosition().z + 1f)
                .uv(1f, 1f)
                .normal(0f, -1f, 0f));
        db.push(db.next()
                .pos(getPosition().x - 1f, getPosition().y - 1f, getPosition().z + 1f)
                .lastpos(getLastPosition().x - 1, getLastPosition().y - 1f, getLastPosition().z + 1f)
                .uv(1f, 0f)
                .normal(0f, -1f, 0f));
        db.push(db.next()
                .pos(getPosition().x - 1f, getPosition().y - 1f, getPosition().z - 1f)
                .lastpos(getLastPosition().x - 1, getLastPosition().y - 1f, getLastPosition().z - 1f)
                .uv(0f, 0f)
                .normal(0f, -1f, 0f));

        //Z- face
        db.push(db.next()
                .pos(getPosition().x - 1f, getPosition().y + 1f, getPosition().z - 1)
                .lastpos(getLastPosition().x - 1f, getLastPosition().y + 1f, getLastPosition().z - 1)
                .uv(0f, 1f)
                .normal(0f, 0f, -1f));
        db.push(db.next()
                .pos(getPosition().x + 1f, getPosition().y + 1f, getPosition().z - 1)
                .lastpos(getLastPosition().x + 1f, getLastPosition().y + 1f, getLastPosition().z - 1)
                .uv(1f, 1f)
                .normal(0f, 0f, -1f));
        db.push(db.next()
                .pos(getPosition().x + 1f, getPosition().y - 1f, getPosition().z - 1)
                .lastpos(getLastPosition().x + 1f, getLastPosition().y - 1f, getLastPosition().z - 1)
                .uv(1f, 0f)
                .normal(0f, 0f, -1f));
        db.push(db.next()
                .pos(getPosition().x - 1f, getPosition().y - 1f, getPosition().z - 1)
                .lastpos(getLastPosition().x - 1f, getLastPosition().y - 1f, getLastPosition().z - 1)
                .uv(0f, 0f)
                .normal(0f, 0f, -1f));

        db.push(new GlEndCommand());
        db.push(new UnbindTexturesCommand());

        db.render((float)Main.tpsCount / Main.tps);
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