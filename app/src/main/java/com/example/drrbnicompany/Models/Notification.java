package com.example.drrbnicompany.Models;

public class Notification {

    private String notificationId, senderUid, recipientUid, title, body , adsId;

    public Notification(String notificationId, String senderUid, String recipientUid, String title, String body, String adsId) {
        this.notificationId = notificationId;
        this.senderUid = senderUid;
        this.recipientUid = recipientUid;
        this.title = title;
        this.body = body;
        this.adsId = adsId;
    }

    public Notification() {
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getSenderUid() {
        return senderUid;
    }

    public void setSenderUid(String senderUid) {
        this.senderUid = senderUid;
    }

    public String getRecipientUid() {
        return recipientUid;
    }

    public void setRecipientUid(String recipientUid) {
        this.recipientUid = recipientUid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAdsId() {
        return adsId;
    }

    public void setAdsId(String adsId) {
        this.adsId = adsId;
    }
}
