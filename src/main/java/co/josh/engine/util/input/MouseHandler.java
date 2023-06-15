package co.josh.engine.util.input;

import org.lwjgl.BufferUtils;

import java.nio.DoubleBuffer;

import co.josh.engine.Main;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;

public class MouseHandler {

    public MouseHandler(long window){
        this.window = window;
    }
    public long window;

    public int xPos;
    public int yPos;

    public float relativeCurX;
    public float relativeCurY;

    public void update(){
        DoubleBuffer xBuffer = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer yBuffer = BufferUtils.createDoubleBuffer(1);
        glfwGetCursorPos(window, xBuffer, yBuffer);
        this.xPos = (int)xBuffer.get(0);
        this.yPos = (int)yBuffer.get(0);
        this.relativeCurX = ((Main.currentWidth/(float)Main.width)*this.xPos);
        this.relativeCurY = ((Main.currentHeight/(float)Main.height)*this.yPos);
    }

}
