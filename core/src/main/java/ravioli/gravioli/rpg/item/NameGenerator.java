package ravioli.gravioli.rpg.item;

import java.util.Random;

public class NameGenerator {
    private final String[] PREFIXES = {
            "VIOLENT",
            "MIGHTY",
            "AMAZING",
            "TERRIFYING",
            "PROFANE",
            "SACRED"
    };
    private final String[] SUFFIXES = {
            "APPARITION",
            "REAPER"
    };

    public String generateName(Item item) {
        if (item instanceof ItemArmour) {

        } else if (item instanceof ItemWeapon) {

        }
        Random random = new Random();
        return PREFIXES[random.nextInt(PREFIXES.length)] + " " + SUFFIXES[random.nextInt(SUFFIXES.length)];
    }
}
