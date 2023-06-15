package co.josh.engine.render;

import org.lwjgl.opengl.GL11;
import co.josh.engine.Main;
import co.josh.engine.util.vector.Vector3f;

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
            if ((GL_MODE == GL_TRIANGLES && i%3 == 0)||(GL_MODE == GL_QUADS && i%4 == 0)){ // This will be useful for rendering chunks if I decide to make a minecraft clone for the lolz
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0); // Unbind the texture
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, vertex.textureid); // Bind the texture
            }
            GL11.glTexCoord2f(vertex.texcoords.x, vertex.texcoords.y);
        }else{
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
            GL11.glColor4f(vertex.color.x, vertex.color.y, vertex.color.z, vertex.color.w);
        }
        GL11.glVertex3f((vertexPos.x - Main.camera.position.x)*((float) Main.currentWidth/(float)Main.width), (vertexPos.y - Main.camera.position.y)*((float)Main.currentHeight/(float)Main.height), (vertexPos.z - Main.camera.position.z)*((float)Main.currentHeight/(float)Main.height));

    }
}
