package co.josh.engine.util.exceptions;

public class JoshShaderFailure extends RuntimeException {
    public JoshShaderFailure(String error){
        super(error);
    }
}
