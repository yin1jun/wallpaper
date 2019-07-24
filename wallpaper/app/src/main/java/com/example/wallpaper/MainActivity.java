package com.example.wallpaper;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.alibaba.fastjson.JSON;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button Button1;
    private Button Button2;
    private Button Button3;
    private TextView textview;
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private ArrayList<String> datas;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first);
        verifyStoragePermissions(this);
        initButon();
        initData();
        initRecycle();
        //getjson();

    }


    private  final int REQUEST_EXTERNAL_STORAGE = 1;
    private  String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE };
    public  void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }

    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {

                List<thirdlevel> x = (List<thirdlevel>) msg.obj;
                load(x);


        }
    };

    public void initButon(){
    Button1=findViewById(R.id.button1);
    Button2=findViewById(R.id.button2);
    Button3=findViewById(R.id.button3);
    Button1.setOnClickListener(this);
    Button2.setOnClickListener(this);
    Button3.setOnClickListener(this);
    }

    public void initRecycle(){
        recyclerView=findViewById(R.id.recycleview1);
        myAdapter=new MyAdapter(MainActivity.this,datas);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,3,GridLayoutManager.VERTICAL,false));

    }

    public void initData(){
        datas=new ArrayList<String>();
        for(int i=0;i<19;i++){
            datas.add("https://www.baidu.com/img/bd_logo1.png?where=super");
        }
    }

    private void load(List<thirdlevel> img){
       if(isNetworkAvailable(getApplicationContext())){
        datas.clear();
        for(int i=0;i<img.size();i++){
            datas.add(img.get(i).getImg());
        }
        myAdapter = new MyAdapter(this,datas);
        recyclerView.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
    }
    }



    public void getjson(String url){
        List<thirdlevel> imglist=new ArrayList<>();
        System.out.println("1");
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback()
                {
                    @Override
                    public void onError(okhttp3.Call call, Exception e, int id) {
                        System.out.println("2 "+e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        System.out.println("3");
                        List<thirdlevel> img=jsonaction(response);
                        Message message = new Message();
                        message.obj = img;
                        handler.sendMessage(message);
                        System.out.println("4");
                    }
                });

        System.out.println("5");
    }

    public List<thirdlevel> jsonaction(String response){
        firstlevel firstlevel= JSON.parseObject(response,firstlevel.class);
        List<thirdlevel> img=firstlevel.getRes().getVertical();
        return img;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected())
            {
                System.out.println("当前网络是连接的");
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED)
                {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button1:
                getjson("http://service.picasso.adesk.com/v1/lightwp/vertical?adult=0&first=1&limit=30&order=mixin&skip=0");
                System.out.println("最新");
                break;
            case R.id.button2:
                getjson("http://service.picasso.adesk.com/v1/lightwp/vertical?adult=0&first=1&limit=30&order=hot&skip=30");
                System.out.println("分类");
                break;
            case R.id.button3:
                getjson("http://service.picasso.adesk.com/v1/lightwp/vertical?adult=0&first=1&limit=0&order=mixin&skip=30");
                System.out.println("热门");
                break;
        }
    }
}
