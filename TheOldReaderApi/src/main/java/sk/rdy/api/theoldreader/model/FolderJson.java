package sk.rdy.api.theoldreader.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created with IntelliJ IDEA.
 * User: Radovan Dvorsky
 * Date: 30.6.2013
 * Time: 18:36
 */
public class FolderJson {

    private String id;
    @JsonProperty("sortid")
    private String sortId;

    public String getSortId() {
        return sortId;
    }

    public void setSortId(String sortId) {
        this.sortId = sortId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "FolderJson{" +
                "id='" + id + '\'' +
                ", sortId='" + sortId + '\'' +
                '}';
    }
}
