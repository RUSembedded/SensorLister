package de.rub.rus.sensorlister;

import android.hardware.Sensor;
import android.widget.TextView;

public class ShowAmbientTemperatureSensorInfoActivity extends ShowSensorInfoActivity {

    /*
    Set sensor type for the specific sensor, i.e. the rotation vector sensor.
     */
    protected void setSensorType(){
        sensorType= Sensor.TYPE_AMBIENT_TEMPERATURE;
    }

    /*
    Set view ids for the specific sensor, i.e. the rotation vector sensor.
     */
    protected void setTextViews(){
        defaultSensorInfoTextView = (TextView) findViewById(R.id.AmbientTemperatureDefaultSensorInfoTextView);
        allSensorsInfoTextView= (TextView) findViewById(R.id.allAmbientTemperatureSensorsInfoTextView);
    }

    /*
    Returns content view id for the specific sensor type.
     */
    protected int getContentViewId(){
        return R.layout.activity_show_ambient_temperature_sensor_info;
    }

}
