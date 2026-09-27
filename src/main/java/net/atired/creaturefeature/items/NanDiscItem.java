package net.atired.creaturefeature.items;

import net.atired.creaturefeature.client.CreatureFeatureClient;
import net.atired.creaturefeature.init.CFSoundInit;
import net.minecraft.network.chat.Component;
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
        InteractionResult result = super.useOn(context);
        if (context.getLevel().isClientSide()
                && context.getLevel().getBlockState(context.getClickedPos()).getBlock() == Blocks.JUKEBOX
                && result.consumesAction()) {
            CreatureFeatureClient.PROXY.nan_title=1.0f;
            if (context.getPlayer() != null) {
                context.getPlayer().displayClientMessage(Component.empty(), true);
            }
        }
        return result;
    }
}
