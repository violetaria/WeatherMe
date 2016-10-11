//package com.getlosthere.weatherme.api_clients;
//
//import android.os.AsyncTask;
//import android.util.Log;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
///**
// * Created by violetaria on 10/11/16.
// */
//
//public class OpenWeatherAPIClient extends AsyncTask<String, Void, String> {
//
//    protected String doInBackground(String... addresses){
//        StringBuilder stringBuilder = new StringBuilder();
//        InputStream inputStream = null;
//        try {
//            URL url = new URL(addresses[0]);
//            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//            urlConnection.connect();
//            inputStream = urlConnection.getInputStream();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//            String line;
//            while ((line = reader.readLine()) != null) {
//                stringBuilder.append(line);
//            }
//            if (inputStream != null)
//                inputStream.close();
//            return stringBuilder.toString();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return stringBuilder.toString();
//    }
//
//    protected void onPostExecute(String result) {
//        try {
//            JSONObject resultObject = new JSONObject(result);
//            Log.d("DEBUG",resultObject.toString());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//}
