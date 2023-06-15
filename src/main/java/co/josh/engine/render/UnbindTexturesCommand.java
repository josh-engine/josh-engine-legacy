package co.josh.engine.render;

import org.lwjgl.opengl.GL11;

public class UnbindTexturesCommand implements DrawBuilderCommand {

    public void run(int GL_MODE, int i){
        try{
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        } catch (Exception e) {
            System.out.println("GL_ERROR UNBIND, ITER "+i+ " MODE "+ GL11.glGetString(GL_MODE));
        }
    }
}
