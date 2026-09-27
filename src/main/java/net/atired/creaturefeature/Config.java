package net.atired.creaturefeature;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.common.ForgeConfigSpec;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
public class Config {
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec SPEC2;
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec.Builder BUILDER2 = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec.BooleanValue REMODEL_FRIEND;
    public static final ForgeConfigSpec.BooleanValue REMODEL_MB;
    public static final ForgeConfigSpec.BooleanValue FIX_SAS;
    public static final ForgeConfigSpec.BooleanValue END_DREAMWEAVER;
    public static final ForgeConfigSpec.BooleanValue PATHOGEN;
    public static final ForgeConfigSpec.BooleanValue SAINT;
    public static final ForgeConfigSpec.BooleanValue FIEND_FOLIO_RELOADED;
    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        REMODEL_FRIEND = builder
                .comment("Shove FRIEND into a woodchipper.")
                .define("remodelfriend", false);

        REMODEL_MB = builder
                .comment("Pressure wash the Mockingbird.")
                .define("remodelmb", false);
        FIX_SAS = builder
                .comment("Remove postprocessing from the bear (in case of broken depth).")
                .define("fixsas", false);

        SPEC = builder.build();
        builder = new ForgeConfigSpec.Builder();
        END_DREAMWEAVER = builder
                .comment("Dreamweavers only spawn Nothings, Endermen and themselves.")
                .define("endreamweaver", false);
        PATHOGEN = builder
                .comment("Pathogen only spawns in temperate overworld biomes.")
                .define("pathogen", false);
        SAINT = builder
                .comment("Weakens the rotation of Saint Solis' Stars")
                .define("pathogen", false);
        FIEND_FOLIO_RELOADED = builder
                .comment("Gives Fiend a gun.")
                .define("ff_reloaded", true);

        SPEC2 = builder.build();
    }
}
