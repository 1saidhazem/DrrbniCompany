package com.example.drrbnicompany.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.drrbnicompany.Models.Job;
import com.example.drrbnicompany.R;
import com.example.drrbnicompany.ViewModels.MyListener;
import com.example.drrbnicompany.databinding.CustomJobItemBinding;

import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobViewHolder> {

    private List<Job> jobList;
    private Context context;
    private MyListener<String> listener;

    public JobAdapter() {}

    public JobAdapter(List<Job> jobList , MyListener<String> listener) {
        this.jobList = jobList;
        this.listener = listener;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new JobViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_job_item , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {
        Job job = jobList.get(position);
        holder.bind(job);
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    class JobViewHolder extends RecyclerView.ViewHolder {

        CustomJobItemBinding binding;
        Job job;
        public JobViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CustomJobItemBinding.bind(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onValuePosted(job.getJobId());
                }
            });
        }

        public void bind(Job job) {
            this.job = job;
            Glide.with(context).load(job.getImg()).placeholder(R.drawable.anim_progress).into(binding.jobImage);
            binding.jobTitle.setText(job.getJobName());
            binding.jobDescription.setText(job.getJobDescription());

        }
    }

}


