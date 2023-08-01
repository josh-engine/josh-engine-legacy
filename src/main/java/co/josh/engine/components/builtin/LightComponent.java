package co.josh.engine.components.builtin;


import co.josh.engine.Main;
import co.josh.engine.components.Component;
import co.josh.engine.objects.GameObject;
import co.josh.engine.render.drawbuilder.DrawBuilder;
import co.josh.engine.render.drawbuilder.commands.*;
import co.josh.engine.render.lights.Light;
import co.josh.engine.util.Transform;
import co.josh.engine.util.model.ModelReader;
import co.josh.engine.util.model.jmodel.JoshModel;
import co.josh.engine.util.render.Vertex3F;
import co.josh.engine.util.texture.TexturePreloader;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL13;

import java.util.ArrayList;

public class LightComponent implements Component {

    public Light light;

    public Vector3f position;

    public GameObject parent;


    public LightComponent(GameObject parent, Vector3f relativePosition, Vector4f diffuse, Vector4f specular){
        this.parent = parent;
        this.position = relativePosition;
        light = new Light();
        light.create(parent.getTransform().applyUnscaledTo(relativePosition), true, diffuse, specular);
    }

    public String getName() {
        return "LightComponent";
    }

    public void on3D() {
        Vector3f pos = new Vector3f(position.x, position.y, position.z);
        Vector3f ptemp = parent.getTransform().applyUnscaledTo(pos);
        light.vector3f = ptemp;
    }

    public GameObject getParent() {
        return null;
    }
}
