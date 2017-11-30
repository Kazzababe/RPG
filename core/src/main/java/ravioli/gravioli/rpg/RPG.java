package ravioli.gravioli.rpg;

import net.citizensnpcs.api.CitizensAPI;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandMap;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import ravioli.gravioli.rpg.api.command.BaseCommand;
import ravioli.gravioli.rpg.command.DialogueCommand;
import ravioli.gravioli.rpg.command.TestCommand;
import ravioli.gravioli.rpg.listeners.PlayerListeners;
import ravioli.gravioli.rpg.listeners.RPGListeners;
import ravioli.gravioli.rpg.npc.AbstractNPC;
import ravioli.gravioli.rpg.player.RPGPlayer;
import ravioli.gravioli.rpg.quest.QuestManager;
import ravioli.gravioli.rpg.sql.MySQL;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class RPG extends JavaPlugin {
    private static HashMap<UUID, RPGPlayer> players = new HashMap();
    private static MySQL SQL;

    @Override
    public void onLoad() {
        SQL = new MySQL(this);
        SQL.loadPlayers();
    }

    @Override
    public void onEnable() {
        QuestManager.registerQuests();

        registerCommand(new TestCommand());
        registerCommand(new DialogueCommand());

        registerListener(new PlayerListeners(), new RPGListeners());

        AbstractNPC.NPCS.values().stream().forEach(AbstractNPC::init);
    }

    @Override
    public void onDisable() {
        for (RPGPlayer player : players.values()) {
            try (PreparedStatement statement = SQL.getConnection().prepareStatement(
                    "INSERT INTO `skills` (`pid`, `learned`, `equipped`, `memories`) VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE `equipped` = VALUES(`equipped`)"
            )) {
                statement.setInt(1, player.getId());
                statement.setString(2, "");

                ArrayList<String> skills = new ArrayList();
                for (int i = 0; i < 6; i++) {
                    skills.add(player.getSkill(i) == null? "-1" : player.getSkill(i).getId() + "");
                }
                statement.setString(3, StringUtils.join(skills, ","));
                statement.setString(4, "");
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            SQL.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static MySQL getSQL() {
        return SQL;
    }

    /**
     * Register all events in all the specified listener classes
     *
     * @param listeners Listeners to register
     */
    public void registerListener(Listener... listeners) {
        for (Listener listener : listeners) {
            getServer().getPluginManager().registerEvents(listener, this);
        }
    }

    /**
     * Dynamically register a command
     *
     * @param command The command to register
     */
    public void registerCommand(BaseCommand command) {
        CommandMap commandMap = null;
        try {
            Field field = SimplePluginManager.class.getDeclaredField("commandMap");
            field.setAccessible(true);
            commandMap = (CommandMap) field.get(this.getServer().getPluginManager());
            commandMap.register(this.getName(), command);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static RPGPlayer getPlayer(UUID uniqueId) {
        return players.get(uniqueId);
    }

    public static RPGPlayer getPlayer(Player player) {
        return players.get(player.getUniqueId());
    }

    public static RPGPlayer addPlayer(Integer id, UUID uniqueId, String name) {
        RPGPlayer player = new RPGPlayer(id, uniqueId, name);
        players.put(uniqueId, player);
        return player;
    }

    public static void removePlayer(UUID uniqueId) {
        players.remove(uniqueId);
    }

    public static RPG getInstance() {
        return getPlugin(RPG.class);
    }
}
