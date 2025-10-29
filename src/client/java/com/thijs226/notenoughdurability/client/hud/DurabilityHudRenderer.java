package com.thijs226.notenoughdurability.client.hud;

import com.thijs226.notenoughdurability.client.NotEnoughDurabilityClient;
import com.thijs226.notenoughdurability.client.config.ModConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Formatting;

public class DurabilityHudRenderer {
    private static int tickCounter = 0;
    
    public static void render(DrawContext context, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;
        
        ModConfig config = NotEnoughDurabilityClient.getConfig();
        
        // Check if we should show HUD
        boolean isMultiplayer = client.isInSingleplayer() == false;
        if (!config.showDurabilityHud) return;
        if (isMultiplayer && !config.enableInMultiplayer) return;
        if (isMultiplayer && !config.showHudInMultiplayer) return;
        
        PlayerEntity player = client.player;
        int y = config.hudY;
        
        tickCounter++;
        
        // Apply HUD scale
        context.getMatrices().push();
        context.getMatrices().scale(config.hudScale, config.hudScale, 1.0f);
        int scaledX = (int)(config.hudX / config.hudScale);
        int scaledY = (int)(y / config.hudScale);
        
        // Render armor durability
        if (config.showArmorDurability) {
            scaledY = renderArmorDurability(context, player, scaledX, scaledY, config, tickCounter);
        }
        
        // Render held item durability
        if (config.showToolDurability) {
            scaledY = renderHeldItemDurability(context, player, scaledX, scaledY, config, tickCounter);
        }
        
        // Render offhand durability
        if (config.showOffhandDurability) {
            renderOffhandDurability(context, player, scaledX, scaledY, config, tickCounter);
        }
        
        context.getMatrices().pop();
    }
    
    private static int renderArmorDurability(DrawContext context, PlayerEntity player, int x, int y, ModConfig config, int tick) {
        int startY = y;
        
        for (int i = 0; i < 4; i++) {
            ItemStack armorStack = player.getInventory().armor.get(i);
            if (!armorStack.isEmpty() && armorStack.isDamageable()) {
                y += renderItemDurability(context, armorStack, x, y, config, tick);
            }
        }
        
        return y;
    }
    
    private static int renderHeldItemDurability(DrawContext context, PlayerEntity player, int x, int y, ModConfig config, int tick) {
        ItemStack heldStack = player.getMainHandStack();
        if (!heldStack.isEmpty() && heldStack.isDamageable()) {
            return y + renderItemDurability(context, heldStack, x, y, config, tick);
        }
        return y;
    }
    
    private static int renderOffhandDurability(DrawContext context, PlayerEntity player, int x, int y, ModConfig config, int tick) {
        ItemStack offhandStack = player.getOffHandStack();
        if (!offhandStack.isEmpty() && offhandStack.isDamageable()) {
            return y + renderItemDurability(context, offhandStack, x, y, config, tick);
        }
        return y;
    }
    
    private static int renderItemDurability(DrawContext context, ItemStack stack, int x, int y, ModConfig config, int tick) {
        int maxDurability = stack.getMaxDamage();
        int currentDurability = maxDurability - stack.getDamage();
        String itemName = stack.getName().getString();
        
        // Build text based on style
        String text = buildDurabilityText(stack, itemName, currentDurability, maxDurability, config);
        int color = getDurabilityColor(stack, config);
        
        // Apply flashing for low durability items
        if (config.flashLowDurabilityItems && currentDurability <= config.lowDurabilityThreshold) {
            if ((tick / config.flashInterval) % 2 == 0) {
                color = 0xFFFFFF; // Flash to white
            }
        }
        
        int textWidth = MinecraftClient.getInstance().textRenderer.getWidth(text);
        int lineHeight = 10;
        
        // Render background if enabled
        if (config.hudBackground) {
            int padding = 2;
            context.fill(x - padding, y - padding, 
                        x + textWidth + padding, y + lineHeight, 
                        config.hudBackgroundColor);
        }
        
        // Render border if enabled
        if (config.hudBorder) {
            int padding = 2;
            context.drawBorder(x - padding, y - padding, 
                              textWidth + padding * 2, lineHeight, 
                              color);
        }
        
        // Render item icon if enabled
        int iconOffset = 0;
        if (config.showItemIcon) {
            context.drawItem(stack, x, y - 2);
            iconOffset = 18;
        }
        
        // Render text
        context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, 
                                  text, x + iconOffset, y, color);
        
