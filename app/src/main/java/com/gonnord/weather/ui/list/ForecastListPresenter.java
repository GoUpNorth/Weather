package com.gonnord.weather.ui.list;

import android.content.Context;
import android.os.Handler;

import com.gonnord.weather.R;
import com.gonnord.weather.model.ForecastProvider;
import com.gonnord.weather.model.ForecastSource;
import com.gonnord.weather.model.data.Response;
import com.gonnord.weather.ui.BasePresenter;

/**
 * Created by GONNORD_pierreantoine on 26/11/2017.
 */

public class ForecastListPresenter extends BasePresenter<IForecastsListContract.View> implements IForecastsListContract.Presenter {

    private IForecastsListContract.View view;

    private ForecastProvider provider;

    // To execute callbacks on the UI thread
    private Handler handler;

    public ForecastListPresenter(IForecastsListContract.View view) {
        this.view = view;
        provider = new ForecastProvider();
        handler = new Handler();
    }


    @Override
    public void getWeekForecast(final Context context) {

        view.showProgressBar();

        if(provider != null) {
            provider.getWeatherForecasts(context, new ForecastSource.ForecastRequestCallback() {
                @Override
                public void onSuccess(final Response response) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            view.displayForecasts(response.getForecasts());
                            view.hideProgressBar();
                        }
                    });
                }

                @Override
                public void onFailure(final Throwable throwable) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            view.showError(throwable.getMessage());
                            view.hideProgressBar();
                        }
                    });
                }

                @Override
                public void onNetworkFailure() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            view.showError(context.getResources().getString(R.string.network_error_message));
                            view.hideProgressBar();
                        }
                    });
                }
            });
        }
    }
}
