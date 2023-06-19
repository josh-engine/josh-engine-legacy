package co.josh.engine.render.drawbuilder.commands;

import co.josh.engine.render.joshshade.ShadersObject;
import org.lwjgl.opengl.GL12;

public class BindTextureCommand implements DrawBuilderCommand {
    public int id;

    public BindTextureCommand(int id){
        this.id = id;
    }

    public void run(int GL_MODE, int i, ShadersObject shaders, float t){
        try{
            GL12.glBindTexture(GL12.GL_TEXTURE_2D, id);
        } catch (Exception e) {
            System.out.println("GL_ERROR BIND, ITER "+i+ " MODE "+ GL12.glGetString(GL_MODE) + " ID " + id);
        }
    }
}
