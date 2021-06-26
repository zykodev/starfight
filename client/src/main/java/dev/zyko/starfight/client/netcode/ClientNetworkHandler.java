package dev.zyko.starfight.client.netcode;

import dev.zyko.starfight.client.StarfightClient;
import dev.zyko.starfight.client.entity.Entity;
import dev.zyko.starfight.client.entity.EntityMovable;
import dev.zyko.starfight.client.entity.EntityPlayerSpaceship;
import dev.zyko.starfight.client.entity.EntitySpaceship;
import dev.zyko.starfight.client.gui.impl.GuiScreenDisconnected;
import dev.zyko.starfight.client.world.World;
import dev.zyko.starfight.protocol.Packet;
import dev.zyko.starfight.protocol.impl.*;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientNetworkHandler extends SimpleChannelInboundHandler<Packet> {

    private Channel channel;
    private long latency;

    public ClientNetworkHandler(Channel channel) {
        this.channel = channel;
    }

    public Channel getChannel() {
        return channel;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
        if(msg instanceof S01PacketKeepAlive) {
            this.latency = System.currentTimeMillis() - ((S01PacketKeepAlive) msg).getSystemTime();
        }
        if(msg instanceof S03PacketAcceptConnection) {
            S03PacketAcceptConnection packet = (S03PacketAcceptConnection) msg;
            World world = new World(packet.getWorldRadius());
            EntityPlayerSpaceship entityPlayerSpaceship = new EntityPlayerSpaceship(packet.getEntityId(), packet.getPosX(), packet.getPosY(), 0, packet.getNickname());
            world.loadEntity(entityPlayerSpaceship);
            StarfightClient.getInstance().setWorld(world);
            StarfightClient.getInstance().setPlayerSpaceship(entityPlayerSpaceship);
            StarfightClient.getInstance().getGameRenderer().displayGuiScreen(null);
        }
        if(msg instanceof S02PacketDisconnect) {
            S02PacketDisconnect packet = (S02PacketDisconnect) msg;
            StarfightClient.getInstance().getGameRenderer().displayGuiScreen(new GuiScreenDisconnected(packet.getMessage()));
        }
        if(msg instanceof S04PacketPlayOutEntitySpawn) {
            S04PacketPlayOutEntitySpawn packet = (S04PacketPlayOutEntitySpawn) msg;
            System.out.println(packet.getType());
            if(packet.getType() == S04PacketPlayOutEntitySpawn.SPACESHIP) {
                EntitySpaceship entitySpaceship = new EntitySpaceship(packet.getEntityId(), packet.getPosX(), packet.getPosY(), packet.getRotation(), packet.getName());
                StarfightClient.getInstance().getWorld().loadEntity(entitySpaceship);
            }
        }
        if(msg instanceof S05PacketPlayOutEntityPosition) {
            int id = ((S05PacketPlayOutEntityPosition) msg).getId();
            double x = ((S05PacketPlayOutEntityPosition) msg).getPosX();
            double y = ((S05PacketPlayOutEntityPosition) msg).getPosY();
            double rot = ((S05PacketPlayOutEntityPosition) msg).getRotation();
            Entity entity = StarfightClient.getInstance().getWorld().getEntity(id);
            if(entity != null) {
                entity.setPosX(x);
                entity.setPosY(y);
                if(entity instanceof EntityMovable && entity != StarfightClient.getInstance().getPlayerSpaceship()) {
                    ((EntityMovable)entity).setRotation(rot);
                }
            }
        }
    }

    public long getLatency() {
        return latency;
    }

}
