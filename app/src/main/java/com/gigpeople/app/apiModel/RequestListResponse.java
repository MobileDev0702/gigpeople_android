package com.gigpeople.app.apiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RequestListResponse {

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("request_list")
    @Expose
    public List<RequestList> requestList = null;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<RequestList> getRequestList() {
        return requestList;
    }

    public class RequestList {

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

        public String getUserId() {
            return userId;
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


}
