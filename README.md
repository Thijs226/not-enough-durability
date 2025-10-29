# not-enough-durability
A Minecraft Fabric mod that displays item durability on the HUD and notifies you when tools or armor are about to break.

## Features

- **HUD Display**: Shows durability for armor and held items on the side of your screen
- **Visual Notifications**: On-screen text warnings when items are low on durability
- **Sound Notifications**: Audio alerts when tools/armor are about to break
- **Auto-Pause**: Optionally pause the game in singleplayer when durability reaches a critical level
- **Customizable Settings**: Configure thresholds, toggle features via commands
- **Multiplayer Support**: Separate configuration options for multiplayer servers

## Commands

- `/ned` or `/notenoughdurability` - Show help and available commands
- `/ned toggle hud` - Toggle HUD display on/off
- `/ned toggle notifications` - Toggle all notifications
- `/ned toggle sound` - Toggle sound notifications
- `/ned toggle text` - Toggle text notifications
- `/ned toggle pause` - Toggle game pause on low durability (Singleplayer only)
- `/ned threshold <value>` - Set the low durability warning threshold (1-100)
- `/ned pausethreshold <value>` - Set the threshold for auto-pause (1-100)
- `/ned multiplayer toggle` - Enable/disable features in multiplayer
- `/ned status` - Display current configuration settings

## Compatibility

- **Minecraft**: 1.20.x to 1.21.x
- **Mod Loader**: Fabric
- **Java**: 17+

## Building

```bash
./gradlew build
```

The compiled mod will be in `build/libs/`.

## Installation

1. Install [Fabric Loader](https://fabricmc.net/use/)
2. Download [Fabric API](https://modrinth.com/mod/fabric-api)
3. Place both Fabric API and this mod in your `mods` folder
4. Launch Minecraft

## Configuration

The configuration file is automatically created at `config/not-enough-durability.json` after first launch. You can edit it directly or use the in-game commands.

## License

MIT License - See LICENSE file for details

