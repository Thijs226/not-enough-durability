package com.thijs226.notenoughdurability.client.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thijs226.notenoughdurability.NotEnoughDurability;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ModConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = new File("config/not-enough-durability.json");
    
    // HUD Settings
    public boolean showDurabilityHud = true;
    public boolean showArmorDurability = true;
    public boolean showToolDurability = true;
    public boolean showOffhandDurability = true;
    public boolean showDurabilityBar = true;
    public boolean showDurabilityPercentage = false;
    public boolean showItemIcon = false;
    public int hudX = 5;
    public int hudY = 5;
    public float hudScale = 1.0f;
    public int hudTextColor = 0xFFFFFF;
    
    // HUD Style
    public String hudStyle = "SIMPLE"; // SIMPLE, DETAILED, COMPACT, ICON_ONLY
    public boolean hudBackground = false;
    public int hudBackgroundColor = 0x80000000;
    public boolean hudBorder = false;
    
    // Notification Settings
    public boolean enableNotifications = true;
    public boolean showTextNotifications = true;
    public boolean playSoundNotifications = true;
    public int lowDurabilityThreshold = 10;
    public int criticalDurabilityThreshold = 5;
    public boolean repeatNotifications = false;
    public int notificationRepeatInterval = 100; // ticks
    public float soundVolume = 0.5f;
    public float soundPitch = 1.0f;
    
    // Warning Levels
    public boolean useWarningLevels = true;
    public int warningLevel1Threshold = 50; // Yellow
    public int warningLevel2Threshold = 25; // Orange
    public int warningLevel3Threshold = 10; // Red
    
    // Game Pause Settings (Singleplayer)
    public boolean pauseOnLowDurability = false;
    public int pauseThreshold = 1;
    public boolean useCustomPauseScreen = true;
    public boolean pauseOnCritical = true;
    
    // Multiplayer-specific Settings
    public boolean enableInMultiplayer = true;
    public boolean showHudInMultiplayer = true;
    public boolean notificationsInMultiplayer = true;
    
    // Advanced Settings
    public boolean monitorMainHandOnly = false;
    public boolean monitorOffHand = true;
    public boolean monitorHotbarItems = false;
    public boolean ignoreInfiniteDurability = true;
    public boolean flashLowDurabilityItems = true;
    public int flashInterval = 10; // ticks
    
    public static ModConfig load() {
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                return GSON.fromJson(reader, ModConfig.class);
            } catch (IOException e) {
                NotEnoughDurability.LOGGER.error("Failed to load config", e);
            }
        }
        
        ModConfig config = new ModConfig();
        config.save();
        return config;
    }
    
    public void save() {
        try {
            CONFIG_FILE.getParentFile().mkdirs();
            try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
                GSON.toJson(this, writer);
            }
        } catch (IOException e) {
            NotEnoughDurability.LOGGER.error("Failed to save config", e);
        }
    }
}
