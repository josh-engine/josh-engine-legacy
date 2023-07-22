package example;

import co.josh.engine.components.Component;
import co.josh.engine.objects.GameObject;
import co.josh.engine.util.Transform;

public class RotateComponent implements Component {

    GameObject parent;

    public RotateComponent(GameObject parent){
        this.parent = parent;
    }

    @Override
    public String getName() {
        return "YouSpinMeRightRoundBabyRightRoundLikeARecordBabyRightRoundRoundRound";
    }

    @Override
    public void tickValues() {
        Transform t = parent.getTransform();
        t.rotation = t.rotation.add(0f, 1f, 0f);
        parent.setTransform(t);
    }

    @Override
    public GameObject getParent() {
        return parent;
    }
}
