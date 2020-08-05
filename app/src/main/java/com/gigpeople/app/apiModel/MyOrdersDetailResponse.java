package com.gigpeople.app.apiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyOrdersDetailResponse {

    /* "order_status": "6",
             "admin_report": "0",
             "user_rating": "3",
             "order_rating": "3",
             "order_started": "6",
             "order_deliver": "6",
             "revision_count": "6",*/

    @SerializedName("sellerDetails")
    @Expose
    public SellerDetails sellerDetails;

    @SerializedName("other_rating")
    @Expose
    public String otherRating;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("order_status")
    @Expose
    public String orderStatus;
    @SerializedName("admin_report")
    @Expose
    public String adminReport;
    @SerializedName("user_rating")
    @Expose
    public String userRating;
    @SerializedName("order_rating")
    @Expose
    public String orderRating;
    @SerializedName("order_started")
    @Expose
    public String orderStarted;
    @SerializedName("order_deliver")
    @Expose
    public String orderDeliver;
    @SerializedName("revision_count")
    @Expose
    public String revisionCount;
    @SerializedName("gig_revision")
    @Expose
    public String gigRevision;
    @SerializedName("message_list")
    @Expose
    public List<MessageList> messageList = null;
    @SerializedName("revision_order")
    @Expose
    public List<RevisionOrder> revisionOrder = null;
    @SerializedName("dispute_order")
    @Expose
    public List<DisputeOrder> disputeOrder = null;
    @SerializedName("cancelled_order")
    @Expose
    public CancelledOrder cancelledOrder;
    @SerializedName("order_reviews")
    @Expose
    public OrderReviews orderReviews;
    @SerializedName("user_reviews")
    @Expose
    public UserReviews userReviews;
    @SerializedName("time_Extensions")
    @Expose
    public TimeExtensions timeExtensions;
    @SerializedName("order_delivery")
    @Expose
    public List<OrderDelivery> orderDelivery = null;
    @SerializedName("orderDetails")
    @Expose
    public OrderDetails orderDetails;

    public List<OrderDelivery> getOrderDelivery() {
        return orderDelivery;
    }

    public SellerDetails getSellerDetails() {
        return sellerDetails;
    }

    public OrderDetails getOrderDetails() {
        return orderDetails;
    }

    public class OrderDetails {

        @SerializedName("order_id")
        @Expose
        public String orderId;
        @SerializedName("gig_id")
        @Expose
        public String gig_id;
        @SerializedName("order_date")
        @Expose
        public String orderDate;
        @SerializedName("title")
        @Expose
        public String title;
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
        @SerializedName("requirement")
        @Expose
        public String requirement;
        @SerializedName("shipping_price")
        @Expose
        public String shippingPrice;
        @SerializedName("total_cost")
        @Expose
        public String totalCost;
        @SerializedName("time_extension")
        @Expose
        public String timeExtension;
        @SerializedName("time_description")
        @Expose
        public String timeDescription;
        @SerializedName("time_status")
        @Expose
        public String timeStatus;

        public String getOrderId() {
            return orderId;
        }

        public String getOrderDate() {
            return orderDate;
        }

        public String getTitle() {
            return title;
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

        public List<ImageList> getImageList() {
            return imageList;
        }

        public String getPrice() {
            return price;
        }

        public String getQuantity() {
            return quantity;
        }

        public String getDeliverytime() {
            return deliverytime;
        }

        public String getDescription() {
            return description;
        }

        public String getRequirement() {
            return requirement;
        }

        public String getShippingPrice() {
            return shippingPrice;
        }

        public String getTotalCost() {
            return totalCost;
        }

        public String getTimeExtension() {
            return timeExtension;
        }

        public String getTimeDescription() {
            return timeDescription;
        }

        public String getTimeStatus() {
            return timeStatus;
        }

        public String getGig_id() {
            return gig_id;
        }

        public void setGig_id(String gig_id) {
            this.gig_id = gig_id;
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

            public String getImageUrl() {
                return imageUrl;
            }
        }

    }

    public class OrderDelivery {

        @SerializedName("user_id")
        @Expose
        public String userId;
        @SerializedName("order_id")
        @Expose
        public String orderId;
        @SerializedName("type")
        @Expose
        public String type;
        @SerializedName("thumb")
        @Expose
        public String thumb;
        @SerializedName("thumb_url")
        @Expose
        public String thumbUrl;
        @SerializedName("project")
        @Expose
        public String project;
        @SerializedName("project_url")
        @Expose
        public String projectUrl;
        @SerializedName("description")
        @Expose
        public String description;
        @SerializedName("is_date")
        @Expose
        public String isDate;

        public String getUserId() {
            return userId;
        }

        public String getOrderId() {
            return orderId;
        }

        public String getType() {
            return type;
        }

        public String getThumb() {
            return thumb;
        }

        public String getThumbUrl() {
            return thumbUrl;
        }

        public String getProject() {
            return project;
        }

        public String getProjectUrl() {
            return projectUrl;
        }

        public String getDescription() {
            return description;
        }

        public String getIsDate() {
            return isDate;
        }
    }

    public class SellerDetails {

         /*"sellerDetails": {
            "user_id": "6",
                    "first_name": "Seller",
                    "last_name": "Sell",
                    "email": "seller@gmail.com",
                    "email_otp": "",
                    "is_email_verified": "0",
                    "profile": "",
                    "profile_image_url": "",
                    "mobile_country": "91",
                    "phone_no": "9677382536",
                    "mobile_otp": "",
                    "is_mobile_verified": "0",
                    "address": "Airport Rd, Civil Aerodrome Post, Peelamedu, Tamil Nadu 641014, India",
                    "lattitude": "11.0304324",
                    "longitude": "77.03909279999999",
                    "about": "your time and consideration and look forward to hearing from you soon and I will be in PDF format",
                    "skills": "Desktop applications ",
                    "language": "English,Tamil",
                    "wallet": "0.00",
                    "rating": "4",
                    "no_of_reviews": "2",
                    "no_of_gigpost": "3",
                    "join_date": "2020-02-21 22:10:47",
                    "notification": "1",
                    "customer_order": "0",
                    "account_status": "0"
        }*/

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


    public TimeExtensions getTimeExtensions() {
        return timeExtensions;
    }

    public UserReviews getUserReviews() {
        return userReviews;
    }

    public String getOtherRating() {
        return otherRating;
    }

    public String getGigRevision() {
        return gigRevision;
    }

    public class TimeExtensions {

        @SerializedName("order_id")
        @Expose
        public String orderId;
        @SerializedName("time_extension")
        @Expose
        public String timeExtension;
        @SerializedName("time_description")
        @Expose
        public String timeDescription;
        @SerializedName("time_status")
        @Expose
        public String timeStatus;
        @SerializedName("is_date")
        @Expose
        public String isDate;

        public String getOrderId() {
            return orderId;
        }

        public String getTimeExtension() {
            return timeExtension;
        }

        public String getTimeDescription() {
            return timeDescription;
        }

        public String getTimeStatus() {
            return timeStatus;
        }

        public String getIsDate() {
            return isDate;
        }
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<MessageList> getMessageList() {
        return messageList;
    }

    public List<RevisionOrder> getRevisionOrder() {
        return revisionOrder;
    }

    public List<DisputeOrder> getDisputeOrder() {
        return disputeOrder;
    }

    public CancelledOrder getCancelledOrder() {
        return cancelledOrder;
    }

    public OrderReviews getOrderReviews() {
        return orderReviews;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public String getAdminReport() {
        return adminReport;
    }

    public String getUserRating() {
        return userRating;
    }

    public String getOrderRating() {
        return orderRating;
    }

    public String getOrderStarted() {
        return orderStarted;
    }

    public String getOrderDeliver() {
        return orderDeliver;
    }

    public String getRevisionCount() {
        return revisionCount;
    }

    public class MessageList {

        @SerializedName("msg_id")
        @Expose
        public String msgId;
        @SerializedName("user_id")
        @Expose
        public String UserId;
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
        @SerializedName("message")
        @Expose
        public String message;
        @SerializedName("is_date")
        @Expose
        public String isDate;

        public String getMsgId() {
            return msgId;
        }

        public String getUserId() {
            return UserId;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getProfile() {
            return profile;
        }

        public String getProfileImageUrl() {
            return profileImageUrl;
        }

        public String getMessage() {
            return message;
        }

        public String getIsDate() {
            return isDate;
        }
    }

    public class RevisionOrder {

        @SerializedName("msg_id")
        @Expose
        public String msgId;
        @SerializedName("order_id")
        @Expose
        public String orderId;
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
        @SerializedName("message")
        @Expose
        public String message;
        @SerializedName("is_date")
        @Expose
        public String isDate;
        @SerializedName("status")
        @Expose
        public String status;

        public String getMsgId() {
            return msgId;
        }

        public String getOrderId() {
            return orderId;
        }

        public String getMessage() {
            return message;
        }

        public String getIsDate() {
            return isDate;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getProfile() {
            return profile;
        }

        public String getProfileImageUrl() {
            return profileImageUrl;
        }

        public String getStatus() {
            return status;
        }
    }

    public class DisputeOrder {

        @SerializedName("order_id")
        @Expose
        public String orderId;
        @SerializedName("message")
        @Expose
        public String message;
        @SerializedName("is_date")
        @Expose
        public String isDate;

        public String getOrderId() {
            return orderId;
        }

        public String getMessage() {
            return message;
        }

        public String getIsDate() {
            return isDate;
        }
    }

    public class CancelledOrder {

        @SerializedName("order_id")
        @Expose
        public String orderId;
        @SerializedName("cancel_reason")
        @Expose
        public String cancelReason;
        @SerializedName("cancelled_date")
        @Expose
        public String cancelledDate;
        @SerializedName("cancel_status")
        @Expose
        public String cancelStatus;
        @SerializedName("cancel_by")
        @Expose
        public String cancelBy;

        public String getOrderId() {
            return orderId;
        }

        public String getCancelReason() {
            return cancelReason;
        }

        public String getCancelledDate() {
            return cancelledDate;
        }

        public String getCancelStatus() {
            return cancelStatus;
        }

        public String getCancelBy() {
            return cancelBy;
        }
    }

    public class OrderReviews {

        @SerializedName("order_id")
        @Expose
        public String orderId;
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
        @SerializedName("rating")
        @Expose
        public String rating;
        @SerializedName("review")
        @Expose
        public String review;
        @SerializedName("is_date")
        @Expose
        public String isDate;

        public String getOrderId() {
            return orderId;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getProfile() {
            return profile;
        }

        public String getProfileImageUrl() {
            return profileImageUrl;
        }

        public String getRating() {
            return rating;
        }

        public String getReview() {
            return review;
        }

        public String getIsDate() {
            return isDate;
        }
    }

    public class UserReviews {

        @SerializedName("order_id")
        @Expose
        public String orderId;
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
        @SerializedName("rating")
        @Expose
        public String rating;
        @SerializedName("review")
        @Expose
        public String review;
        @SerializedName("is_date")
        @Expose
        public String isDate;

        public String getOrderId() {
            return orderId;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getProfile() {
            return profile;
        }

        public String getProfileImageUrl() {
            return profileImageUrl;
        }

        public String getRating() {
            return rating;
        }

        public String getReview() {
            return review;
        }

        public String getIsDate() {
            return isDate;
        }
    }

}
