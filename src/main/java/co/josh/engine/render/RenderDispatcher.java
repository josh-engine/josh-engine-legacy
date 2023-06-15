package co.josh.engine.render;

import co.josh.engine.objects.o2d.GameObject;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.*;

import co.josh.engine.Main;

public class RenderDispatcher {

     float PI_OVER_180 = 0.0174532925199432957692369076849f;
     float _180_OVER_PI =  57.2957795130823208767981548141f;
     public float DEG_TO_RAD(float x) {
         return (x * PI_OVER_180);
     }

     public float RAD_TO_DEG(float x) {
         return x * _180_OVER_PI;
     }

     public boolean doPerspectiveDraw = true;

    public void render(long window){
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer so there's no trippy shit in the skybox

        //Init some render shid
        GL11.glMatrixMode(GL_PROJECTION);
        GL11.glLoadIdentity();

        //GL FLAGS! VERY IMPORTANT
        GL11.glDisable(GL_CULL_FACE); //This is generally a bad idea for performance. I have it on for debug reasons.
        GL11.glEnable(GL_DEPTH_TEST); //VERY IMPORTANT! Depth sorting for the camera is here.

        if (doPerspectiveDraw){
            /*
            This line is stolen from the *third* page of google, after using a time machine to go back to whenever GL11 was useful.
            I don't know what anything here does, and probably couldn't figure it out if my life depended on it.
            GL11 is entirely outdated and I promise I will update to GL30 soon and probably code a deferred renderer.
            Maybe.

            TLDR: DO NOT FUCK WITH GLFRUSTUM UNLESS YOU KNOW EXACTLY WHAT IT DOES AND HOW TO USE IT
            */
            glFrustum(-0.88f, 0.88f, -0.5f, 0.5f, 0.8f, 300.0f);
        }else{
            GL11.glOrtho(0, Main.currentWidth, 0, Main.currentHeight, 0, -1);
        }


        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        for (GameObject gameObject : Main.gameObjects){
            //TODO: depth sort for 2d
            gameObject.render();
        }

        glfwSwapBuffers(window); // update the screen with the newest frame (swapping the buffers)
    }
}
