package co.josh.engine.components;

import co.josh.engine.objects.GameObject;

public interface Component {

    String getName();

    void onTick();

    void onFrame();

    GameObject getParent();

}
