package com.h2m.carassistance;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CarItemsAdapter  extends RecyclerView.Adapter<CarItemsAdapter.ViewHolder>{

    private Context context;
    public List<CarItemsEntry> items;

    Activity activity ;

    public CarItemsAdapter(Context context, List<CarItemsEntry> items) {
        this.context = context;
        this.items = items;
        activity = (Activity) context;
    }

    @Override
    public CarItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_item_car, parent, false);
        return new CarItemsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CarItemsAdapter.ViewHolder holder, final int position) {

        String item_name = items.get(position).getItemName();
        String item_km = items.get(position).getcKilo();
        String item_date = items.get(position).getAddedDate();

        holder.item_name.setText(item_name);
        holder.item_km.setText(item_km);
        holder.item_date.setText(item_date);

        adapterListener(holder, position);

    }

    private void adapterListener(final CarItemsAdapter.ViewHolder holder, final int position) {

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add_data(position);

            }
        });

    }

    public void update_data(List<CarItemsEntry> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView item_name;
        private TextView item_km;
        private TextView item_date;
        private CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            item_name = itemView.findViewById(R.id.item_title);
            item_km = itemView.findViewById(R.id.item_km_value);
            cardView = itemView.findViewById(R.id.card_view);
            item_date = itemView.findViewById(R.id.item_date_value);
        }
    }

}