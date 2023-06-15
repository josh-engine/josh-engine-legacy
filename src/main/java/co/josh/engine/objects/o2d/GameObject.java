package co.josh.engine.objects.o2d;

import co.josh.engine.components.Component;
import org.joml.Vector3f;

import java.util.ArrayList;

public interface GameObject {
    String getName();
    Vector3f getPosition();
    Vector3f getLastPosition();
    void setPosition(Vector3f position);
    void setLastPosition(Vector3f lastPosition);
    void movePosition(int x, int y, int z);
    void render();
    ArrayList<Component> getComponents();
    void addComponent(Component c);
    float getSize();
    void setSize(float size);


}
