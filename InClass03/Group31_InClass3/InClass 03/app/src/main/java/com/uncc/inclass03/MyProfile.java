package com.uncc.inclass03;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MyProfile extends AppCompatActivity {

    static final int SELECT_AVATAR_REQUEST = 1;
    static final int DISPLAY_PROFILE_REQUEST = 2;
    private static int selectedImageId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        final Student student = new Student();
        final ImageView imageMyAvatar= findViewById(R.id.imageMyAvatar);
        final TextView editTextFirstName = findViewById(R.id.editTextFirstName);
        final TextView editTextLastName = findViewById(R.id.editTextLastName);
        final TextView editTextStudentId = findViewById(R.id.editTextStudentId);
        final RadioGroup radioGroupDepartment = findViewById(R.id.radioGroupDepartment);
        final Button buttonSave = findViewById(R.id.buttonSave);


        imageMyAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myAvatarIntent = new Intent(MyProfile.this, SelectAvatar.class);
                startActivityForResult(myAvatarIntent,SELECT_AVATAR_REQUEST);
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                student.setFirstName(editTextFirstName.getText().toString());
                student.setLastName(editTextLastName.getText().toString());
                student.setStudentId(Integer.parseInt(editTextStudentId.getText().toString()));
                student.setImageId(selectedImageId);
                if(radioGroupDepartment.getCheckedRadioButtonId() == R.id.radioButtonCS){
                    student.setDepartmentId("CS");
                }
                else if(radioGroupDepartment.getCheckedRadioButtonId() == R.id.radioButtonBIO){
                    student.setDepartmentId("BIO");
                }
                else if(radioGroupDepartment.getCheckedRadioButtonId() == R.id.radioButtonSIS){
                    student.setDepartmentId("SIS");
                }
                else if(radioGroupDepartment.getCheckedRadioButtonId() == R.id.radioButtonOther){
                    student.setDepartmentId("Others");
                }

                Intent displayProfileIntent = new Intent(MyProfile.this, DisplayMyProfile.class);
                displayProfileIntent.putExtra("student",student);
                startActivity(displayProfileIntent);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MyProfile.SELECT_AVATAR_REQUEST) {
            if (resultCode == RESULT_OK) {
                if(data.hasExtra("selectedImage")){

                    final ImageView imageMyAvatar= findViewById(R.id.imageMyAvatar);
                    selectedImageId = data.getIntExtra("selectedImage",R.drawable.select_image);
                    imageMyAvatar.setImageResource(data.getIntExtra("selectedImage",R.drawable.select_image));
                }
            }
        }

        if (requestCode == MyProfile.DISPLAY_PROFILE_REQUEST) {
            if (resultCode == RESULT_OK) {
                if(data.hasExtra("student")){

                    //final ImageView imageMyAvatar= findViewById(R.id.imageMyAvatar);
                    //imageMyAvatar.setImageResource(data.getIntExtra("selectedImage",R.drawable.select_image));
                }
            }
        }

    }
}
