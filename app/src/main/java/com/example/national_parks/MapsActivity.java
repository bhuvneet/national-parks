package com.example.national_parks;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.national_parks.data.Repository;
import com.example.national_parks.model.Park;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.national_parks.databinding.ActivityMapsBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // instantiate bottom nav view
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int id = item.getItemId();
            if(id == R.id.maps_nav_button)
            {
                mMap.clear(); // clear markers
                // show map view
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.map, mapFragment)
                        .commit();
                mapFragment.getMapAsync(this);
                return true;

            }
            else if(id == R.id.parks_nav_button)
            {
                // show parks view
                selectedFragment = ParksFragment.newInstance();
            }
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.map, selectedFragment)
                    .commit();
            return true;
        });
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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;



        Repository.getParks(parks -> {
            for (Park park : parks)
            {
                String latitude = park.getLatitude();
                String longitude = park.getLongitude();
                if(!latitude.isEmpty() && !longitude.isEmpty())
                {
                    // Add a markers for parks in map
                    LatLng whichPark = new LatLng(Double.parseDouble(park.getLatitude()),
                            Double.parseDouble(park.getLongitude()));
                    mMap.addMarker(new MarkerOptions().position(whichPark)
                            .title(park.getFullName()));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(whichPark));
                    Log.d("Parks", "onMapReady: " + park.getFullName());
                }
                else
                {
                    Log.d("ERROR:", "onMapReady: Invalid longitude/latitude");
                }

            }
        });
    }
}