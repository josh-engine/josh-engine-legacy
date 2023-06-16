package co.josh.engine.render.drawbuilder.commands;


import org.lwjgl.opengl.GL33;

public class GlBeginCommand implements DrawBuilderCommand {

    public GlBeginCommand(){

    }

    public void run(int GL_MODE, int i, float t){
        try{
            GL33.glBegin(GL_MODE);
        } catch (Exception e) {
            System.out.println("GL_ERROR BEGIN, ITER "+i+ " MODE "+ GL33.glGetString(GL_MODE));
        }
    }
}
