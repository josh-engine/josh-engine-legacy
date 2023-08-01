package co.josh.engine.render;

import co.josh.engine.components.Component;
import co.josh.engine.objects.GameObject;

import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL13.GL_CULL_FACE;
import static org.lwjgl.opengl.GL13.GL_PROJECTION;
import static org.lwjgl.opengl.GL13.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL13.GL_MODELVIEW;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL13.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL13.GL_DEPTH_BUFFER_BIT;

import co.josh.engine.Main;
import co.josh.engine.render.lights.Light;
import co.josh.engine.util.texture.TexturePreloader;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL13;

public class RenderDispatcher {

    public boolean skyboxEnabled = true;

    float fov = 67f;

    public RenderDispatcher(){
         updateFrustum(0.1f, 300f);
    }

    float PI_OVER_180 = 0.0174532925199432957692369076849f;

    public boolean doPerspectiveDraw = true;

    public void updateFrustum(float near, float far){
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
        //Only clear color if skybox is disabled
        GL13.glClear(skyboxEnabled ? GL_DEPTH_BUFFER_BIT : GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        GL13.glMatrixMode(GL_PROJECTION); //Setting up camera
        GL13.glLoadIdentity();

        GL13.glEnable(GL_CULL_FACE); //On by default for performance
        GL13.glEnable(GL_DEPTH_TEST);

        GL13.glEnable(GL13.GL_BLEND);
        GL13.glBlendFunc(GL13.GL_SRC_ALPHA, GL13.GL_ONE_MINUS_SRC_ALPHA);

        GL13.glCullFace(GL13.GL_BACK);

        if (doPerspectiveDraw) {
            GL13.glFrustum(l, r, b, t, n, f);

            GL13.glMatrixMode(GL_MODELVIEW); //Setting up render
            GL13.glEnable(GL_TEXTURE_2D);

            Vector3f pos = Main.camera.transform.position;

            Vector3f rot = Main.camera.transform.rotation;

            //Transform
            GL13.glRotatef(rot.x, 1, 0, 0);
            GL13.glRotatef(rot.y, 0, 1, 0);
            GL13.glRotatef(rot.z, 0, 0, 1);

            if (skyboxEnabled) {
                drawCubeMap();
            }

            GL13.glActiveTexture(GL13.GL_TEXTURE0);

            GL13.glTranslatef(-1*pos.x, -1*pos.y, -1*pos.z);

            //Gameobject render
            for (GameObject gameObject : Main.gameObjects){
                gameObject.render3d();
                gameObject.getComponents().forEach(Component::on3D);
            }

            //LightComponent update
            for (Light light : Main.lights){
                light.update();
            }

            GL13.glLoadIdentity();
        }

        GL13.glDisable(GL_TEXTURE_2D);
        GL13.glDisable(GL_CULL_FACE);

        GL13.glMatrixMode(GL_PROJECTION); //Setting up camera
        GL13.glLoadIdentity();

        GL13.glOrtho(0, Main.width, 0, Main.height, 0, -1);

        GL13.glMatrixMode(GL_MODELVIEW); //Setting up render
        GL13.glEnable(GL_TEXTURE_2D);

        for (GameObject gameObject : Main.gameObjects){
            gameObject.render2d();
            gameObject.getComponents().forEach(Component::on2D);
        }

        GL13.glLoadIdentity();

        glfwSwapBuffers(window); // update the screen with the newest frame (swapping the buffers)
    }
    
    void drawCubeMap(){
        // Disable depth writing to ensure the skybox is drawn behind the scene
        GL13.glDepthMask(false);
        GL13.glDisable(GL13.GL_LIGHTING);
        GL13.glBindTexture(GL_TEXTURE_2D, 0);
        // Front face
        GL13.glBindTexture(GL13.GL_TEXTURE_2D, TexturePreloader.skyboxTextures.get("front"));
        GL13.glBegin(GL13.GL_QUADS);
        GL13.glTexCoord2f(0, 0); GL13.glVertex3f(-1, -1, -1);
        GL13.glTexCoord2f(1, 0); GL13.glVertex3f(1, -1, -1);
        GL13.glTexCoord2f(1, 1); GL13.glVertex3f(1, 1, -1);
        GL13.glTexCoord2f(0, 1); GL13.glVertex3f(-1, 1, -1);
        GL13.glEnd();
        GL13.glBindTexture(GL_TEXTURE_2D, 0);
// Back face
        GL13.glBindTexture(GL13.GL_TEXTURE_2D, TexturePreloader.skyboxTextures.get("back"));
        GL13.glBegin(GL13.GL_QUADS);
        GL13.glTexCoord2f(1, 1); GL13.glVertex3f(-1, 1, 1);
        GL13.glTexCoord2f(0, 1); GL13.glVertex3f(1, 1, 1);
        GL13.glTexCoord2f(0, 0); GL13.glVertex3f(1, -1, 1);
        GL13.glTexCoord2f(1, 0); GL13.glVertex3f(-1, -1, 1);
        GL13.glEnd();
        GL13.glBindTexture(GL_TEXTURE_2D, 0);
// Top face
        GL13.glBindTexture(GL13.GL_TEXTURE_2D, TexturePreloader.skyboxTextures.get("top"));
        GL13.glBegin(GL13.GL_QUADS);
        GL13.glTexCoord2f(0, 0); GL13.glVertex3f(-1, 1, -1);
        GL13.glTexCoord2f(1, 0); GL13.glVertex3f(1, 1, -1);
        GL13.glTexCoord2f(1, 1); GL13.glVertex3f(1, 1, 1);
        GL13.glTexCoord2f(0, 1); GL13.glVertex3f(-1, 1, 1);
        GL13.glEnd();
        GL13.glBindTexture(GL_TEXTURE_2D, 0);
// Bottom face
        GL13.glBindTexture(GL13.GL_TEXTURE_2D, TexturePreloader.skyboxTextures.get("bottom"));
        GL13.glBegin(GL13.GL_QUADS);
        GL13.glTexCoord2f(0, 0); GL13.glVertex3f(-1, -1, 1);
        GL13.glTexCoord2f(1, 0); GL13.glVertex3f(1, -1, 1);
        GL13.glTexCoord2f(1, 1); GL13.glVertex3f(1, -1, -1);
        GL13.glTexCoord2f(0, 1); GL13.glVertex3f(-1, -1, -1);
        GL13.glEnd();
        GL13.glBindTexture(GL_TEXTURE_2D, 0);
// Left face
        GL13.glBindTexture(GL13.GL_TEXTURE_2D, TexturePreloader.skyboxTextures.get("left"));
        GL13.glBegin(GL13.GL_QUADS);
        GL13.glTexCoord2f(0, 0); GL13.glVertex3f(-1, -1, 1);
        GL13.glTexCoord2f(1, 0); GL13.glVertex3f(-1, -1, -1);
        GL13.glTexCoord2f(1, 1); GL13.glVertex3f(-1, 1, -1);
        GL13.glTexCoord2f(0, 1); GL13.glVertex3f(-1, 1, 1);
        GL13.glEnd();
        GL13.glBindTexture(GL_TEXTURE_2D, 0);
// Right face
        GL13.glBindTexture(GL13.GL_TEXTURE_2D, TexturePreloader.skyboxTextures.get("right"));
        GL13.glBegin(GL13.GL_QUADS);
        GL13.glTexCoord2f(0, 0); GL13.glVertex3f(1, -1, -1);
        GL13.glTexCoord2f(1, 0); GL13.glVertex3f(1, -1, 1);
        GL13.glTexCoord2f(1, 1); GL13.glVertex3f(1, 1, 1);
        GL13.glTexCoord2f(0, 1); GL13.glVertex3f(1, 1, -1);
        GL13.glEnd();
        GL13.glBindTexture(GL_TEXTURE_2D, 0);
// Restore the previous OpenGL state
        GL13.glDepthMask(true);
        GL13.glEnable(GL13.GL_LIGHTING);
        GL13.glBindTexture(GL_TEXTURE_2D, 0);
    }
}
