package net.atired.creaturefeature;

import net.atired.creaturefeature.accessors.LivingEntityGoopAccessor;
import net.atired.creaturefeature.entity.*;
import net.atired.creaturefeature.init.CFBlockInit;
import net.atired.creaturefeature.init.CFEntityInit;
import net.atired.creaturefeature.init.CFItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.InstrumentTags;
import net.minecraft.world.entity.LivingEntity;

import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.InstrumentItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;

import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.network.PacketDistributor;

@EventBusSubscriber(modid = CreatureFeature.MODID)
public class CFEvents {

    @SubscribeEvent
    public static void onFuelBurnTime(FurnaceFuelBurnTimeEvent event) {
        if (event.getItemStack().is(CFItemInit.SOLAR_SHARD.get())) {
            event.setBurnTime(1600*8);
        }
    }
    public static void buildContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            for (var egg : java.util.List.of(CFItemInit.MINDS_EGG, CFItemInit.BEAUTY_EGG, CFItemInit.SINISTER_EGG,
                    CFItemInit.MACHINATION_EGG, CFItemInit.MINEDFLAYER_EGG, CFItemInit.EEPER_EGG, CFItemInit.BLITZ_EGG,
                    CFItemInit.NOTHING_EGG, CFItemInit.CANARY_EGG, CFItemInit.VERTIGO_EGG, CFItemInit.DREAMWEAVER_EGG,
                    CFItemInit.RUNAWAY_EGG, CFItemInit.FIEND_EGG, CFItemInit.FEND_EGG, CFItemInit.FRIEND_EGG,
                    CFItemInit.TOADSTOOL_EGG, CFItemInit.MOCKINGBIRD_EGG, CFItemInit.PATHOGEN_EGG, CFItemInit.BLOSSOM_EGG,
                    CFItemInit.SAINT_SOLIS_EGG, CFItemInit.DETRITUS_EGG, CFItemInit.STAINED_GLASS_EGG, CFItemInit.COAT_OF_ARMS_EGG)) event.accept(egg.get());
        }
        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            for (var item : java.util.List.of(CFItemInit.THE_PILL, CFItemInit.FIENDISH_SODA, CFItemInit.VERTIGO_CHUNK, CFItemInit.BLIGHTED_BRAIN)) event.accept(item.get());
        }
        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            for (var item : java.util.List.of(CFItemInit.VITRIC_ARROW, CFItemInit.FLINTLOCK, CFItemInit.HEAVY_AXE)) event.accept(item.get());
        }
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            for (var item : java.util.List.of(CFItemInit.BI_SCROLL, CFItemInit.PRIDE_SCROLL, CFItemInit.TRANS_SCROLL, CFItemInit.PAN_SCROLL,
                    CFItemInit.MINEDFLAYER_GOOP, CFItemInit.BLITZ_ROD, CFItemInit.CARAPACE, CFItemInit.DREAM_SILK, CFItemInit.DOWN_FEATHER,
                    CFItemInit.ECTOPLASM, CFItemInit.THINGAMABOB, CFItemInit.FIENDISH_ESSENCE, CFItemInit.COAT_SCRAPS,
                    CFItemInit.LIVING_GLASS_SHARDS, CFItemInit.SLEEPING_POWDER)) event.accept(item.get());
        }
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            for (var item : java.util.List.of(CFItemInit.VERTIGO_HORN, CFItemInit.NOTHING_DISC, CFItemInit.NEW_AGE_NEVERMORE_DISC,
                    CFItemInit.OPEN_MIND, CFItemInit.DREAM_CATCHER, CFItemInit.BACTERIUM_BALL, CFItemInit.BOUQUET,
                    CFItemInit.SOLAR_SHARD, CFItemInit.MURKY_PEARL)) event.accept(item.get());
        }
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            for (var block : java.util.List.of(CFBlockInit.MURKY_PEARL_BLOCK, CFBlockInit.MURKY_PEARL_TILES, CFBlockInit.MURKY_PEARL_TILE_STAIRS,
                    CFBlockInit.DOWN_FEATHERS, CFBlockInit.DOWN_FEATHERS_CARPET, CFBlockInit.MOSAIC_DOWN_FEATHERS,
                    CFBlockInit.MOSAIC_DOWN_FEATHERS_CARPET, CFBlockInit.FIENDISH_TILES, CFBlockInit.BLIND_FIENDISH_TILES,
                    CFBlockInit.CARAPACE_BLOCK, CFBlockInit.CARAPACE_BRICKS, CFBlockInit.CARAPACE_BRICK_STAIRS,
                    CFBlockInit.DREAM_SILK_SPOOL, CFBlockInit.SOLAR_BLOCK, CFBlockInit.SOLAR_BRICKS, CFBlockInit.WALLPAPER)) event.accept(block.get());
        }
        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            for (var block : java.util.List.of(CFBlockInit.DOOHICKEY, CFBlockInit.CARAPACE_BLOCK, CFBlockInit.MINEDFLAYER_JELLY)) event.accept(block.get());
        }
        if (event.getTabKey() == CreativeModeTabs.REDSTONE_BLOCKS) event.accept(CFBlockInit.EEP.get());
    }
    @SubscribeEvent // on the mod event bus
    public static void hurtMinds(AttackEntityEvent event) {
        if(event.getTarget() instanceof MindsEntityPart part){
            event.setCanceled(true);
            event.getEntity().attack(part.getParent());
        }
    }
    public static void spawnPlacements() {
        SpawnPlacements.register(CFEntityInit.EEPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(CFEntityInit.NOTHING.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(CFEntityInit.BLITZ.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(CFEntityInit.MINEDFLAYER.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ((entityType, serverLevelAccessor, mobSpawnType, blockPos, randomSource) -> {return blockPos.getY()>50;}));

        SpawnPlacements.register(CFEntityInit.SINISTER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SinisterEntity::checkSinisterSpawnRules);
        SpawnPlacements.register(CFEntityInit.BEAUTY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BeautyEntity::checkMonsterSpawnRules);
        SpawnPlacements.register(CFEntityInit.MINDS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MindsEntity::checkMonsterSpawnRules);
        SpawnPlacements.register(CFEntityInit.MACHINATION.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MachinationEntity::checkMonsterSpawnRules);

        SpawnPlacements.register(CFEntityInit.VERTIGO.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, VertigoEntity::checkMonsterSpawnRules);
        SpawnPlacements.register(CFEntityInit.CANARY.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CanaryEntity::checkCanarySpawnRules);
        SpawnPlacements.register(CFEntityInit.CANNONBALL_CRAB.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CannonballCrabEntity::checkMonsterSpawnRules);
        SpawnPlacements.register(CFEntityInit.DREAMWEAVER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DreamWeaverEntity::checkMonsterSpawnRules);

        SpawnPlacements.register(CFEntityInit.FIEND.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, FiendEntity::checkMonsterSpawnRules);
        SpawnPlacements.register(CFEntityInit.FRIEND.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, FriendEntity::checkFriendSpawnRules);
        SpawnPlacements.register(CFEntityInit.FEND.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, FendEntity::checkFendSpawnRules);

        SpawnPlacements.register(CFEntityInit.TOADSTOOL.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ToadstoolEntity::checkMonsterSpawnRules);
        SpawnPlacements.register(CFEntityInit.BLOSSOM.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BlossomEntity::checkMonsterSpawnRules);
        SpawnPlacements.register(CFEntityInit.DETRITUS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DetritusEntity::checkDetritusSpawnRules);
        SpawnPlacements.register(CFEntityInit.SAINT_SOLIS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SaintSolisEntity::checkMonsterSpawnRules);
        SpawnPlacements.register(CFEntityInit.COATOFARMS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CoatOfArmsEntity::checkMonsterSpawnRules);
        SpawnPlacements.register(CFEntityInit.STAINED_GLASS.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ((entityType, serverLevelAccessor, mobSpawnType, blockPos, randomSource) -> {return Monster.isDarkEnoughToSpawn(serverLevelAccessor, blockPos, randomSource)&&blockPos.getY()>0&&Math.random()>0.4&&serverLevelAccessor.getHeight(Heightmap.Types.MOTION_BLOCKING,blockPos.getX(),blockPos.getZ())+6>blockPos.getY();}));
        SpawnPlacements.register(CFEntityInit.MOCKINGBIRD.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MockingBirdEntity::checkMonsterSpawnRules);

    }
    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
    }
}
