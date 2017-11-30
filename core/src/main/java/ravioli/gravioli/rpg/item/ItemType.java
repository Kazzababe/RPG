package ravioli.gravioli.rpg.item;

import org.bukkit.Material;
import ravioli.gravioli.rpg.api.item.CustomItemType;

public enum ItemType {
    LEATHER_BOOTS,
    LEATHER_LEGGINGS,
    LEATHER_CHEST,
    LEATHER_HELMET,
    CHAIN_BOOTS,
    CHAIN_LEGGINGS,
    CHAIN_CHEST,
    CHAIN_HELMET,
    IRON_BOOTS,
    IRON_LEGGINGS,
    IRON_CHEST,
    IRON_HELMET,
    GOLD_BOOTS,
    GOLD_LEGGINGS,
    GOLD_CHEST,
    GOLD_HELMET,
    DIAMOND_BOOTS,
    DIAMOND_LEGGINGS,
    DIAMOND_CHEST,
    DIAMOND_HELMET,
    WOOD_SWORD,
    STONE_SWORD,
    IRON_SWORD,
    GOLD_SWORD,
    DIAMOND_SWORD;

    static {
        LEATHER_BOOTS.setMaterial(Material.LEATHER_BOOTS, CustomItemType.ARMOUR);
        LEATHER_LEGGINGS.setMaterial(Material.LEATHER_LEGGINGS, CustomItemType.ARMOUR);
        LEATHER_CHEST.setMaterial(Material.LEATHER_CHESTPLATE, CustomItemType.ARMOUR);
        LEATHER_HELMET.setMaterial(Material.LEATHER_HELMET, CustomItemType.ARMOUR);
        CHAIN_BOOTS.setMaterial(Material.CHAINMAIL_BOOTS, CustomItemType.ARMOUR);
        CHAIN_LEGGINGS.setMaterial(Material.CHAINMAIL_LEGGINGS, CustomItemType.ARMOUR);
        CHAIN_CHEST.setMaterial(Material.CHAINMAIL_CHESTPLATE, CustomItemType.ARMOUR);
        CHAIN_HELMET.setMaterial(Material.CHAINMAIL_HELMET, CustomItemType.ARMOUR);
        IRON_BOOTS.setMaterial(Material.IRON_BOOTS, CustomItemType.ARMOUR);
        IRON_LEGGINGS.setMaterial(Material.IRON_LEGGINGS, CustomItemType.ARMOUR);
        IRON_CHEST.setMaterial(Material.IRON_CHESTPLATE, CustomItemType.ARMOUR);
        IRON_HELMET.setMaterial(Material.IRON_HELMET, CustomItemType.ARMOUR);
        GOLD_BOOTS.setMaterial(Material.GOLD_BOOTS, CustomItemType.ARMOUR);
        GOLD_LEGGINGS.setMaterial(Material.GOLD_LEGGINGS, CustomItemType.ARMOUR);
        GOLD_CHEST.setMaterial(Material.GOLD_CHESTPLATE, CustomItemType.ARMOUR);
        GOLD_HELMET.setMaterial(Material.GOLD_HELMET, CustomItemType.ARMOUR);
        DIAMOND_BOOTS.setMaterial(Material.DIAMOND_BOOTS, CustomItemType.ARMOUR);
        DIAMOND_LEGGINGS.setMaterial(Material.DIAMOND_LEGGINGS, CustomItemType.ARMOUR);
        DIAMOND_CHEST.setMaterial(Material.DIAMOND_CHESTPLATE, CustomItemType.ARMOUR);
        DIAMOND_HELMET.setMaterial(Material.DIAMOND_HELMET, CustomItemType.ARMOUR);
        WOOD_SWORD.setMaterial(Material.WOOD_SWORD, CustomItemType.WEAPON);
        STONE_SWORD.setMaterial(Material.STONE_SWORD, CustomItemType.WEAPON);
        IRON_SWORD.setMaterial(Material.IRON_SWORD, CustomItemType.WEAPON);
        GOLD_SWORD.setMaterial(Material.GOLD_SWORD, CustomItemType.WEAPON);
        DIAMOND_SWORD.setMaterial(Material.DIAMOND_SWORD, CustomItemType.WEAPON);
    }

    private Material material;
    private CustomItemType type;

    private void setMaterial(Material material, CustomItemType type) {
        this.material = material;
        this.type = type;
    }

    public Material getMaterial() {
        return material;
    }

    public CustomItemType getType() {
        return type;
    }
}
