package ravioli.gravioli.rpg.dialogue;

import ravioli.gravioli.rpg.dialogue.stage.DialogueStageMultipleChoice;
import ravioli.gravioli.rpg.dialogue.stage.DialogueStageSingleMessage;

import java.util.Arrays;

public class DialogueBuilder {
    private Dialogue dialogue;

    public DialogueBuilder() {
        dialogue = new Dialogue();
    }

    public DialogueBuilder message(String message) {
        dialogue.stages.add(new DialogueStageSingleMessage(dialogue, message));
        return this;
    }

    public DialogueBuilder message(String message, DialogueAction action) {
        dialogue.stages.add(new DialogueStageSingleMessage(dialogue, message, action));
        return this;
    }

    public DialogueBuilder multipleChoice(String message, DialogueStageMultipleChoice.DialogueOption... options) {
        DialogueStageMultipleChoice stage = new DialogueStageMultipleChoice(dialogue, message);
        stage.getOptions().addAll(Arrays.asList(options));
        dialogue.stages.add(stage);
        return this;
    }

    public DialogueBuilder withPrefix(String prefix) {
        dialogue.prefix = prefix;
        return this;
    }

    public Dialogue getDialogue() {
        return dialogue;
    }
}