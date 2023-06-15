package co.josh.engine.render;
import org.lwjgl.BufferUtils;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL30.glGenerateMipmap;

public class TextureLoader {
    //Thanks ChatGPT
    public static int loadTexture(String filename) {
        // Load Texture file
        ByteBuffer buf = null;
        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);

        try {
            STBImage.stbi_set_flip_vertically_on_load(true);
            buf = STBImage.stbi_load(filename, width, height, channels, 4);
            if (buf == null) {
                throw new Exception("Image file [" + filename + "] not loaded: " + STBImage.stbi_failure_reason());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Generate texture ID
        IntBuffer textureID = BufferUtils.createIntBuffer(1);
        textureID.clear();
        GL11.glGenTextures(textureID);

        int id = textureID.get(0);
        if (id == 0) {
            try{
                throw new Exception("Failed to generate texture ID");
            } catch (Exception e){
                return -1;
            }
        }

        // Bind texture and set parameters
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
        GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);

        // Upload texture data
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width.get(0), height.get(0), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buf);

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0); //unbind
        // Clean up
        STBImage.stbi_image_free(buf);

        return textureID.get(0);
    }

}
