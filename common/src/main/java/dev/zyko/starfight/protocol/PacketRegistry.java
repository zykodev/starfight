package dev.zyko.starfight.protocol;

import dev.zyko.starfight.protocol.impl.*;

import java.util.Arrays;
import java.util.List;

public class PacketRegistry {

    public static final List<Class<? extends Packet>> CLIENT_PACKETS = Arrays.asList(C01PacketKeepAlive.class, C02PacketDisconnect.class, C03PacketConnect.class, C04PacketPlayOutPlayerData.class);
    public static final List<Class<? extends Packet>> SERVER_PACKETS = Arrays.asList(S01PacketKeepAlive.class, S02PacketDisconnect.class, S03PacketAcceptConnection.class, S04PacketPlayOutEntitySpawn.class);

}
