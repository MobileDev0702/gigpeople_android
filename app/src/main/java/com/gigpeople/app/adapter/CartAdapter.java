package com.gigpeople.app.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gigpeople.app.R;
import com.gigpeople.app.api.ApiService;
import com.gigpeople.app.api.RetrofitSingleton;
import com.gigpeople.app.apiModel.CartItemResponse;

import com.gigpeople.app.apiModel.CommonResponse;
import com.gigpeople.app.model.MainCategory;
import com.gigpeople.app.utils.GlobalMethods;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by praveensamuel on 26/01/19.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.DataObjectHolder> {

    ApiService apiService;
    Context context;
    List<CartItemResponse.CartItem> mData;
    String types;
    int selectposition = 0;
    int number_increase;
    ProgressDialog progressDialog;
    CallBack callBack;

    public interface CallBack {
        public void call(String name);
    }

    public CartAdapter(Context context, List<CartItemResponse.CartItem> mData,CallBack callBack) {
        this.context = context;
        this.mData = mData;
        this.callBack=callBack;
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder {

        ImageView imagFav, increment, decrement, img_delete;
        TextView txtMenu, txtgigName, txtgigDescription, txtgigTotal, txtshippingCharge, txttotalAmount;
        RoundedImageView gigImage;
        EditText number;

        public DataObjectHolder(View itemView) {
            super(itemView);
            imagFav = (ImageView) itemView.findViewById(R.id.img_fav);
            increment = (ImageView) itemView.findViewById(R.id.increment);
            decrement = (ImageView) itemView.findViewById(R.id.decrement);
            number = (EditText) itemView.findViewById(R.id.edt_number);
            txtgigName = (TextView) itemView.findViewById(R.id.txtgigname);
            txtgigDescription = (TextView) itemView.findViewById(R.id.txtdescription);
            txtgigTotal = (TextView) itemView.findViewById(R.id.txt_gigtotal);
            txtshippingCharge = (TextView) itemView.findViewById(R.id.txtshippingcharge);
            txttotalAmount = (TextView) itemView.findViewById(R.id.txttotalamout);
            gigImage = (RoundedImageView) itemView.findViewById(R.id.imageView);
            img_delete = (ImageView) itemView.findViewById(R.id.img_delete);

        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_cart_item, parent, false);
        apiService = RetrofitSingleton.createService(ApiService.class);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {

        holder.txtgigName.setText(mData.get(position).getTitle());
        holder.txtgigDescription.setText(mData.get(position).getGigDescription());
        holder.txtgigTotal.setText("$" + mData.get(position).getPrice());
        holder.txtshippingCharge.setText("$" + mData.get(position).getShippingPrice());

        Double total_amount = Double.parseDouble(mData.get(position).getPrice()) + Double.parseDouble(mData.get(position).getShippingPrice());
        holder.txttotalAmount.setText("$" + total_amount);
        holder.number.setText(mData.get(position).getQuantity());

        Glide.with(context).load(mData.get(position).getImageList().get(0).getThumnail()).into(holder.gigImage);

        holder.increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number_increase = (Integer.parseInt(holder.number.getText().toString()));
                number_increase++;
                if (GlobalMethods.isNetworkAvailable(context)){
                    String qty= String.valueOf(number_increase);
                    toClickAddtocart(GlobalMethods.GetUserID(context),mData.get(position).getGigId(),mData.get(position).getSellerId(),"",qty,position);
                }else {
                    GlobalMethods.Toast(context,context.getString(R.string.internet));
                }
            }
        });

        holder.decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number_increase = (Integer.parseInt(holder.number.getText().toString()));
                if (number_increase > 1) {
                    number_increase--;
                    if (GlobalMethods.isNetworkAvailable(context)){
                        String qty=String.valueOf(number_increase);
                        toClickAddtocart(GlobalMethods.GetUserID(context),mData.get(position).getGigId(),mData.get(position).getSellerId(),"",qty,position);
                    }else {
                        GlobalMethods.Toast(context,context.getString(R.string.internet));
                    }
                }
            }
        });

        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                removeItemincart(GlobalMethods.GetUserID(context), mData.get(position).getGigId(), position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private void toClickAddtocart(String userid, String gigid, String seller_ID, String description, final String Quantity, final int positi) {
        /*progressDialog = ProgressDialog.show(context, "", "", true);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress);*/
        String logg = "USER ID:" + userid + "\nGigId :" + gigid + "\nSeller :" + seller_ID + "\nDescription :" + description+ "\nQuantity :" + Quantity;
        Log.e("ClickAddCartReq", logg);
        Call<CommonResponse> call = apiService.callcartAdd(userid, gigid, seller_ID, description, Quantity);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                Log.e("ClickAddCartResp", new Gson().toJson(response.body()));
                //progressDialog.dismiss();
                if (response.isSuccessful()) {
                    CommonResponse resp = response.body();

                    if (resp.getStatus().equals("1")) {
                         mData.get(positi).setQuantity(Quantity);
                         notifyDataSetChanged();
                         callBack.call("");
                         //GlobalMethods.Toast(context, resp.getMessage());
                    } else {
                        GlobalMethods.Toast(context, resp.getMessage());
                    }
                }
            }

            public void onFailure(Call<CommonResponse> call, Throwable t) {
                Log.e("ClickAddCartFail", t.getMessage());
               // progressDialog.dismiss();
            }
        });
    }

    private void removeItemincart(String userid, String Gigid, final int position) {
       /* progressDialog = ProgressDialog.show(context, "", "", true);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress);*/
        Log.e("CartRemoveReq","UserId: "+userid+"\nGigId: "+Gigid);
        Call<CommonResponse> call = apiService.callCartitemRemove(userid, Gigid);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                Log.e("CartRemoveResp", new Gson().toJson(response.body()));
               // progressDialog.dismiss();
                if (response.isSuccessful()){
                    CommonResponse resp = response.body();
                    if (resp.getStatus().equals("1")) {
                        callBack.call("");
                        mData.remove(position);
                        notifyDataSetChanged();
                        GlobalMethods.Toast(context, resp.getMessage());
                    }
                }
            }

            public void onFailure(Call<CommonResponse> call, Throwable t) {
                Log.e("cartRemovereFail", t.getMessage());
               // progressDialog.dismiss();
            }
        });

    }

}

