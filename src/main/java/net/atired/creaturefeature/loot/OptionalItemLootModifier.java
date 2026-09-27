package net.atired.creaturefeature.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

/** Adds compatibility drops without requiring the optional mod's item registry during datapack loading. */
public final class OptionalItemLootModifier extends LootModifier {
    public static final Codec<OptionalItemLootModifier> CODEC = RecordCodecBuilder.create(instance ->
            codecStart(instance)
                    .and(ResourceLocation.CODEC.fieldOf("item").forGetter(modifier -> modifier.itemId))
                    .and(Codec.FLOAT.fieldOf("chance").forGetter(modifier -> modifier.chance))
                    .and(Codec.FLOAT.fieldOf("base_min").forGetter(modifier -> modifier.baseMin))
                    .and(Codec.FLOAT.fieldOf("base_max").forGetter(modifier -> modifier.baseMax))
                    .and(Codec.FLOAT.fieldOf("looting_min").forGetter(modifier -> modifier.lootingMin))
                    .and(Codec.FLOAT.fieldOf("looting_max").forGetter(modifier -> modifier.lootingMax))
                    .apply(instance, OptionalItemLootModifier::new));

    private final ResourceLocation itemId;
    private final float chance;
    private final float baseMin;
    private final float baseMax;
    private final float lootingMin;
    private final float lootingMax;

    public OptionalItemLootModifier(LootItemCondition[] conditions, ResourceLocation itemId, float chance,
                                    float baseMin, float baseMax, float lootingMin, float lootingMax) {
        super(conditions);
        this.itemId = itemId;
        this.chance = chance;
        this.baseMin = baseMin;
        this.baseMax = baseMax;
        this.lootingMin = lootingMin;
        this.lootingMax = lootingMax;
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        if (!ModList.get().isLoaded(itemId.getNamespace())
                || (chance < 1.0F && context.getRandom().nextFloat() >= chance)) {
            return generatedLoot;
        }

        Item item = ForgeRegistries.ITEMS.getValue(itemId);
        if (item == null || item == Items.AIR) {
            return generatedLoot;
        }

        ItemStack stack = new ItemStack(item);
        stack = SetItemCountFunction.setCount(UniformGenerator.between(baseMin, baseMax)).build().apply(stack, context);
        stack = LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(lootingMin, lootingMax))
                .build().apply(stack, context);
        if (!stack.isEmpty()) {
            generatedLoot.add(stack);
        }
        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
