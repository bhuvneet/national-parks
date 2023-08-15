package com.example.national_parks;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.national_parks.adapter.OnParkClickListener;
import com.example.national_parks.adapter.ParkRecyclerViewAdapter;
import com.example.national_parks.data.Repository;
import com.example.national_parks.model.Park;
import com.example.national_parks.model.ParkViewModel;

import java.util.ArrayList;
import java.util.List;

public class ParksFragment extends Fragment implements OnParkClickListener {
    private RecyclerView recyclerView;
    private ParkRecyclerViewAdapter parkRecyclerViewAdapter;
    private List<Park> parkList;
    private ParkViewModel parkViewModel;

    public ParksFragment(List<Park> parkList) {
        this.parkList = parkList;
    }

    public static ParksFragment newInstance(List<Park> parkList) {
        ParksFragment fragment = new ParksFragment(parkList);
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        parkViewModel = new ViewModelProvider(requireActivity())
                .get(ParkViewModel.class);
        if (parkViewModel.getParks().getValue() != null){
            parkList = parkViewModel.getParks().getValue();
            parkRecyclerViewAdapter = new ParkRecyclerViewAdapter(parkList, this);
            recyclerView.setAdapter(parkRecyclerViewAdapter);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parkList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for park recycler view adapter
        View view = inflater.inflate(R.layout.fragment_parks, container, false);
        recyclerView = view.findViewById(R.id.parks_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;

    }

    @Override
    public void onParkClicked(Park park) {
        Log.d("Park clicked: ", park.getName());
        parkViewModel.selectPark(park);
        getParentFragmentManager().beginTransaction()
                .replace(R.id.park_fragment, new DetailsFragment())
                .commit();
    }
}