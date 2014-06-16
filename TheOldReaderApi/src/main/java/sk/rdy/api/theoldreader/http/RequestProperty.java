package sk.rdy.api.theoldreader.http;

/**
 * Created with IntelliJ IDEA.
 * User: Radovan Dvorsky
 * Date: 30.6.2013
 * Time: 22:34
 */
public class RequestProperty {

    private String key;
    private String value;

    public RequestProperty(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
