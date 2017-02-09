package com.christian.wallapopsuperhero.net;

import com.christian.wallapopsuperhero.data.Comic;

import java.util.ArrayList;

/**
 * Interface to communicate with CapitainAmericaComicParser
 */
public interface CapitainAmericaComicsResults {

    /**
     * Load new results from the parser call
     *
     * @param results ArrayList<Comic>
     *
     * @param totalItems int
     */
    void loadResults(ArrayList<Comic> results, int totalItems);
}
