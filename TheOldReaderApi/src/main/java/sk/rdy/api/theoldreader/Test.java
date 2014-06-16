package sk.rdy.api.theoldreader;

/**
 * Created with IntelliJ IDEA.
 * User: Radovan Dvorsky
 * Date: 30.6.2013
 * Time: 21:01
 */
public class Test {

    public static void main(String [] args){
        /*
        HttpManager manager = HttpManager.getManager();


        List<RequestProperty> properties = new ArrayList<RequestProperty>();
        properties.add(new RequestProperty("Authorization","GoogleLogin auth="));
            manager.doGet("https://theoldreader.com/reader/api/0/token?output=json","", properties, new HttpResponseCallback() {
            public void onResponse(String result) {
                System.out.println(result);
            }
        });
        */
    /*
        try {
            // login
            System.out.println("---------------login---------------");
            TheOldReader api = new TheOldReader("TestApp");
            api.signUp("@gmail.com","");

            // user info
            System.out.println("---------------user info---------------");
            System.out.println(api.getUserInfo().toString());

            // folders
            System.out.println("---------------Folders---------------");
            ArrayList<FolderJson> folders = api.getFolders();
            System.out.println(folders);

            // rename folder
            // api.renameFolder(folders.get(0).getId(),folders.get(0).getId() + " pridane");
            // System.out.println(api.getFolders());

            // subscriptions list
            System.out.println("---------------subscriptions list---------------");
            System.out.println(api.getSubscriptions());


            // add subscription
            System.out.println("---------------add subscription---------------");
            String feedId = api.addSubscription("blog.theoldreader.com");
            System.out.println(feedId);
            String newFeedId = api.addSubscription("www.zive.cz/rss/sc-47/default.aspx");
            System.out.println(newFeedId);

            // remove subscription
            System.out.println("---------------remove subscription---------------");
            System.out.println(api.unsubscribeSubscription(feedId));

            // rename subscription
            System.out.println("---------------change feed title---------------");
            System.out.println(api.renameFeedTitle(newFeedId,"Toto je zive"));

            // ItemsJson
            System.out.println("---------------All items---------------");
            ItemsJson items = api.getItems(true, 100, false);
            System.out.println(items);

            System.out.println("---------------FolderJson items---------------");
            ItemsJson folderItems = api.getItemsFromFolder(folders.get(1).getId(),true,100,false);
            System.out.println(folderItems);

            System.out.println("---------------Feed items---------------");
            ItemsJson feedItems = api.getItemsFromFeed(newFeedId,false,100,true);
            System.out.println(feedItems);

            System.out.println("---------------Item contents---------------");
            String[] ids = new String[feedItems.getItemRefs().size()];
            int i = 0;
            for (ItemsJson.Item item: feedItems.getItemRefs()){
                ids[i++] = item.getId();
            }
            ItemContentsJson itemContents = api.getItemContents(ids);
            System.out.println(itemContents);

        } catch (TheOldReaderException e) {
            e.printStackTrace();
        }
*/
    }
}
