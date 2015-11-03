package com.move10x.totem.design;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.app.Activity;
import android.widget.ImageView;

import com.move10x.totem.R;
import com.move10x.totem.models.CurrentProfile;
import com.move10x.totem.services.CurrentProfileService;

import java.util.List;

/**
 * Created by Ravi on 10/21/2015.
 */
public class DrawerAdapter extends ArrayAdapter<DrawerListItem> {

    Context context;
    List<DrawerListItem> drawerItemList;
    int layoutResID;

    public DrawerAdapter(Context context, int layoutResourceID,
                         List<DrawerListItem> listItems) {
        super(context, layoutResourceID, listItems);
        this.context = context;
        this.drawerItemList = listItems;
        this.layoutResID = layoutResourceID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        DrawerItemHolder drawerHolder;
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            drawerHolder = new DrawerItemHolder();

            view = inflater.inflate(layoutResID, parent, false);
            drawerHolder.ItemName = (TextView) view
                    .findViewById(R.id.drawer_itemName);
            drawerHolder.icon = (ImageView) view.findViewById(R.id.drawer_icon);
            drawerHolder.profileLayout = (LinearLayout) view.findViewById(R.id.profileLayout);
            drawerHolder.menuLayout = (LinearLayout) view.findViewById(R.id.menuLayout);
            drawerHolder.userName = (TextView) view.findViewById(R.id.drawer_userName);
            drawerHolder.userRole = (TextView) view.findViewById(R.id.drawer_userRole);
            //drawerHolder.userImage= (ImageView) view.findViewById(R.id.drawer_userImage);
            view.setTag(drawerHolder);

        } else {
            drawerHolder = (DrawerItemHolder) view.getTag();

        }

        DrawerListItem dItem = (DrawerListItem) this.drawerItemList.get(position);

        drawerHolder.icon.setImageDrawable(view.getResources().getDrawable(
                dItem.getImgResID()));
        drawerHolder.ItemName.setText(dItem.getItemName());

        //Check if user item.
        if (dItem.getItemName().equals("User")) {
            //Show user section and hide MenuSection
            //LinearLayout profileLayout = view.drawerHolder;
            drawerHolder.profileLayout.setVisibility(View.VISIBLE);
            drawerHolder.menuLayout.setVisibility(View.GONE);

            CurrentProfile cr = new CurrentProfileService(view.getContext()).getCurrentProfile();
            drawerHolder.userName.setText(cr.getUserName());
            drawerHolder.userRole.setText(cr.getUserType().toUpperCase());
        } else {
            //set listener
            drawerHolder.menuLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("drawerClick", "drawer item clicked. Position: ");
                }
            });
        }

        return view;
    }

    private static class DrawerItemHolder {

        TextView ItemName;
        ImageView icon, userImage;
        LinearLayout profileLayout, menuLayout;
        TextView userName, userRole;
    }
}