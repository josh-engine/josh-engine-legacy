package co.josh.engine.util.texture;
import co.josh.engine.util.exceptions.TextureLoadFailure;

import org.lwjgl.opengl.*;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.List;
import java.util.Objects;

public class TextureLoader {

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
        int textureID = GL13.glGenTextures();
        GL13.glBindTexture(GL13.GL_TEXTURE_2D, textureID);

        // Set texture parameters
        GL13.glTexParameteri(GL13.GL_TEXTURE_2D, GL13.GL_TEXTURE_WRAP_S, GL13.GL_CLAMP_TO_EDGE);
        GL13.glTexParameteri(GL13.GL_TEXTURE_2D, GL13.GL_TEXTURE_WRAP_T, GL13.GL_CLAMP_TO_EDGE);
        GL13.glTexParameteri(GL13.GL_TEXTURE_2D, GL13.GL_TEXTURE_WRAP_R, GL13.GL_CLAMP_TO_EDGE);
        GL13.glTexParameteri(GL13.GL_TEXTURE_2D, GL13.GL_TEXTURE_MIN_FILTER, GL13.GL_NEAREST);
        GL13.glTexParameteri(GL13.GL_TEXTURE_2D, GL13.GL_TEXTURE_MAG_FILTER, GL13.GL_NEAREST);

        // Upload texture data
        GL13.glTexImage2D(GL13.GL_TEXTURE_2D, 0, GL13.GL_RGBA, tWidth, tHeight, 0, GL13.GL_RGBA, GL13.GL_UNSIGNED_BYTE, buf);

        // Free image data
        STBImage.stbi_image_free(Objects.requireNonNull(buf));

        return textureID;
    }


}
