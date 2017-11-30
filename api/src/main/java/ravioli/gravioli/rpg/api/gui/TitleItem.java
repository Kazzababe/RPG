package ravioli.gravioli.rpg.api.gui;

import org.bukkit.entity.Player;

public class TitleItem {
    private String title;
    private String subTitle;

    public TitleItem(String title, String subTitle) {
        this.title = title;
        this.subTitle = subTitle;
    }
    public void send(Player player) {
        Title message = new Title()
                .setTitle(title)
                .setSubTitle(subTitle)
                .setFadeIn(10)
                .setStay(35)
                .setFadeOut(10);
        message.send(player);
    }
}