package ravioli.gravioli.rpg.quest;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.bukkit.ChatColor;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import ravioli.gravioli.rpg.player.RPGPlayer;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;

public class QuestManager {
    public static final QuestRegistry REGISTRY = new QuestRegistry();

    /**
     * Register all active quests.
     */
    public static void registerQuests() {
        REGISTRY.registerQuest(0, Quest00Test.class);
    }

    private RPGPlayer owningPlayer;
    private HashMap<Integer, Quest> activeQuests = new HashMap();
    private HashSet<Integer> completedQuests = new HashSet();

    public QuestManager(RPGPlayer player) {
        owningPlayer = player;
    }

    public boolean hasCompletedQuest(Integer questId) {
        return completedQuests.contains(questId);
    }

    public Quest startQuest(Integer questId) {
        Quest quest = null;
        try {
            Class<? extends Quest> questClass = REGISTRY.getQuestById(questId);
            quest = questClass.getConstructor(RPGPlayer.class).newInstance(new Object[] {owningPlayer});
        } catch (InstantiationException |
                 IllegalAccessException |
                 InvocationTargetException |
                 NoSuchMethodException e) {
            e.printStackTrace();
        }
        if (quest != null) {
            activeQuests.put(questId, quest);
            // TODO: Update the quest book
        }
        return quest;
    }

    public void completeQuest(Quest quest) {
        activeQuests.remove(quest.getId());
        completedQuests.add(quest.getId());

        // Unregister the listener
        HandlerList.unregisterAll(quest);

        owningPlayer.sendMessage("Nice job bruther");
        // At this point, we'll probably divvy out rewards

        owningPlayer.sendTitleMessage(ChatColor.GOLD + "QUEST COMPLETED", "");
        for (ItemStack itemStack : quest.getItemRewards()) {
            int leftover = owningPlayer.addItem(itemStack);
            owningPlayer.sendTitleMessage(ChatColor.GREEN + "" + itemStack.getType() + ChatColor.GREEN + " recieved",
                    leftover <= 0? "\u2694" : "Item sent to mailbox");
        }

        // TODO: Update the quest book
    }
}
