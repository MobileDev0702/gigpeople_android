package com.gigpeople.app.apiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GigCategoryResultResponse {

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("categorygig_list")
    @Expose
    public List<CategorygigList> categorygigList = null;

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

    public List<CategorygigList> getCategorygigList() {
        return categorygigList;
    }

    public void setCategorygigList(List<CategorygigList> categorygigList) {
        this.categorygigList = categorygigList;
    }

    public class CategorygigList {

        @SerializedName("seller_details")
        @Expose
        public SellerDetails sellerDetails;
        @SerializedName("gig_id")
        @Expose
        public String gigId;
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
        @SerializedName("delivery_time")
        @Expose
        public String deliveryTime;
        @SerializedName("shipping")
        @Expose
        public String shipping;
        @SerializedName("shipping_price")
        @Expose
        public String shippingPrice;
        @SerializedName("total_cost")
        @Expose
        public String totalCost;
        @SerializedName("revisions")
        @Expose
        public String revisions;
        @SerializedName("gig_tag")
        @Expose
        public String gigTag;
        @SerializedName("description")
        @Expose
        public String description;
        @SerializedName("gig_status")
        @Expose
        public String gigStatus;
        @SerializedName("review_count")
        @Expose
        public String reviewCount;
        @SerializedName("gig_rating")
        @Expose
        public String gigRating;
        @SerializedName("is_favourite")
        @Expose
        public String isFavourite;

        public SellerDetails getSellerDetails() {
            return sellerDetails;
        }

        public void setSellerDetails(SellerDetails sellerDetails) {
            this.sellerDetails = sellerDetails;
        }

        public String getGigId() {
            return gigId;
        }

        public void setGigId(String gigId) {
            this.gigId = gigId;
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

        public String getDeliveryTime() {
            return deliveryTime;
        }

        public void setDeliveryTime(String deliveryTime) {
            this.deliveryTime = deliveryTime;
        }

        public String getShipping() {
            return shipping;
        }

        public void setShipping(String shipping) {
            this.shipping = shipping;
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

        public String getRevisions() {
            return revisions;
        }

        public void setRevisions(String revisions) {
            this.revisions = revisions;
        }

        public String getGigTag() {
            return gigTag;
        }

        public void setGigTag(String gigTag) {
            this.gigTag = gigTag;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getGigStatus() {
            return gigStatus;
        }

        public void setGigStatus(String gigStatus) {
            this.gigStatus = gigStatus;
        }

        public String getReviewCount() {
            return reviewCount;
        }

        public void setReviewCount(String reviewCount) {
            this.reviewCount = reviewCount;
        }

        public String getGigRating() {
            return gigRating;
        }

        public void setGigRating(String gigRating) {
            this.gigRating = gigRating;
        }

        public String getIsFavourite() {
            return isFavourite;
        }

        public void setIsFavourite(String isFavourite) {
            this.isFavourite = isFavourite;
        }

        public class ImageList {

            @SerializedName("file")
            @Expose
            public String file;
            @SerializedName("thumnail")
            @Expose
            public String thumnail;
            @SerializedName("type")
            @Expose
            public String type;

            public String getFile() {
                return file;
            }

            public void setFile(String file) {
                this.file = file;
            }

            public String getThumnail() {
                return thumnail;
            }

            public void setThumnail(String thumnail) {
                this.thumnail = thumnail;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
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
