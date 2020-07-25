package de.leibmann.nfu;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author 4674215 (Matrikelnummer)
 */
public class Ram {

    public Page[] pages; // Physischer Arbeitsspeicher mit begrenzter Groeße
    public PagingArea pagingArea; // "Festplatte" des Recheners zum Auslagern von Pages

    public Ram(PagingArea pagingArea) {
        this.pagingArea = pagingArea;
        this.pages = new Page[Config.getRamSize()];
    }

    public void write(Page page) {
        for(int i = 0; i < pages.length - 1; i++) {
            if(pages[i] == null) {
                pages[i] = page;
                return;
            }
        }
        Page toReplace = getLeastReferenced();
        pagingArea.add(toReplace);
        replace(toReplace, page);
    }

    public void write(List<Page> pageSet) {
        int i = 0;
        while(i < pages.length && pages[i] == null) {
            pages[i] = pageSet.get(i);
            i++;
        }
        if(pageSet.size() > pages.length) {
            for (int j = i; j < pageSet.size(); j++) {
                pagingArea.add(pageSet.get(j));
            }
        }
    }

    public Page read(char c) {
        Page page = new Page(c);
        for(Page p : pages) {
            if (page.equals(p)) {
                p.addReference();
                return p;
            }
        }
        for(Page paPage : pagingArea.pageList) {
            if (page.equals(paPage)) {
                Page toReplace = getLeastReferenced();
                if (toReplace.getReferences() < paPage.getReferences()) {
                    pagingArea.add(toReplace);
                    paPage.addReference();
                    replace(toReplace, paPage);
                    return paPage;
                }
            }
        }
        return null;
    }

    public void access(char c) {
        for (Page page : pages) {
            if (page.getData() == c) {
                page.addReference();
            }
        }
        if(pagingArea.contains(c)) {
            pagingArea.get(new Page(c)).addReference();
            Page leastReferenced = getLeastReferenced();
            if(leastReferenced.getReferences() < pagingArea.get(new Page(c)).getReferences()) {
                pagingArea.add(leastReferenced);
                replace(leastReferenced, pagingArea.get(new Page(c)));
            }
        }
    }

    public Page getLeastReferenced() {
        Page leastReferenced = pages[0];
        int lowestReferences = leastReferenced.getReferences();
        for(Page page : pages) {
            if (page.getReferences() <= lowestReferences) {
                leastReferenced = page;
                lowestReferences = page.getReferences();
            }
        }
        return leastReferenced;

    }

    private void replace(Page toReplace, Page replaceWith) {
        for(int i = 0; i < pages.length; i++) {
            if(pages[i].equals(toReplace)) {
                pages[i] = replaceWith;
                replaceWith.resetLifetime();
                pagingArea.pageList.remove(replaceWith);
                toReplace.kill();
            }
        }
    }

    /**
     * Überprüft ob der RAM eine Page mit einem bestimmten char enthält
     * Erhöht aus Modellierungsgründen nicht den Zugriffszähler
     * @param c char nach dem gesucht wird
     * @return boolean ob paging area und Speicher eine Page mit dem char enthalten
     */
    public boolean contains(char c) {
        for (Page page : pages) {
            if (page.getData() == c) {
                return true;
            }
        }
        return pagingArea.contains(c);
    }

    public boolean memoryContains(Page page) {
        for(Page p : pages) {
            if(page.equals(p)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Page{" +
                "slots=" + Arrays.toString(pages) +
                '}';
    }
}