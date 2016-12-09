package de.rub.rus.sensorlister;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.widget.TextView;

public class RunGravitySensorActivity extends RunSensorActivity {
    private TextView g_x, g_y, g_z;

    protected int getContentViewId(){return R.layout.activity_run_gravity_sensor;}

    protected void setSensorType(){sensorType= Sensor.TYPE_GRAVITY;}

    protected void setTextViews(){
        g_x= (TextView) findViewById(R.id.g_xGravitySensor);
        g_y= (TextView) findViewById(R.id.g_yGravitySensor);
        g_z= (TextView) findViewById(R.id.g_zGravitySensor);
        accuracyTextView= (TextView) findViewById(R.id.gravityAccuracy);
    }

    protected void setTextViewDefaultValues(){
        g_x.setText("unknown");
        g_y.setText("unknown");
        g_z.setText("unknown");
        accuracyTextView.setText("unknown");
    }

    protected void setSensorDataDefaultValues(){}

    public void onSensorChanged(SensorEvent theEvent) {
// TODO add low pass filter
        // Extract rotation matrix if event is actually due to change in rotation vector
        if (theEvent.sensor.getType() == Sensor.TYPE_GRAVITY) {
            g_x.setText(String.format("%.2f", theEvent.values[0]));
            g_y.setText(String.format("%.2f", theEvent.values[1]));
            g_z.setText(String.format("%.2f", theEvent.values[2]));
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

