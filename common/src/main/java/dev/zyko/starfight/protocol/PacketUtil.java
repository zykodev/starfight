package dev.zyko.starfight.protocol;

import io.netty.buffer.ByteBuf;

import java.nio.charset.StandardCharsets;

public class PacketUtil {

    public static void writeString(ByteBuf byteBuf, String data) {
        byteBuf.writeInt(data.length());
        byteBuf.writeBytes(data.getBytes(StandardCharsets.UTF_8));
    }

    public static String readString(ByteBuf byteBuf) {
        int length = byteBuf.readInt();
        return new String(byteBuf.readBytes(length).array(), StandardCharsets.UTF_8);
    }

}
