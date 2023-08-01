package co.josh.engine.util.texture;

import java.nio.ByteBuffer;

public class ByteImage {
    public ByteBuffer data;

    public int w, h;

    public ByteImage(int w, int h, ByteBuffer data){
        this.w = w;
        this.h = h;
        this.data = data;
    }
}
