package com.example.wallpaper;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.viewpager.widget.ViewPager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import okhttp3.Call;
import okhttp3.Request;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Testactivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPager viewPager;
    private ArrayList<String> datas;
    private Button buttonl;
    private Button buttond;
    private int poision;
    private static final String TAG=MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager);
        datas = (ArrayList<String>) getIntent().getSerializableExtra("data");
        poision= (int) getIntent().getSerializableExtra("poision");
        viewPager = findViewById(R.id.viewpager1);
        PagerAdapter pagerAdapter=new PagerAdapter(this,datas);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(poision);

        buttond=findViewById(R.id.buttond);
        buttonl=findViewById(R.id.buttonl);
        buttond.setOnClickListener(this);
        buttonl.setOnClickListener(this);
    }

    public void look(){
        Intent intent = new Intent(getApplicationContext(),lookactivity.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("img", datas.get(poision));
        System.out.println("llll"+poision+" "+datas.get(poision));
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void Notification(){
        NotificationManager manager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT>=26){
            NotificationChannel channel = new NotificationChannel("id","chanel",NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        Notification notification=new NotificationCompat.Builder(this,"id")
                .setContentTitle("下载完成")
                .setContentText("已下载完成，可以更换新壁纸了喔！")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.logo)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.logo))
                .build();
        manager.notify(1,notification);
    }

    public void download(){
        String url = datas.get(poision);
        OkHttpUtils//
                .get()//
                .url(url)//
                .build()//
                .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), "bizhi.jpg")//
                {
                    @Override
                    public void onBefore(Request request, int id) {
                    }

                    @Override
                    public void inProgress(float progress, long total, int id) {
                        Log.e(TAG, "inProgress :" + (int) (100 * progress));
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError :" + e.getMessage());
                    }

                    @Override
                    public void onResponse(File file, int id) {
                        Log.e(TAG, "onResponse :" + file.getAbsolutePath());
                        Notification();
                        System.out.println(file.getAbsolutePath());
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonl:
                look();
                System.out.println("全屏查看");
                break;
            case R.id.buttond:
                download();
                System.out.println("下载图片");
                break;
        }

    }
}
