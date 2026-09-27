package net.atired.creaturefeature.items;

import net.atired.creaturefeature.client.CreatureFeatureClient;
import net.atired.creaturefeature.init.CFSoundInit;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Blocks;

public class NanDiscItem extends RecordItem {
    public NanDiscItem(Properties properties) {
        super(15, CFSoundInit.NEW_AGE_NEVERMORE.get(), properties, 300);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if(context.getLevel().isClientSide()&&context.getLevel().getBlockState(context.getClickedPos()).getBlock()== Blocks.JUKEBOX){
            CreatureFeatureClient.PROXY.nan_title=1.0f;
            return InteractionResult.SUCCESS;
        }
        return super.useOn(context);
    }
}
