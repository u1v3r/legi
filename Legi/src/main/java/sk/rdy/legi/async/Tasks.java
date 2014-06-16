package sk.rdy.legi.async;

import android.os.AsyncTask;
import android.util.Log;
import com.google.common.collect.LinkedListMultimap;
import sk.rdy.api.theoldreader.model.*;
import sk.rdy.legi.db.model.FeedItem;
import sk.rdy.legi.db.model.Folder;
import sk.rdy.legi.db.model.Subscription;
import sk.rdy.legi.fragment.FeedListFragment;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static sk.rdy.legi.auth.TheOldReaderAuth.oldReader;
import static sk.rdy.legi.util.Constants.*;

/**
 * Created by rdy on 21.7.2013.
 */
public class Tasks {

    private static final String TAG = Tasks.class.getSimpleName();
    private static long feedContinuation = 0l;

    public interface TaskCallback<T>{
        public void onAsyncTaskFinished(T data);
    }

    public static void fetchFeedList(String feedId, int itemId, final  TaskCallback<List<FeedItem>> callback){
        new AsyncTask<String, Void, List<FeedItem>>() {

            private List<FeedItem> feedItemList;
            private int itemId;

            @Override
            protected List<FeedItem> doInBackground(String... params) {

                List<FeedItem> feedItems = new ArrayList<FeedItem>();

                if(params.length != 2) return feedItems;
                try {
                    String feedId = params[0];
                    itemId = Integer.valueOf(params[1]);
                    feedItemList = new ArrayList<FeedItem>();
                    ItemsJson itemsJson;

                    if(feedContinuation == 0){
                        itemsJson = oldReader.getItemsFromFeed(feedId, true, 5, false);
                    }else{
                        itemsJson = oldReader.getItemsFromFeed(feedId, true, 5, false, feedContinuation);
                    }

                    String[] itemIds = new String[itemsJson.getItemRefs().size()];
                    int i = 0;

                    for(ItemsJson.Item itemJson : itemsJson.getItemRefs()){
                        itemIds[i++] = itemJson.getId();
                    }

                    ItemContentsJson itemContentsJson = oldReader.getItemContents(itemIds);
                    i = 0;
                    for (ItemContentsJson.ItemContent itemContent : itemContentsJson.getItems()){
                        feedItems.add(new FeedItem(itemContent,itemsJson.getItemRefs().get(i)));
                        i++;
                    }

                    feedContinuation = itemsJson.getContinuation();
                } catch (Exception e){}


                return feedItems;

            }

            @Override
            protected void onPostExecute(List<FeedItem> feedItemList) {
                callback.onAsyncTaskFinished(feedItemList);
            }
        }.execute(feedId,String.valueOf(itemId));
    }

    public static void syncNavigationDrawerData(final TaskCallback<List<Folder>> callback){

        new AsyncTask<Void, Void, List<Folder>>() {
            @Override
            protected List<Folder> doInBackground(Void... voids) {
                if(DEBUG) Log.d(TAG, "sync folders in background...");

                LinkedListMultimap<String,SubscriptionsJson.Subscription> map = LinkedListMultimap.create();
                LinkedHashMap<String,UnreadCountsJson.UnreadItemCount> unreadMap =
                        new LinkedHashMap<String, UnreadCountsJson.UnreadItemCount>();
                ArrayList<Folder> folders = new ArrayList<Folder>();
                try{
                ArrayList<FolderJson> folderJsons = oldReader.getFolders();
                if(DEBUG) Log.d(TAG,"folders count: " + folderJsons.size());

                SubscriptionsJson subscriptionsJson = oldReader.getSubscriptions();
                if(DEBUG) Log.d(TAG, "subscriptions count: " + subscriptionsJson.getSubscriptions().size());

                UnreadCountsJson unread = oldReader.getUnreadCounts();
                if(DEBUG) Log.d(TAG,unread.getUnreadItemCounts().toString());

                for (UnreadCountsJson.UnreadItemCount unreadItemCount : unread.getUnreadItemCounts()){
                    unreadMap.put(unreadItemCount.getId(),unreadItemCount);
                }


                for (SubscriptionsJson.Subscription subscription : subscriptionsJson.getSubscriptions()){

                    // Subscription without category
                    if(subscription.getCategories().size() == 0){
                        map.put(SUBSCRIPTION_WITHOUT_CATEGORY_ID,subscription);
                        continue;
                    }

                    // Other subscriptions
                    for(CategoryJson categoryJson : subscription.getCategories()){
                        map.put(categoryJson.getId(),subscription);
                    }
                }

                // Add subscriptions without category
                List<SubscriptionsJson.Subscription> subsWithouCategory;
                if((subsWithouCategory = map.get(SUBSCRIPTION_WITHOUT_CATEGORY_ID)).size() > 0){
                    Folder folder = new Folder(SUBSCRIPTION_WITHOUT_CATEGORY_ID,"0",SUBSCRIPTION_WITHOUT_CATEGORY);
                    folder.setUnreadCount(unreadMap.get(folder.getId()));
                    for (SubscriptionsJson.Subscription subscription : subsWithouCategory){
                        Subscription subscriptionModel = new Subscription(subscription);
                        subscriptionModel.setUnreadCount(unreadMap.get(subscriptionModel.getId()));
                        folder.getSubscriptions().add(subscriptionModel);
                    }
                    folders.add(folder);
                }

                // Add subscriptions with categories
                for (FolderJson folderJson : folderJsons){
                    Folder folder = new Folder(folderJson);
                    folder.setUnreadCount(unreadMap.get(folder.getId()));
                    for(SubscriptionsJson.Subscription subscription : map.get(folderJson.getId())){
                        Subscription subscriptionModel = new Subscription(subscription);
                        subscriptionModel.setUnreadCount(unreadMap.get(subscriptionModel.getId()));
                        folder.getSubscriptions().add(subscriptionModel);
                    }

                    folders.add(folder);
                }
                } catch (Exception e){

                }

                return folders;
            }

            @Override
            protected void onPostExecute(List<Folder> folders) {
                callback.onAsyncTaskFinished(folders);
            }

        }.execute();
    }
}
