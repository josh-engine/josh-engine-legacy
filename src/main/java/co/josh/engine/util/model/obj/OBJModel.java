package co.josh.engine.util.model.obj;

import co.josh.engine.Main;
import co.josh.engine.render.drawbuilder.commands.*;
import co.josh.engine.render.lights.Light;
import co.josh.engine.util.Transform;
import co.josh.engine.util.render.Vertex3F;
import co.josh.engine.util.texture.TexturePreloader;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL13;

import java.util.ArrayList;

public class OBJModel {

    ArrayList<DrawBuilderCommand> commands;

    public int GL_MODE;

    public boolean lit;

    public boolean tex;

    public OBJModel(int GL_MODE, ArrayList<DrawBuilderCommand> commands, boolean lit, boolean textured){
        this.commands = commands;
        this.GL_MODE = GL_MODE;
        this.lit = lit;
        this.tex = textured;
    }

    public ArrayList<DrawBuilderCommand> drawBuilderCommands(Transform transform, Transform lastTransform){
        int i = 0;
        for (DrawBuilderCommand command : commands){
            if (command instanceof VertexCommand){
                Vertex3F vert = ((VertexCommand) command).vertex.clone();
                //Apply GameObject transforms
                vert.position = transform.apply(vert.position);
                vert.lastposition = lastTransform.apply(vert.lastposition);
                vert.normal = Transform.applyRotationMatrix(vert.normal, new Vector3f(), transform.rotationMatrix);
                commands.set(i, new VertexCommand(vert));
            }
            i++;
        }
        return commands;
    }
}
