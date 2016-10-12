package com.getlosthere.weatherme.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.getlosthere.weatherme.R;
import com.getlosthere.weatherme.activities.WeatherDetailActivity;
import com.getlosthere.weatherme.models.WeatherPoint;

import java.util.List;

/**
 * Created by violetaria on 10/11/16.
 */

public class WeatherPointsAdapter extends RecyclerView.Adapter<WeatherPointsAdapter.ViewHolder>{
    private List<WeatherPoint> weatherPoints;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView ivWeatherImage;
        public TextView tvDate;
        public TextView tvWeatherText;
        public TextView tvMaxTemp;
        public TextView tvMinTemp;
        private Context context;

        public ViewHolder(Context context, View itemView) {
            super(itemView);
            ivWeatherImage = (ImageView) itemView.findViewById(R.id.ivWeatherImage);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            tvWeatherText = (TextView) itemView.findViewById(R.id.tvWeatherText);
            tvMaxTemp = (TextView) itemView.findViewById(R.id.tvMaxTemp);
            tvMinTemp = (TextView) itemView.findViewById(R.id.tvMinTemp);
            this.context = context;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                WeatherPoint weatherPoint = weatherPoints.get(position);
                Intent i = new Intent(context,WeatherDetailActivity.class);
                i.putExtra("weather_point",weatherPoint);
                context.startActivity(i);
            }
        }
    }

    public WeatherPointsAdapter(Context context, List<WeatherPoint> weatherPoints){
        this.context = context;
        this.weatherPoints = weatherPoints;
    }


    private Context getContext(){
        return context;
    }

    @Override
    public WeatherPointsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View weatherView = inflater.inflate(R.layout.weather_point, parent, false);

        ViewHolder viewHolder = new ViewHolder(context, weatherView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(WeatherPointsAdapter.ViewHolder viewHolder, int position) {
        WeatherPoint weatherPoint = weatherPoints.get(position);

        ImageView ivWeatherImage = viewHolder.ivWeatherImage;
        ivWeatherImage.setImageDrawable(ResourcesCompat.getDrawable(getContext().getResources(),weatherPoint.getIconICResource(),null));

        TextView tvDate = viewHolder.tvDate;
        tvDate.setText(weatherPoint.getDateWeekday());

        TextView tvWeatherText = viewHolder.tvWeatherText;
        tvWeatherText.setText(weatherPoint.getText());

        TextView tvMaxTemp = viewHolder.tvMaxTemp;
        tvMaxTemp.setText(weatherPoint.getMaxTemp());

        TextView tvMinTemp = viewHolder.tvMinTemp;
        tvMinTemp.setText(weatherPoint.getMinTemp());
    }

    @Override
    public int getItemCount() {
        return weatherPoints.size();
    }
}
