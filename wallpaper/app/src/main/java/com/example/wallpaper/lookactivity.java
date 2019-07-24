package com.example.wallpaper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

import java.net.URL;
import java.util.ArrayList;

public class lookactivity extends AppCompatActivity implements View.OnClickListener {
   private String url;
   private ImageView imageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView=findViewById(R.id.img);
        imageView.setOnClickListener(this);
        url= (String) getIntent().getSerializableExtra("img");
        Glide.with(getApplicationContext()).
                load(url).into(imageView);
    }
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getApplicationContext(),Testactivity.class);
        startActivity(intent);
    }
}
