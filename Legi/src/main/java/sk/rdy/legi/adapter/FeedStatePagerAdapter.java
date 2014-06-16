package sk.rdy.legi.adapter;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import sk.rdy.api.theoldreader.model.ItemContentsJson;
import sk.rdy.api.theoldreader.model.ItemsJson;
import sk.rdy.legi.async.Tasks;
import sk.rdy.legi.db.model.FeedItem;
import sk.rdy.legi.fragment.FeedListFragment;
import sk.rdy.legi.fragment.FeedListItemFragment;
import sk.rdy.legi.util.Constants;

import java.util.ArrayList;
import java.util.List;

import static sk.rdy.legi.auth.TheOldReaderAuth.oldReader;
import static sk.rdy.legi.util.Constants.DEBUG;
import static sk.rdy.legi.util.Constants.URL_PARAM_ALL_FEEDS;

/**
 * Created by rdy on 21.7.2013.
 */
public class FeedStatePagerAdapter extends FragmentStatePagerAdapter {

    private static final String TAG = FeedStatePagerAdapter.class.getSimpleName();
    private List<FeedListFragment> fragmentList;
    private Tasks.TaskCallback<List<FeedItem>> callback = new Tasks.TaskCallback<List<FeedItem>>() {
        @Override
        public void onAsyncTaskFinished(List<FeedItem> data) {
            if(DEBUG) Log.d(TAG, "itemContents postExecute: " + data);
            fragmentList.add(FeedListFragment.newInstance(data));
            notifyDataSetChanged();
        }
    };

    public FeedStatePagerAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
        fragmentList = new ArrayList<FeedListFragment>();

        // init first page
        Tasks.fetchFeedList(Constants.URL_PARAM_ALL_FEEDS,0,callback);
    }

    @Override
    public Fragment getItem(int i) {

        // fetch next page
        if(i > 0) Tasks.fetchFeedList(Constants.URL_PARAM_ALL_FEEDS,i,callback);

        // return current page
        return fragmentList.get(i);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
