package sk.rdy.legi.db.model;

import sk.rdy.api.theoldreader.model.CategoryJson;
import sk.rdy.api.theoldreader.model.HasIdentifier;
import sk.rdy.api.theoldreader.model.SubscriptionsJson;
import sk.rdy.api.theoldreader.model.UnreadCountsJson;
import sk.rdy.legi.db.model.annotation.IgnoreAttr;

import java.util.ArrayList;
import java.util.List;

import static sk.rdy.api.theoldreader.model.UnreadCountsJson.UnreadItemCount;

/**
 * Created by rdy on 16.7.2013.
 */
public class Subscription implements IModel, HasIdentifier {

    private String id;
    private String title;
    @IgnoreAttr
    private List<Category> categories = new ArrayList<Category>();
    private String sortId;
    private Long firstItemMSec;
    private String url;
    private String htmlUrl;
    @IgnoreAttr
    private UnreadItemCount unreadCount;

    public Subscription() {}

    public Subscription(SubscriptionsJson.Subscription subscription){
        this.id = subscription.getId();
        this.title = subscription.getTitle();
        for(CategoryJson categoryJson : subscription.getCategories()){
            this.categories.add(new Category(categoryJson.getId(),categoryJson.getLabel()));
        }
        this.sortId = subscription.getSortId();
        this.firstItemMSec = Long.valueOf(subscription.getFirstitemmsec());
        this.url = subscription.getUrl();
        this.htmlUrl = subscription.getHtmlUrl();
    }

    /**
     *
     * @param id
     * @param title
     * @param categories
     * @param sortId
     * @param firstItemMSec
     * @param url
     * @param htmlUrl
     */
    public Subscription(String id, String title, List<Category> categories, String sortId, Long firstItemMSec,
                        String url, String htmlUrl) {
        this(id,title,categories,sortId,firstItemMSec,url,htmlUrl,null);
    }

    /**
     *
     * @param id
     * @param title
     * @param categories
     * @param sortId
     * @param firstItemMSec
     * @param url
     * @param htmlUrl
     * @param unreadCount
     */
    public Subscription(String id, String title, List<Category> categories, String sortId, Long firstItemMSec,
                        String url, String htmlUrl, UnreadItemCount unreadCount) {
        this.id = id;
        this.title = title;
        this.categories = categories;
        this.sortId = sortId;
        this.firstItemMSec = firstItemMSec;
        this.url = url;
        this.htmlUrl = htmlUrl;
        this.unreadCount = unreadCount;
    }

    private class Category {

        private String id;
        private String Label;

        public Category(){}

        public Category(String id, String label) {
            this.id = id;
            Label = label;
        }

        private String getId() {
            return id;
        }

        private void setId(String id) {
            this.id = id;
        }

        private String getLabel() {
            return Label;
        }

        private void setLabel(String label) {
            Label = label;
        }

        @Override
        public String toString() {
            return "Category{" +
                    "id='" + id + '\'' +
                    ", Label='" + Label + '\'' +
                    '}';
        }
    }

    @Override
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public String getSortId() {
        return sortId;
    }

    public void setSortId(String sortId) {
        this.sortId = sortId;
    }

    public Long getFirstItemMSec() {
        return firstItemMSec;
    }

    public void setFirstItemMSec(Long firstItemMSec) {
        this.firstItemMSec = firstItemMSec;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public UnreadItemCount getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(UnreadItemCount unreadCount) {
        this.unreadCount = unreadCount;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", categories=" + categories +
                ", sortId='" + sortId + '\'' +
                ", firstItemMSec='" + firstItemMSec + '\'' +
                ", url='" + url + '\'' +
                ", htmlUrl='" + htmlUrl + '\'' +
                '}';
    }
}
