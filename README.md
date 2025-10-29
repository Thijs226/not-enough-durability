# not-enough-durability
A Minecraft Fabric mod that displays item durability on the HUD and notifies you when tools or armor are about to break, with extensive customization options.

## Features

- **Enhanced HUD Display**: Shows durability for armor, held items, and offhand with multiple display styles
  - Simple, Detailed, Compact, and Icon-only modes
  - Optional durability bars with color coding
  - Optional percentage display
  - Optional item icons
  - Adjustable HUD scale and position
  - Background and border options
  - Flashing effect for low durability items
  
- **Visual Notifications**: On-screen text warnings when items are low on durability
  - Color-coded messages (yellow, orange, red)
  - Multiple warning levels
  - Repeat notification option

- **Sound Notifications**: Audio alerts when tools/armor are about to break
  - Adjustable volume and pitch
  - Customizable sound

- **Custom Pause Screen**: Beautiful durability warning screen with item details
  - Shows item name and exact durability remaining
  - Displays durability percentage
  - Shows item icon
  - "Continue" button to resume gameplay
  - "Save and Quit to Title" button to save and exit safely

- **Auto-Pause**: Optionally pause the game in singleplayer when durability reaches a critical level
  - Customizable threshold
  - Optional custom pause screen
  - Safety for hardcore mode

- **Flexible Commands**: 20+ commands for complete customization
  - Toggle any feature independently
  - Set custom thresholds
  - Change HUD styles on the fly

- **Multiplayer Support**: Separate configuration options for multiplayer servers

## Commands

### Basic Commands
- `/ned` or `/notenoughdurability` - Show help and available commands
- `/ned status` - Display current configuration settings

### Toggle Commands
- `/ned toggle hud` - Toggle HUD display on/off
- `/ned toggle notifications` - Toggle all notifications
- `/ned toggle sound` - Toggle sound notifications
- `/ned toggle text` - Toggle text notifications
- `/ned toggle pause` - Toggle game pause on low durability (Singleplayer only)
- `/ned toggle custompause` - Toggle custom pause screen
- `/ned toggle bar` - Toggle durability bar display
- `/ned toggle percentage` - Toggle percentage display
- `/ned toggle icon` - Toggle item icon display
- `/ned toggle offhand` - Toggle offhand item monitoring
- `/ned toggle flash` - Toggle flashing effect for low durability items

### Configuration Commands
- `/ned threshold <1-100>` - Set the low durability warning threshold
- `/ned pausethreshold <1-100>` - Set the threshold for auto-pause
- `/ned style <simple|detailed|compact|icon>` - Set HUD display style

### Multiplayer Commands
- `/ned multiplayer toggle` - Enable/disable features in multiplayer

## HUD Styles

**SIMPLE**: Item name with current/max durability (default)
- Example: `Diamond Pickaxe: 1534/1561`

**DETAILED**: Includes percentage with durability
- Example: `Diamond Pickaxe: 1534/1561 (98.3%)`

**COMPACT**: Shortened item name with current durability only
- Example: `Diamond Pi: 1534`

**ICON_ONLY**: Just the numbers (use with icon toggle for best results)
- Example: `1534/1561`

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

### Configuration Options

The mod includes 40+ configuration options including:
- HUD display settings (position, scale, style)
- Visual customization (colors, icons, bars, backgrounds)
- Notification preferences (text, sound, repeat intervals)
- Warning level thresholds
- Pause settings
- Multiplayer-specific overrides
- Advanced monitoring options

## License

MIT License - See LICENSE file for details

