package com.gigpeople.app.apiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartItemResponse {

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("grand_total")
    @Expose
    public String grandTotal;
    @SerializedName("cart_item")
    @Expose
    public List<CartItem> cartItem = null;

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

    public String getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(String grandTotal) {
        this.grandTotal = grandTotal;
    }

    public List<CartItem> getCartItem() {
        return cartItem;
    }

    public void setCartItem(List<CartItem> cartItem) {
        this.cartItem = cartItem;
    }

    public class CartItem {

        @SerializedName("user_id")
        @Expose
        public String userId;
        @SerializedName("seller_id")
        @Expose
        public String sellerId;
        @SerializedName("gig_id")
        @Expose
        public String gigId;
        @SerializedName("title")
        @Expose
        public String title;
        @SerializedName("image_list")
        @Expose
        public List<ImageList> imageList = null;
        @SerializedName("gig_description")
        @Expose
        public String gigDescription;
        @SerializedName("card_description")
        @Expose
        public String cardDescription;
        @SerializedName("price")
        @Expose
        public String price;
        @SerializedName("quantity")
        @Expose
        public String quantity;
        @SerializedName("total_cost")
        @Expose
        public String totalCost;
        @SerializedName("shipping_price")
        @Expose
        public String shippingPrice;
        @SerializedName("final_cost")
        @Expose
        public String finalCost;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getSellerId() {
            return sellerId;
        }

        public void setSellerId(String sellerId) {
            this.sellerId = sellerId;
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

        public List<ImageList> getImageList() {
            return imageList;
        }

        public void setImageList(List<ImageList> imageList) {
            this.imageList = imageList;
        }

        public String getGigDescription() {
            return gigDescription;
        }

        public void setGigDescription(String gigDescription) {
            this.gigDescription = gigDescription;
        }

        public String getCardDescription() {
            return cardDescription;
        }

        public void setCardDescription(String cardDescription) {
            this.cardDescription = cardDescription;
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

        public String getTotalCost() {
            return totalCost;
        }

        public void setTotalCost(String totalCost) {
            this.totalCost = totalCost;
        }

        public String getShippingPrice() {
            return shippingPrice;
        }

        public void setShippingPrice(String shippingPrice) {
            this.shippingPrice = shippingPrice;
        }

        public String getFinalCost() {
            return finalCost;
        }

        public void setFinalCost(String finalCost) {
            this.finalCost = finalCost;
        }

        public class ImageList {

            @SerializedName("thumnail")
            @Expose
            public String thumnail;

            public String getThumnail() {
                return thumnail;
            }

            public void setThumnail(String thumnail) {
                this.thumnail = thumnail;
            }
        }

        }

}

