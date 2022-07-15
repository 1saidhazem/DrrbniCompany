package com.example.drrbnicompany.Adapters;


import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import static com.example.drrbnicompany.Constant.STUDENT_DEFAULT_IMAGE_PROFILE;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.drrbnicompany.Models.Job;
import com.example.drrbnicompany.R;
import com.example.drrbnicompany.databinding.CustomJobItemBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

public class HomeAdapter extends FirestoreAdapter<HomeAdapter.ViewHolder> {

    private OnJobSelectedListener mListener;

    public interface OnJobSelectedListener {

        void onJobSelected(Job job);

    }



    public HomeAdapter(Query query, OnJobSelectedListener mListener) {
        super(query);
        this.mListener = mListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_job_item , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getSnapshot(position), mListener);

    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        CustomJobItemBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CustomJobItemBinding.bind(itemView);
        }


        public void bind(final DocumentSnapshot snapshot,
                         final OnJobSelectedListener listener) {

            load();

            Job job = snapshot.toObject(Job.class);

            binding.jobTitle.setText(job.getJobName());
            binding.jobDescription.setText(job.getJobDescription());
            binding.progressBar.setVisibility(View.VISIBLE);
            Glide.with(binding.jobImage.getContext()).load(job.getImg()).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    binding.progressBar.setVisibility(View.GONE);
                    return false;
                }
            }).into(binding.jobImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onJobSelected(job);
                }
            });

            stopLoad();
        }

        public void load() {
            binding.shimmerView.setVisibility(View.VISIBLE);
            binding.shimmerView.startShimmerAnimation();
            binding.customJobsLayout.setVisibility(View.GONE);
        }

        public void stopLoad() {
            binding.shimmerView.setVisibility(View.GONE);
            binding.shimmerView.stopShimmerAnimation();
            binding.customJobsLayout.setVisibility(View.VISIBLE);
        }

    }


}
