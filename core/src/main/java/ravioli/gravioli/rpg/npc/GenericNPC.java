package ravioli.gravioli.rpg.npc;

import org.bukkit.Location;

public class GenericNPC extends AbstractNPC {
    private Location location;

    public GenericNPC(String name, Location location) {
        super(name);
        this.location = location;

        spawn();
    }

    @Override
    public Location getSpawnLocation() {
        return location;
    }

    @Override
    public int getId() {
        return -1;
    }
}
