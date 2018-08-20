package cc.wenmin92.androidlearn.service.location;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Locale;

import cc.wenmin92.androidlearn.R;

public class LocationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        TextView tv = findViewById(R.id.tv_text);
        LocationUtil.getLocation(this, new LocationUtil.LocationCallBack() {
            @Override
            public void onLocationSuccess(Location location) {
                tv.setText(String.format(Locale.ROOT, "[%.4f, %.4f]", location.getLatitude(), location.getLongitude()));
            }

            @Override
            public void onLocationFail(String msg) {
                tv.setText(msg);
            }
        });
    }
}
