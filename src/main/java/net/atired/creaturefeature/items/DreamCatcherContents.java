package net.atired.creaturefeature.items;

import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public final class DreamCatcherContents implements TooltipComponent {
    public static final DreamCatcherContents EMPTY = new DreamCatcherContents(List.of());
    private final List<ItemStack> items;

    public DreamCatcherContents(List<ItemStack> items) {
        this.items = List.copyOf(items);
    }

    public Iterable<ItemStack> items() {
        return items;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof DreamCatcherContents contents) || items.size() != contents.items.size()) return false;
        for (int i = 0; i < items.size(); i++) {
            if (!ItemStack.matches(items.get(i), contents.items.get(i))) return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 1;
        for (ItemStack stack : items) result = 31 * result + stack.save(new net.minecraft.nbt.CompoundTag()).hashCode();
        return result;
    }
}
