package co.josh.engine.util.vector;

public class Vector2f {
    public float x;
    public float y;

    public Vector2f(float x, float y){
        this.x = x;
        this.y = y;
    }

    public void move(float x, float y){
        this.x += x;
        this.y += y;
    }
}
