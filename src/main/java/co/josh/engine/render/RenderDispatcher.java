package co.josh.engine.render;

import co.josh.engine.objects.GameObject;

import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL12.GL_CULL_FACE;
import static org.lwjgl.opengl.GL12.GL_PROJECTION;
import static org.lwjgl.opengl.GL12.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL12.GL_MODELVIEW;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL12.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL12.GL_DEPTH_BUFFER_BIT;

import co.josh.engine.Main;
import org.lwjgl.opengl.GL12;

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


     public boolean doPerspectiveDraw = true;

    public void render(long window){
        //clear framebuffer. on enclosed maps this may not be needed.
        GL12.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        GL12.glMatrixMode(GL_PROJECTION); //Setting up camera
        GL12.glLoadIdentity();

        GL12.glEnable(GL_CULL_FACE); //On by default for performance
        GL12.glEnable(GL_DEPTH_TEST);

        if (doPerspectiveDraw){
            /*
            This line is stolen from the *third* page of google, after using a time machine to go back to whenever GL12 was useful.
            I don't know what anything here does, and probably couldn't figure it out if my life depended on it.
            GL12 is entirely outdated and I promise I will update to GL30 soon and probably code a deferred renderer.
            Maybe.

            TLDR: DO NOT FUCK WITH GLFRUSTUM UNLESS YOU KNOW EXACTLY WHAT IT DOES AND HOW TO USE IT
            */
            GL12.glFrustum(-0.88f, 0.88f, -0.5f, 0.5f, 0.8f, 300.0f);
        }else{
            GL12.glOrtho(0, Main.currentWidth, 0, Main.currentHeight, 0, -1);
        }


        GL12.glMatrixMode(GL_MODELVIEW); //Setting up render
        GL12.glEnable(GL_TEXTURE_2D);

        for (GameObject gameObject : Main.gameObjects){
            gameObject.render();
        }

        glfwSwapBuffers(window); // update the screen with the newest frame (swapping the buffers)
    }
}
