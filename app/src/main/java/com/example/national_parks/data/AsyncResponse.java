package com.example.national_parks.data;

import com.example.national_parks.model.Park;

import java.util.List;

// process list containing park projects
public interface AsyncResponse {
    void processParks(List<Park> parks);
}
