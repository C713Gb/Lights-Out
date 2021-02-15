package com.example.codingfaction.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.codingfaction.ClassroomActivity;
import com.example.codingfaction.Models.Class;
import com.example.codingfaction.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ClassFragment extends Fragment {

    Button save;
    ImageView back;
    LinearLayout manualLayout, autoLayout, lightLayout;
    TextView manualTxt, autoTxt;
    ProgressDialog progressDialog;
    String mode = "";
    ClassroomActivity classroomActivity;
    CheckBox fan1, fan2, fan3, fan4, light1, light2;
    DatabaseReference databaseReference;
    String strfan1 = "false", strfan2 = "false", strfan3 = "false"
            , strfan4 = "false", strlight1 = "false", strlight2 = "false";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_class, container, false);
        save = root.findViewById(R.id.save_btn);
        back = root.findViewById(R.id.back_btn);
        manualLayout = root.findViewById(R.id.manual_layout);
        autoLayout = root.findViewById(R.id.auto_layout);
        lightLayout = root.findViewById(R.id.light_layout_1);
        manualTxt = root.findViewById(R.id.manual_txt);
        autoTxt = root.findViewById(R.id.auto_txt);
        fan1 = root.findViewById(R.id.fan1_check);
        fan2 = root.findViewById(R.id.fan2_check);
        fan3 = root.findViewById(R.id.fan3_check);
        fan4 = root.findViewById(R.id.fan4_check);
        light1 = root.findViewById(R.id.light1_check);
        light2 = root.findViewById(R.id.light2_check);
        classroomActivity = (ClassroomActivity) getActivity();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                classroomActivity.onBackPressed();
            }
        });

        if (classroomActivity.exists.equals("true")){
            updatePage();
        } else {
            updateManual();
        }

        manualTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateManual();
            }
        });

        manualLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateManual();
            }
        });

        autoTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAuto();
            }
        });

        autoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAuto();
            }
        });

        light1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mode.equals("manual")){
                    if (light1.isChecked()){
                        strlight1 = "true";
                        light1.setChecked(true);
                    } else {
                        strlight1 = "false";
                        light1.setChecked(false);
                    }
                } else {
                    if (light1.isChecked()){
                        light1.setChecked(false);
                    } else {
                        light1.setChecked(true);
                    }
                }
            }
        });

        light2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mode.equals("manual")){
                    if (light2.isChecked()){
                        strlight2 = "true";
                        light2.setChecked(true);
                    } else {
                        strlight2 = "false";
                        light2.setChecked(false);
                    }
                } else {
                    if (light2.isChecked()){
                        light2.setChecked(false);
                    } else {
                        light2.setChecked(true);
                    }
                }
            }
        });

        fan1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) strfan1 = "true";
                else strfan1 = "false";
            }
        });

        fan2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) strfan2 = "true";
                else strfan2 = "false";
            }
        });

        fan3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) strfan3 = "true";
                else strfan3 = "false";
            }
        });

        fan4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) strfan4 = "true";
                else strfan4 = "false";
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDatabase();
            }
        });

    }

    private void updatePage() {
        progressDialog.setMessage("Updating...");
        progressDialog.show();

        databaseReference = FirebaseDatabase.getInstance().getReference("Classes");
        databaseReference
                .child(classroomActivity.strFloor)
                .child(classroomActivity.strRoom)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Class classObject = snapshot.getValue(Class.class);
                        String strMode = classObject.getMode();
                        if (strMode.equals("true")){
                            updateAuto();
                        } else {
                            updateManual();
                        }

                        String sfan1 = classObject.getFan1();
                        String sfan2 = classObject.getFan2();
                        String sfan3 = classObject.getFan3();
                        String sfan4 = classObject.getFan4();
                        String slight1 = classObject.getLight1();
                        String slight2 = classObject.getLight2();

                        if (sfan1.equals("true")) fan1.setChecked(true);
                        if (sfan2.equals("true")) fan2.setChecked(true);
                        if (sfan3.equals("true")) fan3.setChecked(true);
                        if (sfan4.equals("true")) fan4.setChecked(true);
                        if (slight1.equals("true")) light1.setChecked(true);
                        if (slight2.equals("true")) light2.setChecked(true);
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void updateDatabase() {
        progressDialog.setMessage("Saving...");
        progressDialog.show();
        String strmode = "false";

        if (mode.equals("manual")){
            strmode = "false";
        } else {
            strmode = "true";
        }

        Class classObject = new Class(strfan1, strfan2, strfan3, strfan4, strlight1, strlight2, strmode);

        databaseReference = FirebaseDatabase.getInstance().getReference("Classes");
        databaseReference
                .child(classroomActivity.strFloor)
                .child(classroomActivity.strRoom)
                .setValue(classObject)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(classroomActivity, "Updated successfully", Toast.LENGTH_SHORT).show();
                    classroomActivity.onBackPressed();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(classroomActivity, "Failed : "+task.getException().getMessage()
                            , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateManual(){
        mode = "manual";

        manualLayout.setBackground(classroomActivity.getResources().getDrawable(R.drawable.manual_background));
        autoLayout.setBackground(classroomActivity.getResources().getDrawable(R.drawable.auto_background_not_selected));
        manualTxt.setTextColor(classroomActivity.getResources().getColor(R.color.white));
        autoTxt.setTextColor(classroomActivity.getResources().getColor(R.color.colorPrimary));

        enableLights();
    }


    private void updateAuto(){
        mode = "auto";

        autoLayout.setBackground(classroomActivity.getResources().getDrawable(R.drawable.auto_background));
        manualLayout.setBackground(classroomActivity.getResources().getDrawable(R.drawable.manual_background_not_selected));
        autoTxt.setTextColor(classroomActivity.getResources().getColor(R.color.white));
        manualTxt.setTextColor(classroomActivity.getResources().getColor(R.color.colorPrimary));

        disableLights();
    }

    private void disableLights() {
        lightLayout.setBackgroundColor(classroomActivity.getResources().getColor(R.color.grey));
    }
    private void enableLights() {
        lightLayout.setBackground(null);
    }
}