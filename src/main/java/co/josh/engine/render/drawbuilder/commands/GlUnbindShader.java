package co.josh.engine.render.drawbuilder.commands;

import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL33;

public class GlUnbindShader implements DrawBuilderCommand {
    public void run(int GL_MODE, int i, float t){
        try{
            GL33.glUseProgram(0);
        } catch (Exception e) {
            System.out.println("GL_ERROR SHADER_UNBIND, ITER "+i+ " MODE "+ GL33.glGetString(GL_MODE));
        }
    }
}
