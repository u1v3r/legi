package sk.rdy.legi.fragment;

import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import sk.rdy.legi.R;
import sk.rdy.legi.db.model.FeedItem;
import sk.rdy.legi.util.Constants;

/**
 * Created by rdy on 26.7.2013.
 */
public class FeedListItemFragment extends Fragment {

    public static final String ARG_TITLE = "title";
    public static final String ARG_AUTHOR = "author";
    public static final String ARG_CONTENT = "content";
    public static final String ARG_URL = "url";
    public static final String ARG_DATE = "date";
    private static final String TAG = FeedListItemFragment.class.getSimpleName();

    protected FeedListItemFragment(){
        super();
    }

    public static FeedListItemFragment newInstance(FeedItem feedItem){
        FeedListItemFragment fragment = new FeedListItemFragment();
        fragment.setArguments(createBundle(feedItem));
        return fragment;
    }

    private static Bundle createBundle(FeedItem feedItem){
        Bundle bundle = new Bundle();

        if(Constants.DEBUG) Log.d(TAG,"creating feed item fragment: " + feedItem);
        bundle.putString(ARG_TITLE,feedItem.getTitle());
        bundle.putString(ARG_AUTHOR,feedItem.getAuthor());
        bundle.putString(ARG_CONTENT,feedItem.getContent());
        bundle.putString(ARG_URL,feedItem.getFeedRssAtomUrl());
        bundle.putString(ARG_DATE,String.valueOf(feedItem.getPublished()));

        return bundle;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed_list_item, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();

        Bundle args = getArguments();
        if(args != null){
            updateView(args);
        }
    }


    private void updateView(Bundle args) {

        TextView title = (TextView) getView().findViewById(R.id.feed_item_title);
        TextView author = (TextView) getView().findViewById(R.id.feed_item_author);
        TextView date = (TextView) getView().findViewById(R.id.feed_item_date);
        TextView content = (TextView) getView().findViewById(R.id.feed_item_content);
        TextView url = (TextView) getView().findViewById(R.id.feed_item_url);

        title.setText(args.getString(ARG_TITLE));
        author.setText(args.getString(ARG_AUTHOR));
        date.setText(args.getString(ARG_DATE));
        content.setText(args.getString(ARG_CONTENT));
        url.setText(args.getString(ARG_URL));
    }
}
