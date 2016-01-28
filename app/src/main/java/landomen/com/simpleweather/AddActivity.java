package landomen.com.simpleweather;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_CITY_NAME = "landomen.com.simpleweather.CityName";

    private TextInputLayout nameWrapper;
    private Button btnAddCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        nameWrapper = (TextInputLayout) findViewById(R.id.text_input_city_name);
        btnAddCity = (Button) findViewById(R.id.btn_add_city);
        btnAddCity.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String cityName = nameWrapper.getEditText().getText().toString();
        if (cityName.trim().isEmpty()) {
            nameWrapper.setError("Not a valid name!");
        } else {
            // exit activity and send back data
            Intent resultIntent = new Intent();
            resultIntent.putExtra(EXTRA_CITY_NAME, cityName);
            setResult(RESULT_OK, resultIntent);
            finish();
        }
    }
}
