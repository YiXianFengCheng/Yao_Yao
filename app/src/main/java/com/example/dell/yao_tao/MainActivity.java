package com.example.dell.yao_tao;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public TextView yao;
    public SensorManager sensorManager;

    private SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            int sensorType = event.sensor.getType();
            //values[0]:X轴，values[1]：Y轴，values[2]：Z轴
            float[] values = event.values;
            float x = values[0];
            float y = values[1];
            float z = values[2];
            //获得传感器的类型，对传感器数组赋值
            if (sensorType == Sensor.TYPE_ACCELEROMETER) {
                final int value = 15;//摇一摇阀值,不同手机能达到的最大值不同,如某品牌手机只能达到20
                if (x >= value || x <= -value || y >= value || y <= -value || z >= value || z <= -value) {
                    yao.setText("你已经摇了");
                }
            }
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        yao = (TextView)findViewById(R.id.yao);
        //获取传感器的管理器
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        //获取加速度传感器
        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(listener, accelerometerSensor,
                SensorManager.SENSOR_DELAY_GAME);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {//退出的时候移除监听器
        super.onDestroy();

        if (sensorManager != null) {
            sensorManager.unregisterListener(listener);
        }
    }
}
