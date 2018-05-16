package com.grenoble.miage.metrobilite;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class FavoriAdapter extends RecyclerView.Adapter<FavoriAdapter.FavoriViewHolder> {

    private List<Favori> favs;

    public FavoriAdapter (List<Favori>fav)
    {
        this.favs=fav;
    }

    @Override
    public FavoriViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.view_favori, parent, false);

        return new FavoriViewHolder(view);

    }

    @Override
    public void onBindViewHolder(FavoriViewHolder holder, int position) {
        holder.nameLigne.setText(favs.get(position).getNomLigne());
        holder.nameArret.setText(favs.get(position).getNomArret());
        holder.destination.setText(favs.get(position).getDestination());

    }

    @Override
    public int getItemCount() {
        return favs.size();
    }


    public class FavoriViewHolder extends  RecyclerView.ViewHolder{
        public TextView nameLigne;
        public TextView nameArret;
        public TextView destination;

        public FavoriViewHolder(View itemView) {
            super(itemView);
            nameLigne = (TextView) itemView.findViewById(R.id.ligneFavoriName);
            nameArret = (TextView) itemView.findViewById(R.id.arretFavoriName);
            destination = (TextView) itemView.findViewById(R.id.destinationFavoriName);
        }

    }
}
