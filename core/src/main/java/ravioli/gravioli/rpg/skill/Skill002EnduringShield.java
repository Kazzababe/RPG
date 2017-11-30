package ravioli.gravioli.rpg.skill;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import ravioli.gravioli.rpg.api.item.util.ItemBuilder;
import ravioli.gravioli.rpg.api.player.Player;

public class Skill002EnduringShield extends BaseSkill {
    @Override
    public long getCooldown() {
        return 7500;
    }

    @Override
    public ItemBuilder getItemBuilder() {
        return new ItemBuilder(Material.DIRT)
                .setTitle("Enduring Shield")
                .addLines("Grants a shield for 200 damage and if the shield lasts, heal for 200 health");
    }

    @Override
    public int getId() {
        return 1;
    }

    @Override
    public void useSkill(Player player) {

    }
}
