package ravioli.gravioli.rpg.npc;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import ravioli.gravioli.rpg.player.RPGPlayer;

public class NPC001Blacksmith extends AbstractNPC {
    public NPC001Blacksmith() {
        super("Blacksmith");
    }

    @Override
    public void init() {
        spawn();
    }

    @Override
    public Location getSpawnLocation() {
        return new Location(
                Bukkit.getWorlds().get(0),
                -1239, 72, 315
        );
    }

    @Override
    public int getId() {
        return 1;
    }

    @Override
    public void onClick(RPGPlayer player) {
        player.sendMessage("I'm the blackest smith in all the lands");
    }
}
