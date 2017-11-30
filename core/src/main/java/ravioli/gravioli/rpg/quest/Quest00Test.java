package ravioli.gravioli.rpg.quest;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import ravioli.gravioli.rpg.player.RPGPlayer;

public class Quest00Test extends Quest {
    public Quest00Test(RPGPlayer player) {
        super(player);

        addObjective(0, "Collect 10 weeds (leaves)");
        setData("weeds", 0);

        addItemReward(new ItemStack(Material.DIAMOND_SWORD));
    }

    @Override
    public String getName() {
        return "Test Quest";
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (getStage() == 0) {
            if (event.getBlock().getType() == Material.LEAVES) {
                int currentWeeds = (int) getData("weeds");
                setData("weeds", currentWeeds + 1);
                getOwningPlayer().sendMessage("You've got " + currentWeeds + " weeds yo");
                if (currentWeeds + 1 >= 10) {
                    // Quest is done, you've got 10 weeds
                    completeQuest();
                }
            }
        }
    }
}
