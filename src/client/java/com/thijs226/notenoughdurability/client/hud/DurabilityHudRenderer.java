package com.thijs226.notenoughdurability.client.hud;

import com.thijs226.notenoughdurability.client.NotEnoughDurabilityClient;
import com.thijs226.notenoughdurability.client.config.ModConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Formatting;

public class DurabilityHudRenderer {
    
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
        
        // Render armor durability
        if (config.showArmorDurability) {
            y = renderArmorDurability(context, player, config.hudX, y);
        }
        
        // Render held item durability
        if (config.showToolDurability) {
            renderHeldItemDurability(context, player, config.hudX, y);
        }
    }
    
    private static int renderArmorDurability(DrawContext context, PlayerEntity player, int x, int y) {
        int startY = y;
        
        for (int i = 0; i < 4; i++) {
            ItemStack armorStack = player.getInventory().armor.get(i);
            if (!armorStack.isEmpty() && armorStack.isDamageable()) {
                String text = getItemDurabilityText(armorStack);
                int color = getDurabilityColor(armorStack);
                context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, text, x, y, color);
                y += 10;
            }
        }
        
        return y;
    }
    
    private static void renderHeldItemDurability(DrawContext context, PlayerEntity player, int x, int y) {
        ItemStack heldStack = player.getMainHandStack();
        if (!heldStack.isEmpty() && heldStack.isDamageable()) {
            String text = getItemDurabilityText(heldStack);
            int color = getDurabilityColor(heldStack);
            context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, text, x, y, color);
        }
    }
    
    private static String getItemDurabilityText(ItemStack stack) {
        int maxDurability = stack.getMaxDamage();
        int currentDurability = maxDurability - stack.getDamage();
        String itemName = stack.getName().getString();
        return String.format("%s: %d/%d", itemName, currentDurability, maxDurability);
    }
    
    private static int getDurabilityColor(ItemStack stack) {
        int maxDurability = stack.getMaxDamage();
        int currentDurability = maxDurability - stack.getDamage();
        float durabilityPercent = (float) currentDurability / maxDurability;
        
        if (durabilityPercent > 0.5f) {
            return Formatting.GREEN.getColorValue();
        } else if (durabilityPercent > 0.25f) {
            return Formatting.YELLOW.getColorValue();
        } else {
            return Formatting.RED.getColorValue();
        }
    }
}
