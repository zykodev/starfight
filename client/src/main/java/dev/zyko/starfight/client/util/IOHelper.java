package dev.zyko.starfight.client.util;

import org.lwjgl.BufferUtils;

import java.io.*;
import java.nio.ByteBuffer;

public class IOHelper {

    public static File extractAsset(String assetName) throws Exception {
        InputStream inputStream = ClassLoader.getSystemResourceAsStream("assets/" + assetName);
        if(inputStream != null) {
            File assetFile = File.createTempFile("asset-", ".sfasset");
            FileOutputStream fileOutputStream = new FileOutputStream(assetFile);
            byte[] buffer = new byte[inputStream.available()];
            int read = 0;
            while((read = inputStream.read(buffer, 0, buffer.length)) != -1) {
                fileOutputStream.write(buffer);
            }
            inputStream.close();
            fileOutputStream.flush();
            fileOutputStream.close();
            assetFile.deleteOnExit();
            return assetFile;
        }
        return null;
    }

    public static String readFile(File f) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(f));
        String readLine = "", totalText = "";
        while((readLine = bufferedReader.readLine()) != null) {
            totalText += readLine;
        }
        bufferedReader.close();
        return readLine;
    }

    public static void writeFile(File f, String text) throws Exception {
        FileWriter fw = new FileWriter(f);
        if(f.exists()) {
            f.delete();
        }
        fw.write(text);
        fw.flush();
        fw.close();
    }

    public static ByteBuffer streamToByteBuffer(InputStream inputStream) throws Exception {
        ByteBuffer buffer = BufferUtils.createByteBuffer(inputStream.available());
        byte[] bBuf = new byte[inputStream.available()];
        int read = 0;
        while((read = inputStream.read(bBuf, 0, bBuf.length)) != -1) {
            buffer.put(bBuf);
        }
        buffer.flip();
        return buffer;
    }

}
