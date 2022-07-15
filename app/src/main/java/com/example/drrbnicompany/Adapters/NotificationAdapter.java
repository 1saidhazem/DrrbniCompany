package com.example.drrbnicompany.Adapters;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import static com.example.drrbnicompany.Constant.STUDENT_DEFAULT_IMAGE;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.drrbnicompany.Fragments.BottomNavigationScreens.NotificationsFragmentDirections;
import com.example.drrbnicompany.Models.Notification;
import com.example.drrbnicompany.Models.Student;
import com.example.drrbnicompany.Notification.FcmNotificationsSender;
import com.example.drrbnicompany.R;
import com.example.drrbnicompany.ViewModels.MyListener;
import com.example.drrbnicompany.ViewModels.NotificationViewModel;
import com.example.drrbnicompany.databinding.CustomNotificationItemBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private List<Notification> notifications;
    private NotificationViewModel notificationViewModel;
    private Context context;
    private Activity activity;
    private FcmNotificationsSender notificationsSender;
    private String uid;

    public NotificationAdapter(List<Notification> notifications, Activity activity
                               ,NotificationViewModel notificationViewModel , String uid) {
        this.notifications = notifications;
        this.activity = activity;
        this.notificationViewModel = notificationViewModel;
        this.uid = uid;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new NotificationViewHolder(LayoutInflater.from(context).inflate(R.layout.custom_notification_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Notification notification = notifications.get(position);
        holder.bind(notification);
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    class NotificationViewHolder extends RecyclerView.ViewHolder {

        CustomNotificationItemBinding binding;
        Notification notification;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CustomNotificationItemBinding.bind(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NavController navController = Navigation.findNavController(binding.getRoot());
                    navController.navigate(NotificationsFragmentDirections
                            .actionNotificationsFragmentToShowAndEditAdsFragment2(notification.getAdsId()));

                }
            });
        }

        public void bind(Notification notification){
            this.notification = notification;
            binding.progressBar.setVisibility(View.VISIBLE);

            notificationViewModel.getSender(notification.getSenderUid(), new MyListener<Student>() {
                @Override
                public void onValuePosted(Student value) {
                    if (value.getImg() != null){
                        Glide.with(binding.senderImg.getContext()).load(value.getImg()).addListener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                binding.progressBar.setVisibility(View.GONE);
                                return false;
                            }
                        }).into(binding.senderImg);
                    }else {
                        Glide.with(binding.senderImg.getContext()).load(STUDENT_DEFAULT_IMAGE).addListener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                               binding.progressBar.setVisibility(View.GONE);
                               return false;
                            }
                        }).into(binding.senderImg);
                    }

                    binding.message.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(Intent.ACTION_SENDTO);
                            Uri email = Uri.fromParts("mailto" ,value.getEmail() , null);
                            intent.setData(email);
                            context.startActivity(intent);

                            notificationViewModel.getNameByUid(uid, new MyListener<String>() {
                                @Override
                                public void onValuePosted(String name) {

                                    notificationViewModel.getTokenByStudentId(notification.getSenderUid(), new MyListener<String>() {
                                        @Override
                                        public void onValuePosted(String token) {

                                            sendNotification(notification.getAdsId() , token , name);
                                        }
                                    });
                                }
                            });
                        }
                    });

                    binding.profile.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            NavController navController = Navigation.findNavController(binding.getRoot());
                            navController.navigate(NotificationsFragmentDirections
                                    .actionNotificationsFragmentToStudentProfileFragment2(value.getUserId()));

                        }
                    });

                }
            });

            binding.notificationBody.setText(notification.getBody());
            binding.notificationTitle.setText(notification.getTitle());

        }

        public void sendNotification(String adsId , String recipientToken , String senderName){
            notificationsSender = new FcmNotificationsSender(recipientToken, senderName ,adsId , activity);
            notificationsSender.SendMessageNotifications();
        }
    }
}
