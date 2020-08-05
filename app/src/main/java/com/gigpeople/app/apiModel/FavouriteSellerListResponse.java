package com.gigpeople.app.apiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FavouriteSellerListResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("favouriteseller_list")
    @Expose
    private List<FavouritesellerList> favouritesellerList = null;

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

    public List<FavouritesellerList> getFavouritesellerList() {
        return favouritesellerList;
    }

    public void setFavouritesellerList(List<FavouritesellerList> favouritesellerList) {
        this.favouritesellerList = favouritesellerList;
    }

    public class FavouritesellerList {

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
        @SerializedName("gig_details")
        @Expose
        private List<GigDetail> gigDetails = null;

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

        public List<GigDetail> getGigDetails() {
            return gigDetails;
        }

        public void setGigDetails(List<GigDetail> gigDetails) {
            this.gigDetails = gigDetails;
        }


        public class GigDetail {

            @SerializedName("gig_id")
            @Expose
            private String gigId;
            @SerializedName("title")
            @Expose
            private String title;
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
            @SerializedName("delivery_time")
            @Expose
            private String deliveryTime;
            @SerializedName("shipping")
            @Expose
            private String shipping;
            @SerializedName("shipping_price")
            @Expose
            private String shippingPrice;
            @SerializedName("total_cost")
            @Expose
            private String totalCost;
            @SerializedName("revisions")
            @Expose
            private String revisions;
            @SerializedName("gig_tag")
            @Expose
            private String gigTag;
            @SerializedName("description")
            @Expose
            private String description;
            @SerializedName("gig_status")
            @Expose
            private String gigStatus;
            @SerializedName("review_count")
            @Expose
            private String reviewCount;
            @SerializedName("gig_rating")
            @Expose
            private String gigRating;
            @SerializedName("is_favourite")
            @Expose
            private String isFavourite;

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
                private String file;
                @SerializedName("thumnail")
                @Expose
                private String thumnail;
                @SerializedName("type")
                @Expose
                private String type;

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
    }
}