package ravioli.gravioli.rpg.player;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import ravioli.gravioli.rpg.RPG;
import ravioli.gravioli.rpg.api.gui.TitleItem;
import ravioli.gravioli.rpg.api.item.util.InventoryUtils;
import ravioli.gravioli.rpg.dialogue.Dialogue;
import ravioli.gravioli.rpg.quest.QuestManager;
import ravioli.gravioli.rpg.roles.Memory;
import ravioli.gravioli.rpg.skill.BaseSkill;
import ravioli.gravioli.rpg.sql.MySQL;
import ravioli.gravioli.rpg.util.CommonUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;

public class RPGPlayer implements ravioli.gravioli.rpg.api.player.Player {
    private static final ItemStack FILLER_ITEM = new ItemStack(119, 1);

    private Integer id;
    private UUID uniqueId;
    private String name;

    private int health;
    private int maxHealth;

    private QuestManager questManager;

    private ArrayList<TitleItem> titleQueue = new ArrayList();
    private boolean displayingTitle = false;

    private PlayerExperience experience;
    private int level;

    private Dialogue dialogue;

    private HashSet<Memory> memories = new HashSet();
    private BaseSkill[] skills = new BaseSkill[6];

    private int dexterity;
    private int might;
    private int insight;

    public RPGPlayer(final Integer id, UUID uniqueId, String name) {
        this.id = id;
        this.uniqueId = uniqueId;
        this.name = name;

        questManager = new QuestManager(this);
    }

    public void onJoin() {
        getInventory().setHeldItemSlot(0);
        getPlayer().getAttribute(org.bukkit.attribute.Attribute.GENERIC_ATTACK_SPEED).setBaseValue(16);

        for (int i = 2; i < 8; i++) {
            getInventory().setItem(i, null);
            if (skills[i - 2] != null) {
                getInventory().setItem(i, skills[i - 2].getItemBuilder().build());
            }
        }
    }

    public void loadSkills() {
        try (PreparedStatement statement = RPG.getSQL().getConnection().prepareStatement(
                "SELECT * FROM `skills` WHERE `pid` = ?"
        )) {
            statement.setInt(1, id);

            ResultSet results = statement.executeQuery();
            if (results.next()) {
                String[] skillsString = results.getString(4).split(",");
                for (int i = 0; i < skillsString.length; i++) {
                    String string = skillsString[i];
                    if (CommonUtils.isInteger(string)) {
                        skills[i] = BaseSkill.SKILLS.get(Integer.parseInt(string));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public UUID getUniqueId() {
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        // We only need this to update it when a player logins
        this.name = name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Player getPlayer() {
        return Bukkit.getPlayer(uniqueId);
    }

    @Override
    public World getWorld() {
        return getPlayer().getWorld();
    }

    @Override
    public boolean isOnline() {
        Player player = getPlayer();
        return player != null && player.isOnline();
    }

    public PlayerInventory getInventory() {
        return getPlayer().getInventory();
    }

    public void sendMessage(Object message) {
        if (isOnline()) {
            getPlayer().sendMessage(message.toString());
        }
    }

    public void reset() {
        setHealth(maxHealth);
    }

    /**
     * Add an item to the player's custom setup inventory
     *
     * @param itemStack The item to add
     * @return Leftover amount
     */
    public int addItem(ItemStack itemStack) {
        return InventoryUtils.addItem(getInventory(), 36, itemStack, 0, 1, 2, 3, 4, 5, 6, 7, 8);
    }

    /**
     * Set the current health of the player.
     *
     * @param health Health to assign
     */
    public void setHealth(int health) {
        this.health = Math.min(health, maxHealth);

        // Modify the actual player's hp
        if (isOnline()) {
            if (health <= 0) {
                // The player is dead, manage it
            } else {
                getPlayer().setHealth(Math.round(Math.max(health / maxHealth * 20.0, 1)));
            }
        }
    }

    /**
     * Get the current health of the player.
     *
     * @return Current health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Get the players max health.
     * TODO: Have this reflect the health given by gear/items
     *
     * @return Max health
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * Get the player's quest manager.
     *
     * @return Player's quest manager
     */
    public QuestManager getQuestManager() {
        return questManager;
    }

    public void addExperience(long exp) {
        experience.addExperience(exp);
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    protected void onLevelUp() {
        getWorld().playSound(this.getPlayer().getLocation(), Sound.ITEM_BOTTLE_FILL, 1, 1);
        //if (!this.isDialogueOpen()) {
            this.sendMessage("Congratulations, you have reached level " + level + "!");
        //}
    }

    public void sendTitleMessage(String title, String subTitle) {
        final TitleItem titleItem = new TitleItem(title, subTitle);
        if (!displayingTitle) {
            titleItem.send(getPlayer());
            displayingTitle = true;
            updateTitleQueue();
        } else {
            titleQueue.add(titleItem);
        }
    }
    private void updateTitleQueue() {
        Bukkit.getScheduler().runTaskLater(RPG.getInstance(), () -> {
            if (!titleQueue.isEmpty()) {
                titleQueue.get(0).send(getPlayer());
                titleQueue.remove(0);
                updateTitleQueue();
            } else {
                displayingTitle = false;
            }
        }, 55L);
    }

    /**
     * Gets a set of all the players memories
     *
     * @return The player's memories
     */
    public HashSet<Memory> getMemories() {
        return memories;
    }

    /**
     * Checks whether or not the player has a certain memory unlocked.
     *
     * @param memory The memory to check
     * @return Whether the player has the specified memory
     */
    public boolean hasMemory(Memory memory) {
        return memories.contains(memory);
    }

    public Dialogue getDialogue() {
        return dialogue;
    }

    public void startDialogue(Dialogue dialogue) {
        this.dialogue = dialogue.clone();
        this.dialogue.start(this);
    }

    public void stopDialogue() {
        if (dialogue != null) {
            dialogue = null;
            sendMessage("Dialogue over");
        }
    }

    public boolean isDialogueOpen() {
        return dialogue != null;
    }

    public BaseSkill getSkill(int slot) {
        return skills[slot];
    }

    public void setSkill(int slot, BaseSkill skill) {
        skills[slot] = skill;
        try (PreparedStatement statement = RPG.getSQL().getConnection().prepareStatement(
                "UPDATE `skills` SET `equipped` = ? WHERE `pid` = ?"
        )) {
            ArrayList<String> skills = new ArrayList();
            for (int i = 0; i < 6; i++) {
                skills.add(getSkill(i) == null? "-1" : getSkill(i).getId() + "");
            }
            statement.setString(1, StringUtils.join(skills, ","));
            statement.setInt(2, getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getDexterity() {
        return dexterity;
    }

    public int getMight() {
        return might;
    }

    public int getInsight() {
        return insight;
    }

    /**
     * Gets the total dexterity of the player.
     * Grabs dexterity stats from base stats, gear, buffs, etc.
     *
     * @return Player's total dexterity
     */
    public int getTotalDexterity() {
        return dexterity;
    }

    /**
     * Gets the total might of the player.
     * Grabs might stats from base stats, gear, buffs, etc.
     *
     * @return Player's total might
     */
    public int getTotalMight() {
        return might;
    }

    /**
     * Gets the total insight of the player.
     * Grabs insight stats from base stats, gear, buffs, etc.
     *
     * @return Player's total insight
     */
    public int getTotalInsight() {
        return insight;
    }
}
