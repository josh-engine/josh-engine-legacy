package co.josh.engine.render.drawbuilder.commands;

import org.lwjgl.opengl.GL12;

public class GlEndCommand implements DrawBuilderCommand {
    public GlEndCommand(){

    }

    public void run(int GL_MODE, int i){
        try{
            GL12.glEnd();
        } catch (Exception e) {
            System.out.println("GL_ERROR END, ITER "+i+ " MODE "+ GL12.glGetString(GL_MODE));
        }
    }
}
