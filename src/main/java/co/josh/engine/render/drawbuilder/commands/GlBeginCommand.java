package co.josh.engine.render.drawbuilder.commands;

import co.josh.engine.render.joshshade.JShader;
import co.josh.engine.render.joshshade.ShadersObject;
import org.lwjgl.opengl.GL12;

import java.util.ArrayList;

public class GlBeginCommand implements DrawBuilderCommand {

    public GlBeginCommand(){

    }

    public void run(int GL_MODE, int i, ShadersObject shaders, float t){
        try{
            GL12.glBegin(GL_MODE);
        } catch (Exception e) {
            System.out.println("GL_ERROR BEGIN, ITER "+i+ " MODE "+ GL12.glGetString(GL_MODE));
        }
    }
}
