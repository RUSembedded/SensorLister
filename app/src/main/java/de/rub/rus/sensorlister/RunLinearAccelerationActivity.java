package de.rub.rus.sensorlister;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.widget.TextView;

public class RunLinearAccelerationActivity extends RunSensorActivity {
    private TextView la_x, la_y, la_z;

    protected int getContentViewId(){return R.layout.activity_run_linear_acceleration;}

    protected void setSensorType(){sensorType= Sensor.TYPE_LINEAR_ACCELERATION;}

    protected void setTextViews(){
        la_x = (TextView) findViewById(R.id.la_xLinearAccelerationSensor);
        la_y = (TextView) findViewById(R.id.la_yLinearAccelerationSensor);
        la_z = (TextView) findViewById(R.id.la_zLinearAccelerationSensor);
        accuracyTextView= (TextView) findViewById(R.id.linearAccelerationAccuracy);
    }

    protected void setTextViewDefaultValues(){
        la_x.setText("unknown");
        la_y.setText("unknown");
        la_z.setText("unknown");
        accuracyTextView.setText("unknown");
    }

    protected void setSensorDataDefaultValues(){}

    public void onSensorChanged(SensorEvent theEvent) {
// TODO add low pass filter
        if (theEvent.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
            la_x.setText(String.format("%.2f", theEvent.values[0]));
            la_y.setText(String.format("%.2f", theEvent.values[1]));
            la_z.setText(String.format("%.2f", theEvent.values[2]));
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
