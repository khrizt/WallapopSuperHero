package com.christian.wallapopsuperhero.ui.adapters;

import com.christian.wallapopsuperhero.data.Comic;

/**
 * Interface to communicate between the ComicAdapter and the ComicFragment.
 */
public interface ComicActionsListener {
    /**
     * The user has tapped in a comic
     *
     * @param c Comic
     */
    void onComicClick(Comic c);
}
