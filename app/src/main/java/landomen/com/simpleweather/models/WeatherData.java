package landomen.com.simpleweather.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Domen on 29. 01. 2016.
 */
public class WeatherData {
    @SerializedName("name")
    private String city;
    private Weather[] weather;
    private Main main;

    public String getCity() {
        return city;
    }

    public Weather[] getWeather() {
        return weather;
    }

    public Main getMain() {
        return main;
    }
}
