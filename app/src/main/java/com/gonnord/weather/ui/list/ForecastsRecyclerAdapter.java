package com.gonnord.weather.ui.list;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gonnord.weather.R;
import com.gonnord.weather.model.data.Forecast;
import com.gonnord.weather.ui.view.TemperatureView;
import com.gonnord.weather.utils.DateUtils;
import com.gonnord.weather.utils.MeasurementSystem;
import com.gonnord.weather.utils.PreferencesUtils;
import com.gonnord.weather.utils.Properties;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Created by GONNORD_pierreantoine on 25/11/2017.
 */

public class ForecastsRecyclerAdapter extends RecyclerView.Adapter<ForecastsRecyclerAdapter.ViewHolder> {

    private List<Forecast> forecasts;

    private Fragment fragment;

    ViewHolder.IViewHolderClickHandler clickHandler;


    public ForecastsRecyclerAdapter(List<Forecast> forecasts, Fragment fragment, ViewHolder.IViewHolderClickHandler clickHandler) {
        this.forecasts = forecasts;
        this.fragment = fragment;
        this.clickHandler = clickHandler;
    }

    @Override
    public ForecastsRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.view_holder_forecasts, parent, false);

        return new ViewHolder(view, clickHandler);
    }

    @Override
    public void onBindViewHolder(ForecastsRecyclerAdapter.ViewHolder holder, int position) {
        MeasurementSystem system = PreferencesUtils.getMeasurementSystem(fragment.getContext());
        Forecast forecast = forecasts.get(position);

        String iconUrl = String.format(Properties.WEATHER_ICONS_URL, forecast.getWeathers().get(0).getIconId());
        RequestOptions options = new RequestOptions().error(R.drawable.ic_error_black_24dp);
        Glide.with(fragment).load(iconUrl).transition(withCrossFade()).apply(options).into(holder.weatherIcon);

        ViewCompat.setTransitionName(holder.itemView, String.valueOf(forecast.getDate()));

        long roundedTemp = Math.round(forecast.getTemperature().getDayTemp());
        holder.temp.setValue(String.valueOf(roundedTemp));
        holder.temp.setUnit(system);
        holder.weatherStatus.setText(forecast.getWeathers().get(0).getDescription());

        Date date = forecast.getDateObject();
        String dateString = DateUtils.getReadableDate(date);
        holder.date.setText(dateString);
    }

    @Override
    public int getItemCount() {
        return forecasts.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.date)
        TextView date;

        @BindView(R.id.weather_status)
        TextView weatherStatus;

        @BindView(R.id.weather_icon)
        ImageView weatherIcon;

        @BindView(R.id.temp)
        TemperatureView temp;

        IViewHolderClickHandler handler;

        public ViewHolder(View itemView, IViewHolderClickHandler clickHandler) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            this.handler = clickHandler;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            handler.onClick(this);
        }

        interface IViewHolderClickHandler {
            void onClick(ViewHolder viewHolder);
        }
    }

    public void clear() {
        int size = getItemCount();
        forecasts.clear();
        notifyItemRangeRemoved(0, size);
    }

    public void addAll(List<Forecast> forecasts) {
        int prevSize = getItemCount();
        this.forecasts.addAll(forecasts);
        notifyItemRangeInserted(prevSize, forecasts.size());
    }
}
