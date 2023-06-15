package co.josh.engine.util.texture;
import co.josh.engine.util.exceptions.TextureLoadFailure;

import org.lwjgl.opengl.*;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class TextureLoader {
    //Thanks ChatGPT
    public static int loadTexture(String path) {
        ByteBuffer buf = null;
        int tWidth = 0;
        int tHeight = 0;

        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer comp = stack.mallocInt(1);

            // Load image data
            STBImage.stbi_set_flip_vertically_on_load(true);
            buf = STBImage.stbi_load(path, w, h, comp, 4);
            if (buf == null) {
                throw new TextureLoadFailure(System.lineSeparator() + STBImage.stbi_failure_reason());
            }

            // Get image width and height
            tWidth = w.get();
            tHeight = h.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Generate texture ID
        int textureID = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);

        // Set texture parameters
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);

        // Upload texture data
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, tWidth, tHeight, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buf);

        // Free image data
        STBImage.stbi_image_free(buf);

        return textureID;
    }

}
