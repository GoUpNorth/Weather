package com.gonnord.weather.ui.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gonnord.weather.R;
import com.gonnord.weather.model.data.Forecast;
import com.gonnord.weather.ui.BaseFragment;
import com.gonnord.weather.ui.IBaseFragment;
import com.gonnord.weather.ui.detail.ForecastDetailFragment;
import com.gonnord.weather.utils.MeasurementSystem;
import com.gonnord.weather.utils.PreferencesUtils;
import com.gonnord.weather.utils.Properties;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by GONNORD_pierreantoine on 27/11/2017.
 */

public class ForecastListFragment extends BaseFragment implements IForecastsListContract.View, IBaseFragment {

    public static final String TAG = ForecastListFragment.class.getSimpleName();

    public static final String FORECAST_LIST_EXTRA = "FORECAST_LIST_EXTRA";

    public static final String FORECAST_TAB_COUNT_ARG = "FORECAST_TAB_COUNT_ARG";

    /**
     * Since the ForecastListFragment is put in the back stack, the forecasts data are saved and reinstated when
     * the fragment is popped out of the stack.
     * If the measurement system was changed, the data are invalid and need to be queried.
     */
    public static final String MEASUREMENT_SYSTEM_EXTRA = "MEASUREMENT_SYSTEM_EXTRA";

    @BindView(R.id.recycler)
    RecyclerView recycler;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.empty_message)
    TextView emptyListMessage;

    List<Forecast> forecasts;

    ForecastsRecyclerAdapter adapter;

    IForecastsListContract.Presenter presenter;

    private int requestForecastsCount = -1;

    private MeasurementSystem system;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        forecasts = new ArrayList<>();
        adapter = new ForecastsRecyclerAdapter(forecasts, this, new ClickHandler());
        presenter = new ForecastListPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_forecast_list, container, false);

        if(this.getArguments() != null && this.getArguments().getInt(FORECAST_TAB_COUNT_ARG) > 0) {
            this.setRequestForecastsCount(getArguments().getInt(FORECAST_TAB_COUNT_ARG));
        }

        if (savedInstanceState != null && requestForecastsCount < 0) {
            if(savedInstanceState.getParcelableArrayList(FORECAST_LIST_EXTRA) != null) {
                forecasts = savedInstanceState.getParcelableArrayList(FORECAST_LIST_EXTRA);
                requestForecastsCount = forecasts.size();
                adapter.clear();
                adapter.addAll(forecasts);
            }
            if(savedInstanceState.containsKey(MEASUREMENT_SYSTEM_EXTRA)) {
                system = (MeasurementSystem) savedInstanceState.getSerializable(MEASUREMENT_SYSTEM_EXTRA);
            }
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle(getString(R.string.fragment_week_forecasts));

        ButterKnife.bind(this, view);

        recycler.addItemDecoration(new DividerItemDecoration(this.getContext(),
                DividerItemDecoration.VERTICAL));
        recycler.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recycler.setLayoutManager(linearLayoutManager);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshForecast();
            }
        });
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorPrimary);

        if(forecasts.size() == 0 || checkMeasurementSystemHasChanged() || forecasts.size() != requestForecastsCount) {
            refreshForecast();
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
    public void onDestroyView() {
        this.system = PreferencesUtils.getMeasurementSystem(getContext());
        super.onDestroyView();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(forecasts != null) {
            outState.putParcelableArrayList(FORECAST_LIST_EXTRA, new ArrayList<>(forecasts));
        }
        if(system != null) {
            outState.putSerializable(MEASUREMENT_SYSTEM_EXTRA, system);
        }
    }


    @Override
    public void onDestroy() {
        this.presenter = null;
        this.adapter = null;
        super.onDestroy();
    }

    /**
     * Checks if the measurement system has been changed since the last time the fragment was put in the back stack.
     * @return true if the system has changed, false otherwise
     */
    private boolean checkMeasurementSystemHasChanged() {
        MeasurementSystem currentSystem = PreferencesUtils.getMeasurementSystem(getContext());
        return this.system != null && !this.system.equals(currentSystem);
    }

    /**
     * IForecastsListContract.View implementation
     */

    @Override
    public void displayForecasts(List<Forecast> list) {
        forecasts = list;
        adapter.clear();
        adapter.addAll(list);
        emptyListMessage.setVisibility(View.GONE);
        system = PreferencesUtils.getMeasurementSystem(getContext());
    }


    @Override
    public void showError(String message) {
        if(this.forecasts.size() == 0) {
            emptyListMessage.setVisibility(View.VISIBLE);
        }
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }


    @Override
    public void showProgressBar(boolean show) {
        this.swipeRefreshLayout.setRefreshing(show);
    }

    /**
     * End IForecastsListContract.View implementation
     */

    private class ClickHandler implements ForecastsRecyclerAdapter.ViewHolder.IViewHolderClickHandler {

        @Override
        public void onClick(ForecastsRecyclerAdapter.ViewHolder viewHolder) {
            Forecast forecast = forecasts.get(viewHolder.getAdapterPosition());

            Bundle args = new Bundle();
            args.putParcelable(ForecastDetailFragment.FORECAST_SERIALIZABLE_EXTRA, forecast);

            if(fragmentManager != null) {
                fragmentManager.displayFragment(ForecastDetailFragment.class, args, true, false);
                if(getArguments() != null) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(FORECAST_TAB_COUNT_ARG, -1);
                    getArguments().putAll(bundle);
                }
            }
        }
    }

    /**
     * IBaseFragment Implementation
     */

    @Override
    public void onNetworkRecover() {
        if (this.forecasts.size() == 0 || checkMeasurementSystemHasChanged()) {
            Log.i(TAG, "Refresh after network loss");
            refreshForecast();
        }
    }

    @Override
    public void refreshForecast() {
        int count = this.requestForecastsCount;
        if(count < 0) {
            count = Properties.DEFAULT_REQUESTED_FORECASTS_COUNT;
        }
        presenter.getForecast(getContext(), count);
        Log.i(TAG, "Refreshing forecast");
    }

    @Override
    public void clearForecast() {
        forecasts = null;
    }

    /**
     * End IBaseFragment implementation
     */

    public void setRequestForecastsCount(int value) {
        this.requestForecastsCount = value;
    }
}
