package co.josh.engine.util.model.jmodel;

import co.josh.engine.Main;
import co.josh.engine.render.drawbuilder.commands.*;
import co.josh.engine.render.lights.Light;
import co.josh.engine.util.Transform;
import co.josh.engine.util.render.Vertex3F;
import co.josh.engine.util.texture.TexturePreloader;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL13;

import java.util.ArrayList;

public class JoshModel {

    ArrayList<Vertex3F> vertices;

    String textureName;

    int textureId;

    public int GL_MODE;

    public boolean lit;

    public boolean tex;

    public JoshModel(String textureName, int GL_MODE, ArrayList<Vertex3F> vertices, boolean lit, boolean textured){
        this.vertices = vertices;
        this.textureName = textureName;
        if (textured) this.textureId = TexturePreloader.textures.get(textureName);
        this.GL_MODE = GL_MODE;
        this.lit = lit;
        this.tex = textured;
    }

    public ArrayList<DrawBuilderCommand> drawBuilderCommands(Transform transform, Transform lastTransform){
        ArrayList<DrawBuilderCommand> commands = new ArrayList<>();
        commands.add(new UnbindTexturesCommand());
        if (lit){
            commands.add(new GlEnableCommand(GL13.GL_LIGHTING));
            for (Light light : Main.lights){
                commands.add(new GlEnableCommand(light.id));
            }
            commands.add(new GlEnableCommand(GL13.GL_COLOR_MATERIAL));
        }
        if (tex) commands.add(new BindTextureCommand(textureId));
        commands.add(new GlBeginCommand());
        for (Vertex3F vertex : vertices){
            Vertex3F vert = vertex.clone();
            vert.position = transform.apply(vert.position);
            vert.lastposition = lastTransform.apply(vert.lastposition);
            vert.normal = Transform.applyRotationMatrix(vert.normal, new Vector3f(), transform.rotationMatrix);
            commands.add(new VertexCommand(vert));
        }
        commands.add(new GlEndCommand());
        commands.add(new UnbindTexturesCommand());
        if (lit){
            commands.add(new GlDisableCommand(GL13.GL_LIGHTING));
            for (Light light : Main.lights){
                commands.add(new GlDisableCommand(light.id));
            }
            commands.add(new GlDisableCommand(GL13.GL_COLOR_MATERIAL));
        }
        return commands;
    }
}
