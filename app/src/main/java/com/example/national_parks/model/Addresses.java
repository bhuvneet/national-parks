package com.example.national_parks.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Addresses {
    private String postalCode;

    private String city;

    private String stateCode;

    private String line1;

    private String type;

    private String line;

    private String line2;

    public String getPostalCode() {
        return this.postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateCode() {
        return this.stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getLine1() {
        return this.line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLine() {
        return this.line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getLine2() {
        return this.line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    static Addresses fill(JSONObject jsonObject) throws JSONException
    {
        Addresses entity = new Addresses();
        if(jsonObject.has("postalCode"))
        {
            entity.setPostalCode((jsonObject.getString("postalCode")));
        }
        if(jsonObject.has("city"))
        {
            entity.setCity((jsonObject.getString("city")));
        }
        if(jsonObject.has("stateCode"))
        {
            entity.setStateCode((jsonObject.getString("stateCode")));
        }
        if(jsonObject.has("line1"))
        {
            entity.setPostalCode((jsonObject.getString("line1")));
        }
        if(jsonObject.has("type"))
        {
            entity.setType((jsonObject.getString("type")));
        }
        if(jsonObject.has("line"))
        {
            entity.setLine((jsonObject.getString("line")));
        }
        if(jsonObject.has("line2"))
        {
            entity.setLine2((jsonObject.getString("line2")));
        }
        return entity;
    }

    static List<Addresses> fillList(JSONArray jsonArray) throws JSONException
    {
        if(jsonArray == null || jsonArray.length() == 0)
        {
            return null;
        }
        List<Addresses> list = new ArrayList<Addresses>();
        for (int i = 0; i < jsonArray.length(); i++)
        {
            list.add(fill(jsonArray.getJSONObject(i)));
        }
        return list;
    }
}
