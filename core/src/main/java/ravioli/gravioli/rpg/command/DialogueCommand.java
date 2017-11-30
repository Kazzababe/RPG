package ravioli.gravioli.rpg.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ravioli.gravioli.rpg.RPG;
import ravioli.gravioli.rpg.api.command.BaseCommand;
import ravioli.gravioli.rpg.dialogue.Dialogue;
import ravioli.gravioli.rpg.player.RPGPlayer;
import ravioli.gravioli.rpg.util.CommonUtils;

public class DialogueCommand extends BaseCommand {
    public DialogueCommand() {
        super("dialogue");
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player) sender;
        RPGPlayer rpgPlayer = RPG.getPlayer(player);

        // The format is /dialogue next [stage] [messageId] [message uniqueIndex]
        if (args.length == 4) {
            if (args[0].equals("next")) {
                if (CommonUtils.isInteger(args[1]) && CommonUtils.isInteger(args[2]) && CommonUtils.isInteger(args[3])) {
                    if (rpgPlayer.isDialogueOpen()) {
                        Dialogue dialogue = rpgPlayer.getDialogue();
                        if (Long.parseLong(args[3]) == dialogue.getUniqueIndex()) {
                            dialogue.onChatClick(rpgPlayer, Integer.parseInt(args[1]), Integer.parseInt(args[2]));
                        }
                    }
                }
            }
        } else if (args.length == 1) {
            if (args[0].equals("end")) {
                if (rpgPlayer.isDialogueOpen()) {
                    rpgPlayer.stopDialogue();
                }
            }
        }

        return true;
    }
}
