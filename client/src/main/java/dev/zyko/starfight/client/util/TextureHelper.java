package dev.zyko.starfight.client.util;

import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class TextureHelper {

    /**
     * Liest eine Textur-Datei in einen ByteBuffer ein.
     *
     * @param filePath die Datei, welche gelesen werden soll.
     * @return einen ByteBuffer, welcher die Daten aus der Datei enthält.
     * @throws Exception, falls die Datei nicht gelesen werden konnte.
     */
    public static ByteBuffer fileToBuffer(String filePath) throws Exception {
        ByteBuffer imageBuffer;
        int width, height;
        try (MemoryStack memoryStack = MemoryStack.stackPush()) {
            IntBuffer comp = memoryStack.mallocInt(1);
            IntBuffer w = memoryStack.mallocInt(1);
            IntBuffer h = memoryStack.mallocInt(1);
            imageBuffer = STBImage.stbi_load(filePath, w, h, comp, 4);
            width = w.get();
            height = h.get();
        }
        return imageBuffer;
    }

}
