package ravioli.gravioli.rpg.api.quest;

public interface Quest {
    /**
     * Get the name of the quest.
     *
     * @return The quest's name
     */
    public String getName();

    /**
     * Get the unique integer id of the quest.
     *
     * @return The quest's id
     */
    public int getId();
}
