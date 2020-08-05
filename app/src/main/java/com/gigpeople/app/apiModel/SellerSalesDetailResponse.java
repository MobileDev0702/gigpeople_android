package com.gigpeople.app.apiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SellerSalesDetailResponse {

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
    @SerializedName("time_Extensions")
    @Expose
    public TimeExtensions timeExtensions;
    @SerializedName("user_reviews")
    @Expose
    public UserReviews userReviews;
    @SerializedName("order_delivery")
    @Expose
    public List<OrderDelivery> orderDelivery = null;
    @SerializedName("orderDetails")
    @Expose
    public OrderDetails orderDetails;
    @SerializedName("buyerDetails")
    @Expose
    public BuyerDetails buyerDetails;

    public OrderDetails getOrderDetails() {
        return orderDetails;
    }

    public BuyerDetails getBuyerDetails() {
        return buyerDetails;
    }


    public class BuyerDetails {

        @SerializedName("buyer_id")
        @Expose
        public String buyerId;
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
        @SerializedName("last_visited")
        @Expose
        public String lastVisited;
        @SerializedName("join_date")
        @Expose
        public String joinDate;
        @SerializedName("orders_completed")
        @Expose
        public String ordersCompleted;

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

        public String getMobileCountry() {
            return mobileCountry;
        }

        public String getPhoneNo() {
            return phoneNo;
        }

        public String getIsEmailVerified() {
            return isEmailVerified;
        }

        public String getIsMobileVerified() {
            return isMobileVerified;
        }

        public String getAddress() {
            return address;
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

        public String getLastVisited() {
            return lastVisited;
        }

        public String getJoinDate() {
            return joinDate;
        }

        public String getOrdersCompleted() {
            return ordersCompleted;
        }

        public String getBuyerId() {
            return buyerId;
        }
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

    public List<OrderDelivery> getOrderDelivery() {
        return orderDelivery;
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

    public TimeExtensions getTimeExtensions() {
        return timeExtensions;
    }

    public UserReviews getUserReviews() {
        return userReviews;
    }

    public String getOtherRating() {
        return otherRating;
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


    public class MessageList {

        @SerializedName("msg_id")
        @Expose
        public String msgId;
        @SerializedName("order_id")
        @Expose
        public String orderId;
        @SerializedName("user_id")
        @Expose
        public String userId;
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

        public String getOrderId() {
            return orderId;
        }

        public String getUserId() {
            return userId;
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
        @SerializedName("user_id")
        @Expose
        public String userId;
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

        public String getUserId() {
            return userId;
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

        public String getStatus() {
            return status;
        }
    }

}
