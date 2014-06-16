package sk.rdy.legi.auth;

import sk.rdy.api.theoldreader.TheOldReader;
import sk.rdy.legi.util.Constants;

/**
 * Created by rdy on 10.7.2013.
 */
public class TheOldReaderAuth {

    /**
     * Account type id
     */
    public static final String ACCOUNT_TYPE = "sk.rdy.legi.theoldreader";

    /**
     * Account name
     */
    public static final String ACCOUNT_NAME = Constants.APP_NAME;

    /**
     * Auth token types
     */
    public static final String AUTHTOKEN_TYPE_READ_ONLY = "Read only";
    public static final String AUTHTOKEN_TYPE_READ_ONLY_LABEL = "Read only access to an Legi account";

    public static final String AUTHTOKEN_TYPE_FULL_ACCESS = "Full access";
    public static final String AUTHTOKEN_TYPE_FULL_ACCESS_LABEL = "Full access to an Legi account";

    /**
     * The Old Reader Api
     */
    public static final TheOldReader oldReader = new TheOldReader(Constants.APP_NAME);
}
