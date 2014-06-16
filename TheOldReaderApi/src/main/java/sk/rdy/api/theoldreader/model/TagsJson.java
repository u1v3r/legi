package sk.rdy.api.theoldreader.model;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Radovan Dvorsky
 * Date: 1.7.2013
 * Time: 18:07
 */
public class TagsJson {

    private ArrayList<FolderJson> tags;

    public ArrayList<FolderJson> getTags() {
        return tags;
    }

    public void setTags(ArrayList<FolderJson> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "TagsJson{" +
                "tags=" + tags +
                '}';
    }
}
