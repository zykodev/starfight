package dev.zyko.starfight.client.util;

import org.lwjgl.BufferUtils;

import java.io.*;
import java.nio.ByteBuffer;

public class IOHelper {

    /**
     * Extrahiert eine Ressource aus der Spieldatei zu einer temporären Datei.
     *
     * @param assetName Name der zu extrahierenden Ressource.
     * @return ein File-Objekt, welches auf die temporäre Datei zeigt.
     * @throws Exception, falls die Datei nicht extrahiert werden konnte.
     */
    public static File extractAsset(String assetName) throws Exception {
        InputStream inputStream = ClassLoader.getSystemResourceAsStream("assets/" + assetName);
        if (inputStream != null) {
            File assetFile = File.createTempFile("asset-", ".sfasset");
            FileOutputStream fileOutputStream = new FileOutputStream(assetFile);
            byte[] buffer = new byte[inputStream.available()];
            int read = 0;
            while ((read = inputStream.read(buffer, 0, buffer.length)) != -1) {
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

    /**
     * Liest eine File als String ein.
     *
     * @param f die File, welche gelesen werden soll.
     * @return den Inhalt der Datei als String.
     * @throws Exception, falls die Datei nicht gelesen werden konnte.
     */
    public static String readFile(File f) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(f));
        String readLine = "", totalText = "";
        while ((readLine = bufferedReader.readLine()) != null) {
            totalText += readLine;
        }
        bufferedReader.close();
        return readLine;
    }

    /**
     * Schreibt einen String in eine Datei. Existiert die Datei bereits, so wird sie überschrieben.
     *
     * @param f    die Datei, in welche geschrieben werden soll.
     * @param text der Text, welcher in die Datei geschrieben werden soll.
     * @throws Exception, falls nicht in die Datei geschrieben werden konnte.
     */
    public static void writeFile(File f, String text) throws Exception {
        FileWriter fw = new FileWriter(f);
        if (f.exists()) {
            f.delete();
        }
        fw.write(text);
        fw.flush();
        fw.close();
    }

    /**
     * Liest einen InputStream in einen ByteBuffer ein.
     *
     * @param inputStream der InputStream, welcher gelesen werden soll.
     * @return einen ByteBuffer, welcher die Inhalte des InputStreams enthält.
     * @throws Exception, falls der Stream nicht gelesen werden konnte.
     */
    public static ByteBuffer streamToByteBuffer(InputStream inputStream) throws Exception {
        ByteBuffer buffer = BufferUtils.createByteBuffer(inputStream.available());
        byte[] bBuf = new byte[inputStream.available()];
        int read = 0;
        while ((read = inputStream.read(bBuf, 0, bBuf.length)) != -1) {
            buffer.put(bBuf);
        }
        buffer.flip();
        return buffer;
    }

}
