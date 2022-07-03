package com.example.drrbnicompany.Adapters;

import static com.example.drrbnicompany.Constant.COMPANY_DEFAULT_IMAGE;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.drrbnicompany.Fragments.BottomNavigationScreens.ProfileFragmentDirections;
import com.example.drrbnicompany.Models.Ads;
import com.example.drrbnicompany.Models.Company;
import com.example.drrbnicompany.R;
import com.example.drrbnicompany.ViewModels.MyListener;
import com.example.drrbnicompany.ViewModels.ProfileViewModel;
import com.example.drrbnicompany.databinding.CustomPostItemBinding;
import com.google.android.material.snackbar.Snackbar;
import java.text.SimpleDateFormat;
import java.util.List;

public class AdsAdapter extends RecyclerView.Adapter<AdsAdapter.AdsViewHolder> {

    private List<Ads> adsList;
    private Context context;
    private ProfileViewModel profileViewModel;
    private String uid;

    public AdsAdapter() {}

    public AdsAdapter(List<Ads> adsList, ProfileViewModel profileViewModel , String uid) {
        this.adsList = adsList;
        this.profileViewModel = profileViewModel;
        this.uid = uid;
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
        }

        public void bind(Ads ads){
            load();
            this.ads = ads;
            profileViewModel.getCompanyImgAndName(uid, new MyListener<Company>() {
                @Override
                public void onValuePosted(Company value) {
                    binding.companyName.setText(value.getName());
                    if (value.getImg() == null) {
                        Glide.with(itemView.getContext()).load(COMPANY_DEFAULT_IMAGE).placeholder(R.drawable.anim_progress).into(binding.companyAvatar);
                    } else {
                        Glide.with(context).load(value.getImg()).placeholder(R.drawable.anim_progress).into(binding.companyAvatar);
                    }
                    stopLoad();
                }
            });

            binding.postDescription.setText(ads.getAdsDescription());
            SimpleDateFormat formatter = new SimpleDateFormat("d MMMM");
            String dateString = formatter.format(ads.getTimestamp().toDate());
            binding.postTime.setText(dateString);

            binding.companyMenu.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.Q)
                @Override
                public void onClick(View view) {
                    PopupMenu popup = new PopupMenu(context , binding.companyMenu);
                    popup.inflate(R.menu.ads_menu);
                    popup.setForceShowIcon(true);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()){
                                case R.id.adsMenuEdit:
                                    NavController navController = Navigation.findNavController(binding.getRoot());
                                    navController.navigate(ProfileFragmentDirections
                                            .actionProfileFragmentToShowAndEditAdsFragment(ads.getAdsId()));
                                    return true;
                                case R.id.adsMenuDelete:
                                    profileViewModel.deleteAds(ads.getAdsId(), new MyListener<Boolean>() {
                                        @Override
                                        public void onValuePosted(Boolean value) {
                                            Snackbar.make(binding.getRoot() , "تم الحذف" , Snackbar.LENGTH_LONG).show();

                                        }
                                    }, new MyListener<Boolean>() {
                                        @Override
                                        public void onValuePosted(Boolean value) {
                                            Snackbar.make(binding.getRoot() , "فشل الحذف" , Snackbar.LENGTH_LONG).show();
                                        }
                                    });
                                    return true;
                                default:
                                    return false;
                            }
                        }
                    });
                    popup.show();
                }
            });
        }

        public void load() {
            binding.shimmerView.setVisibility(View.VISIBLE);
            binding.shimmerView.startShimmerAnimation();
            binding.customPostItemLayout.setVisibility(View.GONE);
        }

        public void stopLoad() {
            binding.shimmerView.setVisibility(View.GONE);
            binding.shimmerView.stopShimmerAnimation();
            binding.customPostItemLayout.setVisibility(View.VISIBLE);
        }
    }


}
