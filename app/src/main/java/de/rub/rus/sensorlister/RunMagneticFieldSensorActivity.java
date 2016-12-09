package de.rub.rus.sensorlister;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.widget.TextView;

public class RunMagneticFieldSensorActivity extends RunSensorActivity {
    private TextView mf_x, mf_y, mf_z;

    protected int getContentViewId() {
        return R.layout.activity_run_magnetic_field_sensor;
    }

    protected void setSensorType() {
        sensorType = Sensor.TYPE_MAGNETIC_FIELD;
    }

    protected void setTextViews() {
        mf_x = (TextView) findViewById(R.id.mf_xMagneticFieldSensor);
        mf_y = (TextView) findViewById(R.id.mf_yMagneticFieldSensor);
        mf_z = (TextView) findViewById(R.id.mf_zMagneticFieldSensor);
        accuracyTextView = (TextView) findViewById(R.id.magneticFieldSensorAccuracy);
    }

    protected void setTextViewDefaultValues() {
        mf_x.setText("unknown");
        mf_y.setText("unknown");
        mf_z.setText("unknown");
        accuracyTextView.setText("unknown");
    }

    protected void setSensorDataDefaultValues() {
    }


    public void onSensorChanged(SensorEvent theEvent) {
// TODO add low pass filter
        if (theEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            mf_x.setText(String.format("%.2f", theEvent.values[0]));
            mf_y.setText(String.format("%.2f", theEvent.values[1]));
            mf_z.setText(String.format("%.2f", theEvent.values[2]));
        }
    }
}