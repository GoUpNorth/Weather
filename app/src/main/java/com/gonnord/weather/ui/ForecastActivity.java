package com.gonnord.weather.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;

import com.gonnord.weather.R;
import com.gonnord.weather.ui.detail.ForecastDetailFragment;
import com.gonnord.weather.ui.list.ForecastListFragment;
import com.gonnord.weather.utils.MeasurementSystem;
import com.gonnord.weather.utils.NetworkUtils;
import com.gonnord.weather.utils.PreferencesUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by GONNORD_pierreantoine on 26/11/2017.
 */

public class ForecastActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String SAVED_FRAGMENT_EXTRA = "SAVED_FRAGMENT_EXTRA";

    SwitchCompat switchCompat;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        Menu menu = navigationView.getMenu();
        MenuItem menuItem = menu.findItem(R.id.nav_switch);
        View actionView = MenuItemCompat.getActionView(menuItem);
        switchCompat = (SwitchCompat) actionView.findViewById(R.id.switch_button);
        initToggleSwitch();

        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState != null) {
            //Restore the fragment's instance
            Fragment fragment = getSupportFragmentManager().getFragment(savedInstanceState, SAVED_FRAGMENT_EXTRA);
            this.displayFragment(fragment, null, false, false);
        } else {
            this.displayFragment(ForecastListFragment.class, null, false, false);
        }
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * OnNavigationItemSelected implementation
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_today) {
            if(!(fragment instanceof ForecastDetailFragment)) {
                displayFragment(ForecastDetailFragment.class, null, false, true);
            } else {
                fragment.clearForecast();
                fragment.refreshForecast();
            }
        } else if (id == R.id.nav_five_days) {
            if(!(fragment instanceof ForecastListFragment)) {
                displayListFragment(5);
            } else {
                ((ForecastListFragment) fragment).setRequestForecastsCount(5);
                fragment.refreshForecast();
            }
        } else if (id == R.id.nav_one_week) {
            if(!(fragment instanceof ForecastListFragment)) {
                displayListFragment(7);
            } else {
                ((ForecastListFragment) fragment).setRequestForecastsCount(7);
                fragment.refreshForecast();
            }
        } else if (id == R.id.nav_two_weeks) {
            if(!(fragment instanceof ForecastListFragment)) {
                displayListFragment(14);
            } else {
                ((ForecastListFragment) fragment).setRequestForecastsCount(14);
                fragment.refreshForecast();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * display the forecasts list fragment
     * @param forecastCount Number of forecasts to be displayed in the list
     */
    private void displayListFragment(int forecastCount) {
        Bundle bundle = null;
        if(forecastCount > 0) {
            bundle = new Bundle();
            bundle.putInt(ForecastListFragment.FORECASTS_COUNT_EXTRA, forecastCount);
        }
        displayFragment(ForecastListFragment.class, bundle, false, true);
    }


    private void initToggleSwitch() {
        if(switchCompat != null) {
            MeasurementSystem system = PreferencesUtils.getMeasurementSystem(this);
            switchCompat.setChecked(system.getSwitchToggle());

            switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // The network must be available to get the query the forecast data in a
                    // different measurement system
                    boolean networkAvailable = NetworkUtils.isNetworkAvailable(buttonView.getContext());
                    if(!networkAvailable) {
                        ForecastActivity.this.displaySnackbar(getString(R.string.snackbar_title_connectivity), getString(R.string.snackbar_hide_action), Snackbar.LENGTH_INDEFINITE);
                        buttonView.setChecked(!isChecked);
                        return;
                    }

                    if(!isChecked) {
                        PreferencesUtils.setMeasurementSystem(buttonView.getContext(), MeasurementSystem.METRIC);
                    } else {
                        PreferencesUtils.setMeasurementSystem(buttonView.getContext(), MeasurementSystem.IMPERIAL);
                    }
                    if(fragment != null) {
                        fragment.refreshForecast();
                    }
                }
            });
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Save the fragment's instance
        getSupportFragmentManager().putFragment(outState, SAVED_FRAGMENT_EXTRA, (Fragment) fragment);
    }
}
