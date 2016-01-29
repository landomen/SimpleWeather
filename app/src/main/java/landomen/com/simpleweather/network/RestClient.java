package landomen.com.simpleweather.network;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Domen on 29. 01. 2016.
 */
public class RestClient {
    private static final String BASE_URL = " http://api.openweathermap.org/data/2.5/";
    private static WeatherApi weatherApi;

    /**
     * Get instance of WeatherApi.
     *
     * @return
     */
    public static WeatherApi get() {
        if (weatherApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            weatherApi = retrofit.create(WeatherApi.class);
        }
        return weatherApi;
    }
}
