package co.josh.engine.render.drawbuilder.commands;

import org.lwjgl.opengl.GL33;

public class BindTextureCommand implements DrawBuilderCommand {
    public int id;

    public BindTextureCommand(int id){
        this.id = id;
    }

    public void run(int GL_MODE, int i, float t){
        try{
            GL33.glBindTexture(GL33.GL_TEXTURE_2D, id);
        } catch (Exception e) {
            System.out.println("GL_ERROR BIND, ITER "+i+ " MODE "+ GL33.glGetString(GL_MODE) + " ID " + id);
        }
    }
}
