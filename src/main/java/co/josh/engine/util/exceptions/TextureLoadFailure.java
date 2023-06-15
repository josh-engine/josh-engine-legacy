package co.josh.engine.util.exceptions;

public class TextureLoadFailure extends Exception {
    public TextureLoadFailure(String error){
        super("TextureLoadFailure: " + error);
    }
}
