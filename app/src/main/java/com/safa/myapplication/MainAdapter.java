package com.safa.myapplication;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.safa.myapplication.databinding.ItemLayoutBinding;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private Activity context;
    private ArrayList<MainData> dataArrayList;

    public MainAdapter(Activity context, ArrayList<MainData> dataArrayList) {
        this.context = context;
        this.dataArrayList = dataArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLayoutBinding binding = ItemLayoutBinding.inflate(LayoutInflater.from(context), parent,
                false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        MainData data = dataArrayList.get(position);

        Glide.with(context).load(data.getImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.binding.imageView);

        holder.binding.textView.setText(data.getTitle());

    }

    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ItemLayoutBinding binding;

        public ViewHolder(@NonNull ItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
