package com.example.national_parks.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.national_parks.ParksFragment;
import com.example.national_parks.R;
import com.example.national_parks.model.Park;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ParkRecyclerViewAdapter extends RecyclerView.Adapter<ParkRecyclerViewAdapter.ViewHolder>
{
    private final List<Park> parkList;

    public ParkRecyclerViewAdapter(List<Park> parkList) {
        this.parkList = parkList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.park_rows, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        Park park = parkList.get(position);

        holder.parkName.setText(park.getName());
        holder.parkType.setText(park.getDesignation());
        holder.parkState.setText(park.getStates());

        if (park.getImages().size() > 0) {
            // if there's at least 1 image, get the URL of the image and add it to target
            // add a placeholder when the image is being downloaded
            Picasso.get()
                    .load(park.getImages().get(0).getUrl())
                    .placeholder(android.R.drawable.stat_sys_download)
                    .error(android.R.drawable.stat_notify_error)
                    .resize(100, 100)
                    .centerCrop()
                    .into(holder.parkImage);
        }
    }

    @Override
    public int getItemCount()
    {
        return parkList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView parkImage;
        public TextView parkName;
        public TextView parkType;
        public TextView parkState;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            parkImage = itemView.findViewById(R.id.row_park_imageview);
            parkName = itemView.findViewById(R.id.row_park_name);
            parkType = itemView.findViewById(R.id.row_park_type);
            parkState = itemView.findViewById(R.id.row_park_state);
        }

    }

}
