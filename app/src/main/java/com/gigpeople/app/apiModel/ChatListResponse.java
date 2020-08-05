package com.gigpeople.app.apiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChatListResponse {
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("chat_list")
    @Expose
    public List<ChatList> chatList = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ChatList> getChatList() {
        return chatList;
    }

    public void setChatList(List<ChatList> chatList) {
        this.chatList = chatList;
    }


    public class ChatList {

        @SerializedName("to_user_id")
        @Expose
        public String toUserId;
        @SerializedName("to_user_details")
        @Expose
        public ToUserDetails toUserDetails;
        @SerializedName("message")
        @Expose
        public String message;
        @SerializedName("message_count")
        @Expose
        public String messageCount;
        @SerializedName("firebase_id")
        @Expose
        public String firebaseId;
        @SerializedName("created_at")
        @Expose
        public String createdAt;

        public String getToUserId() {
            return toUserId;
        }

        public void setToUserId(String toUserId) {
            this.toUserId = toUserId;
        }

        public ToUserDetails getToUserDetails() {
            return toUserDetails;
        }

        public void setToUserDetails(ToUserDetails toUserDetails) {
            this.toUserDetails = toUserDetails;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getMessageCount() {
            return messageCount;
        }

        public void setMessageCount(String messageCount) {
            this.messageCount = messageCount;
        }

        public String getFirebaseId() {
            return firebaseId;
        }

        public void setFirebaseId(String firebaseId) {
            this.firebaseId = firebaseId;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public class ToUserDetails {

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
            @SerializedName("profile_image_name")
            @Expose
            public String profileImageName;
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
            @SerializedName("lattitude")
            @Expose
            public String lattitude;
            @SerializedName("longitude")
            @Expose
            public String longitude;
            @SerializedName("notification")
            @Expose
            public String notification;
            @SerializedName("address")
            @Expose
            public String address;
            @SerializedName("country")
            @Expose
            public String country;
            @SerializedName("wallet")
            @Expose
            public String wallet;
            @SerializedName("account_status")
            @Expose
            public String accountStatus;
            @SerializedName("created_at")
            @Expose
            public String createdAt;

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getFirstName() {
                return firstName;
            }

            public void setFirstName(String firstName) {
                this.firstName = firstName;
            }

            public String getLastName() {
                return lastName;
            }

            public void setLastName(String lastName) {
                this.lastName = lastName;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getEmailOtp() {
                return emailOtp;
            }

            public void setEmailOtp(String emailOtp) {
                this.emailOtp = emailOtp;
            }

            public String getProfileImageName() {
                return profileImageName;
            }

            public void setProfileImageName(String profileImageName) {
                this.profileImageName = profileImageName;
            }

            public String getProfileImageUrl() {
                return profileImageUrl;
            }

            public void setProfileImageUrl(String profileImageUrl) {
                this.profileImageUrl = profileImageUrl;
            }

            public String getMobileCountry() {
                return mobileCountry;
            }

            public void setMobileCountry(String mobileCountry) {
                this.mobileCountry = mobileCountry;
            }

            public String getPhoneNo() {
                return phoneNo;
            }

            public void setPhoneNo(String phoneNo) {
                this.phoneNo = phoneNo;
            }

            public String getMobileOtp() {
                return mobileOtp;
            }

            public void setMobileOtp(String mobileOtp) {
                this.mobileOtp = mobileOtp;
            }

            public String getLattitude() {
                return lattitude;
            }

            public void setLattitude(String lattitude) {
                this.lattitude = lattitude;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }

            public String getNotification() {
                return notification;
            }

            public void setNotification(String notification) {
                this.notification = notification;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public String getWallet() {
                return wallet;
            }

            public void setWallet(String wallet) {
                this.wallet = wallet;
            }

            public String getAccountStatus() {
                return accountStatus;
            }

            public void setAccountStatus(String accountStatus) {
                this.accountStatus = accountStatus;
            }

            public String getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }
        }
    }
}
