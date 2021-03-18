package com.matrixdeveloper.tajika.location;

import com.matrixdeveloper.tajika.R;
import com.matrixdeveloper.tajika.app.App;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class PlaceApi {

    public ArrayList<String> autoComplete(String input) {
        ArrayList arrayListTitle = new ArrayList();

        HttpURLConnection connection = null;
        StringBuilder jsonResult = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/autocomplete/json?");
            sb.append("input=").append(input);
            sb.append(App.getContext().getString(R.string.map_api_key));
            URL url = new URL(sb.toString());
            connection = (HttpURLConnection) url.openConnection();
            InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());

            int read;

            char[] buff = new char[1024];
            while ((read = inputStreamReader.read(buff)) != -1) {
                jsonResult.append(buff, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonResult.toString());
            JSONArray prediction = jsonObject.getJSONArray("predictions");

            for (int i = 0; i < prediction.length(); i++) {
                arrayListTitle.add(prediction.getJSONObject(i).getString("description"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return arrayListTitle;
    }
}
