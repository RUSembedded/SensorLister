package de.rub.rus.sensorlister;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.widget.TextView;

public class RunGyroscopeSensorActivity extends RunSensorActivity {

    private TextView xRate, yRate, zRate;

    protected int getContentViewId(){return R.layout.activity_run_gyroscope;}

    protected void setSensorType(){sensorType= Sensor.TYPE_GYROSCOPE;}

    protected void setTextViews(){
        xRate= (TextView) findViewById(R.id.xGyroscopeSensor);
        yRate= (TextView) findViewById(R.id.yGyroscopeSensor);
        zRate= (TextView) findViewById(R.id.zGyroscopeSensor);
        accuracyTextView= (TextView) findViewById(R.id.gyroscopeAccuracy);
    }

    protected void setTextViewDefaultValues(){
        xRate.setText("unknown");
        yRate.setText("unknown");
        zRate.setText("unknown");
        accuracyTextView.setText("unknown");
    }

    protected void setSensorDataDefaultValues(){}

    public void onSensorChanged(SensorEvent theEvent) {
// TODO add low pass filter
        // Extract rotation matrix if event is actually due to change in rotation vector
        if (theEvent.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            xRate.setText(String.format("%.2f", theEvent.values[0]));
            yRate.setText(String.format("%.2f", theEvent.values[1]));
            zRate.setText(String.format("%.2f", theEvent.values[2]));
        }
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_run_gyroscope, menu);
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
