package com.example.codingfaction.Adapters;

import android.app.ProgressDialog;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class RoomsAdapter extends RecyclerView.Adapter<RoomsAdapter.ViewHolder> {

    private final Context mContext;
    private final List<String> roomsList;
    ClassroomActivity classroomActivity;
    ProgressDialog progressDialog;

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
    public void onBindViewHolder(@NonNull RoomsAdapter.ViewHolder holder, final int position) {

        classroomActivity = (ClassroomActivity) mContext;
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        holder.text.setText(roomsList.get(position));
        holder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classroomActivity.strRoom = roomsList.get(position);
                progressDialog.show();

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Classes");
                reference
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.hasChild(classroomActivity.strFloor)){
                                    if (snapshot
                                            .child(classroomActivity.strFloor)
                                            .hasChild(classroomActivity.strRoom)){
                                        classroomActivity.exists = "true";
                                    } else {
                                        classroomActivity.exists = "false";
                                    }
                                } else {
                                    classroomActivity.exists = "false";
                                }
                                progressDialog.dismiss();
                                classroomActivity.addFragment(new ClassFragment(), false, "tag");
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


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
