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

public class CFMobEffectInit {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, CreatureFeature.MODID);
    public static final RegistryObject<MobEffect> FIENDISH = MOB_EFFECTS.register("fiendish", () -> new FiendishEffect(
            MobEffectCategory.BENEFICIAL).addAttributeModifier(Attributes.MOVEMENT_SPEED,CreatureFeature.getId("effect.speedup").toString(), 0.1, AttributeModifier.Operation.MULTIPLY_BASE)
    );
    public static final RegistryObject<MobEffect> SLEEPY = MOB_EFFECTS.register("sleepy", () -> new SleepyStatusEffect(
                    MobEffectCategory.HARMFUL).addAttributeModifier(Attributes.MOVEMENT_SPEED,CreatureFeature.getId("effect.slowdown").toString(), -0.5, AttributeModifier.Operation.MULTIPLY_BASE)
                    .addAttributeModifier(Attributes.JUMP_STRENGTH,CreatureFeature.getId("effect.jumpy").toString(), -1.0, AttributeModifier.Operation.MULTIPLY_BASE)
            );
}
