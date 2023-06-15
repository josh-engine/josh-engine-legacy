package co.josh.engine.render;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Vertex3F {
    public Vector3f position;
    public Vector4f color;
    public Vector2f texcoords;
    public boolean textured;
    public Vector3f nextPos;

    public Vertex3F(Vector3f pos, Vector4f col){
        this.position = pos;
        this.color = col;
        this.textured = false;
        this.texcoords = new Vector2f(0f, 0f);
        this.nextPos = pos;
    }

    Vertex3F(Vector3f pos, Vector4f col, boolean textured, Vector2f texcoords, Vector3f nextPos){
        this.position = pos;
        this.color = col;
        this.texcoords = texcoords;
        this.nextPos = nextPos;
    }

    public Vertex3F pos(float x, float y, float z){
        return new Vertex3F(new Vector3f(x, y, z), color, textured, texcoords, nextPos);
    }

    public Vertex3F col(float r, float g, float b, float a){
        return new Vertex3F(position, new Vector4f(r, g, b, a), textured, texcoords, nextPos);
    }

    public Vertex3F tex(boolean isTextured){
        return new Vertex3F(position, color, isTextured, texcoords, nextPos);
    }

    public Vertex3F uv(float x, float y){
        return new Vertex3F(position, color, textured, new Vector2f(x, y), nextPos);
    }

    public Vertex3F nextpos(float x, float y, float z){
        return new Vertex3F(position, color, textured, texcoords, new Vector3f(x, y, z));
    }
    public String toString(){
        return position.toString();
    }
}
