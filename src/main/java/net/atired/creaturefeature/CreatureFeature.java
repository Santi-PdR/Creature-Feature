package net.atired.creaturefeature;

import net.atired.creaturefeature.entity.*;
import net.atired.creaturefeature.init.*;
import net.atired.creaturefeature.misc.IcoSphere;
import net.atired.creaturefeature.networking.CFNetwork;
import net.atired.creaturefeature.networking.payloads.VelSyncPayload;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;


import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;

import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(CreatureFeature.MODID)
public class CreatureFeature {
    public static final String MODID = "creaturefeature";
    public static IcoSphere ICO = IcoSphere.MakeIcosphere(2);
    public CreatureFeature() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        CFNetwork.registerMessages();
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::createEntityAttributes);
        modEventBus.addListener(CFEvents::buildContents);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SPEC2);
        CFRecipeSerialisers.RECIPES.register(modEventBus);

        CFSoundInit.SOUND_EVENTS.register(modEventBus);
        CFParticleInit.PARTICLE_TYPES.register(modEventBus);
        CFEntityInit.ENTITIES.register(modEventBus);
        CFMobEffectInit.MOB_EFFECTS.register(modEventBus);
        CFItemInit.ITEMS.register(modEventBus);
        CFBlockInit.BLOCKS.register(modEventBus);
        CFBlockEntityInit.BLOCK_ENTITY_TYPES.register(modEventBus);

    }
    public static ResourceLocation getId(String string){
        return new ResourceLocation(MODID,string);
    }
    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            CFEvents.spawnPlacements();
            CFAchievements.register();
        });
    }

    public void createEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(CFEntityInit.MINDS.get(), MindsEntity.createMindsAttributes().build());
        event.put(CFEntityInit.MACHINATION.get(), MachinationEntity.createMachinationAttributes().build());
        event.put(CFEntityInit.SINISTER.get(), SinisterEntity.createSinisterAttributes().build());
        event.put(CFEntityInit.BEAUTY.get(), BeautyEntity.createBeautyAttributes().build());
        event.put(CFEntityInit.BLITZ.get(), BlitzEntity.createBlitzAttributes().build());
        event.put(CFEntityInit.EEPER.get(), EeperEntity.createEeperAttributes().build());
        event.put(CFEntityInit.MINEDFLAYER.get(), MinedFlayerEntity.createFlayerAttributes().build());
        event.put(CFEntityInit.NOTHING.get(), NoThingEntity.createNothingAttributes().build());
        event.put(CFEntityInit.CANARY.get(), CanaryEntity.createCanaryAttributes().build());
        event.put(CFEntityInit.CANARY_PART.get(), CanaryPart.createCanaryAttributes().build());
        event.put(CFEntityInit.VERTIGO.get(), VertigoEntity.createVertigoAttributes().build());
        event.put(CFEntityInit.CANNONBALL_CRAB.get(), CannonballCrabEntity.createCrabAttributes().build());
        event.put(CFEntityInit.DREAMWEAVER.get(), DreamWeaverEntity.createWeaverAttributes().build());
        event.put(CFEntityInit.FIEND.get(), FiendEntity.createFiendAttributes().build());
        event.put(CFEntityInit.FEND.get(), FendEntity.createFendAttributes().build());
        event.put(CFEntityInit.FRIEND.get(), FriendEntity.createFriendAttributes().build());
        event.put(CFEntityInit.TOADSTOOL.get(), ToadstoolEntity.createToadstoolAttributes().build());
        event.put(CFEntityInit.MOCKINGBIRD.get(), MockingBirdEntity.createMockingBirdAttributes().build());
        event.put(CFEntityInit.PATHOGEN.get(), PathogenesisEntity.createPathogenAttributes().build());
        event.put(CFEntityInit.BLOSSOM.get(), BlossomEntity.createAttributes().build());
        event.put(CFEntityInit.SAINT_SOLIS.get(), SaintSolisEntity.createSaintSolisAttributes().build());
        event.put(CFEntityInit.DETRITUS.get(), DetritusEntity.createDetritusAttributes().build());
        event.put(CFEntityInit.STAINED_GLASS.get(), StainedGlassEntity.createStainedGlassAttributes().build());
        event.put(CFEntityInit.COATOFARMS.get(), CoatOfArmsEntity.createCoatOfArmsAttributes().build());
    }

}
