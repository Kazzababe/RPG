package ravioli.gravioli.rpg.api.item;

public enum CustomItemType {
    ARMOUR(CustomArmour.class),
    POTION(CustomPotion.class),
    WEAPON(CustomWeapon.class);

    private Class<? extends CustomItem> itemClass;

    private CustomItemType(Class<? extends CustomItem> itemClass) {
        this.itemClass = itemClass;
    }

    public Class<? extends CustomItem> getItemClass() {
        return itemClass;
    }
}
