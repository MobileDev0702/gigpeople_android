package com.gigpeople.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gigpeople.app.R;
import com.gigpeople.app.model.ClassLeftDrawer;

import java.util.List;



/**
 * Created by swesspos on 11/11/17.
 */

public class LeftNavAdapter extends ArrayAdapter<ClassLeftDrawer> {

    Context context;

    public LeftNavAdapter(Context context, int resourceId,
                          List<ClassLeftDrawer> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    /*private view holder class*/
    public class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
        View view1;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        ClassLeftDrawer rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.activity_main_left_drawer_menu, null);
            holder = new ViewHolder();
             holder.txtTitle = (TextView) convertView.findViewById(R.id.txv_nave);
            holder.imageView = (ImageView) convertView.findViewById(R.id.img_nave);
            holder.view1 = (View) convertView.findViewById(R.id.view1);

            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.txtTitle.setText(rowItem.getMenu_name());
        holder.imageView.setImageResource(rowItem.getMenu_img());
        if(position==3 || position==5)
        {
            holder.view1.setVisibility(View.VISIBLE);

        }

        else
        {
            holder.view1.setVisibility(View.GONE);

        }
        return convertView;
    }
}