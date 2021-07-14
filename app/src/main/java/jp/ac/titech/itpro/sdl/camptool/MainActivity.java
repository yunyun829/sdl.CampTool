package jp.ac.titech.itpro.sdl.camptool;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import android.hardware.camera2.CameraManager;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private CameraManager camera;
    private CameraDevice cd;
    private String ID;
    private boolean LEDActive = false;
    private CameraDevice.StateCallback sc;
    private int tmpFragID = 1;
    private NavController navController;
    private RelativeLayout backGround;
    String TAG = "MainActivity";
    private boolean themeColorRight = true;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        backGround = findViewById(R.id.back_ground__layout);
        FloatingActionButton fab = findViewById(R.id.fab);
        Button toFragment1 = findViewById(R.id.toFragment1);
        Button toFragment2 = findViewById(R.id.toFragment2);
        Button toFragment3 = findViewById(R.id.toFragment3);
        Button toFrabment4 = findViewById(R.id.toFragment4);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        camera = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        camera.registerTorchCallback(new CameraManager.TorchCallback() {
            @Override
            public void onTorchModeChanged(@NonNull String cameraId, boolean enabled) {
                super.onTorchModeChanged(cameraId, enabled);
                ID = cameraId;
                LEDActive = enabled;

            }
        }, new Handler());

        fab.setOnClickListener(this);
        toFragment1.setOnClickListener(this);
        toFragment2.setOnClickListener(this);
        toFragment3.setOnClickListener(this);
        toFrabment4.setOnClickListener(this);


    }

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
            if (themeColorRight){
                item.setTitle("right theme");
                backGround.setBackgroundColor(Color.parseColor("#15202B"));
                themeColorRight = false;
            }else {
                item.setTitle("dark theme");
                backGround.setBackgroundColor(Color.parseColor("#A3F875"));
                themeColorRight = true;
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        cd.close();
    }


    private void switchLED() throws CameraAccessException {
        if(!LEDActive){
            camera.setTorchMode(ID,true);
        }else{
            camera.setTorchMode(ID,false);
        }
    }

    public Fragment getVisibleFragment(){
        FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if(fragments != null){
            for(Fragment fragment : fragments){
                if(fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG,"get:"+v.getId());
        switch (v.getId()){
            case R.id.fab:
                Log.d(TAG,"akari tukero");
                if(camera!=null){
                    try {
                        switchLED();
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }else {
                    Log.d(TAG,"no camera ");
                }
                break;
            case R.id.toFragment1:
                Log.d(TAG,"...?");
                switch (tmpFragID){
                    case 1:
                        break;
                    case 2:
                        tmpFragID = 1;
                        navController.navigate(R.id.action_SecondFragment_to_FirstFragment);
                        break;
                    case 3:
                        tmpFragID = 1;
                        navController.navigate(R.id.action_thirdFragment_to_FirstFragment);
                        break;
                    case 4:
                        tmpFragID = 1;
                        navController.navigate(R.id.action_fourthFragment_to_FirstFragment);
                        break;
                }
                break;
            case R.id.toFragment2:
                switch (tmpFragID){
                    case 1:
                        tmpFragID = 2;
                        navController.navigate(R.id.action_FirstFragment_to_SecondFragment);
                        break;
                    case 2:
                        break;
                    case 3:
                        tmpFragID = 2;
                        navController.navigate(R.id.action_thirdFragment_to_SecondFragment);
                        break;
                    case 4:
                        tmpFragID = 2;
                        navController.navigate(R.id.action_fourthFragment_to_SecondFragment);
                        break;
                }
                break;
            case R.id.toFragment3:
                switch (tmpFragID) {
                    case 1:
                        tmpFragID = 3;
                        navController.navigate(R.id.action_FirstFragment_to_thirdFragment);
                        break;
                    case 2:
                        tmpFragID = 3;
                        navController.navigate(R.id.action_SecondFragment_to_thirdFragment);
                        break;
                    case 3:
                        break;
                    case 4:
                        tmpFragID = 3;
                        navController.navigate((R.id.action_fourthFragment_to_thirdFragment));
                        break;
                }
                break;
            case R.id.toFragment4:
                switch (tmpFragID) {
                    case 1:
                        tmpFragID = 4;
                        navController.navigate(R.id.action_FirstFragment_to_fourthFragment2);
                        break;
                    case 2:
                        tmpFragID = 4;
                        navController.navigate(R.id.action_SecondFragment_to_fourthFragment);
                        break;
                    case 3:
                        tmpFragID = 4;
                        navController.navigate(R.id.action_thirdFragment_to_fourthFragment);
                        break;
                    case 4:
                        break;
                }
                break;
            //NavHostFragment.findNavController().navigate(R.id.action_SecondFragment_to_thirdFragment);

        }
    }
}