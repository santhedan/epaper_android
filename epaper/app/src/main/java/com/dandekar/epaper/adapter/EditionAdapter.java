package com.dandekar.epaper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dandekar.epaper.R;
import com.dandekar.epaper.data.EditionDetails;
import com.dandekar.epaper.data.Mirror;
import com.dandekar.epaper.data.Publication;
import com.dandekar.epaper.data.PublicationDetails;
import com.dandekar.epaper.data.TheEconomicTimes;
import com.dandekar.epaper.data.TheTimesOfIndia;
import com.dandekar.epaper.holder.EditionHolder;

public class EditionAdapter extends RecyclerView.Adapter<EditionHolder> {

    private PublicationDetails details = null;
    private Context mContext;
    private View.OnClickListener mListener;

    public EditionAdapter(Context context, String publication, View.OnClickListener listener) {

        this.mContext = context;
        this.mListener = listener;

        if (Publication.TheTimesOfIndia.name().equals(publication)) {
            details = new TheTimesOfIndia();
        } else if (Publication.TheEconomicTimes.name().equals(publication)) {
            details = new TheEconomicTimes();
        } else if (Publication.Mirror.name().equals(publication)) {
            details = new Mirror();
        }

    }

    @NonNull
    @Override
    public EditionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        ImageView v = (ImageView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.edition_cell, parent, false);
        EditionHolder vh = new EditionHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull EditionHolder holder, int position) {
        if (details != null) {
            EditionDetails editionDetails = details.getEditionDetails().get(position);
            int resource = editionDetails.getImageResource();
            holder.editionImageView.setImageDrawable(mContext.getResources().getDrawable(resource));
            holder.editionImageView.setTag(editionDetails);
            holder.editionImageView.setOnClickListener(mListener);
        }
    }

    @Override
    public int getItemCount() {
        if (details == null) {
            return 0;
        } else {
            return details.getEditionDetails().size();
        }
    }
}
