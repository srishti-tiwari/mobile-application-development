/*
FileName : Group5_InClass06.zip
Name : Srishti Tiwari, Suryasruthi, Wali Hassan Khan
 */

package com.example.suryasruthi.chatroom;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.SharedPreferences;
import android.widget.Toast;

import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class MessageThreadActivity extends AppCompatActivity {

    ArrayList<ChatRoom> chatlist = new ArrayList<>();
    Button buttonLogout;
    ImageView imageViewAddThread;
    ImageView imageViewDeleteThread;
    String token = null;
    String ALL_THREADS_API = "http://ec2-18-234-222-229.compute-1.amazonaws.com/api/thread";
    String ADD_THREAD_API = "http://ec2-18-234-222-229.compute-1.amazonaws.com/api/thread/add";
    String DELETE_THREAD_API = "http://ec2-18-234-222-229.compute-1.amazonaws.com/api/thread/delete/";
    String userid = null;
    ListView messageListView;
    SharedPreferences preferences;
    TextView textViewUserName;
    EditText editTextAddThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_message_thread );
        setTitle( "Message Threads" );

        textViewUserName = findViewById( R.id.textViewUserName );
        editTextAddThread = findViewById( R.id.editTextAddThread );
        buttonLogout = findViewById( R.id.buttonLogout );
        imageViewAddThread = findViewById( R.id.imageViewAdd );
        imageViewDeleteThread = findViewById( R.id.imageViewDelete );
        messageListView = findViewById( R.id.messageListView );

        preferences = getSharedPreferences( "myPrefs", MODE_PRIVATE );

        SignUp signUp = (SignUp) getIntent().getExtras().get( "token" );
        userid = signUp.getUser_id();
        String userfirstname = getIntent().getExtras().getString( "user_fname" );
        String userlastname = getIntent().getExtras().getString( "user_lname" );
        textViewUserName.setText( userfirstname + " " + userlastname );

        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add( "Authtoken", preferences.getString( "myPrefs", "" ) )
                .build();
        Request request = new Request.Builder()
                .url( ALL_THREADS_API )
                .post( formBody )
                .build();
        client.newCall( request ).enqueue( new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                response.body().toString();
            }
        } );


        imageViewAddThread.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editTextAddThread.getText().toString().isEmpty()) {
                    String threadtitle = editTextAddThread.getText().toString();
                    addThread( threadtitle );
                    editTextAddThread.setText( "" );
                } else {
                    Toast.makeText( MessageThreadActivity.this, "Enter new Message thread!", Toast.LENGTH_SHORT ).show();
                }
            }
        } );


        buttonLogout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                preferences.edit().remove( "token" ).commit();
                String token = preferences.getString( "token", "" );
                Log.d( "demo", "tokenafterlogout :" + token );
                Intent loginintent = new Intent( MessageThreadActivity.this, MainActivity.class );

                startActivity( loginintent );
                finish();
            }
        } );
        if (getIntent() != null && getIntent().getExtras() != null) {
            Intent intent = getIntent();
            SignUp user = (SignUp) intent.getExtras().getSerializable( "token" );
            token = preferences.getString( "token", "" );
            // preferences.edit().putString("userid",user.userId.toString() ).commit();

            Log.d( "demo", "token" + token );
            userid = user.getUser_id();
            textViewUserName = (TextView) findViewById( R.id.textViewUserName );
            if (user.user_fname != null)
                textViewUserName.setText( user.user_fname.toString() + " " + user.user_lname.toString() );
            if (isConnected()) {
                new LoadThreadData().execute( ALL_THREADS_API );

            }

        }
    }

    private void addThread(String threadtitle) {
        if (token != null) {
            OkHttpClient addclient = new OkHttpClient();
            RequestBody formBody = new FormBody.Builder()
                    .add( "title", threadtitle ).build();
            Request request = new Request.Builder()
                    .url( ADD_THREAD_API )
                    .header( "Authorization", "BEARER " + token )
                    .post( formBody )
                    .build();

            addclient.newCall( request ).enqueue( new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    if (response.isSuccessful()) {
                        // Log.d("demo","added");
                        new LoadThreadData().execute( ALL_THREADS_API );
                    }
                }
            } );

        }

    }

    public void setChatRoomparser(ArrayList<ChatRoom> chatRooms) {
        chatlist = chatRooms;
        ChatRoomAdapter chatRoomAdapter = new ChatRoomAdapter( this, R.layout.activity_list__chat_room, chatlist );
        messageListView.setAdapter( chatRoomAdapter );

    }

    class LoadThreadData extends AsyncTask<String, Integer, ArrayList<ChatRoom>> {
        @Override
        protected void onPostExecute(ArrayList<ChatRoom> chatRooms) {
            chatlist = chatRooms;
            ChatRoomAdapter adapter = new ChatRoomAdapter( MessageThreadActivity.this, R.layout.activity_list__chat_room, chatlist );
            messageListView.setAdapter( adapter );
        }

        @Override
        protected ArrayList<ChatRoom> doInBackground(String... strings) {
            ArrayList<ChatRoom> result = new ArrayList<>();
            OkHttpClient threadclient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url( strings[0] )
                    .header( "Authorization", "BEARER " + token )
                    .build();
            try {
                Response response = threadclient.newCall( request ).execute();
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    JSONObject root = null;
                    try {
                        root = new JSONObject( json );

                        JSONArray threads = root.getJSONArray( "threads" );
                        for (int i = 0; i < threads.length(); i++) {
                            JSONObject sourcesJson = threads.getJSONObject( i );
                            ChatRoom chatRoom = new ChatRoom();

                            chatRoom.user_id = sourcesJson.getString( "user_id" );

                            chatRoom.title = sourcesJson.getString( "title" );
                            chatRoom.id = sourcesJson.getString( "id" );


                            result.add( chatRoom );
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;

        }
    }

    public class ChatRoomAdapter extends ArrayAdapter<ChatRoom> {

        public ChatRoomAdapter(@NonNull Context context, int resource, @NonNull List<ChatRoom> objects) {
            super( context, resource, objects );
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            ChatRoom chatroom = getItem( position );
            if (convertView == null) {
                convertView = LayoutInflater.from( getContext() ).inflate( R.layout.activity_list__chat_room, parent, false );
            }
            TextView tv_id = (TextView) convertView.findViewById( R.id.textViewTitle );
            if (chatroom.title != null)
                tv_id.setText( chatroom.title );
            ImageView imageViewDelete = convertView.findViewById( R.id.imageViewDelete );
            imageViewDelete.setVisibility( View.INVISIBLE );
            if (chatroom.user_id.equals( userid )) {
                imageViewDelete.setVisibility( View.VISIBLE );
            }
            imageViewDelete.setTag( position );
            imageViewDelete.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int i = (int) view.getTag();
                    ChatRoom item = getItem( i );
                    new DeleteThreadData().execute( DELETE_THREAD_API + item.id );
                    new LoadThreadData().execute( ALL_THREADS_API );


                }
            } );


            //return super.getView(position, convertView, parent);
            return convertView;
        }


    }

    public class DeleteThreadData extends AsyncTask<String, Integer, ChatRoom> {

        @Override
        protected void onPostExecute(ChatRoom threaditems) {
            super.onPostExecute( threaditems );

        }

        @Override
        protected ChatRoom doInBackground(String... strings) {
            ChatRoom result = new ChatRoom();
            OkHttpClient threadclient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url( strings[0] )
                    .header( "Authorization", "BEARER " + token )
                    .build();
            try {
                Response response = threadclient.newCall( request ).execute();
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    JSONObject root = null;
                    try {
                        root = new JSONObject( json );
                        JSONObject obj = root.getJSONObject( "thread" );
                        result.id = obj.getString( "id" );
                        result.title = obj.getString( "title" );
                        result.user_id = obj.getString( "user_id" );


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;

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


