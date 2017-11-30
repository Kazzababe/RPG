package ravioli.gravioli.rpg.listeners;

import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import ravioli.gravioli.rpg.RPG;
import ravioli.gravioli.rpg.api.item.CustomItemType;
import ravioli.gravioli.rpg.gui.container.SkillsContainer;
import ravioli.gravioli.rpg.item.Item;
import ravioli.gravioli.rpg.item.ItemPotion;
import ravioli.gravioli.rpg.npc.AbstractNPC;
import ravioli.gravioli.rpg.player.RPGPlayer;
import ravioli.gravioli.rpg.skill.BaseSkill;

public class PlayerListeners implements Listener {
    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        RPGPlayer rpgPlayer = RPG.getPlayer(player);

        if (player.getGameMode() == GameMode.CREATIVE) {
            // If you're in creative mode, you're not trying to play the game, ignore this stuff.
            return;
        }

        int slot = event.getNewSlot();
        if (slot > 0 && slot < 8) {
            // Cancel the event if we're using a skill or potion.
            event.setCancelled(true);
        }

        if (slot == 1) {
            // Player moved their cursor to where a potion should be.
            ItemStack itemStack = player.getInventory().getItem(5);
            if (Item.isCustom(itemStack, CustomItemType.POTION)) {
                // The item is a potion, lets use the potion
                ItemPotion potion = Item.parse(itemStack, CustomItemType.POTION);
                potion.use(player);
            }
        } else if (slot > 1 && slot < 9) {
            // Player moved their cursor to a slot where a skill should be.
            BaseSkill skill = rpgPlayer.getSkill(slot - 2);
            if (skill != null) {
                rpgPlayer.sendMessage(skill.getTimeLeft());
                if (!skill.use(rpgPlayer)) {
                    rpgPlayer.sendMessage("Skill is on cooldown for " + ChatColor.BLUE + skill.getTimeLeft() + ChatColor.RESET + " milliseconds");
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        int slot = event.getRawSlot();
        if (slot > 37 && slot < 44) {
            // The player clicked a skill slot, if the skill is off cd, open skill select screen
            event.setCancelled(true);

            SkillsContainer container = new SkillsContainer();
            container.setData("slot", event.getSlot());

            container.open(RPG.getPlayer((Player) event.getWhoClicked()));
        }
    }

    @EventHandler
    public void onEntityKilled(EntityDeathEvent event) {
        LivingEntity deadBoy = event.getEntity();
        if (deadBoy.getKiller() != null) {
            RPGPlayer player = RPG.getPlayer(deadBoy.getKiller());
            if (player.isOnline()) {
                // Player just killed an entity, do something about it

            }
        }

        // Exp should not be dropped as it will mess with our own exp system.
        event.setDroppedExp(0);
    }

    @EventHandler
    public void NPCInteractEvent(NPCRightClickEvent event) {
        // TODO: Make my own npc's rather than use citizens
        AbstractNPC.NPCS.get(event.getNPC().getId()).onClick(RPG.getPlayer(event.getClicker()));
    }
}
