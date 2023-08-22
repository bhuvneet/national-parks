package com.example.national_parks.data;

import android.app.Activity;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.national_parks.controller.AppController;
import com.example.national_parks.model.Activities;
import com.example.national_parks.model.EntranceFees;
import com.example.national_parks.model.Images;
import com.example.national_parks.model.OperatingHours;
import com.example.national_parks.model.Park;
import com.example.national_parks.model.StandardHours;
import com.example.national_parks.model.Topics;
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

                    // setup activities
                    JSONArray activityArray = jsonObject.getJSONArray("activities");
                    List<Activities> activityList = new ArrayList<>();
                    for (int j = 0; j < activityArray.length(); j++)
                    {
                        Activities activities = new Activities();
                        activities.setId(activityArray.getJSONObject(j).getString("id"));
                        activities.setName(activityArray.getJSONObject(j).getString("name"));
                        activityList.add(activities);
                    }
                    park.setActivities(activityList);

                    // topics
                    JSONArray topicsArray = jsonObject.getJSONArray("topics");
                    List<Topics> topicsList = new ArrayList<>();
                    for(int j = 0; j < topicsArray.length(); j++)
                    {
                        Topics topics = new Topics();
                        topics.setId(topicsArray.getJSONObject(j).getString("id"));
                        topics.setName(topicsArray.getJSONObject(j).getString("name"));
                        topicsList.add(topics);
                    }
                    park.setTopics(topicsList);

                    // hours
                    JSONArray hoursArray = jsonObject.getJSONArray("operatingHours");
                    List<OperatingHours> operatingHours = new ArrayList<>();
                    for(int j = 0; j < hoursArray.length(); j++)
                    {
                        OperatingHours opHours = new OperatingHours();
                        opHours.setDescription(hoursArray.getJSONObject(j).getString("description"));
                        StandardHours standardHours = new StandardHours();
                        JSONObject hours = hoursArray.getJSONObject(j).getJSONObject("standardHours");

                        standardHours.setMonday(hours.getString("monday"));
                        standardHours.setTuesday(hours.getString("tuesday"));
                        standardHours.setWednesday(hours.getString("wednesday"));
                        standardHours.setThursday(hours.getString("thursday"));
                        standardHours.setFriday(hours.getString("friday"));
                        standardHours.setSaturday(hours.getString("saturday"));
                        standardHours.setSunday(hours.getString("sunday"));

                        opHours.setStandardHours(standardHours);
                        operatingHours.add(opHours);
                    }
                    park.setOperatingHours(operatingHours);

                    park.setDirectionsInfo(jsonObject.getString("directionsInfo"));

                    park.setDescription(jsonObject.getString("description"));

                    JSONArray entranceFeesArray = jsonObject.getJSONArray("entranceFees");
                    List<EntranceFees> entranceFees = new ArrayList<>();
                    for (int j = 0; j < entranceFeesArray.length(); j++) {
                        EntranceFees fees = new EntranceFees();
                        fees.setCost(entranceFeesArray.getJSONObject(j).getString("cost"));
                        fees.setDescription(entranceFeesArray.getJSONObject(j).getString("description"));
                        fees.setTitle(entranceFeesArray.getJSONObject(j).getString("title"));
                        entranceFees.add(fees);
                    }
                    park.setEntranceFees(entranceFees);
                    park.setWeatherInfo(jsonObject.getString("weatherInfo"));

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
