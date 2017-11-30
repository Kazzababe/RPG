package ravioli.gravioli.rpg.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import ravioli.gravioli.rpg.RPG;
import ravioli.gravioli.rpg.player.RPGPlayer;

import java.util.UUID;

public class RPGListeners implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        RPGPlayer player = RPG.getPlayer(event.getPlayer());
        player.onJoin();
    }

    @EventHandler
    public void onPlayerLogin(AsyncPlayerPreLoginEvent event) {
        final UUID uniqueId = event.getUniqueId();
        final String name = event.getName();

        if (RPG.getPlayer(uniqueId) == null) {
            RPG.getSQL().updatePlayer(uniqueId, name);
        }
    }
}
