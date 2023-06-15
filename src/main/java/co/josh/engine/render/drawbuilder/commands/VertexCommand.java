package co.josh.engine.render.drawbuilder.commands;

import co.josh.engine.render.drawbuilder.DrawBuilder;
import co.josh.engine.render.drawbuilder.util.Vertex3F;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL12;
import co.josh.engine.Main;

public class VertexCommand implements DrawBuilderCommand {
    public Vertex3F vertex;
    public VertexCommand(Vertex3F vertex){
        this.vertex = vertex;
    }

    public void run(int GL_MODE, int i, float t) {
        //TODO: smoothing with lerp between ticks (so that it runs at an actual 100FPS, not just synced with the 20TPS)
        Vector3f vertexPos = DrawBuilder.rotato_potato(vertex.lastposition.lerp(vertex.position, t), Main.camera);
        //Vector3f vertexPos = DrawBuilder.rotato_potato(vertex.position, Main.camera);
        GL12.glTexCoord2f(vertex.texcoords.x, vertex.texcoords.y);
        GL12.glColor4f(vertex.color.x, vertex.color.y, vertex.color.z, vertex.color.w);
        GL12.glVertex3f((vertexPos.x - Main.camera.position.x)*((float) Main.currentWidth/(float)Main.width), (vertexPos.y - Main.camera.position.y)*((float)Main.currentHeight/(float)Main.height), (vertexPos.z - Main.camera.position.z)*((float)Main.currentHeight/(float)Main.height));

    }
}
