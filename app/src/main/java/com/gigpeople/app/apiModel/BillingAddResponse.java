package com.gigpeople.app.apiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BillingAddResponse {

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("billing_details")
    @Expose
    public BillingDetails billingDetails;

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

    public BillingDetails getBillingDetails() {
        return billingDetails;
    }

    public void setBillingDetails(BillingDetails billingDetails) {
        this.billingDetails = billingDetails;
    }

    public class BillingDetails {

        @SerializedName("user_id")
        @Expose
        public String userId;
        @SerializedName("billing_id")
        @Expose
        public String billingId;
        @SerializedName("company_name")
        @Expose
        public String companyName;
        @SerializedName("full_name")
        @Expose
        public String fullName;
        @SerializedName("address")
        @Expose
        public String address;
        @SerializedName("lattitude")
        @Expose
        public String lattitude;
        @SerializedName("longitude")
        @Expose
        public String longitude;
        @SerializedName("country")
        @Expose
        public String country;
        @SerializedName("zipcode")
        @Expose
        public String zipcode;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getBillingId() {
            return billingId;
        }

        public void setBillingId(String billingId) {
            this.billingId = billingId;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLattitude() {
            return lattitude;
        }

        public void setLattitude(String lattitude) {
            this.lattitude = lattitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getZipcode() {
            return zipcode;
        }

        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
        }
    }
}
