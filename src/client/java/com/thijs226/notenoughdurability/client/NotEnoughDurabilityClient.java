package com.thijs226.notenoughdurability.client;

import com.thijs226.notenoughdurability.client.command.ModCommands;
import com.thijs226.notenoughdurability.client.config.ModConfig;
import com.thijs226.notenoughdurability.client.hud.DurabilityHudRenderer;
import com.thijs226.notenoughdurability.client.monitor.DurabilityMonitor;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class NotEnoughDurabilityClient implements ClientModInitializer {
    private static ModConfig config;
    private static DurabilityMonitor durabilityMonitor;
    
    @Override
    public void onInitializeClient() {
        // Load configuration
        config = ModConfig.load();
        
        // Initialize durability monitor
        durabilityMonitor = new DurabilityMonitor();
        
        // Register HUD renderer
        HudRenderCallback.EVENT.register((context, tickDelta) -> {
            DurabilityHudRenderer.render(context, tickDelta);
        });
        
        // Register tick event for monitoring
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null) {
                durabilityMonitor.tick();
            }
        });
        
        // Register commands
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            ModCommands.register(dispatcher);
        });
    }
    
    public static ModConfig getConfig() {
        return config;
    }
}
