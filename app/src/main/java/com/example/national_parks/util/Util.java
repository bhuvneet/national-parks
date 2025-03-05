package com.example.national_parks.util;

import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.national_parks.api_keys.API_KEYS;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Util {

    private static final API_KEYS apiKeys = new API_KEYS();
    private static final String national_parks_api = apiKeys.getNationalParksApiKey();
    public static final String PARKS_URL =
            "https://developer.nps.gov/api/v1/parks?stateCode=&api_key="
                    + national_parks_api;

    public static String getParksUrl(String stateCode)
    {
        if (stateCode == null)
        {
            // either the map is launched for the first time OR no stateCode provided
            return "https://developer.nps.gov/api/v1/parks?stateCode=&api_key="
             + apiKeys.getNationalParksApiKey();
        }
        else
        {
            return "https://developer.nps.gov/api/v1/parks?stateCode="
                    + stateCode + "&api_key="
                    + apiKeys.getNationalParksApiKey();
        }
    }
}


