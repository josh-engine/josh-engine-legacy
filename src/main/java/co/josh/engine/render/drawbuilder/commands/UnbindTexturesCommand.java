package co.josh.engine.render.drawbuilder.commands;

import co.josh.engine.render.joshshade.ShadersObject;
import org.lwjgl.opengl.GL13;

public class UnbindTexturesCommand implements DrawBuilderCommand {

    public void run(int GL_MODE, int i, ShadersObject shaders, float t){
        try{
            GL13.glBindTexture(GL13.GL_TEXTURE_2D, 0);
        } catch (Exception e) {
            System.out.println("GL_ERROR UNBIND, ITER "+i+ " MODE "+ GL13.glGetString(GL_MODE));
        }
    }
}
