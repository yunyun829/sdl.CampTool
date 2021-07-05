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
    private SensorManager sensorManager;
    private TextView textView;
    private CompassView compassView;

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
        textView = getView().findViewById(R.id.textview_second);
        compassView = getView().findViewById(R.id.compass_view);
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
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
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType()==Sensor.TYPE_MAGNETIC_FIELD){
            G = event.values.clone();//磁気
        }
        if (event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            A = event.values.clone();
        }
        float[] R = new float[16];
        float[] I = new float[16];

        SensorManager.getRotationMatrix(R,I,A,G);

        float[] O = new float[3];

        SensorManager.getOrientation(R,O);
        Log.d("FF", String.valueOf(O));
        compassView.setDirection(O[0]);
        textView.setText("direct:"+O[0]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }



}