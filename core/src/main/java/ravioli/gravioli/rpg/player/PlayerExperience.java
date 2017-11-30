package ravioli.gravioli.rpg.player;

public class PlayerExperience {
    private RPGPlayer player;

    private long minExperience;
    private long maxExperience;
    private long currentExperience;

    public PlayerExperience(RPGPlayer player) {
        this.player = player;

        minExperience = calculateExperience(player.getLevel());
        maxExperience = calculateExperience(player.getLevel() + 1);
        currentExperience = minExperience;
    }

    public void updateExperience() {
        if (currentExperience >= maxExperience) {
            minExperience = maxExperience;
            player.setLevel(player.getLevel() + 1);
            player.onLevelUp();
        } else if (currentExperience < minExperience) {
            currentExperience = minExperience;
        }
        maxExperience = calculateExperience(player.getLevel());

        float exp = currentExperience - minExperience;
        float max = maxExperience - minExperience;

        player.getPlayer().setExp(exp / max);
        player.getPlayer().setLevel(player.getLevel());
    }

    public void addExperience(long experience) {
        currentExperience += experience;
        updateExperience();
    }

    private long calculateExperience(int level) {
        long total = 0;
        for (int i = 1; i < level; i++) {
            total += (long) Math.floor(i + 300 * Math.pow(2, i / 7.0));
        }
        return (long) Math.floor(total / 4.0);
    }
}