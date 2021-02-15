package com.example.codingfaction;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.codingfaction.Fragments.FloorsFragment;
import com.example.codingfaction.Models.User;
import com.example.codingfaction.SharedPreference.SharedPreference;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ClassroomActivity extends AppCompatActivity {

    FirebaseAuth auth;
    public String strFloor = "", strRoom = "", exists = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom);
        auth = FirebaseAuth.getInstance();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new FloorsFragment()).commit();

        updateSharedPreference();
    }

    private void updateSharedPreference() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                SharedPreference.setUserEmail(ClassroomActivity.this, user.getEmail());
                SharedPreference.setUserId(ClassroomActivity.this, user.getId());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void addFragment(Fragment fragment, boolean addToBackStack, String tag){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.fragment_container, fragment, tag);
        ft.commitAllowingStateLoss();
    }
}