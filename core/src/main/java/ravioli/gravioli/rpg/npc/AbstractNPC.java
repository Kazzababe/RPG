package ravioli.gravioli.rpg.npc;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import ravioli.gravioli.rpg.player.RPGPlayer;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.UUID;

public abstract class AbstractNPC {
    public static final HashMap<Integer, AbstractNPC> NPCS = new HashMap();
    public static final NPC000Test TEST_NPC = new NPC000Test();
    public static final NPC001Blacksmith BLACKSMITH_NPC = new NPC001Blacksmith();

    private NPC npc;
    private String originalName;

    public AbstractNPC(String name) {
        originalName = name;

        if (getId() >= 0) {
            NPCS.put(getId(), this);
        }


        NPCRegistry registry = CitizensAPI.getNPCRegistry();
        npc = registry.getById(getId());

        if (npc == null) {
            npc = registry.createNPC(EntityType.PLAYER, UUID.randomUUID(), getId(), name);
        }
    }

    protected void spawn() {
        npc.spawn(getSpawnLocation());
    }

    public String getName() {
        return npc.getName();
    }

    public NPC getNpc() {
        return npc;
    }

    public void onClick(RPGPlayer whoClicked) {}
    public void init() {};

    public abstract Location getSpawnLocation();
    public abstract int getId();
}
