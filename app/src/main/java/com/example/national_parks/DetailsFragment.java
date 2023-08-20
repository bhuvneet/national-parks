package com.example.national_parks;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.national_parks.adapter.ViewPagerAdapter;
import com.example.national_parks.model.ParkViewModel;

public class DetailsFragment extends Fragment {
    private ParkViewModel parkViewModel;
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager2 viewPager2;

    public DetailsFragment() {

    }

    public static DetailsFragment newInstance(String param1, String param2) {
        DetailsFragment fragment = new DetailsFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager2 = view.findViewById(R.id.details_view_pager);

        parkViewModel = new ViewModelProvider(requireActivity())
                .get(ParkViewModel.class);

        parkViewModel.getSelectedPark().observe(getViewLifecycleOwner(), park -> {
            viewPagerAdapter = new ViewPagerAdapter(park.getImages());
            viewPager2.setAdapter(viewPagerAdapter);
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false);
    }
}