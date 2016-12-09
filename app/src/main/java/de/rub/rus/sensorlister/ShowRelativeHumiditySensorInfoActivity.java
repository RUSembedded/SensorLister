package de.rub.rus.sensorlister;

import android.hardware.Sensor;
import android.widget.TextView;

public class ShowRelativeHumiditySensorInfoActivity extends ShowSensorInfoActivity {

    /*
    Set sensor type for the specific sensor, i.e. the rotation vector sensor.
     */
    protected void setSensorType(){
        sensorType= Sensor.TYPE_RELATIVE_HUMIDITY;
    }

    /*
    Set view ids for the specific sensor, i.e. the rotation vector sensor.
     */
    protected void setTextViews(){
        defaultSensorInfoTextView = (TextView) findViewById(R.id.RelativeHumidityDefaultSensorInfoTextView);
        allSensorsInfoTextView= (TextView) findViewById(R.id.allRelativeHumiditySensorsInfoTextView);
    }

    /*
    Returns content view id for the specific sensor type.
     */
    protected int getContentViewId(){ return R.layout.activity_show_relative_humidity_sensor_info; }

}
