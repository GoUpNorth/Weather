package com.gonnord.weather.network;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.gonnord.weather.model.ForecastSource;
import com.gonnord.weather.utils.MeasurementSystem;
import com.gonnord.weather.utils.PreferencesUtils;
import com.gonnord.weather.utils.Properties;
import com.gonnord.weather.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by GONNORD_pierreantoine on 26/11/2017.
 */

public class OpenWeatherMapForecastSource extends ForecastSource {

    private static final String TAG = OpenWeatherMapForecastSource.class.getSimpleName();

    private static final String OPEN_WEATHER_ENTRY_POINT = "https://api.openweathermap.org/data/2.5/forecast/daily?q=Paris&units=%s&cnt=%d&appid=";

    private static final String REQUEST_TAG = "REQUEST_TAG";

    private static OpenWeatherMapForecastSource forecastSource;

    private OkHttpClient okHttpClient;

    public static synchronized OpenWeatherMapForecastSource get() {
        if (forecastSource == null) {
            forecastSource = new OpenWeatherMapForecastSource();
        }
        return forecastSource;
    }

    private OpenWeatherMapForecastSource() {
        okHttpClient = new OkHttpClient();
    }

    @Override
    public void getWeatherForecast(final ForecastRequestCallback callback, Context context, int count) {
        if(count < 1) {
            count = Properties.DEFAULT_REQUESTED_FORECASTS_COUNT;
        }

        MeasurementSystem unit = PreferencesUtils.getMeasurementSystem(context);

        String url = String.format(OPEN_WEATHER_ENTRY_POINT, unit.getCode(), count).concat(Properties.OPEN_WEATHER_APP_ID);

        final Request httpRequest = new Request.Builder()
                .url(url)
                .tag(REQUEST_TAG)
                .get()
                .build();

        okHttpClient.newCall(httpRequest).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                if(!call.isCanceled()) {
                    call.cancel();
                    callback.onNetworkFailure();
                }
                Log.e(TAG, e.getMessage(), e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                switch(response.code()) {
                    case 200:
                        try {
                            InputStream is = response.body().byteStream();
                            String body = StringUtils.getStringFromInputStream(is);
                            com.gonnord.weather.model.data.Response weatherResponse = new Gson().fromJson(body, com.gonnord.weather.model.data.Response.class);

                            callback.onSuccess(weatherResponse);
                        } catch (NullPointerException | JsonSyntaxException e) {
                            callback.onFailure(e);
                        }
                        break;
                    default:
                        callback.onFailure(new Throwable(response.message()));
                        break;
                }
            }
        });
    }

    @Override
    public void cancelForecastRequests() {
        int transactionsNumber = 0;
        if(okHttpClient != null) {
            transactionsNumber += okHttpClient.dispatcher().queuedCallsCount();
            transactionsNumber += okHttpClient.dispatcher().runningCallsCount();
            for(Call call: okHttpClient.dispatcher().queuedCalls()) {
                if (call.request().tag().equals(REQUEST_TAG)) {
                    call.cancel();
                    Log.i(TAG, "Queued call cancelled");
                }
            }

            for(Call call: okHttpClient.dispatcher().runningCalls()) {
                if (call.request().tag().equals(REQUEST_TAG)) {
                    call.cancel();
                    Log.i(TAG, "Running call cancelled");
                }
            }
        }

        Log.i(TAG, String.format("%d requests cancelled", transactionsNumber));
    }
}
