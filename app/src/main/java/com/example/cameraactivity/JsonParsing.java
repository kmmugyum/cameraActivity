package com.example.cameraactivity;

import android.os.Build;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

public class JsonParsing {
    JSONObject jsonObject;
    JSONArray images;
    JSONObject receipt;
    JSONObject result;
    JSONArray subResults;
    JSONObject names;
    JSONArray items;
    HashMap<String, Integer> hashMap;
    ArrayList<String> arrayList = new ArrayList<>();

    JsonParsing (JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
    public List<String> parseToString() {
        try {
            images = jsonObject.getJSONArray("images");
            receipt = images.getJSONObject(0).getJSONObject("receipt");
            result = receipt.getJSONObject("result");
            subResults = result.getJSONArray("subResults"); // length : 1
            names = subResults.getJSONObject(0);
            items = names.getJSONArray("items");
            for(int i=0; i< items.length(); i++) {
                Log.d("items index " + i, items.getJSONObject(i).getJSONObject("name").getJSONObject("formatted").getString("value"));
                arrayList.add(items.getJSONObject(i).getJSONObject("name").getJSONObject("formatted").getString("value"));
            }
            Log.d("result array", arrayList.toString());

            return arrayList;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
