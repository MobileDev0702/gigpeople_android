package com.gigpeople.app.utils;

import com.gigpeople.app.apiModel.DashBoardResponse;
import com.gigpeople.app.apiModel.FavouriteGigListResponse;
import com.gigpeople.app.apiModel.FavouriteSellerListResponse;
import com.gigpeople.app.apiModel.GigCategoryResultResponse;
import com.gigpeople.app.apiModel.GigRequestOffersentResponse;
import com.gigpeople.app.apiModel.GigRequestResponse;
import com.gigpeople.app.apiModel.MyOrdersListResponse;

import java.util.ArrayList;
import java.util.List;

public class GlobalVariables {



    //ArrayList

   //Banner images for all Screens
    public static List<DashBoardResponse.BannerList> dash_boardBannerlist;
    //Dashboard and Category and sub category an
    public static List <DashBoardResponse.MainCategoryList>dashmainCategorylist=new ArrayList<>();

   // Gig NewREquest

    public static List<GigRequestResponse.RequestDetail>gigNewRequestlist;

    // Gig  newRequestSENT tab
    public static List<GigRequestOffersentResponse.RequestDetail> gigNewRequesSenttlist;

    //Favurite Seller list
    public static List<FavouriteSellerListResponse.FavouritesellerList> FavouriteSellerList;


    public  static List<FavouriteGigListResponse.FavouritegigList> FavouriteGigList;

 public  static List<GigCategoryResultResponse.CategorygigList> DashboardMenuDetialspage;

 //  MY orders Pages
 public  static List<MyOrdersListResponse.ActiveOrderList> MyorderActiveListpage;

 public  static List<MyOrdersListResponse.DeliveredOrderList> MyorderDeliveryListpage;
 //

 public  static List<MyOrdersListResponse.CancelledOrderList> MyorderCancelListpage;

}
