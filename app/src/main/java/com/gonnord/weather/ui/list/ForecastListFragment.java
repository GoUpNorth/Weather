package com.gonnord.weather.ui.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gonnord.weather.R;
import com.gonnord.weather.model.data.Forecast;
import com.gonnord.weather.ui.ForecastActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pierre-antoinegonnord on 27/11/2017.
 */

public class ForecastListFragment extends Fragment implements IForecastsListContract.View {

    @BindView(R.id.recycler)
    RecyclerView recycler;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.empty_message)
    TextView emptyListMessage;

    List<Forecast> forecasts;

    ForecastsRecyclerAdapter adapter;

    IForecastsListContract.Presenter presenter;

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

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        recycler.addItemDecoration(new DividerItemDecoration(this.getContext(),
                DividerItemDecoration.VERTICAL));
        recycler.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recycler.setLayoutManager(linearLayoutManager);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshForecasts();
            }
        });
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorPrimary);

        if(forecasts.size() == 0) {
            emptyListMessage.setVisibility(View.VISIBLE);
            refreshForecasts();
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
    public void displayForecasts(List<Forecast> list) {
        adapter.clear();
        adapter.addAll(list);
        emptyListMessage.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgressBar() {
        this.swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgressBar() {
        this.swipeRefreshLayout.setRefreshing(false);
    }


    private void refreshForecasts() {
        presenter.getWeekForecast(getContext());
    }

    private class ClickHandler implements ForecastsRecyclerAdapter.ViewHolder.IViewHolderClickHandler {

        @Override
        public void onClick(ForecastsRecyclerAdapter.ViewHolder viewHolder) {
            Forecast forecast = forecasts.get(viewHolder.getAdapterPosition());

            ((ForecastActivity)(ForecastListFragment.this.getActivity())).showForecastDetail(forecast);
        }
    }
}
