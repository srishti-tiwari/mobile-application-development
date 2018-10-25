/*
 * Homework02 - Group 31
 * FileName: Group31_HW02.zip
 * Name : Srishti Tiwari
 * Name : Pawan Bole
 */

package uncc.com.hw02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        final TextView textViewLength1 = findViewById( R.id.textViewLength1 );
        final EditText editTextLength1 = findViewById( R.id.editTextLength1 );
        final TextView textViewLength2 = findViewById( R.id.textViewLength2 );
        final EditText editTextLength2 = findViewById( R.id.editTextLength2 );
        final ImageView imageViewTriangle = findViewById( R.id.imageViewTriangle );
        final ImageView imageViewSquare = findViewById( R.id.imageViewSquare );
        final ImageView imageViewCircle = findViewById( R.id.imageViewCircle );
        final TextView textViewSelectShape = findViewById( R.id.textViewSelectShape );
        final Button buttonCalculate = findViewById( R.id.buttonCalculate );
        final EditText editTextArea = findViewById( R.id.editTextArea );
        final Button buttonClear = findViewById( R.id.buttonClear );

        textViewSelectShape.setText( "Select a shape" );

        imageViewTriangle.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewLength2.setVisibility( View.VISIBLE );
                editTextLength2.setVisibility( View.VISIBLE );
                textViewSelectShape.setText( "Triangle" );
            }
        } );

        imageViewSquare.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewLength2.setVisibility( View.INVISIBLE );
                editTextLength2.setVisibility( View.INVISIBLE );
                textViewSelectShape.setText( "Square" );
            }
        } );

        imageViewCircle.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewLength2.setVisibility( View.INVISIBLE );
                editTextLength2.setVisibility( View.INVISIBLE );
                textViewSelectShape.setText( "Circle" );
            }
        } );

        buttonCalculate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double length1 = 0.0;
                double length2 = 0.0;
                editTextLength1.setError( null);
                editTextLength2.setError( null);

                if (textViewLength2.getVisibility() == View.VISIBLE) {
                    if ((editTextLength1.getText().toString().isEmpty() ||
                            editTextLength1.getText().toString().equals( "0" )) &&
                            (editTextLength2.getText().toString().isEmpty() ||
                                    editTextLength2.getText().toString().equals( "0" ))) {

                        // Both the lengths are invalid

                        editTextLength1.requestFocus();
                        editTextLength1.setError( "Length cannot be 0 or empty" );
                        editTextLength2.requestFocus();
                        editTextLength2.setError( "Length cannot be 0 or empty" );
                    } else if (textViewLength1.getText().toString().isEmpty() ||
                            textViewLength1.getText().toString().equals( "0" )) {

                        // length 1 is invalid
                        editTextLength1.requestFocus();
                        editTextLength1.setError( "Length cannot be 0 or empty" );
                    } else if (editTextLength2.getText().toString().isEmpty() ||
                            editTextLength2.getText().toString().equals( "0" )) {

                        //length 2 is invalid
                        editTextLength2.requestFocus();
                        editTextLength2.setError( "Length cannot be 0 or empty" );
                    } else if( textViewSelectShape.getText().toString().equals( "Triangle" )){

                        if(!editTextLength1.getText().toString().matches( "^[1-9]\\d*(\\.\\d+)?$" ))
                        {
                            editTextLength1.requestFocus();
                            editTextLength1.setError( "Length should be a number!" );
                        }
                        if(!editTextLength2.getText().toString().matches( "^[1-9]\\d*(\\.\\d+)?$" ))
                        {
                            editTextLength2.requestFocus();
                            editTextLength2.setError( "Length should be a number!" );
                        }

                        try {
                            length1 = Double.parseDouble( editTextLength1.getText().toString() );
                            length2 = Double.parseDouble( editTextLength2.getText().toString() );

                            //calculate area of triangle
                            editTextArea.setText(String.format("%.2f",0.5 * length1 * length2));
                        }
                        catch (Exception e){

                        }


                    }
                } else if (textViewLength2.getVisibility() == View.INVISIBLE ) {
                    if (editTextLength1.getText().toString().isEmpty() ||
                            editTextLength1.getText().toString().equals( "0" )) {
                        // if square or circle length 1 is invalid

                        editTextLength1.requestFocus();
                        editTextLength1.setError( "Length cannot be 0 or empty" );
                    } else {

                        // check if square or circle and calculate area

                        if(textViewSelectShape.getText().toString().equals("Square") ){

                            if(!editTextLength1.getText().toString().matches( "^[1-9]\\d*(\\.\\d+)?$" ))
                            {
                                editTextLength1.requestFocus();
                                editTextLength1.setError( "Length should be a number!" );
                            }
                            try {
                                length1 = Double.parseDouble( editTextLength1.getText().toString() );
                                //calculate area of square
                                editTextArea.setText( String.format( "%.2f", (length1 * length1) ) );
                            }
                            catch (Exception e){

                            }

                    }else if(textViewSelectShape.getText().toString().equals( "Circle" ))
                        {
                            if(!editTextLength1.getText().toString().matches( "^[1-9]\\d*(\\.\\d+)?$" ))
                            {
                                editTextLength1.requestFocus();
                                editTextLength1.setError( "Length should be a number!" );
                            }
                            try {
                                length1 = Double.parseDouble( editTextLength1.getText().toString() );
                                //calculate area of circle
                                editTextArea.setText( String.format( "%.2f", (3.1416 * length1 * length1) ) );
                            }
                            catch (Exception e){

                            }
                        }
                    }
                }


            }
        } );

        buttonClear.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewLength2.setVisibility( View.VISIBLE);
                editTextLength2.setVisibility( View.VISIBLE);
                editTextLength1.setText( "" );
                editTextLength2.setText( "" );
                textViewSelectShape.setText( "Select a shape" );
                editTextArea.setText( "" );
                editTextLength1.setError(null);
                editTextLength2.setError( null );
            }
        } );


    }
}
