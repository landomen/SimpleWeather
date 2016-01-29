package landomen.com.simpleweather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import landomen.com.simpleweather.models.WeatherData;
import landomen.com.simpleweather.network.RestClient;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class DetailsActivity extends AppCompatActivity {
    private TextView txtCity, txtDesc, txtTemp, txtHum, txtHumStatic;
    private ProgressBar pbLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // read city name
        Intent intent = getIntent();
        String city = null;
        if (intent != null) {
            city = intent.getStringExtra(MainActivity.EXTRA_CITY);
        }
        if (city == null)
            return;

        txtCity = (TextView) findViewById(R.id.txt_details_city_name);
        txtDesc = (TextView) findViewById(R.id.txt_details_description);
        txtTemp = (TextView) findViewById(R.id.txt_details_temperature);
        txtHum = (TextView) findViewById(R.id.txt_details_humidity);
        txtHumStatic = (TextView) findViewById(R.id.txt_details_humidity_static);
        pbLoading = (ProgressBar) findViewById(R.id.pb_details);

        //get weather details
        Call<WeatherData> weather = RestClient.get().getCityWeather(city);
        weather.enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Response<WeatherData> response, Retrofit retrofit) {
                pbLoading.setVisibility(View.GONE);
                if (response != null && response.body() != null) {
                    WeatherData wd = response.body();
                    // display weather details
                    txtCity.setText(wd.getCity());
                    txtDesc.setText(wd.getWeather()[0].getDescription());
                    txtTemp.setText(String.format("%d°C", (int) Math.round(wd.getMain().getTemp())));
                    txtHum.setText(String.format("%d%s", (int) Math.round(wd.getMain().getHumidity()), "%"));
                    txtHumStatic.setVisibility(View.VISIBLE);
                } else {
                    txtDesc.setText(R.string.details_no_weather);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                pbLoading.setVisibility(View.GONE);
                txtDesc.setText(R.string.details_no_response);
            }
        });
    }
}
