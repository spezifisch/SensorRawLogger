package com.github.spezifisch.sensorrawlogger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.github.spezifisch.sensorrawlogger.Input.GpsInput;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(new Intent(this, GpsInput.class));
        GpsInput.getInstance().init(getApplicationContext());

        TextView v = (TextView)findViewById(R.id.text_service);
        if (GpsInput.getInstance().isSuccess()) {
            v.setText(getString(R.string.service_running));
        } else {
            v.setText(getString(R.string.service_failed));
        }
    }
}
