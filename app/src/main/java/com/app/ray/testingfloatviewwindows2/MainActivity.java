package com.app.ray.testingfloatviewwindows2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "TestFloatWinActivity";
    public static int OVERLAY_PERMISSION_REQ_CODE = 1234;
    //开启悬浮窗的Service
    Intent floatWinIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        floatWinIntent = new Intent(MainActivity.this, FloatWinService.class);
    }

    /**
     * 按下begin按钮
     *
     * @param v
     */
    public void begin(View v) {
        //开启悬浮框前先请求权限
        askForPermission();
    }

    /**
     * 按下end按钮
     *
     * @param v
     */
    public void end(View v) {
        //关闭悬浮框
        stopService(floatWinIntent);
    }


    /**
     * 请求用户给予悬浮窗的权限
     */
    public void askForPermission() {
        if (!Settings.canDrawOverlays(this)) {
            Toast.makeText(MainActivity.this, "当前无权限，请授权！", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
        } else {
            startService(floatWinIntent);
        }
    }

    /**
     * 用户返回
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == OVERLAY_PERMISSION_REQ_CODE) {
            if (!Settings.canDrawOverlays(this)) {
                Toast.makeText(MainActivity.this, "权限授予失败，无法开启悬浮窗", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "权限授予成功！", Toast.LENGTH_SHORT).show();
                //启动FxService
                startService(floatWinIntent);
            }

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}