package ravioli.gravioli.rpg.gui.container;

import org.bukkit.inventory.ItemStack;
import ravioli.gravioli.rpg.skill.BaseSkill;

import java.util.ArrayList;

public class SkillsContainer extends GuiContainer {
    private ArrayList<BaseSkill> skills = new ArrayList();

    @Override
    public void init() {
        for (int i = 0; i < BaseSkill.SKILLS.size(); i++) {
            inventory.setItem(i, BaseSkill.SKILLS.get(i).getItemBuilder().build());
            skills.add(BaseSkill.SKILLS.get(i));
        }
    }

    @Override
    public boolean onClick(ItemStack item, int slot) {
        if (item != null) {
            owner.setSkill(slot, skills.get(slot));
            owner.getInventory().setItem((int) data.get("slot"), item);
            close();
        }
        return true;
    }

    @Override
    public int getRows() {
        return 3;
    }

    @Override
    public String getTitle() {
        return "Skills";
    }
}
