package co.josh.engine.util.input;

import co.josh.engine.util.annotations.hooks.OnClick;
import org.lwjgl.BufferUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.DoubleBuffer;
import java.util.Set;

import co.josh.engine.Main;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;

public class MouseHandler {
    public long window;

    public int xPos;
    public int yPos;

    public float relativeCurX;
    public float relativeCurY;

    public GLFWMouseButtonCallback mouseCallback;

    public MouseHandler(long window){
        this.window = window;

        Set<Method> onClickRunnables = Main.getAllAnnotatedWith(OnClick.class);

        mouseCallback = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                Object[] params = {button, action};
                try {
                    Main.run(onClickRunnables, params);
                } catch (InvocationTargetException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        GLFW.glfwSetMouseButtonCallback(window, mouseCallback);
    }

    public void update(){
        DoubleBuffer xBuffer = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer yBuffer = BufferUtils.createDoubleBuffer(1);
        glfwGetCursorPos(window, xBuffer, yBuffer);
        this.xPos = (int)xBuffer.get(0);
        this.yPos = Main.currentHeight-(int)yBuffer.get(0);
        this.relativeCurX = ((Main.width/(float)Main.currentWidth)*this.xPos);
        this.relativeCurY = ((Main.height/(float)Main.currentHeight)*this.yPos);
    }

}
