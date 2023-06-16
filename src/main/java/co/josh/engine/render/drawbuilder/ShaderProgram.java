package co.josh.engine.render.drawbuilder;

import co.josh.engine.Main;
import co.josh.engine.util.exceptions.ShaderFailure;
import org.lwjgl.opengl.GL33;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ShaderProgram {
    public final int programId;
    public int vertexShaderId;
    public int fragmentShaderId;
    public ShaderProgram() {
        programId = GL33.glCreateProgram();
        if (programId == 0) {
            throw new ShaderFailure("Could not create Shader");
        } else {
            Main.shaderPrograms.add(programId);
        }
    }

    public void createVertexShader(String shaderCode) {
        try{
        vertexShaderId = createShader(shaderCode, GL33.GL_VERTEX_SHADER);
        } catch (Exception e){
            throw new ShaderFailure("Failed to create shader with code " + shaderCode + ". " + e.toString());
        }
    }
    public void createFragmentShader(String shaderCode) {
        try{
            fragmentShaderId = createShader(shaderCode, GL33.GL_FRAGMENT_SHADER);
        } catch (Exception e){
            throw new ShaderFailure("Failed to create shader with code " + shaderCode + "." + e.toString());
        }
    }
    protected int createShader(String shaderCode, int shaderType) {
        int shaderId = GL33.glCreateShader(shaderType);
        if (shaderId == 0) {
            throw new ShaderFailure("Error creating shader. Type: " + shaderType);
        }
        GL33.glShaderSource(shaderId, shaderCode);
        GL33.glCompileShader(shaderId);
        if (GL33.glGetShaderi(shaderId, GL33.GL_COMPILE_STATUS) == 0) {
            throw new ShaderFailure("Error compiling Shader code: " + GL33.glGetShaderInfoLog(shaderId, 1024));
        }
        GL33.glAttachShader(programId, shaderId);
        return shaderId;
    }
    public void link() {
        GL33.glLinkProgram(programId);
        if (GL33.glGetProgrami(programId, GL33.GL_LINK_STATUS) == 0) {
            throw new ShaderFailure("Error linking Shader code: " + GL33.glGetProgramInfoLog(programId, 1024));
        }
        if (vertexShaderId != 0) {
            GL33.glDetachShader(programId, vertexShaderId);
        }
        if (fragmentShaderId != 0) {
            GL33.glDetachShader(programId, fragmentShaderId);
        }
        GL33.glValidateProgram(programId);
        if (GL33.glGetProgrami(programId, GL33.GL_VALIDATE_STATUS) == 0) {
            System.err.println("Warning validating Shader code: " + GL33.glGetProgramInfoLog(programId, 1024));
        }
    }
    public void bind() {
        GL33.glUseProgram(programId);
    }

    public void createVertexFromFile(String fileLocation){
        try{
        String text = Files.readString(Paths.get(Main.dir+fileLocation));
        createVertexShader(text);
        } catch (IOException e){
            throw new ShaderFailure(e.toString());
        }
    }

    public void createFragmentFromFile(String fileLocation){
        try{
            String text = Files.readString(Paths.get(Main.dir+fileLocation));
            createFragmentShader(text);
        } catch (IOException e){
            throw new ShaderFailure(e.toString());
        }
    }

    public void unbind() {
        GL33.glUseProgram(0);
    }
    public void cleanup() {
        unbind();
        if (programId != 0) {
            GL33.glDeleteProgram(programId);
        }
    }
}
