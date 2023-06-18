package co.josh.engine.util.render;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Vertex3F {

    public Vector3f position;

    public Vector3f lastposition;

    public Vector4f color;

    public Vector2f texcoords;

    public Vector3f normal;

    public Vertex3F(Vector3f pos, Vector4f col){
        this.position = pos;
        this.lastposition = pos;
        this.color = col;
        this.texcoords = new Vector2f(0f, 0f);
        this.normal = new Vector3f(0f, 0f, 1f); //OpenGL default.
    }

    public Vertex3F(Vector3f pos, Vector3f lastpos, Vector4f col, Vector2f texcoords, Vector3f normal){
        this.position = pos;
        this.lastposition = lastpos;
        this.color = col;
        this.texcoords = texcoords;
        this.normal = normal;
    }

    public Vertex3F pos(float x, float y, float z){
        return new Vertex3F(new Vector3f(x, y, z), new Vector3f(x, y, z), color, texcoords, normal);
    }

    public Vertex3F lastpos(float x, float y, float z){
        return new Vertex3F(position, new Vector3f(x, y, z), color, texcoords, normal);
    }

    public Vertex3F col(float r, float g, float b, float a){
        return new Vertex3F(position, lastposition, new Vector4f(r, g, b, a), texcoords, normal);
    }

    public Vertex3F normal(float x, float y, float z){
        return new Vertex3F(position, lastposition, color, texcoords, new Vector3f(x, y, z));
    }

    public Float[] dump(){
        return new Float[]{position.x, position.y, position.z, lastposition.x, lastposition.y, lastposition.z, color.x, color.y, color.z, color.w, texcoords.x, texcoords.y, normal.x, normal.y, normal.z};
    }

    public static Vertex3F pack(Float[] values){
        return new Vertex3F(new Vector3f(values[0], values[1], values[2]),
                new Vector3f(values[3], values[4], values[5]),
                new Vector4f(values[6], values[7], values[8], values[9]),
                new Vector2f(values[10], values[11]),
                new Vector3f(values[12], values[13], values[14]));
    }

    public Vertex3F uv(float x, float y){
        return new Vertex3F(position, lastposition, color, new Vector2f(x, y), normal);
    }

    public String toString(){
        return position.toString();
    }

    public Vertex3F clone() {
        return new Vertex3F(new Vector3f(position.x, position.y, position.z), new Vector3f(lastposition.x, lastposition.y, lastposition.z), new Vector4f(color.x, color.y, color.z, color.w), new Vector2f(texcoords.x, texcoords.y), new Vector3f(normal.x, normal.y, normal.z));
    }
}
