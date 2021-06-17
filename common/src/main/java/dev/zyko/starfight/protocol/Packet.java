package dev.zyko.starfight.protocol;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

public interface Packet {

    void write(ByteBuf byteBuf) throws IOException;
    void read(ByteBuf byteBuf) throws IOException;

}
