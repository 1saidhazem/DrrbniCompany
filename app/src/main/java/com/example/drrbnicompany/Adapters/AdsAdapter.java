package com.example.drrbnicompany.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.drrbnicompany.Models.Ads;
import com.example.drrbnicompany.Models.Company;
import com.example.drrbnicompany.R;
import com.example.drrbnicompany.ViewModels.MyListener;
import com.example.drrbnicompany.databinding.CustomPostItemBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AdsAdapter extends RecyclerView.Adapter<AdsAdapter.AdsViewHolder> {

    private List<Ads> adsList;
    private Context context;
    private Company company;
    private MyListener<String> listener;

    public AdsAdapter() {}

    public AdsAdapter(List<Ads> adsList, Company company, MyListener<String> listener) {
        this.adsList = adsList;
        this.company = company;
        this.listener = listener;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new AdsViewHolder(LayoutInflater.from(context).inflate(R.layout.custom_post_item , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdsViewHolder holder, int position) {
        Ads ads = adsList.get(position);
        holder.bind(ads);
    }

    @Override
    public int getItemCount() {
        return adsList.size();
    }

    class AdsViewHolder extends RecyclerView.ViewHolder{

        CustomPostItemBinding binding;
        Ads ads;

        public AdsViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CustomPostItemBinding.bind(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onValuePosted(ads.getAdsId());
                }
            });
        }

        public void bind(Ads ads){
            this.ads = ads;
            binding.companyName.setText(company.getName());
            if (company.getImg() == null) {
                binding.companyAvatar.setImageResource(R.drawable.company_defult_image);
            } else {
                Glide.with(context).load(company.getImg()).placeholder(R.drawable.anim_progress).into(binding.companyAvatar);
            }
            binding.postDescription.setText(ads.getAdsDescription());
            SimpleDateFormat formatter = new SimpleDateFormat("d MMMM");
            String dateString = formatter.format(ads.getTimestamp().toDate());
            binding.postTime.setText(dateString);
        }
    }
}
