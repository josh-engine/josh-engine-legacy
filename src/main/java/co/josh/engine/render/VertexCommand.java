package co.josh.engine.render;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import co.josh.engine.Main;

import static org.lwjgl.opengl.GL11.*;

public class VertexCommand implements DrawBuilderCommand{
    public Vertex3F vertex;
    public VertexCommand(Vertex3F vertex){
        this.vertex = vertex;
    }

    public void run(int GL_MODE, int i) {
        //Vector3f vertexPos = rotato_potato(Vector3f.lerp(vertex.position, vertex.nextPos, t), camera);
        Vector3f vertexPos = DrawBuilder.rotato_potato(vertex.position, Main.camera);
        if (vertex.textured){
            GL11.glTexCoord2f(vertex.texcoords.x, vertex.texcoords.y);
        }else{
            GL11.glColor4f(vertex.color.x, vertex.color.y, vertex.color.z, vertex.color.w);
        }
        GL11.glVertex3f((vertexPos.x - Main.camera.position.x)*((float) Main.currentWidth/(float)Main.width), (vertexPos.y - Main.camera.position.y)*((float)Main.currentHeight/(float)Main.height), (vertexPos.z - Main.camera.position.z)*((float)Main.currentHeight/(float)Main.height));

    }
}
