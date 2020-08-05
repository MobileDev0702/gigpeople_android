package com.gigpeople.app.apiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyOrdersListResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("active_count")
    @Expose
    private String activeCount;
    @SerializedName("delivered_count")
    @Expose
    private String deliveredCount;
    @SerializedName("cancelled_count")
    @Expose
    private String cancelledCount;
    @SerializedName("active_order_list")
    @Expose
    private List<ActiveOrderList> activeOrderList = null;
    @SerializedName("delivered_order_list")
    @Expose
    private List<DeliveredOrderList> deliveredOrderList = null;
    @SerializedName("cancelled_order_list")
    @Expose
    private List<CancelledOrderList> cancelledOrderList = null;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getActiveCount() {
        return activeCount;
    }

    public String getDeliveredCount() {
        return deliveredCount;
    }

    public String getCancelledCount() {
        return cancelledCount;
    }

    public List<ActiveOrderList> getActiveOrderList() {
        return activeOrderList;
    }

    public List<DeliveredOrderList> getDeliveredOrderList() {
        return deliveredOrderList;
    }

    public List<CancelledOrderList> getCancelledOrderList() {
        return cancelledOrderList;
    }

    public class ActiveOrderList {

        @SerializedName("order_date")
        @Expose
        private String orderDate;
        @SerializedName("seller_id")
        @Expose
        private String sellerId;
        @SerializedName("seller_details")
        @Expose
        private SellerDetails sellerDetails;
        @SerializedName("order_id")
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
        @SerializedName("total_cost")
        @Expose
        private String totalCost;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("shipping_price")
        @Expose
        private String shippingPrice;
        @SerializedName("requirement")
        @Expose
        private String requirement;
        public String getRequirement() {
            return requirement;
        }
        public String getShippingPrice() {
            return shippingPrice;
        }
        public String getTitle() {
            return title;
        }

        public String getTotalCost() {
            return totalCost;
        }


        public String getSellerId() {
            return sellerId;
        }

        public SellerDetails getSellerDetails() {
            return sellerDetails;
        }

        public String getRequestId() {
            return requestId;
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

        public String getOrderDate() {
            return orderDate;
        }


        public class SellerDetails {

            @SerializedName("seller_id")
            @Expose
            private String sellerId;
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
            @SerializedName("mobile_country")
            @Expose
            private String mobileCountry;
            @SerializedName("phone_no")
            @Expose
            private String phoneNo;
            @SerializedName("is_email_verified")
            @Expose
            private String isEmailVerified;
            @SerializedName("is_mobile_verified")
            @Expose
            private String isMobileVerified;
            @SerializedName("address")
            @Expose
            private String address;
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
            @SerializedName("last_visited")
            @Expose
            private String lastVisited;
            public String getLastVisited() {
                return lastVisited;
            }

            public String getSellerId() {
                return sellerId;
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

            public String getJoinDate() {
                return joinDate;
            }

            public String getOrdersCompleted() {
                return ordersCompleted;
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

            public String getImageUrl() {
                return imageUrl;
            }
        }

        }

    public class DeliveredOrderList {

        @SerializedName("order_date")
        @Expose
        private String orderDate;
        @SerializedName("seller_id")
        @Expose
        private String sellerId;
        @SerializedName("seller_details")
        @Expose
        private SellerDetails sellerDetails;
        @SerializedName("order_id")
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
        @SerializedName("total_cost")
        @Expose
        private String totalCost;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("shipping_price")
        @Expose
        private String shippingPrice;
        @SerializedName("requirement")
        @Expose
        private String requirement;
        public String getRequirement() {
            return requirement;
        }
        public String getShippingPrice() {
            return shippingPrice;
        }
        public String getTitle() {
            return title;
        }
        public String getTotalCost() {
            return totalCost;
        }

        public String getSellerId() {
            return sellerId;
        }

        public SellerDetails getSellerDetails() {
            return sellerDetails;
        }

        public String getRequestId() {
            return requestId;
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

        public String getOrderDate() {
            return orderDate;
        }


        public class SellerDetails {

            @SerializedName("seller_id")
            @Expose
            private String sellerId;
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
            @SerializedName("mobile_country")
            @Expose
            private String mobileCountry;
            @SerializedName("phone_no")
            @Expose
            private String phoneNo;
            @SerializedName("is_email_verified")
            @Expose
            private String isEmailVerified;
            @SerializedName("is_mobile_verified")
            @Expose
            private String isMobileVerified;
            @SerializedName("address")
            @Expose
            private String address;
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
            @SerializedName("last_visited")
            @Expose
            private String lastVisited;
            public String getLastVisited() {
                return lastVisited;
            }

            public String getSellerId() {
                return sellerId;
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

            public String getJoinDate() {
                return joinDate;
            }

            public String getOrdersCompleted() {
                return ordersCompleted;
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

            public String getImageUrl() {
                return imageUrl;
            }
        }

    }

    public class CancelledOrderList {

        @SerializedName("order_date")
        @Expose
        private String orderDate;
        @SerializedName("seller_id")
        @Expose
        private String sellerId;
        @SerializedName("seller_details")
        @Expose
        private SellerDetails sellerDetails;
        @SerializedName("order_id")
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
        @SerializedName("total_cost")
        @Expose
        private String totalCost;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("shipping_price")
        @Expose
        private String shippingPrice;
        @SerializedName("requirement")
        @Expose
        private String requirement;
        public String getRequirement() {
            return requirement;
        }
        public String getShippingPrice() {
            return shippingPrice;
        }
        public String getTitle() {
            return title;
        }
        public String getTotalCost() {
            return totalCost;
        }

        public String getSellerId() {
            return sellerId;
        }

        public SellerDetails getSellerDetails() {
            return sellerDetails;
        }

        public String getRequestId() {
            return requestId;
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

        public String getOrderDate() {
            return orderDate;
        }


        public class SellerDetails {

            @SerializedName("seller_id")
            @Expose
            private String sellerId;
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
            @SerializedName("mobile_country")
            @Expose
            private String mobileCountry;
            @SerializedName("phone_no")
            @Expose
            private String phoneNo;
            @SerializedName("is_email_verified")
            @Expose
            private String isEmailVerified;
            @SerializedName("is_mobile_verified")
            @Expose
            private String isMobileVerified;
            @SerializedName("address")
            @Expose
            private String address;
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
            @SerializedName("last_visited")
            @Expose
            private String lastVisited;
            public String getLastVisited() {
                return lastVisited;
            }

            public String getSellerId() {
                return sellerId;
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

            public String getJoinDate() {
                return joinDate;
            }

            public String getOrdersCompleted() {
                return ordersCompleted;
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

            public String getImageUrl() {
                return imageUrl;
            }
        }

    }

}
