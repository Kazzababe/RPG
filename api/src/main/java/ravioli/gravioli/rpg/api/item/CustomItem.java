package ravioli.gravioli.rpg.api.item;

import org.bukkit.inventory.ItemStack;
import ravioli.gravioli.rpg.api.item.util.ItemBuilder;

public interface CustomItem {
    /**
     * Modify the NBT data of an item to make it clear it's a custom item and to hold the item's data.
     *
     * @param itemStack The itemstack to modify
     * @return The modified itemstack
     */
    public ItemStack setTags(ItemStack itemStack);

    /**
     * Used to fetch the custom NBT data we apply to an item.
     *
     * @param itemStack the itemstack to grab data from
     * @return The modified custom item
     */
    public CustomItem fetchTags(net.minecraft.server.v1_12_R1.ItemStack itemStack);

    /**
     * Get the itembuilder representation of the itemstack.
     *
     * @return Custom item's item builder
     *
     */
    public ItemBuilder getItemBuilder();

    /**
     * Get the custom item type of the item.
     *
     * @return The custom item type
     */
    public CustomItemType getType();
}
