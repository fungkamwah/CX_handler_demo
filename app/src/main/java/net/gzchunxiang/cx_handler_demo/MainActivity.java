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

        //1.
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
