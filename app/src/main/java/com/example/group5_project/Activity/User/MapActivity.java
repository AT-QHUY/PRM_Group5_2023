package com.example.group5_project.Activity.User;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.group5_project.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.internal.PolylineEncoding;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener, GoogleMap.OnPolylineClickListener {
    private static final LatLng BRANCH = new LatLng(10.8412038, 106.8098855);
    private static final float MAP_ZOOM_DEFAULT_SCALE = 15;
    private static final int LOCATION_REQUEST_PERMISSION_CODE = 10;
    private boolean locationPermissionStatus;
    private com.google.maps.model.LatLng currentLocationLatLng;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private ImageButton btnCurrentLocation;
    private GoogleMap map;
    private GeoApiContext geoApiContext;
    private Polyline choosenPolyline = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        btnCurrentLocation = findViewById(R.id.btnCurrentLocation);
        btnCurrentLocation.setOnClickListener(this::onClick);
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this::onMapReady);
        geoApiContext = new GeoApiContext.Builder().apiKey(getString(R.string.google_maps_api_key)).build();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        map.addMarker(new MarkerOptions().position(BRANCH));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(BRANCH, MAP_ZOOM_DEFAULT_SCALE));
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.setOnPolylineClickListener(this::onPolylineClick);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == btnCurrentLocation.getId()) {
            checkLocationPermission();
            if(locationPermissionStatus){
                getCurrentLocation();
            }
        }
    }


    private void checkLocationPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            locationPermissionStatus = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        }
        locationPermissionStatus = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        if (!locationPermissionStatus) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_REQUEST_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationPermissionStatus = true;
                getCurrentLocation();
            } else {
                locationPermissionStatus = false;
                Toast.makeText(this, "Enable location permission to get direction", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

//        fusedLocationProviderClient.getCurrentLocation(new CurrentLocationRequest.Builder().build(), new CancellationToken() {
//            @NonNull
//            @Override
//            public CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) {
//                return null;
//            }
//
//            @Override
//            public boolean isCancellationRequested() {
//                return false;
//            }
//        }).addOnSuccessListener(new OnSuccessListener<Location>() {
//            @Override
//            public void onSuccess(Location location) {
//                if (location != null) {
//                    double latitude = location.getLatitude();
//                    double longitude = location.getLongitude();
//                }
//            }
//        });
        try {
            fusedLocationProviderClient.requestLocationUpdates(
                    new LocationRequest.Builder(Long.MAX_VALUE).setPriority(Priority.PRIORITY_HIGH_ACCURACY).setWaitForAccurateLocation(true).build(),
                    new LocationCallback() {
                @Override
                public void onLocationResult(@NonNull LocationResult locationResult) {
//                super.onLocationResult(locationResult);
                    fusedLocationProviderClient.removeLocationUpdates(this);

                    if (locationResult.getLocations().size() > 0) {
                        int index = locationResult.getLocations().size() - 1;
                        double latitude = locationResult.getLocations().get(index).getLatitude();
                        double longitude = locationResult.getLocations().get(index).getLongitude();
                        currentLocationLatLng = new com.google.maps.model.LatLng(latitude, longitude);
                        getDirections(currentLocationLatLng, new com.google.maps.model.LatLng(BRANCH.latitude, BRANCH.longitude));
                    }
                }
            }, Looper.getMainLooper());
        } catch (Exception e){
            Log.e("Error", e.toString());
            Arrays.stream(e.getStackTrace()).iterator().forEachRemaining(stackTraceElement -> Log.e("Error", stackTraceElement.toString()));
        }
    }

    private void getDirections(com.google.maps.model.LatLng origin, com.google.maps.model.LatLng destination){
        DirectionsApiRequest directionsRequest = new DirectionsApiRequest(geoApiContext);
        // show all possible route instead of the shortest (?) one
        directionsRequest.alternatives(true);
        directionsRequest.origin(origin);
        directionsRequest.destination(destination);
        directionsRequest.setCallback(new PendingResult.Callback<DirectionsResult>() {
            @Override
            public void onResult(DirectionsResult result) {
                        addDirectionPolylines(result.routes);
                Log.d("Error", "Success");
            }
             @Override
             public void onFailure(Throwable e) {
                Log.e("Error", "Error at MapActivity: " + e.toString());
                Arrays.stream(e.getStackTrace()).iterator().forEachRemaining(stackTraceElement -> {
                    Log.e("Error", stackTraceElement.toString());
                });
            }
        });
    }

    private void addDirectionPolylines(DirectionsRoute[] routes){
        MapActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for(DirectionsRoute route: routes){
                    List<com.google.maps.model.LatLng> path1 = PolylineEncoding.decode(route.overviewPolyline.getEncodedPath());
                    List<LatLng> path2 = new ArrayList<>();
                    for(com.google.maps.model.LatLng latLng: path1){
                        path2.add(new LatLng(latLng.lat, latLng.lng));
                    }
                    Polyline polyline = map.addPolyline(new PolylineOptions().addAll(path2));
                    polyline.setColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
                    polyline.setClickable(true);
                }
            }
        });
    }

    @Override
    public void onPolylineClick(@NonNull Polyline polyline) {
        if(choosenPolyline != null){
            choosenPolyline.setColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
            choosenPolyline.setZIndex(0);
        }
        polyline.setColor(ContextCompat.getColor(getApplicationContext(), R.color.blue));
        polyline.setZIndex(1);
        choosenPolyline = polyline;
    }
}
