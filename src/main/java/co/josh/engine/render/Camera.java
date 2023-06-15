package co.josh.engine.render;

import org.joml.Matrix4f;
import co.josh.engine.util.vector.Vector3f;

public class Camera {
    public Vector3f position;
    public Matrix4f rotationMatrix;
    public Vector3f rotation;
    public Camera(Vector3f position, Vector3f rotation){
        this.position = position;
        this.rotation = rotation;
        updateRotationMatrix();
    }

    public void moveWithRotation(Vector3f change){
        position.move(change.joml_compat().mulTransposeDirection(rotationMatrix));
    }

    public void rotate(Vector3f change){
        rotation.move(change.x, change.y, change.z);
        updateRotationMatrix();
    }

    public void updateRotationMatrix(){
        rotationMatrix = new Matrix4f();
        rotationMatrix.identity();
        rotationMatrix.rotate((float) Math.toRadians(rotation.x), 1, 0, 0); // Rotate around the X axis
        rotationMatrix.rotate((float) Math.toRadians(rotation.y), 0, 1, 0); // Rotate around the Y axis
        rotationMatrix.rotate((float) Math.toRadians(rotation.z), 0, 0, 1); // Rotate around the Z axis
    }
}
