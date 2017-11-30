package ravioli.gravioli.rpg.skill;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import ravioli.gravioli.rpg.api.item.util.ItemBuilder;
import ravioli.gravioli.rpg.api.player.Player;
import ravioli.gravioli.rpg.player.RPGPlayer;

public class Skill001GroundSlam extends BaseSkill {
    @Override
    public void useSkill(Player p) {
        RPGPlayer player = (RPGPlayer) p;

        for (Entity entity : player.getPlayer().getNearbyEntities(5, 5, 2)) {
            if (entity instanceof LivingEntity) {
                ((LivingEntity) entity).damage(2.0, player.getPlayer());
            }
        }
    }

    @Override
    public long getCooldown() {
        return 10000;
    }

    @Override
    public ItemBuilder getItemBuilder() {
        return new ItemBuilder(Material.DIRT)
                .setTitle("Ground Slam")
                .addLines("Does " + ChatColor.RED + "2.0" + ChatColor.RESET + " damage to all surrounding enemies");
    }

    @Override
    public int getId() {
        return 0;
    }
}
