package ravioli.gravioli.rpg.gui.container;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import ravioli.gravioli.rpg.RPG;
import ravioli.gravioli.rpg.api.gui.Container;
import ravioli.gravioli.rpg.api.player.Player;
import ravioli.gravioli.rpg.player.RPGPlayer;

import java.util.HashMap;

public abstract class GuiContainer implements Container, Listener {
    protected HashMap<String, Object> data = new HashMap();

    protected RPGPlayer owner;
    protected Inventory inventory;

    @Override
    public void open(Player player) {
        owner = (RPGPlayer) player;
        player.getPlayer().openInventory(inventory = createInventory());
        Bukkit.getPluginManager().registerEvents(this, RPG.getInstance());
    }

    @Override
    public void close() {
        HandlerList.unregisterAll(this);
        owner.getPlayer().closeInventory();
    }

    @Override
    public Inventory createInventory() {
        inventory = Bukkit.createInventory(null,getRows() * 9, getTitle());

        // We want to call the init method right after the inventory is created
        init();

        return inventory;
    }

    public void setData(String key, Object value) {
        data.put(key, value);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getInventory().equals(inventory)) {
            close();
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory().equals(inventory)) {
            event.setCancelled(onClick(event.getCurrentItem(), event.getSlot()));
        }
    }
}
