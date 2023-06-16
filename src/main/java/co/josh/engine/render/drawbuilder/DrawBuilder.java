package co.josh.engine.render.drawbuilder;

import co.josh.engine.render.Camera;
import co.josh.engine.render.drawbuilder.commands.DrawBuilderCommand;
import co.josh.engine.render.drawbuilder.commands.VertexCommand;
import co.josh.engine.util.render.Vertex3F;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.LinkedList;
import java.util.List;

public class DrawBuilder {

    public Camera camera;
    public int GL_MODE;
    public List<DrawBuilderCommand> drawList;

    public DrawBuilder(Camera camera, int GL_MODE){
        this.camera = camera;
        this.GL_MODE = GL_MODE;
        this.drawList = new LinkedList<>();
    }

    public static Vector3f rotato_potato(Vector3f start, Camera camera){
        Matrix4f rotationMatrix = camera.rotationMatrix;
        org.joml.Vector3f relativeVector = start.sub(camera.position);
        org.joml.Vector3f rotatedRelativeVector = new org.joml.Vector3f();
        rotationMatrix.transformPosition(relativeVector, rotatedRelativeVector);
        return new org.joml.Vector3f(rotatedRelativeVector).add(camera.position);
    }

    public Vertex3F next(){
        return new Vertex3F(new Vector3f(0f,0f,0f), new Vector4f(1f, 1f, 1f, 0f));
    }

    public void push(Vertex3F vert){
        drawList.add(new VertexCommand(vert));
    }
    public void push(DrawBuilderCommand drawBuilderCommand) { drawList.add(drawBuilderCommand);}

    public void render(float t){
        int total = drawList.size();
        for (int i = 0; i < total; i++){
            DrawBuilderCommand command = drawList.remove(0);
            command.run(GL_MODE, i, t);
        }
    }

    public void render(List<DrawBuilderCommand> commands, float t){
        int total = commands.size();
        for (int i = 0; i < total; i++){
            DrawBuilderCommand command = commands.remove(0);
            command.run(GL_MODE, i, t);
        }
    }




}
