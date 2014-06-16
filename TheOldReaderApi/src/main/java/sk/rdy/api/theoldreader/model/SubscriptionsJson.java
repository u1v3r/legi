package sk.rdy.api.theoldreader.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Radovan Dvorsky
 * Date: 1.7.2013
 * Time: 19:52
 */
public class SubscriptionsJson {

    private ArrayList<Subscription> subscriptions;

    public ArrayList<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public static class Subscription implements HasIdentifier{
        private String id;
        private String title;
        private List<CategoryJson> categories;
        @JsonProperty("sortid")
        private String sortId;
        private String firstitemmsec;
        private String url;
        private String htmlUrl;
        private String iconUrl;

        public String getId() {
            return id;
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

        public List<CategoryJson> getCategories() {
            return categories;
        }

        public void setCategories(List<CategoryJson> categories) {
            this.categories = categories;
        }

        public String getSortId() {
            return sortId;
        }

        public void setSortId(String sortId) {
            this.sortId = sortId;
        }

        public String getFirstitemmsec() {
            return firstitemmsec;
        }

        public void setFirstitemmsec(String firstitemmsec) {
            this.firstitemmsec = firstitemmsec;
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

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        @Override
        public String toString() {
            return "Subscription{" +
                    "id='" + id + '\'' +
                    ", title='" + title + '\'' +
                    ", categories=" + categories +
                    ", sortId='" + sortId + '\'' +
                    ", firstitemmsec='" + firstitemmsec + '\'' +
                    ", url='" + url + '\'' +
                    ", htmlUrl='" + htmlUrl + '\'' +
                    ", iconUrl='" + iconUrl + '\'' +
                    '}';
        }
    }

    public void setSubscriptions(ArrayList<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    @Override
    public String toString() {
        return "SubscriptionsJson{" +
                "subscriptions=" + subscriptions +
                '}';
    }
}
