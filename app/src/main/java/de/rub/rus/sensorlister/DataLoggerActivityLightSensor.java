package de.rub.rus.sensorlister;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.media.MediaScannerConnection;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;

/**
 * Created by fjo on 28.10.16.
 */
public class DataLoggerActivityLightSensor extends DataLoggerActivity {

    @Override
    protected int getContentViewId() {
        return R.layout.activity_data_logger_light;
    }

    @Override
    protected void setSensorType() {
        sensorType = Sensor.TYPE_LIGHT;
    }

    @Override
    protected void setTextViewDefaultValues(){
        textStatus.setText("ready to collect");
    }

    @Override
    protected void setAverageSensorDelay(int delay) {
        switch (delay) {
            case SensorManager.SENSOR_DELAY_NORMAL: averageSensorDelay = (float) 62.5; break;
            case SensorManager.SENSOR_DELAY_GAME: averageSensorDelay = (float) 30.0; break;
            case SensorManager.SENSOR_DELAY_FASTEST: averageSensorDelay = (float) 10.0; break;
            default: break;

        }
    }

    @Override
    protected Long normalizeTimeline(Long actualTimestamp, Long startTime) {
        Long normalizedTimestamp =  actualTimestamp - startTime;
        Log.d("acc", Long.toString(normalizedTimestamp));
        return normalizedTimestamp;
    }

    @Override
    protected void setTextStatus(String str) {
        textStatus.setText(str);
    }

    @Override
    protected void storeData() {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        String filename = "lightSensor" + timeStamp + ".txt";
        boolean firstTimestamp = true;
        try {
            File logFile = new File(storageLocation.toString());
            boolean tmp  = logFile.mkdirs();
            File file = new File(storageLocation.toString(), filename);
            file.createNewFile();
            Log.d("acc", Boolean.toString(tmp));
            FileOutputStream fOut = new FileOutputStream(file);
            FileWriter writer = new FileWriter(file);
            writer.write("lum\ttime abs[ns]\ttime rel[ns]\n");
            while(capturedData.size() > 0 ){

                writer.write(capturedData.poll().toString());
                writer.write("\t");
                Long timestamp = capturedTimestamp.poll();

                if(firstTimestamp) {
                    startTime = timestamp;
                    firstTimestamp = false;
                }
                timestamp = normalizeTimeline(timestamp, startTime);

                writer.write(timestamp.toString());
                writer.write("\t");

                Long timestampRel = (timestamp - oldTimestamp);
                writer.write(timestampRel.toString());
                oldTimestamp = timestamp;
                writer.write("\n");
            }
            writer.flush();
            writer.close();
            fOut.close();

            /*
             * re-scan the media folder manually so the new file shows up via usb on a pc
             */
            MediaScannerConnection.scanFile(getApplicationContext(), new String[]{file.getAbsolutePath()}, null, null);
            //logFile.createNewFile();
        }catch (Exception e) {
            Log.d("acc", e.toString());
        }

    }

    @Override
    protected void calculateRequiredSpace() {
        setAverageSensorDelay(sensorListenerDelay);
        requiredSpace = (1 * 8 * (loggingDuration/averageSensorDelay) );
        tvEstimatedSize.setText( (Integer.toString( (int) requiredSpace) + " Byte"));
    }

    @Override
    protected Vector getRecordedDataAsVector() {
        return null;
    }

    @Override
    protected File getRecordedDataAsFile() {
        return null;
    }

    @Override
    public void onSensorChanged(SensorEvent theEvent) {
        if (theEvent.sensor.getType() == theSensor.getType()) {
            Float value1 = theEvent.values[0];
            capturedData.add(value1);
            Long timestamp = theEvent.timestamp;
            capturedTimestamp.add(timestamp);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


}
