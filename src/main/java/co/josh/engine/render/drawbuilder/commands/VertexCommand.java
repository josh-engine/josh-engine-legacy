package co.josh.engine.render.drawbuilder.commands;

import co.josh.engine.render.drawbuilder.DrawBuilder;
import co.josh.engine.render.joshshade.JShader;
import co.josh.engine.render.joshshade.ShadersObject;
import co.josh.engine.util.render.Vertex3F;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL12;
import co.josh.engine.Main;

public class VertexCommand implements DrawBuilderCommand {

    public Vertex3F vertex;

    public VertexCommand(Vertex3F vertex){
        this.vertex = vertex;
    }

    public void run(int GL_MODE, int i, ShadersObject shaders, float t){
        for (JShader shader : shaders.shaders){
            vertex = shader.shade(vertex.clone(), shaders.shaderData);
        }
        Vector3f vertexPos = DrawBuilder.applyCameraRotationMatrix(vertex.lastposition.lerp(vertex.position, t), Main.camera);
        GL12.glTexCoord2f(vertex.texcoords.x, vertex.texcoords.y);
        GL12.glColor4f(vertex.color.x, vertex.color.y, vertex.color.z, vertex.color.w);
        GL12.glNormal3f(vertex.normal.x, vertex.normal.y, vertex.normal.z);
        GL12.glVertex3f(
                (vertexPos.x - Main.camera.position.x)*((float)Main.currentWidth/(float)Main.width),
                (vertexPos.y - Main.camera.position.y)*((float)Main.currentHeight/(float)Main.height),
                (vertexPos.z - Main.camera.position.z)*((float)Main.currentHeight/(float)Main.height));

    }
}
