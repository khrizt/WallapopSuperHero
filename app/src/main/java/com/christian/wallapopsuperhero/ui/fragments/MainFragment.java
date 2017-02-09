package com.christian.wallapopsuperhero.ui.fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.christian.wallapopsuperhero.R;
import com.christian.wallapopsuperhero.data.Comic;
import com.christian.wallapopsuperhero.ui.MainView;
import com.christian.wallapopsuperhero.ui.activities.MainActivity;
import com.christian.wallapopsuperhero.ui.adapters.ComicActionsListener;
import com.christian.wallapopsuperhero.ui.adapters.ComicAdapter;
import com.christian.wallapopsuperhero.ui.presenters.MainPresenter;

import java.util.ArrayList;

public class MainFragment extends BaseFragment implements MainView,
        ComicActionsListener, SwipeRefreshLayout.OnRefreshListener {

    protected MainPresenter presenter;
    protected RecyclerView list;
    protected int arrangeMode = ComicAdapter.MODE_LIST;

    public MainFragment() {
        this.presenter = new MainPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        setHasOptionsMenu(true);

        SwipeRefreshLayout layout = ((SwipeRefreshLayout) rootView.findViewById(R.id.swipable_refresh));
        layout.setOnRefreshListener(this);
        layout.setColorSchemeResources(R.color.colorPrimary);

        list = (RecyclerView) rootView.findViewById(R.id.list);
        if (list.getAdapter() == null) {
            setLayoutManager();
            list.setAdapter(new ComicAdapter());
        }
        ((ComicAdapter) list.getAdapter()).setListener(this);
        ((ComicAdapter) list.getAdapter()).setArrangeMode(arrangeMode);

        list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //check for scroll down
                {
                    int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
                    int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                    int firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager())
                            .findFirstVisibleItemPosition();

                    if (presenter.listScroll(visibleItemCount, totalItemCount, firstVisibleItem)) {
                        ((SwipeRefreshLayout) rootView.findViewById(R.id.swipable_refresh)).setRefreshing(true);
                    }
                }
            }
        });


        this.presenter.setView(this);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        presenter.resume();
    }

    @Override
    public void loadComics(ArrayList<Comic> comics) {
        ((SwipeRefreshLayout) rootView.findViewById(R.id.swipable_refresh)).setRefreshing(false);
        rootView.findViewById(R.id.progress_bar).setVisibility(View.GONE);
        rootView.findViewById(R.id.swipable_refresh).setVisibility(View.VISIBLE);
        ((ComicAdapter) list.getAdapter()).addComics(comics);
        list.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void loadErrorView() {
        ((SwipeRefreshLayout) rootView.findViewById(R.id.swipable_refresh)).setRefreshing(false);
        rootView.findViewById(R.id.progress_bar).setVisibility(View.GONE);
        rootView.findViewById(R.id.empty_layout).setVisibility(View.VISIBLE);
    }

    @Override
    public void onComicClick(Comic c) {
        ((MainActivity) getActivity()).loadComicFragment(c);
    }

    @Override
    public void onRefresh() {
        presenter.clearContent();
        presenter.resume();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.change_view_menu, menu);

        if (arrangeMode == ComicAdapter.MODE_GRID) {
            menu.getItem(0).setIcon(R.drawable.ic_view_list);
        }

        super.onCreateOptionsMenu(menu,inflater);
    }

    private void setLayoutManager() {
        if (arrangeMode == ComicAdapter.MODE_GRID) {
            list.setLayoutManager(new GridLayoutManager(
                    rootView.getContext(),
                    2
            ));
        } else {
            list.setLayoutManager(new LinearLayoutManager(
                    rootView.getContext(),
                    LinearLayoutManager.VERTICAL, false
            ));
        }
    }

    private void switchViewMode() {
        arrangeMode = (arrangeMode == ComicAdapter.MODE_LIST ?
                ComicAdapter.MODE_GRID :
                ComicAdapter.MODE_LIST);
        setLayoutManager();
        ((ComicAdapter) list.getAdapter()).toggleView();
        list.getAdapter().notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.change_view_action) {
            switchViewMode();

            if (((ComicAdapter) list.getAdapter()).getArrangeMode() == ComicAdapter.MODE_GRID) {
                item.setIcon(R.drawable.ic_view_list);
            } else {
                item.setIcon(R.drawable.ic_view_grid);
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
