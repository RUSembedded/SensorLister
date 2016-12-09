package de.rub.rus.sensorlister;

import android.hardware.Sensor;
import android.widget.TextView;

public class ShowGravitySensorInfoActivity extends ShowSensorInfoActivity {

    /*
    Set sensor type for the specific sensor, i.e. the rotation vector sensor.
     */
    protected void setSensorType(){
        sensorType= Sensor.TYPE_GRAVITY;
    }

    /*
    Set view ids for the specific sensor, i.e. the rotation vector sensor.
     */
    protected void setTextViews(){
        defaultSensorInfoTextView = (TextView) findViewById(R.id.GravityDefaultSensorInfoTextView);
        allSensorsInfoTextView= (TextView) findViewById(R.id.allGravitySensorsInfoTextView);
    }

    /*
    Returns content view id for the specific sensor type.
     */
    protected int getContentViewId(){
        return R.layout.activity_show_gravity_sensor_info;
    }

}
