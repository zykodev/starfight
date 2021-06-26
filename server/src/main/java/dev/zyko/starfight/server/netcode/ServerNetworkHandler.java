package dev.zyko.starfight.server.netcode;

import dev.zyko.starfight.logging.StarfightLogger;
import dev.zyko.starfight.protocol.Packet;
import dev.zyko.starfight.protocol.impl.*;
import dev.zyko.starfight.server.StarfightServer;
import dev.zyko.starfight.server.thread.ServerTickThread;
import dev.zyko.starfight.server.world.entity.Entity;
import dev.zyko.starfight.server.world.entity.EntityPlayerSpaceship;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerNetworkHandler extends SimpleChannelInboundHandler<Packet> {

    private Channel channel;
    private EntityPlayerSpaceship entityPlayerSpaceship;

    public ServerNetworkHandler(Channel channel) {
        this.channel = channel;
    }

    public Channel getChannel() {
        return channel;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
        StarfightServer.getInstance().getLogger().setLevel(StarfightLogger.Level.DEBUG).log(this.getClass(), "Client " + this.channel.remoteAddress().toString() + " sent " + msg.getClass().getSimpleName() + ".");
        if(msg instanceof C01PacketKeepAlive) {
            this.channel.writeAndFlush(new S01PacketKeepAlive(((C01PacketKeepAlive) msg).getSystemTime()), channel.voidPromise());
        }
        if(msg instanceof C02PacketDisconnect) {
            if(this.entityPlayerSpaceship != null) {
                StarfightServer.getInstance().getWorld().despawnEntity(this.entityPlayerSpaceship);
            }
        }
        if(msg instanceof C03PacketConnect) {
            C03PacketConnect packet = (C03PacketConnect) msg;
            if(packet.getVersion().equalsIgnoreCase(StarfightServer.VERSION)) {
                System.out.println(packet.getNickname());
                for(Entity e : StarfightServer.getInstance().getWorld().getEntityList()) {
                    if(e instanceof EntityPlayerSpaceship) {
                        if(((EntityPlayerSpaceship) e).getName().equalsIgnoreCase(packet.getNickname())) {
                            this.sendPacket(new S02PacketDisconnect("The name you entered is already in use. Please choose another name."));
                            this.channel.close().sync();
                            return;
                        }
                    }
                }
                if(packet.getNickname().equalsIgnoreCase("forbidden")) {
                    this.sendPacket(new S02PacketDisconnect("That name is forbidden."));
                    this.channel.close().sync();
                    return;
                }
                double[] entitySpawnPosition = StarfightServer.getInstance().getWorld().getRandomSpawnPosition();
                int entityId = StarfightServer.getInstance().getWorld().getNextEntityID();
                EntityPlayerSpaceship entitySpaceship = new EntityPlayerSpaceship(entityId, entitySpawnPosition[0], entitySpawnPosition[1], 0, packet.getNickname(), this);
                StarfightServer.getInstance().getWorld().spawnEntity(entitySpaceship);
                this.entityPlayerSpaceship = entitySpaceship;
                this.sendPacket(new S03PacketAcceptConnection(entityId, packet.getNickname(), entitySpawnPosition[0], entitySpawnPosition[1], StarfightServer.getInstance().getWorld().getRadius()));
            } else {
                this.sendPacket(new S02PacketDisconnect("The client version does not match the server version. Please update."));
                this.channel.close().sync();
            }
        }
        if(msg instanceof C04PacketPlayOutPlayerData) {
            C04PacketPlayOutPlayerData packet = (C04PacketPlayOutPlayerData) msg;
            this.entityPlayerSpaceship.setRotation(packet.getRotation());
        }
    }

    public void sendPacket(Packet packet) {
        if(!this.channel.isOpen() && this.entityPlayerSpaceship != null) {
            StarfightServer.getInstance().getWorld().despawnEntity(this.entityPlayerSpaceship);
            return;
        }
        StarfightServer.getInstance().getLogger().setLevel(StarfightLogger.Level.DEBUG).log(this.getClass(), "Sending " + packet.getClass().getSimpleName() + " to client " + this.channel.remoteAddress().toString() + ".");
        this.channel.writeAndFlush(packet, this.channel.voidPromise());
    }

}
