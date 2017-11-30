package ravioli.gravioli.rpg.skill;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import ravioli.gravioli.rpg.api.item.util.ItemBuilder;
import ravioli.gravioli.rpg.api.player.Player;
import ravioli.gravioli.rpg.api.skill.Skill;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class BaseSkill implements Skill {
    // This is just to keep track of all the skills in the game
    // More than likely in the future, I'll make this more in-depth so skills can be sorted/organized
    public static final HashMap<Integer, BaseSkill> SKILLS = new HashMap();
    public static final Skill GROUND_SLAM = new Skill001GroundSlam();

    private long useTime = System.currentTimeMillis() - getCooldown();

    public BaseSkill() {
        SKILLS.put(getId(), this);
    }

    @Override
    public boolean use(Player player) {
        if (getTimeLeft() <= 0) {
            useTime = System.currentTimeMillis();
            useSkill(player);
            return true;
        }
        return false;
    }

    @Override
    public long getTimeLeft() {
        return getCooldown() - (System.currentTimeMillis() - useTime);
    }

    public abstract void useSkill(Player player);
}
