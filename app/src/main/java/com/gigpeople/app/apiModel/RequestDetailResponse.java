package com.gigpeople.app.apiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RequestDetailResponse {

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("request_Details")
    @Expose
    public RequestDetails requestDetails;
    @SerializedName("offer_Details")
    @Expose
    public List<OfferDetail> offerDetails = null;
    @SerializedName("accept_Details")
    @Expose
    public AcceptDetails acceptDetails;

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

    public RequestDetails getRequestDetails() {
        return requestDetails;
    }

    public void setRequestDetails(RequestDetails requestDetails) {
        this.requestDetails = requestDetails;
    }

    public List<OfferDetail> getOfferDetails() {
        return offerDetails;
    }

    public void setOfferDetails(List<OfferDetail> offerDetails) {
        this.offerDetails = offerDetails;
    }

    public AcceptDetails getAcceptDetails() {
        return acceptDetails;
    }

    public class RequestDetails {

        @SerializedName("user_id")
        @Expose
        public String userId;
        @SerializedName("request_id")
        @Expose
        public String requestId;
        @SerializedName("category_id")
        @Expose
        public String categoryId;
        @SerializedName("category_name")
        @Expose
        public String categoryName;
        @SerializedName("category_icon")
        @Expose
        public String categoryIcon;
        @SerializedName("sub_category_id")
        @Expose
        public String subCategoryId;
        @SerializedName("sub_category_name")
        @Expose
        public String subCategoryName;
        @SerializedName("sub_category_icon")
        @Expose
        public String subCategoryIcon;
        @SerializedName("image_list")
        @Expose
        public List<ImageList> imageList = null;
        @SerializedName("price")
        @Expose
        public String price;
        @SerializedName("quantity")
        @Expose
        public String quantity;
        @SerializedName("deliverytime")
        @Expose
        public String deliverytime;
        @SerializedName("description")
        @Expose
        public String description;
        @SerializedName("request_status")
        @Expose
        public String request_status;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
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

        public String getRequest_status() {
            return request_status;
        }

        public class ImageList {

            @SerializedName("image")
            @Expose
            public String image;
            @SerializedName("image_url")
            @Expose
            public String imageUrl;

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

    public class OfferDetail {

        @SerializedName("skills")
        @Expose
        public String skills;
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
        @SerializedName("profile")
        @Expose
        public String profile;
        @SerializedName("profile_image_url")
        @Expose
        public String profileImageUrl;
        @SerializedName("phone_no")
        @Expose
        public String phoneNo;
        @SerializedName("address")
        @Expose
        public String address;
        @SerializedName("country")
        @Expose
        public String country;
        @SerializedName("about")
        @Expose
        public String about;
        @SerializedName("language")
        @Expose
        public String language;
        @SerializedName("rating")
        @Expose
        public String rating;
        @SerializedName("live_status")
        @Expose
        public String liveStatus;
        @SerializedName("join_date")
        @Expose
        public String joinDate;
        @SerializedName("orders_completed")
        @Expose
        public String ordersCompleted;
        @SerializedName("category_id")
        @Expose
        public String categoryId;
        @SerializedName("category_name")
        @Expose
        public String categoryName;
        @SerializedName("category_icon")
        @Expose
        public String categoryIcon;
        @SerializedName("sub_category_id")
        @Expose
        public String subCategoryId;
        @SerializedName("sub_category_name")
        @Expose
        public String subCategoryName;
        @SerializedName("sub_category_icon")
        @Expose
        public String subCategoryIcon;
        @SerializedName("price")
        @Expose
        public String price;
        @SerializedName("deliverytime")
        @Expose
        public String deliverytime;
        @SerializedName("description")
        @Expose
        public String description;

        @SerializedName("seller_id")
        @Expose
        public String seller_id;
        @SerializedName("offer_id")
        @Expose
        public String offer_id;
        @SerializedName("offer_status")
        @Expose
        public String offerStatus;


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

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
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

        public String getSeller_id() {
            return seller_id;
        }

        public void setSeller_id(String seller_id) {
            this.seller_id = seller_id;
        }

        public String getOffer_id() {
            return offer_id;
        }

        public void setOffer_id(String offer_id) {
            this.offer_id = offer_id;
        }

        public String getSkills() {
            return skills;
        }

        public String getOfferStatus() {
            return offerStatus;
        }
    }

    public class AcceptDetails {

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
        @SerializedName("phone_no")
        @Expose
        public String phoneNo;
        @SerializedName("address")
        @Expose
        public String address;
        @SerializedName("country")
        @Expose
        public String country;
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
        @SerializedName("join_date")
        @Expose
        public String joinDate;
        @SerializedName("orders_completed")
        @Expose
        public String ordersCompleted;
        @SerializedName("seller_id")
        @Expose
        public String sellerId;
        @SerializedName("offer_id")
        @Expose
        public String offerId;
        @SerializedName("category_id")
        @Expose
        public String categoryId;
        @SerializedName("category_name")
        @Expose
        public String categoryName;
        @SerializedName("category_icon")
        @Expose
        public String categoryIcon;
        @SerializedName("sub_category_id")
        @Expose
        public String subCategoryId;
        @SerializedName("sub_category_name")
        @Expose
        public String subCategoryName;
        @SerializedName("sub_category_icon")
        @Expose
        public String subCategoryIcon;
        @SerializedName("price")
        @Expose
        public String price;
        @SerializedName("deliverytime")
        @Expose
        public String deliverytime;
        @SerializedName("description")
        @Expose
        public String description;

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getEmail() {
            return email;
        }

        public String getProfile() {
            return profile;
        }

        public String getProfileImageUrl() {
            return profileImageUrl;
        }

        public String getPhoneNo() {
            return phoneNo;
        }

        public String getAddress() {
            return address;
        }

        public String getCountry() {
            return country;
        }

        public String getAbout() {
            return about;
        }

        public String getSkills() {
            return skills;
        }

        public String getLanguage() {
            return language;
        }

        public String getRating() {
            return rating;
        }

        public String getLiveStatus() {
            return liveStatus;
        }

        public String getJoinDate() {
            return joinDate;
        }

        public String getOrdersCompleted() {
            return ordersCompleted;
        }

        public String getSellerId() {
            return sellerId;
        }

        public String getOfferId() {
            return offerId;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public String getCategoryIcon() {
            return categoryIcon;
        }

        public String getSubCategoryId() {
            return subCategoryId;
        }

        public String getSubCategoryName() {
            return subCategoryName;
        }

        public String getSubCategoryIcon() {
            return subCategoryIcon;
        }

        public String getPrice() {
            return price;
        }

        public String getDeliverytime() {
            return deliverytime;
        }

        public String getDescription() {
            return description;
        }
    }
}
