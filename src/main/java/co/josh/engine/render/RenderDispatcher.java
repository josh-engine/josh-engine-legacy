package co.josh.engine.render;

import co.josh.engine.GameObject;

import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;

import co.josh.engine.Main;
import co.josh.engine.render.drawbuilder.ShaderBuilder;
import co.josh.engine.render.drawbuilder.ShaderProgram;
import org.lwjgl.opengl.GL33;

public class RenderDispatcher {
     /*
     There once was a dark time when this was useful. Luckily, no more.
     I'm keeping it here just in case I feel the need to mess with glFrustum again.

     float PI_OVER_180 = 0.0174532925199432957692369076849f;
     float _180_OVER_PI =  57.2957795130823208767981548141f;
     public float DEG_TO_RAD(float x) {
         return (x * PI_OVER_180);
     }

     public float RAD_TO_DEG(float x) {
         return x * _180_OVER_PI;
     }
     */
    public ShaderProgram defaultShader;
    public ShaderBuilder mainShaderBuilder;

    public RenderDispatcher(){
        mainShaderBuilder = new ShaderBuilder(Main.camera);
        render(Main.window);
        mainShaderBuilder.loadVAOsVBOs(Main.tpsCount/20f);
        defaultShader = new ShaderProgram();
        defaultShader.createVertexFromFile("/josh/shaders/vertex.glsl");
        defaultShader.createFragmentFromFile("/josh/shaders/fragment.glsl");
        defaultShader.link();
    }


    public boolean doPerspectiveDraw = true;

    public void render(long window){
        //clear framebuffer. on enclosed maps this may not be needed.
        GL33.glClear(GL33.GL_COLOR_BUFFER_BIT | GL33.GL_DEPTH_BUFFER_BIT);

        /*
        GL33.glMatrixMode(GL33.GL_PROJECTION); //Setting up camera
        GL33.glLoadIdentity();

        GL33.glEnable(GL33.GL_CULL_FACE); //On by default for performance
        GL33.glEnable(GL33.GL_DEPTH_TEST);

        if (doPerspectiveDraw){
            /*
            This line is stolen from the *third* page of google, after using a time machine to go back to whenever GL12 was useful.
            I don't know what anything here does, and probably couldn't figure it out if my life depended on it.
            GL12 is entirely outdated and I promise I will update to GL33 soon and probably code a deferred renderer.
            Maybe.

            TLDR: DO NOT FUCK WITH GLFRUSTUM UNLESS YOU KNOW EXACTLY WHAT IT DOES AND HOW TO USE IT

            GL33.glFrustum(-0.88f, 0.88f, -0.5f, 0.5f, 0.8f, 300.0f);
        }else{
            GL33.glOrtho(0, Main.currentWidth, 0, Main.currentHeight, 0, -1);
        }


        GL33.glMatrixMode(GL33.GL_MODELVIEW); //Setting up render
        GL33.glEnable(GL33.GL_TEXTURE_2D);

*/
        for (GameObject gameObject : Main.gameObjects){
            gameObject.render();
        }
        mainShaderBuilder.render();
        glfwSwapBuffers(window); // update the screen with the newest frame (swapping the buffers)
    }
}
