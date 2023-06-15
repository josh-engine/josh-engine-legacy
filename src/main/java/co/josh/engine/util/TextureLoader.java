package co.josh.engine.util;
import org.lwjgl.BufferUtils;

import org.lwjgl.opengl.*;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL30.glGenerateMipmap;

public class TextureLoader {
    //Thanks ChatGPT
    public static int loadTexture(String filename) {
        int textureID = GL12.glGenTextures();

        try {
            FileInputStream fileInputStream = new FileInputStream(filename);
            byte[] imageData = fileInputStream.readAllBytes();
            ByteBuffer imageBuffer = ByteBuffer.allocateDirect(imageData.length);
            imageBuffer.put(imageData).flip();

            IntBuffer width = ByteBuffer.allocateDirect(4).asIntBuffer();
            IntBuffer height = ByteBuffer.allocateDirect(4).asIntBuffer();
            IntBuffer channels = ByteBuffer.allocateDirect(4).asIntBuffer();
            int[] channelsArr = new int[1];

            ByteBuffer image = STBImage.stbi_load_from_memory(imageBuffer, width, height, channels, 0);
            if (image != null) {
                GL12.glBindTexture(GL12.GL_TEXTURE_2D, textureID);
                GL12.glTexParameteri(GL12.GL_TEXTURE_2D, GL12.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
                GL12.glTexParameteri(GL12.GL_TEXTURE_2D, GL12.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
                GL12.glTexParameteri(GL12.GL_TEXTURE_2D, GL12.GL_TEXTURE_MIN_FILTER, GL12.GL_LINEAR);
                GL12.glTexParameteri(GL12.GL_TEXTURE_2D, GL12.GL_TEXTURE_MAG_FILTER, GL12.GL_LINEAR);
                
                int widthValue = width.get(0);
                int heightValue = height.get(0);
                int channelsValue = channelsArr[0];

                if (channelsValue == 3) {
                    GL12.glTexImage2D(GL12.GL_TEXTURE_2D, 0, GL12.GL_RGB, widthValue, heightValue, 0, GL12.GL_RGB, GL12.GL_UNSIGNED_BYTE, image);
                } else if (channelsValue == 4) {
                    GL12.glTexImage2D(GL12.GL_TEXTURE_2D, 0, GL12.GL_RGBA, widthValue, heightValue, 0, GL12.GL_RGBA, GL12.GL_UNSIGNED_BYTE, image);
                } else {
                    throw new IOException("Unsupported image format");
                }

                GL12.glBindTexture(GL12.GL_TEXTURE_2D, 0); // Unbind texture
                STBImage.stbi_image_free(image);
            }

            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return textureID;
    }

}
