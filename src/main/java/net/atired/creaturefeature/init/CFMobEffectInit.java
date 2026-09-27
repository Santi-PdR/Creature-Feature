package net.atired.creaturefeature.init;

import net.atired.creaturefeature.CreatureFeature;
import net.atired.creaturefeature.statuseffects.FiendishEffect;
import net.atired.creaturefeature.statuseffects.SleepyStatusEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class CFMobEffectInit {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, CreatureFeature.MODID);
    public static final RegistryObject<MobEffect> FIENDISH = MOB_EFFECTS.register("fiendish", () -> new FiendishEffect(
            MobEffectCategory.BENEFICIAL).addAttributeModifier(Attributes.MOVEMENT_SPEED, modifierId("effect.speedup"), 0.1, AttributeModifier.Operation.MULTIPLY_BASE)
    );
    public static final RegistryObject<MobEffect> SLEEPY = MOB_EFFECTS.register("sleepy", () -> new SleepyStatusEffect(
                    MobEffectCategory.HARMFUL).addAttributeModifier(Attributes.MOVEMENT_SPEED, modifierId("effect.slowdown"), -0.5, AttributeModifier.Operation.MULTIPLY_BASE)
                    .addAttributeModifier(Attributes.JUMP_STRENGTH, modifierId("effect.jumpy"), -1.0, AttributeModifier.Operation.MULTIPLY_BASE)
            );

    private static String modifierId(String path) {
        return UUID.nameUUIDFromBytes((CreatureFeature.MODID + ":" + path).getBytes(StandardCharsets.UTF_8)).toString();
    }
}
