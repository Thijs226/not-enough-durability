package com.thijs226.notenoughdurability.client.monitor;

import com.thijs226.notenoughdurability.client.NotEnoughDurabilityClient;
import com.thijs226.notenoughdurability.client.config.ModConfig;
import com.thijs226.notenoughdurability.client.gui.DurabilityWarningScreen;
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
    private int notificationCounter = 0;
    
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
        
        // Increment notification counter for repeat notifications
        notificationCounter++;
        
        PlayerEntity player = client.player;
        
        // Check armor
        for (int i = 0; i < 4; i++) {
            ItemStack armorStack = player.getInventory().armor.get(i);
            checkItemDurability(armorStack, config, client);
        }
        
        // Check held item
        ItemStack heldStack = player.getMainHandStack();
        checkItemDurability(heldStack, config, client);
        
        // Check offhand if enabled
        if (config.monitorOffHand) {
            ItemStack offhandStack = player.getOffHandStack();
            checkItemDurability(offhandStack, config, client);
        }
        
        // Check hotbar items if enabled
        if (config.monitorHotbarItems) {
            for (int i = 0; i < 9; i++) {
                ItemStack hotbarStack = player.getInventory().getStack(i);
                checkItemDurability(hotbarStack, config, client);
            }
        }
        
        // Clean up notified items that are no longer low
        cleanupNotifications(player, config);
    }
    
    private void checkItemDurability(ItemStack stack, ModConfig config, MinecraftClient client) {
        if (stack.isEmpty() || !stack.isDamageable()) return;
        
        int maxDurability = stack.getMaxDamage();
        int currentDurability = maxDurability - stack.getDamage();
        String itemKey = getItemKey(stack);
        
        // Check if durability is low
        if (currentDurability <= config.lowDurabilityThreshold) {
            boolean shouldNotify = !notifiedItems.contains(itemKey);
            
            // Handle repeat notifications
            if (config.repeatNotifications && notifiedItems.contains(itemKey)) {
                shouldNotify = notificationCounter % (config.notificationRepeatInterval / 20) == 0;
            }
            
            if (shouldNotify) {
                notifiedItems.add(itemKey);
                notifyLowDurability(stack, currentDurability, config, client);
            }
            
            // Check if we should pause the game (singleplayer only)
            if (config.pauseOnLowDurability && client.isInSingleplayer()) {
                boolean shouldPause = currentDurability <= config.pauseThreshold;
                if (config.pauseOnCritical && currentDurability <= config.criticalDurabilityThreshold) {
                    shouldPause = true;
                }
                
                if (shouldPause) {
                    pauseGame(stack, currentDurability, config, client);
                }
            }
        } else {
            notifiedItems.remove(itemKey);
        }
    }
    
    private void notifyLowDurability(ItemStack stack, int durability, ModConfig config, MinecraftClient client) {
        String itemName = stack.getName().getString();
        
        // Determine warning level color
        Formatting color = Formatting.RED;
        if (config.useWarningLevels) {
            if (durability > config.warningLevel2Threshold) {
                color = Formatting.YELLOW;
            } else if (durability > config.warningLevel3Threshold) {
                color = Formatting.GOLD;
            }
        }
        
        // Show text notification
        if (config.showTextNotifications && client.player != null) {
            Text message = Text.literal("⚠ ")
                    .formatted(color)
                    .append(Text.literal(itemName).formatted(Formatting.YELLOW))
                    .append(Text.literal(" is low on durability! (").formatted(color))
                    .append(Text.literal(String.valueOf(durability)).formatted(Formatting.WHITE))
                    .append(Text.literal(" left)").formatted(color));
            client.player.sendMessage(message, true);
        }
        
        // Play sound notification
        if (config.playSoundNotifications && client.player != null) {
            client.player.playSound(SoundEvents.BLOCK_ANVIL_LAND, config.soundVolume, config.soundPitch);
        }
    }
    
    private void pauseGame(ItemStack stack, int durabilityLeft, ModConfig config, MinecraftClient client) {
        if (client.world != null && client.isInSingleplayer()) {
            client.execute(() -> {
                if (client.currentScreen == null) {
                    if (config.useCustomPauseScreen) {
                        // Open custom durability warning screen
                        client.setScreen(new DurabilityWarningScreen(null, stack, durabilityLeft));
                    } else {
                        // Use default pause menu
                        client.openPauseMenu(false);
                        if (client.player != null) {
                            Text message = Text.literal("⚠ Game Paused: ")
                                    .formatted(Formatting.RED, Formatting.BOLD)
                                    .append(Text.literal(stack.getName().getString()).formatted(Formatting.YELLOW))
                                    .append(Text.literal(" is about to break!").formatted(Formatting.RED));
                            client.player.sendMessage(message, false);
                        }
                    }
                }
            });
        }
    }
    
    private void cleanupNotifications(PlayerEntity player, ModConfig config) {
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
        
        // Add offhand if monitored
        if (config.monitorOffHand) {
            ItemStack offhandStack = player.getOffHandStack();
            if (!offhandStack.isEmpty() && offhandStack.isDamageable()) {
                currentItems.add(getItemKey(offhandStack));
            }
        }
        
        // Add hotbar items if monitored
        if (config.monitorHotbarItems) {
            for (int i = 0; i < 9; i++) {
                ItemStack hotbarStack = player.getInventory().getStack(i);
                if (!hotbarStack.isEmpty() && hotbarStack.isDamageable()) {
                    currentItems.add(getItemKey(hotbarStack));
                }
            }
        }
        
        // Remove notifications for items no longer in inventory
        notifiedItems.retainAll(currentItems);
    }
    
    private String getItemKey(ItemStack stack) {
        return stack.getItem().toString() + "_" + System.identityHashCode(stack);
    }
}
