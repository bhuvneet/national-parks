package com.example.national_parks.data;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.national_parks.controller.AppController;
import com.example.national_parks.model.Images;
import com.example.national_parks.model.Park;
import com.example.national_parks.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    static List<Park> parkList = new ArrayList<>();
    public static void populateParks(final AsyncResponse callback)
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
                    park.setLatitude(jsonObject.getString("latitude"));
                    park.setLongitude(jsonObject.getString("longitude"));
                    park.setParkCode(jsonObject.getString("parkCode"));
                    park.setStates(jsonObject.getString("states"));

                    // fetch images
                    JSONArray imgList = jsonObject.getJSONArray("images");
                    List<Images> list = new ArrayList<>();
                    for (int j = 0; j < imgList.length(); j++)
                    {
                        Images images = new Images();
                        images.setCredit(imgList.getJSONObject(j).getString("credit"));
                        images.setTitle(imgList.getJSONObject(j).getString("title"));
                        images.setUrl(imgList.getJSONObject(j).getString("url"));

                        list.add(images);   // add images to list
                    }
                    park.setImages(list);
                    park.setWeatherInfo(jsonObject.getString("weatherInfo"));
                    park.setName(jsonObject.getString("name"));
                    park.setDesignation(jsonObject.getString("designation"));

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

    public static List<Park> getParks()
    {
        return parkList;
    }
}
