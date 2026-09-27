package net.atired.creaturefeature.items;

import net.atired.creaturefeature.accessors.PlayerBrainrotAccessor;
import net.atired.creaturefeature.client.ClientItemEffects;
import net.atired.creaturefeature.init.CFParticleInit;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class VertigoHornItem extends Item {
    private final TagKey<Instrument> instruments;

    public VertigoHornItem(Item.Properties properties, TagKey<Instrument> instruments) {
        super(properties);
        this.instruments = instruments;
    }

    public void appendHoverText(ItemStack stack, net.minecraft.world.level.Level level, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, level, tooltipComponents, tooltipFlag);
        Optional<ResourceKey<Instrument>> optional = this.getInstrument(stack).flatMap(Holder::unwrapKey);
        if (optional.isPresent()) {
            MutableComponent mutablecomponent = Component.translatable(Util.makeDescriptionId("instrument", ((ResourceKey)optional.get()).location())).append("?");
            tooltipComponents.add(mutablecomponent.withStyle(ChatFormatting.GRAY));
        }

    }

    public static ItemStack create(Item item, Holder<Instrument> instrument) {
        ItemStack itemstack = new ItemStack(item);
        instrument.unwrapKey().ifPresent(key -> itemstack.getOrCreateTag().putString("Instrument", key.location().toString()));
        return itemstack;
    }

    public static void setRandom(ItemStack stack, TagKey<Instrument> instrumentTag, RandomSource random) {
        List<Holder<Instrument>> instruments = java.util.stream.StreamSupport.stream(BuiltInRegistries.INSTRUMENT.getTagOrEmpty(instrumentTag).spliterator(), false).toList();
        Optional<Holder<Instrument>> optional = instruments.isEmpty() ? Optional.empty() : Optional.of(instruments.get(random.nextInt(instruments.size())));
        optional.flatMap(Holder::unwrapKey).ifPresent(key -> stack.getOrCreateTag().putString("Instrument", key.location().toString()));
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        if(level instanceof ServerLevel serverLevel){
            serverLevel.sendParticles(CFParticleInit.RABIES_PARTICLE.get(),livingEntity.getX(),livingEntity.getY(0.8),livingEntity.getZ(),4,0.2,0.2,0.2,0.2);
        }
        super.onUseTick(level, livingEntity, stack, remainingUseDuration);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemstack = player.getItemInHand(usedHand);
        Optional<? extends Holder<Instrument>> optional = this.getInstrument(itemstack);
        if (optional.isPresent()) {
            Instrument instrument = (Instrument)((Holder)optional.get()).value();
            player.startUsingItem(usedHand);
            play(level, player, instrument);
            player.getCooldowns().addCooldown(this, instrument.useDuration());
            player.addDeltaMovement(new Vec3(0,0.4,0));
            if (level.isClientSide()) {
                net.minecraftforge.fml.DistExecutor.unsafeRunWhenOn(net.minecraftforge.api.distmarker.Dist.CLIENT,
                        () -> () -> ClientItemEffects.applyVertigoHornImpulse(player));
            }
            if(level instanceof ServerLevel serverLevel){
                serverLevel.sendParticles(CFParticleInit.TOOT_PARTICLE.get(),player.getX(),player.getY(0.5),player.getZ(),1,0,0,0,0);
                serverLevel.sendParticles(CFParticleInit.RABIES_PARTICLE.get(),player.getX(),player.getY(0.5),player.getZ(),32,0.6,0.2,0.6,0.2);
            }
            if(player instanceof PlayerBrainrotAccessor accessor){
                accessor.setRabies(0.5f);
            }
            player.awardStat(Stats.ITEM_USED.get(this));
            return InteractionResultHolder.consume(itemstack);
        } else {
            return InteractionResultHolder.fail(itemstack);
        }
    }

    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        Optional<Holder<Instrument>> optional = this.getInstrument(stack);
        return (Integer)optional.map((p_248418_) -> {
            return ((Instrument)p_248418_.value()).useDuration();
        }).orElse(0);
    }

    private Optional<Holder<Instrument>> getInstrument(ItemStack stack) {
        if (stack.hasTag() && stack.getTag().contains("Instrument")) {
            ResourceLocation id = ResourceLocation.tryParse(stack.getTag().getString("Instrument"));
            if (id != null) {
                Optional<Instrument> instrument = BuiltInRegistries.INSTRUMENT.getOptional(id);
                if (instrument.isPresent()) {
                    return BuiltInRegistries.INSTRUMENT.getHolder(ResourceKey.create(Registries.INSTRUMENT, id)).map(holder -> holder);
                }
            }
        }
        Iterator<Holder<Instrument>> iterator = BuiltInRegistries.INSTRUMENT.getTagOrEmpty(this.instruments).iterator();
        return iterator.hasNext() ? Optional.of(iterator.next()) : Optional.empty();
    }

    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.TOOT_HORN;
    }

    private static void play(Level level, Player player, Instrument instrument) {
        SoundEvent soundevent = (SoundEvent)instrument.soundEvent().value();
        float f = instrument.range() / 16.0F;
        level.playSound(player, player, soundevent, SoundSource.RECORDS, f, 0.66F);
        level.playSound(player, player, soundevent, SoundSource.RECORDS, f*0.5f, 1.66F);
        level.playSound(player, player, soundevent, SoundSource.RECORDS, f*0.5f, 0.33F);
        level.gameEvent(GameEvent.INSTRUMENT_PLAY, player.position(), GameEvent.Context.of(player));
    }
}
