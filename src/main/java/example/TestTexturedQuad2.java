package example;

import co.josh.engine.Main;
import co.josh.engine.components.Component;
import co.josh.engine.objects.o2d.GameObject;
import co.josh.engine.render.drawbuilder.commands.BindTextureCommand;
import co.josh.engine.render.drawbuilder.DrawBuilder;
import co.josh.engine.render.drawbuilder.commands.GlBeginCommand;
import co.josh.engine.render.drawbuilder.commands.GlEndCommand;
import co.josh.engine.render.drawbuilder.commands.UnbindTexturesCommand;
import co.josh.engine.util.TexturePreloader;
import org.joml.Vector3f;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL12.GL_QUADS;

public class TestTexturedQuad2 implements GameObject {

    float size;

    ArrayList<Component> components = new ArrayList<>();

    Vector3f vector3F;

    public int textureId;

    public TestTexturedQuad2(float x, float y, float z){
        this.vector3F = new Vector3f(x, y, z);
        this.textureId = TexturePreloader.textures.get("dirt");
        this.size = 1f;
        System.out.println("Quad2:"+textureId);
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


    public void render() {
        DrawBuilder db = new DrawBuilder(Main.camera, GL_QUADS);
        db.push(new UnbindTexturesCommand());
        db.push(new BindTextureCommand(this.textureId));
        db.push(new GlBeginCommand());

        db.push(db.next()
                .pos(getPosition().x - 50f, getPosition().y - 50f, getPosition().z)
                .uv(0f, 0f)
                .col(1f, 0f, 0f, 1f));
        db.push(db.next()
                .pos(getPosition().x + 50f, getPosition().y - 50f, getPosition().z)
                .uv(1f, 0f)
                .col(0f, 1f, 0f, 1f));
        db.push(db.next()
                .pos(getPosition().x + 50f, getPosition().y + 50f, getPosition().z)
                .uv(1f, 1f)
                .col(0f, 0f, 1f, 1f));
        db.push(db.next()
                .pos(getPosition().x - 50f, getPosition().y + 50f, getPosition().z)
                .uv(0f, 1f));

        db.push(new GlEndCommand());
        db.push(new UnbindTexturesCommand());
        db.render();
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
