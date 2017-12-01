package com.gonnord.weather.ui.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gonnord.weather.R;
import com.gonnord.weather.model.data.Forecast;
import com.gonnord.weather.ui.BaseFragment;
import com.gonnord.weather.ui.list.ForecastListPresenter;
import com.gonnord.weather.ui.list.IForecastsListContract;
import com.gonnord.weather.utils.Properties;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by GONNORD_pierreantoine on 27/11/2017.
 */

public class ForecastDetailFragment extends BaseFragment implements IForecastsListContract.View {

    public static final String TAG = ForecastDetailFragment.class.getSimpleName();

    @BindView(R.id.recycler)
    RecyclerView recycler;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.empty_message)
    TextView emptyForecastMessage;

    Forecast forecast;
    ForecastDetailRecyclerAdapter adapter;

    IForecastsListContract.Presenter presenter;

    public static final String FORECAST_SERIALIZABLE_EXTRA = "FORECAST_SERIALIZABLE_EXTRA";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ForecastDetailRecyclerAdapter(forecast, this);
        presenter = new ForecastListPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forecast_detail, container, false);

        if (savedInstanceState != null && savedInstanceState.containsKey(FORECAST_SERIALIZABLE_EXTRA)) {
            forecast = savedInstanceState.getParcelable(FORECAST_SERIALIZABLE_EXTRA);
            adapter.add(forecast);
        } else if(getArguments() != null && getArguments().containsKey(FORECAST_SERIALIZABLE_EXTRA)) {
            forecast = this.getArguments().getParcelable(FORECAST_SERIALIZABLE_EXTRA);
            adapter.add(forecast);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle(getString(R.string.fragment_day_forecast));

        ButterKnife.bind(this, view);

        recycler.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recycler.setLayoutManager(linearLayoutManager);

        progressBar.setIndeterminate(true);

        if(forecast == null) {
            refreshForecast();
            emptyForecastMessage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onViewActive(this);
    }

    @Override
    public void onPause() {
        presenter.onViewInactive();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        adapter = null;
        presenter = null;
        super.onDestroy();
    }

    @Override
    public void displayForecasts(List<Forecast> list) {
        if(list != null && list.size() > 0) {
            this.forecast = list.get(list.size()-1);
            adapter.add(this.forecast);
            emptyForecastMessage.setVisibility(View.GONE);
        }
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgressBar(boolean show) {
        if(show) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onNetworkRecover() {
        if(forecast == null) {
            refreshForecast();
        }
    }

    /*
     * The detail fragment wants to refresh the forecast for the currently displayed date.
     * For that it has to calculate the number of the forecasts to query since the API doesn't allow to query by date.
     */
    @Override
    public void refreshForecast() {
        if (forecast != null) {
            long forecastDt = forecast.getDate();
            Calendar fixedParisCalendar = Calendar.getInstance(TimeZone.getTimeZone(Properties.PARIS_TIMEZONE));
            fixedParisCalendar.set(Calendar.HOUR_OF_DAY, 11);
            fixedParisCalendar.set(Calendar.MINUTE, 0);

            long fixedParisDate = fixedParisCalendar.getTime().getTime()/1000;

            int daysToQuery = (int) ((forecastDt - fixedParisDate)/86400);

            if(daysToQuery < 0) {
                return;
            }
            this.presenter.getForecast(getContext(), daysToQuery+1);

        } else {
            this.presenter.getForecast(getContext(), 1);
        }
    }

    @Override
    public void clearForecast() {
        forecast = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if(forecast != null) {
            outState.putParcelable(FORECAST_SERIALIZABLE_EXTRA, forecast);
        }
    }
}
