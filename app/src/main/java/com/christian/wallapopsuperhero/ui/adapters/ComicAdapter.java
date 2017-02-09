package com.christian.wallapopsuperhero.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.christian.wallapopsuperhero.R;
import com.christian.wallapopsuperhero.data.Comic;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ComicAdapter extends RecyclerView.Adapter {
    protected ArrayList<Comic> comics = new ArrayList<>();
    protected ComicActionsListener listener;
    protected int arrangeMode = MODE_LIST;

    public static final int MODE_LIST = 1;
    public static final int MODE_GRID = 2;

    public void toggleView() {
        arrangeMode = (arrangeMode == MODE_LIST ? MODE_GRID : MODE_LIST);
    }

    public void setArrangeMode(int arrangeMode) {
        this.arrangeMode = arrangeMode;
    }

    public int getArrangeMode() {
        return arrangeMode;
    }

    public void setListener(ComicActionsListener listener) {
        this.listener = listener;
    }

    public void addComics(ArrayList<Comic> comics) {
        this.comics.addAll(comics);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater l = LayoutInflater.from(parent.getContext());

        View v;
        if (arrangeMode == MODE_GRID) {
            v = l.inflate(R.layout.adapter_comic_grid, parent, false);
        } else {
            v = l.inflate(R.layout.adapter_comic, parent, false);
        }

        return new ComicViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ComicViewHolder) holder).render(comics.get(position));
    }

    @Override
    public int getItemCount() {
        return comics.size();
    }

    private class ComicViewHolder extends RecyclerView.ViewHolder {

        public ComicViewHolder(View itemView) {
            super(itemView);
        }

        public void render(final Comic c) {

            itemView.findViewById(R.id.comic_layout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onComicClick(c);
                    }
                }
            });

            int width = itemView.getContext().getResources().getDisplayMetrics().widthPixels;
            if (arrangeMode == MODE_GRID) {
                width /= 2.05;
            }

            ImageView thumbnail = (ImageView) itemView.findViewById(R.id.comic_thumbnail);
            int height = (int) Math.round(width * 1.52);
            thumbnail.getLayoutParams().height = height;


            Picasso.with(itemView.getContext())
                .load(c.getThumbnail().getUri())
                .resize(width, height)
                .onlyScaleDown()
                .into((thumbnail));

            ((TextView) itemView.findViewById(R.id.comic_title)).setText(c.getTitle());
        }
    }
}
