package de.rub.rus.sensorlister;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.widget.TextView;

public class RunRotationVectorSensorActivity
        extends RunSensorActivity {

    private TextView xAngleTextView, yAngleTextView, zAngleTextView;
    private float[] Rmat3by3 = new float[9]; // 3-by-3 variant of orientation matrix
    private float[] Rmat4by4 = new float[16]; // 4-by-4 variant of orientation matrix
    private float[] anglesFrom3by3RotationMatrix = new float[3]; // (azimuth, pitch, roll)= angle about z, x, y, resp.
    private float[] anglesFrom4by4RotationMatrix= new float[3];
    private final float PI= (float) 3.141592653589793;

    /**
    Set sensor type for the specific sensor, i.e. the rotation vector sensor.
     */
    protected void setSensorType(){
        sensorType= Sensor.TYPE_ROTATION_VECTOR;
    }

    /**
    Set view ids for the specific sensor, i.e. the rotation vector sensor.
     */
    protected void setTextViews(){
        xAngleTextView= (TextView) findViewById(R.id.xAngleRotationVectorSensor);
        yAngleTextView= (TextView) findViewById(R.id.yAngleRotationVectorSensor);
        zAngleTextView= (TextView) findViewById(R.id.zAngleRotationVectorSensor);
        accuracyTextView = (TextView) findViewById(R.id.rotationVectorAccuracy);
    }

    /**
    Sets all TextViews to display "unknown".
     */
    protected void setTextViewDefaultValues(){
        xAngleTextView.setText("unknown");
        yAngleTextView.setText("unknown");
        zAngleTextView.setText("unknown");
        accuracyTextView.setText("unknown");

    }

    /**
    Returns content view id, i.e. the handle to the screen, for the specific sensor type.
     */
    protected int getContentViewId(){
        return R.layout.activity_run_rotation_vector;
    }

    /**
    Helper function that can call whatever initialization functions are
    required for the sensor.
     */
    protected void setSensorDataDefaultValues(){
        initRotationMatrices();
    }

    /**
      Initializes 3-by-3 and 4-by-4 rotation matrices; remember methods
      that use rotation matrix are overloaded to accept either
      3 by 3 rotation matrix or 4 by 4 rotation matrix.
    */
    private void initRotationMatrices() {
    /*
      Initialize 3-by-3 rotation matrix.
    */
        Rmat3by3[0]= (float) 1.0;
        Rmat3by3[1]= (float) 0.0;
        Rmat3by3[2]= (float) 0.0;

        Rmat3by3[3]= (float) 0.0;
        Rmat3by3[4]= (float) 1.0;
        Rmat3by3[5]= (float) 0.0;

        Rmat3by3[6]= (float) 0.0;
        Rmat3by3[7]= (float) 0.0;
        Rmat3by3[8]= (float) 1.0;
    /*
      Initialize 4-by-4 rotation matrix.
    */
        Rmat4by4[0]= (float) 1.0;
        Rmat4by4[1]= (float) 0.0;
        Rmat4by4[2]= (float) 0.0;
        Rmat4by4[3]= (float) 0.0;

        Rmat4by4[4]= (float) 0.0;
        Rmat4by4[5]= (float) 1.0;
        Rmat4by4[6]= (float) 0.0;
        Rmat4by4[7]= (float) 0.0;

        Rmat4by4[8]= (float) 0.0;
        Rmat4by4[9]= (float) 0.0;
        Rmat4by4[10]= (float) 1.0;
        Rmat4by4[11]= (float) 0.0;

        Rmat4by4[12]= (float) 0.0;
        Rmat4by4[13]= (float) 0.0;
        Rmat4by4[14]= (float) 0.0;
        Rmat4by4[15]= (float) 1.0;
    }

    /**
    Carries out actual sensor evaluation; changes data displayed on the screen
    whenever new sensor data becomes available.
     */
    public void onSensorChanged(SensorEvent theEvent){

        // Extract rotation matrix if event is actually due to change in rotation vector
        if (theEvent.sensor.getType()== Sensor.TYPE_ROTATION_VECTOR){
            // determine orientation using 3-by-3 rotation matrix
            SensorManager.getRotationMatrixFromVector(Rmat3by3, theEvent.values);
            SensorManager.getOrientation(Rmat3by3, anglesFrom3by3RotationMatrix);

            // determine orientation using 4-by-4 rotation matrix
            SensorManager.getRotationMatrixFromVector(Rmat4by4, theEvent.values);
            SensorManager.getOrientation(Rmat4by4, anglesFrom4by4RotationMatrix);

            zAngleTextView.setText(String.format("%.2f", anglesFrom3by3RotationMatrix[0]) +
                            " [" +
                            String.format("%.2f", anglesFrom4by4RotationMatrix[0]) + "]");

            xAngleTextView.setText(String.format("%.2f", anglesFrom3by3RotationMatrix[1])+
                            " ["+
                            String.format("%.2f", anglesFrom4by4RotationMatrix[1])+ "]");
            yAngleTextView.setText(String.format("%.2f", anglesFrom3by3RotationMatrix[2])+
                            " ["+
                            String.format("%.2f", anglesFrom4by4RotationMatrix[2])+ "]");
        }
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rotation_vector_sensor_show, menu);
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
