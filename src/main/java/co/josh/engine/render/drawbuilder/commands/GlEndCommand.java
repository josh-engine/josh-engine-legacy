package co.josh.engine.render.drawbuilder.commands;

import org.lwjgl.opengl.GL33;

public class GlEndCommand implements DrawBuilderCommand {
    public void run(int GL_MODE, int i, float t){
        try{
            GL33.glEnd();
        } catch (Exception e) {
            System.out.println("GL_ERROR END, ITER "+i+ " MODE "+ GL33.glGetString(GL_MODE));
        }
    }
}
