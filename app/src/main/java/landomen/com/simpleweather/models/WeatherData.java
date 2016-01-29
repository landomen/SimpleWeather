package landomen.com.simpleweather.models;

import com.google.gson.annotations.SerializedName;

/**
 * Represents JSON response from REST endpoint.
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
