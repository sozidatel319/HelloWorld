package com.mikhail.weatherclient.presentation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mikhail.weatherclient.R;

public class DaysOfWeekAdapter extends RecyclerView.Adapter<DaysOfWeekAdapter.ViewHolder> {

    private String[] daysofweek;
    private String[] mintempofweek;
    private String[] maxtempofweek;

    public DaysOfWeekAdapter(String[] daysofweek, String[] mintempofweek, String[] maxtempofweek) {
        this.daysofweek = daysofweek;
        this.mintempofweek = mintempofweek;
        this.maxtempofweek = maxtempofweek;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.dayofweek_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setLabel(daysofweek[position]);
        holder.setMintemp(mintempofweek[position]);
        holder.setMaxtemp(maxtempofweek[position]);

    }

    @Override
    public int getItemCount() {
        return daysofweek.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView label;
        TextView mintemp;
        TextView maxtemp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            label = itemView.findViewById(R.id.label);
            mintemp = itemView.findViewById(R.id.mintemp);
            maxtemp = itemView.findViewById(R.id.maxtemp);

        }

        void setLabel(String text) {
            label.setText(text);
        }

        void setMintemp(String text) {
            mintemp.setText(text);
        }

        void setMaxtemp(String text) {
            maxtemp.setText(text);
        }
    }
}
