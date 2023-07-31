package com.example.national_parks.data;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.national_parks.controller.AppController;
import com.example.national_parks.model.Park;
import com.example.national_parks.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    static List<Park> parkList = new ArrayList<>();
    public static void getParks(final AsyncResponse callback)
    {
        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.GET, Util.PARKS_URL, null, response ->
        {
            try{
                JSONArray jsonArray = response.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++)
                {
                    Park park = new Park(); // get data for each park
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    park.setId(jsonObject.getString("id"));
                    park.setFullName(jsonObject.getString("fullName"));
                    parkList.add(park);
                }
                if(null != callback){
                    callback.processParks(parkList);
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

        }, error->{
            error.printStackTrace();
        });

        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
    }
}
