package com.gigpeople.app.apiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AnalyticsReportResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("analytics_Report")
    @Expose
    private AnalyticsReport analyticsReport;

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

    public AnalyticsReport getAnalyticsReport() {
        return analyticsReport;
    }

    public void setAnalyticsReport(AnalyticsReport analyticsReport) {
        this.analyticsReport = analyticsReport;
    }
    public class AnalyticsReport {

        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("month_earned")
        @Expose
        private String monthEarned;
        @SerializedName("created_orders")
        @Expose
        private String createdOrders;
        @SerializedName("avg_selling_price")
        @Expose
        private String avgSellingPrice;
        @SerializedName("positive_rating")
        @Expose
        private String positiveRating;
        @SerializedName("new_orders")
        @Expose
        private String newOrders;
        @SerializedName("multiple_orders")
        @Expose
        private String multipleOrders;
        @SerializedName("extra_orders")
        @Expose
        private String extraOrders;
        @SerializedName("delivered_orders")
        @Expose
        private String deliveredOrders;
        @SerializedName("positive_reviews")
        @Expose
        private String positiveReviews;
        @SerializedName("negative_reviews")
        @Expose
        private String negativeReviews;
        @SerializedName("rated")
        @Expose
        private String rated;
        @SerializedName("completed_orders")
        @Expose
        private String completedOrders;
        @SerializedName("cancelled_orders")
        @Expose
        private String cancelledOrders;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getMonthEarned() {
            return monthEarned;
        }

        public void setMonthEarned(String monthEarned) {
            this.monthEarned = monthEarned;
        }

        public String getCreatedOrders() {
            return createdOrders;
        }

        public void setCreatedOrders(String createdOrders) {
            this.createdOrders = createdOrders;
        }

        public String getAvgSellingPrice() {
            return avgSellingPrice;
        }

        public void setAvgSellingPrice(String avgSellingPrice) {
            this.avgSellingPrice = avgSellingPrice;
        }

        public String getPositiveRating() {
            return positiveRating;
        }

        public void setPositiveRating(String positiveRating) {
            this.positiveRating = positiveRating;
        }

        public String getNewOrders() {
            return newOrders;
        }

        public void setNewOrders(String newOrders) {
            this.newOrders = newOrders;
        }

        public String getMultipleOrders() {
            return multipleOrders;
        }

        public void setMultipleOrders(String multipleOrders) {
            this.multipleOrders = multipleOrders;
        }

        public String getExtraOrders() {
            return extraOrders;
        }

        public void setExtraOrders(String extraOrders) {
            this.extraOrders = extraOrders;
        }

        public String getDeliveredOrders() {
            return deliveredOrders;
        }

        public void setDeliveredOrders(String deliveredOrders) {
            this.deliveredOrders = deliveredOrders;
        }

        public String getPositiveReviews() {
            return positiveReviews;
        }

        public void setPositiveReviews(String positiveReviews) {
            this.positiveReviews = positiveReviews;
        }

        public String getNegativeReviews() {
            return negativeReviews;
        }

        public void setNegativeReviews(String negativeReviews) {
            this.negativeReviews = negativeReviews;
        }

        public String getRated() {
            return rated;
        }

        public void setRated(String rated) {
            this.rated = rated;
        }

        public String getCompletedOrders() {
            return completedOrders;
        }

        public void setCompletedOrders(String completedOrders) {
            this.completedOrders = completedOrders;
        }

        public String getCancelledOrders() {
            return cancelledOrders;
        }

        public void setCancelledOrders(String cancelledOrders) {
            this.cancelledOrders = cancelledOrders;
        }

    }
}
