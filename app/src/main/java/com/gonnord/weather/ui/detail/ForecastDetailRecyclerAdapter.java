package com.gonnord.weather.ui.detail;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.gonnord.weather.R;
import com.gonnord.weather.model.data.Forecast;
import com.gonnord.weather.ui.view.SpeedView;
import com.gonnord.weather.ui.view.TemperatureView;
import com.gonnord.weather.utils.DateUtils;
import com.gonnord.weather.utils.Properties;
import com.gonnord.weather.utils.WindUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Created by GONNORD_pierreantoine on 26/11/2017.
 */

public class ForecastDetailRecyclerAdapter extends RecyclerView.Adapter<ForecastDetailRecyclerAdapter.ViewHolder> {

    private Forecast forecast;

    private Fragment fragment;


    public ForecastDetailRecyclerAdapter(Forecast forecast, Fragment fragment) {
        this.forecast = forecast;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.view_holder_forecast_detail, parent, false);

        return new ForecastDetailRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // weather icon
        String iconUrl = String.format(Properties.WEATHER_ICONS_URL, forecast.getWeathers().get(0).getIconId());
        RequestOptions options = new RequestOptions().error(R.drawable.ic_error_black_24dp);
        Glide.with(fragment).load(iconUrl).transition(withCrossFade()).apply(options).into(holder.weatherIcon);

        // Weather status
        holder.weatherStatus.setText(forecast.getWeathers().get(0).getDescription());

        // Date
        Date date = forecast.getDateObject();
        String dateString = DateUtils.getReadableDate(date);
        holder.date.setText(dateString);

        // Day temp
        holder.dayTemp.setValue(String.valueOf(forecast.getTemperature().getDayTemp()));

        // Night temp
        holder.nightTemp.setValue(String.valueOf(forecast.getTemperature().getNightTemp()));

        // Humidity
        holder.humidity.setText(String.valueOf(forecast.getHumidity()));

        // Pressure
        holder.pressure.setText(String.valueOf(forecast.getPressure()));

        // Cloudiness
        holder.cloudiness.setText(String.valueOf(forecast.getClouds()));

        // Temp chart
        initializeTempChart(holder.tempChart);

        // Max temp
        holder.tempMax.setValue(String.valueOf(forecast.getTemperature().getMaxTemp()));

        // Min temp
        holder.tempMin.setValue(String.valueOf(forecast.getTemperature().getMinTemp()));

        // Wind status
        String windStatus = WindUtils.getWindDescriptionBySpeed(fragment.getContext(), forecast.getSpeed());
        holder.windStatus.setText(windStatus);

        // Wind speed
        holder.windSpeed.setValue(String.valueOf(forecast.getSpeed()));

        // Wind orientation
        String windOrientation = WindUtils.getWindOrientationByDegree(fragment.getContext(), forecast.getDeg());
        holder.windOrientation.setText(windOrientation);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.date)
        TextView date;

        @BindView(R.id.day_temp)
        TemperatureView dayTemp;

        @BindView(R.id.night_temp)
        TemperatureView nightTemp;

        @BindView(R.id.weather_icon)
        ImageView weatherIcon;

        @BindView(R.id.weather_status)
        TextView weatherStatus;

        @BindView(R.id.humidity_value)
        TextView humidity;

        @BindView(R.id.pressure_value)
        TextView pressure;

        @BindView(R.id.cloudiness_value)
        TextView cloudiness;

        @BindView(R.id.temp_chart)
        BarChart tempChart;

        @BindView(R.id.temp_max)
        TemperatureView tempMax;

        @BindView(R.id.temp_min)
        TemperatureView tempMin;

        @BindView(R.id.wind_status)
        TextView windStatus;

        @BindView(R.id.wind_speed)
        SpeedView windSpeed;

        @BindView(R.id.wind_orientation)
        TextView windOrientation;


        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    private void initializeTempChart(BarChart tempChart) {
        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, (float) forecast.getTemperature().getMorningTemp(), "morning"));
        entries.add(new BarEntry(1, (float) forecast.getTemperature().getDayTemp(), "day"));
        entries.add(new BarEntry(2, (float) forecast.getTemperature().getEveningTemp(), "evening"));
        entries.add(new BarEntry(3, (float) forecast.getTemperature().getNightTemp(), "night"));

        BarDataSet dataSet = new BarDataSet(entries, "Temperatures"); // add entries to dataset
        dataSet.setColor(R.color.colorAccent);
        dataSet.setValueTextColor(R.color.colorPrimary);

        dataSet.setValueTextSize(12f);

        BarData lineData = new BarData(dataSet);
        tempChart.setData(lineData);
        tempChart.setDescription(null);

        XAxis xval = tempChart.getXAxis();
        xval.setDrawLabels(true);
        xval.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                switch (Math.round(value)) {
                    case 0:
                        return "morning";
                    case 1:
                        return "day";
                    case 2:
                        return "evening";
                    case 3:
                        return "night";
                    default:
                        return "";
                }
            }
        });
        tempChart.invalidate();
    }
}
