package net.atired.creaturefeature.networking;

import net.atired.creaturefeature.CreatureFeature;
import net.atired.creaturefeature.accessors.PlayerBrainrotAccessor;
import net.atired.creaturefeature.init.CFAchievements;
import net.atired.creaturefeature.networking.payloads.*;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public final class CFNetwork {
    private static final String PROTOCOL = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(CreatureFeature.MODID, "main"), () -> PROTOCOL,
            PROTOCOL::equals, PROTOCOL::equals);
    private static boolean registered;

    private CFNetwork() {}

    public static void registerMessages() {
        if (!registered) {
            CHANNEL.registerMessage(0, Message.class, Message::encode, Message::decode, Message::handle);
            registered = true;
        }
    }

    public static void sendToPlayer(Player player, Object payload) {
        if (player instanceof ServerPlayer serverPlayer) CHANNEL.send(PacketDistributor.PLAYER.with(() -> serverPlayer), Message.from(payload));
    }

    public static void sendToServer(Object payload) {
        CHANNEL.sendToServer(Message.from(payload));
    }

    public static void sendToTrackingEntity(Entity entity, Object... payloads) {
        for (Object payload : payloads) CHANNEL.send(PacketDistributor.TRACKING_ENTITY.with(() -> entity), Message.from(payload));
    }

    public static void sendToTrackingChunk(ServerLevel level, ChunkPos chunk, Object payload) {
        CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunk(chunk.x, chunk.z)), Message.from(payload));
    }

    static record Message(int kind, int entityId, double x, double y, double z, float amount, boolean flag) {
        private static Message from(Object payload) {
            if (payload instanceof VelSyncPayload p) return new Message(0, p.playerID(), p.x(), p.y(), p.z(), 0.0F, false);
            if (payload instanceof C2SVelSyncPayload p) return new Message(1, p.playerID(), p.x(), p.y(), p.z(), 0.0F, false);
            if (payload instanceof C2SManPayload p) return new Message(2, p.playerID(), 0, 0, 0, 0, false);
            if (payload instanceof DeAmpPayload p) return new Message(3, p.playerID(), 0, 0, 0, 0, p.should());
            if (payload instanceof GasLeakPayload p) return new Message(4, p.playerID(), 0, 0, 0, p.damageAmp(), false);
            if (payload instanceof PathogenPayload p) return new Message(5, p.playerID(), 0, 0, 0, p.hpAgain(), false);
            if (payload instanceof RabiesPayload p) return new Message(6, p.playerID(), 0, 0, 0, 0, false);
            if (payload instanceof SquashedPayload p) return new Message(7, p.playerID(), 0, 0, 0, 0, false);
            throw new IllegalArgumentException("Unsupported Creature Feature message: " + payload.getClass().getName());
        }

        private static Message decode(FriendlyByteBuf buffer) {
            return new Message(buffer.readVarInt(), buffer.readVarInt(), buffer.readDouble(), buffer.readDouble(), buffer.readDouble(), buffer.readFloat(), buffer.readBoolean());
        }

        private void encode(FriendlyByteBuf buffer) {
            buffer.writeVarInt(kind);
            buffer.writeVarInt(entityId);
            buffer.writeDouble(x);
            buffer.writeDouble(y);
            buffer.writeDouble(z);
            buffer.writeFloat(amount);
            buffer.writeBoolean(flag);
        }

        private void handle(Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context context = contextSupplier.get();
            context.enqueueWork(() -> {
                if (context.getDirection().getReceptionSide().isServer()) {
                    ServerPlayer sender = context.getSender();
                    if (sender == null) return;
                    if (kind == 1 && entityId == sender.getId()) sender.addDeltaMovement(new Vec3(x, y, z));
                    if (kind == 2 && sender instanceof PlayerBrainrotAccessor accessor) {
                        accessor.setManCan(true);
                        CFAchievements.NOBODY.trigger(sender);
                    }
                } else {
                    net.minecraftforge.fml.DistExecutor.unsafeRunWhenOn(net.minecraftforge.api.distmarker.Dist.CLIENT,
                            () -> () -> CFNetworkClient.handle(this));
                }
            });
            context.setPacketHandled(true);
        }
    }

    static int kind(Message message) { return message.kind; }
    static int entityId(Message message) { return message.entityId; }
    static double x(Message message) { return message.x; }
    static double y(Message message) { return message.y; }
    static double z(Message message) { return message.z; }
    static float amount(Message message) { return message.amount; }
    static boolean flag(Message message) { return message.flag; }
}
