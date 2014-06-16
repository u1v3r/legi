package sk.rdy.api.theoldreader.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Radovan Dvorsky
 * Date: 2.7.2013
 * Time: 15:54
 */
public class ItemsJson {

    @JsonProperty("itemRefs")
    private ArrayList<Item> itemRefs;
    private long continuation;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Item {

        private String id;
        /*@JsonProperty("directStreamIds")
        private ArrayList<String> directStreamIds;*/
        @JsonProperty("timestampUsec")
        private long timestampUsec;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
        /*
            public ArrayList<String> getDirectStreamIds() {
                return directStreamIds;
            }

            public void setDirectStreamIds(ArrayList<String> directStreamIds) {
                this.directStreamIds = directStreamIds;
            }
        */
        public long getTimestampUsec() {
            return timestampUsec;
        }

        public void setTimestampUsec(long timestampUsec) {
            this.timestampUsec = timestampUsec;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "id='" + id + '\'' +
                    ", timestampUsec=" + timestampUsec +
                    '}';
        }
    }

    public ArrayList<Item> getItemRefs() {
        return itemRefs;
    }

    public void setItemRefs(ArrayList<Item> itemRefs) {
        this.itemRefs = itemRefs;
    }

    public long getContinuation() {
        return continuation;
    }

    public void setContinuation(long continuation) {
        this.continuation = continuation;
    }

    @Override
    public String toString() {
        return "ItemsJson{" +
                "itemRefs=" + itemRefs +
                ", continuation=" + continuation +
                '}';
    }
}
