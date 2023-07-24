package co.josh.engine.util;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Transform {

    public Vector3f position;

    public Vector3f rotation;

    public Matrix4f rotationMatrix;

    public Vector3f scale;

    public Transform(Vector3f position, Vector3f rotation, Vector3f scale){
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    public Transform(Vector3f position){
        this.position = position;
        this.rotation = new Vector3f();
        this.scale = new Vector3f(1f);
    }

    public Vector3f apply(Vector3f vector){
        updateRotationMatrix();
        return applyRotationMatrix(vector.mul(scale).add(position), position, rotationMatrix);
    }

    public void updateRotationMatrix(){
        rotationMatrix = new Matrix4f();
        rotationMatrix.identity();
        rotationMatrix.rotate((float) Math.toRadians(rotation.x), 1, 0, 0); // Rotate around the X axis
        rotationMatrix.rotate((float) Math.toRadians(rotation.y), 0, 1, 0); // Rotate around the Y axis
        rotationMatrix.rotate((float) Math.toRadians(rotation.z), 0, 0, 1); // Rotate around the Z axis
    }

    public static Vector3f applyRotationMatrix(Vector3f point, Vector3f origin, Matrix4f rotationMatrix){
        Vector3f relativeVector = point.sub(origin);
        Vector3f rotatedRelativeVector = new Vector3f();
        rotationMatrix.transformPosition(relativeVector, rotatedRelativeVector);
        return new Vector3f(rotatedRelativeVector).add(origin);
    }

    public Transform clone(){
        return new Transform(new Vector3f(position.x(), position.y(), position.z()), new Vector3f(rotation.x(), rotation.y(), rotation.z()), new Vector3f(scale.x(), scale.y(), scale.z()));
    }
}
