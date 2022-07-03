package com.example.drrbnicompany.Adapters;

import static com.example.drrbnicompany.Constant.STUDENT_DEFAULT_IMAGE;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.drrbnicompany.Fragments.BottomNavigationScreens.NotificationsFragmentDirections;
import com.example.drrbnicompany.Fragments.Dialogs.NotificationInterviewDateFragment;
import com.example.drrbnicompany.Models.Notification;
import com.example.drrbnicompany.Models.Student;
import com.example.drrbnicompany.Notification.FcmNotificationsSender;
import com.example.drrbnicompany.R;
import com.example.drrbnicompany.ViewModels.MyListener;
import com.example.drrbnicompany.ViewModels.NotificationViewModel;
import com.example.drrbnicompany.databinding.CustomNotificationItemBinding;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private List<Notification> notificationList;
    private FragmentActivity activity;
    private Context context;
    private NotificationViewModel notificationViewModel;
    private String uid;
    private NotificationInterviewDateFragment interviewDateFragment;
    private FcmNotificationsSender notificationsSender;

    public NotificationAdapter(List<Notification> notificationList, NotificationViewModel notificationViewModel, String uid, NotificationInterviewDateFragment interviewDateFragment) {
        this.notificationList = notificationList;
        this.notificationViewModel = notificationViewModel;
        this.uid = uid;
        this.interviewDateFragment = interviewDateFragment;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new NotificationAdapter.NotificationViewHolder(LayoutInflater.from(context).inflate(R.layout.custom_notification_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Notification notification = notificationList.get(position);
        holder.bind(notification);
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    class NotificationViewHolder extends RecyclerView.ViewHolder {

        CustomNotificationItemBinding binding;

        Notification notification;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CustomNotificationItemBinding.bind(itemView);
        }


        public void bind(Notification notification) {
            this.notification = notification;
            notificationViewModel.getStudentNameAndImageByUid(uid, new MyListener<Student>() {
                @Override
                public void onValuePosted(Student value) {
                    if (value.getImg() == null) {
                        Glide.with(context).load(STUDENT_DEFAULT_IMAGE).placeholder(R.drawable.anim_progress).into(binding.imageProfileStudent);
                    } else {
                        Glide.with(context).load(value.getImg()).placeholder(R.drawable.anim_progress).into(binding.imageProfileStudent);
                    }
                    binding.studentName.setText(value.getName());
                }
            });

            binding.notificationDescription.setText(notification.getBody());

            binding.btnVisitProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // get student profile by uid
                    notificationViewModel.getStudentNameAndImageByUid(uid, new MyListener<Student>() {
                        @Override
                        public void onValuePosted(Student value) {
                            NavController navController = Navigation.findNavController(binding.getRoot());
                            navController.navigate(NotificationsFragmentDirections
                                    .actionNotificationsFragmentToStudentProfileFragment2(value.getUserId()));
                        }
                    });
                }
            });


            binding.btnAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity = (FragmentActivity) context;
                    FragmentManager manager = activity.getSupportFragmentManager();
                    interviewDateFragment.show(manager, NotificationInterviewDateFragment.TAG);
                }
            });

            binding.btnReject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    notificationViewModel.getNameByUid(notification.getSenderUid(), new MyListener<String>() {
                        @Override
                        public void onValuePosted(String value) {
                            String senderName = value;
                            notificationViewModel.getTokenByStudentId(notification.getRecipientUid(), new MyListener<String>() {
                                @Override
                                public void onValuePosted(String value) {
                                    SendRejectNotification(value, senderName, notification.getAdsId(), activity);
                                }
                            });
                        }
                    });
                }
            });

        }

        public void SendRejectNotification(String recipientToken, String senderName, String adsId, Activity mActivity) {
            if (mActivity == null) return;
            notificationsSender = new FcmNotificationsSender(recipientToken, senderName, adsId, mActivity);
            notificationsSender.SendRejectNotifications();
        }

/*
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
        */
    }
}
