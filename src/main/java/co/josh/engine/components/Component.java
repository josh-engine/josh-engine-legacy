package co.josh.engine.components;

import co.josh.engine.objects.o2d.GameObject;

public interface Component {

    String getName();

    void tickValues();

    GameObject getParent();

}
