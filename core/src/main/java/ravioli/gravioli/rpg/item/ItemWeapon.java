package ravioli.gravioli.rpg.item;

import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import ravioli.gravioli.rpg.api.item.CustomItem;
import ravioli.gravioli.rpg.api.item.CustomItemType;
import ravioli.gravioli.rpg.api.item.util.ItemBuilder;

@SuppressWarnings("ALL")
public class ItemWeapon extends Item {
    protected int requiredLevel;
    protected int itemLevel;

    private Rarity rarity = Rarity.RANDOM.next();

    // The below numbers represent the damage range of the item
    protected int minimumDamage;
    protected int maximumDamage;

    public ItemWeapon(Material material) {
        super(material);
    }

    @Override
    public ItemStack build() {
        ItemBuilder itemBuilder = getItemBuilder();

        itemBuilder.setTitle(rarity.getColor() + nameGenerator.generateName(this));
        itemBuilder.setLines(minimumDamage + " - " + maximumDamage + " damage",
                "Required level " + requiredLevel,
                "Item Level " + itemLevel);

        return super.build();
    }

    @Override
    public ItemStack setTags(ItemStack itemStack) {
        net.minecraft.server.v1_12_R1.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);

        NBTTagCompound tag = nmsItemStack.getTag();

        tag.setInt("requiredLevel", requiredLevel);
        tag.setInt("itemLevel", itemLevel);
        tag.setInt("minDamage", minimumDamage);
        tag.setInt("maxDamage", maximumDamage);
        tag.setString("rarity", rarity.name());

        return CraftItemStack.asBukkitCopy(nmsItemStack);
    }

    @Override
    public CustomItem fetchTags(net.minecraft.server.v1_12_R1.ItemStack itemStack) {
        NBTTagCompound tag = itemStack.getTag();

        requiredLevel = tag.getInt("requiredLevel");
        itemLevel = tag.getInt("itemLevel");
        minimumDamage = tag.getInt("minDamage");
        maximumDamage = tag.getInt("maxDamage");
        rarity = Rarity.valueOf(tag.getString("rarity"));

        return this;
    }

    @Override
    public CustomItemType getType() {
        return CustomItemType.WEAPON;
    }
}
