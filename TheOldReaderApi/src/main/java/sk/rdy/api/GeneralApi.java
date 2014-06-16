package sk.rdy.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import sk.rdy.api.theoldreader.exception.TheOldReaderException;
import sk.rdy.api.theoldreader.http.HttpManager;
import sk.rdy.api.theoldreader.http.RequestProperty;
import sk.rdy.api.theoldreader.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rdy on 10.7.2013.
 */
public abstract class GeneralApi {


    protected final HttpManager httpManager = HttpManager.getManager();
    protected final ArrayList<RequestProperty> properties;
    protected final ObjectMapper mapper;
    protected final String appName;

    protected String token;

    public GeneralApi(String appName){
        this.appName = appName;
        this.properties = new ArrayList<RequestProperty>();
        this.mapper = new ObjectMapper();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public abstract String signUp(String login, String password) throws TheOldReaderException;

    public abstract boolean isLogged();

    public abstract boolean isTokenValid();

    public abstract UserInformationJson getUserInfo();

    /**
     * Currently not supported
     * @return
     */
    public abstract String getPreferencesList();

    /**
     * Currently not supported
     * @return
     */
    public abstract String getFriendList();
    public abstract List<FolderJson> getFolders();
    public abstract boolean renameFolder(String sourceFolderId, String destFolderId);
    public abstract boolean removeFolder(String folderId);
    public abstract SubscriptionsJson getSubscriptions();
    public abstract String addSubscription(String url);
    public abstract boolean moveFeedToFolder(String feedId, String title);
    public abstract boolean renameFeedTitle(String feedId, String destFolderId);
    public abstract UnreadCountsJson getUnreadCounts();
    public abstract ItemsJson getItems(boolean onlyUnread, int limit, boolean reverseSorting);
    public abstract ItemsJson getItems(boolean onlyUnread, int limit, boolean reverseSorting, long continuation);
    public abstract ItemsJson getItemsNewerThan(boolean onlyUnread, int limit, boolean reverseSorting, long newerThanTimestamp);
    public abstract ItemsJson getItemsOlderThan(boolean onlyUnread, int limit, boolean reverseSorting, long olderThanTimestamp);
    public abstract ItemsJson getItemsFromFeedNewerThan(
            String feedId, boolean onlyUnread, int limit, boolean reverseSorting, long newerThanTimestamp);
    public abstract ItemsJson getItemsFromFeed(String feedId, boolean onlyUnread, int limit, boolean reverseSorting);
    public abstract ItemsJson getItemsFromFeed(String feedId, boolean onlyUnread, int limit, boolean reverseSorting, long continuation);
    public abstract ItemsJson getItemsFromFeedOlderThan(
            String feedId, boolean onlyUnread, int limit, boolean reverseSorting, long olderThanTimestamp);
    public abstract ItemContentsJson getItemContents(String[] itemIds);
    public abstract boolean unsubscribeSubscription(String id);
    public abstract boolean markReadAll();
    public abstract boolean markReadFolder(String folderId);
    public abstract boolean markReadFeed(String feedId);
    public abstract boolean markRead(String[] ids);
    public abstract boolean markUnread(String[] ids);
}
