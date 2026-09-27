package net.atired.creaturefeature.blocks;

import net.atired.creaturefeature.entity.ToadstoolStoneProjectile;
import net.atired.creaturefeature.entity.ToadstoolEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

/** The rune block used by Toadstool's thrown stone cluster. */
public class RunicStoneBricksBlock extends Block {
    public RunicStoneBricksBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if (!(entity instanceof ToadstoolEntity) && !(entity instanceof ToadstoolStoneProjectile)) {
            entity.hurt(level.damageSources().flyIntoWall(), 4.0F);
        }
        super.stepOn(level, pos, state, entity);
    }
}
