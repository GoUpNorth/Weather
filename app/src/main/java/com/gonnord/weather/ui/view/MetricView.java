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
import com.gonnord.weather.utils.PreferencesUtils;

/**
 * Created by GONNORD_pierreantoine on 28/11/2017.
 */

public abstract class MetricView extends LinearLayout {

    private static float defaultTextSize = 14f;

    private static int defaultTextColor = Color.BLACK;

    protected TextView value;

    protected TextView unit;

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

        this.value = new TextView(context);
        this.unit = new TextView(context);

        this.value.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.value.setGravity(Gravity.CENTER_VERTICAL);
        this.value.setMaxLines(1);

        this.unit.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.unit.setGravity(Gravity.CENTER_VERTICAL);
        this.unit.setMaxLines(1);

        this.unit.setPadding(15, 0, 0, 0);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.unit.setPaddingRelative(15, 0, 0, 0);
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

        PreferencesUtils.UnitSystem system = PreferencesUtils.UnitSystem.METRIC;
        if (!this.isInEditMode()) {
            system = PreferencesUtils.getUnitSystem(context);
        }
        setUnit(system);

        this.addView(this.value);
        this.addView(this.unit);
    }

    public void setTextSize(float spValue) {
        value.setTextSize(TypedValue.COMPLEX_UNIT_PX, spValue);
        unit.setTextSize(TypedValue.COMPLEX_UNIT_PX, spValue);
    }

    public void setTextColor(int colorValue) {
        value.setTextColor(colorValue);
        unit.setTextColor(colorValue);
    }

    public abstract void setUnit(@NonNull PreferencesUtils.UnitSystem tempUnit);

    public void setValue(String value) {
        this.value.setText(value);
    }
}
