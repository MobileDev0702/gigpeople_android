package com.gigpeople.app.apiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DashBoardResponse {


    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("banner_list")
    @Expose
    private List<BannerList> bannerList = null;
    @SerializedName("main_category_list")
    @Expose
    private List<MainCategoryList> mainCategoryList = null;

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

    public List<BannerList> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<BannerList> bannerList) {
        this.bannerList = bannerList;
    }

    public List<MainCategoryList> getMainCategoryList() {
        return mainCategoryList;
    }

    public void setMainCategoryList(List<MainCategoryList> mainCategoryList) {
        this.mainCategoryList = mainCategoryList;
    }

    public class BannerList {

        @SerializedName("banner_image")
        @Expose
        private String bannerImage;

        public String getBannerImage() {
            return bannerImage;
        }

        public void setBannerImage(String bannerImage) {
            this.bannerImage = bannerImage;
        }
    }

    public class MainCategoryList {

        @SerializedName("main_category_id")
        @Expose
        private String mainCategoryId;
        @SerializedName("main_category_name")
        @Expose
        private String mainCategoryName;
        @SerializedName("main_category_icon")
        @Expose
        private String mainCategoryIcon;
        @SerializedName("sub_category")
        @Expose
        private List<SubCategory> subCategory = null;




        private String status;
        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMainCategoryId() {
            return mainCategoryId;
        }

        public void setMainCategoryId(String mainCategoryId) {
            this.mainCategoryId = mainCategoryId;
        }

        public String getMainCategoryName() {
            return mainCategoryName;
        }

        public void setMainCategoryName(String mainCategoryName) {
            this.mainCategoryName = mainCategoryName;
        }

        public String getMainCategoryIcon() {
            return mainCategoryIcon;
        }

        public void setMainCategoryIcon(String mainCategoryIcon) {
            this.mainCategoryIcon = mainCategoryIcon;
        }

        public List<SubCategory> getSubCategory() {
            return subCategory;
        }

        public void setSubCategory(List<SubCategory> subCategory) {
            this.subCategory = subCategory;
        }

        public class SubCategory {

            @SerializedName("sub_category_id")
            @Expose
            private String subCategoryId;
            @SerializedName("sub_category_name")
            @Expose
            private String subCategoryName;
            @SerializedName("sub_category_icon")
            @Expose
            private String subCategoryIcon;




            private String status;
            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
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

        }
    }
}