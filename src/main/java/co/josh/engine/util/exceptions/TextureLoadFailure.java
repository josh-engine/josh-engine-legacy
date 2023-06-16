package co.josh.engine.util.exceptions;

public class TextureLoadFailure extends RuntimeException {
    public TextureLoadFailure(String error){
        super("TextureLoadFailure: " + error);
    }
}
