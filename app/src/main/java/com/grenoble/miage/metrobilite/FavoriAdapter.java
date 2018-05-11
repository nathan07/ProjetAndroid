package com.grenoble.miage.metrobilite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class FavoriAdapter extends ArrayAdapter<Favori> {
    //tweets est la liste des models à afficher
    public FavoriAdapter(Context context, List<Favori> Favoris) {
        super(context, 0, Favoris);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_favori,parent, false);
        }

        FavoriViewHolder viewHolder = (FavoriViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new FavoriViewHolder();
            viewHolder.nameLigne = (TextView) convertView.findViewById(R.id.ligneFavoriName);
            viewHolder.nameArret = (TextView) convertView.findViewById(R.id.arretFavoriName);
            viewHolder.destination = (TextView) convertView.findViewById(R.id.destinationFavoriName);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        Favori favori = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.nameLigne.setText(favori.getNomLigne());
        viewHolder.nameArret.setText(favori.getNomArret());
        viewHolder.destination.setText(favori.getDestination());

        return convertView;
    }

    private class FavoriViewHolder {
        public TextView nameLigne;
        public TextView nameArret;
        public TextView destination;
    }
}
