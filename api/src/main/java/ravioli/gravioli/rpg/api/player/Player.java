package ravioli.gravioli.rpg.api.player;

import org.bukkit.World;

import java.util.UUID;

public interface Player {
    UUID getUniqueId();

    String getName();

    int getId();

    org.bukkit.entity.Player getPlayer();

    World getWorld();

    boolean isOnline();
}
