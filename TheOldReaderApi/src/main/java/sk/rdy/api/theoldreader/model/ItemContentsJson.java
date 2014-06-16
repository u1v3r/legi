package sk.rdy.api.theoldreader.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Radovan Dvorsky
 * Date: 2.7.2013
 * Time: 16:38
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemContentsJson {

    private String ltr;
    @JsonProperty("id")
    private String feedId;
    private String title;
    private String description;
    private long updated;
    private ArrayList<ItemContent> items;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ItemContent {
        private String id;
        private String title;
        private long published;
        private long updated;
        /*@JsonProperty("canonical")
        private String url;*/
        private Summary summary;
        private Origin origin;
        private String author;
        @JsonProperty("alternate")
        private ArrayList<Alternate> alternates;
        private ArrayList<String> categories;

        public static class Summary {
            private String direction;
            private String content;

            public String getDirection() {
                return direction;
            }

            private void setDirection(String direction) {
                this.direction = direction;
            }

            public String getContent() {
                return content;
            }

            private void setContent(String content) {
                this.content = content;
            }

            @Override
            public String toString() {
                return "Summary{" +
                        "direction='" + direction + '\'' +
                        ", content='" + content + '\'' +
                        '}';
            }
        }

        public static class Origin {
            @JsonProperty("streamId")
            private String feedId;
            private String title;
            @JsonProperty("htmlUrl")
            private String htmlUrl;

            public String getFeedId() {
                return feedId;
            }

            private void setFeedId(String feedId) {
                this.feedId = feedId;
            }

            public String getTitle() {
                return title;
            }

            private void setTitle(String title) {
                this.title = title;
            }

            public String getHtmlUrl() {
                return htmlUrl;
            }

            private void setHtmlUrl(String htmlUrl) {
                this.htmlUrl = htmlUrl;
            }

            @Override
            public String toString() {
                return "Origin{" +
                        "feedId='" + feedId + '\'' +
                        ", title='" + title + '\'' +
                        ", htmlUrl='" + htmlUrl + '\'' +
                        '}';
            }
        }

        public static class Alternate {
            private String href;
            private String type;

            public String getHref() {
                return href;
            }

            private void setHref(String href) {
                this.href = href;
            }

            public String getType() {
                return type;
            }

            private void setType(String type) {
                this.type = type;
            }

            @Override
            public String toString() {
                return "Alternate{" +
                        "href='" + href + '\'' +
                        ", type='" + type + '\'' +
                        '}';
            }
        }

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

        public long getPublished() {
            return published;
        }

        public void setPublished(long published) {
            this.published = published;
        }

        public long getUpdated() {
            return updated;
        }

        public void setUpdated(long updated) {
            this.updated = updated;
        }

        public Summary getSummary() {
            return summary;
        }

        public void setSummary(Summary summary) {
            this.summary = summary;
        }

        public Origin getOrigin() {
            return origin;
        }

        public void setOrigin(Origin origin) {
            this.origin = origin;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public ArrayList<Alternate> getAlternates() {
            return alternates;
        }

        public void setAlternates(ArrayList<Alternate> alternates) {
            this.alternates = alternates;
        }

        public ArrayList<String> getCategories() {
            return categories;
        }

        public void setCategories(ArrayList<String> categories) {
            this.categories = categories;
        }

        @Override
        public String toString() {
            return "ItemContent{" +
                    "id='" + id + '\'' +
                    ", title='" + title + '\'' +
                    ", published=" + published +
                    ", updated=" + updated +
                    ", summary=" + summary +
                    ", origin=" + origin +
                    ", author='" + author + '\'' +
                    ", alternates=" + alternates +
                    ", categories=" + categories +
                    '}';
        }
    }

    public String getLtr() {
        return ltr;
    }

    public void setLtr(String ltr) {
        this.ltr = ltr;
    }

    public String getFeedId() {
        return feedId;
    }

    public void setFeedId(String feedId) {
        this.feedId = feedId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getUpdated() {
        return updated;
    }

    public void setUpdated(long updated) {
        this.updated = updated;
    }

    public ArrayList<ItemContent> getItems() {
        return items;
    }

    public void setItems(ArrayList<ItemContent> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "ItemContentsJson{" +
                "ltr='" + ltr + '\'' +
                ", feedId='" + feedId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", updated=" + updated +
                ", items=" + items +
                '}';
    }
}
