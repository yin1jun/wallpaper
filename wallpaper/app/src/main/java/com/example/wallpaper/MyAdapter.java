package com.example.wallpaper;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private  Context context;
    private  ArrayList<String> datas;
    public MyAdapter(Context context, ArrayList<String> datas){
        this.context=context;
        this.datas=datas;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        return new MyViewHolder(inflater.inflate(R.layout.wallpaper,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, final int i){
        String url1=datas.get(i);
        MyViewHolder myViewHolder = (MyViewHolder)viewHolder;
        Glide.with(context).
                load(url1).into(myViewHolder.imageView);
        myViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("HHHH");
                Intent intent = new Intent(context,Testactivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("data", (Serializable) datas);
                bundle.putSerializable("poision", i);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageview1);
            /*imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("HHHH");
                    Intent intent = new Intent(context,Testactivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("data", (Serializable) datas);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });*/
        }
    }
}
