package net.atired.creaturefeature.init;

import com.mojang.serialization.Codec;
import net.atired.creaturefeature.CreatureFeature;
import net.atired.creaturefeature.loot.OptionalItemLootModifier;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class CFGlobalLootModifierInit {
    private static final DeferredRegister<Codec<? extends IGlobalLootModifier>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, CreatureFeature.MODID);

    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> OPTIONAL_ITEM =
            SERIALIZERS.register("optional_item", () -> OptionalItemLootModifier.CODEC);

    private CFGlobalLootModifierInit() {}

    public static void register(IEventBus modEventBus) {
        SERIALIZERS.register(modEventBus);
    }
}
