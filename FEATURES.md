# Features Documentation

## HUD Display

The mod displays item durability information on your screen in real-time.

### What's Displayed
- **Armor Durability**: All 4 armor pieces (helmet, chestplate, leggings, boots)
- **Held Item Durability**: The tool or item in your main hand
- **Format**: `Item Name: Current/Max` (e.g., "Diamond Pickaxe: 1534/1561")

### Color Coding
The text color changes based on durability percentage:
- **Green**: >50% durability remaining (healthy)
- **Yellow**: 25-50% durability (caution)
- **Red**: <25% durability (warning)

### Position
- Default: Top-left corner (5, 5)
- Customizable via config file

### Toggle
- Command: `/ned toggle hud`
- Config: `showDurabilityHud`

## Durability Monitoring

The mod actively monitors your equipment for low durability.

### Monitoring Frequency
- Checks every 1 second (20 game ticks)
- Efficient: Minimal performance impact

### What's Monitored
- All 4 armor pieces
- Held item in main hand
- Items in process of breaking

### Smart Tracking
- Only notifies once per item per durability level
- Clears notifications when item is removed or repaired
- Handles item switching gracefully

## Notifications

### Text Notifications
- **Display**: Shows in action bar (above hotbar)
- **Format**: "⚠ [Item Name] is low on durability! (X left)"
- **Colors**: Red warning symbol, yellow item name
- **Toggle**: `/ned toggle text`

### Sound Notifications
- **Sound**: Anvil landing sound
- **Volume**: 50% (0.5)
- **Pitch**: Normal (1.0)
- **Toggle**: `/ned toggle sound`

### Combined Notifications
Both text and sound can be:
- Enabled together (default)
- Enabled individually
- Disabled completely

## Low Durability Threshold

### Default: 10 durability points
When an item reaches this threshold, notifications trigger.

### Customization
- Command: `/ned threshold <1-100>`
- Examples:
  - `/ned threshold 5` - Very low warning
  - `/ned threshold 50` - Early warning
  - `/ned threshold 100` - Maximum warning
- Config: `lowDurabilityThreshold`

### Recommended Settings
- **Conservative**: 20-50 (plenty of time to repair)
- **Balanced**: 10-15 (default range)
- **Risky**: 1-5 (last-minute warnings)

## Auto-Pause Feature

### Singleplayer Only
Automatically pauses the game when durability hits critical level.

### How It Works
1. Item reaches pause threshold
2. Game opens pause menu
3. Warning message displayed
4. Player must unpause manually

### Configuration
- **Enable**: `/ned toggle pause`
- **Threshold**: `/ned pausethreshold <1-100>`
- **Default**: Disabled, threshold = 1

### Use Cases
- Hardcore mode protection
- Valuable item preservation
- AFK safety

### Limitations
- Singleplayer only (cannot pause multiplayer)
- Only pauses if no screen is open
- Requires player attention to unpause

## Command System

### Two Command Aliases
Both work identically:
- `/ned` - Short version
- `/notenoughdurability` - Full name

### Command Structure
```
/ned <action> [parameters]
```

### Available Commands

#### Help
```
/ned
```
Shows list of all commands.

#### Toggle Features
```
/ned toggle hud              # Toggle HUD display
/ned toggle notifications    # Toggle all notifications
/ned toggle sound           # Toggle sound notifications
/ned toggle text            # Toggle text notifications
/ned toggle pause           # Toggle auto-pause
```

#### Set Thresholds
```
/ned threshold <value>       # Set low durability warning (1-100)
/ned pausethreshold <value>  # Set auto-pause threshold (1-100)
```

#### Multiplayer Settings
```
/ned multiplayer toggle      # Enable/disable in multiplayer
```

#### Status
```
/ned status                  # Show all current settings
```

### Command Feedback
- All commands show confirmation messages
- Settings are saved immediately
- Changes take effect instantly

## Configuration File

### Location
`config/not-enough-durability.json`

### Format
JSON format with all settings:
```json
{
  "showDurabilityHud": true,
  "showArmorDurability": true,
  "showToolDurability": true,
  "hudX": 5,
  "hudY": 5,
  "enableNotifications": true,
  "showTextNotifications": true,
  "playSoundNotifications": true,
  "lowDurabilityThreshold": 10,
  "pauseOnLowDurability": false,
  "pauseThreshold": 1,
  "enableInMultiplayer": true,
  "showHudInMultiplayer": true,
  "notificationsInMultiplayer": true
}
```

