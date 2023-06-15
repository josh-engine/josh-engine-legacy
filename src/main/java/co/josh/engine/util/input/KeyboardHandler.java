package co.josh.engine.util.input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

public class KeyboardHandler {
    public boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];
    public long window;

    public GLFWKeyCallback keyCallback;

    public KeyboardHandler(long window){
        this.window = window;

        keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                keys[key] = (action != GLFW.GLFW_RELEASE);
            }
        };

        GLFW.glfwSetKeyCallback(window, keyCallback);
    }


    public void update() {
        GLFW.glfwPollEvents();
    }

    public boolean isKeyDown(int keycode) {
        return keys[keycode];
    }
}
