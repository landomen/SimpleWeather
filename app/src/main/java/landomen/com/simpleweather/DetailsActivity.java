package landomen.com.simpleweather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {
    private TextView txtCity, txtDesc, txtTemp, txtHum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

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
    }
}
