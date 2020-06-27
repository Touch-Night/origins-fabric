package io.github.apace100.origins.networking;

import io.github.apace100.origins.Origins;
import io.github.apace100.origins.component.OriginComponent;
import io.github.apace100.origins.power.Active;
import io.github.apace100.origins.registry.ModComponents;
import io.github.apace100.origins.registry.ModRegistries;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.util.Identifier;

public class ModPacketsC2S {

    public static void register() {
        ServerSidePacketRegistry.INSTANCE.register(ModPackets.CHOOSE_ORIGIN, ((packetContext, packetByteBuf) -> {
            String originId = packetByteBuf.readString(32767);
            packetContext.getTaskQueue().execute(() -> {
                OriginComponent component = ModComponents.ORIGIN.get(packetContext.getPlayer());
                if(!component.hasOrigin()) {
                    Origins.LOGGER.info("Player " + packetContext.getPlayer().getDisplayName().asString() + " chose Origin: " + originId);
                    Identifier id = Identifier.tryParse(originId);
                    if(id != null) {
                        component.setOrigin(ModRegistries.ORIGIN.get(id));
                    } else {
                        Origins.LOGGER.warn("Player " + packetContext.getPlayer().getDisplayName().asString() + " chose unknown origin: " + originId);
                    }
                } else {
                    Origins.LOGGER.warn("Player " + packetContext.getPlayer().getDisplayName().asString() + " try to chose origin while having one already.");
                }
            });
        }));
        ServerSidePacketRegistry.INSTANCE.register(ModPackets.USE_ACTIVE_POWER, ((packetContext, packetByteBuf) -> {
            packetContext.getTaskQueue().execute(() -> {
                OriginComponent component = ModComponents.ORIGIN.get(packetContext.getPlayer());
                if(component.hasOrigin()) {
                    component.getPowers().stream().filter(p -> p instanceof Active).forEach(p -> ((Active)p).onUse());
                }
            });
        }));
    }
}
