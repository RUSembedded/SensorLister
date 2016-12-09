package de.rub.rus.sensorlister;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView sensorList;
    private TextView accelerometerInfoText, ambientTemperatureInfoText, gravityInfoText, gyroscopeInfoText, heartRateInfoText,
            lightSensorInfoText, linearAccelerationInfoText, magneticFieldInfoText, pressureInfoText, proximityInfoText, relativeHumidityInfoText, rotationVectorInfoText;
    // TODO: delete if obsolete

    private SensorManager theSensorManager;
    private Button accelerometerInfoButton, accelerometerRunSensorButton, accelerationLogButton;
    private Button ambientTemperatureInfoButton, ambientTemperatureRunSensorButton, ambientTemperatureLogButton;
    private Button gravityInfoButton, gravityRunSensorButton, gravityLogButton;
    private Button gyroscopeInfoButton, gyroscopeRunSensorButton, gyroscopeLogButton;
    private Button heartRateInfoButton, heartRateRunSensorButton, heartRateLogButton;
    private Button lightSensorInfoButton, lightSensorRunSensorButton, lightLogButton;
    private Button linearAccelerationInfoButton, linearAccelerationRunSensorButton, linearAccelerationLogButton;
    private Button magneticFieldInfoButton, magneticFieldRunSensorButton, magneticFieldLogButton;
    private Button pressureInfoButton, pressureRunSensorButton, pressureLogButton;
    private Button proximityInfoButton, proximityRunSensorButton, proximityLogButton;
    private Button relativeHumidityInfoButton, relativeHumidityRunSensorButton, relativeHumidityLogButton;
    private Button rotationVectorInfoButton, rotationVectorRunSensorButton, rotationVectorLogButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        identifyButtonsAndTextViews();
        theSensorManager= (SensorManager) getSystemService(SENSOR_SERVICE);
        checkSensors();

    }

    /**
     Assigns all info button, run sensor button and info text views (for example,
     accelerometerInfoButton, accelerometerRunSensorButton and accelerometerInfoText)
     to local variables; wrapper method required to keep things neat only.
    */
    private void identifyButtonsAndTextViews() {
        accelerometerInfoButton = (Button) findViewById(R.id.accelerometerInfoButton);
        accelerometerRunSensorButton= (Button) findViewById(R.id.accelerometerRunSensorButton);
        accelerationLogButton = (Button) findViewById(R.id.accelerometerLogSensorButton);


        ambientTemperatureInfoButton = (Button) findViewById(R.id.ambientTemperatureInfoButton);
        ambientTemperatureRunSensorButton = (Button) findViewById(R.id.ambientTemperatureRunSensorButton);
        ambientTemperatureLogButton = (Button) findViewById(R.id.ambientTemperatureLogButton);

        gravityInfoButton = (Button) findViewById(R.id.gravitySensorInfoButton);
        gravityRunSensorButton = (Button) findViewById(R.id.gravitySensorRunSensorButton);
        gravityLogButton = (Button) findViewById(R.id.gravityLogButton);

        gyroscopeInfoButton = (Button) findViewById(R.id.gyroscopeSensorInfoButton);
        gyroscopeRunSensorButton = (Button) findViewById(R.id.gyroscopeSensorRunSensorButton);
        gyroscopeLogButton = (Button) findViewById(R.id.gyroscopeLogButton);

        heartRateInfoButton = (Button) findViewById(R.id.heartRateSensorInfoButton);
        heartRateRunSensorButton = (Button) findViewById(R.id.heartRateSensorRunSensorButton);
        heartRateLogButton = (Button) findViewById(R.id.heartRateLogButton);

        lightSensorInfoButton = (Button) findViewById(R.id.lightSensorInfoButton);
        lightSensorRunSensorButton = (Button) findViewById(R.id.lightSensorRunSensorButton);
        lightLogButton = (Button) findViewById(R.id.lightLogButton);

        linearAccelerationInfoButton = (Button) findViewById(R.id.linearAccelerationSensorInfoButton);
        linearAccelerationRunSensorButton = (Button) findViewById(R.id.linearAccelerationSensorRunSensorButton);
        linearAccelerationLogButton = (Button) findViewById(R.id.linearAccelerationButton);

        magneticFieldInfoButton = (Button) findViewById(R.id.magneticFieldSensorInfoButton);
        magneticFieldRunSensorButton = (Button) findViewById(R.id.magneticFieldSensorRunSensorButton);
        magneticFieldLogButton = (Button) findViewById(R.id.magneticFieldLogButton);

        pressureInfoButton = (Button) findViewById(R.id.pressureSensorInfoButton);
        pressureRunSensorButton = (Button) findViewById(R.id.pressureSensorRunSensorButton);
        pressureLogButton = (Button) findViewById(R.id.pressureLogButton);

        proximityInfoButton = (Button) findViewById(R.id.proximitySensorInfoButton);
        proximityRunSensorButton = (Button) findViewById(R.id.proximitySensorRunSensorButton);
        proximityLogButton = (Button) findViewById(R.id.proximityLogButton);

        relativeHumidityInfoButton = (Button) findViewById(R.id.relativeHumiditySensorInfoButton);
        relativeHumidityRunSensorButton = (Button) findViewById(R.id.relativeHumiditySensorRunSensorButton);
        relativeHumidityLogButton = (Button) findViewById(R.id.relativeHumidityLogButton);

        rotationVectorInfoButton = (Button) findViewById(R.id.rotationVectorSensorInfoButton);
        rotationVectorRunSensorButton = (Button) findViewById(R.id.rotationVectorSensorRunSensorButton);
        rotationVectorLogButton = (Button) findViewById(R.id.rotationVectorLogButton);


    }

    /**
    Checks if sensor of type passed in 1st parameter exists and sets info text to yes/no
    and buttons to enabled/disabled accordingly.
     */
    private void checkSensor(int sensorType, TextView infoText, Button infoButton, Button runSensorButton, Button logSensorButton){
        if (hasSensor(sensorType)) {
           // infoText.setText("yes");
            infoButton.setEnabled(true);
            infoButton.setBackgroundColor(Color.argb(0xF1, 0x00, 0x35, 0x60));
            infoButton.setTypeface(null, Typeface.NORMAL);
            runSensorButton.setEnabled(true);
            runSensorButton.setBackgroundColor(Color.argb(0xFF, 0x8D, 0xAE, 0x10));
            runSensorButton.setTypeface(null, Typeface.NORMAL);
            logSensorButton.setEnabled(true);
            logSensorButton.setBackgroundColor(Color.argb(0x66, 0x66, 0x66, 0x60));
            logSensorButton.setTypeface(null, Typeface.NORMAL);



        } else {
          //  infoText.setText("no");
            infoButton.setEnabled(false);
            infoButton.setBackgroundColor(Color.argb(0x11, 0x00, 0x35, 0x60));
            infoButton.setTypeface(null, Typeface.ITALIC);
            runSensorButton.setEnabled(false);
            runSensorButton.setBackgroundColor(Color.argb(0x11, 0x8D, 0xAE, 0x10));
            runSensorButton.setTypeface(null, Typeface.ITALIC);
            logSensorButton.setEnabled(false);
            logSensorButton.setBackgroundColor(Color.argb(0x11, 0x66, 0x66, 0x60));
            logSensorButton.setTypeface(null, Typeface.ITALIC);


        }
    }

    /**
    Runs checkSensor for all sensors.
     */
    private void checkSensors(){

        TextView theTextView;

        checkSensor(Sensor.TYPE_ACCELEROMETER, accelerometerInfoText, accelerometerInfoButton, accelerometerRunSensorButton, accelerationLogButton);
        checkSensor(Sensor.TYPE_AMBIENT_TEMPERATURE, ambientTemperatureInfoText, ambientTemperatureInfoButton, ambientTemperatureRunSensorButton, ambientTemperatureLogButton);
        checkSensor(Sensor.TYPE_GRAVITY, gravityInfoText, gravityInfoButton, gravityRunSensorButton, gravityLogButton);
        checkSensor(Sensor.TYPE_GYROSCOPE, gyroscopeInfoText, gyroscopeInfoButton, gyroscopeRunSensorButton, gyroscopeLogButton);
        checkSensor(Sensor.TYPE_HEART_RATE, heartRateInfoText, heartRateInfoButton, heartRateRunSensorButton, heartRateLogButton);
        checkSensor(Sensor.TYPE_LIGHT, lightSensorInfoText, lightSensorInfoButton, lightSensorRunSensorButton, lightLogButton);
        checkSensor(Sensor.TYPE_LINEAR_ACCELERATION, linearAccelerationInfoText, linearAccelerationInfoButton, linearAccelerationRunSensorButton, linearAccelerationLogButton);
        checkSensor(Sensor.TYPE_MAGNETIC_FIELD, magneticFieldInfoText, magneticFieldInfoButton, magneticFieldRunSensorButton, magneticFieldLogButton);
        checkSensor(Sensor.TYPE_PRESSURE, pressureInfoText, pressureInfoButton, pressureRunSensorButton, pressureLogButton);
        checkSensor(Sensor.TYPE_PROXIMITY, proximityInfoText, proximityInfoButton, proximityRunSensorButton, proximityLogButton);
        checkSensor(Sensor.TYPE_RELATIVE_HUMIDITY, relativeHumidityInfoText, relativeHumidityInfoButton, relativeHumidityRunSensorButton, relativeHumidityLogButton);
        checkSensor(Sensor.TYPE_ROTATION_VECTOR, rotationVectorInfoText, rotationVectorInfoButton, rotationVectorRunSensorButton, rotationVectorLogButton);


    }


    /**
     * Checks if the sensor of the type passed in the first parameter exists.
     */
    private boolean hasSensor(int type){
        return theSensorManager.getDefaultSensor(type) != null;
    }

    /** LOG BUTTONS ***********************************/
    /**
     *
     */

    public void sendAccelerometerLogButtonPushed(View view){
        Intent theIntent= new Intent(this, DataLoggerActivityAccelerometer.class);
        startActivity(theIntent);
    }

    // TODO implement DataLoggerActivity class for all sensor types

    public void sendThermometerLogButtonPushed(View view){
        Intent theIntent= new Intent(this, DataLoggerActivityAmbientTemperature.class);
        startActivity(theIntent);
    }

    public void sendGravityLogButtonPushed(View view){
        Intent theIntent= new Intent(this, DataLoggerActivityGravity.class);
        startActivity(theIntent);
    }

    public void sendGyroscopeLogButtonPushed(View view){
        Intent theIntent= new Intent(this, DataLoggerActivityGyroscope.class);
        startActivity(theIntent);
    }

    public void sendHeartRateLogButtonPushed(View view){
        Intent theIntent= new Intent(this, DataLoggerActivityHeartRate.class);
        startActivity(theIntent);
    }

    public void sendLightLogButtonPushed(View view){
        Intent theIntent= new Intent(this, DataLoggerActivityLightSensor.class);
        startActivity(theIntent);
    }

    public void sendLinearAccelerationLogButtonPushed(View view){
        Intent theIntent= new Intent(this, DataLoggerActivityLinearAcceleration.class);
        startActivity(theIntent);
    }

    public void sendMagneticFieldLogButtonPushed(View view){
        Intent theIntent= new Intent(this, DataLoggerActivityMagneticField.class);
        startActivity(theIntent);
    }

    public void sendPressureLogButtonPushed(View view){
        Intent theIntent= new Intent(this, DataLoggerActivityPressure.class);
        startActivity(theIntent);
    }

    public void sendProximityLogButtonPushed(View view){
        Intent theIntent= new Intent(this, DataLoggerActivityProximity.class);
        startActivity(theIntent);
    }

    public void sendRelativeHumidityLogButtonPushed(View view){
        Intent theIntent= new Intent(this, DataLoggerActivityRelativeHumidity.class);
        startActivity(theIntent);
    }

    public void sendRotationVectorLogButtonPushed(View view){
        Intent theIntent= new Intent(this, DataLoggerActivityRotationVector.class);
        startActivity(theIntent);
    }






    /** RUN BUTTONS ***********************************/
    /**
     * Method invoked when respective button is pushed.
     */


    public void sendAccelerometerRunSensorButtonPushed(View view){
        Intent theIntent= new Intent(this, RunAccelerometerSensorActivity.class);
        startActivity(theIntent);
    }

    /** TODO Sensor not found on test device, maybe wrong sensor type? Try TYPE_TEMPERATURE instead
     */
    public void sendAmbientTemperatureRunSensorButtonPushed(View view){
        Intent theIntent = new Intent(this, RunAmbientTemperatureSensorActivity.class);
        startActivity(theIntent);
    }

    public void sendGravityRunSensorButtonPushed(View view){
        Intent theIntent = new Intent(this, RunGravitySensorActivity.class);
        startActivity(theIntent);
    }

    public void sendGyroscopeRunSensorButtonPushed(View view){
        Intent theIntent= new Intent(this, RunGyroscopeSensorActivity.class);
        startActivity(theIntent);
    }

    /** TODO check permissions of the application. According to Android developers reference, the heart rate sensor requires android.permission.BODY_SENSORS
     *  TODO Check  the accuracy. If it's SENSOR_STATUS_UNRELIABLE or SENSOR_STATUS_NO_CONTACT, the value should be discarded
     */
    public void sendHeartRateRunSensorButtonPushed(View view){
        Intent theIntent = new Intent(this, RunHeartRateSensorActivity.class);
        startActivity(theIntent);
    }

    public void sendLightRunSensorButtonPushed(View view){
        Intent theIntent = new Intent(this, RunLightSensorActivity.class);
        startActivity(theIntent);
    }

    public void sendMagneticFieldRunSensorButtonPushed(View view){
        Intent theIntent = new Intent(this, RunMagneticFieldSensorActivity.class);
        startActivity(theIntent);
    }

    public void sendPressureRunSensorButtonPushed(View view){
        Intent theIntent = new Intent(this, RunPressureSensorActivity.class);
        startActivity(theIntent);
    }

    public void sendProximityRunSensorButtonPushed(View view){
        Intent theIntent = new Intent(this, RunProximitySensorActivity.class);
        startActivity(theIntent);
    }

    public void sendRelativeHumidityRunSensorButtonPushed(View view){
        Intent theIntent = new Intent(this, RunRelativeHumiditySensorActivity.class);
        startActivity(theIntent);
    }

    public void sendRotationVectorRunSensorButtonPushed(View view){
        Intent theIntent = new Intent(this, RunRotationVectorSensorActivity.class);
        startActivity(theIntent);
    }

    /** Purpose of this method? */
     // TODO: check which button this refers to by running the app
    public void sendRotationVectorSensorDetailsButtonPushed(View view) {
        Intent theIntent= new Intent(this, RunRotationVectorSensorActivity.class);
        startActivity(theIntent);
    }

    /** INFO BUTTONS **********************************/
     /**
     * Methods invoked when respective button is pushed.
     */
    public void sendAccelerometerInfoButtonPushed(View view){
        Intent theIntent= new Intent(this, ShowAccelerometerSensorInfoActivity.class);
        startActivity(theIntent);
    }

    public void sendAmbientTemperatureInfoButtonPushed(View view){
        Intent theIntent = new Intent(this, ShowAmbientTemperatureSensorInfoActivity.class);
        startActivity(theIntent);
    }

    public void sendGravityInfoButtonPushed(View view){
        Intent theIntent = new Intent(this, ShowGravitySensorInfoActivity.class);
        startActivity(theIntent);
    }

    public void sendGyroscopeInfoButtonPushed(View view){
        Intent theIntent = new Intent(this, ShowGyroscopeSensorInfoActivity.class);
        startActivity(theIntent);
    }

    public void sendHeartRateInfoButtonPushed(View view){
        Intent theIntent = new Intent(this, ShowHeartRateSensorInfoActivity.class);
        startActivity(theIntent);
    }

    public void sendLightSensorInfoButtonPushed(View view){
        Intent theIntent = new Intent(this, ShowLightSensorInfoActivity.class);
        startActivity(theIntent);
    }

    public void sendLinearAccelerationInfoButtonPushed(View view){
        Intent theIntent = new Intent(this, ShowLinearAccelerationSensorInfoActivity.class);
        startActivity(theIntent);
    }

    public void sendMagneticFieldInfoButtonPushed(View view){
        Intent theIntent = new Intent(this, ShowMagneticFieldSensorInfoActivity.class);
        startActivity(theIntent);
    }

    public void sendPressureInfoButtonPushed(View view){
        Intent theIntent = new Intent(this, ShowPressureSensorInfoActivity.class);
        startActivity(theIntent);
    }

    public void sendProximityInfoButtonPushed(View view){
        Intent theIntent = new Intent(this, ShowProximitySensorInfoActivity.class);
        startActivity(theIntent);
    }

    public void sendRelativeHumidityInfoButtonPushed(View view){
        Intent theIntent = new Intent(this, ShowRelativeHumiditySensorInfoActivity.class);
        startActivity(theIntent);
    }

    public void sendRotationVectorInfoButtonPushed(View view){
        Intent theIntent= new Intent(this, ShowRotationVectorSensorInfoActivity.class);
        startActivity(theIntent);
    }


    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
