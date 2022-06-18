package com.example.drrbnicompany.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.drrbnicompany.Models.Major;
import com.example.drrbnicompany.R;
import com.example.drrbnicompany.ViewModels.MyListener;
import com.example.drrbnicompany.databinding.CustomCategoryItemBinding;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>{

    private List<Major> majorList;
    private Context context;
    private MyListener<String> listener;

    public CategoryAdapter() {}

    public CategoryAdapter(List<Major> majorList, MyListener<String> listener) {
        this.majorList = majorList;
        this.listener = listener;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new CategoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_category_item , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Major major = majorList.get(position);
        holder.bind(major);
    }

    @Override
    public int getItemCount() {
        return majorList.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {

        CustomCategoryItemBinding binding;
        Major major;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CustomCategoryItemBinding.bind(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onValuePosted(major.getName());
                }
            });
        }

        public void bind(Major major) {
            this.major = major;
            binding.tvCategoryName.setText(major.getName());
            binding.progressBar.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(major.getImage())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            binding.progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(binding.imgBg);

        }
    }
}
