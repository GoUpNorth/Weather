package com.gonnord.weather.ui.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;

import com.gonnord.weather.R;
import com.gonnord.weather.utils.MeasurementSystem;

/**
 * Created by GONNORD_pierreantoine on 27/11/2017.
 */

public class TemperatureView extends MetricView {


    public TemperatureView(Context context) {
        super(context);
    }

    public TemperatureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TemperatureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TemperatureView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void setUnit(@NonNull MeasurementSystem tempUnit) {
        this.unit = tempUnit;
        if(tempUnit == MeasurementSystem.METRIC) {
            unitView.setText(this.getContext().getString(R.string.detail_unit_celsius));
        } else {
            unitView.setText(this.getContext().getString(R.string.detail_unit_fahrenheit));
        }
    }
}
