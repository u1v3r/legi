package sk.rdy.api.theoldreader.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created with IntelliJ IDEA.
 * User: Radovan Dvorsky
 * Date: 30.6.2013
 * Time: 18:30
 */
public class UserInformationJson {

    private String userId;
    private String userName;
    private String userProfileId;
    private String userEmail;
    @JsonProperty("isBloggerUser")
    private boolean isBloggerUser;
    private String signupTimeSec;
    @JsonProperty("isMultiLoginEnabled")
    private boolean isMultiLoginEnabled;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(String userProfileId) {
        this.userProfileId = userProfileId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public boolean isBloggerUser() {
        return isBloggerUser;
    }

    public void setBloggerUser(boolean bloggerUser) {
        isBloggerUser = bloggerUser;
    }

    public String getSignupTimeSec() {
        return signupTimeSec;
    }

    public void setSignupTimeSec(String signupTimeSec) {
        this.signupTimeSec = signupTimeSec;
    }

    public boolean isMultiLoginEnabled() {
        return isMultiLoginEnabled;
    }

    public void setMultiLoginEnabled(boolean multiLoginEnabled) {
        isMultiLoginEnabled = multiLoginEnabled;
    }

    @Override
    public String toString() {
        return "UserInformationJson{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userProfileId='" + userProfileId + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", isBloggerUser=" + isBloggerUser +
                ", signupTimeSec='" + signupTimeSec + '\'' +
                ", isMultiLoginEnabled=" + isMultiLoginEnabled +
                '}';
    }
}
