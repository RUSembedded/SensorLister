package de.rub.rus.sensorlister;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.widget.TextView;

// TODO check what style is for comments of derived classes
public class RunAccelerometerSensorActivity extends RunSensorActivity {
    private TextView a_x, a_y, a_z;

    protected int getContentViewId(){return R.layout.activity_run_accelerometer;}

    protected void setSensorType(){sensorType= Sensor.TYPE_ACCELEROMETER;}

    protected void setTextViews(){
        a_x= (TextView) findViewById(R.id.a_xAccelerationSensor);
        a_y= (TextView) findViewById(R.id.a_yAccelerationSensor);
        a_z= (TextView) findViewById(R.id.a_zAccelerationSensor);
        accuracyTextView= (TextView) findViewById(R.id.accelerometerAccuracy);
    }

    protected void setTextViewDefaultValues(){
            a_x.setText("unknown");
            a_y.setText("unknown");
            a_z.setText("unknown");
            accuracyTextView.setText("unknown");
    }

    protected void setSensorDataDefaultValues(){}

    public void onSensorChanged(SensorEvent theEvent) {
// TODO add low pass filter
        // Extract rotation matrix if event is actually due to change in rotation vector
        if (theEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            a_x.setText(String.format("%.2f", theEvent.values[0]));
            a_y.setText(String.format("%.2f", theEvent.values[1]));
            a_z.setText(String.format("%.2f", theEvent.values[2]));
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
