package com.gigpeople.app.api;


import com.gigpeople.app.apiModel.AnalyticsReportResponse;
import com.gigpeople.app.apiModel.BillingAddResponse;
import com.gigpeople.app.apiModel.CartCount;
import com.gigpeople.app.apiModel.CartItemResponse;
import com.gigpeople.app.apiModel.ChatListResponse;
import com.gigpeople.app.apiModel.CommonResponse;
import com.gigpeople.app.apiModel.DashBoardResponse;
import com.gigpeople.app.apiModel.FavouriteGigListResponse;
import com.gigpeople.app.apiModel.FavouriteSellerListResponse;
import com.gigpeople.app.apiModel.ForgotPasswordResponse;
import com.gigpeople.app.apiModel.GiGAddResponse;
import com.gigpeople.app.apiModel.GiGDetailsResponse;
import com.gigpeople.app.apiModel.GiGListResponse;
import com.gigpeople.app.apiModel.GigCategoryResultResponse;
import com.gigpeople.app.apiModel.GigRequestOffersentResponse;
import com.gigpeople.app.apiModel.GigRequestResponse;
import com.gigpeople.app.apiModel.HelpandSupportResponse;
import com.gigpeople.app.apiModel.ImageUploadResponse;
import com.gigpeople.app.apiModel.LanguageModel;
import com.gigpeople.app.apiModel.LoginResponse;
import com.gigpeople.app.apiModel.MoreReviewResponse;
import com.gigpeople.app.apiModel.MyOrdersDetailResponse;
import com.gigpeople.app.apiModel.MyOrdersListResponse;
import com.gigpeople.app.apiModel.MySalesListResponse;
import com.gigpeople.app.apiModel.NotificationResponse;
import com.gigpeople.app.apiModel.PaymentHistoryResponse;
import com.gigpeople.app.apiModel.ProfileViewResponse;
import com.gigpeople.app.apiModel.RequestDetailResponse;
import com.gigpeople.app.apiModel.RequestListResponse;
import com.gigpeople.app.apiModel.SearchGiGListResponse;
import com.gigpeople.app.apiModel.SearchSellerListResponse;
import com.gigpeople.app.apiModel.SellerSalesDetailResponse;
import com.gigpeople.app.apiModel.TagModel;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

public interface ApiService {

    //1. SocialSignin
    @FormUrlEncoded
    @POST("socialLogin")
    Call<LoginResponse> callSocialLoginAPI(@Field("email_id") String email_id,
                                           @Field("first_name") String first_name,
                                           @Field("last_name") String last_name,
                                           @Field("device_type") String device_type,  //Android/IOS
                                           @Field("device_token") String device_token);

    //2. signIn
    @FormUrlEncoded
    @POST("signIn")
    Call<LoginResponse> callLoginAPI(@Field("email_id") String email_id,
                                     @Field("password") String password,
                                     @Field("device_type") String device_type,  //Android/IOS
                                     @Field("device_token") String device_token);

    //3. Image Upload
    @Multipart
    @POST("doUpload")
    Call<ImageUploadResponse> callUploadImageAPI(@PartMap Map<String, RequestBody> params);

    //4. profileView
    @FormUrlEncoded
    @POST("profileView")
    Call<ProfileViewResponse> callProfileAPI(@Field("user_id") String user_id);

    //5. ResendMobile Otp
    @FormUrlEncoded
    @POST("mobileOTP")
    Call<CommonResponse> callResendMobileOtpAPI(@Field("user_id") String user_id,
                                                @Field("mobile_no") String mobile_no);

    //6. ResendEmail Otp
    @FormUrlEncoded
    @POST("emailOTP")
    Call<CommonResponse> callResendEmailOtpAPI(@Field("user_id") String user_id,
                                               @Field("email_id") String email_id);

    //7. Verified
    @FormUrlEncoded
    @POST("Verified")
    Call<CommonResponse> callVerifyAPI(@Field("user_id") String user_id,
                                       @Field("verify_type") String verify_type);//1-Mobile, 2-Email

    //8. EditProfile
    @FormUrlEncoded
    @POST("EditProfile")
    Call<LoginResponse> callEditProfileAPI(@Field("user_id") String user_id,
                                           @Field("first_name") String first_name,
                                           @Field("last_name") String last_name,
                                           @Field("email_id") String email_id,
                                           @Field("phone_no") String phone_no,
                                           @Field("address") String address,
                                           @Field("lattitude") String lattitude,
                                           @Field("longitude") String longitude,
                                           @Field("country") String country,
                                           @Field("profile_image") String profile_image,
                                           @Field("language") String language,
                                           @Field("about") String about,
                                           @Field("skills") String skills);

