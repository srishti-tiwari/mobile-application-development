/*
FileName : Group5_InClass06.zip
Name : Srishti Tiwari, Suryasruthi, Wali Hassan Khan
 */
package com.example.suryasruthi.chatroom;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
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

public class MainActivity extends AppCompatActivity {

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        final EditText email = findViewById( R.id.editTextEmail );
        final EditText password = findViewById( R.id.editTextPassword );
        final Button buttonLogin = findViewById( R.id.buttonLogin );
        final Button buttonSignUp = findViewById( R.id.button2SignUp );

        if(isConnected()) {
            handler = new Handler( new Handler.Callback() {
                @Override
                public boolean handleMessage(Message message) {
                    if (message.what == 150) {
                        Toast.makeText( MainActivity.this, "Username or password is incorrect", Toast.LENGTH_SHORT ).show();
                    }
                    return false;
                }
            } );

            buttonLogin.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    OkHttpClient client = new OkHttpClient();
                    RequestBody formBody = new FormBody.Builder()
                            .add( "email", email.getText().toString() )
                            .add( "password", password.getText().toString() )
                            .build();
                    Request request = new Request.Builder()
                            .url( "http://ec2-18-234-222-229.compute-1.amazonaws.com/api/login" )
                            .post( formBody )
                            .build();
                    client.newCall( request ).enqueue( new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if (!response.isSuccessful()) {
                                //throw new IOException("Unexpected code " + response);
                                Message message = new Message();
                                message.what = 150;
                                handler.sendMessage( message );

                            } else {
                                SignUp signUp = new LoginResponseParser().Parse( response.body().string() );
                                SharedPreferences preferences = getSharedPreferences( "myPrefs", MODE_PRIVATE );
                                preferences.edit().remove( "token" ).commit();//remove if any pref tokens are there
                                preferences.edit().putString( "token", signUp.getToken() ).commit();

                                Intent intent1 = new Intent( MainActivity.this, MessageThreadActivity.class );
                                intent1.putExtra( "token", (Serializable) signUp );
                                intent1.putExtra( "user_fname", signUp.user_fname );
                                intent1.putExtra( "user_lname", signUp.user_lname );
                                startActivity( intent1 );
                            }
                        }
                    } );

                }
            } );

            buttonSignUp.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent( MainActivity.this, SignUpActivity.class );
                    startActivity( intent );
                }
            } );

        }else{
            Toast.makeText( MainActivity.this, "No Internet connection!", Toast.LENGTH_SHORT ).show();

        }
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService( Context.CONNECTIVITY_SERVICE );
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() || (networkInfo.getType() != ConnectivityManager.TYPE_WIFI && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            return false;
        } else
            return true;
    }
}
