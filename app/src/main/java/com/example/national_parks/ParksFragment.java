package com.example.national_parks;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.national_parks.adapter.ParkRecyclerViewAdapter;
import com.example.national_parks.data.Repository;
import com.example.national_parks.model.Park;

import java.util.List;

public class ParksFragment extends Fragment {
    private RecyclerView recyclerView;
    private ParkRecyclerViewAdapter parkRecyclerViewAdapter;
    private List<Park> parkList;

    public ParksFragment() {
        // Required empty public constructor
    }

    public static ParksFragment newInstance() {
        ParksFragment fragment = new ParksFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        Repository.populateParks(parks -> {
            parkRecyclerViewAdapter = new ParkRecyclerViewAdapter(parks);
            recyclerView.setAdapter(parkRecyclerViewAdapter);
        });
        //parkList =  Repository.getParks();
        //parkRecyclerViewAdapter = new ParkRecyclerViewAdapter(parkList);
        //recyclerView.setAdapter(parkRecyclerViewAdapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}