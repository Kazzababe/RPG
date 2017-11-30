package ravioli.gravioli.rpg.api.item.util;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.stream.IntStream;

public class InventoryUtils {
    /**
     * Add an item to an inventory whilst ignoring certain slots
     *
     * @param inventory The inventory
     * @param inventorySize Need to specify this in order to ignore custom inventory slots
     * @param itemStack The item to add
     * @param ignore Slots to ignore
     * @return The leftover amount
     */
    public static int addItem(Inventory inventory, int inventorySize, ItemStack itemStack, int... ignore) {
        int amount = itemStack.getAmount();
        int index = inventorySize;

        while ((index--) > 0 && amount > 0) {
            if (ArrayUtils.contains(ignore, index)) {
                System.out.println("Ignore slot : " + index);
                continue;
            }
            ItemStack item = inventory.getItem(index);

            // If there isn't anything in the slot, put it there and return 0
            if (item == null) {
                inventory.setItem(index, itemStack);
                System.out.println("Item was null, just add it");
                return 0;
            }
            if (item.isSimilar(itemStack)) {
                if (amount + item.getAmount() <= item.getMaxStackSize()) {
                    item.setAmount(amount + item.getAmount());
                    inventory.setItem(index, item);
                    System.out.println("Fit fully into itemstack, return 0");
                    return 0;
                }
                int difference = item.getMaxStackSize() - item.getAmount();

                item.setAmount(item.getMaxStackSize());
                inventory.setItem(index, item);
                amount -= difference;
            }
        }

        System.out.println("End of method, return " + amount);
        return amount;
    }
}
