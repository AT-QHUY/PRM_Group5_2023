package com.example.group5_project.Activity.User;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.example.group5_project.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.internal.PolylineEncoding;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {
    private static final LatLng BRANCH = new LatLng(10.841203842437997, 106.80988558901014);
    private static final float MAP_ZOOM_DEFAULT_SCALE = 15;
    private static final int LOCATION_REQUEST_PERMISSION_CODE = 10;
    private static final int GPS_REQUEST_PERMISSION_CODE = 11;
    private ImageButton btnCurrentLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private GoogleMap map;
    private GeoApiContext geoApiContext;
    private Location currentLocation;
    private List<Polyline> polylines = null;
    private boolean locationPermissionGranted = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        btnCurrentLocation = findViewById(R.id.btnCurrentLocation);
        btnCurrentLocation.setOnClickListener(this::onClick);
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this::onMapReady);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        geoApiContext = new GeoApiContext.Builder().apiKey(getString(R.string.google_maps_api_key)).build();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        map.addMarker(new MarkerOptions().position(BRANCH));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(BRANCH, MAP_ZOOM_DEFAULT_SCALE));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == btnCurrentLocation.getId()) {
            // check permission, GPS, request it, if user deny => explain why it's needed
            checkLocationPermission();
            if(!isGPSEnabled()){
                showGPSAlert();
            }
            // if permission granted, get device current location & draw direction
            getDeviceCurrentLocation();
//            calculateDirections();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_REQUEST_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationPermissionGranted = true;
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void checkLocationPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if(!isGPSEnabled()){
                showGPSAlert();
            }
            locationPermissionGranted = true;
        } else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_PERMISSION_CODE);
        }
    }

    private void showGPSAlert(){
        LocationRequest locationRequest = new LocationRequest.Builder(10000000).build();
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage("We needs GPS to show you direction, do you want to enable it?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                        registerForActivityResult();
//                        startActivityForResult(intent, GPS_REQUEST_PERMISSION_CODE);
                        startActivity(intent);
                    }
                })
                .create();
        dialog.show();
    }

    private boolean isGPSEnabled(){
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private void getDeviceCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                Toast.makeText(MapActivity.this, location.getLatitude() + ", " + location.getLongitude(), Toast.LENGTH_SHORT).show();
            }
        });
//        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
//            @Override
//            public void onSuccess(Location location) {
//                if (location != null){
//                    currentLocation = location;
//                    Toast.makeText(MapActivity.this, location.getLatitude() + ", " + location.getLongitude(), Toast.LENGTH_SHORT).show();
//                    calculateDirections();
//                }
//            }
//        });
    }

    private String getDirectionURL(LatLng origin, LatLng destination){
        String originParameter = "origin=" + origin.latitude + "," + origin.longitude;
        String destinationParameter = "destination=" + destination.latitude + "," + destination.longitude;
        String parameters = originParameter + "&" + destinationParameter;
        String outputFormat = "json";
        // can define transport mode
        String url = "https://maps.googleapis.com/maps/api/directions/" + outputFormat + "?" + parameters + "key=AIzaSyAqaHEp1COlnSMCKPc2SW5OM6lkocN5u9Q";
        return url;
    }

    private void calculateDirections(){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                DirectionsApiRequest directionsRequest = new DirectionsApiRequest(geoApiContext);
                // show all possible route instead of the shortest (?) one
                directionsRequest.alternatives(true);
                directionsRequest.origin(new com.google.maps.model.LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()));
                directionsRequest.destination(new com.google.maps.model.LatLng(BRANCH.latitude, BRANCH.longitude)).setCallback(new PendingResult.Callback<DirectionsResult>() {
                    @Override
                    public void onResult(DirectionsResult result) {
                        addDirectionPolylines(result);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        Log.e("", "Error at MapActivity: " + e.getMessage());
                    }
                });
            }
        });
    }

    private void addDirectionPolylines(DirectionsResult result){
        for(DirectionsRoute route: result.routes){
            List<com.google.maps.model.LatLng> path1 = PolylineEncoding.decode(route.overviewPolyline.getEncodedPath());
            List<LatLng> path2 = new ArrayList<>();
            for(com.google.maps.model.LatLng latLng: path1){
                path2.add(new LatLng(latLng.lat, latLng.lng));
            }
            Polyline polyline = map.addPolyline(new PolylineOptions().addAll(path2));
            polyline.setColor(R.color.black);
            polyline.setClickable(true);
        }
    }
}
