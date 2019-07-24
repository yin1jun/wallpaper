package com.example.wallpaper;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PagerAdapter  extends androidx.viewpager.widget.PagerAdapter  {
    private Context context;
    private ArrayList<String> datas;


    public PagerAdapter(Context context, ArrayList<String> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();//在Viewpager显示3个页面
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView=new ImageView(container.getContext());
        String url1=datas.get(position);
        Glide.with(context).
                load(url1).into(imageView);
        container.addView(imageView); // 添加到ViewPager容器
        return imageView;// 返回填充的View对象

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}
