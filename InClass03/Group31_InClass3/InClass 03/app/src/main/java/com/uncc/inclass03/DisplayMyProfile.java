package com.uncc.inclass03;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class DisplayMyProfile extends AppCompatActivity {

    private Student student;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_my_profile);
        final TextView textViewMyName =  findViewById(R.id.textViewMyName);
        final TextView textViewMyStudentId = findViewById(R.id.textViewMyStudentId);
        final TextView textViewMyDepartment = findViewById(R.id.textViewMyDepartment);
        final ImageView imageViewMyAvatar = findViewById(R.id.imageViewMyAvatar);
        final Button editButton = findViewById(R.id.buttonEdit);

        if(getIntent()!=null && getIntent().getExtras()!=null){
            Student student = (Student)getIntent().getSerializableExtra("student");

            textViewMyName.setText(student.getFirstName() + " " +student.getLastName());
            textViewMyStudentId.setText(student.getStudentId().toString());
            textViewMyDepartment.setText(student.getDepartmentId());
            imageViewMyAvatar.setImageResource(student.getImageId());

        }

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEditStudent = new Intent(DisplayMyProfile.this, MyProfile.class);
                intentEditStudent.putExtra("student", student);
                setResult(RESULT_OK,intentEditStudent);
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MyProfile.SELECT_AVATAR_REQUEST) {
            if (resultCode == RESULT_OK) {
                if (data.hasExtra("student")) {

                }
            }
        }

                }
}
