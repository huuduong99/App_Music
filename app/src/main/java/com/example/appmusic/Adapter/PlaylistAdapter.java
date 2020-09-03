package com.example.appmusic.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appmusic.Model.Playlist;
import com.example.appmusic.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PlaylistAdapter extends ArrayAdapter<Playlist> {
    public PlaylistAdapter(@NonNull Context context, int resource, @NonNull List<Playlist> objects) {
        super(context, resource, objects);
    }

    private class ViewHolder{
        TextView txtTenPlaylist;
        ImageView imgBackground,imgPlaylist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder ;

        if(convertView == null){
            holder =new ViewHolder();
            LayoutInflater inflater=LayoutInflater.from(getContext());
            convertView=inflater.inflate(R.layout.row_playlist,null);

            holder.txtTenPlaylist   =convertView.findViewById(R.id.textViewTenPlaylist);
            holder.imgPlaylist      =convertView.findViewById(R.id.imageViewPlaylist);
            holder.imgBackground    =convertView.findViewById(R.id.imageViewBackgroundPlaylist);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        Playlist playlist = getItem(position);
        Picasso.with(getContext()).load(playlist.getHinhPlaylist()).into(holder.imgBackground);
        Picasso.with(getContext()).load(playlist.getIcon()).into(holder.imgPlaylist);
        holder.txtTenPlaylist.setText(playlist.getTen());

        return convertView;
    }
}
