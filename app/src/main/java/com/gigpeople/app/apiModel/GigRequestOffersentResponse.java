package com.gigpeople.app.apiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GigRequestOffersentResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("request_Details")
    @Expose
    private List<RequestDetail> requestDetails = null;

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

    public List<RequestDetail> getRequestDetails() {
        return requestDetails;
    }

    public void setRequestDetails(List<RequestDetail> requestDetails) {
        this.requestDetails = requestDetails;
    }



    public class RequestDetail {

        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("user_details")
        @Expose
        private UserDetails userDetails;
        @SerializedName("request_id")
        @Expose
        private String requestId;
        @SerializedName("category_id")
        @Expose
        private String categoryId;
        @SerializedName("category_name")
        @Expose
        private String categoryName;
        @SerializedName("category_icon")
        @Expose
        private String categoryIcon;
        @SerializedName("sub_category_id")
        @Expose
        private String subCategoryId;
        @SerializedName("sub_category_name")
        @Expose
        private String subCategoryName;
        @SerializedName("sub_category_icon")
        @Expose
        private String subCategoryIcon;
        @SerializedName("image_list")
        @Expose
        private List<ImageList> imageList = null;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("quantity")
        @Expose
        private String quantity;
        @SerializedName("deliverytime")
        @Expose
        private String deliverytime;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("my_offer")
        @Expose
        private MyOffer myOffer;

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

        public String getRequestId() {
            return requestId;
        }

        public void setRequestId(String requestId) {
            this.requestId = requestId;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getCategoryIcon() {
            return categoryIcon;
        }

        public void setCategoryIcon(String categoryIcon) {
            this.categoryIcon = categoryIcon;
        }

        public String getSubCategoryId() {
            return subCategoryId;
        }

        public void setSubCategoryId(String subCategoryId) {
            this.subCategoryId = subCategoryId;
        }

        public String getSubCategoryName() {
            return subCategoryName;
        }

        public void setSubCategoryName(String subCategoryName) {
            this.subCategoryName = subCategoryName;
        }

        public String getSubCategoryIcon() {
            return subCategoryIcon;
        }

        public void setSubCategoryIcon(String subCategoryIcon) {
            this.subCategoryIcon = subCategoryIcon;
        }

        public List<ImageList> getImageList() {
            return imageList;
        }

        public void setImageList(List<ImageList> imageList) {
            this.imageList = imageList;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getDeliverytime() {
            return deliverytime;
        }

        public void setDeliverytime(String deliverytime) {
            this.deliverytime = deliverytime;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public MyOffer getMyOffer() {
            return myOffer;
        }

        public void setMyOffer(MyOffer myOffer) {
            this.myOffer = myOffer;
        }


        public class UserDetails {

            @SerializedName("user_id")
            @Expose
            private String userId;
            @SerializedName("first_name")
            @Expose
            private String firstName;
            @SerializedName("last_name")
            @Expose
            private String lastName;
            @SerializedName("email")
            @Expose
            private String email;
            @SerializedName("profile")
            @Expose
            private String profile;
            @SerializedName("profile_image_url")
            @Expose
            private String profileImageUrl;
            @SerializedName("phone_no")
            @Expose
            private String phoneNo;
            @SerializedName("is_mobile_verified")
            @Expose
            private String isMobileVerified;
            @SerializedName("is_email_verified")
            @Expose
            private String isEmailVerified;
            @SerializedName("address")
            @Expose
            private String address;
            @SerializedName("country")
            @Expose
            private String country;
            @SerializedName("about")
            @Expose
            private String about;
            @SerializedName("skills")
            @Expose
            private String skills;
            @SerializedName("language")
            @Expose
            private String language;
            @SerializedName("rating")
            @Expose
            private String rating;
            @SerializedName("live_status")
            @Expose
            private String liveStatus;
            @SerializedName("join_date")
            @Expose
            private String joinDate;
            @SerializedName("orders_completed")
            @Expose
            private String ordersCompleted;

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

            public String getPhoneNo() {
                return phoneNo;
            }

            public void setPhoneNo(String phoneNo) {
                this.phoneNo = phoneNo;
            }

            public String getIsMobileVerified() {
                return isMobileVerified;
            }

            public void setIsMobileVerified(String isMobileVerified) {
                this.isMobileVerified = isMobileVerified;
            }

            public String getIsEmailVerified() {
                return isEmailVerified;
            }

            public void setIsEmailVerified(String isEmailVerified) {
                this.isEmailVerified = isEmailVerified;
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

        public class MyOffer {

            @SerializedName("deliverytime")
            @Expose
            private String deliverytime;
            @SerializedName("price")
            @Expose
            private String price;
            @SerializedName("description")
            @Expose
            private String description;
            @SerializedName("offer_status")
            @Expose
            private String offer_status;

            public String getOffer_status() {
                return offer_status;
            }

            public void setOffer_status(String offer_status) {
                this.offer_status = offer_status;
            }

            public String getDeliverytime() {
                return deliverytime;
            }

            public void setDeliverytime(String deliverytime) {
                this.deliverytime = deliverytime;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }
        }


        public class ImageList {

            @SerializedName("image")
            @Expose
            private String image;
            @SerializedName("image_url")
            @Expose
            private String imageUrl;

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getImageUrl() {
                return imageUrl;
            }

            public void setImageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
            }

        }
    }
}
