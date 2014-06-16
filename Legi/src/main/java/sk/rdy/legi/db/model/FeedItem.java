package sk.rdy.legi.db.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import sk.rdy.api.theoldreader.model.ItemContentsJson;
import sk.rdy.api.theoldreader.model.ItemsJson;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by rdy on 22.7.2013.
 */
public class FeedItem implements IModel{

    private static final String TYPE_HTML_TEXT = "text/html";
    private String id;
    private long timestampUsec;
    private String title;
    private long updated;
    private long published;
    private String contentUrl;
    private String feedRssAtomUrl;
    private String author;
    private String content;

    public FeedItem(){}

    /**
     *
     * @param itemContent
     * @param itemJson
     */
    public FeedItem(ItemContentsJson.ItemContent itemContent, ItemsJson.Item itemJson){
        this.id = itemJson.getId();
        this.timestampUsec = itemJson.getTimestampUsec();
        this.title = itemContent.getTitle();
        this.updated = itemContent.getUpdated();
        this.published = itemContent.getPublished();
        for (ItemContentsJson.ItemContent.Alternate alternate : itemContent.getAlternates()){
            if(alternate.getType().equals(TYPE_HTML_TEXT)){
                this.contentUrl = alternate.getHref();
                break;
            }
        }
        this.feedRssAtomUrl = itemContent.getOrigin().getHtmlUrl();
        this.author = itemContent.getAuthor();
        this.content = itemContent.getSummary().getContent();
    }

    /**
     *
     * @param id
     * @param timestampUsec
     * @param title
     * @param updated
     * @param published
     * @param contentUrl
     * @param feedRssAtomUrl
     * @param author
     * @param content
     */
    public FeedItem(String id, long timestampUsec, String title, long updated, long published,
                    String contentUrl, String feedRssAtomUrl, String author, String content) {
        this.id = id;
        this.timestampUsec = timestampUsec;
        this.title = title;
        this.updated = updated;
        this.published = published;
        this.contentUrl = contentUrl;
        this.feedRssAtomUrl = feedRssAtomUrl;
        this.author = author;
        this.content = content;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTimestampUsec() {
        return timestampUsec;
    }

    public void setTimestampUsec(long timestampUsec) {
        this.timestampUsec = timestampUsec;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getUpdated() {
        return updated;
    }

    public void setUpdated(long updated) {
        this.updated = updated;
    }

    public long getPublished() {
        return published;
    }

    public void setPublished(long published) {
        this.published = published;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public String getFeedRssAtomUrl() {
        return feedRssAtomUrl;
    }

    public void setFeedRssAtomUrl(String feedRssAtomUrl) {
        this.feedRssAtomUrl = feedRssAtomUrl;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "FeedItem{" +
                "id='" + id + '\'' +
                ", timestampUsec=" + timestampUsec +
                ", title='" + title + '\'' +
                ", updated=" + updated +
                ", published=" + published +
                ", contentUrl='" + contentUrl + '\'' +
                ", feedRssAtomUrl='" + feedRssAtomUrl + '\'' +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
