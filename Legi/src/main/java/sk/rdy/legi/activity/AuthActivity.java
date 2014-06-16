package sk.rdy.legi.activity;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import sk.rdy.legi.R;
import sk.rdy.legi.auth.TheOldReaderAuth;

import static sk.rdy.legi.auth.TheOldReaderAuth.AUTHTOKEN_TYPE_FULL_ACCESS;
import static sk.rdy.legi.auth.TheOldReaderAuth.oldReader;
import static sk.rdy.legi.util.Constants.DEBUG;

public class AuthActivity extends AccountAuthenticatorActivity {

    public final static String ARG_ACCOUNT_TYPE = "ACCOUNT_TYPE";
    public final static String ARG_AUTH_TYPE = "AUTH_TYPE";
    public final static String ARG_ACCOUNT_NAME = "ACCOUNT_NAME";
    public final static String ARG_IS_ADDING_NEW_ACCOUNT = "IS_ADDING_ACCOUNT";
    public final static String PARAM_USER_PASS = "USER_PASS";
    public final static String KEY_ERROR_MESSAGE = "ERR_MSG";

    private static final String TAG = AuthActivity.class.getName();

    private final int REQ_SIGNUP = 1;

    private AccountManager mAccountManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        mAccountManager = AccountManager.get(getBaseContext());

        String accountName = getIntent().getStringExtra(ARG_ACCOUNT_NAME);

        if (accountName != null) {
            ((TextView)findViewById(R.id.auth_email)).setText(accountName);
        }
    }

    public void onSignInClick(View v){

        final Editable email = ((EditText)findViewById(R.id.auth_email)).getText();
        final Editable pwd = ((EditText)findViewById(R.id.auth_password)).getText();
        final String accountType = TheOldReaderAuth.ACCOUNT_TYPE;

        if(email == null || pwd == null || email.length() < 1 || pwd.length() < 1){
            showEmailPwdNotSet(email,pwd);
            return;
        }

        if(DEBUG){
            Log.d(TAG,"signing in with email - " + email.toString());
        }

        new AsyncTask<String, Void, Intent>() {

            @Override
            protected Intent doInBackground(String... params) {

                if(DEBUG) Log.d(TAG,"> Started authenticating");

                String authtoken = null;
                Bundle data = new Bundle();
                try {
                    authtoken = oldReader.signUp(email.toString(), pwd.toString());

                    data.putString(AccountManager.KEY_ACCOUNT_NAME, email.toString());
                    data.putString(AccountManager.KEY_ACCOUNT_TYPE, accountType);
                    data.putString(AccountManager.KEY_AUTHTOKEN, authtoken);
                    data.putString(PARAM_USER_PASS, pwd.toString());

                } catch (Exception e) {
                    data.putString(KEY_ERROR_MESSAGE, e.getMessage());
                }

                final Intent res = new Intent();
                res.putExtras(data);
                return res;
            }

            @Override
            protected void onPostExecute(Intent intent) {
                if (intent.hasExtra(KEY_ERROR_MESSAGE)) {
                    Toast.makeText(getBaseContext(), intent.getStringExtra(KEY_ERROR_MESSAGE), Toast.LENGTH_SHORT).show();
                } else {
                    finishLogin(intent);
                }
            }
        }.execute();

    }

    private void finishLogin(Intent intent) {
        if(DEBUG) {
            Log.d(TAG, "Recieved token: " + intent.getStringExtra(AccountManager.KEY_AUTHTOKEN));
            Log.d(TAG," finishLogin");
        }

        String accountName = intent.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        String accountPassword = intent.getStringExtra(PARAM_USER_PASS);
        final Account account = new Account(accountName, intent.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE));

        String authtoken = intent.getStringExtra(AccountManager.KEY_AUTHTOKEN);


        // Creating the account on the device and setting the auth token we got
        // (Not setting the auth token will cause another call to the server to authenticate the user)
        mAccountManager.addAccountExplicitly(account, accountPassword, null);
        mAccountManager.setAuthToken(account, AUTHTOKEN_TYPE_FULL_ACCESS, authtoken);

        /*
        if (getIntent().getBooleanExtra(ARG_IS_ADDING_NEW_ACCOUNT, false)) {
            if(DEBUG) Log.d(TAG,"> finishLogin > addAccountExplicitly");
            String authtoken = intent.getStringExtra(AccountManager.KEY_AUTHTOKEN);


            // Creating the account on the device and setting the auth token we got
            // (Not setting the auth token will cause another call to the server to authenticate the user)
            mAccountManager.addAccountExplicitly(account, accountPassword, null);
            mAccountManager.setAuthToken(account, AUTHTOKEN_TYPE_FULL_ACCESS, authtoken);
        } else {
            if(DEBUG) Log.d(TAG,"> finishLogin > setPassword");
            String authtoken = intent.getStringExtra(AccountManager.KEY_AUTHTOKEN);


            // Creating the account on the device and setting the auth token we got
            // (Not setting the auth token will cause another call to the server to authenticate the user)
            mAccountManager.addAccountExplicitly(account, accountPassword, null);
            mAccountManager.setAuthToken(account, AUTHTOKEN_TYPE_FULL_ACCESS, authtoken);
        }
        */

        setAccountAuthenticatorResult(intent.getExtras());
        setResult(RESULT_OK, intent);
        startMainActivity();
    }

    private void startMainActivity() {
        Intent mainActivity = new Intent(this, MainActivity.class);
        mainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainActivity);
        finish();
    }

    private void showEmailPwdNotSet(Editable email, Editable pwd) {
        if(DEBUG){
            Log.d(TAG,"email or password not set");
        }

        if(email == null){
            ((EditText)findViewById(R.id.auth_email)).setSelected(true);
        }else{
            ((EditText)findViewById(R.id.auth_password)).setSelected(true);
        }

    }
}