package sk.rdy.legi.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import sk.rdy.legi.R;
import sk.rdy.legi.db.model.FeedItem;
import sk.rdy.legi.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rdy on 22.7.2013.
 */
public class FeedListAdapter extends BaseAdapter {

    private static final String TAG = FeedListAdapter.class.getSimpleName();
    private final Activity mContext;
    public static List<FeedItem> feedItemList;
    private final LayoutInflater mLayoutInflater;

    public FeedListAdapter(Activity context){
        this.mContext = context;
        feedItemList = new ArrayList<FeedItem>();
        this.mLayoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return feedItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return feedItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        FeedListViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.fragment_feed_list_item, null);
            viewHolder = new FeedListViewHolder(v);
            v.setTag(viewHolder);
        } else {
            viewHolder = (FeedListViewHolder) v.getTag();
        }

        viewHolder.title.setText(feedItemList.get(position).getTitle());
        viewHolder.author.setText(feedItemList.get(position).getAuthor().toUpperCase());
        viewHolder.date.setText(String.valueOf(feedItemList.get(position).getPublished()));
        String content = feedItemList.get(position).getContent();
        if(content.length() > Constants.FEED_ITEM_CONTENT_MAX_LENGHT){
            content = content.substring(0,Constants.FEED_ITEM_CONTENT_MAX_LENGHT);

        }
        viewHolder.content.setText(content);
        String url = feedItemList.get(position).getFeedRssAtomUrl().toUpperCase();
        viewHolder.url.setText(url);

        return v;
    }

    public static class FeedListViewHolder{

        public ImageView image;
        public TextView title;
        public TextView url;
        public TextView content;
        public TextView author;
        public TextView date;

        FeedListViewHolder(View base) {
            this.title = (TextView)base.findViewById(R.id.feed_item_title);
            this.author = (TextView)base.findViewById(R.id.feed_item_author);
            this.date = (TextView)base.findViewById(R.id.feed_item_date);
            this.content = (TextView)base.findViewById(R.id.feed_item_content);
            this.url = (TextView)base.findViewById(R.id.feed_item_url);
        }
    }
}
