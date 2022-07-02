package com.example.drrbnicompany.Notification;

import android.app.Activity;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FcmNotificationsSender {

     String recipientToken, date, senderName, adsId, recipientTopic ;
     Activity mActivity;
     FirebaseUser firebaseUser;

    private RequestQueue requestQueue;
    private final String postUrl = "https://fcm.googleapis.com/fcm/send";
    private final String fcmServerKey = "AAAAyXR_2Eg:APA91bFYCAG39OqbR55m90JlcUahrhOtaJ4tXJlkHzZwE399nNdmWU1lScFEhko4isWpOrd471sEasFDPtmCfyhr1AKZUUHQezUjkAbDn0MUS14Dkl82OYgM1HfI6-aUQ_PAZK9Eg9b7";

    public FcmNotificationsSender(String recipientToken, String date, String senderName,
                                  String adsId, Activity mActivity) {
        this.recipientToken = recipientToken;
        this.date = date;
        this.senderName = senderName;
        this.adsId = adsId;
        this.mActivity = mActivity;
    }

    public FcmNotificationsSender(String recipientToken, String senderName, String adsId, Activity mActivity) {
        this.recipientToken = recipientToken;
        this.senderName = senderName;
        this.adsId = adsId;
        this.mActivity = mActivity;
    }

    public FcmNotificationsSender(String recipientTopic, String adsId, Activity mActivity) {
        this.recipientTopic = recipientTopic;
        this.adsId = adsId;
        this.mActivity = mActivity;
    }



    public void SendNewAdsNotifications() {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String senderUid = firebaseUser.getUid();

        requestQueue = Volley.newRequestQueue(mActivity);

        JSONObject mainObject = new JSONObject();
        try {
            mainObject.put("to", "/topics/"+ recipientTopic);

            JSONObject dataObject = new JSONObject();
            dataObject.put("title", "فرصة تدريب جديدة");
            dataObject.put("body" , "هناك فرصة تدريب جديدة أطلع عليها");
            dataObject.put("senderUid" , senderUid);
            dataObject.put("adsId" , adsId);

            mainObject.put("data", dataObject);


            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, postUrl, mainObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("ffffffff" , response.toString());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("ffffffff" , error.toString());
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> header = new HashMap<>();
                    header.put("content-type", "application/json");
                    header.put("authorization", "key=" + fcmServerKey);
                    return header;
                }
            };
            requestQueue.add(request);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void SendAcceptNotifications() {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String senderUid = firebaseUser.getUid();

        requestQueue = Volley.newRequestQueue(mActivity);

        JSONObject mainObject = new JSONObject();
        try {
            mainObject.put("to", recipientToken);

            JSONObject dataObject = new JSONObject();
            dataObject.put("title", "فرصة تدريب جديدة");
            dataObject.put("body", "قام "+senderName+" بقبول تدريبك في تاريخ "+ date);
            dataObject.put("senderUid" , senderUid);
            dataObject.put("adsId" , adsId);


            mainObject.put("data", dataObject);


            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, postUrl, mainObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> header = new HashMap<>();
                    header.put("content-type", "application/json");
                    header.put("authorization", "key=" + fcmServerKey);
                    return header;
                }
            };
            requestQueue.add(request);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void SendRejectNotifications() {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String senderUid = firebaseUser.getUid();

        requestQueue = Volley.newRequestQueue(mActivity);

        JSONObject mainObject = new JSONObject();
        try {
            mainObject.put("to", recipientToken);

            JSONObject dataObject = new JSONObject();
            dataObject.put("title", "فرصة تدريب جديدة");
            dataObject.put("body", "قام "+senderName+" برفض طلب تدريبك");
            dataObject.put("senderUid" , senderUid);
            dataObject.put("adsId" , adsId);

            mainObject.put("data", dataObject);


            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, postUrl, mainObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> header = new HashMap<>();
                    header.put("content-type", "application/json");
                    header.put("authorization", "key=" + fcmServerKey);
                    return header;
                }
            };
            requestQueue.add(request);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
