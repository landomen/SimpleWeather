package landomen.com.simpleweather.network;


import landomen.com.simpleweather.models.WeatherData;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Domen on 29. 01. 2016.
 */
public interface WeatherApi {
    @GET("weather?appid=e2694d7b0e4fee43d6ab0e20e7e0cc00&units=metric")
    Call<WeatherData> getCityWeather(@Query("q") String cityName);
}
