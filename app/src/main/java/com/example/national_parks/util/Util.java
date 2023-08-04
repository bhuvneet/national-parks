package com.example.national_parks.util;

import com.example.national_parks.api_keys.API_KEYS;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Util {
    private static final String national_parks_api = API_KEYS.getNationalParksApiKey();
    public static final String PARKS_URL =
            "https://developer.nps.gov/api/v1/parks?stateCode=&api_key=" + national_parks_api;
}


