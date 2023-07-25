package co.josh.engine.render;

import co.josh.engine.util.Transform;
import org.joml.Vector3f;

public class Camera {
    public Vector3f position(float tps){
        return lastTransform.position.lerp(transform.position, tps);
    }

    public Vector3f rotation(float tps){
        return lastTransform.rotation.lerp(transform.rotation, tps);
    }

    public Transform transform;

    public Transform lastTransform;

    public float clippingPlane = 0.1f;

    public Camera(Vector3f position, Vector3f rotation){
        lastTransform = new Transform(position, rotation, new Vector3f());
        transform = new Transform(position, rotation, new Vector3f());
        transform.updateRotationMatrix();
    }

    public void updateLast(){
        this.lastTransform = transform.clone();
    }

    public void moveWithRotation(Vector3f change){
        transform.position.add(change.mulTransposeDirection(transform.rotationMatrix));
    }

    public void rotate(Vector3f change){
        transform.rotation.add(change.x, change.y, change.z);
        transform.updateRotationMatrix();
    }
}
