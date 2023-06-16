package co.josh.engine.render.drawbuilder.commands;

import org.lwjgl.opengl.GL33;

public class UnbindTexturesCommand implements DrawBuilderCommand {

    public void run(int GL_MODE, int i, float t){
        try{
            GL33.glBindTexture(GL33.GL_TEXTURE_2D, 0);
        } catch (Exception e) {
            System.out.println("GL_ERROR UNBIND, ITER "+i+ " MODE "+ GL33.glGetString(GL_MODE));
        }
    }
}
