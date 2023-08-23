package com.example.national_parks;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.text.TextUtils;
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

    public static DetailsFragment newInstance() {
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
        viewPager2 = view.findViewById(R.id.details_viewpager);

        parkViewModel = new ViewModelProvider(requireActivity())
                .get(ParkViewModel.class);

        TextView parkName = view.findViewById(R.id.details_park_name);
        TextView parkDesignation = view.findViewById(R.id.details_park_designation);
        TextView parkDescription = view.findViewById(R.id.details_description);
        TextView parkActivity = view.findViewById(R.id.details_activities);
        TextView parkFees = view.findViewById(R.id.details_entrancefees);
        TextView parkHours = view.findViewById(R.id.details_operatinghours);
        TextView parkTopics = view.findViewById(R.id.details_topics);
        TextView parkDirections = view.findViewById(R.id.details_directions);

        parkViewModel.getSelectedPark().observe(getViewLifecycleOwner(), park -> {
            parkName.setText(park.getName());
            parkDesignation.setText(park.getDesignation());
            parkDescription.setText(park.getDescription());

            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < park.getActivities().size(); i++)
            {
                stringBuilder.append(park.getActivities().get(i).getName())
                        .append(" | ");
            }
            parkActivity.setText(stringBuilder);

            if(park.getEntranceFees().size() > 0)
            {
                parkFees.setText("Cost: $ " + park.getEntranceFees().get(0).getCost());
            }
            else
            {
                parkFees.setText(R.string.info_unavailable);
            }
            StringBuilder opStringBuilder = new StringBuilder();
            opStringBuilder.append("Monday: ")
                    .append(park.getOperatingHours().get(0).getStandardHours().getMonday()).append("\n")
                    .append("Tuesday: ").append(park.getOperatingHours()
                            .get(0).getStandardHours().getTuesday()).append("\n")
                    .append("Wednesday: ").append(park.getOperatingHours()
                            .get(0).getStandardHours().getWednesday()).append("\n")
                    .append("Thursday: ").append(park.getOperatingHours()
                            .get(0).getStandardHours().getThursday()).append("\n")
                    .append("Friday: ").append(park.getOperatingHours()
                            .get(0).getStandardHours().getFriday()).append("\n")
                    .append("Saturday: ").append(park.getOperatingHours()
                            .get(0).getStandardHours().getSaturday()).append("\n")
                    .append("Sunday: ").append(park.getOperatingHours()
                            .get(0).getStandardHours().getSunday()).append("\n");

            parkHours.setText(opStringBuilder);

            StringBuilder topicStringBuilder = new StringBuilder();
            for (int i = 0; i < park.getTopics().size(); i++)
            {
                topicStringBuilder.append(park.getTopics().get(i).getName())
                        .append(" | ");
            }
            parkTopics.setText(topicStringBuilder);

            if (!TextUtils.isEmpty(park.getDirectionsInfo()))
            {
                parkDirections.setText(park.getDirectionsInfo());
            }
            else
            {
                parkDirections.setText(R.string.direction_unavailable);
            }

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