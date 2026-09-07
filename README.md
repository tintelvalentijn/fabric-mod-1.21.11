# Polar Mod - Fabric 1.21.1

A cool semi-transparent GUI mod for Fabric Minecraft 1.21.1 with client modules, customization options, and utility features.

## Features

### Client Module (Polar)
- **Customizable GUI**: Semi-transparent menu with accent color customization
- **RGB Sliders**: Adjust accent color with red, green, and blue sliders (0-255)
- **Transparency Controls**: Adjust GUI transparency and text transparency independently
- **Keybind Configuration**: Customize GUI keybind (default: G)

### Render Module (Nametags)
- **Player Nametags**: Displays nametags above players with health information
- **Health Display**: Shows current health/max health with heart emoji
- **Toggle Option**: Enable/disable nametags from the GUI

### Movement Module (Sprint)
- **Auto Sprint**: Hold the sprint key to continuously sprint
- **Customizable Keybind**: Configure sprint key (default: LEFT_CONTROL)
- **Smooth Integration**: Seamlessly integrates with vanilla movement

## Building

```bash
./gradlew build
```

The built JAR will be in `build/libs/`

## Installation

1. Download the mod JAR from the releases
2. Place it in your `mods` folder
3. Launch Minecraft with Fabric

## Configuration

Configuration is saved to `config/polar-mod/config.json` and can be edited directly or through the in-game GUI.

## Requirements

- Minecraft 1.21.1
- Fabric Loader 0.16.5+
- Fabric API 0.100.0+
- Java 21+

## Credits

Built with Fabric and the Fabric API.
