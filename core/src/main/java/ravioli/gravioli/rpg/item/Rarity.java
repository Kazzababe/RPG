package ravioli.gravioli.rpg.item;

import org.bukkit.ChatColor;
import ravioli.gravioli.rpg.util.WeightedRandom;

/**
 * Items of each rarity have a random chance of rolling its max affixes - (max affixes - 2), but a higher chance
 * of rolling towards its max.
 */
public enum Rarity {
    COMMON(ChatColor.GRAY, 50, 2),
    UNCOMMON(ChatColor.WHITE, 30, 3),
    RARE(ChatColor.YELLOW, 15, 4),
    MYTHIC(ChatColor.DARK_PURPLE, 4, 5),
    LEGENDARY(ChatColor.BLUE, 1, 5, 1.25);

    public static WeightedRandom<Rarity> RANDOM = new WeightedRandom();

    static {
        for (Rarity rarity : Rarity.values()) {
            RANDOM.add(rarity.weight, rarity);
        }
    }

    private ChatColor color;
    private int weight;
    private int maxAffixes;
    private double attributeModifier;

    Rarity(ChatColor color, int weight, int maxAffixes) {
        this(color, weight, maxAffixes, 1);
    }

    Rarity(ChatColor color, int weight, int maxAffixes, double attributeModifier) {
        this.color = color;
        this.weight = weight;
        this.maxAffixes = maxAffixes;
        this.attributeModifier = attributeModifier;
    }

    public ChatColor getColor() {
        return color;
    }

    public int getWeight() {
        return weight;
    }

    public int getMaxAffixes() {
        return maxAffixes;
    }

    public double getAttributeModifier() {
        return attributeModifier;
    }
}
