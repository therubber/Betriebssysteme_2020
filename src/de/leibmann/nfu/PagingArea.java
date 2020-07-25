package de.leibmann.nfu;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 4674215 (Matrikelnummer)
 */
public class PagingArea {

    List<Page> pageList;

    public PagingArea() {
        this.pageList = new ArrayList<>();
    }

    public Page get(int i) {
        return pageList.get(i);
    }

    public void add(Page page) {
        pageList.add(page);
    }

    public int size() {
        return pageList.size();
    }

    public Page get(Page page) {
        return pageList.get(pageList.indexOf(page));
    }

    public boolean contains(char c) {
        return pageList.contains(new Page(c));
    }

    public void clear() {
        pageList.clear();
    }
}
