package ravioli.gravioli.rpg.npc;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import ravioli.gravioli.rpg.dialogue.Dialogue;
import ravioli.gravioli.rpg.dialogue.DialogueBuilder;
import ravioli.gravioli.rpg.dialogue.stage.DialogueStageMultipleChoice;
import ravioli.gravioli.rpg.player.RPGPlayer;
import ravioli.gravioli.rpg.roles.Memory;

public class NPC000Test extends AbstractNPC {
    private final Dialogue NPC_DIALOGUE = new DialogueBuilder()
            .withPrefix("Generic NPC")
            .message("Something something about how you washed up and don't remember anything recent")
            .multipleChoice("What do you remember?",
                    new DialogueStageMultipleChoice.DialogueOption("I remember helping my mom at the hospital", (p, d) -> {
                        p.getMemories().add(Memory.HEALER);
                        d.end(p);
                    }),
                    new DialogueStageMultipleChoice.DialogueOption("I lived on the streets and stuff", (p, d) -> {
                        p.getMemories().add(Memory.FIGHTER);
                        d.end(p);
                    }))
            .getDialogue();

    public NPC000Test() {
        super("Test");
    }

    @Override
    public void init() {
        spawn();
    }

    @Override
    public Location getSpawnLocation() {
        return new Location(
                Bukkit.getWorlds().get(0),
                -1233, 71, 319
        );
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public void onClick(RPGPlayer player) {
        player.startDialogue(NPC_DIALOGUE);
        /*if (!player.isDialogueOpen() && player.getQuestManager().hasCompletedQuest(0)) {
            player.getQuestManager().startQuest(0);
        }*/
    }
}
