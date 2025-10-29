package com.thijs226.notenoughdurability.client.monitor;

import com.thijs226.notenoughdurability.client.NotEnoughDurabilityClient;
import com.thijs226.notenoughdurability.client.config.ModConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.HashSet;
import java.util.Set;

public class DurabilityMonitor {
    private final Set<String> notifiedItems = new HashSet<>();
    private int tickCounter = 0;
    
    public void tick() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;
        
        ModConfig config = NotEnoughDurabilityClient.getConfig();
        if (!config.enableNotifications) return;
        
        // Check if we should notify in multiplayer
        boolean isMultiplayer = client.isInSingleplayer() == false;
        if (isMultiplayer && !config.enableInMultiplayer) return;
        if (isMultiplayer && !config.notificationsInMultiplayer) return;
        
        // Only check every 20 ticks (1 second)
        tickCounter++;
        if (tickCounter < 20) return;
        tickCounter = 0;
        
        PlayerEntity player = client.player;
        
        // Check armor
        for (int i = 0; i < 4; i++) {
            ItemStack armorStack = player.getInventory().armor.get(i);
            checkItemDurability(armorStack, config, client);
        }
        
        // Check held item
        ItemStack heldStack = player.getMainHandStack();
        checkItemDurability(heldStack, config, client);
        
        // Clean up notified items that are no longer low
        cleanupNotifications(player);
    }
    
    private void checkItemDurability(ItemStack stack, ModConfig config, MinecraftClient client) {
        if (stack.isEmpty() || !stack.isDamageable()) return;
        
        int maxDurability = stack.getMaxDamage();
        int currentDurability = maxDurability - stack.getDamage();
        String itemKey = getItemKey(stack);
        
        // Check if durability is low
        if (currentDurability <= config.lowDurabilityThreshold) {
            if (!notifiedItems.contains(itemKey)) {
                notifiedItems.add(itemKey);
                notifyLowDurability(stack, currentDurability, config, client);
            }
            
            // Check if we should pause the game (singleplayer only)
            if (config.pauseOnLowDurability && client.isInSingleplayer()) {
                if (currentDurability <= config.pauseThreshold) {
                    pauseGame(stack, client);
                }
            }
        } else {
            notifiedItems.remove(itemKey);
        }
    }
    
    private void notifyLowDurability(ItemStack stack, int durability, ModConfig config, MinecraftClient client) {
        String itemName = stack.getName().getString();
        
        // Show text notification
        if (config.showTextNotifications && client.player != null) {
            Text message = Text.literal("⚠ ")
                    .formatted(Formatting.RED)
                    .append(Text.literal(itemName).formatted(Formatting.YELLOW))
                    .append(Text.literal(" is low on durability! (").formatted(Formatting.RED))
                    .append(Text.literal(String.valueOf(durability)).formatted(Formatting.WHITE))
                    .append(Text.literal(" left)").formatted(Formatting.RED));
            client.player.sendMessage(message, true);
        }
        
        // Play sound notification
        if (config.playSoundNotifications && client.player != null) {
            client.player.playSound(SoundEvents.BLOCK_ANVIL_LAND, 0.5f, 1.0f);
        }
    }
    
    private void pauseGame(ItemStack stack, MinecraftClient client) {
        if (client.world != null && client.isInSingleplayer()) {
            client.execute(() -> {
                if (client.currentScreen == null) {
                    client.openPauseMenu(false);
                    if (client.player != null) {
                        Text message = Text.literal("⚠ Game Paused: ")
                                .formatted(Formatting.RED, Formatting.BOLD)
                                .append(Text.literal(stack.getName().getString()).formatted(Formatting.YELLOW))
                                .append(Text.literal(" is about to break!").formatted(Formatting.RED));
                        client.player.sendMessage(message, false);
                    }
                }
            });
        }
    }
    
    private void cleanupNotifications(PlayerEntity player) {
        Set<String> currentItems = new HashSet<>();
        
        // Add current armor
        for (int i = 0; i < 4; i++) {
            ItemStack armorStack = player.getInventory().armor.get(i);
            if (!armorStack.isEmpty() && armorStack.isDamageable()) {
                currentItems.add(getItemKey(armorStack));
            }
        }
        
        // Add current held item
        ItemStack heldStack = player.getMainHandStack();
        if (!heldStack.isEmpty() && heldStack.isDamageable()) {
            currentItems.add(getItemKey(heldStack));
        }
        
        // Remove notifications for items no longer in inventory
        notifiedItems.retainAll(currentItems);
    }
    
    private String getItemKey(ItemStack stack) {
        return stack.getItem().toString() + "_" + System.identityHashCode(stack);
    }
}
