package ravioli.gravioli.rpg.dialogue;

import ravioli.gravioli.rpg.dialogue.stage.DialogueStage;
import ravioli.gravioli.rpg.player.RPGPlayer;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class Dialogue implements Cloneable {
    public static long DIALOGUE_COUNTER = 0;

    protected ArrayList<DialogueStage> stages = new ArrayList();
    protected String prefix;

    private int stage = 0;
    private long uniqueIndex;

    public void start(RPGPlayer player) {
        uniqueIndex = DIALOGUE_COUNTER++;
        next(player);
    }

    public String getPrefix() {
        return prefix;
    }

    public int getStage() {
        return stage;
    }

    public boolean hasNext() {
        return stage < stages.size() - 1;
    }

    public void next(RPGPlayer player) {
        if (stage++ < stages.size()) {
            IntStream.range(0, 20).forEach(i -> {
                player.sendMessage("");
            });
            getCurrentStage().sendMessage(player);
        } else {
            end(player);
        }
    }

    public DialogueStage getCurrentStage() {
        return stages.get(stage);
    }

    public DialogueStage setStage(int stage) {
        return stages.get(stage);
    }

    public void skipTo(RPGPlayer player, int stage) {
        this.stage = stage;
        next(player);
    }

    public void end(RPGPlayer player) {
        player.stopDialogue();
        // Bukkit.getServer().getPluginManager().callEvent(new DialogueFinishEvent(this, player));
    }

    public long getUniqueIndex() {
        return uniqueIndex;
    }

    public void onChatClick(RPGPlayer player, int stage, int messageId) {
        if(this.stage == stage) {
            getCurrentStage().onChatClick(player, messageId);
        }
    }

    @Override
    public Dialogue clone() {
        try {
            Dialogue dialogue = (Dialogue) super.clone();
            for (DialogueStage stage : dialogue.stages) {
                stage.setParent(dialogue);
            }
            return dialogue;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
