package com.thijs226.notenoughdurability.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class DurabilityWarningScreen extends Screen {
    private final Screen parent;
    private final ItemStack item;
    private final int durabilityLeft;
    
    public DurabilityWarningScreen(Screen parent, ItemStack item, int durabilityLeft) {
        super(Text.literal("Durability Warning"));
        this.parent = parent;
        this.item = item;
        this.durabilityLeft = durabilityLeft;
    }
    
    @Override
    protected void init() {
        super.init();
        
        int buttonWidth = 200;
        int buttonHeight = 20;
        int centerX = this.width / 2;
        int buttonY = this.height / 2 + 40;
        
        // Continue button
        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("Continue").formatted(Formatting.GREEN),
                button -> {
                    if (this.client != null) {
                        this.client.setScreen(null);
                    }
                })
                .dimensions(centerX - buttonWidth / 2, buttonY, buttonWidth, buttonHeight)
                .build());
        
        // Save and Quit button
        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("Save and Quit to Title").formatted(Formatting.YELLOW),
                button -> {
                    if (this.client != null) {
                        boolean isMultiplayer = !this.client.isInSingleplayer();
                        this.client.world.disconnect();
                        if (isMultiplayer) {
                            this.client.disconnect();
                        } else {
                            this.client.disconnect(new net.minecraft.client.gui.screen.MessageScreen(Text.translatable("menu.savingLevel")));
                        }
                    }
                })
                .dimensions(centerX - buttonWidth / 2, buttonY + 25, buttonWidth, buttonHeight)
                .build());
    }
    
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Render dark background
        this.renderBackground(context, mouseX, mouseY, delta);
        
        // Get item name and stats
        String itemName = item.getName().getString();
        int maxDurability = item.getMaxDamage();
        float durabilityPercent = (float) durabilityLeft / maxDurability * 100;
        
        // Title
        Text warningTitle = Text.literal("⚠ DURABILITY WARNING ⚠")
                .formatted(Formatting.RED, Formatting.BOLD);
        context.drawCenteredTextWithShadow(this.textRenderer, warningTitle, 
                this.width / 2, 40, 0xFF5555);
        
        // Main message
        Text mainMessage = Text.literal("Your ")
                .formatted(Formatting.WHITE)
                .append(Text.literal(itemName).formatted(Formatting.YELLOW, Formatting.BOLD))
                .append(Text.literal(" has only").formatted(Formatting.WHITE));
        context.drawCenteredTextWithShadow(this.textRenderer, mainMessage, 
                this.width / 2, 80, 0xFFFFFF);
        
        // Durability info - large
        Text durabilityText = Text.literal(durabilityLeft + " / " + maxDurability)
                .formatted(Formatting.RED, Formatting.BOLD);
        int textWidth = this.textRenderer.getWidth(durabilityText);
        context.drawText(this.textRenderer, durabilityText, 
                this.width / 2 - textWidth / 2, 100, 0xFF5555, true);
        
        // Percentage
        Formatting percentColor;
        if (durabilityPercent > 10) {
            percentColor = Formatting.YELLOW;
        } else if (durabilityPercent > 5) {
            percentColor = Formatting.GOLD;
        } else {
            percentColor = Formatting.RED;
        }
        
        Text percentText = Text.literal(String.format("(%.1f%%)", durabilityPercent))
                .formatted(percentColor);
        context.drawCenteredTextWithShadow(this.textRenderer, percentText, 
                this.width / 2, 120, percentColor.getColorValue());
        
        // Warning message
        Text warning = Text.literal("durability left!")
                .formatted(Formatting.WHITE);
        context.drawCenteredTextWithShadow(this.textRenderer, warning, 
                this.width / 2, 135, 0xFFFFFF);
        
        // Render item icon in the center
        if (this.client != null) {
            int itemX = this.width / 2 - 8;
            int itemY = this.height / 2 - 30;
            context.drawItem(item, itemX, itemY);
            context.drawItemInSlot(this.textRenderer, item, itemX, itemY);
        }
        
        super.render(context, mouseX, mouseY, delta);
    }
    
    @Override
    public boolean shouldPause() {
        return true;
    }
    
    @Override
    public void close() {
        if (this.client != null) {
            this.client.setScreen(this.parent);
        }
    }
}
