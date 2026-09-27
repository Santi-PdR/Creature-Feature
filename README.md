
# Creature Feature — Forge 1.20.1

Port of Creature Feature 1.2.3.3 for Minecraft 1.20.1 and Forge 47.4.x.

## Build

Requires Java 17. From the project root, run:

```sh
./gradlew build
```

The distributable JARs are written to `build/libs/`. The `-all` JAR includes
the required MixinExtras dependency.

For local development, use `./gradlew runClient`. A dedicated server run may
require accepting Mojang's EULA in its generated `run/eula.txt` file.
