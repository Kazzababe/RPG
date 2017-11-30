package ravioli.gravioli.rpg.api.skill;

import ravioli.gravioli.rpg.api.item.util.ItemBuilder;
import ravioli.gravioli.rpg.api.player.Player;

public interface Skill {
    /**
     * Attempts to use the skill by the specified player
     * @param player the player using the skill
     * @return if the skill was able to be used
     */
    boolean use(Player player);

    /**
     * The interval between when the skill can be used in milliseconds
     * @return the skill's cooldown
     */
    long getCooldown();

    /**
     * Get the current time left on the skill's cooldown in milliseconds
     * @return time until the skill is off cd
     */
    long getTimeLeft();

    /**
     * Get the itembuilder that represents the itemstack for the skill
     * @return the skill's itembuilder
     */
    ItemBuilder getItemBuilder();

    /**
     * Get the id that will represent the skill
     * @return the skill's unique id
     */
    int getId();
}