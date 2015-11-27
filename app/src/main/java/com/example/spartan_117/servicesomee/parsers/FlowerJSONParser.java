package com.example.spartan_117.servicesomee.parsers;


import com.example.spartan_117.servicesomee.model.Flower;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FlowerJSONParser {

    public static List<Flower> parseFeed(String content) {
        try {

        JSONArray jsonArray = new JSONArray(content);
        List<Flower> flowerList = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Flower flower = new Flower();

            flower.setProductId(jsonObject.getInt("productId"));
            flower.setName(jsonObject.getString("name"));
            flower.setCategory(jsonObject.getString("category"));
            flower.setInstructions(jsonObject.getString("instructions"));
            flower.setPhoto(jsonObject.getString("photo"));
            flower.setPrice(jsonObject.getDouble("price"));

            flowerList.add(flower);

        }
        return flowerList;
    }
        catch (JSONException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
