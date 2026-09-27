package net.atired.creaturefeature.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.atired.creaturefeature.init.CFBlockInit;
import net.atired.creaturefeature.init.CFBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.checkerframework.checker.units.qual.A;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;


@Mixin(ShearsItem.class)
public class ShearsItemMixin {

    @ModifyReturnValue(method = "mineBlock(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/LivingEntity;)Z",at=@At("RETURN"))
    private boolean mineMyBlock(boolean original, ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entityLiving){
        if(state.is(CFBlockInit.DREAM_SILK_SPOOL.get())||
                state.is(CFBlockInit.DOWN_FEATHERS.get())||state.is(CFBlockInit.DOWN_FEATHERS_CARPET.get())||
                state.is(CFBlockInit.MOSAIC_DOWN_FEATHERS.get())||state.is(CFBlockInit.MOSAIC_DOWN_FEATHERS_CARPET.get())){
            return true;
        }
        return original;
    }
}
