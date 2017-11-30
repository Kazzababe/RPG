package ravioli.gravioli.rpg.command;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ravioli.gravioli.rpg.RPG;
import ravioli.gravioli.rpg.api.command.BaseCommand;
import ravioli.gravioli.rpg.item.AttributeType;
import ravioli.gravioli.rpg.item.Item;
import ravioli.gravioli.rpg.item.ItemPotion;
import ravioli.gravioli.rpg.item.LootData;
import ravioli.gravioli.rpg.player.RPGPlayer;
import ravioli.gravioli.rpg.quest.QuestManager;

public class TestCommand extends BaseCommand {
    public TestCommand() {
        super("test");
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player) sender;
        RPGPlayer rpgPlayer = RPG.getPlayer(player);

        if (args[0].equals("potion")) {
            player.getInventory().setItem(
                    5,
                    new ItemPotion(20, 5).build()
            );
        } else if (args[0].equals("quest")) {
            Class questClass = QuestManager.REGISTRY.getQuestById(Integer.parseInt(args[1]));
            if (questClass != null) {
                rpgPlayer.getQuestManager().startQuest(Integer.parseInt(args[1]));
            }
        } else if (args[0].equals("gimme")) {
            int leftover = rpgPlayer.addItem(new ItemStack(Material.DIRT, 16));
            player.getWorld().dropItem(player.getLocation(), new ItemStack(Material.GOLD_BLOCK, leftover));
        } else if (args[0].equals("item")) {
            rpgPlayer.addItem(Item.generateItem(new LootData((int) (Math.random() * 50), (int) (Math.random() * 50), AttributeType.DEXTERITY)).build());
        }

        return true;
    }
}
