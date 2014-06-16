package sk.rdy.legi.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import sk.rdy.api.theoldreader.model.UnreadCountsJson;
import sk.rdy.legi.R;
import sk.rdy.legi.db.model.Folder;
import sk.rdy.legi.db.model.Subscription;

import java.util.List;

/**
 * Created by rdy on 17.7.2013.
 */
public class FoldersExpandableAdapter extends BaseExpandableListAdapter {

    private static final String TAG = FoldersExpandableAdapter.class.getSimpleName();

    private List<Folder> folderList;
    private LayoutInflater mLayoutInflater;
    private Activity mContext;


    public FoldersExpandableAdapter(Activity context, List<Folder> folderList) {
        this.mContext = context;
        this.folderList = folderList;
        this.mLayoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getGroupCount() {
        return folderList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return folderList.get(i).getSubscriptions().size();
    }

    @Override
    public Object getGroup(int i) {
        return folderList.get(i);
    }

    @Override
    public Object getChild(int i, int i2) {
        return folderList.get(i).getSubscriptions();
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i2) {
        return i2;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        View v = view;
        FoldersViewHolder viewHolder;
        if (view == null) {
            LayoutInflater li = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.drawer_list_group_item, null);
            viewHolder = new FoldersViewHolder(v);
            v.setTag(viewHolder);
        } else {
            viewHolder = (FoldersViewHolder) v.getTag();
        }

        viewHolder.folderTextView.setText(folderList.get(i).getName());
        try{
            viewHolder.unreadCountTextView.setText(String.valueOf(folderList.get(i).getUnreadCount().getCount()));
        } catch (NullPointerException e){
            int sum = 0;
            for (Subscription subscription : folderList.get(i).getSubscriptions()){
                sum += subscription.getUnreadCount().getCount();
            }
            viewHolder.unreadCountTextView.setText(String.valueOf(sum));
        }


        return v;
    }

    @Override
    public View getChildView(int i, int i2, boolean b, View view, ViewGroup viewGroup) {
        View v = view;
        SubscriptionsViewHolder viewHolder;

        Log.d(TAG,"pred-" + folderList.get(i).getSubscriptions().toString());

        if (view == null) {
            LayoutInflater li = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.drawer_list_child_item, null);
            viewHolder = new SubscriptionsViewHolder(v);
            v.setTag(viewHolder);
        } else {
            viewHolder = (SubscriptionsViewHolder) v.getTag();
        }


        viewHolder.subscriptionTextView.setText(folderList.get(i).getSubscriptions().get(i2).getTitle());
        try {
            viewHolder.unreadCountTextView.setText(
                    String.valueOf(folderList.get(i).getSubscriptions().get(i2).getUnreadCount().getCount()));
        } catch (NullPointerException e){
            viewHolder.unreadCountTextView.setText("0");
        }
        return v;
    }

    @Override
    public boolean isChildSelectable(int i, int i2) {
        return true;
    }

    private static class FoldersViewHolder {
        public TextView folderTextView;
        public TextView unreadCountTextView;

        FoldersViewHolder(View base) {
            this.folderTextView = (TextView)base.findViewById(R.id.navigation_drawer_group_folder_name);
            this.unreadCountTextView = (TextView)base.findViewById(R.id.navigation_drawer_group_unread_count);
        }
    }

    private static class SubscriptionsViewHolder {
        public TextView subscriptionTextView;
        public TextView unreadCountTextView;

        SubscriptionsViewHolder(View base) {
            this.subscriptionTextView = (TextView)base.findViewById(R.id.navigation_drawer_child_subscription_name);
            this.unreadCountTextView = (TextView)base.findViewById(R.id.navigation_drawer_child_unread_count);
        }
    }
}
