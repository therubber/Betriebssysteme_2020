package de.leibmann.nfu;

import java.util.LongSummaryStatistics;

public class Result {

    private Result() {}

    private static int replacements = 0;
    private static LongSummaryStatistics lifetimes = new LongSummaryStatistics();

    public static void addReplacement() {
        replacements++;
    }
    public static void addLifetime(Long lifetime) {
        lifetimes.accept(lifetime);
    }

    public static void showResults() {
        System.out.println("Average Page Lifetime: " + lifetimes.getAverage() + "ms.");
        System.out.println("Replacements: " + replacements);
        System.out.println();
        System.out.println("INFO: Im RAM befindet sich eine Page mit niedrigerem Zugriffszähler als eine andere Page in der PagingArea.");
        System.out.println("Dies hat den Grund, dass diese als letztes Accessed wurde und deshalb in den RAM ausgelagert wurde.");
    }
}