        // Render durability bar if enabled
        int barY = y + lineHeight;
        if (config.showDurabilityBar) {
            renderDurabilityBar(context, x + iconOffset, barY, textWidth, 
                               currentDurability, maxDurability, config);
            return lineHeight + 5; // Extra space for bar
        }
        
        return lineHeight;
    }
    
    private static String buildDurabilityText(ItemStack stack, String itemName, int current, int max, ModConfig config) {
        StringBuilder text = new StringBuilder();
        
        switch (config.hudStyle) {
            case "SIMPLE":
                text.append(itemName).append(": ").append(current).append("/").append(max);
                break;
            case "DETAILED":
                float percent = (float) current / max * 100;
                text.append(itemName).append(": ").append(current).append("/").append(max)
                    .append(" (").append(String.format("%.1f%%", percent)).append(")");
                break;
            case "COMPACT":
                text.append(itemName.substring(0, Math.min(10, itemName.length())))
                    .append(": ").append(current);
                break;
            case "ICON_ONLY":
                text.append(current).append("/").append(max);
                break;
            default:
                text.append(itemName).append(": ").append(current).append("/").append(max);
        }
        
        if (config.showDurabilityPercentage && !config.hudStyle.equals("DETAILED")) {
            float percent = (float) current / max * 100;
            text.append(" (").append(String.format("%.0f%%", percent)).append(")");
        }
        
        return text.toString();
    }
    
    private static void renderDurabilityBar(DrawContext context, int x, int y, int width, 
                                           int current, int max, ModConfig config) {
        float percent = (float) current / max;
        int barWidth = (int)(width * percent);
        
        // Background bar
        context.fill(x, y, x + width, y + 2, 0xFF333333);
        
        // Foreground bar with color
        int barColor = getBarColor(percent, config);
        context.fill(x, y, x + barWidth, y + 2, barColor);
    }
    
    private static int getBarColor(float percent, ModConfig config) {
        if (config.useWarningLevels) {
            float threshold1 = (float)config.warningLevel1Threshold / 100;
            float threshold2 = (float)config.warningLevel2Threshold / 100;
            float threshold3 = (float)config.warningLevel3Threshold / 100;
            
            if (percent > threshold1) return 0xFF00FF00; // Green
            if (percent > threshold2) return 0xFFFFFF00; // Yellow
            if (percent > threshold3) return 0xFFFF8800; // Orange
            return 0xFFFF0000; // Red
        } else {
            if (percent > 0.5f) return 0xFF00FF00; // Green
            if (percent > 0.25f) return 0xFFFFFF00; // Yellow
            return 0xFFFF0000; // Red
        }
    }
    
    private static int getDurabilityColor(ItemStack stack, ModConfig config) {
        int maxDurability = stack.getMaxDamage();
        int currentDurability = maxDurability - stack.getDamage();
        float durabilityPercent = (float) currentDurability / maxDurability;
        
        if (config.useWarningLevels) {
            float threshold1 = (float)config.warningLevel1Threshold / 100;
            float threshold2 = (float)config.warningLevel2Threshold / 100;
            float threshold3 = (float)config.warningLevel3Threshold / 100;
            
            if (durabilityPercent > threshold1) {
                return Formatting.GREEN.getColorValue();
            } else if (durabilityPercent > threshold2) {
                return Formatting.YELLOW.getColorValue();
            } else if (durabilityPercent > threshold3) {
                return Formatting.GOLD.getColorValue();
            } else {
                return Formatting.RED.getColorValue();
            }
        } else {
            if (durabilityPercent > 0.5f) {
                return Formatting.GREEN.getColorValue();
            } else if (durabilityPercent > 0.25f) {
                return Formatting.YELLOW.getColorValue();
            } else {
                return Formatting.RED.getColorValue();
            }
        }
    }
}
