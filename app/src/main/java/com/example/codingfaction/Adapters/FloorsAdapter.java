package com.example.codingfaction.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codingfaction.ClassroomActivity;
import com.example.codingfaction.Fragments.RoomsFragment;
import com.example.codingfaction.R;

import java.util.List;

public class FloorsAdapter extends RecyclerView.Adapter<FloorsAdapter.ViewHolder> {

    private final Context mContext;
    private final List<String> floorsList;
    ClassroomActivity classroomActivity;

    public FloorsAdapter(Context mContext, List<String> floorsList) {
        this.mContext = mContext;
        this.floorsList = floorsList;
    }

    @NonNull
    @Override
    public FloorsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.classroom_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FloorsAdapter.ViewHolder holder, int position) {
        classroomActivity = (ClassroomActivity) mContext;
        holder.text.setText(floorsList.get(position));
        holder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classroomActivity.addFragment(new RoomsFragment(), false, "tag");
            }
        });

    }

    @Override
    public int getItemCount() {
        return floorsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.card_txt);
        }
    }
}
