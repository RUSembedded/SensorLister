package de.rub.rus.sensorlister;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.widget.TextView;

public class RunLightSensorActivity extends RunSensorActivity {
    private TextView lx;

    protected int getContentViewId(){return R.layout.activity_run_light_sensor;}

    protected void setSensorType(){sensorType= Sensor.TYPE_LIGHT;}

    protected void setTextViews(){
        lx = (TextView) findViewById(R.id.lxLightSensor);
        accuracyTextView= (TextView) findViewById(R.id.lightSensorAccuracy);
    }

    protected void setTextViewDefaultValues(){
        lx.setText("unknown");
        accuracyTextView.setText("unknown");
    }

    protected void setSensorDataDefaultValues(){}

    public void onSensorChanged(SensorEvent theEvent) {
// TODO add low pass filter
        // Extract rotation matrix if event is actually due to change in rotation vector
        if (theEvent.sensor.getType() == Sensor.TYPE_LIGHT) {
            lx.setText(String.format("%.2f", theEvent.values[0]));
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
