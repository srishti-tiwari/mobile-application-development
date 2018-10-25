/*
FileName : Group5_InClass06.zip
Name : Srishti Tiwari, Suryasruthi, Wali Hassan Khan
 */

package com.example.suryasruthi.chatroom;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.Serializable;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_sign_up );


        final EditText editTextFname = findViewById( R.id.editTextFname );
        final EditText editText2LName = findViewById( R.id.editText2LName );
        final EditText editTextEmail = findViewById( R.id.editTextEmail );
        final EditText editTextChoosePassword = findViewById( R.id.editTextChoosePassword );
        final EditText editTextRepeatPassword = findViewById( R.id.editTextRepeatPassword );
        Button buttonCancel = findViewById( R.id.buttonCancel );
        Button buttonSignUp1 = findViewById( R.id.buttonSignUp1 );


        buttonCancel.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        } );

        buttonSignUp1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editTextChoosePassword.getText().toString().equals( editTextRepeatPassword.getText().toString() )) {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody formBody = new FormBody.Builder()
                            .add( "fname", editTextFname.getText().toString() )
                            .add( "lname", editText2LName.getText().toString() )
                            .add( "email", editTextEmail.getText().toString() )
                            .add( "password", editTextChoosePassword.getText().toString() )
                            .build();
                    Request request = new Request.Builder()
                            .url( "http://ec2-18-234-222-229.compute-1.amazonaws.com/api/signup" )
                            .post( formBody )
                            .build();
                    client.newCall( request ).enqueue( new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                            SignUp signUp = new LoginResponseParser().Parse( response.body().string() );
                            if (signUp != null) {

                                SharedPreferences preferences = getSharedPreferences( "myPrefs", MODE_PRIVATE );
                                preferences.edit().remove( "token" ).commit();//remove if any pref tokens are there
                                preferences.edit().putString( "token", signUp.getToken() ).commit();

                                Intent intent1 = new Intent( SignUpActivity.this, MessageThreadActivity.class );
                                intent1.putExtra( "token", (Serializable) signUp );
                                intent1.putExtra( "user_fname", signUp.user_fname );
                                intent1.putExtra( "user_lname", signUp.user_lname );
                                startActivity( intent1 );

                                //Toast.makeText( MainActivity.this, "Login success", Toast.LENGTH_LONG ).show();
                                Log.d( "hello", "hel!" );
                            }
                        }
                    } );
                } else {
                    Toast.makeText( SignUpActivity.this, "Passwords dont match", Toast.LENGTH_LONG ).show();
                }
            }
        } );


    }
}
