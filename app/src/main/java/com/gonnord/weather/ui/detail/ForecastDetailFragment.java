package com.gonnord.weather.ui.detail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gonnord.weather.R;
import com.gonnord.weather.model.data.Forecast;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by GONNORD_pierreantoine on 27/11/2017.
 */

public class ForecastDetailFragment extends Fragment {

    public static final String TAG = ForecastDetailFragment.class.getSimpleName();

    @BindView(R.id.recycler)
    RecyclerView recycler;

    Forecast forecast;
    ForecastDetailRecyclerAdapter adapter;

    public static final String FORECAST_SERIALIZABLE_EXTRA = "FORECAST_SERIALIZABLE_EXTRA";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Bundle args = this.getArguments();
            forecast = (Forecast) args.getSerializable(FORECAST_SERIALIZABLE_EXTRA);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
        adapter = new ForecastDetailRecyclerAdapter(forecast, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_forecast_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        recycler.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recycler.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(getString(R.string.fragment_day_forecast));
    }
}
