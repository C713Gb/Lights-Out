package com.example.codingfaction;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private Switch aSwitch, bSwitch, cSwitch, allSwitch;
    private DatabaseReference databaseReference, reference;
    private CheckBox checkBox;
    private int lightBulb1 = 0, lightBulb2 = 0, lightBulb3 = 0;
    private Button completedBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aSwitch = findViewById(R.id.switchBtn1);
        bSwitch = findViewById(R.id.switchBtn2);
        cSwitch = findViewById(R.id.switchBtn3);
        allSwitch = findViewById(R.id.switchBtnAll);
        checkBox = findViewById(R.id.allLightsCheckBox);
        completedBtn = findViewById(R.id.completedBtn);

        if (!checkBox.isChecked()) {
            allSwitch.setClickable(false);
        }

        completedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDatabase();
            }
        });

        checkDatabase();
        refresh(1000);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    allSwitch.setClickable(true);
                } else {
                    allSwitch.setClickable(false);
                }
            }
        });

        allSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    turnAllSwitchesOn();
                } else {
                    turnAllSwitchesOff();
                }
            }
        });

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    lightBulb1 = 1;
                } else {
                    lightBulb1 = 0;
                }
            }
        });

        bSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    lightBulb2 = 1;
                } else {
                    lightBulb2 = 0;
                }
            }
        });

        cSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    lightBulb3 = 1;
                } else {
                    lightBulb3 = 0;
                }
            }
        });
    }

    private void refresh(int milliseconds) {

        final Handler handler = new Handler();

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                startActivity(getIntent());
            }
        };

        handler.postDelayed(runnable, milliseconds);

    }

    private void checkDatabase() {

        reference = FirebaseDatabase.getInstance().getReference().child("Lights");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild("Bulb1")) {
                    String s1 = snapshot.child("Bulb1").getValue().toString();

                    if (s1.equals("1") && !aSwitch.isChecked()) {
                        aSwitch.toggle();
                    }
                    if (s1.equals("0") && aSwitch.isChecked()) {
                        aSwitch.toggle();
                    }

                    String s2 = snapshot.child("Bulb2").getValue().toString();
                    if (s2.equals("1") && !bSwitch.isChecked()) {
                        bSwitch.toggle();
                    }
                    if (s2.equals("0") && bSwitch.isChecked()) {
                        bSwitch.toggle();
                    }

                    String s3 = snapshot.child("Bulb3").getValue().toString();
                    if (s3.equals("1") && !cSwitch.isChecked()) {
                        cSwitch.toggle();
                    }
                    if (s3.equals("0") && cSwitch.isChecked()) {
                        cSwitch.toggle();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void updateDatabase() {

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Lights");

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("Bulb1", lightBulb1);
        hashMap.put("Bulb2", lightBulb2);
        hashMap.put("Bulb3", lightBulb3);

        databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "Something went wrong! Please check your Internet connection", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void turnAllSwitchesOff() {

        if (aSwitch.isChecked()) {
            aSwitch.toggle();
        }
        if (bSwitch.isChecked()) {
            bSwitch.toggle();
        }
        if (cSwitch.isChecked()) {
            cSwitch.toggle();
        }

    }

    private void turnAllSwitchesOn() {

        if (!aSwitch.isChecked()) {
            aSwitch.toggle();
        }
        if (!bSwitch.isChecked()) {
            bSwitch.toggle();
        }
        if (!cSwitch.isChecked()) {
            cSwitch.toggle();
        }

    }
}
