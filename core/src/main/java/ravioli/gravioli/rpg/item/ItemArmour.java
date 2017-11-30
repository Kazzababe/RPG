package ravioli.gravioli.rpg.item;

import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.minecraft.server.v1_12_R1.NBTTagList;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import ravioli.gravioli.rpg.api.item.CustomItem;
import ravioli.gravioli.rpg.api.item.CustomItemType;
import ravioli.gravioli.rpg.api.item.util.ItemBuilder;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class ItemArmour extends Item {
    protected int requiredLevel;
    protected int itemLevel;

    protected Rarity rarity;
    protected ArrayList<Attribute> attributes = new ArrayList();

    public ItemArmour(Material material) {
        super(material);
    }

    @Override
    public ItemStack setTags(ItemStack itemStack) {
        net.minecraft.server.v1_12_R1.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);

        NBTTagCompound tag = nmsItemStack.getTag();

        tag.setInt("requiredLevel", requiredLevel);
        tag.setInt("itemLevel", itemLevel);
        tag.setString("rarity", rarity.name());

        NBTTagList list = new NBTTagList();
        for (Attribute attribute : attributes) {
            NBTTagCompound compound = new NBTTagCompound();
            compound.setString("type", attribute.getType().name());
            compound.setInt("amount", attribute.getAmount());
            list.add(compound);
        }
        tag.set("attributes", list);

        return CraftItemStack.asBukkitCopy(nmsItemStack);
    }

    @Override
    public CustomItem fetchTags(net.minecraft.server.v1_12_R1.ItemStack itemStack) {
        NBTTagCompound tag = itemStack.getTag();

        requiredLevel = tag.getInt("requiredLevel");
        itemLevel = tag.getInt("itemLevel");
        rarity = Rarity.valueOf(tag.getString("rarity"));

        NBTTagList listCompound = (NBTTagList) tag.get("attributes");
        for (int i = 0; i < listCompound.size(); i++) {
            NBTTagCompound compound = listCompound.get(i);
            Attribute attribute = new Attribute(AttributeType.valueOf(compound.getString("type")), compound.getInt("amount"));
            attributes.add(attribute);
        }

        return this;
    }

    @Override
    public CustomItemType getType() {
        return CustomItemType.ARMOUR;
    }

    @Override
    public ItemStack build() {
        ItemBuilder itemBuilder = getItemBuilder();

        itemBuilder.setTitle(rarity.getColor() + nameGenerator.generateName(this));
        itemBuilder.setLines("Required level " + requiredLevel,
                             "Item Level " + itemLevel);
        for (Attribute attribute : attributes) {
            itemBuilder.addLines("" + attribute.getType().getColor() + attribute.getAmount() + " " + attribute.getType().name());
        }

        return super.build();
    }

    public int getRequiredLevel() {
        return requiredLevel;
    }

    public int getItemLevel() {
        return itemLevel;
    }

    public Rarity getRarity() {
        return rarity;
    }
}
