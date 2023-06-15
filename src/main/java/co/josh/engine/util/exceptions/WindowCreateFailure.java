package co.josh.engine.util.exceptions;

public class WindowCreateFailure extends Exception {
    public WindowCreateFailure(){
        super("Failed to create a GLFW window");
    }
}
