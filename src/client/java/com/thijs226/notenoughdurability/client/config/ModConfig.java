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
    public int hudX = 5;
    public int hudY = 5;
    
    // Notification Settings
    public boolean enableNotifications = true;
    public boolean showTextNotifications = true;
    public boolean playSoundNotifications = true;
    public int lowDurabilityThreshold = 10;
    
    // Game Pause Settings (Singleplayer)
    public boolean pauseOnLowDurability = false;
    public int pauseThreshold = 1;
    
    // Multiplayer-specific Settings
    public boolean enableInMultiplayer = true;
    public boolean showHudInMultiplayer = true;
    public boolean notificationsInMultiplayer = true;
    
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
