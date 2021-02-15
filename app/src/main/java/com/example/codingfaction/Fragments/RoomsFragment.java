package com.example.codingfaction.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.codingfaction.Adapters.RoomsAdapter;
import com.example.codingfaction.ClassroomActivity;
import com.example.codingfaction.R;

import java.util.ArrayList;
import java.util.List;

public class RoomsFragment extends Fragment {

    RecyclerView recyclerView;
    List<String> roomsList;
    ImageView back;
    ClassroomActivity classroomActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_rooms, container, false);
        recyclerView = root.findViewById(R.id.recycler_view);
        back = root.findViewById(R.id.back_btn);
        classroomActivity = (ClassroomActivity) getActivity();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        roomsList = new ArrayList<>();

        roomsList.add("G1");
        roomsList.add("G2");
        roomsList.add("G3");
        roomsList.add("G4");
        roomsList.add("G5");

        RoomsAdapter roomsAdapter = new RoomsAdapter(getContext(), roomsList);
        recyclerView.setAdapter(roomsAdapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classroomActivity.onBackPressed();
            }
        });
    }
}