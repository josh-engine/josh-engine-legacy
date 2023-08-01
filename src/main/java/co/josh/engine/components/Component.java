package co.josh.engine.components;

import co.josh.engine.objects.GameObject;

public interface Component {

    String getName();

    default void onTick(){

    }

    default void on3D(){

    }

    default void on2D(){

    }

    GameObject getParent();

}
