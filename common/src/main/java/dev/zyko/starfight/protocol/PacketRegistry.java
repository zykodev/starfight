package dev.zyko.starfight.protocol;

import com.esotericsoftware.kryo.Kryo;
import dev.zyko.starfight.data.ScoreboardEntry;
import dev.zyko.starfight.protocol.impl.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PacketRegistry {

    public static final List<Class<? extends Packet>> CLIENT_PACKETS = Arrays.asList(C01PacketKeepAlive.class, C02PacketDisconnect.class, C03PacketConnect.class, C04PacketPlayOutPlayerData.class);
    public static final List<Class<? extends Packet>> SERVER_PACKETS = Arrays.asList(S01PacketKeepAlive.class, S02PacketDisconnect.class, S03PacketAcceptConnection.class, S04PacketPlayOutEntitySpawn.class, S05PacketPlayOutEntityPosition.class, S06PacketPlayOutEntityDespawn.class, S07PacketPlayOutEntityHealth.class, S08PacketPlayOutScoreboardData.class, S09PacketPlayOutEffectSpawn.class);

    public static void apply(Kryo kryo) {
        CLIENT_PACKETS.forEach(kryo::register);
        SERVER_PACKETS.forEach(kryo::register);
        kryo.register(ArrayList.class);
        kryo.register(ScoreboardEntry.class);
    }

}
