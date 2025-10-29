package com.thijs226.notenoughdurability.client.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.thijs226.notenoughdurability.client.NotEnoughDurabilityClient;
import com.thijs226.notenoughdurability.client.config.ModConfig;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class ModCommands {
    
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        // Register /ned command
        dispatcher.register(ClientCommandManager.literal("ned")
                .executes(ModCommands::showHelp)
                .then(ClientCommandManager.literal("toggle")
                        .then(ClientCommandManager.literal("hud")
                                .executes(ModCommands::toggleHud))
                        .then(ClientCommandManager.literal("notifications")
                                .executes(ModCommands::toggleNotifications))
                        .then(ClientCommandManager.literal("sound")
                                .executes(ModCommands::toggleSound))
                        .then(ClientCommandManager.literal("text")
                                .executes(ModCommands::toggleText))
                        .then(ClientCommandManager.literal("pause")
                                .executes(ModCommands::togglePause))
                        .then(ClientCommandManager.literal("custompause")
                                .executes(ModCommands::toggleCustomPause))
                        .then(ClientCommandManager.literal("bar")
                                .executes(ModCommands::toggleBar))
                        .then(ClientCommandManager.literal("percentage")
                                .executes(ModCommands::togglePercentage))
                        .then(ClientCommandManager.literal("icon")
                                .executes(ModCommands::toggleIcon))
                        .then(ClientCommandManager.literal("offhand")
                                .executes(ModCommands::toggleOffhand))
                        .then(ClientCommandManager.literal("flash")
                                .executes(ModCommands::toggleFlash)))
                .then(ClientCommandManager.literal("threshold")
                        .then(ClientCommandManager.argument("value", IntegerArgumentType.integer(1, 100))
                                .executes(ModCommands::setThreshold)))
                .then(ClientCommandManager.literal("pausethreshold")
                        .then(ClientCommandManager.argument("value", IntegerArgumentType.integer(1, 100))
                                .executes(ModCommands::setPauseThreshold)))
                .then(ClientCommandManager.literal("style")
                        .then(ClientCommandManager.literal("simple")
                                .executes(ModCommands::setStyleSimple))
                        .then(ClientCommandManager.literal("detailed")
                                .executes(ModCommands::setStyleDetailed))
                        .then(ClientCommandManager.literal("compact")
                                .executes(ModCommands::setStyleCompact))
                        .then(ClientCommandManager.literal("icon")
                                .executes(ModCommands::setStyleIcon)))
                .then(ClientCommandManager.literal("multiplayer")
                        .then(ClientCommandManager.literal("toggle")
                                .executes(ModCommands::toggleMultiplayer)))
                .then(ClientCommandManager.literal("status")
                        .executes(ModCommands::showStatus)));
        
        // Register /notenoughdurability command (alias)
        dispatcher.register(ClientCommandManager.literal("notenoughdurability")
                .executes(ModCommands::showHelp)
                .then(ClientCommandManager.literal("toggle")
                        .then(ClientCommandManager.literal("hud")
                                .executes(ModCommands::toggleHud))
                        .then(ClientCommandManager.literal("notifications")
                                .executes(ModCommands::toggleNotifications))
                        .then(ClientCommandManager.literal("sound")
                                .executes(ModCommands::toggleSound))
                        .then(ClientCommandManager.literal("text")
                                .executes(ModCommands::toggleText))
                        .then(ClientCommandManager.literal("pause")
                                .executes(ModCommands::togglePause))
                        .then(ClientCommandManager.literal("custompause")
                                .executes(ModCommands::toggleCustomPause))
                        .then(ClientCommandManager.literal("bar")
                                .executes(ModCommands::toggleBar))
                        .then(ClientCommandManager.literal("percentage")
                                .executes(ModCommands::togglePercentage))
                        .then(ClientCommandManager.literal("icon")
                                .executes(ModCommands::toggleIcon))
                        .then(ClientCommandManager.literal("offhand")
                                .executes(ModCommands::toggleOffhand))
                        .then(ClientCommandManager.literal("flash")
                                .executes(ModCommands::toggleFlash)))
                .then(ClientCommandManager.literal("threshold")
                        .then(ClientCommandManager.argument("value", IntegerArgumentType.integer(1, 100))
                                .executes(ModCommands::setThreshold)))
                .then(ClientCommandManager.literal("pausethreshold")
                        .then(ClientCommandManager.argument("value", IntegerArgumentType.integer(1, 100))
                                .executes(ModCommands::setPauseThreshold)))
                .then(ClientCommandManager.literal("style")
                        .then(ClientCommandManager.literal("simple")
                                .executes(ModCommands::setStyleSimple))
                        .then(ClientCommandManager.literal("detailed")
                                .executes(ModCommands::setStyleDetailed))
                        .then(ClientCommandManager.literal("compact")
                                .executes(ModCommands::setStyleCompact))
                        .then(ClientCommandManager.literal("icon")
                                .executes(ModCommands::setStyleIcon)))
                .then(ClientCommandManager.literal("multiplayer")
                        .then(ClientCommandManager.literal("toggle")
                                .executes(ModCommands::toggleMultiplayer)))
                .then(ClientCommandManager.literal("status")
                        .executes(ModCommands::showStatus)));
    }
    
    private static int showHelp(CommandContext<FabricClientCommandSource> context) {
        context.getSource().sendFeedback(Text.literal("=== Not Enough Durability Commands ===").formatted(Formatting.GOLD));
        context.getSource().sendFeedback(Text.literal("/ned toggle hud - Toggle HUD display").formatted(Formatting.YELLOW));
        context.getSource().sendFeedback(Text.literal("/ned toggle notifications - Toggle all notifications").formatted(Formatting.YELLOW));
        context.getSource().sendFeedback(Text.literal("/ned toggle sound - Toggle sound notifications").formatted(Formatting.YELLOW));
        context.getSource().sendFeedback(Text.literal("/ned toggle text - Toggle text notifications").formatted(Formatting.YELLOW));
        context.getSource().sendFeedback(Text.literal("/ned toggle pause - Toggle pause on low durability (SP only)").formatted(Formatting.YELLOW));
        context.getSource().sendFeedback(Text.literal("/ned toggle custompause - Toggle custom pause screen").formatted(Formatting.YELLOW));
        context.getSource().sendFeedback(Text.literal("/ned toggle bar - Toggle durability bar").formatted(Formatting.YELLOW));
        context.getSource().sendFeedback(Text.literal("/ned toggle percentage - Toggle percentage display").formatted(Formatting.YELLOW));
        context.getSource().sendFeedback(Text.literal("/ned toggle icon - Toggle item icons").formatted(Formatting.YELLOW));
        context.getSource().sendFeedback(Text.literal("/ned toggle offhand - Toggle offhand monitoring").formatted(Formatting.YELLOW));
        context.getSource().sendFeedback(Text.literal("/ned toggle flash - Toggle flashing low items").formatted(Formatting.YELLOW));
        context.getSource().sendFeedback(Text.literal("/ned threshold <value> - Set low durability threshold").formatted(Formatting.YELLOW));
        context.getSource().sendFeedback(Text.literal("/ned pausethreshold <value> - Set pause threshold").formatted(Formatting.YELLOW));
        context.getSource().sendFeedback(Text.literal("/ned style <simple|detailed|compact|icon> - Set HUD style").formatted(Formatting.YELLOW));
        context.getSource().sendFeedback(Text.literal("/ned multiplayer toggle - Toggle features in multiplayer").formatted(Formatting.YELLOW));
        context.getSource().sendFeedback(Text.literal("/ned status - Show current settings").formatted(Formatting.YELLOW));
        return 1;
    }
    
    private static int toggleHud(CommandContext<FabricClientCommandSource> context) {
        ModConfig config = NotEnoughDurabilityClient.getConfig();
        config.showDurabilityHud = !config.showDurabilityHud;
        config.save();
        
        String status = config.showDurabilityHud ? "enabled" : "disabled";
        context.getSource().sendFeedback(Text.literal("HUD display " + status).formatted(Formatting.GREEN));
        return 1;
    }
    
    private static int toggleNotifications(CommandContext<FabricClientCommandSource> context) {
        ModConfig config = NotEnoughDurabilityClient.getConfig();
        config.enableNotifications = !config.enableNotifications;
        config.save();
        
        String status = config.enableNotifications ? "enabled" : "disabled";
        context.getSource().sendFeedback(Text.literal("Notifications " + status).formatted(Formatting.GREEN));
        return 1;
    }
    
    private static int toggleSound(CommandContext<FabricClientCommandSource> context) {
        ModConfig config = NotEnoughDurabilityClient.getConfig();
        config.playSoundNotifications = !config.playSoundNotifications;
        config.save();
        
        String status = config.playSoundNotifications ? "enabled" : "disabled";
        context.getSource().sendFeedback(Text.literal("Sound notifications " + status).formatted(Formatting.GREEN));
        return 1;
    }
    
    private static int toggleText(CommandContext<FabricClientCommandSource> context) {
        ModConfig config = NotEnoughDurabilityClient.getConfig();
        config.showTextNotifications = !config.showTextNotifications;
        config.save();
        
        String status = config.showTextNotifications ? "enabled" : "disabled";
        context.getSource().sendFeedback(Text.literal("Text notifications " + status).formatted(Formatting.GREEN));
        return 1;
    }
    
    private static int togglePause(CommandContext<FabricClientCommandSource> context) {
        ModConfig config = NotEnoughDurabilityClient.getConfig();
        config.pauseOnLowDurability = !config.pauseOnLowDurability;
        config.save();
        
        String status = config.pauseOnLowDurability ? "enabled" : "disabled";
        context.getSource().sendFeedback(Text.literal("Pause on low durability " + status + " (Singleplayer only)").formatted(Formatting.GREEN));
        return 1;
    }
    
    private static int setThreshold(CommandContext<FabricClientCommandSource> context) {
        int value = IntegerArgumentType.getInteger(context, "value");
        ModConfig config = NotEnoughDurabilityClient.getConfig();
        config.lowDurabilityThreshold = value;
        config.save();
        
        context.getSource().sendFeedback(Text.literal("Low durability threshold set to " + value).formatted(Formatting.GREEN));
        return 1;
    }
    
    private static int setPauseThreshold(CommandContext<FabricClientCommandSource> context) {
        int value = IntegerArgumentType.getInteger(context, "value");
        ModConfig config = NotEnoughDurabilityClient.getConfig();
        config.pauseThreshold = value;
        config.save();
        
        context.getSource().sendFeedback(Text.literal("Pause threshold set to " + value).formatted(Formatting.GREEN));
        return 1;
    }
    
    private static int toggleMultiplayer(CommandContext<FabricClientCommandSource> context) {
        ModConfig config = NotEnoughDurabilityClient.getConfig();
        config.enableInMultiplayer = !config.enableInMultiplayer;
        config.save();
        
        String status = config.enableInMultiplayer ? "enabled" : "disabled";
        context.getSource().sendFeedback(Text.literal("Multiplayer features " + status).formatted(Formatting.GREEN));
        return 1;
    }
    
    private static int showStatus(CommandContext<FabricClientCommandSource> context) {
        ModConfig config = NotEnoughDurabilityClient.getConfig();
        
        context.getSource().sendFeedback(Text.literal("=== Not Enough Durability Status ===").formatted(Formatting.GOLD));
        context.getSource().sendFeedback(Text.literal("HUD Display: " + (config.showDurabilityHud ? "ON" : "OFF")).formatted(Formatting.YELLOW));
        context.getSource().sendFeedback(Text.literal("HUD Style: " + config.hudStyle).formatted(Formatting.YELLOW));
        context.getSource().sendFeedback(Text.literal("Durability Bar: " + (config.showDurabilityBar ? "ON" : "OFF")).formatted(Formatting.YELLOW));
        context.getSource().sendFeedback(Text.literal("Show Percentage: " + (config.showDurabilityPercentage ? "ON" : "OFF")).formatted(Formatting.YELLOW));
        context.getSource().sendFeedback(Text.literal("Show Icons: " + (config.showItemIcon ? "ON" : "OFF")).formatted(Formatting.YELLOW));
        context.getSource().sendFeedback(Text.literal("Monitor Offhand: " + (config.monitorOffHand ? "ON" : "OFF")).formatted(Formatting.YELLOW));
        context.getSource().sendFeedback(Text.literal("Flash Low Items: " + (config.flashLowDurabilityItems ? "ON" : "OFF")).formatted(Formatting.YELLOW));
        context.getSource().sendFeedback(Text.literal("Notifications: " + (config.enableNotifications ? "ON" : "OFF")).formatted(Formatting.YELLOW));
        context.getSource().sendFeedback(Text.literal("Sound Notifications: " + (config.playSoundNotifications ? "ON" : "OFF")).formatted(Formatting.YELLOW));
        context.getSource().sendFeedback(Text.literal("Text Notifications: " + (config.showTextNotifications ? "ON" : "OFF")).formatted(Formatting.YELLOW));
        context.getSource().sendFeedback(Text.literal("Pause on Low Durability: " + (config.pauseOnLowDurability ? "ON" : "OFF")).formatted(Formatting.YELLOW));
        context.getSource().sendFeedback(Text.literal("Custom Pause Screen: " + (config.useCustomPauseScreen ? "ON" : "OFF")).formatted(Formatting.YELLOW));
        context.getSource().sendFeedback(Text.literal("Low Durability Threshold: " + config.lowDurabilityThreshold).formatted(Formatting.YELLOW));
        context.getSource().sendFeedback(Text.literal("Pause Threshold: " + config.pauseThreshold).formatted(Formatting.YELLOW));
        context.getSource().sendFeedback(Text.literal("Multiplayer Features: " + (config.enableInMultiplayer ? "ON" : "OFF")).formatted(Formatting.YELLOW));
        return 1;
    }
    
    // New toggle commands
    private static int toggleCustomPause(CommandContext<FabricClientCommandSource> context) {
        ModConfig config = NotEnoughDurabilityClient.getConfig();
        config.useCustomPauseScreen = !config.useCustomPauseScreen;
        config.save();
        
        String status = config.useCustomPauseScreen ? "enabled" : "disabled";
        context.getSource().sendFeedback(Text.literal("Custom pause screen " + status).formatted(Formatting.GREEN));
        return 1;
    }
    
    private static int toggleBar(CommandContext<FabricClientCommandSource> context) {
        ModConfig config = NotEnoughDurabilityClient.getConfig();
        config.showDurabilityBar = !config.showDurabilityBar;
        config.save();
        
        String status = config.showDurabilityBar ? "enabled" : "disabled";
        context.getSource().sendFeedback(Text.literal("Durability bar " + status).formatted(Formatting.GREEN));
        return 1;
    }
    
    private static int togglePercentage(CommandContext<FabricClientCommandSource> context) {
        ModConfig config = NotEnoughDurabilityClient.getConfig();
        config.showDurabilityPercentage = !config.showDurabilityPercentage;
        config.save();
        
        String status = config.showDurabilityPercentage ? "enabled" : "disabled";
        context.getSource().sendFeedback(Text.literal("Percentage display " + status).formatted(Formatting.GREEN));
        return 1;
    }
    
    private static int toggleIcon(CommandContext<FabricClientCommandSource> context) {
        ModConfig config = NotEnoughDurabilityClient.getConfig();
        config.showItemIcon = !config.showItemIcon;
        config.save();
        
        String status = config.showItemIcon ? "enabled" : "disabled";
        context.getSource().sendFeedback(Text.literal("Item icons " + status).formatted(Formatting.GREEN));
        return 1;
    }
    
    private static int toggleOffhand(CommandContext<FabricClientCommandSource> context) {
        ModConfig config = NotEnoughDurabilityClient.getConfig();
        config.monitorOffHand = !config.monitorOffHand;
        config.save();
        
        String status = config.monitorOffHand ? "enabled" : "disabled";
        context.getSource().sendFeedback(Text.literal("Offhand monitoring " + status).formatted(Formatting.GREEN));
        return 1;
    }
    
    private static int toggleFlash(CommandContext<FabricClientCommandSource> context) {
        ModConfig config = NotEnoughDurabilityClient.getConfig();
        config.flashLowDurabilityItems = !config.flashLowDurabilityItems;
        config.save();
        
        String status = config.flashLowDurabilityItems ? "enabled" : "disabled";
        context.getSource().sendFeedback(Text.literal("Flashing low items " + status).formatted(Formatting.GREEN));
        return 1;
    }
    
    // Style commands
    private static int setStyleSimple(CommandContext<FabricClientCommandSource> context) {
        ModConfig config = NotEnoughDurabilityClient.getConfig();
        config.hudStyle = "SIMPLE";
        config.save();
        
        context.getSource().sendFeedback(Text.literal("HUD style set to SIMPLE").formatted(Formatting.GREEN));
        return 1;
    }
    
    private static int setStyleDetailed(CommandContext<FabricClientCommandSource> context) {
        ModConfig config = NotEnoughDurabilityClient.getConfig();
        config.hudStyle = "DETAILED";
        config.save();
        
        context.getSource().sendFeedback(Text.literal("HUD style set to DETAILED").formatted(Formatting.GREEN));
        return 1;
    }
    
    private static int setStyleCompact(CommandContext<FabricClientCommandSource> context) {
        ModConfig config = NotEnoughDurabilityClient.getConfig();
        config.hudStyle = "COMPACT";
        config.save();
        
        context.getSource().sendFeedback(Text.literal("HUD style set to COMPACT").formatted(Formatting.GREEN));
        return 1;
    }
    
    private static int setStyleIcon(CommandContext<FabricClientCommandSource> context) {
        ModConfig config = NotEnoughDurabilityClient.getConfig();
        config.hudStyle = "ICON_ONLY";
        config.save();
        
        context.getSource().sendFeedback(Text.literal("HUD style set to ICON_ONLY").formatted(Formatting.GREEN));
        return 1;
    }
}
