# CX_handler_demo
Android上手练习8.Handler

安卓开发中要了解的Handler消息传递机制，先阅读：
[http://www.runoob.com/w3cnote/android-tutorial-handler-message.html](http://www.runoob.com/w3cnote/android-tutorial-handler-message.html)

简单来讲，耗时任务要在子线程里进行，然后通过给Handler发送Message，通知更新UI。

示例：
layout_main.xml
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:gravity="center_horizontal"
    android:orientation="vertical"
    tools:context=".MainActivity">

    <TextView
        android:id="@+id/tv1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_margin="20dp"
        android:textSize="20sp"
        android:text="0" />

    <ImageView
        android:id="@+id/image"
        android:layout_width="100dp"
        android:layout_height="100dp" />

</LinearLayout>
```

MainActivity.java

```
package net.gzchunxiang.cx_handler_demo;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    ImageView imageView;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.tv1);
        imageView = findViewById(R.id.image);

        //两个使用Handler的示例

        //1.十秒后设置imageView
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //这里是一个子线程
                handler.sendEmptyMessage(0);
            }
        },1000*10);

        //2.定时任务，每过1秒更新textView一次
        Timer timer  = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                count++;
                Message msg = new Message();
                msg.what = 1;
                Bundle data = new Bundle();
                data.putInt("count",count);
                msg.setData(data);
                handler.sendMessage(msg);
            }
        },1000,1000);
    }


    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        //这里是UI线程，可更新UI
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    //10秒后收到msg的操作
                    imageView.setImageResource(R.mipmap.ic_launcher);
                    break;
                case 1:
                    int i = msg.getData().getInt("count");
                    textView.setText(String.valueOf(i));
                    break;
            }

        }
    };
}

```
