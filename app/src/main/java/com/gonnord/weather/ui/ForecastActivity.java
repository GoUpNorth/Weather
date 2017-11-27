package com.gonnord.weather.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gonnord.weather.R;
import com.gonnord.weather.model.data.Forecast;
import com.gonnord.weather.ui.detail.ForecastDetailFragment;
import com.gonnord.weather.ui.list.ForecastListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by GONNORD_pierreantoine on 26/11/2017.
 */

public class ForecastActivity extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecasts_list);

        this.setTitle(getString(R.string.activity_week_forecasts));

        showForecastList();
    }

    public void showForecastList() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(
                ForecastListFragment.class.getSimpleName());
        if (fragment == null) {
            try {
                fragment = ForecastListFragment.class.newInstance();
            } catch (InstantiationException e) {
                throw new RuntimeException("New Fragment should have been created", e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("New Fragment should have been created", e);
            }
        }

        fragmentTransaction.replace(R.id.fragmentContainer, fragment,
                ForecastListFragment.class.getSimpleName());
        fragmentTransaction.commit();
    }

    public void showForecastDetail(Forecast forecast) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(
                ForecastDetailFragment.class.getSimpleName());
        if (fragment == null) {
            try {
                fragment = ForecastDetailFragment.class.newInstance();
            } catch (InstantiationException e) {
                throw new RuntimeException("New Fragment should have been created", e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("New Fragment should have been created", e);
            }
        }

        Bundle args = new Bundle();
        args.putSerializable(ForecastDetailFragment.FORECAST_SERIALIZABLE_EXTRA, forecast);
        fragment.setArguments(args);

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.fragmentContainer, fragment,
                ForecastListFragment.class.getSimpleName());
        fragmentTransaction.commit();
    }

}
