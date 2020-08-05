package com.gigpeople.app.apiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("user_id")
    @Expose
    public String userId;
    @SerializedName("user_details")
    @Expose
    public UserDetails userDetails;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getUserId() {
        return userId;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public class UserDetails {

        @SerializedName("user_id")
        @Expose
        public String userId;
        @SerializedName("first_name")
        @Expose
        public String firstName;
        @SerializedName("last_name")
        @Expose
        public String lastName;
        @SerializedName("email")
        @Expose
        public String email;
        @SerializedName("email_otp")
        @Expose
        public String emailOtp;
        @SerializedName("is_email_verified")
        @Expose
        public String isEmailVerified;
        @SerializedName("profile")
        @Expose
        public String profile;
        @SerializedName("profile_image_url")
        @Expose
        public String profileImageUrl;
        @SerializedName("mobile_country")
        @Expose
        public String mobileCountry;
        @SerializedName("phone_no")
        @Expose
        public String phoneNo;
        @SerializedName("mobile_otp")
        @Expose
        public String mobileOtp;
        @SerializedName("address")
        @Expose
        public String address;
        @SerializedName("lattitude")
        @Expose
        public String lattitude;
        @SerializedName("longitude")
        @Expose
        public String longitude;
        @SerializedName("about")
        @Expose
        public String about;
        @SerializedName("language")
        @Expose
        public String language;
        @SerializedName("wallet")
        @Expose
        public String wallet;
        @SerializedName("notification")
        @Expose
        public String notification;
        @SerializedName("account_status")
        @Expose
        public String accountStatus;
        @SerializedName("is_mobile_verified")
        @Expose
        public String isMobileVerified;
        @SerializedName("customer_order")
        @Expose
        public String customerOrder;
        @SerializedName("skills")
        @Expose
        public String skills;

        public String getUserId() {
            return userId;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getEmail() {
            return email;
        }

        public String getEmailOtp() {
            return emailOtp;
        }

        public String getIsEmailVerified() {
            return isEmailVerified;
        }

        public String getProfile() {
            return profile;
        }

        public String getProfileImageUrl() {
            return profileImageUrl;
        }

        public String getMobileCountry() {
            return mobileCountry;
        }

        public String getPhoneNo() {
            return phoneNo;
        }

        public String getMobileOtp() {
            return mobileOtp;
        }

        public String getAddress() {
            return address;
        }

        public String getLattitude() {
            return lattitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public String getAbout() {
            return about;
        }

        public String getLanguage() {
            return language;
        }

        public String getWallet() {
            return wallet;
        }

        public String getNotification() {
            return notification;
        }

        public String getAccountStatus() {
            return accountStatus;
        }

        public String getIsMobileVerified() {
            return isMobileVerified;
        }

        public String getCustomerOrder() {
            return customerOrder;
        }

        public String getSkills() {
            return skills;
        }
    }


}
