package com.safa.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.safa.myapplication.databinding.ActivityLikeBinding;
import com.safa.myapplication.databinding.ActivityMainBinding;

public class LikeActivity extends AppCompatActivity {

    ActivityLikeBinding binding;
    int Count = 20;// number from DB
    boolean isClick = false;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_like);
        binding = ActivityLikeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.count.setText(String.valueOf(Count));
        binding.favorite.setBackgroundResource(R.drawable.favorite_empty);

        binding.favorite.setOnClickListener(view -> {
            if (isClick){
                Count -= 1;
                binding.count.setText(String.valueOf(Count));
                binding.favorite.setBackgroundResource(R.drawable.favorite_empty);
                isClick = false;
            }else {
                Count += 1;
                binding.count.setText(String.valueOf(Count));
                binding.favorite.setBackgroundResource(R.drawable.favorite_full);
                isClick = true;
            }
        });

    }
}