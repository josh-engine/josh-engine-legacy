package co.josh.engine.objects.o2d;

import co.josh.engine.util.vector.Vector3f;
import co.josh.engine.components.Component;

import java.util.ArrayList;

public interface GameObject {
    String getName();
    Vector3f getPosition();
    Vector3f getNextPosition();
    void movePosition(int x, int y, int z);
    void render();
    ArrayList<Component> getComponents();
    void addComponent(Component c);
    float getSize();
    void setSize(float size);
    

}
