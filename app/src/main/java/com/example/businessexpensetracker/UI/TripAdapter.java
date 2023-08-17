package com.example.businessexpensetracker.UI;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.businessexpensetracker.R;
import com.example.businessexpensetracker.entities.Trip;

import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder> {

    class TripViewHolder extends RecyclerView.ViewHolder {
        private final TextView tripItemView;
        private final TextView tripStartDate;

        private TripViewHolder (View itemview) {
            super(itemview);
            tripItemView = itemview.findViewById(R.id.textView2);
            tripStartDate = itemview.findViewById(R.id.start_date);
            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Trip current=mTrips.get(position);

                    Intent intent = new Intent(context, TripDetails.class);

                    intent.putExtra("id", current.getTripID());
                    intent.putExtra("name", current.getTripName());
                    intent.putExtra("budget", current.getBudget());
                    intent.putExtra("lodging", current.getLodging());
                    intent.putExtra("start_date", current.getStartDate());
                    intent.putExtra("end_date", current.getEndDate());
                    context.startActivity(intent);
                }
            });
        }
    }

    private List<Trip> mTrips;
    private final Context context;
    private final LayoutInflater mInflater;

    public TripAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public TripAdapter.TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.trip_list_item, parent, false);
        return new TripViewHolder((itemView));
    }

    @Override
    public void onBindViewHolder(@NonNull TripAdapter.TripViewHolder holder, int position) {
        if(mTrips != null) {
            Trip current = mTrips.get(position);
            String name = current.getTripName();
            holder.tripItemView.setText(name);
            Log.d("TripAdapter", "Binding trip at position " + position + ": " + name);
        }
        else {
            holder.tripItemView.setText("No Trip Name");
            Log.d("TripAdapter", "No trips to bind at position " + position);
        }
    }

    @Override
    public int getItemCount() {
        if (mTrips != null)
            return mTrips.size();
        else
            return 0;
    }

    public void setTrips(List<Trip> trips){
        mTrips = trips;
        Log.d("TripAdapter", "Updated trip count: " + mTrips.size());
        notifyDataSetChanged();
    }

}

