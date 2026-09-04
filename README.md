# Auto Attack Forge Mod

A minimal Minecraft Forge 1.20.1 mod that pulses the client attack key once per client tick while a living mob or player is under the crosshair.

## Build

Requirements:

- Java 17
- Gradle 8.x or the ForgeGradle-compatible wrapper

From the project directory:

```sh
gradle build
```

The built jar is created in `build/libs/`. Install it in a Forge 1.20.1 client with Forge `47.2.0` or newer within the declared dependency range.

The attack pulse is disabled while a screen is open or the game is paused.
