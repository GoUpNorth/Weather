package com.gonnord.weather.ui.list;

import android.content.Context;

import com.gonnord.weather.model.data.Forecast;
import com.gonnord.weather.ui.IBasePresenter;

import java.util.List;

/**
 * Created by GONNORD_pierreantoine on 25/11/2017.
 */

public interface IForecastsListContract {

    // TODO create Forecast object model
    interface View {
        void displayForecasts(List<Forecast> list);

        void showError(String message);

        void showProgressBar();

        void hideProgressBar();
    }

    interface Presenter extends IBasePresenter<View> {
        void getWeekForecast(Context context);
    }
}
