package ravioli.gravioli.rpg.quest;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class QuestRegistry {
    private BiMap<Integer, Class<? extends Quest>> questsById = HashBiMap.create();

    protected void registerQuest(Integer id, Class<? extends Quest> questClass) {
        questsById.put(id, questClass);
    }

    public int getQuestId(Quest quest) {
        return questsById.inverse().get(quest.getClass());
    }

    public Class<? extends Quest> getQuestById(Integer id) {
        return questsById.get(id);
    }
}
