package com.getlosthere.weatherme.activities;

import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.getlosthere.weatherme.R;
import com.getlosthere.weatherme.models.WeatherPoint;

public class WeatherDetailActivity extends AppCompatActivity {
    private WeatherPoint weatherPoint;
    private TextView tvWeekday;
    private TextView tvDate;
    private TextView tvMaxTemp;
    private TextView tvMinTemp;
    private TextView tvHumidity;
    private TextView tvWind;
    private TextView tvPressure;
    private ImageView ivWeatherImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_detail);
        weatherPoint = (WeatherPoint) getIntent().getSerializableExtra("weather_point");

        tvWeekday = (TextView) findViewById(R.id.tvWeekday);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvMaxTemp = (TextView) findViewById(R.id.tvMaxTemp);
        tvMinTemp = (TextView) findViewById(R.id.tvMinTemp);
        tvHumidity = (TextView) findViewById(R.id.tvHumidity);
        tvWind = (TextView) findViewById(R.id.tvWind);
        tvPressure = (TextView) findViewById(R.id.tvPressure);
        ivWeatherImage = (ImageView) findViewById(R.id.ivWeatherImage);

        populateWeatherData();
    }

    private void populateWeatherData(){
        tvWeekday.setText(weatherPoint.getDateWeekday());
        tvDate.setText(weatherPoint.getDateMonthDay());
        tvMaxTemp.setText(weatherPoint.getMaxTemp());
        tvMinTemp.setText(weatherPoint.getMinTemp());
        tvHumidity.setText(weatherPoint.getHumidity());
        tvWind.setText(weatherPoint.getWind() + " " + weatherPoint.getDirection());
        tvPressure.setText(weatherPoint.getPressure());

        ivWeatherImage.setImageDrawable(ResourcesCompat.getDrawable(getResources(),weatherPoint.getIconArtResource(),null));
    }
}
