package com.example.appmusic.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmusic.Activity.DanhSachBaiHatActivity;
import com.example.appmusic.Model.Playlist;
import com.example.appmusic.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DanhSachCacPlaylistAdapter extends RecyclerView.Adapter<DanhSachCacPlaylistAdapter.ViewHolder> {

    Context context;
    ArrayList<Playlist> mangPlaylist;

    public DanhSachCacPlaylistAdapter(Context context, ArrayList<Playlist> mangPlaylist) {
        this.context = context;
        this.mangPlaylist = mangPlaylist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_danh_sach_cac_playlist,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Playlist playlist = mangPlaylist.get(position);
        holder.txtTenPlaylist.setText(playlist.getTen());
        Picasso.with(context).load(playlist.getHinhPlaylist()).into(holder.imgHinhNen);

    }

    @Override
    public int getItemCount() {
        return mangPlaylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtTenPlaylist;
        ImageView imgHinhNen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgHinhNen = itemView.findViewById(R.id.imageViewDanhSachCacPlaylist);
            txtTenPlaylist = itemView.findViewById(R.id.textViewTenDanhSachCacPlaylist);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DanhSachBaiHatActivity.class);
                    intent.putExtra("itemplaylist",mangPlaylist.get(getPosition()));
                    context.startActivity(intent);
                }
            });

        }
    }
}
