package co.josh.engine.util.input;

import co.josh.engine.Main;
import co.josh.engine.util.annotations.hooks.OnKey;
import co.josh.engine.util.exceptions.MethodInvocationFailure;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

public class KeyboardHandler {
    public boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];
    public long window;

    public GLFWKeyCallback keyCallback;

    public KeyboardHandler(long window){
        this.window = window;

        Set<Method> onKeyRunnables = Main.getAllAnnotatedWith(OnKey.class);

        keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                keys[key] = (action != GLFW.GLFW_RELEASE);
                Object[] params = {key, action};
                try {
                    Main.run(onKeyRunnables, params);
                } catch (InvocationTargetException | IllegalAccessException e) {
                    throw new MethodInvocationFailure(onKeyRunnables);
                }
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
