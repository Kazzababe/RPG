package ravioli.gravioli.rpg.sql;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import ravioli.gravioli.rpg.RPG;
import ravioli.gravioli.rpg.player.RPGPlayer;

import java.sql.*;
import java.util.UUID;

public class MySQL {
    private final RPG plugin;
    private Connection connection;

    public MySQL(RPG plugin) {
        this.plugin = plugin;

        createTables();
    }

    public synchronized Connection getConnection() throws SQLException {
        if (connection != null) {
            if (connection.isValid(1)) {
                return connection;
            }
            connection.close();
        }
        FileConfiguration config = plugin.getConfig();
        connection = DriverManager.getConnection(
                "jdbc:mysql://" +
                        "localhost:" +
                        3636 +
                        "/rpg",
                "root",
                "password");

        return connection;
    }

    private void createTables() {
            try (PreparedStatement statement = getConnection().prepareStatement(
                    "CREATE TABLE IF NOT EXISTS `players` " +
                    "(`id` MEDIUMINT(8) UNSIGNED NOT NULL AUTO_INCREMENT," +
                    " `name` VARCHAR(16) NOT NULL," +
                    " `uuid` VARCHAR(36) NOT NULL," +
                    " PRIMARY KEY(`id`)," +
                    " UNIQUE(`uuid`))"
            )){
                statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try (PreparedStatement statement = getConnection().prepareStatement(
                    "CREATE TABLE IF NOT EXISTS `stats` " +
                    "(`id` MEDIUMINT(8) UNSIGNED NOT NULL AUTO_INCREMENT," +
                    " `pid` MEDIUMINT(8) UNSIGNED NOT NULL," +
                    " `level` TINYINT UNSIGNED NOT NULL," +
                    " `exp` INT UNSIGNED NOT NULL," +
                    " `maxhealth` MEDIUMINT UNSIGNED NOT NULL," +
                    " `dexterity` SMALLINT NOT NULL," +
                    " `might` SMALLINT NOT NULL," +
                    " `insight` SMALLINT NOT NULL," +
                    " PRIMARY KEY(`id`)," +
                    " UNIQUE(`pid`))"
            )) {
                statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try (PreparedStatement statement = getConnection().prepareStatement(
                    "CREATE TABLE IF NOT EXISTS `skills` " +
                    "(`id` MEDIUMINT(8) UNSIGNED NOT NULL AUTO_INCREMENT," +
                    " `pid` MEDIUMINT(8) UNSIGNED NOT NULL," +
                    " `learned` VARCHAR(255) NOT NULL," +
                    " `equipped` VARCHAR(23) NOT NULL DEFAULT '-1,-1,-1,-1,-1,-1'," +
                    " `memories` VARCHAR(36) NOT NULL," +
                    " PRIMARY KEY(`id`)," +
                    " UNIQUE(`pid`))"
            )) {
                statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public void loadPlayers() {
        try (PreparedStatement statement = getConnection().prepareStatement(
                "SELECT * FROM `players`"
        )) {
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                UUID uniqueId = UUID.fromString(results.getString(3));
                RPG.addPlayer(
                        results.getInt(1),
                        uniqueId,
                        results.getString(2));
                RPG.getPlayer(uniqueId).loadSkills();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePlayer(final UUID uniqueId, final String name) {
        runAsync(() -> {
            RPGPlayer player = RPG.getPlayer(uniqueId);
            if (player == null) {
                // Player doesn't exist, so add them
                try (PreparedStatement statement = getConnection().prepareStatement(
                        "INSERT INTO `players` (`name`, `uuid`)" +
                        "VALUES (?, ?)", new String[] {"id"}
                )) {
                    statement.setString(1, name);
                    statement.setString(2, uniqueId.toString());
                    statement.execute();

                    ResultSet results = statement.getGeneratedKeys();
                    if (results.next()) {
                        Integer id = results.getInt(1);
                        RPG.addPlayer(id, uniqueId, name);

                        try (PreparedStatement statement1 = getConnection().prepareStatement(
                                "INSERT INTO `skills` (`pid`) VALUES (?)"
                        )) {
                            statement1.setInt(1, id);
                            statement1.execute();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                // Player does exist, so update info if need be
                if (player.getName().equals(name)) {
                    player.setName(name);
                    try (PreparedStatement statement = getConnection().prepareStatement(
                            "UPDATE `players` SET `name` = ? WHERE `id` = ?"
                    )) {
                        statement.setString(1, name);
                        statement.setInt(2, player.getId());
                        statement.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void runAsync(Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, runnable);
    }
}
