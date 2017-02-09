package com.christian.wallapopsuperhero.ui.presenters;

import android.support.annotation.NonNull;

import com.christian.wallapopsuperhero.ui.BaseView;

public interface Presenter {
    /**
     * Method that control the lifecycle of the view. It should be called in the view's
     * (Activity or Fragment) onResume() method.
     */
    void resume();

    /**
     * Method that control the lifecycle of the view. It should be called in the view's
     * (Activity or Fragment) onPause() method.
     */
    void pause();

    /**
     * Method that control the lifecycle of the view. It should be called in the view's
     * (Activity or Fragment) onDestroy() method.
     */
    void destroy();

    /**
     * Sets the view (Fragment) to operate from the presenter.
     */
    void setView(@NonNull BaseView view);
}
