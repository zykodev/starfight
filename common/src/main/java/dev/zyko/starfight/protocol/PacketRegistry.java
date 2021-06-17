package dev.zyko.starfight.protocol;

import dev.zyko.starfight.protocol.impl.C01PacketKeepAlive;
import dev.zyko.starfight.protocol.impl.S01PacketKeepAlive;

import java.util.Arrays;
import java.util.List;

public class PacketRegistry {

    public static final List<Class<? extends Packet>> CLIENT_PACKETS = Arrays.asList(C01PacketKeepAlive.class);
    public static final List<Class<? extends Packet>> SERVER_PACKETS = Arrays.asList(S01PacketKeepAlive.class);

}
