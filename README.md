# FartPlugin

`/fart` plays a pitched shulker shot and leaves a little brown puff behind you. Nearby players get a chat message.

Built for Paper 1.21 and Java 21. Run `mvn clean package`, put `target/fart-plugin-1.0.jar` in `plugins` and restart the server.

## Commands

| Command | What it does |
| --- | --- |
| `/fart quiet` | Lower volume and smaller radius |
| `/fart deep` | Lower pitch |
| `/fart mute` | Hide other players' fart sounds, chat and smoke |
| `/fart bind double-sneak` | Tap sneak twice |
| `/fart bind sneak-swap` | Swap hands while sneaking |
| `/fart bind off` | Turn the bind off |

Add `quiet` to either bind command to use quiet mode. For example, `/fart bind double-sneak quiet`. Binds are off by default. Binds and mute are saved for each player. If you use BurpPlugin too, pick a different gesture for each plugin.

## Config

The default radius is 10 blocks. Set `sound.radius`, `messages.radius` or `cloud.radius` in `plugins/FartPlugin/config.yml` to give each effect a different range. Quiet mode uses half of each radius and 35% of the volume by default. The cooldown is shared between commands and binds.

Set `cloud.enabled` to `false` to hide the smoke. The `sound` section controls volume and pitch. `fart.use` is allowed by default. Use `/fart reload` to apply changes, which requires `fart.reload` and is available from the server console.
