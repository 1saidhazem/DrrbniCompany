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
import com.example.drrbnicompany.Models.Job;
import com.example.drrbnicompany.Models.Student;
import com.example.drrbnicompany.R;
import com.example.drrbnicompany.ViewModels.MyListener;
import com.example.drrbnicompany.databinding.CustomStudentItemBinding;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentHolder>{

    private List<Student> studentList;
    private Context context;
    private MyListener<String> listener;

    public StudentAdapter(List<Student> studentList, MyListener<String> listener) {
        this.studentList = studentList;
        this.listener = listener;
    }

    public StudentAdapter() {

    }

    @NonNull
    @Override
    public StudentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new StudentHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_student_item , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull StudentHolder holder, int position) {
        Student student = studentList.get(position);
        holder.bind(student);
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    class StudentHolder extends RecyclerView.ViewHolder{

        CustomStudentItemBinding binding;
        Student student;

        public StudentHolder(@NonNull View itemView) {
            super(itemView);
            binding = CustomStudentItemBinding.bind(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onValuePosted(student.getUserId());
                }
            });
        }

        public void bind(Student student){
            this.student = student;

            if (student.getImg() != null){
                binding.progressBar.setVisibility(View.VISIBLE);
                Glide.with(context).load(student.getImg()).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        binding.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                }).into(binding.studentImage);
            }else
                binding.studentImage.setImageResource(R.drawable.defult_img_student);


            binding.studentName.setText(student.getName());
            binding.studentMajor.setText(student.getMajor());
            binding.studentCollage.setText(student.getCollege());
        }
    }
}
