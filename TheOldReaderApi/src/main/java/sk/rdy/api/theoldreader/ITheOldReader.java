package sk.rdy.api.theoldreader;

/**
 * Created with IntelliJ IDEA.
 * User: Radovan Dvorsky
 * Date: 30.6.2013
 * Time: 18:23
 */
interface ITheOldReader{

    final static String AUTH_URL = "https://theoldreader.com/accounts/ClientLogin";
    final static String TOKEN_URL = "https://theoldreader.com/reader/api/0/token";
    final static String USER_INFO_URL = "https://theoldreader.com/reader/api/0/user-info?output=json";
    final static String PREFERENCE_LIST_URL = "https://theoldreader.com/reader/api/0/preference/list?output=json";
    final static String FRIEND_LIST_URL = "https://theoldreader.com/reader/api/0/friend/list?output=json";
    final static String FOLDERS_URL = "https://theoldreader.com/reader/api/0/tag/list?output=json";
    final static String FOLDER_RENAME_URL = "https://theoldreader.com/reader/api/0/rename-tag";
    final static String FOLDER_REMOVE_URL = "https://theoldreader.com/reader/api/0/disable-tag";
    final static String SUBSCRIPTIONS_URL = "https://theoldreader.com/reader/api/0/subscription/list?output=json";
    final static String SUBSCRIPTION_ADD_URL = "https://theoldreader.com/reader/api/0/subscription/quickadd?quickadd=";
    final static String SUBSCRIPTION_UPDATE_URL = "https://theoldreader.com/reader/api/0/subscription/edit";
    final static String UNREAD_COUNT_URL = "https://theoldreader.com/reader/api/0/unread-count?output=json";
    final static String ITEMS_URL = "https://theoldreader.com/reader/api/0/stream/items/ids?output=json";
    final static String ITEM_CONTENT = "https://theoldreader.com/reader/api/0/stream/items/contents?output=json";
    final static String STREAM_CONTENT = "https://theoldreader.com/reader/api/0/stream/contents?output=json";
    final static String ITEMS_UPDATE = "https://theoldreader.com/reader/api/0/edit-tag";
    final static String MARK_ALL_AS_READ_URL = "https://theoldreader.com/reader/api/0/mark-all-as-read";
    final static String REQUEST_SUCCESS = "OK";
}
