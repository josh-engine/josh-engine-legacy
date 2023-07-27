package co.josh.engine.render.drawbuilder.commands;

import co.josh.engine.render.joshshade.JShader;
import co.josh.engine.render.joshshade.ShadersObject;
import co.josh.engine.util.render.Vertex3F;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL13;

public class VertexCommand implements DrawBuilderCommand {

    public Vertex3F vertex;

    public VertexCommand(Vertex3F vertex){
        this.vertex = vertex;
    }

    public void run(int GL_MODE, int i, ShadersObject shaders, float t){
        for (JShader shader : shaders.shaders){
            vertex = shader.shade(vertex.clone(), shaders.shaderData);
        }
        Vector3f vertexPos = vertex.lastposition.lerp(vertex.position, t);
        GL13.glTexCoord2f(vertex.texcoords.x, vertex.texcoords.y);
        GL13.glColor4f(vertex.color.x, vertex.color.y, vertex.color.z, vertex.color.w);
        GL13.glNormal3f(vertex.normal.x, vertex.normal.y, vertex.normal.z);
        GL13.glVertex3f(vertexPos.x, vertexPos.y, vertexPos.z);

    }
}
