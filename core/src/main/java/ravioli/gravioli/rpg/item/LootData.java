package ravioli.gravioli.rpg.item;

public class LootData {
    /**
     * The level of the area the loot was found in.
     */
    private int areaLevel;

    /**
     * The level of the player that the loot is currently dropping for.
     */
    private int playerLevel;

    /**
     * The primary attribute that will* be rolled on the item.
     */
    private AttributeType mainAttribute;

    public LootData(int areaLevel, int playerLevel, AttributeType type) {
        this.areaLevel = areaLevel;
        this.playerLevel = playerLevel;
        mainAttribute = type;
    }

    public int getAreaLevel() {
        return areaLevel;
    }

    public int getPlayerLevel() {
        return playerLevel;
    }

    public AttributeType getMainAttribute() {
        return mainAttribute;
    }
}
