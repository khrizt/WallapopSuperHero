package com.christian.wallapopsuperhero.ui.presenters;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.christian.wallapopsuperhero.data.Comic;
import com.christian.wallapopsuperhero.net.CapitainAmericaComicsParser;
import com.christian.wallapopsuperhero.net.CapitainAmericaComicsResults;
import com.christian.wallapopsuperhero.ui.BaseView;
import com.christian.wallapopsuperhero.ui.MainView;

import java.util.ArrayList;

public class MainPresenter extends BasePresenter implements CapitainAmericaComicsResults {
    protected MainView view;
    private ArrayList<Comic> comics = new ArrayList<>();
    private CapitainAmericaComicsParser client;
    private int totalItems;

    public static int SCROLL_LIMIT = 5;

    public void clearContent() {
        comics = new ArrayList<>();
    }

    @Override
    public void resume() {
        if (comics == null || comics.size() == 0) {
            loadComics();
        } else {
            loadResults(comics, totalItems);
        }
    }

    private void loadComics() {
        currentTask = getClient()
                .setListener(this)
                .setOffset(comics.size())
                .execute();
    }

    @Override
    public void pause() {
        if (currentTask != null) {
            currentTask.cancel(true);
        }
    }

    @Override
    public void destroy() {
        if (currentTask != null) {
            currentTask.cancel(true);
        }
    }

    @Override
    public void setView(@NonNull BaseView view) {
        this.view = (MainView) view;
    }

    private CapitainAmericaComicsParser getClient() {
        if (client != null) {
            return client;
        } else {
            return new CapitainAmericaComicsParser();
        }
    }

    public MainPresenter setClient(CapitainAmericaComicsParser client) {
        this.client = client;

        return this;
    }

    public ArrayList<Comic> getComics() {
        return comics;
    }

    public boolean listScroll(int visibleItemCount, int totalItemCount, int firstVisibleItem) {
        if (currentTask == null &&
                (visibleItemCount + firstVisibleItem) >= (totalItemCount - SCROLL_LIMIT) &&
                comics.size() < totalItems) {
            loadComics();

            return true;
        }

        return false;
    }

    @Override
    public void loadResults(ArrayList<Comic> results, int totalItems) {
        currentTask = null;
        comics.addAll(results);
        this.totalItems = totalItems;
        if (results == null) {
            view.loadErrorView();
        } else {
            view.loadComics(results);
        }
    }
}
