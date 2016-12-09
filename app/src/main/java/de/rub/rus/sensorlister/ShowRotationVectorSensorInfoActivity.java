package de.rub.rus.sensorlister;

import android.hardware.Sensor;
import android.widget.TextView;

public class ShowRotationVectorSensorInfoActivity extends ShowSensorInfoActivity {

    /*
    Set sensor type for the specific sensor, i.e. the rotation vector sensor.
     */
    protected void setSensorType(){
        sensorType= Sensor.TYPE_ROTATION_VECTOR;
    }

    /*
    Set view ids for the specific sensor, i.e. the rotation vector sensor.
     */
    protected void setTextViews(){
        defaultSensorInfoTextView = (TextView) findViewById(R.id.rotationVectorDefaultSensorInfoTextView);
        allSensorsInfoTextView= (TextView) findViewById(R.id.allRotationVectorSensorsInfoTextView);
    }

    /*
    Returns content view id for the specific sensor type.
     */
    protected int getContentViewId(){
        return R.layout.activity_show_rotation_vector_sensor_info;
    }

}
