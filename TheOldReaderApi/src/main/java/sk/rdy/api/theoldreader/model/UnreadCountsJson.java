package sk.rdy.api.theoldreader.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by rdy on 12.7.2013.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UnreadCountsJson {

    private int max;

    @JsonProperty("unreadcounts")
    private ArrayList<UnreadItemCount> unreadItemCounts;

    public static class UnreadItemCount{
        private String id;
        private long count;
        @JsonProperty("newestItemTimestampUsec")
        private long newestItemTimestampUsec;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public long getCount() {
            return count;
        }

        public void setCount(long count) {
            this.count = count;
        }

        public long getNewestItemTimestampUsec() {
            return newestItemTimestampUsec;
        }

        public void setNewestItemTimestampUsec(long newestItemTimestampUsec) {
            this.newestItemTimestampUsec = newestItemTimestampUsec;
        }

        @Override
        public String toString() {
            return "UnreadItemCount{" +
                    "id='" + id + '\'' +
                    ", count=" + count +
                    ", newestItemTimestampUsec=" + newestItemTimestampUsec +
                    '}';
        }
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public ArrayList<UnreadItemCount> getUnreadItemCounts() {
        return unreadItemCounts;
    }

    public void setUnreadItemCounts(ArrayList<UnreadItemCount> unreadItemCounts) {
        this.unreadItemCounts = unreadItemCounts;
    }

    @Override
    public String toString() {
        return "UnreadCountsJson{" +
                "max=" + max +
                ", unreadItemCounts=" + unreadItemCounts +
                '}';
    }
}
