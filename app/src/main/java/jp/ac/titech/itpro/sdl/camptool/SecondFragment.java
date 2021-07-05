package jp.ac.titech.itpro.sdl.camptool;

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

import java.util.List;

import static android.content.Context.SENSOR_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

public class SecondFragment extends Fragment {
    private SensorManager sensorManager;
    private TextView textView;


    private int[] sensorList = {
            Sensor.TYPE_ACCELEROMETER,
            Sensor.TYPE_ACCELEROMETER_UNCALIBRATED,
            Sensor.TYPE_AMBIENT_TEMPERATURE,
            Sensor.TYPE_DEVICE_PRIVATE_BASE,
            Sensor.TYPE_GAME_ROTATION_VECTOR,
            //
            Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR,
            Sensor.TYPE_GRAVITY,
            Sensor.TYPE_GYROSCOPE,
            Sensor.TYPE_GYROSCOPE_UNCALIBRATED,
            Sensor.TYPE_HEART_BEAT,
            //
            Sensor.TYPE_HEART_RATE,
            Sensor.TYPE_LIGHT,
            Sensor.TYPE_LINEAR_ACCELERATION,
            Sensor.TYPE_LOW_LATENCY_OFFBODY_DETECT,
            Sensor.TYPE_MAGNETIC_FIELD,
            //
            Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED,
            Sensor.TYPE_MOTION_DETECT,
            Sensor.TYPE_POSE_6DOF,
            Sensor.TYPE_PRESSURE,
            Sensor.TYPE_PROXIMITY,
            //
            Sensor.TYPE_RELATIVE_HUMIDITY,
            Sensor.TYPE_ROTATION_VECTOR,
            Sensor.TYPE_SIGNIFICANT_MOTION,
            Sensor.TYPE_STATIONARY_DETECT,
            Sensor.TYPE_STEP_COUNTER,
            //
            Sensor.TYPE_STEP_DETECTOR
    };

    private String[] sensorNameList = {
            "ACCELEROMETER",
            "ACCELEROMETER_UNCALIBRATED",
            "AMBIENT_TEMPERATURE",
            "DEVICE_PRIVATE_BASE",
            "GAME_ROTATION_VECTOR",
            //
            "GEOMAGNETIC_ROTATION_VECTOR",
            "GRAVITY",
            "GYROSCOPE",
            "GYROSCOPE_UNCALIBRATED",
            "HEART_BEAT",
            //
            "HEART_RATE",
            "LIGHT",
            "LINEAR_ACCELERATION",
            "LOW_LATENCY_OFFBODY_DETECT",
            "MAGNETIC_FIELD",
            //
            "MAGNETIC_FIELD_UNCALIBRATED",
            "MOTION_DETECT",
            "POSE_6DOF",
            "PRESSURE",
            "PROXIMITY",
            //
            "RELATIVE_HUMIDITY",
            "ROTATION_VECTOR",
            "SIGNIFICANT_MOTION",
            "STATIONARY_DETECT",
            "STEP_COUNTER",
            //
            "STEP_DETECTOR",
    };
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
        textView = getView().findViewById(R.id.textview_second);

        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();

        boolean flg = true;
        // 表示の切り替え
        if(flg){
            // 端末で使用できるセンサーを表示
            checkSensors();
        }
        else{
            // センサーリストから使用可能かどうかの表示
            checkSensorsEach();
        }

    }

    private void checkSensors(){

        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        StringBuffer strListbuf =new StringBuffer("Sensor List:\n\n");
        int count = 0;

        for(Sensor sensor : sensors) {
            count++;
            String str = String.format(
                    "%s: %s\n", String.valueOf(count+1), sensor.getName());
            strListbuf.append(str);
        }

        if(textView!=null){
            textView.setText(strListbuf);
        }

    }


    private void checkSensorsEach(){
        StringBuffer strbuf =new StringBuffer("Sensor List:\n\n");

        for(int i=0; i < sensorList.length; i++){
            Sensor sensor = sensorManager.getDefaultSensor(sensorList[i]);

            if(sensor !=null){
                String strTmp = String.format("%s: %s: 使用可能\n",
                        String.valueOf(i+1), sensorNameList[i]);
                strbuf.append(strTmp);
            }
            else{
                String strTmp = String.format("%s: %s: XXX 不可\n",
                        String.valueOf(i+1), sensorNameList[i]);
                strbuf.append(strTmp);
            }
        }
        textView.setText(strbuf);
    }

}