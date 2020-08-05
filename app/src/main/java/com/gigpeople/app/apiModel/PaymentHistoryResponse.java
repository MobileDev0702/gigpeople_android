package com.gigpeople.app.apiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PaymentHistoryResponse {

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("amount_earning")
    @Expose
    public List<AmountEarning> amountEarning = null;
    @SerializedName("amount_spending")
    @Expose
    public List<AmountSpending> amountSpending = null;

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

    public List<AmountEarning> getAmountEarning() {
        return amountEarning;
    }

    public void setAmountEarning(List<AmountEarning> amountEarning) {
        this.amountEarning = amountEarning;
    }

    public List<AmountSpending> getAmountSpending() {
        return amountSpending;
    }

    public void setAmountSpending(List<AmountSpending> amountSpending) {
        this.amountSpending = amountSpending;
    }

    public class AmountEarning {

        @SerializedName("seller_id")
        @Expose
        public String sellerId;
        @SerializedName("buyer_details")
        @Expose
        public BuyerDetails buyerDetails;
        @SerializedName("title")
        @Expose
        public String title;
        @SerializedName("order_id")
        @Expose
        public String orderId;
        @SerializedName("order_date")
        @Expose
        public String orderDate;
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
        @SerializedName("shipping_price")
        @Expose
        public String shippingPrice;
        @SerializedName("total_cost")
        @Expose
        public String totalCost;
        @SerializedName("requirement")
        @Expose
        private String requirement;
        public String getRequirement() {
            return requirement;
        }

        public String getSellerId() {
            return sellerId;
        }

        public void setSellerId(String sellerId) {
            this.sellerId = sellerId;
        }

        public BuyerDetails getBuyerDetails() {
            return buyerDetails;
        }

        public void setBuyerDetails(BuyerDetails buyerDetails) {
            this.buyerDetails = buyerDetails;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getOrderDate() {
            return orderDate;
        }

        public void setOrderDate(String orderDate) {
            this.orderDate = orderDate;
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

        public String getShippingPrice() {
            return shippingPrice;
        }

        public void setShippingPrice(String shippingPrice) {
            this.shippingPrice = shippingPrice;
        }

        public String getTotalCost() {
            return totalCost;
        }

        public void setTotalCost(String totalCost) {
            this.totalCost = totalCost;
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
            @SerializedName("join_date")
            @Expose
            public String joinDate;

            public String getBuyerId() {
                return buyerId;
            }

            public void setBuyerId(String buyerId) {
                this.buyerId = buyerId;
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

    public class AmountSpending {

        @SerializedName("buyer_id")
        @Expose
        public String buyerId;
        @SerializedName("seller_details")
        @Expose
        public SellerDetails sellerDetails;
        @SerializedName("order_id")
        @Expose
        public String orderId;
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
        @SerializedName("shipping_price")
        @Expose
        public String shippingPrice;
        @SerializedName("total_cost")
        @Expose
        public String totalCost;
        @SerializedName("requirement")
        @Expose
        private String requirement;
        public String getRequirement() {
            return requirement;
        }

        public String getBuyerId() {
            return buyerId;
        }

        public void setBuyerId(String buyerId) {
            this.buyerId = buyerId;
        }

        public SellerDetails getSellerDetails() {
            return sellerDetails;
        }

        public void setSellerDetails(SellerDetails sellerDetails) {
            this.sellerDetails = sellerDetails;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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

        public String getShippingPrice() {
            return shippingPrice;
        }

        public void setShippingPrice(String shippingPrice) {
            this.shippingPrice = shippingPrice;
        }

        public String getTotalCost() {
            return totalCost;
        }

        public void setTotalCost(String totalCost) {
            this.totalCost = totalCost;
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

        public class SellerDetails {

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

    }

}
