package com.example.codingfaction.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codingfaction.ClassroomActivity;
import com.example.codingfaction.Fragments.ClassFragment;
import com.example.codingfaction.R;

import java.util.List;

public class RoomsAdapter extends RecyclerView.Adapter<RoomsAdapter.ViewHolder> {

    private final Context mContext;
    private final List<String> roomsList;
    ClassroomActivity classroomActivity;

    public RoomsAdapter(Context mContext, List<String> roomsList) {
        this.mContext = mContext;
        this.roomsList = roomsList;
    }

    @NonNull
    @Override
    public RoomsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.classroom_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomsAdapter.ViewHolder holder, int position) {

        classroomActivity = (ClassroomActivity) mContext;
        holder.text.setText(roomsList.get(position));
        holder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classroomActivity.addFragment(new ClassFragment(), false, "tag");
            }
        });
    }

    @Override
    public int getItemCount() {
        return roomsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.card_txt);
        }
    }
}
