/*
FileName : Group5_HW06.zip
Name : Srishti Tiwari, Suryasruthi, Wali Hassan Khan
 */
package com.example.suryasruthi.chatroom;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ocpsoft.prettytime.PrettyTime;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatMessagesActivity extends AppCompatActivity {
    ArrayList<messages> messagesList = new ArrayList<>();

    TextView textViewMsgName;
    Button buttonHome, buttonSend;
    String ALL_MESSAGES_API = "http://ec2-18-234-222-229.compute-1.amazonaws.com/api/messages/";
    String ADD_MESSAGE_API = "http://ec2-18-234-222-229.compute-1.amazonaws.com/api/message/add";
    String DELETE_MESSAGE_API="http://ec2-18-234-222-229.compute-1.amazonaws.com/api/message/delete/";
    SharedPreferences preferences;
    String token = null;
    ListView chattextListView;
    EditText editTextEnterMsg;
    ArrayList<messages> result;
    String tid=null;
    String userid =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom_messages);
        setTitle("Chat Room");
        token = getIntent().getExtras().getString("auth");
        preferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
        tid=getIntent().getExtras().getString("thread_id");
        userid =getIntent().getExtras().getString("user_id");

        preferences = getSharedPreferences( "myPrefs", MODE_PRIVATE );

        textViewMsgName = findViewById(R.id.textViewMsgName);
        buttonHome = findViewById(R.id.buttonHome);
        buttonSend = findViewById(R.id.buttonSend);
        chattextListView = findViewById(R.id.chattextListView);
        editTextEnterMsg = findViewById(R.id.editTextEnterMsg);



        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("Authorization","BEARER "+token)
                .build();
        Request request = new Request.Builder()
                .url(ALL_MESSAGES_API+getIntent().getExtras().getString("thread_id"))
                .post(formBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
               // new LoadMessageData().execute("ALL_MESSAGES_API")
                response.body().toString();


            }
        });

        if (isConnected()) {
            new LoadMessagesData().execute( ALL_MESSAGES_API+tid );

        }
        textViewMsgName.setText(getIntent().getExtras().getString("title"));

        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent intent1 = new Intent(ChatMessagesActivity.this, MessageThreadActivity.class);
                finish();
            }
        });

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editTextEnterMsg.getText().toString().isEmpty()) {
                    String messagetitle = editTextEnterMsg.getText().toString();
                  addMessage( messagetitle );
                  editTextEnterMsg.setText("");

                    //editTextEnterMsg.setText( "" );
               } else {
                    Toast.makeText( ChatMessagesActivity.this, "Enter new messages!", Toast.LENGTH_SHORT ).show();
                }

            }
        });


    }
    private void addMessage( String messagetitle) {
        messages msg=new messages();
        if (token != null) {
            OkHttpClient addclient = new OkHttpClient();
            RequestBody formBody = new FormBody.Builder()
                    .add("message", messagetitle)
                    .add("thread_id", tid)
                    .build();
            Request request = new Request.Builder()
                    .url(ADD_MESSAGE_API)
                    .header("Authorization", "BEARER " + token)
                    .post(formBody)
                    .build();

            addclient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }
                  @Override
                public void onResponse(Call call, Response response) throws IOException {

                    if (response.isSuccessful()) {
                        // Log.d("demo","added");

                      new LoadMessagesData().execute(ALL_MESSAGES_API + tid);

                    }
                }
            });
        }
    }

        class LoadMessagesData extends AsyncTask<String, Integer, ArrayList<messages>> {

            @Override
            protected ArrayList<messages> doInBackground(String... strings) {
                result = new ArrayList<>();

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

                            JSONArray messages = root.getJSONArray( "messages" );
                            for (int i = 0; i < messages.length(); i++) {
                                messages msg1=new messages();
                                JSONObject messagesJSONObject = messages.getJSONObject( i );

                                msg1.user_fname = messagesJSONObject.getString("user_fname");
                                msg1.user_lname = messagesJSONObject.getString("user_lname");
                                msg1.message = messagesJSONObject.getString("message");
                                msg1.user_id=messagesJSONObject.getString("user_id");
                                msg1.message_id=messagesJSONObject.getString("id");
                                String date = messagesJSONObject.getString("created_at");
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
                                msg1.created_date= sdf.parse(date);

                                result.add(msg1);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        catch (ParseException e)
                        {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(ArrayList<messages> messages) {
                super.onPostExecute(messages);

                 MessageAdapter adapter1=new MessageAdapter(ChatMessagesActivity.this,R.layout.activity_list_messages,messages);
                 chattextListView.setAdapter(adapter1);
                    //adapter1.notifyDataSetChanged();
            }
        }


        public class MessageAdapter extends ArrayAdapter<messages> {

        public MessageAdapter(@NonNull Context context, int resource, @NonNull ArrayList<messages> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                messages m=getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_list_messages, parent, false);
            }
            TextView textViewMsgName1 = convertView.findViewById(R.id.textViewMsgName1);
            TextView textViewNameUser = convertView.findViewById(R.id.textViewNameUser);
            TextView textViewDate = convertView.findViewById(R.id.textViewDate);
            ImageView buttonDelete1 = convertView.findViewById(R.id.buttondelete1);
            buttonDelete1.setVisibility(View.INVISIBLE);

            textViewMsgName1.setText(m.message);
            textViewNameUser.setText(m.user_fname + " " + m.user_lname);


            Calendar cal = Calendar.getInstance();
            cal.setTime(m.created_date);
            cal.add(Calendar.HOUR, -4);  // API response is in GMT hence converting it to EDT

            Date actualDate = cal.getTime();
            PrettyTime p = new PrettyTime();

            String datetime = p.format(actualDate);

            textViewDate.setText(datetime);

            if(m.user_id.equals(userid)){
                buttonDelete1.setVisibility(View.VISIBLE);
            }
            buttonDelete1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    deleteMessage(result.get(position).message_id);

                }
            });




            //return super.getView(position, convertView, parent);
            return convertView;
        }

            private void deleteMessage(String message_id) {
                messages msg=new messages();
                if (token != null) {
                    OkHttpClient addclient = new OkHttpClient();

                    Request request = new Request.Builder()
                            .url(DELETE_MESSAGE_API+message_id)
                            .header("Authorization", "BEARER " + token)
                            .build();

                    addclient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                            if (response.isSuccessful()) {
                                // Log.d("demo","added");

                                new LoadMessagesData().execute(ALL_MESSAGES_API + tid);
                            }
                        }
                    });
                }
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
