package com.christian.wallapopsuperhero.ui.presenters;

import android.os.AsyncTask;

public abstract class BasePresenter implements Presenter {
    protected AsyncTask<Void, Void, Void> currentTask;
}
