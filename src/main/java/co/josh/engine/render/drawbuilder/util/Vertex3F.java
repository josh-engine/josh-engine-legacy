package co.josh.engine.render.drawbuilder.util;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Vertex3F {
    public Vector3f position;
    public Vector4f color;
    public Vector2f texcoords;
    public Vector3f lastposition;

    public Vertex3F(Vector3f pos, Vector4f col){
        this.position = pos;
        this.lastposition = pos;
        this.color = col;
        this.texcoords = new Vector2f(0f, 0f);
    }

    Vertex3F(Vector3f pos, Vector3f lastpos, Vector4f col, Vector2f texcoords){
        this.position = pos;
        this.lastposition = lastpos;
        this.color = col;
        this.texcoords = texcoords;
    }

    public Vertex3F pos(float x, float y, float z){
        return new Vertex3F(new Vector3f(x, y, z), new Vector3f(x, y, z), color, texcoords);
    }

    public Vertex3F lastpos(float x, float y, float z){
        return new Vertex3F(position, new Vector3f(x, y, z), color, texcoords);
    }

    public Vertex3F col(float r, float g, float b, float a){
        return new Vertex3F(position, lastposition, new Vector4f(r, g, b, a), texcoords);
    }

    public Vertex3F uv(float x, float y){
        return new Vertex3F(position, lastposition, color, new Vector2f(x, y));
    }

    public String toString(){
        return position.toString();
    }
}
