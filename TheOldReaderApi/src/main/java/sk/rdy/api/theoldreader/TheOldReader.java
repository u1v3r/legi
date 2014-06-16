package sk.rdy.api.theoldreader;

import sk.rdy.api.GeneralApi;
import sk.rdy.api.theoldreader.exception.TheOldReaderException;
import sk.rdy.api.theoldreader.http.RequestProperty;
import sk.rdy.api.theoldreader.model.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Radovan Dvorsky
 * Date: 30.6.2013
 * Time: 18:19
 */
public class TheOldReader extends GeneralApi implements ITheOldReader {

    public static final String AUTH_REQUEST_PROPERTY = "Authorization";
    public static final int AUTH_REQUEST_PROPERTY_INDEX = 0;

    public TheOldReader(String appName) {
        super(appName);
    }

    public boolean isTokenValid() {
        String resultToken = httpManager.doGet(TOKEN_URL,"",properties);
        return resultToken.equals(token);
    }

    public String signUp(String login, String password) throws TheOldReaderException {
        String params =
                "client=" + this.appName +
                        "&accountType=HOSTED_OR_GOOGLE&service=reader&Email=" + login + "&Passwd=" + password +
                        "&output=json";

        try {
            TokenJson t = mapper.readValue(httpManager.doPost(AUTH_URL, params, this.properties),TokenJson.class);
            this.token = t.getAuth();
            this.properties.add(AUTH_REQUEST_PROPERTY_INDEX, new RequestProperty(AUTH_REQUEST_PROPERTY,"GoogleLogin auth=" + this.token));
            return this.token;
        } catch (Exception e) {
            throw new TheOldReaderException("Login Failed: Wrong username or password");
        }
    }

    @Override
    public void setToken(String token) {
        if(properties.size() == 0){
            this.properties.add(new RequestProperty(AUTH_REQUEST_PROPERTY,"GoogleLogin auth=" + token));
        }

        super.setToken(token);
    }

    public boolean isLogged() {
        return !token.isEmpty();
    }

