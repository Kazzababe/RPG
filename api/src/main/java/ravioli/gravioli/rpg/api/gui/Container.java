package ravioli.gravioli.rpg.api.gui;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import ravioli.gravioli.rpg.api.player.Player;

public interface Container {
    void init();

    void open(Player player);

    void close();

    boolean onClick(ItemStack item, int slot);

    Inventory createInventory();

    int getRows();

    String getTitle();
}