### Editing
- **Via Commands**: Recommended (automatic saving)
- **Manual Edit**: Close Minecraft, edit file, reopen
- **Reset**: Delete file, mod creates new one with defaults

### Settings Explained

| Setting | Type | Default | Description |
|---------|------|---------|-------------|
| `showDurabilityHud` | boolean | true | Show HUD overlay |
| `showArmorDurability` | boolean | true | Include armor in HUD |
| `showToolDurability` | boolean | true | Include held items in HUD |
| `hudX` | number | 5 | HUD X position |
| `hudY` | number | 5 | HUD Y position |
| `enableNotifications` | boolean | true | Master notification toggle |
| `showTextNotifications` | boolean | true | Show text warnings |
| `playSoundNotifications` | boolean | true | Play sound warnings |
| `lowDurabilityThreshold` | number | 10 | Warning trigger point |
| `pauseOnLowDurability` | boolean | false | Enable auto-pause |
| `pauseThreshold` | number | 1 | Pause trigger point |
| `enableInMultiplayer` | boolean | true | Work on servers |
| `showHudInMultiplayer` | boolean | true | HUD on servers |
| `notificationsInMultiplayer` | boolean | true | Notifications on servers |

## Multiplayer Support

### Client-Side Mod
- No server installation required
- Works on any server
- All features are client-side

### Multiplayer-Specific Settings

#### Full Disable
```
/ned multiplayer toggle
```
Disables all mod features in multiplayer.

#### Granular Control
Via config file:
- `enableInMultiplayer`: Master toggle
- `showHudInMultiplayer`: HUD display only
- `notificationsInMultiplayer`: Notifications only

### Pause Feature in Multiplayer
- **Automatically disabled** (cannot pause multiplayer games)
- No configuration needed
- Works in singleplayer only

### Recommended Multiplayer Settings

**PvP Servers**
- Keep HUD enabled (tactical advantage)
- Keep notifications enabled (equipment awareness)

**Casual Servers**
- All features enabled (default)

**Server Rules**
- Check server policies on client mods
- Mod is fully client-side and fair

## Technical Details

### Performance
- **CPU**: Minimal (1 check per second)
- **Memory**: <1MB additional usage
- **Network**: No network traffic
- **Rendering**: Lightweight text rendering

### Compatibility
- **Mod Loaders**: Fabric only
- **Other Mods**: Compatible with most mods
- **Shaders**: Compatible
- **Resource Packs**: Compatible

### Client-Only Design
- No server component required
- No packets sent to server
- No gameplay advantages beyond information display

## Advanced Usage

### Custom HUD Position
Edit config file `hudX` and `hudY` values:
- `(0, 0)` = Top-left corner
- Increase X = Move right
- Increase Y = Move down

### Multiple Threshold Strategy
Set different thresholds for different situations:
- **Exploration**: High threshold (20-30)
- **Mining**: Medium threshold (10-15)
- **Safe Areas**: Low threshold (5-10)

### Notification Profiles

**Maximum Awareness**
```
/ned toggle notifications on
/ned toggle text on
/ned toggle sound on
/ned threshold 20
```

**Minimal Distraction**
```
/ned toggle text off
/ned toggle sound on
/ned threshold 5
```

**Silent Mode**
```
/ned toggle sound off
/ned toggle text on
/ned threshold 10
```

## Troubleshooting

### HUD Not Showing
1. Check: `/ned status`
2. Toggle: `/ned toggle hud`
3. Verify: Config file permissions
4. Test: Try different HUD position

### Notifications Not Working
1. Check notification settings
2. Verify threshold isn't too low
3. Test with low-durability item
4. Check sound volume

### Auto-Pause Not Working
1. Verify singleplayer mode
2. Check pause toggle is on
3. Verify threshold setting
4. Ensure no screen is open

### Config Not Saving
1. Check file permissions
2. Verify Minecraft is closed when editing
3. Check JSON syntax if edited manually
4. Try deleting and regenerating

For more issues, check BUILDING.md or report on GitHub.
