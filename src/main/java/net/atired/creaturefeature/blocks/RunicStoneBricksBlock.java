package net.atired.creaturefeature.blocks;

import net.atired.creaturefeature.entity.ToadstoolEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

/** The rune block used by Toadstool's thrown stone cluster. */
public class RunicStoneBricksBlock extends Block {
    public RunicStoneBricksBlock(Properties properties) {
        super(properties);
    }

    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock,
                                BlockPos neighborPos, boolean movedByPiston) {
        boolean hasOneChiseledNeighbor = true;
        boolean hasNoChiseledNeighbor = true;
        boolean hasOtherNeighbor = false;

        for (Direction direction : Direction.values()) {
            BlockState adjacent = level.getBlockState(pos.relative(direction));
            if (adjacent.is(Blocks.CHISELED_STONE_BRICKS)) {
                if (!hasNoChiseledNeighbor) {
                    hasOneChiseledNeighbor = false;
                }
                hasNoChiseledNeighbor = false;
            } else if (!adjacent.isAir()) {
                hasOtherNeighbor = true;
            }
        }

        if (hasOtherNeighbor || hasNoChiseledNeighbor || !hasOneChiseledNeighbor) {
            level.destroyBlock(pos, false);
        }
        super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!(entity instanceof ToadstoolEntity)) {
            entity.hurt(entity.damageSources().flyIntoWall(), 4.0F);
        }
        super.entityInside(state, level, pos, entity);
    }
}
