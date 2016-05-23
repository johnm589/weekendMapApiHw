package com.example.john.mappractice;

/**
 * Created by John on 5/23/16.
 */


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by g on 5/19/16.
 */
public class MapsModel {

    private static MapsModel instance;
    private static ApiResponseHandler responseHandler;


    //Empty Constructor. Don't let anyone instantiate this class
    private MapsModel() {
    }

    //Get the one and only instance of the MapsModel singlton
    public static MapsModel getInstance(ApiResponseHandler handler) {
        responseHandler = handler;
        if(instance == null) {
            instance = new MapsModel();
        }
        return instance;
    }

    //This is where we get data from the api
    public void doRequest(String address1, String city1, String state1) {
        AsyncHttpClient client = new AsyncHttpClient();

        String city;

        String address = address1.replaceAll(" ","+");
        if (city1.contains(" ")) {
             city = city1.replaceAll(" ", "+");
        }else{
             city = city1;
        }
        client.get(
                //"1520+2nd+Street,+Santa+Monica,+CA"

                "https://maps.googleapis.com/maps/api/geocode/json?address=" + address + ",+" + city + ",+" + state1 + "&key=AIzaSyDqZdZm8mYlPPO-9WPvEV-EjgbNYLYAm3w",
                null,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {


                        String address = null;
                        double latitude = 0;
                        String latString = null;
                        String longString = null;
                        double longitude = 0;



                        try {
                            JSONArray results = response.getJSONArray("results");
                            JSONObject location = (JSONObject) results.get(0);
                            JSONObject location2 = (JSONObject) location.get("geometry");
                            JSONObject location3 = (JSONObject) location2.get("bounds");
                            JSONObject location4 = (JSONObject) location3.get("northeast");




                            address = location.getString("formatted_address");
                            latitude = location4.getDouble("lat");
                            longitude = location4.getDouble("lng");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        longString = String.valueOf(longitude);
                        latString = String.valueOf(latitude);
//                        responseHandler.handleResponse(address);
                        responseHandler.handleResponse(longString + "SPACE" + latString + "SPACE" + address);
                    }
                });
    }

    public interface ApiResponseHandler{
        void handleResponse(String response);
    }

}

