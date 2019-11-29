package cn.phoenixsky.pluginable;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 插件化App
        // 0. copy APK到目录
        // 1. 通过DexClassLoader 加载dex文件
        // 2. 拿到对象通过反射




    }

    /**
     * 从assets的apk copy到cache目录
     *
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private File copyApk2Memory() {
        File target = new File(getCacheDir() + "/target.apk");
        Log.i("copyApk2Memory", target.getAbsolutePath());
        try (InputStream inputStream = getAssets().open("apk/pluginable_plugin.apk");
             FileOutputStream fileOutputStream = new FileOutputStream(target)) {
            byte[] buffer = new byte[1024 * 8];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return target;

    }
}