package com.christian.wallapopsuperhero.ui.activities;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.christian.wallapopsuperhero.R;
import com.christian.wallapopsuperhero.data.Comic;
import com.christian.wallapopsuperhero.ui.fragments.ComicFragment;
import com.christian.wallapopsuperhero.ui.fragments.MainFragment;

import static android.R.attr.fragment;

public class MainActivity extends AppCompatActivity {

    private String currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            getSupportActionBar().setHomeButtonEnabled(true);
        } catch (NullPointerException e) {
            // do nothing
        }

        loadHomeFragment();
    }

    public void loadHomeFragment() {
        MainFragment fragment = new MainFragment();

        loadFragment(fragment, "home");
    }

    public void loadComicFragment(Comic c) {
        ComicFragment fragment = new ComicFragment();
        fragment.setComic(c);

        loadFragment(fragment, "comic");
    }

    private void loadFragment(Fragment fragment, String tag) {
        currentFragment = tag;

        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                .replace(R.id.contentContainer, fragment, tag)
                .addToBackStack(null)
                .commit();

        if (currentFragment.equals("comic")) {
            try {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            } catch (NullPointerException e) {
                // do nothing
            }
        }
    }

    private void clearHomeButton() {
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        } catch (NullPointerException e) {
            // do nothing
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        clearHomeButton();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            clearHomeButton();
            getSupportFragmentManager().popBackStack();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
