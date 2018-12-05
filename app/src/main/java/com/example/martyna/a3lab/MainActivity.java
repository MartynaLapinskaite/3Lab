package com.example.martyna.a3lab;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;

    private Button startAndStop;

    private TextView xValue;
    private TextView yValue;
    private TextView zValue;

    private boolean InformationObtained;


    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InformationObtained=false;

        startAndStop=(Button)findViewById(R.id.start_and_stop);
        startAndStop.setOnClickListener(StartAndStopButtonListener);


        xValue=(TextView)findViewById(R.id.x_value);
        yValue=(TextView)findViewById(R.id.y_value);
        zValue=(TextView)findViewById(R.id.z_value);

        senSensorManager=(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer= senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }
    View.OnClickListener StartAndStopButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            if(senAccelerometer==null){
                Toast.makeText(MainActivity.this, "A", Toast.LENGTH_LONG).show();
                return;
            }

            if(InformationObtained){
                startAndStop.setText("B");
                senSensorManager.unregisterListener(MainActivity.this, senAccelerometer);
                InformationObtained=false;
            }
            else{
                senSensorManager.registerListener(MainActivity.this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
                startAndStop.setText("C");
                InformationObtained=true;
            }
        }
    };

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor mySensor = event.sensor;
        if(mySensor.getType()==Sensor.TYPE_ACCELEROMETER){
            xValue.setText(String.valueOf(event.values[0]));
            yValue.setText(String.valueOf(event.values[1]));
            zValue.setText(String.valueOf(event.values[2]));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onPause(){
        super.onPause();
        if(senAccelerometer!=null){
            senSensorManager.unregisterListener(MainActivity.this, senAccelerometer);
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(senAccelerometer!=null && InformationObtained){
            senSensorManager.registerListener(MainActivity.this, senAccelerometer,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }



}
