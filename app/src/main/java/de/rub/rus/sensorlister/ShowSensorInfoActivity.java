package de.rub.rus.sensorlister;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.List;

/**
 * Abstract class for all sensor info activity classes, for example, ShowRotationVectorSensorInfoActivity.
 */
public abstract class ShowSensorInfoActivity extends AppCompatActivity {
    //    private TextView defaultSensorInfoTextView;
//    private TextView allSensorsInfoTextView;

    protected int contentViewId;
    protected int sensorType;
    protected TextView defaultSensorInfoTextView;
    protected TextView allSensorsInfoTextView;

    /**
    Returns content view id.
     */
    protected abstract int getContentViewId();

    /**
    Sets sensor type.
     */
    protected abstract void setSensorType();

    /**
    Sets Ids for TextViews that correspond to the sensor; must be overwritten in
    derived class, since each derived class will have its corresponding unique fragment
    with its corresponding unique TextViews.
     */
    protected abstract void setTextViews();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);


        setSensorType();
        // Every class derived from this abstract one has its own fragment, text views etc.,
        // which must be set in setTextViewIds.

        setTextViews();

        /*
        Get default sensor object, convert to string and print to screen
         */
        SensorManager theSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor defaultSensor = theSensorManager.getDefaultSensor(sensorType);
        defaultSensorInfoTextView.setText(defaultSensor.toString());

        /*
        Get all sensor objects of the given type, print their data to screen.
         */
        List<Sensor> sensorList = theSensorManager.getSensorList(sensorType);

        String infoString = "";
        for (Sensor s : sensorList) {
            infoString += s.toString();
            infoString += "\n";
        }
        allSensorsInfoTextView.setText(infoString);
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rotation_vector_info, menu);
        return true;
    }
    */

    /*
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
