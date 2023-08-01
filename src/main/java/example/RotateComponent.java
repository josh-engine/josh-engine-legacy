package example;

import co.josh.engine.Main;
import co.josh.engine.components.Component;
import co.josh.engine.objects.GameObject;
import co.josh.engine.util.Transform;

public class RotateComponent implements Component {

    GameObject parent;

    float speed;

    public RotateComponent(GameObject parent, float speed){
        this.parent = parent;
        this.speed = speed;
    }

    @Override
    public String getName() {
        return "YouSpinMeRightRoundBabyRightRoundLikeARecordBabyRightRoundRoundRound";
    }

    @Override
    public void onTick() {
        Transform t = parent.getTransform();
        t.rotation = t.rotation.add(0f, speed*Main.tickDeltaTime, 0f);
        parent.setTransform(t);
    }

    @Override
    public GameObject getParent() {
        return parent;
    }
}
