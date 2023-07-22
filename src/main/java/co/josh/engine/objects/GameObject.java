package co.josh.engine.objects;

import co.josh.engine.components.Component;
import co.josh.engine.util.Transform;
import org.joml.Vector3f;

import java.util.ArrayList;

public interface GameObject {

    String getName();

    Transform getTransform();

    Transform getLastTransform();

    void setTransform(Transform t);

    void setLastTransform(Transform t);

    void render();

    ArrayList<Component> getComponents();

    void addComponent(Component c);

}
