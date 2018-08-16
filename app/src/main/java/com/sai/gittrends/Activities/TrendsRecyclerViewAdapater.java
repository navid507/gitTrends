package com.sai.gittrends.Activities;

/**
 * Created by DANIAL on 25/04/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sai.gittrends.Models.Repository;
import com.sai.gittrends.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TrendsRecyclerViewAdapater extends RecyclerView.Adapter<TrendsRecyclerViewAdapater.ViewHolder> {

    private List<Repository> mValues;
    private Context context;
    private IRecDelegate iDelegate;

    public TrendsRecyclerViewAdapater(List items, Context mc, IRecDelegate del) {
        mValues = items;
        context = mc;
        iDelegate = del;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lele_trend, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        if (holder.mItem instanceof Repository) {
            final Repository cp = (Repository) holder.mItem;
            holder.scoreTV.setText(String.valueOf(cp.getScore()));
            holder.titleTV.setText(cp.getName());

            Picasso.with(context)
                    .load(cp.getOwner().getAvatarUrl())
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_trending_up)
                    .into(holder.logoIV);

        }

        if (position > mValues.size() - 10) {
            iDelegate.onEndScroll(position);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iDelegate.onRepoClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public Object mItem;

        TextView titleTV, scoreTV;
        ImageView logoIV;

        public ViewHolder(View view1) {
            super(view1);
            mView = view1;
            titleTV = view1.findViewById(R.id.lc_name);
            scoreTV = view1.findViewById(R.id.lc_score);
            logoIV = view1.findViewById(R.id.lc_img);
        }

        @Override
        public String toString() {
            return super.toString();
        }


    }

    public interface IRecDelegate {
        void onRepoClicked(int pos);

        void onEndScroll(int pos);
    }
}
