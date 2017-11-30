package ravioli.gravioli.rpg.quest;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import ravioli.gravioli.rpg.RPG;
import ravioli.gravioli.rpg.player.RPGPlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * TODO: Add methods related to player rewards (xp, items, etc)
 */
public abstract class Quest implements Listener, ravioli.gravioli.rpg.api.quest.Quest {
    private RPGPlayer owningPlayer;

    /**
     * A list of strings that is used to display an objective to the player, corresponds to stage
     */
    private HashMap<Integer, String> objectives = new HashMap();

    /**
     * Custom data to be stored in the database. Used to track specific details for the player/quest
     */
    private HashMap<String, Object> data = new HashMap();

    /**
     * A list containing items that will be given to the player as a quest completion reward
     */
    private ArrayList<ItemStack> itemRewards = new ArrayList();

    private int stage;

    public Quest(RPGPlayer player) {
        owningPlayer = player;

        if (player != null) {
            RPG.getInstance().registerListener(this);
        }
    }

    @Override
    public int getId() {
        return QuestManager.REGISTRY.getQuestId(this);
    }

    public ArrayList<ItemStack> getItemRewards() {
        return itemRewards;
    }

    protected void addObjective(int index, String objective) {
        objectives.put(index, objective);
    }

    protected String getObjective(int index) {
        return objectives.get(index);
    }

    public void setData(String key, Object value) {
        data.put(key, value);
    }

    public Object getData(String key) {
        return data.get(key);
    }

    public RPGPlayer getOwningPlayer() {
        return owningPlayer;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        stage = stage;
        owningPlayer.sendMessage(getObjective(stage));
    }

    protected void completeQuest() {
        getOwningPlayer().getQuestManager().completeQuest(this);
    }

    protected void addItemReward(ItemStack... items) {
        itemRewards.addAll(Arrays.asList(items));
    }
}