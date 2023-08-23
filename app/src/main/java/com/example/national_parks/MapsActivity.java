package com.example.national_parks;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.national_parks.adapter.CustomInfoWindow;
import com.example.national_parks.data.Repository;
import com.example.national_parks.model.Park;
import com.example.national_parks.model.ParkViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.national_parks.databinding.ActivityMapsBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private ParkViewModel parkViewModel;
    private List<Park> parkList;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        parkViewModel = new ViewModelProvider(this)
                .get(ParkViewModel.class);

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
                selectedFragment = ParksFragment.newInstance(Repository.getParks());

            }
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.map, selectedFragment)
                    .commit();
            return true;
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setInfoWindowAdapter(new CustomInfoWindow(getApplicationContext()));
        mMap.setOnInfoWindowClickListener(this);

        parkList = new ArrayList<>();
        parkList.clear();

        Repository.populateParks(parks -> {
            parkList = parks;
            for (Park park : parks)
            {
                String latitude = park.getLatitude();
                String longitude = park.getLongitude();
                if(!latitude.isEmpty() && !longitude.isEmpty())
                {
                    // Add a markers for parks in map
                    LatLng whichPark = new LatLng(Double.parseDouble(park.getLatitude()),
                            Double.parseDouble(park.getLongitude()));

                    MarkerOptions markerOptions = new MarkerOptions()
                            .position(whichPark)
                            .title(park.getName())
                            .icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                            .snippet(park.getStates());

                    Marker marker = mMap.addMarker(markerOptions);
                    marker.setTag(park);

                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(whichPark, 5));
                    Log.d("Parks", "onMapReady: " + park.getFullName());
                }
                else
                {
                    Log.d("ERROR:", "onMapReady: Invalid longitude/latitude");
                }
            }
            parkViewModel.setSelectedParks(parkList);
        });
    }

    @Override
    public void onInfoWindowClick(Marker marker)
    {
        // go to details fragment to see park details
        goToDetailsFragment(marker);
    }

    private void goToDetailsFragment(Marker marker) {
        parkViewModel.selectPark((Park) marker.getTag());
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.map, DetailsFragment.newInstance())
                .commit();
    }


}