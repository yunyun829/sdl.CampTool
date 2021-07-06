package jp.ac.titech.itpro.sdl.camptool;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import static android.content.Context.SENSOR_SERVICE;

public class FirstFragment extends Fragment implements SensorEventListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private SensorManager sensorManager;
    private TextView textView;
    private CompassView compassView;
    private Sensor ASensor,GSensor;


    private  float[] G = new float[3];
    private  float[] A = new float[3];

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }



    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sensorManager = (SensorManager)getActivity().getSystemService(SENSOR_SERVICE);
        textView = (TextView)view.findViewById(R.id.textview_first);
        compassView = view.findViewById(R.id.compass_view);
        ASensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        GSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this,ASensor,SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this,GSensor,SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType()==Sensor.TYPE_MAGNETIC_FIELD){
            G = calcWaitedAve(event.values.clone(),G);
        }
        if (event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            A = calcWaitedAve(event.values.clone(),A);
        }
        float[] R = new float[9];
        float[] I = new float[16];

        SensorManager.getRotationMatrix(R,null,A,G);

        float[] O = new float[6];

        SensorManager.getOrientation(R,O);

        compassView.setDirection(O[0]);
        textView.setText("direct:"+O[0]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    private float[] calcWaitedAve(float[] cur,float[] prev){
        float wait = 0.85f;

        for (int i = 1; i < prev.length; i++){
            cur[i] = prev[i]*wait+cur[i]*(1-wait);
        }
        return cur;
    }



}