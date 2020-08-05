package com.gigpeople.app.apiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommonResponse {

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("mobile_otp")
    @Expose
    public String mobileOtp;
    @SerializedName("email_otp")
    @Expose
    public String emailOtp;
    @SerializedName("New Password")
    @Expose
    public String NewPassword;
    @SerializedName("chat_status")
    @Expose
    public String chatStatus;
    @SerializedName("amount_earned")
    @Expose
    public String amount_earned;
    @SerializedName("unread_notification_count")
    @Expose
    public String unreadNotificationCount;


    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getMobileOtp() {
        return mobileOtp;
    }

    public String getEmailOtp() {
        return emailOtp;
    }

    public String getNewPassword() {
        return NewPassword;
    }

    public String getChatStatus() {
        return chatStatus;
    }

    public String getAmount_earned() {
        return amount_earned;
    }

    public String getUnreadNotificationCount() {
        return unreadNotificationCount;
    }
}
