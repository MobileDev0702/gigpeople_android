package com.gigpeople.app.apiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProfileViewResponse {



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
        @SerializedName("buyer_reviewcount")
        @Expose
        public String buyerReviewcount;
        @SerializedName("buyer_reviews")
        @Expose
        public List<BuyerReview> buyerReviews = null;
        @SerializedName("seller_reviewcount")
        @Expose
        public String sellerReviewcount;
        @SerializedName("seller_reviews")
        @Expose
        public List<SellerReview> sellerReviews = null;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public String getBuyerReviewcount() {
        return buyerReviewcount;
    }

    public void setBuyerReviewcount(String buyerReviewcount) {
        this.buyerReviewcount = buyerReviewcount;
    }

    public List<BuyerReview> getBuyerReviews() {
        return buyerReviews;
    }

    public void setBuyerReviews(List<BuyerReview> buyerReviews) {
        this.buyerReviews = buyerReviews;
    }

    public String getSellerReviewcount() {
        return sellerReviewcount;
    }

    public void setSellerReviewcount(String sellerReviewcount) {
        this.sellerReviewcount = sellerReviewcount;
    }

    public List<SellerReview> getSellerReviews() {
        return sellerReviews;
    }

    public void setSellerReviews(List<SellerReview> sellerReviews) {
        this.sellerReviews = sellerReviews;
    }


    public class BuyerReview {

        @SerializedName("first_name")
        @Expose
        public String firstName;
        @SerializedName("last_name")
        @Expose
        public String lastName;
        @SerializedName("profile")
        @Expose
        public String profile;
        @SerializedName("profile_image_url")
        @Expose
        public String profileImageUrl;
        @SerializedName("review")
        @Expose
        public String review;
        @SerializedName("rating")
        @Expose
        public String rating;
        @SerializedName("is_date")
        @Expose
        public String isDate;

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

        public String getReview() {
            return review;
        }

        public void setReview(String review) {
            this.review = review;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public String getIsDate() {
            return isDate;
        }

        public void setIsDate(String isDate) {
            this.isDate = isDate;
        }
    }
public class SellerReview {

    @SerializedName("first_name")
    @Expose
    public String firstName;
    @SerializedName("last_name")
    @Expose
    public String lastName;
    @SerializedName("profile")
    @Expose
    public String profile;
    @SerializedName("profile_image_url")
    @Expose
    public String profileImageUrl;
    @SerializedName("review")
    @Expose
    public String review;
    @SerializedName("rating")
    @Expose
    public String rating;
    @SerializedName("is_date")
    @Expose
    public String isDate;

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

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getIsDate() {
        return isDate;
    }

    public void setIsDate(String isDate) {
        this.isDate = isDate;
    }
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
    @SerializedName("is_mobile_verified")
    @Expose
    public String isMobileVerified;
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
    @SerializedName("skills")
    @Expose
    public String skills;
    @SerializedName("language")
    @Expose
    public String language;
    @SerializedName("wallet")
    @Expose
    public String wallet;
    @SerializedName("rating")
    @Expose
    public String rating;
    @SerializedName("no_of_reviews")
    @Expose
    public String noOfReviews;
    @SerializedName("no_of_gigpost")
    @Expose
    public String noOfGigpost;
    @SerializedName("join_date")
    @Expose
    public String joinDate;
    @SerializedName("notification")
    @Expose
    public String notification;
    @SerializedName("customer_order")
    @Expose
    public String customerOrder;
    @SerializedName("account_status")
    @Expose
    public String accountStatus;

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

    public String getIsEmailVerified() {
        return isEmailVerified;
    }

    public void setIsEmailVerified(String isEmailVerified) {
        this.isEmailVerified = isEmailVerified;
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

    public String getMobileOtp() {
        return mobileOtp;
    }

    public void setMobileOtp(String mobileOtp) {
        this.mobileOtp = mobileOtp;
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

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getNoOfReviews() {
        return noOfReviews;
    }

    public void setNoOfReviews(String noOfReviews) {
        this.noOfReviews = noOfReviews;
    }

    public String getNoOfGigpost() {
        return noOfGigpost;
    }

    public void setNoOfGigpost(String noOfGigpost) {
        this.noOfGigpost = noOfGigpost;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getCustomerOrder() {
        return customerOrder;
    }

    public void setCustomerOrder(String customerOrder) {
        this.customerOrder = customerOrder;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }
}

}
