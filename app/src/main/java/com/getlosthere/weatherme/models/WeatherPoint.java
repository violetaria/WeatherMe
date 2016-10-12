package com.getlosthere.weatherme.models;

import android.text.TextUtils;

import com.getlosthere.weatherme.R;
import com.getlosthere.weatherme.helpers.DateFormatHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by violetaria on 10/11/16.
 */

public class WeatherPoint implements Serializable {
    private double minTemp;
    private double maxTemp;
    private String icon;
    private String text;
    private double humidity;
    private double wind;
    private double pressure;
    private double degree;
    private long date;

    public String getHumidity() {
        return Long.toString(Math.round(humidity)) + "%";
    }

    public String getPressure() {
        return Long.toString(Math.round(pressure)) + "hPa";
    }

    public String getWind() {
        return Long.toString(Math.round(wind)) + "km/h";
    }

    public String getDirection() {
        int angle = (int) ((degree/22.5)+.5);
        String[] degree_array = new String[]{"N","NNE","NE","ENE","E","ESE", "SE", "SSE","S","SSW","SW","WSW","W","WNW","NW","NNW"};
        return degree_array[(angle % 16)];
    }

    public long getDate() {
        return date;
    }

    public String getDateMonthDay() {
        try {
            return DateFormatHelper.getMonthDay(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getDateWeekday(){
        try {
            return DateFormatHelper.getWeekday(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getIcon() {
        return icon;
    }

    public int getIconArtResource() {
       int imageResource;
        switch(icon) {
            case "01n":
            case "01d":
                imageResource = R.drawable.art_clear;
                break;
            case "02n":
            case "03n":
            case "02d":
            case "03d":
                imageResource = R.drawable.art_light_clouds;
                break;
            case "04n":
            case "04d":
                imageResource = R.drawable.art_clouds;
                break;
            case "09n":
            case "09d":
                imageResource =  R.drawable.art_light_rain;
                break;
            case "10n":
            case "10d":
                imageResource = R.drawable.art_rain;
                break;
            case "11n":
            case "11d":
                imageResource = R.drawable.art_storm;
                break;
            case "13n":
            case "13d":
                imageResource = R.drawable.art_snow;
                break;
            case "50n":
            case "50d":
                imageResource = R.drawable.art_fog;
                break;
            default:
                imageResource = R.mipmap.ic_launcher;
                break;
        }
        return imageResource;
    }

    public int getIconICResource() {
        int imageResource;
        switch(icon) {
            case "01d":
                imageResource = R.drawable.ic_clear;
                break;
            case "02d":
            case "03d":
                imageResource = R.drawable.ic_light_clouds;
                break;
            case "04d":
                imageResource = R.drawable.ic_cloudy;
                break;
            case "09d":
                imageResource =  R.drawable.ic_light_rain;
                break;
            case "10d":
                imageResource = R.drawable.ic_rain;
                break;
            case "11d":
                imageResource = R.drawable.ic_storm;
                break;
            case "13d":
                imageResource = R.drawable.ic_snow;
                break;
            case "50d":
                imageResource = R.drawable.ic_fog;
                break;
            default:
                imageResource = R.mipmap.ic_launcher;
                break;
        }
        return imageResource;
    }


    public String getMaxTemp() {
        return Long.toString(Math.round(maxTemp)) + "°";
    }

    public String getMinTemp() {
        return Long.toString(Math.round(minTemp)) + "°";
    }

    public String getText() {
        return text;
    }

    public WeatherPoint(){

    }

    public static WeatherPoint currentFromJSONObject(JSONObject jsonObject){
        WeatherPoint weatherPoint = new WeatherPoint();
        try {
            JSONObject weatherObject = jsonObject.getJSONArray("weather").getJSONObject(0);
            weatherPoint.icon = weatherObject.getString("icon");
            weatherPoint.text = weatherObject.getString("main");
            JSONObject mainObject = jsonObject.getJSONObject("main");
            weatherPoint.maxTemp = mainObject.getDouble("temp_max");
            weatherPoint.minTemp = mainObject.getDouble("temp_min");
            weatherPoint.humidity = mainObject.getDouble("humidity");
            weatherPoint.pressure = mainObject.getDouble("pressure");
            JSONObject windObject = jsonObject.getJSONObject("wind");
            weatherPoint.wind = windObject.getDouble("speed");
            weatherPoint.degree = windObject.getDouble("deg");
            weatherPoint.date = jsonObject.getLong("dt");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return weatherPoint;
    }

    public static WeatherPoint forecastFromJSONObject(JSONObject jsonObject){
        WeatherPoint weatherPoint = new WeatherPoint();
        try {
            JSONObject weatherObject = jsonObject.getJSONArray("weather").getJSONObject(0);
            weatherPoint.icon = weatherObject.getString("icon");
            weatherPoint.text = weatherObject.getString("main");
            JSONObject tempObject = jsonObject.getJSONObject("temp");
            weatherPoint.maxTemp = tempObject.getDouble("max");
            weatherPoint.minTemp = tempObject.getDouble("min");
            weatherPoint.humidity = jsonObject.getDouble("humidity");
            weatherPoint.pressure = jsonObject.getDouble("pressure");
            weatherPoint.wind = jsonObject.getDouble("speed");
            weatherPoint.degree = jsonObject.getDouble("deg");
            weatherPoint.date = jsonObject.getLong("dt");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return weatherPoint;
    }

    public static ArrayList<WeatherPoint> forecastFromJSONArray(JSONArray jsonArray) {
        ArrayList<WeatherPoint> weatherPoints = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++){
            try {
                JSONObject weatherPointJSONObject = jsonArray.getJSONObject(i);
                WeatherPoint weatherPoint = WeatherPoint.forecastFromJSONObject(weatherPointJSONObject);
                // don't add today's date which is returned back in the forecasted data
                if (weatherPoint != null && !TextUtils.equals(weatherPoint.getDateWeekday(),"Today")) {
                    weatherPoints.add(weatherPoint);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
        }
        return weatherPoints;
    }
}
