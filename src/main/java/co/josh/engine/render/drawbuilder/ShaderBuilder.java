package co.josh.engine.render.drawbuilder;

import co.josh.engine.Main;
import co.josh.engine.render.Camera;
import co.josh.engine.util.render.Vertex3F;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.lwjgl.system.MemoryUtil.memFree;

public class ShaderBuilder {

    public Camera camera;
    public List<Vertex3F> drawList;

    public ArrayList<Float> verticesList = new ArrayList<Float>();

    public int vaoId;
    public int vboId;

    public ShaderBuilder(Camera camera){
        this.camera = camera;
        this.drawList = new LinkedList<>();
    }

    public static Vector3f applyCameraRotationMatrix(Vector3f start, Camera camera){
        Matrix4f rotationMatrix = camera.rotationMatrix;
        Vector3f relativeVector = start.sub(camera.position);
        Vector3f rotatedRelativeVector = new Vector3f();
        rotationMatrix.transformPosition(relativeVector, rotatedRelativeVector);
        return new Vector3f(rotatedRelativeVector).add(camera.position);
    }

    public Vertex3F next(){
        return new Vertex3F(new Vector3f(0f,0f,0f), new Vector4f(1f, 1f, 1f, 0f));
    }

    public void push(Vertex3F vert){
        drawList.add(vert);
    }

    public void loadVAOsVBOs(float t){
        for (Vertex3F vertex : drawList){
            Vector3f vertexPos = DrawBuilder.applyCameraRotationMatrix(vertex.lastposition.lerp(vertex.position, t), Main.camera);
            verticesList.add((vertexPos.x - Main.camera.position.x)*
                    ((float) Main.currentWidth/(float)Main.width));

            verticesList.add((vertexPos.y - Main.camera.position.y)*
                    ((float)Main.currentHeight/(float)Main.height));

            verticesList.add((vertexPos.z - Main.camera.position.z)*
                    ((float)Main.currentHeight/(float)Main.height));

        }
        float[] vertices = new float[verticesList.size()];
        int i = 0;
        for (Float f : verticesList) {
            vertices[i++] = (f != null ? f : 0f);
        }
        FloatBuffer verticesBuffer = MemoryUtil.memAllocFloat(vertices.length);
        verticesBuffer.put(vertices).flip();

        //VAO setup
        vaoId = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoId);

        //Define VAO data.
        GL30.glVertexAttribPointer(0, //starting index - Start at index 0,
                // but we can set it to skip over some
                3, //size - How many things it processes at a time.
                //In this case, we have blocks of 3 floats (XYZ coordinates)
                //so it's three
                GL30.GL_FLOAT, //Data type - tell it what data it contains (floats)
                false, //Normalized - whether to normalize the data or not
                0, //...byte offset between values? idk. 0 is default
                0); //pointer to first item in array. 0 is default

        //VBO
        vboId = GL30.glGenBuffers();
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, vboId);
        GL30.glBufferData(GL30.GL_ARRAY_BUFFER, verticesBuffer, GL30.GL_STATIC_DRAW);
        memFree(verticesBuffer);

        //unbind buffers
        GL30.glBindVertexArray(0);
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0);

        //Free if not freed already
        if (verticesBuffer != null) {
            MemoryUtil.memFree(verticesBuffer);
        }
    }

    public void render(){
        GL30.glDisableVertexAttribArray(0);
        // Delete the VBO
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0);
        GL30.glDeleteBuffers(vboId);
        // Delete the VAO
        GL30.glBindVertexArray(0);
        GL30.glDeleteVertexArrays(vaoId);
    }




}
