package ravioli.gravioli.rpg.roles;

public enum Memory {
    LUMBERJACK("Lumberjack", "Spent many days as a child cutting down trees for his family, and is expertly " +
            "accustomed in handling axes.",
            "Damage with axes is increased by 15%"),
    HEALER("Born to Heal", "Long nights were spent with your mother as you watched over her ill patients as she performed her work",
            "Your healing abilities heal for 35% more"),
    FIGHTER("Strong Will", "I worked hard for my home town helping the hunters, and doing some random shit that I'll think of later",
            "Your max health is increased by 35%");

    private String name;
    private String lore;
    private String description;

    Memory(String name, String lore, String description) {
        this.name = name;
        this.lore = lore;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getLore() {
        return lore;
    }

    public String getDescription() {
        return description;
    }
}
