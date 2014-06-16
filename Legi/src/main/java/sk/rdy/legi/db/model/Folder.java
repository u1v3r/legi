package sk.rdy.legi.db.model;

import sk.rdy.api.theoldreader.model.*;
import sk.rdy.api.theoldreader.model.UnreadCountsJson;
import sk.rdy.api.theoldreader.model.UnreadCountsJson;
import sk.rdy.legi.db.model.annotation.IgnoreAttr;

import java.util.ArrayList;
import java.util.List;

import static sk.rdy.api.theoldreader.model.UnreadCountsJson.UnreadItemCount;

/**
 * Created by rdy on 12.7.2013.
 */
public class Folder implements IModel, HasIdentifier{

    private String id;
    private String sortId;
    private String name;

    @IgnoreAttr
    private UnreadItemCount unreadCount;

    @IgnoreAttr
    private List<Subscription> subscriptions = new ArrayList<Subscription>();

    public Folder(){}

    /**
     *
     * @param id
     * @param sortId
     * @param name
     */
    public Folder(String id, String sortId, String name) {
        this(id,sortId,name,null);
    }

    /**
     *
     * @param id
     * @param sortId
     * @param name
     * @param unreadCount
     */
    public Folder(String id, String sortId, String name, UnreadItemCount unreadCount) {
        this.id = id;
        this.sortId = sortId;
        this.name = name;
        this.unreadCount = unreadCount;
    }

    /**
     *
     * @param folder
     */
    public Folder(FolderJson folder){
        this.id = folder.getId();
        this.sortId = folder.getSortId();
        this.name = convertIdToName(folder.getId());
    }

    private String convertIdToName(String id){
        return id.substring(id.lastIndexOf("/") + 1);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;

    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSortId() {
        return sortId;
    }

    public void setSortId(String sortId) {
        this.sortId = sortId;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public UnreadItemCount getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(UnreadItemCount unreadCount) {
        this.unreadCount = unreadCount;
    }

    @Override
    public String toString() {
        return "Folder{" +
                "id='" + id + '\'' +
                ", sortId='" + sortId + '\'' +
                ", name='" + name + '\'' +
                ", subscriptions=" + subscriptions +
                '}';
    }
}