    //9. SignUp
    @FormUrlEncoded
    @POST("signup")
    Call<LoginResponse> callSignUpAPI(@Field("first_name") String first_name,
                                      @Field("last_name") String last_name,
                                      @Field("email_id") String email,
                                      @Field("password") String password,
                                      @Field("device_type") String type,
                                      @Field("device_token") String token);

    //10. forgotPassword
    @FormUrlEncoded
    @POST("forgotPassword")
    Call<ForgotPasswordResponse> callforgotPasswordAPI(@Field("email_id") String email_id);

    //11. chatAdd
    @FormUrlEncoded
    @POST("chatAdd")
    Call<CommonResponse> callCahtAddAPI(@Field("from_user_id") String from_user_id,
                                        @Field("to_user_id") String to_user_id,
                                        @Field("message") String message,
                                        @Field("firebase_id") String firebase_id);

    //12. chatRead
    @FormUrlEncoded
    @POST("chatRead")
    Call<CommonResponse> callCahtReadAPI(@Field("user_id") String user_id,
                                         @Field("to_user_id") String to_user_id);

    //13. chatList
    @FormUrlEncoded
    @POST("chatList")
    Call<ChatListResponse> callchatListAPI(@Field("user_id") String user_id);

    //14. Logout
    @FormUrlEncoded
    @POST("logout")
    Call<CommonResponse> callLogout(@Field("user_id") String user_id);

    //15. notification
    @FormUrlEncoded
    @POST("pushNotification")
    Call<CommonResponse> callnotification(@Field("user_id") String user_id, @Field("notification") String notification);

    //16. Changepassword
    @FormUrlEncoded
    @POST("Changepassword")
    Call<CommonResponse> callChangepassword(@Field("user_id") String user_id, @Field("old_password") String old_password, @Field("new_password") String new_password);

    //17. customerOrders
    @FormUrlEncoded
    @POST("customerOrders")
    Call<CommonResponse> callcustomerOrders(@Field("user_id") String user_id, @Field("customer_order") String customer_order);

    //18. Dshboard
    @POST("Dashboard")
    Call<DashBoardResponse> callforDashboard();

    //19. billingAdd
    @FormUrlEncoded
    @POST("billingAdd")
    Call<BillingAddResponse> callbillingAddAPI(@Field("user_id") String user_id,
                                               @Field("company_name") String company_name,
                                               @Field("full_name") String full_name,
                                               @Field("address") String address,
                                               @Field("billing_lattitude") String billing_lattitude,
                                               @Field("billing_longitude") String billing_longitude,
                                               @Field("country") String country,
                                               @Field("zipcode") String zipcode);

    //20. Help & Support
    @POST("HelpSupport")
    Call<HelpandSupportResponse> callforHelpandSupport();

    //21. ContactUs
    @FormUrlEncoded
    @POST("ContactUs")
    Call<CommonResponse> callContactusrequest(@Field("user_id") String user_id, @Field("type") String type, @Field("description") String description);

    //22. Notification List
    @FormUrlEncoded
    @POST("notificationList")
    Call<NotificationResponse> callNotificationList(@Field("user_id") String userid);

    //23. billingDetails
    @FormUrlEncoded
    @POST("billingDetails")
    Call<BillingAddResponse> callBillingDetailAPI(@Field("user_id") String userid);

    //24. RequestList
    @FormUrlEncoded
    @POST("RequestList")
    Call<RequestListResponse> callRequestListAPI(@Field("user_id") String userid);

    //25. RequestDetails
    @FormUrlEncoded
    @POST("RequestDetails")
    Call<RequestDetailResponse> callRequestDetailsAPI(@Field("user_id") String userid,
                                                      @Field("request_id") String request_id);

    //26.requestAdd
    @FormUrlEncoded
    @POST("requestAdd")
    Call<CommonResponse> callrequestAddAPI(@Field("user_id") String user_id,
                                           @Field("category_id") String category_id,
                                           @Field("subcategory_id") String subcategory_id,
                                           @Field("image") String image,
                                           @Field("quantity") String quantity,
                                           @Field("price") String price,
                                           @Field("deliverytime") String deliverytime,
                                           @Field("description") String description);

    //27.requestEdit
    @FormUrlEncoded
    @POST("requestEdit")
    Call<CommonResponse> callrequestEditAPI(@Field("request_id") String request_id,
                                            @Field("category_id") String category_id,
                                            @Field("subcategory_id") String subcategory_id,
                                            @Field("image") String image,
                                            @Field("quantity") String quantity,
                                            @Field("price") String price,
                                            @Field("deliverytime") String deliverytime,
                                            @Field("description") String description);


