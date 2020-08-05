package com.gigpeople.app.apiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GiGDetailsResponse {

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("gig_Details")
    @Expose
    public GigDetails gigDetails;

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

    public GigDetails getGigDetails() {
        return gigDetails;
    }

    public void setGigDetails(GigDetails gigDetails) {
        this.gigDetails = gigDetails;
    }

    @SerializedName("gig_reviews")
    @Expose
    private List<GigReview> gigReviews = null;

    public List<GigReview> getGigReviews() {
        return gigReviews;
    }

    public void setGigReviews(List<GigReview> gigReviews) {
        this.gigReviews = gigReviews;
    }


    public class GigDetails {

        @SerializedName("user_id")
        @Expose
        public String userId;
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



        @SerializedName("gig_reviews")
        @Expose
        private List<GigReview> gigReviews = null;


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
        @SerializedName("total_cost")
        @Expose
        public String totalCost;
        @SerializedName("gig_rating")
        @Expose
        public String gigRating;
        @SerializedName("review_count")
        @Expose
        public String reviewCount;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
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

        public String getTotalCost() {
            return totalCost;
        }

        public List<GigReview> getGigReviews() {
            return gigReviews;
        }

        public void setGigReviews(List<GigReview> gigReviews) {
            this.gigReviews = gigReviews;
        }

        public String getGigRating() {
            return gigRating;
        }

        public String getReviewCount() {
            return reviewCount;
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



    }


    public class GigReview {

        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("profile")
        @Expose
        private String profile;
        @SerializedName("profile_image_url")
        @Expose
        private String profileImageUrl;
        @SerializedName("review")
        @Expose
        private String review;
        @SerializedName("rating")
        @Expose
        private String rating;
        @SerializedName("is_date")
        @Expose
        private String isDate;

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

}

