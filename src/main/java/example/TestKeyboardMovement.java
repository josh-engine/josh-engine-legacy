package example;

import co.josh.engine.components.Component;
import org.lwjgl.glfw.GLFW;
import co.josh.engine.Main;
import co.josh.engine.objects.o2d.GameObject;

public class TestKeyboardMovement implements Component {

    public GameObject parent;
    int hitboxSize;

    public TestKeyboardMovement(GameObject parent){
        this.parent = parent;
        this.hitboxSize = (int) parent.getSize();
    }

    @Override
    public String getName() {
        return "TestKeyboardMovementComponent";
    }

    @Override
    public void tickValues() {
        if (Main.keyboard.isKeyDown(GLFW.GLFW_KEY_W) && parent.getPosition().y + hitboxSize < Main.height){
            parent.getPosition().add(0, 5, 0);
        }
        if (Main.keyboard.isKeyDown(GLFW.GLFW_KEY_S) && parent.getPosition().y - hitboxSize > 0){
            parent.getPosition().add(0, -5, 0);
        }
        if (Main.keyboard.isKeyDown(GLFW.GLFW_KEY_A) && parent.getPosition().x - hitboxSize > 0){
            parent.getPosition().add(-5, 0, 0);
        }
        if (Main.keyboard.isKeyDown(GLFW.GLFW_KEY_D) && parent.getPosition().x + hitboxSize < Main.width){
            parent.getPosition().add(5, 0, 0);
        }
    }

    @Override
    public GameObject getParent() {
        return parent;
    }
}
