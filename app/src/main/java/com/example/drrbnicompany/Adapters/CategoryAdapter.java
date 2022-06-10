package com.example.drrbnicompany.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.drrbnicompany.Models.Category;
import com.example.drrbnicompany.R;
import com.example.drrbnicompany.ViewModels.MyListener;
import com.example.drrbnicompany.databinding.CustomCategoryItemBinding;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>{

    private List<Category> categoryList;
    private Context context;
    private MyListener<Integer> listener;

    public CategoryAdapter() {}

    public CategoryAdapter(List<Category> categoryList,MyListener<Integer> listener) {
        this.categoryList = categoryList;
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
        Category category = categoryList.get(position);
        holder.bind(category);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {

        CustomCategoryItemBinding binding;
        Category category;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CustomCategoryItemBinding.bind(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onValuePosted(getAdapterPosition());
                }
            });
        }

        public void bind(Category category) {
            this.category = category;
            Glide.with(context).load(category.getImage()).placeholder(R.drawable.anim_progress).into(binding.imgBg);
            binding.tvCategoryName.setText(category.getName());
        }
    }
}
