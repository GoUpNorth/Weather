package com.gonnord.weather.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gonnord.weather.R;
import com.gonnord.weather.utils.MeasurementSystem;

/**
 * Created by GONNORD_pierreantoine on 28/11/2017.
 */

public abstract class MetricView extends LinearLayout {

    private static float defaultTextSize = 14f;

    private static int defaultTextColor = Color.BLACK;

    private static MeasurementSystem defaultUnit = MeasurementSystem.METRIC;

    protected TextView valueView;

    protected TextView unitView;

    protected MeasurementSystem unit;

    public MetricView(Context context) {
        super(context);
        initView(context, null);
    }

    public MetricView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public MetricView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MetricView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, attrs);
    }


    private void initView(Context context, AttributeSet attrs) {
        this.setOrientation(HORIZONTAL);

        this.valueView = new TextView(context);
        this.unitView = new TextView(context);

        this.valueView.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.valueView.setGravity(Gravity.CENTER_VERTICAL);
        this.valueView.setMaxLines(1);

        this.unitView.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.unitView.setGravity(Gravity.CENTER_VERTICAL);
        this.unitView.setMaxLines(1);

        this.unitView.setPadding(5, 0, 0, 0);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.unitView.setPaddingRelative(5, 0, 0, 0);
        }

        float defaultSize = defaultTextSize * context.getResources().getDisplayMetrics().scaledDensity;

        float textSize = defaultSize;
        int textColor = defaultTextColor;

        if(attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ViewAttrs);
            textSize = a.getDimensionPixelSize(R.styleable.ViewAttrs_textSize, (int) defaultSize);
            textColor = a.getColor(R.styleable.ViewAttrs_textColor, defaultTextColor);
            a.recycle();
        }

        setTextSize(textSize);
        setTextColor(textColor);

        MeasurementSystem system = defaultUnit;
        setUnit(system);

        this.addView(this.valueView);
        this.addView(this.unitView);
    }

    public void setTextSize(float spValue) {
        valueView.setTextSize(TypedValue.COMPLEX_UNIT_PX, spValue);
        unitView.setTextSize(TypedValue.COMPLEX_UNIT_PX, spValue);
    }

    public void setTextColor(int colorValue) {
        valueView.setTextColor(colorValue);
        unitView.setTextColor(colorValue);
    }

    public abstract void setUnit(@NonNull MeasurementSystem tempUnit);

    public void setValue(String valueView) {
        this.valueView.setText(valueView);
    }

    public MeasurementSystem getUnit() {
        return unit;
    }

    public String getValue() {
        return this.valueView.getText().toString();
    }
}
