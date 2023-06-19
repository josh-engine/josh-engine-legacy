package co.josh.engine.render.drawbuilder.commands;

import co.josh.engine.render.joshshade.ShadersObject;
import org.lwjgl.opengl.GL12;

public class UnbindTexturesCommand implements DrawBuilderCommand {

    public void run(int GL_MODE, int i, ShadersObject shaders, float t){
        try{
            GL12.glBindTexture(GL12.GL_TEXTURE_2D, 0);
        } catch (Exception e) {
            System.out.println("GL_ERROR UNBIND, ITER "+i+ " MODE "+ GL12.glGetString(GL_MODE));
        }
    }
}
