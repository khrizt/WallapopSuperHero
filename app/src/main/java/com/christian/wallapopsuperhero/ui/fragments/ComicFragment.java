package com.christian.wallapopsuperhero.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.christian.wallapopsuperhero.R;
import com.christian.wallapopsuperhero.data.Comic;
import com.christian.wallapopsuperhero.helpers.RandomNumbers;
import com.squareup.picasso.Picasso;

public class ComicFragment extends BaseFragment {

    private Comic comic;

    public void setComic(Comic comic) {
        this.comic = comic;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_comic, container, false);

        if (!comic.getTitle().isEmpty()) {
            ((TextView) rootView.findViewById(R.id.comic_detail_title)).setText(comic.getTitle());
        } else {
            ((TextView) rootView.findViewById(R.id.comic_detail_title)).setText(R.string.no_title);
        }
        ((TextView) rootView.findViewById(R.id.comic_detail_description)).setText(comic.getDescription());
        if (comic.getUpcCode().isEmpty()) {
            rootView.findViewById(R.id.comic_detail_upc_code).setVisibility(View.GONE);
        } else {
            ((TextView) rootView.findViewById(R.id.comic_detail_upc_code)).setText(comic.getUpcCode());
        }
        if (comic.getPageCount() == 0) {
            rootView.findViewById(R.id.comic_detail_pages).setVisibility(View.GONE);
        } else {
            ((TextView) rootView.findViewById(R.id.comic_detail_pages)).setText(comic.getPageCount() + " pages");
        }

        int size = rootView.getContext().getResources().getDisplayMetrics().widthPixels / 2;

        int numImages = comic.getImages().size();
        if (numImages == 1) {
            rootView.findViewById(R.id.comic_detail_image_2).setVisibility(View.GONE);
            rootView.findViewById(R.id.comic_detail_image_separator).setVisibility(View.GONE);
            Picasso.with(rootView.getContext())
                    .load(comic.getImages().get(0).getUri())
                    .resize(size, 0)
                    .onlyScaleDown()
                    .into(((ImageView) rootView.findViewById(R.id.comic_detail_image_1)));

        } else {
            Picasso.with(rootView.getContext())
                    .load(comic.getImages().get(RandomNumbers.getRandomNumber(0, numImages)).getUri())
                    .resize(size, 0)
                    .onlyScaleDown()
                    .into(((ImageView) rootView.findViewById(R.id.comic_detail_image_1)));

            Picasso.with(rootView.getContext())
                    .load(comic.getImages().get(RandomNumbers.getRandomNumber(0, numImages)).getUri())
                    .resize(size, 0)
                    .onlyScaleDown()
                    .into(((ImageView) rootView.findViewById(R.id.comic_detail_image_2)));
        }
        return rootView;
    }

}
