package de.rub.rus.sensorlister;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.concurrent.LinkedBlockingQueue;




/**
 * Abstract class for recording sensor data and store it to the device memory; additionally, the transmission via bluetooth is supported
 */
public abstract class DataLoggerActivity extends AppCompatActivity implements SensorEventListener, AdapterView.OnItemSelectedListener {
    protected double requiredSpace;
    protected int loggingDuration = 1000;
    protected File storageLocation;
    public SensorManager theSensorManager;
    public Sensor theSensor;
    protected int sensorType;
    protected float averageSensorDelay;
    protected Queue<Float> capturedData = new LinkedBlockingQueue<>();
    protected Queue<Long> capturedTimestamp = new LinkedBlockingQueue<>();
    static protected Long oldTimestamp = new Long(0);
    protected ProgressBar pbLoggingProgress;
    // TODO rename spinners to something meaningful
    private Spinner spinner, spinner2, spinner3;
    private Button buttonStart, buttonCancel;
    private LinearLayout nodeButton;
    boolean cancelled = false;
    boolean logging = false;
    boolean countdownRunning = false;
    protected TextView textStatus, tvEstimatedSize;
    protected int sensorListenerDelay = SensorManager.SENSOR_DELAY_NORMAL;
    protected int loggingDelay = 0;
    protected int count = 0 ;
    protected Runnable runnableStartLogging, runnableStartCountdown, runnableLoggingFinished;
    protected Long startTime;
    // TODO rename to something meaningful
    final Handler handler = new Handler();
    final Handler handle = new Handler();
    Timer timerCountdown = new Timer();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        // make sure there an arrow in the action bar that brings back to the parent fragment
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        setSensorType();
        setViews();
        setTextViewDefaultValues();
        nodeButton.removeView(buttonCancel);

        /*
        check if external storage is available for write operations
         */
        boolean tmp = isExternalStorageWritable();

