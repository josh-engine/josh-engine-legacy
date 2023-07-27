package co.josh.engine.components.builtin;


import co.josh.engine.components.Component;
import co.josh.engine.objects.GameObject;
import co.josh.engine.render.lights.Light;
import co.josh.engine.util.Transform;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class LightComponent implements Component {

    public Light light;

    public Vector3f position;

    public GameObject parent;

    public LightComponent(GameObject parent, Vector3f relativePosition, Vector4f diffuse, Vector4f specular){
        this.parent = parent;
        this.position = relativePosition;
        light = new Light();
        light.create(parent.getTransform().apply(relativePosition), true, diffuse, specular);
    }

    public String getName() {
        return "LightComponent";
    }

    public void onTick() {

    }

    public void onFrame() {
        Vector3f parentPosition = new Vector3f(parent.getTransform().position.x,
                                          parent.getTransform().position.y,
                                          parent.getTransform().position.z);
        parent.getTransform().updateRotationMatrix();
        Vector3f positionTemp = Transform.applyRotationMatrix(position, new Vector3f(), parent.getTransform().rotationMatrix);
        parentPosition.add(positionTemp);
        light.vector3f = parentPosition;

    }

    public GameObject getParent() {
        return null;
    }
}
