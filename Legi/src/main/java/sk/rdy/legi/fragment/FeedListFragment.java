package sk.rdy.legi.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import sk.rdy.legi.R;
import sk.rdy.legi.adapter.FeedListAdapter;
import sk.rdy.legi.db.model.FeedItem;
import sk.rdy.legi.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rdy on 22.7.2013.
 */
public class FeedListFragment extends Fragment {

    private static final String TAG = FeedListFragment.class.getSimpleName();
    private List<FeedItem> feedItemList;

    public FeedListFragment(List<FeedItem> feedItemList) {
        super();
        this.feedItemList = feedItemList;
    }

    /**
     * Create a new instance of FeedListFragment
     */
    public static FeedListFragment newInstance(List<FeedItem> feedItemList) {
        return new FeedListFragment(feedItemList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // The last two arguments ensure LayoutParams are inflated
        // properly.
        return inflater.inflate(R.layout.fragment_feed_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View view;
        LinearLayout container = (LinearLayout)getActivity().findViewById(R.id.fragment_feed_list_container);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        for (FeedItem feedItem : feedItemList){
            //view = getLayoutInflater(savedInstanceState).inflate(R.layout.frame,container);
            view = View.inflate(getActivity(),R.layout.frame,null);
            container.addView(view);
            transaction.add(view.getId(),FeedListItemFragment.newInstance(feedItem));
        }
        transaction.commit();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
