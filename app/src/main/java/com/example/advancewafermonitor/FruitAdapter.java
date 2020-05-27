package com.example.advancewafermonitor;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.advancewafermonitor.pojo.Fruit;

import java.util.List;

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {

    private Context context;
    private List<Fruit> fruitList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            imageView = itemView.findViewById(R.id.fruit_image);
            textView = itemView.findViewById(R.id.fruit_name);
        }
    }

    public FruitAdapter(List<Fruit> fruitList) {
        this.fruitList = fruitList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.fruit_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getBindingAdapterPosition();
                Fruit fruit=fruitList.get(position);
                Intent intent=new Intent(context,FruitActivity.class);
                intent.putExtra(FruitActivity.FRUIT_NAME,fruit.getName());
                intent.putExtra(FruitActivity.FRUIT_IMAGE_ID,fruit.getImageId());
                context.startActivity(intent);
            }
        });
        return holder;


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Fruit fruit = fruitList.get(position);
        holder.textView.setText(fruit.getName());
        Glide.with(context).load(fruit.getImageId()).into(holder.imageView);
    }


    @Override
    public int getItemCount() {
        return fruitList.size();
    }
}
