package com.christian.wallapopsuperhero.ui;

import com.christian.wallapopsuperhero.data.Comic;

import java.util.ArrayList;

public interface MainView extends BaseView {
    void loadComics(ArrayList<Comic> comics);

    void loadErrorView();
}