    /**
     * Returns User information's
     *
     * @return UserInformationJson
     */
    public UserInformationJson getUserInfo() {

        UserInformationJson user = null;

        try {
            String result = httpManager.doGet(USER_INFO_URL, "", properties);

            if(result.isEmpty()) {
                return null;
            }

            user = mapper.readValue(result, UserInformationJson.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return user;
    }

    public String getPreferencesList() {
        return null;
    }

    public String getFriendList() {
        return null;
    }

    /**
     * All user folders
     *
     * @return ArrayList<FolderJson>
     */
    public ArrayList<FolderJson> getFolders() {

        try {
            String result = httpManager.doGet(FOLDERS_URL, "", properties);

            if(result.isEmpty()) return new ArrayList<FolderJson>();

            TagsJson tags =
                    mapper.readValue(result, TagsJson.class);

            return tags.getTags();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<FolderJson>();
    }

    /**
     * Rename folder
     *
     * @param srcId String
     * @param destId String
     *
     * @return boolean
     */
    public boolean renameFolder(String srcId, String destId) {

        String params = null;
        try {
            params = "s="+ URLEncoder.encode(srcId, "UTF-8")+"&dest="+URLEncoder.encode(destId,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return httpManager.doPost(FOLDER_RENAME_URL, params, properties).equals(REQUEST_SUCCESS);

        /* ASYNC
        httpManager.doAsyncPost(FOLDER_RENAME_URL,params,properties,new HttpResponseCallback() {
            public void onResponse(String result) {
                if(!result.equals(REQUEST_SUCCESS)){
                    // TODO: do some logging
                }
            }
        });
        */

    }

    /**
     * Remove folder
     *
     * @param folderId String
     * @return boolean
     */
    public boolean removeFolder(String folderId) {

        String params = null;
        try {
            params = "s="+ URLEncoder.encode(folderId, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return httpManager.doPost(FOLDER_REMOVE_URL, params, properties).equals(REQUEST_SUCCESS);

        /* ASYNC
        httpManager.doAsyncPost(FOLDER_REMOVE_URL,params,properties,new HttpResponseCallback() {
            public void onResponse(String result) {
                if(!result.equals(REQUEST_SUCCESS)){
                    // TODO: do some logging
                }
            }
        });
        */
    }

    /**
     * All subscriptions.
     *
     * Each subscription contains url, htmlUrl and iconUrl.
     *
     * @return SubscriptionsJson | null
     */
    public SubscriptionsJson getSubscriptions() {
        try {
            String result = httpManager.doGet(SUBSCRIPTIONS_URL, "", properties);

            if(result.isEmpty()) return null;

            SubscriptionsJson subscriptions =
                    mapper.readValue(result, SubscriptionsJson.class);

            return subscriptions;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Add new subscription
     *
     *
     * @param url String
     * @return String feedId
     */
    public String addSubscription(String url) {

        String result = httpManager.doPost(SUBSCRIPTION_ADD_URL + url, "", properties);
        try {
            Map<String,Object> resultMap = mapper.readValue(result, Map.class);
            return (String)resultMap.get("streamId");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * Rename feed title
     *
     * @param feedId String
     * @param title String
     * @return boolean
     */
    public boolean renameFeedTitle(String feedId, String title) {
        String params = null;
        try {
            params = "ac=edit&s=" + feedId + "&t=" + URLEncoder.encode(title,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return httpManager.doPost(SUBSCRIPTION_UPDATE_URL,params,properties).equals(REQUEST_SUCCESS);
    }

    /**
     * Return all unread counts
     *
     * @return UnreadCountsJson | null
     */
    @Override
    public UnreadCountsJson getUnreadCounts() {

        String result = httpManager.doGet(UNREAD_COUNT_URL, "", properties);
        try {
            return mapper.readValue(result, UnreadCountsJson.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ItemsJson getItems(boolean onlyUnread, int limit, boolean reverseSorting) {
        return _getItems(onlyUnread,limit,reverseSorting,0,0,0,"");
    }

    public ItemsJson getItems(boolean onlyUnread, int limit, boolean reverseSorting, long continuation) {
        return _getItems(onlyUnread,limit,reverseSorting,0,0,continuation,"");
    }

    public ItemsJson getItemsNewerThan(boolean onlyUnread, int limit, boolean reverseSorting, long newerThanTimestamp) {
        return _getItems(onlyUnread,limit,reverseSorting,newerThanTimestamp,0,0,"");
    }

    public ItemsJson getItemsOlderThan(boolean onlyUnread, int limit, boolean reverseSorting, long olderThanTimestamp) {
        return _getItems(onlyUnread,limit,reverseSorting,0,olderThanTimestamp,0,"");
    }

    public ItemsJson getItemsFromFeedNewerThan(String feedId, boolean onlyUnread, int limit, boolean reverseSorting, long newerThanTimestamp) {
        return _getItems(onlyUnread,limit,reverseSorting,newerThanTimestamp,0,0,feedId);
    }

    public ItemsJson getItemsFromFeed(String feedId, boolean onlyUnread, int limit, boolean reverseSorting) {
        return _getItems(onlyUnread,limit,reverseSorting,0,0,0,feedId);
    }

    public ItemsJson getItemsFromFeed(String feedId, boolean onlyUnread, int limit, boolean reverseSorting, long continuation) {
        return _getItems(onlyUnread,limit,reverseSorting,0,0,continuation,feedId);
    }

    public ItemsJson getItemsFromFeedOlderThan(String feedId, boolean onlyUnread, int limit, boolean reverseSorting, long olderThanTimestamp) {
        return _getItems(onlyUnread,limit,reverseSorting,0,olderThanTimestamp,0,feedId);
    }

    /**
     *
     * @param onlyUnread boolean
     * @param limit int
     * @param reverseSorting boolean
     * @param olderThan long
     * @param newerThan long
     * @param continuation long
     * @param feedId String
     * @return ItemsJson
     */
    private ItemsJson _getItems(boolean onlyUnread, int limit, boolean reverseSorting, long newerThan, long olderThan,
                            long continuation, String feedId) {

        StringBuilder builder = new StringBuilder();

        try {
            if(!feedId.isEmpty()){
                // only feed items
                builder.append("s=").append(URLEncoder.encode(feedId,"UTF-8"));
            } else {
                // all items
                builder.append("s=user/-/state/com.google/reading-list");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if(onlyUnread){
            // only unread
            builder.append("&xt=user/-/state/com.google/read");
        }

        if(limit > 0){
            builder.append("&n=").append(limit);
        }

        if(reverseSorting){
            builder.append("&r=o");
        }

        if(olderThan > 0){
            builder.append("&ot=").append(olderThan);
        }

        if(newerThan > 0){
            builder.append("&nt=").append(newerThan);
        }

        if(continuation > 0){
            builder.append("&c=").append(continuation);
        }

        String result = httpManager.doGet(ITEMS_URL,builder.toString(),properties);
        System.out.println("params: " + builder.toString());
        System.out.println(result);
        try {
            return mapper.readValue(result, ItemsJson.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ItemContentsJson getItemContents(String[] itemIds) {

        if(itemIds.length < 1) return null;

        StringBuilder strBuilder = new StringBuilder();


        for(String id : itemIds){
            strBuilder.append("i=").append(id).append("&");
        }

        String params = strBuilder.deleteCharAt(strBuilder.length()-1).toString();
        String result = httpManager.doPost(ITEM_CONTENT,params,properties);

        try {

            return mapper.readValue(result, ItemContentsJson.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean moveFeedToFolder(String feedId, String destFolderId){
        String params = null;
        try {
            params =
                    "ac=edit&s=" + feedId +
                    "&a=" + URLEncoder.encode(destFolderId,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String result = httpManager.doPost(SUBSCRIPTION_UPDATE_URL,params,properties);

        return true;
    }

    public boolean unsubscribeSubscription(String feedId) {
        String params = "ac=unsubscribe&s=" + feedId;
        return httpManager.doPost(SUBSCRIPTION_UPDATE_URL,params,properties).equals(REQUEST_SUCCESS);
    }

    public boolean markReadAll() {
        String params = "s=user/-/state/com.google/reading-list";
        httpManager.doPost(MARK_ALL_AS_READ_URL,params,properties);

        return true;
    }

    public boolean markReadFolder(String folderId) {
        String params = null;
        try {
            params = "s=" + URLEncoder.encode(folderId, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        httpManager.doPost(MARK_ALL_AS_READ_URL,params,properties);

        return true;
    }

    public boolean markReadFeed(String feedId) {
        String params = "s=" + feedId;
        httpManager.doPost(MARK_ALL_AS_READ_URL,params,properties);

        return true;
    }

    public boolean markRead(String[] ids) {

        if(ids.length <= 0) return false;

        httpManager.doPost(ITEMS_UPDATE,buildUrl(ids,true),properties);

        return true;
    }

    public boolean markUnread(String[] ids) {
        if(ids.length <= 0) return false;

        httpManager.doPost(ITEMS_UPDATE,buildUrl(ids,false),properties);

        return true;
    }

    private String buildUrl(String[] ids, boolean read) {
        StringBuilder builder = new StringBuilder();

        for (String id : ids){
            builder.append("i=").append(id).append("&");
        }

        if(read){
            // mark read
            builder.append("a=");
        }else {
            // mark unread
            builder.append("r=");
        }

        builder.append("user/-/state/com.google/read");

        return builder.toString();
    }
}
