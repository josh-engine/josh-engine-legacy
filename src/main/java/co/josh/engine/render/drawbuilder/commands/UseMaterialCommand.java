package co.josh.engine.render.drawbuilder.commands;

import co.josh.engine.render.joshshade.ShadersObject;
import co.josh.engine.util.model.obj.mtl.Material;
import org.lwjgl.opengl.GL13;

public class UseMaterialCommand implements DrawBuilderCommand {
    public Material mat;

    public UseMaterialCommand(Material mat){
        this.mat = mat;
    }

    public void run(int GL_MODE, int i, ShadersObject shaders, float t){

        try{
            GL13.glBindTexture(GL13.GL_TEXTURE_2D, 0);
            GL13.glBindTexture(GL13.GL_TEXTURE_2D, mat.textureID);
            GL13.glMaterialfv( GL13.GL_FRONT_AND_BACK, GL13.GL_AMBIENT,
                    new float[]{mat.ambient.x, mat.ambient.y, mat.ambient.z, 1f});
            GL13.glMaterialfv( GL13.GL_FRONT_AND_BACK, GL13.GL_DIFFUSE,
                    new float[]{mat.diffuse.x, mat.diffuse.y, mat.diffuse.z, 1f});
            GL13.glMaterialfv( GL13.GL_FRONT_AND_BACK, GL13.GL_SPECULAR,
                    new float[]{mat.specular.x, mat.specular.y, mat.specular.z, 1f});
            GL13.glMaterialf(  GL13.GL_FRONT_AND_BACK, GL13.GL_SHININESS, mat.shininess);
        } catch (Exception e) {
            System.out.println("GL_ERROR MATERIAL, ITER "+i+ " MODE "+ GL13.glGetString(GL_MODE));
        }
    }
}
