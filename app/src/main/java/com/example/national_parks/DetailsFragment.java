package com.example.national_parks;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.national_parks.model.ParkViewModel;

public class DetailsFragment extends Fragment {
    private ParkViewModel parkViewModel;

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
        parkViewModel = new ViewModelProvider(requireActivity())
                .get(ParkViewModel.class);
        TextView test = view.getRootView().findViewById(R.id.test);
        parkViewModel.getSelectedPark().observe(getViewLifecycleOwner(), park -> {
            test.setText(park.getName());
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false);
    }
}