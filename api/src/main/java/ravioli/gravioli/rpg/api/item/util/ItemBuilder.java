package ravioli.gravioli.rpg.api.item.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class ItemBuilder {
    private Material material;
    private String title;
    private List<String> lore;

    private int amount;
    private byte data;
    private HashSet<ItemFlag> flags = new HashSet();

    public ItemBuilder(Material material) {
        this(material, 1, 0);
    }

    public ItemBuilder(Material material, int amount) {
        this(material, amount, 0);
    }

    public ItemBuilder(Material material, int amount, int data) {
        this.material = material;
        this.amount = amount;
        this.data = (byte) data;

        lore = new ArrayList();
    }

    public ItemBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public ItemBuilder setLines(String... lines) {
        lore = Arrays.asList(lines);
        return this;
    }

    public ItemBuilder addLines(String... lines) {
        lore.addAll(Arrays.asList(lines));
        return this;
    }

    public ItemBuilder addFlags(ItemFlag... flags) {
        this.flags.addAll(Arrays.asList(flags));
        return this;
    }

    public ItemStack build() {
        ItemStack itemStack = new ItemStack(material, amount, data);

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(title);
        itemMeta.setLore(lore);

        if (!flags.isEmpty()) {
            itemMeta.addItemFlags(flags.toArray(new ItemFlag[flags.size()]));
        }
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }
}
