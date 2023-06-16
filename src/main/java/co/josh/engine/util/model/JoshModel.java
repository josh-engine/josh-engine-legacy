package co.josh.engine.util.model;

import co.josh.engine.render.drawbuilder.commands.*;
import co.josh.engine.util.render.Vertex3F;
import co.josh.engine.util.texture.TexturePreloader;
import org.joml.Vector3f;

import java.util.ArrayList;

public class JoshModel {

    ArrayList<Vertex3F> vertices;

    String textureName;

    int textureId;

    public int GL_MODE;

    public JoshModel(String textureName, int GL_MODE, ArrayList<Vertex3F> vertices){
        this.vertices = vertices;
        this.textureName = textureName;
        this.textureId = TexturePreloader.textures.get(textureName);
        this.GL_MODE = GL_MODE;
    }

    public ArrayList<DrawBuilderCommand> drawBuilderCommands(Vector3f position, Vector3f lastposition){
        ArrayList<DrawBuilderCommand> commands = new ArrayList<>();
        commands.add(new UnbindTexturesCommand());
        commands.add(new BindTextureCommand(textureId));
        commands.add(new GlBeginCommand());
        for (Vertex3F vertex : vertices){
            Vertex3F vert = vertex.clone();
            vert.position.add(position);
            vert.lastposition.add(lastposition);
            commands.add(new VertexCommand(vert));
        }
        commands.add(new GlEndCommand());
        commands.add(new UnbindTexturesCommand());
        return commands;
    }
}