        /* TODO create initViews method
        set up the spinner views; connect corresponding item list in xml to spinner;
         */
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.logging_times_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setOnItemSelectedListener(this);
        spinner.setAdapter(adapter);


        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.sensor_speed_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setOnItemSelectedListener(this);
        spinner2.setAdapter(adapter2);

        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.delay_times_array, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setOnItemSelectedListener(this);
        spinner3.setAdapter(adapter3);

    }

    /**
     * Returns content view id.
     */
    protected abstract int getContentViewId();

    /**
     * Sets sensor type.
     */
    protected abstract void setSensorType();

    /**
     Sets Ids for TextViews that correspond to the particular sensor type.
     */
    // TODO does this really have to be abstract? GUI is the same for every sensortype...
    protected void setViews() {
        setContentView(getContentViewId());
        textStatus = (TextView) findViewById(R.id.textStatus);
        pbLoggingProgress = (ProgressBar) findViewById(R.id.pbLoggingProgress);
        // change height of progress bar
        pbLoggingProgress.setScaleY(5f);

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        tvEstimatedSize = (TextView) findViewById(R.id.tvEstimatedSize);
        buttonStart = (Button) findViewById(R.id.buttonStart);
        buttonCancel = (Button) findViewById(R.id.buttonCancel);
        nodeButton = (LinearLayout) findViewById(R.id.nodeButtons);
    }

    /**
     Sets all TextViews to value "default".
     */
    protected abstract void setTextViewDefaultValues();

    /**
     * TODO: check if the values are the same for every sensor, then implement function in main class; in the meanwhile it's declared as abstract
     * Sets the average time between two measured values
     */
    protected abstract void setAverageSensorDelay(int delay);

    /**
     * this method normalizes the timeline to zero
     */
    protected abstract Long normalizeTimeline(Long actualTimestamp, Long startTime);

    /**
     * helper method to change status text
     * @param str string to display
     */
    protected abstract void setTextStatus(String str);


    /**
     * Unregisters the sensor listener when the measurement is done
     */
    protected void unregisterSensorListener() {
        theSensorManager.unregisterListener(this, theSensor);
    }


    /**
     * Sets the duration for which the data is captured
     * @param milliseconds time to capture in milliseconds
    */
    void setLoggingDuration(int milliseconds) {
        this.loggingDuration = milliseconds;
    }


    /**
     * Sets the storage location; you can choose between internal memory or external memory, e.g. SD-card
     * @param location complete filepath to the log files
    */
    void setStorageLocation(File location){
        this.storageLocation = location;
    }

    /**
     * Sets the delay before data collection starts
     * @param delay time to wait until the measurement is started in milliseconds
     */
    void setLoggingDelay(int delay) {
        loggingDelay = delay;
    }

    /**
     * starts the measurement
    */
    void startLogging(){

        /*
        set logging duration before creating the handler
        */
        count = 0;
        pbLoggingProgress.setMax(loggingDuration);
        pbLoggingProgress.setProgress(loggingDuration);
        textStatus.setText("collecting data");

        /*
        set up timer to decrement the progress bar
        */
        timerCountdown = null;
        timerCountdown = new Timer();
        timerCountdown.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                double interval = loggingDuration/10.0;
                int progress = (int) (loggingDuration - interval*count);
                pbLoggingProgress.setProgress( (int) (loggingDuration - interval*count) );
                count++;
            }
        }, 0, loggingDuration/10);

        /*
        set up runnableStartLogging; the runnableStartLogging is executed via the handler on the UI thread after 'loggingDuration' in [ms]
         */
        runnableLoggingFinished = new Runnable() {
            @Override
            public void run() {
                textStatus.setText("unregister sensor listener...");
                timerCountdown.cancel();
                unregisterSensorListener();
                textStatus.setText("writing data to filesystem...");
                storeData();
                setTextStatus("ready to collect");
                nodeButton.removeView(buttonCancel);
                nodeButton.addView(buttonStart);
                Toast.makeText(getApplicationContext(), "sensor data successfully recorded", Toast.LENGTH_LONG).show();
            }
        };


        /**
         * Register listener.
        */
        theSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        theSensor = theSensorManager.getDefaultSensor(sensorType);
        theSensorManager.registerListener(this,
                theSensor,
                sensorListenerDelay);


        /*
        the handler
         */
        handle.postDelayed(runnableLoggingFinished, loggingDuration);


    }

    /*
    method to get requiredSpace
    */
    public double getRequiredSpace(){
        return this.requiredSpace;
    }


    /**
     *  this method checks if external storage is available for read and write operations
     */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            setStorageLocation(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "Logs"));
            return true;
        }
        return false;
    }


    /**
     * stores captured data
    */
    protected abstract void storeData();


    /**
     * calculates the required space for logging, dependent on sensortype and capturing duration
     */
    protected abstract void calculateRequiredSpace();


    /**
     *  method to get raw captured data
     *  TODO necessary?
     */
    protected abstract Vector getRecordedDataAsVector();


    /**
     * method to get formatted captured data
     * TODO necessary?
     */
    protected abstract File getRecordedDataAsFile();


    /**
     Implements the actual calculations and actions (such refreshing as
     displayed data) to be taken whenever the sensor data changes.
     */
    public abstract void onSensorChanged(SensorEvent theEvent);


    /**
     * Registers the sensor listener
     */
    public void registerListener() {
        theSensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        theSensor = theSensorManager.getDefaultSensor(sensorType);
        theSensorManager.registerListener(this, theSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }


    /**
     * callback method invoked when 'cancel' is pressed
     * @param view actual view
     */
    public void buttonCancelPressed(View view) {

        nodeButton.removeView(buttonCancel);
        nodeButton.addView(buttonStart);
        cancelled = true;
        textStatus.setText("ready to collect");

        if(countdownRunning) {
            handler.removeCallbacks(runnableStartCountdown);
        }

        if(logging) {
            handle.removeCallbacks(runnableLoggingFinished);
            pbLoggingProgress.setProgress(0);
            timerCountdown.cancel();
            unregisterSensorListener();
        }

    }


    /**
     * callback method invoked when button 'start' is pressed
     * @param view actual view
     */
    public void buttonStartPressed(View view) {

        nodeButton.removeView(buttonStart);
        nodeButton.addView(buttonCancel);
        count = 0;
        final int timeRemaining = loggingDelay/1000;
        cancelled = false;


        runnableStartCountdown = new Runnable() {
            @Override
            public void run() {
                if(!cancelled) {
                    textStatus.setText(Integer.toString(timeRemaining-count));
                    count++;
                    countdownRunning = true;
                    handler.postDelayed(runnableStartCountdown, 1000);
                }
            }
        };

        /*
        trigger execution of the runnableStartCountdown manually; the runnableStartCountdown repeatedly triggers itself on execution, as long as the callbacks are not removed
         */
        runnableStartCountdown.run();

          /*
        set up runnableStartLogging; it starts recording of sensor data after the time given in loggingDelay; the runnableStartLogging is executed via the handler on the UI thread after 'loggingDelay' in [ms]
         */
        runnableStartLogging = new Runnable() {
            @Override
            public void run() {
                //timerCountdownStart.cancel();
                handler.removeCallbacks(runnableStartCountdown);
                if(!cancelled) {
                    startLogging();
                    logging = true;


                }
            }
        };

        /*
        the handler -> executes runnableStartLogging after time loggingDelay
         */
        handle.postDelayed(runnableStartLogging, loggingDelay);
    }


    /**
     * callback method invoked, when user selects an item in a spinner
     * @param parent
     * @param view
     * @param pos
     * @param id
     */
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

       if(parent.getId() == spinner.getId()) {
           String item = (String) parent.getItemAtPosition(pos);
           item = item.replace("s", "");
           setLoggingDuration(Integer.parseInt(item) * 1000);

       }else if(parent.getId() == spinner2.getId()) {
           String item = (String) parent.getItemAtPosition(pos);
           switch (item) {
               case "UI": sensorListenerDelay = SensorManager.SENSOR_DELAY_UI; break;
               case "Normal": sensorListenerDelay = SensorManager.SENSOR_DELAY_NORMAL; break;
               case "Game": sensorListenerDelay = SensorManager.SENSOR_DELAY_GAME; break;
               case "Fastest": sensorListenerDelay = SensorManager.SENSOR_DELAY_FASTEST; break;
               default: break;
           }

       }else if(parent.getId() == spinner3.getId()) {
           String item = (String) parent.getItemAtPosition(pos);
           item = item.replace("s", "");
           int loggingDelay = Integer.parseInt(item);
           if(loggingDelay == 0) {
               loggingDelay = 0;
           }else {
               loggingDelay = loggingDelay * 1000;

           }
           setLoggingDelay(loggingDelay);
       }
       calculateRequiredSpace();

    }


    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

}


