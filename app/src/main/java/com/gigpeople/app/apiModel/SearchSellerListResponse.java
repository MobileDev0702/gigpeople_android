package com.gigpeople.app.apiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchSellerListResponse {

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("seller_list")
    @Expose
    public List<SellerList> sellerList = null;

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

    public List<SellerList> getSellerList() {
        return sellerList;
    }

    public void setSellerList(List<SellerList> sellerList) {
        this.sellerList = sellerList;
    }


    public class SellerList {


        @SerializedName("seller_id")
        @Expose
        public String sellerId;
        @SerializedName("first_name")
        @Expose
        public String firstName;
        @SerializedName("last_name")
        @Expose
        public String lastName;
        @SerializedName("email")
        @Expose
        public String email;
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
        @SerializedName("is_email_verified")
        @Expose
        public String isEmailVerified;
        @SerializedName("is_mobile_verified")
        @Expose
        public String isMobileVerified;
        @SerializedName("address")
        @Expose
        public String address;
        @SerializedName("about")
        @Expose
        public String about;
        @SerializedName("skills")
        @Expose
        public String skills;
        @SerializedName("language")
        @Expose
        public String language;
        @SerializedName("rating")
        @Expose
        public String rating;
        @SerializedName("live_status")
        @Expose
        public String liveStatus;
        @SerializedName("is_favourite")
        @Expose
        public String isFavourite;
        @SerializedName("join_date")
        @Expose
        public String joinDate;
        @SerializedName("orders_completed")
        @Expose
        public String ordersCompleted;

        public String getSellerId() {
            return sellerId;
        }

        public void setSellerId(String sellerId) {
            this.sellerId = sellerId;
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

        public String getProfile() {
            return profile;
        }

        public void setProfile(String profile) {
            this.profile = profile;
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

        public String getIsEmailVerified() {
            return isEmailVerified;
        }

        public void setIsEmailVerified(String isEmailVerified) {
            this.isEmailVerified = isEmailVerified;
        }

        public String getIsMobileVerified() {
            return isMobileVerified;
        }

        public void setIsMobileVerified(String isMobileVerified) {
            this.isMobileVerified = isMobileVerified;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAbout() {
            return about;
        }

        public void setAbout(String about) {
            this.about = about;
        }

        public String getSkills() {
            return skills;
        }

        public void setSkills(String skills) {
            this.skills = skills;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public String getLiveStatus() {
            return liveStatus;
        }

        public void setLiveStatus(String liveStatus) {
            this.liveStatus = liveStatus;
        }

        public String getIsFavourite() {
            return isFavourite;
        }

        public void setIsFavourite(String isFavourite) {
            this.isFavourite = isFavourite;
        }

        public String getJoinDate() {
            return joinDate;
        }

        public void setJoinDate(String joinDate) {
            this.joinDate = joinDate;
        }

        public String getOrdersCompleted() {
            return ordersCompleted;
        }

        public void setOrdersCompleted(String ordersCompleted) {
            this.ordersCompleted = ordersCompleted;
        }
    }


}
