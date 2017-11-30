package ravioli.gravioli.rpg.item;

import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ravioli.gravioli.rpg.api.item.CustomArmour;
import ravioli.gravioli.rpg.api.item.CustomItem;
import ravioli.gravioli.rpg.api.item.CustomItemType;
import ravioli.gravioli.rpg.api.item.CustomPotion;
import ravioli.gravioli.rpg.api.item.util.ItemBuilder;
import ravioli.gravioli.rpg.util.WeightedRandom;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public abstract class Item implements CustomItem {
    protected static final NameGenerator nameGenerator = new NameGenerator();
    protected static final WeightedRandom<ItemType> DEFAULT_LOOT_TABLE = new WeightedRandom() {{
        Arrays.stream(ItemType.values()).forEach(item -> add(1, item));
    }};

    private ItemBuilder itemBuilder;

    public Item(Material material) {
        itemBuilder = new ItemBuilder(material);
    }

    public ItemBuilder getItemBuilder() {
        return itemBuilder;
    }

    public ItemStack build() {
        net.minecraft.server.v1_12_R1.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemBuilder.build());
        NBTTagCompound tag = nmsItemStack.getTag() == null? new NBTTagCompound() : nmsItemStack.getTag();

        tag.setBoolean("custom", true);
        tag.setString("type", getType().name());

        ItemStack itemStack = CraftItemStack.asBukkitCopy(nmsItemStack);
        return setTags(itemStack);
    }

    /**
     * Determine whether or not an itemstack is also a custom item of the specified type
     *
     * @param itemStack The itemstack to check
     * @param itemType The custom item type to check for
     * @return <code>true</code> if the itemstack is a custom item
     */
    public static boolean isCustom(ItemStack itemStack, CustomItemType itemType) {
        if (itemStack == null) {
            return false;
        }

        net.minecraft.server.v1_12_R1.ItemStack nmsItemStack  = CraftItemStack.asNMSCopy(itemStack);
        if (!nmsItemStack.hasTag()) {
            return false;
        }
        NBTTagCompound tag = nmsItemStack.getTag();
        return tag.hasKey("custom") && (tag.hasKey("type") && tag.getString("type").equals(itemType.name()));
    }

    /**
     * Convert an itemstack representation of a custom item back to a custom item
     *
     * @param itemStack The itemstack to parse
     * @param itemType The custom itemstack type to parse to
     * @return The new custom item
     */
    public static <T extends Item> T parse(ItemStack itemStack, CustomItemType itemType) {
        if (!isCustom(itemStack, itemType)) {
            return null;
        }
        Class itemClass = itemType.getItemClass();

        // Instantiate a new custom item based on the item type provided
        Item customItem = null;
        if (itemClass.isAssignableFrom(CustomPotion.class)) {
            customItem = new ItemPotion(0, 0);
        } else if (itemClass.isAssignableFrom(CustomArmour.class)) {
            customItem = new ItemArmour(itemStack.getType());
        }

        ItemBuilder itemBuilder = customItem.getItemBuilder();
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta.hasDisplayName()) {
            itemBuilder.setTitle(itemMeta.getDisplayName());
        }
        if (itemMeta.hasLore()) {
            itemBuilder.setLines(itemMeta.getLore().toArray(new String[itemMeta.getLore().size()]));
        }

        return (T) customItem.fetchTags(CraftItemStack.asNMSCopy(itemStack));
    }

    public static Item generateItem(LootData lootData, WeightedRandom<ItemType> lootTable) {
        ItemType itemType = lootTable.next();
        Item item = null;
        if (itemType.getType() == CustomItemType.ARMOUR) {
            item = new ItemArmour(itemType.getMaterial());
            ((ItemArmour) item).requiredLevel = lootData.getPlayerLevel();
            ((ItemArmour) item).itemLevel = lootData.getAreaLevel();
            ((ItemArmour) item).rarity = Rarity.RANDOM.next();
        } else if (itemType.getType() == CustomItemType.WEAPON) {
            item = new ItemWeapon(itemType.getMaterial());
            ((ItemWeapon) item).requiredLevel = lootData.getPlayerLevel();
            ((ItemWeapon) item).itemLevel = lootData.getAreaLevel();
            ((ItemWeapon) item).minimumDamage = 100 * lootData.getAreaLevel();
            ((ItemWeapon) item).maximumDamage = 110 * lootData.getAreaLevel();
        }
        return item;
    }

    public static Item generateItem(LootData lootData) {
        return generateItem(lootData, DEFAULT_LOOT_TABLE);
    }
}
