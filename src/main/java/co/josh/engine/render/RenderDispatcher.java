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
import co.josh.engine.render.lights.Light;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL12;

public class RenderDispatcher {

     float fov = 67f;

     public RenderDispatcher(){
         updateFrustrum(0.1f, 300f);
     }
     /*
     There once was a dark time when this was useful. Luckily, no more.
     I'm keeping it here just in case I feel the need to mess with glFrustum again.
    */
     float PI_OVER_180 = 0.0174532925199432957692369076849f;
     /*
     float _180_OVER_PI =  57.2957795130823208767981548141f;
     public float DEG_TO_RAD(float x) {
         return (x * PI_OVER_180);
     }

     public float RAD_TO_DEG(float x) {
         return x * _180_OVER_PI;
     }
     */


    public boolean doPerspectiveDraw = true;

    public void updateFrustrum(float near, float far){
        float h = (float) (Math.tan(fov * PI_OVER_180 * .5f) * near);
        float w = h * Main.currentWidth/Main.currentHeight;
        l = -w;
        r = w;
        b = -h;
        t = h;
        n = near;
        f = far;
    }

    public float l, r, b, t, n, f;

    public void render(long window){
        //clear framebuffer. on enclosed maps this may not be needed.
        GL12.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        GL12.glMatrixMode(GL_PROJECTION); //Setting up camera
        GL12.glLoadIdentity();

        GL12.glEnable(GL_CULL_FACE); //On by default for performance
        GL12.glEnable(GL_DEPTH_TEST);

        GL12.glCullFace(GL12.GL_BACK);

        if (doPerspectiveDraw) {
            GL12.glFrustum(l, r, b, t, n, f);

            GL12.glMatrixMode(GL_MODELVIEW); //Setting up render
            GL12.glEnable(GL_TEXTURE_2D);

            Vector3f pos = Main.camera.transform.position;

            Vector3f rot = Main.camera.transform.rotation;

            //Transform
            GL12.glRotatef(rot.x, 1, 0, 0);
            GL12.glRotatef(rot.y, 0, 1, 0);
            GL12.glRotatef(rot.z, 0, 0, 1);
            GL12.glTranslatef(-1*pos.x, -1*pos.y, -1*pos.z);

            //Gameobject render
            for (GameObject gameObject : Main.gameObjects){
                gameObject.render3d();
            }

            //Light update
            for (Light light : Main.lights){
                light.update();
            }

            GL12.glLoadIdentity();
        }

        GL12.glDisable(GL_TEXTURE_2D);
        GL12.glDisable(GL_CULL_FACE);

        GL12.glMatrixMode(GL_PROJECTION); //Setting up camera
        GL12.glLoadIdentity();

        GL12.glOrtho(0, Main.width, 0, Main.height, 0, -1);

        GL12.glMatrixMode(GL_MODELVIEW); //Setting up render
        GL12.glEnable(GL_TEXTURE_2D);

        for (GameObject gameObject : Main.gameObjects){
            gameObject.render2d();
        }

        GL12.glLoadIdentity();

        glfwSwapBuffers(window); // update the screen with the newest frame (swapping the buffers)
    }
}
