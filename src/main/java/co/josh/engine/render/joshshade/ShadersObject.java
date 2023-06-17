package co.josh.engine.render.joshshade;

import java.util.ArrayList;

public class ShadersObject {
    public ArrayList<JShader> shaders;

    public ArrayList<Object> shaderData;

    public ShadersObject(ArrayList<JShader> shaders, ArrayList<Object> data){
        this.shaders = shaders;
        this.shaderData = data;
    }
}
