package co.josh.engine.util.exceptions;

public class ShaderFailure extends RuntimeException {
    public ShaderFailure(String shaderName){
        super("ShaderFailure: " + shaderName);
    }
}
