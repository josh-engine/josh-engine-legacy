package co.josh.engine.util.vector;

public class Vector3f {
    public float x;
    public float y;
    public float z;

    public Vector3f(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Vector3f Vector3f_joml(org.joml.Vector3f a){
        return new Vector3f(a.x, a.y, a.z);
    }

    public org.joml.Vector3f joml_compat(){
        return new org.joml.Vector3f(this.x, this.y, this.z);
    }


    public void set(Vector3f _new){
        this.x = _new.x;
        this.y = _new.y;
        this.z = _new.z;
    }

    public static Vector3f lerp(Vector3f a, Vector3f b, float t){
        return new Vector3f(a.x + (b.x - a.x) * t, a.y + (b.y - a.y) * t, a.z + (b.z - a.z) * t);
    }

    public void move(float x, float y, float z){
        this.x += x;
        this.y += y;
        this.z += z;
    }

    public void move(Vector3f a){
        move(a.x, a.y, a.z);
    }

    public void move(org.joml.Vector3f a){
        move(a.x, a.y, a.z);
    }

    public String toString(){
        return this.x + ", " + this.y + ", " + this.z;
    }
}
