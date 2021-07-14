package jp.ac.titech.itpro.sdl.camptool;

import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.util.EventListener;
import java.util.List;

import static android.content.Context.SENSOR_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

public class SecondFragment extends Fragment implements SensorEventListener {
    private SensorManager sensorManager;
    //private TextView textView;
    private SlopeView slopeView;
    private Sensor ASensor,GSensor;

    private float[] rotation = new float[9];
    private float[] gravity = new float[3];
    private float[] magnetic = new float[3];
    private float[] attitude = new float[3];

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sensorManager = (SensorManager)getActivity().getSystemService(SENSOR_SERVICE);
        ASensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        GSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        //textView = getView().findViewById(R.id.textview_second);
        slopeView = getView().findViewById(R.id.slope_view);

        //view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //        NavHostFragment.findNavController(SecondFragment.this)
        //                .navigate(R.id.action_SecondFragment_to_thirdFragment);
        //    }
        //});
    }
    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this,ASensor,SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this,GSensor,SensorManager.SENSOR_DELAY_FASTEST);

    }


    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()) {
            case Sensor.TYPE_MAGNETIC_FIELD:
                magnetic = calcWaitedAve(event.values.clone(),magnetic);
                break;
            case Sensor.TYPE_ACCELEROMETER:
                gravity = calcWaitedAve(event.values.clone(),gravity);
                break;
        }
        if (magnetic != null && gravity != null){
            sensorManager.getRotationMatrix(rotation,null,gravity,magnetic);
            sensorManager.getOrientation(rotation,attitude);

            double x = Math.toDegrees(attitude[1]);
            double y = Math.toDegrees(attitude[2]);
            double theta = Math.toDegrees(Math.atan2(y,x));
            double length = Math.sqrt(x*x+y*y);
            //textView.setText("theta:"+theta);
            slopeView.setDegree(theta,length);


        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private float[] calcWaitedAve(float[] cur,float[] prev){
        float wait = 0.85f;

        for (int i = 1; i < prev.length; i++){
            cur[i] = prev[i]*wait+cur[i]*(1-wait);
        }
        return cur;
    }
}