package co.josh.engine.render.drawbuilder.commands;

import co.josh.engine.render.joshshade.ShadersObject;
import org.lwjgl.opengl.GL12;

public class GlEnableCommand implements DrawBuilderCommand {
    public int id;

    public GlEnableCommand(int id){
        this.id = id;
    }

    public void run(int GL_MODE, int i, ShadersObject shaders, float t){
        try{
            GL12.glEnable(id);
        } catch (Exception e) {
            System.out.println("GL_ERROR ENABLE, ITER "+i+ " MODE "+ GL12.glGetString(GL_MODE) + " FLAG " + id);
        }
    }
}
