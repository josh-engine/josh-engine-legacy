package co.josh.engine.render.drawbuilder.commands;

import co.josh.engine.render.joshshade.ShadersObject;
import co.josh.engine.util.model.obj.mtl.Material;
import org.lwjgl.opengl.GL13;

public class ResetMaterialCommand implements DrawBuilderCommand {

    public void run(int GL_MODE, int i, ShadersObject shaders, float t){
        try{
            GL13.glMaterialfv( GL13.GL_FRONT_AND_BACK, GL13.GL_AMBIENT,
                    new float[]{1f, 1f, 1f, 1f});
            GL13.glMaterialfv( GL13.GL_FRONT_AND_BACK, GL13.GL_DIFFUSE,
                    new float[]{1f, 1f, 1f, 1f});
            GL13.glMaterialfv( GL13.GL_FRONT_AND_BACK, GL13.GL_SPECULAR,
                    new float[]{0f, 0f, 0f, 1f});
            GL13.glMaterialf(GL13.GL_FRONT_AND_BACK, GL13.GL_SHININESS, 0f);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("GL_ERROR MATERIAL, ITER "+i+ " MODE "+ GL13.glGetString(GL_MODE));
        }
    }
}
