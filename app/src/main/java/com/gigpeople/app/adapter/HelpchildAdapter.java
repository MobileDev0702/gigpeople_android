package com.gigpeople.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gigpeople.app.R;
import com.gigpeople.app.activity.HelpDetailActivity;
import com.gigpeople.app.apiModel.HelpandSupportResponse;

import java.util.ArrayList;
import java.util.List;

public class HelpchildAdapter extends RecyclerView.Adapter<HelpAdapter.DataObjectHolder> {


    Context context;

    List<HelpandSupportResponse.FaqList.Faq> faqlist;

    String status="0";

    public interface CallBack{
        public void call(String name);
    }


    public HelpchildAdapter(Context context, List<HelpandSupportResponse.FaqList.Faq> faqlist) {

        this.context = context;
        this.faqlist = faqlist;
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

        }
    }

    @Override
    public HelpAdapter.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_help_child, parent, false);

        HelpAdapter.DataObjectHolder dataObjectHolder = new HelpAdapter.DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final HelpAdapter.DataObjectHolder holder, final int position) {

       // faqlist=new ArrayList<>();

        //holder.bullet.setImageResource(categorylist.get(position).getBullet());
        holder.name.setText(faqlist.get(position).getQuestion());






    holder.itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent=new Intent(context, HelpDetailActivity.class);
              intent.putExtra("question",faqlist.get(position).getQuestion());
              intent.putExtra("answer",faqlist.get(position).getAnswer());

              intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

              context.startActivity(intent);
          }
      });

/*
holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(position==0)
        {
            Intent intent=new Intent(context,HelpDetailActivity.class);
            intent.putExtra("position","0");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(intent);
        }
        if(position==1)
        {
            Intent intent=new Intent(context,HelpDetailActivity.class);
            intent.putExtra("position","1");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(intent);
        }
        if(position==2)
        {
            Intent intent=new Intent(context,HelpDetailActivity.class);
            intent.putExtra("position","2");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(intent);
        }
        if(position==3)
        {
            Intent intent=new Intent(context,HelpDetailActivity.class);
            intent.putExtra("position","3");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(intent);
        }
        if(position==4)
        {
            Intent intent=new Intent(context,HelpDetailActivity.class);
            intent.putExtra("position","4");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(intent);


        }
        if(position==5)
        {
            Intent intent=new Intent(context,HelpDetailActivity.class);
            intent.putExtra("position","5");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(intent);


        }
        if(position==6)
        {
            Intent intent=new Intent(context,HelpDetailActivity.class);
            intent.putExtra("position","6");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(intent);


        }
        if(position==7)
        {
            Intent intent=new Intent(context,HelpDetailActivity.class);
            intent.putExtra("position","7");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(intent);


        }
        if(position==8)
        {
            Intent intent=new Intent(context,HelpDetailActivity.class);
            intent.putExtra("position","8");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(intent);


        }
        if(position==9)
        {
            Intent intent=new Intent(context,HelpDetailActivity.class);
            intent.putExtra("position","9");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(intent);


        }
        if(position==10)
        {
            Intent intent=new Intent(context,HelpDetailActivity.class);
            intent.putExtra("position","10");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(intent);


        }



    }
});
*/





    }


    @Override
    public int getItemCount() {
        return faqlist.size();
    }


}
