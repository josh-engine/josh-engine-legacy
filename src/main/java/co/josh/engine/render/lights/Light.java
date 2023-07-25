package co.josh.engine.render.lights;

import co.josh.engine.Main;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class Light {

    public Vector3f vector3f;

    public int id;

    public boolean point;

    public Vector4f diffuse;

    public Vector4f specular;

    public Light(){

    }

    public boolean create(Vector3f vector3f, boolean isPointLight, Vector4f diffuse, Vector4f specular){
        /*Creates light if possible.*/
        if (Main.lights.size() < GL11.glGetInteger(GL11.GL_MAX_LIGHTS)){
            this.vector3f = vector3f;
            this.point = isPointLight;
            this.diffuse = diffuse;
            this.specular = specular;
            this.id = GL11.GL_LIGHT0 + Main.lights.size();
            Main.lights.add(this);
            return true;
        }else{
            System.out.println("Warning: Could not create light at "
                    + vector3f.toString() +
                    " because lights is already at max size for platform (" + GL11.glGetInteger(GL11.GL_MAX_LIGHTS) + ")!");
            return false;
        }
    }

    public void update(){
        float[] _diffuse = { diffuse.x, diffuse.y, diffuse.z, diffuse.w };
        float[] _specular = { specular.x, specular.y, specular.z, specular.w };
        float[] light = {vector3f.x, vector3f.y, vector3f.z, point ? 1f : 0f};

        GL11.glLightfv(this.id, GL11.GL_POSITION, light);
        GL12.glLightfv(this.id, GL12.GL_DIFFUSE, _diffuse);
        GL12.glLightfv(this.id, GL12.GL_SPECULAR, _specular);
    }
}
