package ravioli.gravioli.rpg.dialogue.stage;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import ravioli.gravioli.rpg.dialogue.Dialogue;
import ravioli.gravioli.rpg.dialogue.DialogueAction;
import ravioli.gravioli.rpg.player.RPGPlayer;

public class DialogueStageSingleMessage extends DialogueStage {
    private DialogueAction action;

    public DialogueStageSingleMessage(Dialogue parent, String message, DialogueAction action) {
        super(parent, message);
        this.action = action;
    }

    public DialogueStageSingleMessage(Dialogue parent, String message) {
        super(parent, message);
    }

    @Override
    public void sendMessage(RPGPlayer player) {
        player.sendMessage(ChatColor.BOLD + "" + ChatColor.GOLD + "[" + parent.getPrefix() + "]");
        player.sendMessage(message);

        if (parent.hasNext()) {
            player.sendMessage("");

            TextComponent message = DialogueStage.getDialogueEnd(this);
            player.getPlayer().spigot().sendMessage(message);
        } else {
            parent.end(player);
        }
        if (action != null) {
            action.run(player, parent);
        }
    }

    @Override
    public void onChatClick(RPGPlayer player, int messageId) {
        parent.next(player);
    }
}
