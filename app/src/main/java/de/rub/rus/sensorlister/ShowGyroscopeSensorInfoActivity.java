package de.rub.rus.sensorlister;

import android.hardware.Sensor;
import android.widget.TextView;

public class ShowGyroscopeSensorInfoActivity extends ShowSensorInfoActivity {

    /*
    Set sensor type for the specific sensor, i.e. the rotation vector sensor.
     */
    protected void setSensorType(){ sensorType= Sensor.TYPE_GYROSCOPE; }

    /*
    Set view ids for the specific sensor, i.e. the rotation vector sensor.
     */
    protected void setTextViews(){
        defaultSensorInfoTextView = (TextView) findViewById(R.id.GyroscopeDefaultSensorInfoTextView);
        allSensorsInfoTextView= (TextView) findViewById(R.id.allGyroscopeSensorsInfoTextView);
    }



    /*
    Returns content view id for the specific sensor type.
     */
    protected int getContentViewId(){
        return R.layout.activity_show_gyroscope_sensor_info;
    }

}
