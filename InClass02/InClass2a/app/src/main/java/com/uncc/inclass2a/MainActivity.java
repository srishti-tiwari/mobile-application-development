/*
a. Assignment # : In Class Assignment 02
b. File Name : Group31_InClass02.zip
c. Name : Pawan Ramesh Bole.
*/


package com.uncc.inclass2a;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private static DecimalFormat df = new DecimalFormat(".##");
    private EditText et_weight;
    private EditText et_feet;
    private EditText et_inches;
    private Button button_calc;
    private TextView bmiVal;
    private TextView bmiAnalysis;
    private static final int bmiConstant = 703;
    private Double bmi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_weight = findViewById(R.id.editTextWeight);
        et_feet = findViewById(R.id.editTextFeet);
        et_inches = findViewById(R.id.editTextInches);
        button_calc = findViewById(R.id.buttonBmiCalc);
        bmiVal = findViewById(R.id.textBMIVal);
        bmiAnalysis = findViewById(R.id.textBmiAnalysis);

        button_calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean fieldValidationSuccessful = true;
                bmiVal.setText(" ");
                bmiAnalysis.setText(" ");

                if (!et_weight.getText().toString().matches("\\d+(\\.\\d{1,2})?")) {
                    bmiVal.setText("Enter weight in numbers");
                    fieldValidationSuccessful = false;
                }
                if(!et_feet.getText().toString().matches("^\\d+$") || !et_inches.getText().toString().matches("^\\d+$") ){
                    bmiAnalysis.setText("Enter height in feet or inches in integer value");
                    fieldValidationSuccessful = false;
                }

                if(fieldValidationSuccessful) {
                    Double weight = Double.parseDouble(et_weight.getText().toString());
                    Double feet = Double.parseDouble(et_feet.getText().toString());
                    Double inches = Double.parseDouble(et_inches.getText().toString());

                    if (inches >= 12) {
                        bmiVal.setText("Incorrect value. Inches should be less than 12");
                    }
                    else {

                        double height = ((feet * 12) + inches);
                        bmi = ((weight * bmiConstant) / (height * height));

                        bmiVal.setText("Your BMI is "+df.format(bmi));
                        if (bmi < 18.5) {
                            bmiAnalysis.setText("You are Underweight");
                        } else if (bmi >= 18.5 && bmi <= 24.9) {
                            bmiAnalysis.setText("You are Normal weight");
                        } else if (bmi >= 25 && bmi <= 29.9) {
                            bmiAnalysis.setText("You are Overweight");
                        } else {
                            bmiAnalysis.setText("You are Obese");
                        }
                    }
                }
            }
        });
    }
}
