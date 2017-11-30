package ravioli.gravioli.rpg.api.gui;

import net.minecraft.server.v1_12_R1.PacketPlayOutTitle;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_12_R1.util.CraftChatMessage;
import org.bukkit.entity.Player;

public class Title {
    private String title;
    private String sub;

    private int fadeIn = 20;
    private int fadeOut = 20;
    private int stay = 60;

    public void send(Player... players) {
        if (this.fadeIn == 0) {
            this.fadeIn = 20;
        }
        if (this.fadeOut == 0) {
            this.fadeOut = 20;
        }
        if (this.stay == 0) {
            this.stay = 60;
        }
        PacketPlayOutTitle packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, CraftChatMessage.fromString(this.title)[0]);
        PacketPlayOutTitle packet2 = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, CraftChatMessage.fromString(this.sub)[0]);
        PacketPlayOutTitle packet3 = new PacketPlayOutTitle(this.fadeIn, this.stay, this.fadeOut);

        for (Player player : players) {
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet3); // send time protocol first
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet2);
        }
    }

    public Title setTitle(String title) {
        this.title = title;
        return this;
    }

    public Title setSubTitle(String subtitle) {
        this.sub = subtitle;
        return this;
    }

    public Title setFadeIn(int fadeIn) {
        this.fadeIn = fadeIn;
        return this;
    }

    public Title setFadeOut(int fadeOut) {
        this.fadeOut = fadeOut;
        return this;
    }

    public Title setStay(int stay) {
        this.stay = stay;
        return this;
    }
}