/*
FileName : Group5_InClass06.zip
Name : Srishti Tiwari, Suryasruthi, Wali Hassan Khan
 */

package com.example.suryasruthi.chatroom;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;

public class LoginResponseParser {

    protected SignUp Parse(String jsonString) {

        String json = jsonString;

        JSONObject root = null;
        try {
            root = new JSONObject( json );
            String status = root.getString( "status" );
            if (status.equals( "ok" )) {
                SignUp signUp = new SignUp();

                signUp.setUser_fname( root.getString( "user_fname" ) );
                signUp.setUser_lname( root.getString( "user_lname" ) );
                signUp.setStatus( root.getString( "status" ) );
                signUp.setToken( root.getString( "token" ) );
                signUp.setUser_id( root.getString( "user_id" ) );
                signUp.setUser_email( root.getString( "user_email" ) );
                signUp.setUser_role( root.getString( "user_role" ) );
                return signUp;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}

