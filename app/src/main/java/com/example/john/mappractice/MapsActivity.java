package com.example.john.mappractice;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, MapsModel.ApiResponseHandler {

    private GoogleMap mMap;
    private EditText editText;

    private TextView textview;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        editText = (EditText) findViewById(R.id.edit1);

        textview = (TextView) findViewById(R.id.tv);
        button = (Button) findViewById(R.id.sub);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String address = editText.getText().toString();


                MapsModel.getInstance(MapsActivity.this).doRequest(address);
                        //"1520+2nd+Street,+Santa+Monica,+CA"



            playSound(MapsActivity.this,R.raw.air);
                // find();
            }

        });
    }
    private static void playSound(Context context, int soundID){
        MediaPlayer mp = MediaPlayer.create(context, soundID);
        mp.start();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
// Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        android.support.v7.widget.SearchView searchView =
                (android.support.v7.widget.SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(new ComponentName(this, MapsActivity.class)));

        return true;
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

//    public void find() {
//
//        int lat = Integer.parseInt(editText.getText().toString());
//        int longitude = Integer.parseInt(textview.getText().toString());
//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(lat, longitude);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//    }
    @Override
    public void onMapReady(GoogleMap googleMap) {



        mMap = googleMap;

        Log.e("hello", String.valueOf(mMap));

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(0, 0);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void handleResponse(String response) {

        String[] ary = response.split("SPACE");
        String lngString = ary[0];
        String latString = ary[1];

        Double lng = Double.parseDouble(lngString);
        Double lat = Double.parseDouble(latString);

        String address = ary[2];

        String newline = System.getProperty("line.separator");


        textview.setText("Full address: " + address + newline + newline + " Latitude: " + latString + " Longitude: " + lngString);

        LatLng sydney = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        float zoomLevel = (float) 10.0; //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoomLevel));

    }
}
