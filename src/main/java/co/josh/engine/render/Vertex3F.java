package co.josh.engine.render;

import co.josh.engine.util.vector.Vector3f;
import co.josh.engine.util.vector.Vector2f;
import co.josh.engine.util.vector.Vector4f;

public class Vertex3F {
    public Vector3f position;
    public Vector4f color;
    public Vector2f texcoords;
    public boolean textured;
    public int textureid;

    public Vector3f nextPos;

    public Vertex3F(Vector3f pos, Vector4f col){
        this.position = pos;
        this.color = col;
        this.textured = false;
        this.texcoords = new Vector2f(0f, 0f);
        this.textureid = 0;
        this.nextPos = pos;
    }

    Vertex3F(Vector3f pos, Vector4f col, boolean textured, Vector2f texcoords, int textureid, Vector3f nextPos){
        this.position = pos;
        this.color = col;
        this.textured = textured;
        this.texcoords = texcoords;
        this.textureid = textureid;
        this.nextPos = nextPos;
    }

    public Vertex3F pos(float x, float y, float z){
        return new Vertex3F(new Vector3f(x, y, z), color, textured, texcoords, textureid, nextPos);
    }

    public Vertex3F col(float r, float g, float b, float a){
        return new Vertex3F(position, new Vector4f(r, g, b, a), textured, texcoords, textureid, nextPos);
    }

    public Vertex3F tex(boolean isTextured){
        return new Vertex3F(position, color, isTextured, texcoords, textureid, nextPos);
    }

    public Vertex3F uv(float x, float y){
        return new Vertex3F(position, color, textured, new Vector2f(x, y), textureid, nextPos);
    }

    public Vertex3F nextpos(float x, float y, float z){
        return new Vertex3F(position, color, textured, texcoords, textureid, new Vector3f(x, y, z));
    }

    public Vertex3F bind(int id){
        return new Vertex3F(position, color, textured, texcoords, id, nextPos);
    }

    public String toString(){
        return position.toString();
    }
}
