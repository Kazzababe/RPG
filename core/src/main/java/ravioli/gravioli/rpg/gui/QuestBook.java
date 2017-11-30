package ravioli.gravioli.rpg.gui;

import ravioli.gravioli.rpg.api.gui.SimpleBook;
import ravioli.gravioli.rpg.player.RPGPlayer;

public class QuestBook extends SimpleBook {
    private RPGPlayer owningPlayer;

    public QuestBook(RPGPlayer player) {
        owningPlayer = player;
    }

    @Override
    public String getAuthor() {
        return owningPlayer.getPlayer().getName();
    }

    @Override
    public String getTitle() {
        return "Quest Book";
    }
}
