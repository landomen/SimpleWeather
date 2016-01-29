package landomen.com.simpleweather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import landomen.com.simpleweather.views.adapters.CityAdapter;

public class MainActivity extends AppCompatActivity implements CityAdapter.CityClickListener {
    private static final int RQC_ADD = 539;
    private static final String STATE_CITIES = "cities";
    private static final String PREFERENCES_KEY = "ListPreferences";
    public static final String EXTRA_CITY = "landomen.com.simpleweather.City";

    private ArrayList<String> cities;
    private TextView txtEmpty;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ItemTouchHelper itemTouchHelper;
    private SwipeRefreshLayout mRefreshLayout;

    private SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addIntent = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(addIntent, RQC_ADD);
            }
        });

        // get saved list of cities
        mPrefs = getSharedPreferences(PREFERENCES_KEY, MODE_PRIVATE);
        cities = new Gson().fromJson(mPrefs.getString(STATE_CITIES, ""), new TypeToken<ArrayList<String>>() {
        }.getType());
        if (cities == null) {
            cities = new ArrayList<>();
        }

        // empty text message
        txtEmpty = (TextView) findViewById(R.id.main_empty_text);

        // list
        mRecyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new CityAdapter(cities);
        ((CityAdapter) mAdapter).setOnClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        itemTouchHelper = new ItemTouchHelper(cityItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.main_swipe_refresh_layout);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.notifyDataSetChanged();
                mRefreshLayout.setRefreshing(false);
            }
        });
        mRefreshLayout.setEnabled(false);

        // display list or message
        if (cities.size() > 0) {
            txtEmpty.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            mRefreshLayout.setEnabled(true);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // save list of cities
        String json = new Gson().toJson(cities);
        mPrefs.edit().putString(STATE_CITIES, json).apply();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RQC_ADD && resultCode == RESULT_OK) {
            if (data != null) {
                String cityName = data.getStringExtra(AddActivity.EXTRA_CITY_NAME);
                if (cityName != null && !cities.contains(cityName)) {
                    // if list was hidden, show it now
                    if (cities.size() == 0) {
                        txtEmpty.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        mRefreshLayout.setEnabled(true);
                    }
                    cities.add(cityName);
                    ((CityAdapter) mAdapter).addItem(mAdapter.getItemCount(), cityName);
                }
            }
        }
    }

    @Override
    public void onCityClicked(int position) {
        Intent detailsIntent = new Intent(this, DetailsActivity.class);
        detailsIntent.putExtra(EXTRA_CITY, cities.get(position));
        startActivity(detailsIntent);
    }

    /**
     * Left/right swipe listener for RecyclerView.
     */
    private ItemTouchHelper.SimpleCallback cityItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition();
            final String cityToDelete = ((CityAdapter) mAdapter).getItem(position);
            Snackbar snackbar = Snackbar.make(mRecyclerView, R.string.main_city_deleted, Snackbar.LENGTH_LONG)
                    .setAction(R.string.main_undo, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // if list was hidden, show it now
                            if (cities.size() == 0) {
                                txtEmpty.setVisibility(View.GONE);
                                mRecyclerView.setVisibility(View.VISIBLE);
                                mRefreshLayout.setEnabled(true);
                            }
                            // add the deleted item back to the adapter
                            ((CityAdapter) mAdapter).addItem(position, cityToDelete);
                            cities.add(position, cityToDelete);
                        }
                    });
            snackbar.show();
            ((CityAdapter) mAdapter).removeItem(position);
            cities.remove(position);
            //check if list is empty and show message
            if (cities.size() == 0) {
                txtEmpty.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
                mRefreshLayout.setEnabled(false);
            }
        }
    };
}
