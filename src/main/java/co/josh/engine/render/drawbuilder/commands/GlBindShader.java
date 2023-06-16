package co.josh.engine.render.drawbuilder.commands;

import co.josh.engine.render.drawbuilder.ShaderProgram;
import org.lwjgl.opengl.GL33;

public class GlBindShader implements DrawBuilderCommand {
    int id;
    int vertexShaderId;
    int fragmentShaderId;

    public String dumpInfo;

    public GlBindShader(int id){
        this.id = id;
        dumpInfo = "PROGRAM ID: " + id;
    }

    /*
     * ShaderProgram input is recommended for development for debug reasons,
     * however switching to just the ID in release is preferred because it
     * takes fewer resources
     */
    public GlBindShader(ShaderProgram shader){
        this.id = shader.programId;
        this.vertexShaderId = shader.vertexShaderId;
        this.fragmentShaderId = shader.fragmentShaderId;
        dumpInfo = " PROGRAM ID: " + id + " VERTEX SHADER ID: " + vertexShaderId + " FRAGMENT SHADER ID: " + fragmentShaderId;
    }
    public void run(int GL_MODE, int i, float t){
        try{
            GL33.glUseProgram(id);
        } catch (Exception e) {
            System.out.println("GL_ERROR SHADER_BIND, ITER " + i
                    + " MODE "+ GL33.glGetString(GL_MODE)
                    + dumpInfo);
        }
    }
}
