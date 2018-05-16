package com.grenoble.miage.metrobilite;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.grenoble.miage.metrobilite.Persistence.DAOFavori;

import java.util.List;

import static android.R.attr.direction;

public class FavoriAdapter extends RecyclerView.Adapter<FavoriAdapter.FavoriViewHolder> {

    private List<Favori> favs;
    private Context context;

    public FavoriAdapter (List<Favori>fav, Context context)
    {
        this.favs=fav;
        this.context=context;
    }

    @Override
    public FavoriViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.view_favori, parent, false);

        return new FavoriViewHolder(view);

    }

    @Override
    public void onBindViewHolder(FavoriViewHolder holder, final int position) {
        holder.nameLigne.setText(favs.get(position).getNomLigne());
        holder.nameArret.setText(favs.get(position).getNomArret());
        holder.destination.setText(favs.get(position).getDestination());
        //holder.notif.setChecked(Boolean.getBoolean(String.valueOf(favs.get(position).getNotifActive())));
        if(favs.get(position).getNotifActive()==1) {
            holder.notif.setChecked(true);
        }
        else {
            holder.notif.setChecked(false);
        }

        holder.notif.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int notify;
                if(b) {
                    notify = 1;
                }
                else {
                    notify = 0;
                }
                DAOFavori dbase = DAOFavori.getDAOFavori(context);
                favs.get(position).setNotifActive(notify);
                dbase.modifier(favs.get(position),favs.get(position).getId());

            }
        });

    }

    @Override
    public int getItemCount() {
        return favs.size();
    }


    public class FavoriViewHolder extends  RecyclerView.ViewHolder{
        public TextView nameLigne;
        public TextView nameArret;
        public TextView destination;
        public Switch notif;

        public FavoriViewHolder(View itemView) {
            super(itemView);
            nameLigne = (TextView) itemView.findViewById(R.id.ligneFavoriName);
            nameArret = (TextView) itemView.findViewById(R.id.arretFavoriName);
            destination = (TextView) itemView.findViewById(R.id.destinationFavoriName);
            notif= (Switch) itemView.findViewById(R.id.activeNotif);
        }

    }
}
