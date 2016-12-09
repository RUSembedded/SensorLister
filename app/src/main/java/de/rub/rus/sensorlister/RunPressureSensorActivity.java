package de.rub.rus.sensorlister;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.widget.TextView;

public class RunPressureSensorActivity extends RunSensorActivity {
    private TextView pressure;

    protected int getContentViewId(){return R.layout.activity_run_pressure_sensor;}

    protected void setSensorType(){sensorType= Sensor.TYPE_PRESSURE;}

    protected void setTextViews(){
        pressure = (TextView) findViewById(R.id.pPressureSensor);
        accuracyTextView= (TextView) findViewById(R.id.pressureSensorAccuracy);
    }

    protected void setTextViewDefaultValues(){
        pressure.setText("unknown");
        accuracyTextView.setText("unknown");
    }

    protected void setSensorDataDefaultValues(){}

    public void onSensorChanged(SensorEvent theEvent) {
        if (theEvent.sensor.getType() == Sensor.TYPE_PRESSURE) {
            pressure.setText(String.format("%.2f", theEvent.values[0]));
        }
    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_run_accelerometer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/
}
