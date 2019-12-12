package cn.phoenixsky.interview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    public static final int MSG_CODE_LOAD_DATA = 1;

    private static final String TAG = "MainActivity";
    private Thread workThread;

    private Object lock = new Object();

    static class MyHandler<T extends AppCompatActivity> extends Handler {
        private WeakReference<T> weakReference;

        MyHandler(T activity) {
            this.weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            T t = weakReference.get();
            switch (msg.what) {
                case MSG_CODE_LOAD_DATA:
                    break;
            }
            super.handleMessage(msg);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HashMap hashMap = new HashMap();



        Integer integer = Integer.valueOf("1");

        int[] arr = {3, 1, 39, 20, 12, 4, 99, 35, 48, 101, 100};

        bubbleSort(arr);


        Handler handler = new MyHandler<MainActivity>(this);

        fetchData(savedInstanceState != null);

        handler.sendMessage(handler.obtainMessage());  // delay = SystemClock.uptimeMillis() + 0
        // 将runnable包装到Message里
        handler.post(new Runnable() {
            @Override
            public void run() {
            }
        });
    }

    void fetchData(final boolean needWait) {
        final MainActivity mainActivity = this;
        workThread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (needWait) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Log.i(TAG, "Thread run : 我现在开始加载控件");
                try {
                    Log.i(TAG, "loading... ");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.i(TAG, "Thread run : 我现在加载好了");
            }
        });}


    @Override
    protected synchronized void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult：对控件进行操作了");
//        synchronized (this){
        workThread.interrupt();
//        }
    }


    /**
     * 冒泡
     *
     * @param arr
     */
    void bubbleSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            boolean exchanged = false;
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    exchanged = true;
                    swap(arr, j, j + 1);
                }
            }
            // 没有更改
            if (!exchanged) {
                break;
            }
        }
        System.out.println(Arrays.toString(arr));
    }


    void swap(int[] arr, int a, int b) {
        arr[a] = arr[a] ^ arr[b];
        arr[b] = arr[a] ^ arr[b];
        arr[a] = arr[a] ^ arr[b];
    }
}
