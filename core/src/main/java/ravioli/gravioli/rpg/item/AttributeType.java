package ravioli.gravioli.rpg.item;

import org.bukkit.ChatColor;

public enum AttributeType {
    DEXTERITY(ChatColor.GREEN),
    MIGHT(ChatColor.GOLD),
    INSIGHT(ChatColor.BLUE);

    private ChatColor associatedColor;

    AttributeType(ChatColor color) {
        associatedColor = color;
    }

    public ChatColor getColor() {
        return associatedColor;
    }
}
