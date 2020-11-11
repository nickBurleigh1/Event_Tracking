package com.example.eventtracking;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList event_id, event_name, event_description, event_date;

    CustomAdapter(Activity activity, Context context, ArrayList  event_id, ArrayList event_name, ArrayList event_description, ArrayList event_date){
        this.activity = activity;
        this.context = context;
        this.event_id = event_id;
        this.event_name = event_name;
        this.event_description = event_description;
        this.event_date = event_date;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.event_id.setText(String.valueOf(event_id.get(position)));
        holder.event_name.setText(String.valueOf(event_name.get(position)));
        holder.event_description.setText(String.valueOf(event_description.get(position)));
        holder.event_date.setText(String.valueOf(event_date.get(position)));
        //Recyclerview onClickListener
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(event_id.get(position)));
                intent.putExtra("name", String.valueOf(event_name.get(position)));
                intent.putExtra("description", String.valueOf(event_description.get(position)));
                intent.putExtra("date", String.valueOf(event_date.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });


    }

    @Override
    public int getItemCount() {
        return event_id.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView event_id, event_name, event_description, event_date;
        LinearLayout mainLayout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            event_id = itemView.findViewById(R.id.event_id);
            event_name = itemView.findViewById(R.id.event_title);
            event_description = itemView.findViewById(R.id.event_description);
            event_date = itemView.findViewById(R.id.event_date);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            //Animate Recyclerview
           // Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
           // mainLayout.setAnimation(translate_anim);
        }

    }

}