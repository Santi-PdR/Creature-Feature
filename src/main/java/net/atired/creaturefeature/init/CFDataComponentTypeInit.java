package net.atired.creaturefeature.init;

import net.atired.creaturefeature.items.DreamCatcherContents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ItemStack;

/** Stores the former 1.21 data components in the 1.20.1 ItemStack tag. */
public final class CFDataComponentTypeInit {
    private static final String OPENED_KEY = "CreatureFeatureOpenedMind";
    private static final String CONTENTS_KEY = "CreatureFeatureDreamCatcher";

    private CFDataComponentTypeInit() {}

    public static int getOpened(ItemStack stack) {
        return stack.getOrCreateTag().getInt(OPENED_KEY);
    }

    public static void setOpened(ItemStack stack, int value) {
        stack.getOrCreateTag().putInt(OPENED_KEY, value);
    }

    public static DreamCatcherContents getDreamCatcherContents(ItemStack stack) {
        ListTag serialized = stack.getOrCreateTag().getList(CONTENTS_KEY, 10);
        java.util.List<ItemStack> items = new java.util.ArrayList<>(serialized.size());
        for (int index = 0; index < serialized.size(); index++) {
            items.add(ItemStack.of(serialized.getCompound(index)));
        }
        return new DreamCatcherContents(items);
    }

    public static void setDreamCatcherContents(ItemStack stack, DreamCatcherContents contents) {
        ListTag serialized = new ListTag();
        for (ItemStack item : contents.items()) {
            serialized.add(item.save(new CompoundTag()));
        }
        stack.getOrCreateTag().put(CONTENTS_KEY, serialized);
    }
}
