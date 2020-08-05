package com.gigpeople.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gigpeople.app.R;
import com.gigpeople.app.activity.HelpDetailActivity;
import com.gigpeople.app.activity.MessengerActivity;
import com.gigpeople.app.apiModel.HelpandSupportResponse;
import com.gigpeople.app.model.ChatModel;
import com.gigpeople.app.model.HelpModel;

import java.util.ArrayList;
import java.util.List;

public class HelpAdapter extends RecyclerView.Adapter<HelpAdapter.DataObjectHolder> implements Filterable {

    HelpchildAdapter adapter;
    Context context;
    static List<HelpandSupportResponse.FaqList> categorylist;
    static List<HelpandSupportResponse.FaqList.Faq> faqlist;
    private   HelpAdapterListener listener;

     List<HelpandSupportResponse.FaqList> categorylistFilter;
    String status="0";

    public interface CallBack{
        public void call(String name);
    }


    public HelpAdapter(Context context, List<HelpandSupportResponse.FaqList> categorylist) {

        this.context = context;
        this.categorylist = categorylist;
        this.categorylistFilter=categorylist;
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder{


        TextView name,message;
        LinearLayout layout;
        ImageView bullet;
        RecyclerView recyclerView;
        public DataObjectHolder(View itemView){
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.txt_five);
            layout=(LinearLayout)itemView.findViewById(R.id.linear1);
            bullet=(ImageView)itemView.findViewById(R.id.bullet);
            recyclerView=(RecyclerView)itemView.findViewById(R.id.recycleView);

           /* itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onContactSelected(categorylistFilter.get(getAdapterPosition()));
                }
            });*/

        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_help, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {

        faqlist=new ArrayList<>();

      //holder.bullet.setImageResource(categorylist.get(position).getBullet());
      holder.name.setText(categorylist.get(position).getCategoryName());
        faqlist=categorylist.get(position).getFaq();


int size=faqlist.size();

if(size>0) {
    adapter = new HelpchildAdapter(context, faqlist);
    holder.recyclerView.setAdapter(adapter);
    holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));

}


    }


    @Override
    public int getItemCount() {
        return categorylistFilter.size();
    }




    public   Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    categorylistFilter = categorylist;
                } else {
                    List<HelpandSupportResponse.FaqList> filteredList = new ArrayList<>();
                    for (HelpandSupportResponse.FaqList row : categorylist) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getCategoryName().toLowerCase().contains(charString.toLowerCase()) ) {
                            filteredList.add(row);
                        }
                    }

                    categorylistFilter = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = categorylistFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                categorylistFilter = (ArrayList<HelpandSupportResponse.FaqList>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface HelpAdapterListener {
        void onHelpSelected(HelpandSupportResponse.FaqList help);
    }







}