    //28. blockChat
    @FormUrlEncoded
    @POST("blockChat")
    Call<CommonResponse> callblockChat(@Field("user_id") String user_id, @Field("friend_id") String friend_id);

    //29.gigAdd
    @FormUrlEncoded
    @POST("gigAdd")
    Call<GiGAddResponse> callgigAddAPI(@Field("user_id") String user_id,
                                       @Field("title") String title,
                                       @Field("category_id") String category_id,
                                       @Field("subcategory_id") String subcategory_id,
                                       @Field("image") String image,
                                       @Field("price") String price,
                                       @Field("delivery_time") String delivery_time,
                                       @Field("shipping") String shipping,
                                       @Field("shipping_price") String shipping_price,
                                       @Field("revisions") String revisions,
                                       @Field("gig_tag") String gig_tag,
                                       @Field("description") String description,
                                       @Field("status") String status);


    //30.mobwebapps.com/gig_people/api/gigEdit
    @FormUrlEncoded
    @POST("gigEdit")
    Call<CommonResponse> callgigEditAPI(@Field("user_id") String user_id,
                                        @Field("gig_id") String gig_id,
                                        @Field("title") String title,
                                        @Field("category_id") String category_id,
                                        @Field("subcategory_id") String subcategory_id,
                                        @Field("image") String image,
                                        @Field("price") String price,
                                        @Field("delivery_time") String delivery_time,
                                        @Field("shipping") String shipping,
                                        @Field("shipping_price") String shipping_price,
                                        @Field("revisions") String revisions,
                                        @Field("gig_tag") String gig_tag,
                                        @Field("description") String description,
                                        @Field("status") String status);


    //31. GigList
    @FormUrlEncoded
    @POST("GigList")
    Call<GiGListResponse> callGigList(@Field("user_id") String user_id);

    //32. gigDetails
    @FormUrlEncoded
    @POST("gigDetails")
    Call<GiGDetailsResponse> callgigDetails(@Field("user_id") String user_id, @Field("gig_id") String gig_id);

    //33. mobwebapps.com/gig_people/api/favouriteAdd
    @FormUrlEncoded
    @POST("favouriteAdd")
    Call<CommonResponse> callFavouriteAdd(@Field("user_id") String user_id, @Field("favourite_type") String favourite_type, @Field("favourite_id") String favourite_id);

    //34. mobwebapps.com/gig_people/api/GigRequest ,subcategory_id
    @FormUrlEncoded
    @POST("GigRequest")
    Call<GigRequestResponse> callGigRequestListNew(@Field("user_id") String user_id,
                                                   @Field("search_key") String search_key,
                                                   @Field("location") String location,
                                                   @Field("lattitude") String latitude,
                                                   @Field("longitude") String longitude,
                                                   @Field("category_id") String category_id,
                                                   @Field("subcategory_id") String subcategory_id);

   /* user_id:1
    search_key:
    location:Coimbatore Road
    lattitude:10.7710793
    longitude:76.67078769999999
    category_id:
    subcategory_id:*/

    //35. mobwebapps.com/gig_people/api/favouriteGig
    @FormUrlEncoded
    @POST("favouriteGig")
    Call<FavouriteGigListResponse> callFavouriteGiglist(@Field("user_id") String user_id);

    //36. mobwebapps.com/gig_people/api/favouriteSeller
    @FormUrlEncoded
    @POST("favouriteSeller")
    Call<FavouriteSellerListResponse> callFavouriteSellerlist(@Field("user_id") String user_id);

    //37.
    @FormUrlEncoded
    @POST("cardAdd")
    Call<CommonResponse> callcartAdd(@Field("user_id") String userid,
                                     @Field("gig_id") String gig_id,
                                     @Field("seller_id") String seller_id,
                                     @Field("description") String description,
                                     @Field("quantity") String quantity);

    //38.
    @FormUrlEncoded
    @POST("orderNow")
    Call<CommonResponse> callOrderNow(@Field("user_id") String userid,
                                      @Field("total_amount") String total_amount,
                                      @Field("payment_option") String payment_option,
                                      @Field("payment_id") String payment_id);

    //39.
    @FormUrlEncoded
    @POST("cardCount")
    Call<CartCount> callShoppingcartCount(@Field("user_id") String userid);

