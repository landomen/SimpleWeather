package landomen.com.simpleweather;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import landomen.com.simpleweather.views.adapters.CityAdapter;

public class MainActivity extends AppCompatActivity implements CityAdapter.CityClickListener {
    private static final int RQC_ADD = 539;
    private static final String STATE_CITIES = "cities";
    public static final String EXTRA_CITY = "landomen.com.simpleweather.City";

    private ArrayList<String> cities = new ArrayList<>();
    private TextView txtEmpty;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

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

        // display list or message
        if (cities.size() > 0) {
            txtEmpty.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(STATE_CITIES, cities);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        cities = savedInstanceState.getStringArrayList(STATE_CITIES);
        if (cities == null)
            cities = new ArrayList<>();
        if (cities.size() > 0) {
            txtEmpty.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
        ((CityAdapter) mAdapter).setItems(cities);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
                    }
                    cities.add(cityName);
                    ((CityAdapter) mAdapter).addItem(cityName);
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
}
