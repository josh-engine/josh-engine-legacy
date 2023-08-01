package co.josh.engine.render.lights;

import co.josh.engine.Main;
import co.josh.engine.render.drawbuilder.DrawBuilder;
import co.josh.engine.render.drawbuilder.commands.DrawBuilderCommand;
import co.josh.engine.util.Transform;
import co.josh.engine.util.model.ModelReader;
import co.josh.engine.util.model.jmodel.JoshModel;
import co.josh.engine.util.texture.TexturePreloader;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL13;

import java.util.ArrayList;

public class Light {

    public Vector3f vector3f;

    public int id;

    public boolean point;

    public Vector4f diffuse;

    public Vector4f specular;

    public JoshModel model;

    public DrawBuilder db;

    public Light(){

    }

    public boolean create(Vector3f vector3f, boolean isPointLight, Vector4f diffuse, Vector4f specular){
        /*Creates light if possible.*/
        if (Main.lights.size() < GL13.glGetInteger(GL13.GL_MAX_LIGHTS)){
            this.vector3f = vector3f;
            this.point = isPointLight;
            this.diffuse = diffuse;
            this.specular = specular;
            this.id = GL13.GL_LIGHT0 + Main.lights.size();
            this.db = new DrawBuilder(Main.camera, GL13.GL_QUADS);
            this.model = ModelReader.loadJoshFormat(Main.dir + Main.gameFolder + "/models/quad.josh", false);
            this.model.textureId = point ? TexturePreloader.textures.get("LightIcon_Point") : TexturePreloader.textures.get("LightIcon_Directional");
            Main.lights.add(this);
            return true;
        }else{
            System.out.println("Warning: Could not create light at "
                    + vector3f.toString() +
                    " because lights is already at max size for platform (" + GL13.glGetInteger(GL13.GL_MAX_LIGHTS) + ")!");
            return false;
        }
    }

    public void update(){
        float[] _diffuse = { diffuse.x, diffuse.y, diffuse.z, diffuse.w };
        float[] _specular = { specular.x, specular.y, specular.z, specular.w };
        float[] light = {vector3f.x, vector3f.y, vector3f.z, point ? 1f : 0f};

        GL13.glLightfv(this.id, GL13.GL_POSITION, light);
        GL13.glLightfv(this.id, GL13.GL_DIFFUSE, _diffuse);
        GL13.glLightfv(this.id, GL13.GL_SPECULAR, _specular);
        GL13.glLightf(this.id, GL13.GL_LINEAR_ATTENUATION, 1f);

        if (!Main.runEvents)
        {
            float avg = (diffuse.x + diffuse.y + diffuse.z)/3;
            Transform t = new Transform(vector3f, new Vector3f(), new Vector3f(1f));
            ArrayList<DrawBuilderCommand> dbcs = model.drawBuilderCommands(t, t, diffuse);
            db.render(dbcs, 0f);
        }
    }
}
