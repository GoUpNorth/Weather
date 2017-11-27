package com.gonnord.weather.model;

import android.content.Context;
import android.os.AsyncTask;

import com.gonnord.weather.R;
import com.gonnord.weather.model.data.Response;
import com.gonnord.weather.utils.StringUtils;
import com.google.gson.Gson;

import java.io.InputStream;

/**
 * Created by GONNORD_pierreantoine on 26/11/2017.
 */

public class MockedForecastSource extends ForecastSource {

    private static MockedForecastSource source;

    public static MockedForecastSource get() {
        if(source == null) {
            source = new MockedForecastSource();
        }

        return source;
    }

    private LoadMockedResponse task;

    @Override
    public void getWeatherForecast(final ForecastRequestCallback callback, final Context context) {

        if(!isTaskRunningOrPending()) {
            task = new LoadMockedResponse(context, callback);
            task.execute(null, null, null);
        }
    }

    public boolean isTaskRunningOrPending() {
        return task != null && (task.getStatus() == AsyncTask.Status.PENDING || task.getStatus() == AsyncTask.Status.RUNNING);
    }

    @Override
    public void cancelForecastRequests() {
        if(isTaskRunningOrPending()) {
            task.cancel(true);
        }
    }

    private class LoadMockedResponse extends AsyncTask<Void, Void, Void>{

        Context context;

        ForecastRequestCallback callback;

        public LoadMockedResponse(Context context, ForecastRequestCallback callback) {
            this.context = context;
            this.callback = callback;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                InputStream is = context.getResources().openRawResource(R.raw.response);
                String jsonResponse = StringUtils.getStringFromInputStream(is);

                Response response = new Gson().fromJson(jsonResponse, Response.class);

                callback.onSuccess(response);
            } catch (Exception e) {
                callback.onFailure(e);
            }
            return null;
        }

        @Override
        protected void onCancelled() {
            cleanTask();
        }

        @Override
        protected void onCancelled(Void aVoid) {
            cleanTask();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            cleanTask();
        }

        private void cleanTask() {
            context = null;
            callback = null;
        }
    }
}
