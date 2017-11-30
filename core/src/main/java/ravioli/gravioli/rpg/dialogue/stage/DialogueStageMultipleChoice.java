package ravioli.gravioli.rpg.dialogue.stage;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import ravioli.gravioli.rpg.dialogue.Dialogue;
import ravioli.gravioli.rpg.dialogue.DialogueAction;
import ravioli.gravioli.rpg.player.RPGPlayer;

import java.util.ArrayList;

public class DialogueStageMultipleChoice extends DialogueStage {
    private ArrayList<DialogueOption> options = new ArrayList();

    public DialogueStageMultipleChoice(Dialogue parent, String message) {
        super(parent, message);
    }

    public ArrayList<DialogueOption> getOptions() {
        return options;
    }

    @Override
    public void sendMessage(RPGPlayer player) {
        player.sendMessage(ChatColor.BOLD + "" + ChatColor.GOLD + "[" + parent.getPrefix() + "]");
        player.sendMessage(message);
        for (int i = 0; i < options.size(); i++) {
            TextComponent option = new TextComponent(ChatColor.GRAY + "> ");
            TextComponent next = new TextComponent(options.get(i).message);
            next.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,
                    "/dialogue next " +
                            parent.getStage() + " " + i + " " + parent.getUniqueIndex()
            ));
            option.addExtra(next);

            player.getPlayer().spigot().sendMessage(option);
        }
        player.sendMessage("");

        TextComponent message = new TextComponent("[");

        TextComponent end = new TextComponent("END");
        end.setColor(ChatColor.DARK_RED);
        end.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dialogue end"));

        message.addExtra(end);
        message.addExtra("]");

        player.getPlayer().spigot().sendMessage(message);
    }

    @Override
    public void onChatClick(RPGPlayer player, int messageId) {
        options.get(messageId).action.run(player, parent);
    }

    public static class DialogueOption {
        protected String message;
        protected DialogueAction action;

        public DialogueOption(String message, DialogueAction action) {
            this.message = message;
            this.action = action;
        }
    }
}