    //40.
    @FormUrlEncoded
    @POST("cardItem")
    Call<CartItemResponse> callCartList(@Field("user_id") String userid);

    //41.
    @FormUrlEncoded
    @POST("categoryGiglist")
    Call<GigCategoryResultResponse> callcategoryGiglist(@Field("user_id") String userid,
                                                        @Field("sub_category_id") String sub_category_id,
                                                        @Field("search_key") String searchkey,
                                                        @Field("seller_location") String sellerLoc,
                                                        @Field("seller_lat") String sellerLat,
                                                        @Field("seller_lon") String sellerLon,
                                                        @Field("category_id") String categId,
                                                        @Field("delivery_time") String deliveryTime,
                                                        @Field("online_status") String onlineStatus,
                                                        @Field("language") String language,
                                                        @Field("minimum_price") String minPrice,
                                                        @Field("maximum_price") String maximumPrice,
                                                        @Field("tags") String tag);

    //42.
    @FormUrlEncoded
    @POST("offerSendList")
    Call<GigRequestOffersentResponse> callGigRequestListNewSent(@Field("user_id") String userid,
                                                                @Field("search_key") String search_key,
                                                                @Field("location") String location,
                                                                @Field("lattitude") String latitude,
                                                                @Field("longitude") String longitude,
                                                                @Field("category_id") String category_id,
                                                                @Field("subcategory_id") String subcategory_id);

    //43. offerSend
    @FormUrlEncoded
    @POST("offerSend")
    Call<CommonResponse> callOferSendRequest(@Field("user_id") String userid, @Field("request_id") String request_id,
                                             @Field("deliver_time") String deliver_time, @Field("price") String price, @Field("description") String description);

    //44.
    @FormUrlEncoded
    @POST("buyerOfferStatus")
    Call<CommonResponse> callbuyerOfferStatusdAPI(@Field("user_id") String user_id,
                                                  @Field("request_id") String request_id,
                                                  @Field("seller_id") String seller_id,
                                                  @Field("offer_id") String offer_id,
                                                  @Field("payment_option") String payment_option,
                                                  @Field("payment_id") String payment_id,
                                                  @Field("offer_status") String offer_status);

    //45.
    @FormUrlEncoded
    @POST("Analytics")
    Call<AnalyticsReportResponse> calllAnalyticsReport(@Field("user_id") String userid);

    //46.
    @FormUrlEncoded
    @POST("myOrders")
    Call<MyOrdersListResponse> callMyordersList(@Field("user_id") String userid);

    //47.
    @GET("languageList")
    Call<LanguageModel> callLanguage();

    //48.
    @FormUrlEncoded
    @POST("tagList")
    Call<TagModel> callTagList(@Field("sub_category_id") String subCategoryid);

    //49.
    @FormUrlEncoded
    @POST("cardRemove")
    Call<CommonResponse> callCartitemRemove(@Field("user_id") String userid, @Field("gig_id") String gigid);

    //50. mobwebapps.com/gig_people/api/searchSeller
    @FormUrlEncoded
    @POST("searchSeller")
    Call<SearchSellerListResponse> callsearchSeller(@Field("user_id") String userid, @Field("user_search") String user_search);

    //51. mobwebapps.com/gig_people/api/searchGig
    @FormUrlEncoded
    @POST("searchGig")
    Call<SearchGiGListResponse> callsearchGig(@Field("user_id") String userid, @Field("gig_search") String gig_search);

    //52.
    @FormUrlEncoded
    @POST("mySales")
    Call<MySalesListResponse> callmySales(@Field("user_id") String userid);

    //53.
    //mobwebapps.com/gig_people/api/sellerRequest
    @FormUrlEncoded
    @POST("sellerRequest")
    Call<CommonResponse> callsellerRequest(@Field("user_id") String userid, @Field("request_id") String request_id, @Field("request_status") String request_status);

    //54.
    @FormUrlEncoded
    @POST("userReview")
    Call<MoreReviewResponse> calluserReviewList(@Field("user_id") String userid);

    //55.
    @FormUrlEncoded
    @POST("paymentHistory")
    Call<PaymentHistoryResponse> callPaymentHistory(@Field("user_id") String userid);

    //56.
    @FormUrlEncoded
    @POST("gigPublish")
    Call<CommonResponse> callPublishGig(@Field("user_id") String userid, @Field("gig_id") String gigid);

    //57.
    @FormUrlEncoded
    @POST("BuyerOrderHistoryList")
    Call<MyOrdersDetailResponse> callMyOrderDetail(@Field("user_id") String userid, @Field("order_id") String order_id);

