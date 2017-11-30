package ravioli.gravioli.rpg.api.gui;

import java.util.ArrayList;
import java.util.List;

public abstract class SimpleBook implements Book {
    private List<Page> pageList = new ArrayList<Page>();

    protected void addPage(Page page) {
        pageList.add(page);
    }
}
