package sk.rdy.api.theoldreader.model;

/**
 * Created with IntelliJ IDEA.
 * User: Radovan Dvorsky
 * Date: 1.7.2013
 * Time: 19:53
 */
public class CategoryJson {
    private String id;
    private String label;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "CategoryJson{" +
                "id='" + id + '\'' +
                ", label='" + label + '\'' +
                '}';
    }
}