    //58.
    @FormUrlEncoded
    @POST("BuyerOrderHistory")
    Call<CommonResponse> callBottomButtonMyOrder(@Field("user_id") String userid,
                                                 @Field("order_id") String order_id,
                                                 @Field("seller_id") String seller_id,
                                                 @Field("history_type") String history_type,//1.Message, 2.Revision, 3.Dispute, 4.Admin
                                                 @Field("message") String message);

    //59.
    @FormUrlEncoded
    @POST("orderCancelled")
    Call<CommonResponse> callOrderCancell(@Field("user_id") String userid,
                                          @Field("order_id") String order_id,
                                          @Field("user_type") String user_type,//1.Buyer, 2.Seller
                                          @Field("cancel_reason") String cancel_reason);

    //60.
    @FormUrlEncoded
    @POST("buyerorderFeedback")
    Call<CommonResponse> callGigReview(@Field("user_id") String userid,
                                       @Field("order_id") String order_id,
                                       @Field("order_rating") String order_rating,
                                       @Field("order_review") String order_review,
                                       @Field("gig_id") String gig_id);

    //61.
    @FormUrlEncoded
    @POST("ratingadd")
    Call<CommonResponse> callUserReview(@Field("user_id") String userid,
                                        @Field("order_id") String order_id,
                                        @Field("rating_type") String rating_type,//1.Buyer, 2.Seller
                                        @Field("rating") String order_rating,
                                        @Field("review") String order_review,
    @Field("gig_id") String gig_id);

    //62.
    @FormUrlEncoded
    @POST("SellerOrderHistoryList")
    Call<SellerSalesDetailResponse> callMySalesDetail(@Field("user_id") String userid, @Field("order_id") String order_id);

    //63.
    @FormUrlEncoded
    @POST("sellerRevision")
    Call<CommonResponse> callsellerRevision(@Field("user_id") String userid,
                                            @Field("order_id") String order_id,
                                            @Field("status") String status,
                                            @Field("msg_id") String msg_id);//1.Accept, 2.Rejected

    //64.
    @FormUrlEncoded
    @POST("SellerOrderHistory")
    Call<CommonResponse> callSellerOrderHistory(@Field("user_id") String userid,
                                                @Field("order_id") String order_id,
                                                @Field("buyer_id") String buyer_id,
                                                @Field("history_type") String history_type,//1.Message, 3.Dispute, 4.Admin
                                                @Field("message") String message);

    //65.
    @FormUrlEncoded
    @POST("sellerDelivery")
    Call<CommonResponse> callsellerDelivery(@Field("user_id") String userid,
                                            @Field("order_id") String order_id,
                                            @Field("buyer_id") String buyer_id,
                                            @Field("project") String project,//1.Message, 3.Dispute, 4.Admin
                                            @Field("description") String description,
                                            @Field("type") String type,
                                            @Field("thumb") String thumb);

    //66.
    @FormUrlEncoded
    @POST("walletList")
    Call<CommonResponse> callwalletList(@Field("user_id") String userid);

    //67.
    @FormUrlEncoded
    @POST("withdraw")
    Call<CommonResponse> callwithdraw(@Field("user_id") String userid);

    //68.
    @FormUrlEncoded
    @POST("buyertimeAccept")
    Call<CommonResponse> callAcceptTimeExtention(@Field("user_id") String userid,
                                                 @Field("order_id") String order_id,
                                                 @Field("status") String status);//1.Accept, 2.Rejected

    //69.
    @FormUrlEncoded
    @POST("timeExtensions")
    Call<CommonResponse> callAskTimeExtention(@Field("user_id") String userid,
                                              @Field("order_id") String order_id,
                                              @Field("delivery_time") String delivery_time,
                                              @Field("description") String description);

    //69.
    @FormUrlEncoded
    @POST("BuyerCancelledStatus")
    Call<CommonResponse> callBuyerCancelledStatus(@Field("user_id") String userid,
                                                  @Field("order_id") String order_id,
                                                  @Field("status") String status);

    //69.
    @FormUrlEncoded
    @POST("SellerCancelledStatus")
    Call<CommonResponse> callSellerCancelledStatus(@Field("user_id") String userid,
                                                   @Field("order_id") String order_id,
                                                   @Field("status") String status);

    //69.
    @FormUrlEncoded
    @POST("notificationUpdate")
    Call<CommonResponse> callUpdateNoti(@Field("user_id") String userid);

    //69.
    @FormUrlEncoded
    @POST("notificationCount")
    Call<CommonResponse> callNotifiCount(@Field("user_id") String userid);


}
