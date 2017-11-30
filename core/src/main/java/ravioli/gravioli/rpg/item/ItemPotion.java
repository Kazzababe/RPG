package ravioli.gravioli.rpg.item;

import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ravioli.gravioli.rpg.api.item.CustomItem;
import ravioli.gravioli.rpg.api.item.CustomItemType;

public class ItemPotion extends Item {
    private int potionValue;
    private int cooldown;

    public ItemPotion(int potionValue, int cooldown) {
        super(Material.POTION);

        this.potionValue = potionValue;
        this.cooldown = cooldown;
    }

    @Override
    public ItemStack setTags(ItemStack itemStack) {
        net.minecraft.server.v1_12_R1.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);

        NBTTagCompound tag = nmsItemStack.getTag();
        tag.setInt("potionValue", potionValue);
        tag.setInt("cooldown", cooldown);

        return CraftItemStack.asBukkitCopy(nmsItemStack);
    }

    @Override
    public CustomItem fetchTags(net.minecraft.server.v1_12_R1.ItemStack itemStack) {
        NBTTagCompound tag = itemStack.getTag();

        potionValue = tag.getInt("potionValue");
        cooldown = tag.getInt("cooldown");

        return this;
    }

    @Override
    public CustomItemType getType() {
        return CustomItemType.POTION;
    }

    public void use(Player player) {
        player.sendMessage(potionValue + ":" + cooldown);
    }
}
