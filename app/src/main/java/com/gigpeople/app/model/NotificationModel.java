package com.gigpeople.app.model;

public class NotificationModel {
    int notify;
    String notification,date;
    public NotificationModel(int notify, String notification, String date) {
        this.notify = notify;
        this.notification=notification;
        this.date=date;

    }

    public int getNotify() {
        return notify;
    }

    public String getNotification() {
        return notification;
    }
    public String getDate() {
        return date;
    }


}
