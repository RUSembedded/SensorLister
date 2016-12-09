package de.rub.rus.sensorlister;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Abstract class for all sensor activity classes, for example, RunRotationVectorSensorActivity.
 */
public abstract class RunSensorActivity extends AppCompatActivity implements SensorEventListener {
    private Sensor theSensor;
    protected TextView accuracyTextView;
    protected int sensorType;

    /**
    Returns content view id.
     */
    protected abstract int getContentViewId();

    /**
    Sets sensor type.
    */
    protected abstract void setSensorType();

    /**
    Sets Ids for TextViews that correspond to the particular sensor type.
     */
    protected abstract void setTextViews();

    /**
    Sets all TextViews to value "default".
     */
    protected abstract void setTextViewDefaultValues();

    /**
    Helper function that sets whatever default values the sensor requires;
    sets initial rotation matrices for rotation vector sensor, for example.
     */
    protected abstract void setSensorDataDefaultValues();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        // make sure there an arrow in the action bar that brings back to the parent fragment
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        setSensorType();

        /*
        Register listener.
         */
        SensorManager theSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        theSensor = theSensorManager.getDefaultSensor(sensorType);
        theSensorManager.registerListener(this,
                theSensor,
                SensorManager.SENSOR_DELAY_NORMAL);

        setTextViews();
        setTextViewDefaultValues();
        setSensorDataDefaultValues();

    }

    /**
    Refreshes info on displayed accuracy.
     */
    public void onAccuracyChanged(Sensor aSensor, int accuracy){
        if (aSensor== theSensor){
            if (accuracy== SensorManager.SENSOR_STATUS_ACCURACY_HIGH){
                accuracyTextView.setText("high");
            } else if (accuracy== SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM){
                accuracyTextView.setText("medium");
            } else if (accuracy== SensorManager.SENSOR_STATUS_ACCURACY_LOW){
                accuracyTextView.setText("low");
            } else if (accuracy== SensorManager.SENSOR_STATUS_NO_CONTACT){
                accuracyTextView.setText("no contact");
            } else if (accuracy== SensorManager.SENSOR_STATUS_UNRELIABLE){
                accuracyTextView.setText("unreliable");
            }
        }
    }

    /**
    Implements the actual calculations and actions (such refreshing as
    displayed data) to be taken whenever the sensor data changes.
     */
    public abstract void onSensorChanged(SensorEvent theEvent);
}
