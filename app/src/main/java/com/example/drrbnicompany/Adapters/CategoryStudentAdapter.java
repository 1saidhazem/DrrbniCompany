package com.example.drrbnicompany.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.drrbnicompany.Models.Student;
import com.example.drrbnicompany.R;
import com.example.drrbnicompany.ViewModels.MyListener;
import com.example.drrbnicompany.databinding.CustomStudentItemBinding;
import java.util.List;

public class CategoryStudentAdapter extends RecyclerView.Adapter<CategoryStudentAdapter.studentsViewHolder>{

    private List<Student> studentList;
    private Context context;
    private MyListener<String> listener;

    public CategoryStudentAdapter(List<Student> studentList, MyListener<String> listener) {
        this.studentList = studentList;
        this.listener = listener;
        notifyDataSetChanged();
    }

    public CategoryStudentAdapter() {}

    @NonNull
    @Override
    public CategoryStudentAdapter.studentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new CategoryStudentAdapter.studentsViewHolder(LayoutInflater.from(context).inflate(R.layout.custom_student_item , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryStudentAdapter.studentsViewHolder holder, int position) {
        Student student = studentList.get(position);
        holder.bind(student);
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    class studentsViewHolder extends RecyclerView.ViewHolder{

        CustomStudentItemBinding binding;
        Student student;

        public studentsViewHolder(@NonNull View itemView) {
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
            binding.studentName.setText(student.getName());
            if (student.getImg() == null) {
                binding.imageStudent.setImageResource(R.drawable.company_defult_image);
            } else {
                Glide.with(context).load(student.getImg()).placeholder(R.drawable.anim_progress).into(binding.imageStudent);
            }
            binding.universityName.setText(student.getCollege());
            binding.specializationName.setText(student.getMajor());
        }
    }
}
