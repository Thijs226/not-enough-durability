# Building and Testing Guide

## Prerequisites

1. **Java Development Kit (JDK) 17 or higher**
   ```bash
   java -version
   ```

2. **Internet connection** (for downloading dependencies)

## Building the Mod

1. **Navigate to the project directory:**
   ```bash
   cd not-enough-durability
   ```

2. **Make gradlew executable (Linux/Mac):**
   ```bash
   chmod +x gradlew
   ```

3. **Build the mod:**
   ```bash
   ./gradlew build
   ```
   
   On Windows:
   ```cmd
   gradlew.bat build
   ```

4. **Find the compiled mod:**
   The mod JAR will be in: `build/libs/not-enough-durability-1.0.0.jar`

## Installing for Testing

1. **Install Minecraft with Fabric:**
   - Download and run the [Fabric Installer](https://fabricmc.net/use/)
   - Select your Minecraft version (1.20.1 or later)
   - Click "Install"

2. **Install Fabric API:**
   - Download [Fabric API](https://modrinth.com/mod/fabric-api) for your Minecraft version
   - Place it in your `.minecraft/mods` folder

3. **Install the mod:**
   - Copy `not-enough-durability-1.0.0.jar` to your `.minecraft/mods` folder
   - Launch Minecraft with the Fabric profile

## Testing Checklist

### Basic Functionality
- [ ] Mod loads without errors
- [ ] HUD displays on screen
- [ ] HUD shows armor durability
- [ ] HUD shows held item durability
- [ ] Colors change based on durability (green → yellow → red)

### Notifications
- [ ] Text notification appears when durability is low
- [ ] Sound plays when durability is low
- [ ] Notifications only trigger once per item

### Commands
Test each command:
- [ ] `/ned` - Shows help
- [ ] `/ned toggle hud` - Toggles HUD on/off
- [ ] `/ned toggle notifications` - Toggles notifications
- [ ] `/ned toggle sound` - Toggles sound
- [ ] `/ned toggle text` - Toggles text notifications
- [ ] `/ned toggle pause` - Toggles pause feature
- [ ] `/ned threshold 5` - Sets threshold to 5
- [ ] `/ned pausethreshold 1` - Sets pause threshold
- [ ] `/ned multiplayer toggle` - Toggles MP features
- [ ] `/ned status` - Shows current settings
- [ ] `/notenoughdurability` - Alternative command works

### Auto-Pause (Singleplayer)
- [ ] Enable with `/ned toggle pause`
- [ ] Set threshold with `/ned pausethreshold 1`
- [ ] Game pauses when item reaches 1 durability
- [ ] Warning message appears on pause

### Multiplayer Testing
- [ ] Join a multiplayer server
- [ ] HUD displays correctly
- [ ] Notifications work
- [ ] `/ned multiplayer toggle` disables features
- [ ] Pause feature does NOT work in multiplayer

### Configuration
- [ ] Config file created at `config/not-enough-durability.json`
- [ ] Changes via commands save to config
- [ ] Config persists across game restarts
- [ ] Manual config edits work

## Common Issues

### Build fails with "Could not resolve..."
- Check internet connection
- Try: `./gradlew build --refresh-dependencies`

### Mod doesn't load
- Verify Fabric Loader is installed
- Check Minecraft version compatibility (1.20+)
- Ensure Fabric API is installed

### HUD doesn't appear
- Check if HUD is enabled: `/ned status`
- Try toggling: `/ned toggle hud`

### Commands don't work
- Verify you're using `/` not just `ned`
- Check console/logs for errors

## Development Setup

For developing/modifying the mod:

1. **Import into IDE:**
   - IntelliJ IDEA: Open `build.gradle` as a project
   - Eclipse: Use Buildship Gradle Integration

2. **Setup Fabric development environment:**
   ```bash
   ./gradlew genSources
   ```

3. **Run Minecraft client for testing:**
   ```bash
   ./gradlew runClient
   ```

## Version Compatibility

The mod is designed to work with:
- Minecraft 1.20.1 through 1.21.x
- Fabric Loader 0.15.11+
- Fabric API 0.92.2+
- Java 17+

For different Minecraft versions, you may need to:
1. Update `gradle.properties` with correct versions
2. Verify Fabric API compatibility
3. Test thoroughly

## Reporting Issues

If you encounter problems:
1. Check the logs at `.minecraft/logs/latest.log`
2. Include Minecraft version and mod version
3. Describe steps to reproduce
4. Report at: https://github.com/Thijs226/not-enough-durability/issues
