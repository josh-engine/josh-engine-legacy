package co.josh.engine.util.exceptions;

public class MaterialNotFoundError extends RuntimeException {
    public MaterialNotFoundError(String error){
        super(error);
    }
}
