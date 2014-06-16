package sk.rdy.legi.activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import sk.rdy.api.theoldreader.model.*;
import sk.rdy.legi.R;
import sk.rdy.legi.adapter.FeedListAdapter;
import sk.rdy.legi.adapter.FeedStatePagerAdapter;
import sk.rdy.legi.adapter.FoldersExpandableAdapter;
import sk.rdy.legi.async.Tasks;
import sk.rdy.legi.db.model.FeedItem;
import sk.rdy.legi.db.model.Folder;
import sk.rdy.legi.fragment.FeedListFragment;
import sk.rdy.legi.listener.OnAuthFinishedListener;
import sk.rdy.legi.util.Utils;
import sk.rdy.legi.widget.TypeFacedTextView;

import java.util.ArrayList;
import java.util.List;

import static sk.rdy.legi.auth.TheOldReaderAuth.*;
import static sk.rdy.legi.util.Constants.*;

public class MainActivity extends FragmentActivity implements OnAuthFinishedListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private AccountManager accountManager;
    private PopupWindow popupWindow;
    private TypeFacedTextView actionBarTitle;
    private ExpandableListView mDrawerExpandableList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private List<Folder> mDrawerFoldersList;
    private FoldersExpandableAdapter mFoldersAdapter;
    private FeedStatePagerAdapter mFeedStatePagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(DEBUG) Log.d(TAG,"onCreate");

        initActionBar();


        accountManager = AccountManager.get(this);
        final Account[] accounts = accountManager != null ? accountManager.getAccountsByType(ACCOUNT_TYPE) : new Account[0];


        if(accounts.length == 0){
            startAuthActivity();
        }else {

            initNavigationDrawer();

            if (accounts.length == 1){
                // Try authenticate
                authUser(accounts[0],this);
            }else {
                // Show account picker and try authenticate user
                showAccountPickerDialog(accounts,this);
            }
        }
    }

    private void initNavigationDrawer() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);
        mDrawerExpandableList = (ExpandableListView) findViewById(R.id.acitivty_main_left_drawer);
        mDrawerFoldersList = new ArrayList<Folder>();

        mFoldersAdapter = new FoldersExpandableAdapter(this, mDrawerFoldersList);

        // Transparent overlay for content
        // mDrawerLayout.setScrimColor(getResources().getColor(R.color.transparent));

        // Set header for ListView
        mDrawerExpandableList.addHeaderView(View.inflate(this, R.layout.drawer_list_header, null));
        // mDrawerExpandableList.addFooterView(View.inflate(this,R.layout.drawer_list_footer,null));

        // Set the adapter for the list view
        mDrawerExpandableList.setAdapter(mFoldersAdapter);

        // Set the list's click listener
        mDrawerExpandableList.setOnItemClickListener(new DrawerItemClickListener());


        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_actionbar_toggle,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                if(DEBUG) Log.d(TAG,"NavDrawerClosed");
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                if(DEBUG) Log.d(TAG,"NavDrawerOpen");
            }
        };


        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void initNavigationDrawerData() {
        Tasks.syncNavigationDrawerData(new Tasks.TaskCallback<List<Folder>>() {

            @Override
            public void onAsyncTaskFinished(List<Folder> data) {
                if(DEBUG) Log.d(TAG,"foldersPostExecute: " + data.toString());

                mDrawerFoldersList.addAll(data);
                mFoldersAdapter.notifyDataSetChanged();
            }
        });
    }


    private void initActionBar() {
        final ActionBar actionBar = getActionBar();
        Utils.setCustomTitleBarLayout(actionBar);
        actionBarTitle = (TypeFacedTextView)findViewById(R.id.action_bar_title);
    }

    private void initAppContent() {

        if(DEBUG){
            Log.d(TAG,"starting main activity");
        }

        actionBarTitle.setText("Latest");

        mFeedStatePagerAdapter = new FeedStatePagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager)findViewById(R.id.activity_main_pager);
        mViewPager.setAdapter(mFeedStatePagerAdapter);
    }

    private void authUser(Account account, OnAuthFinishedListener listener) {
        getExistingAccountAuthToken(account,AUTHTOKEN_TYPE_FULL_ACCESS,listener);
    }

    @Override
    public void authFinished() {
        initNavigationDrawerData();
        initAppContent();
    }

    private void showAccountPickerDialog(final Account[] accounts, final OnAuthFinishedListener listener) {
        String names[] = new String[accounts.length];
        for (int i = 0; i < accounts.length; i++) {
            names[i] = accounts[i].name;
        }

        // Account picker
        new AlertDialog.Builder(this).setTitle("Pick Account").setAdapter(
                new ArrayAdapter<String>(getBaseContext(),
                android.R.layout.simple_list_item_1, names),
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getExistingAccountAuthToken(accounts[i], AUTHTOKEN_TYPE_FULL_ACCESS,listener);
                    }
                }).show();
    }

    private void getExistingAccountAuthToken(final Account account, String authTokenType, final OnAuthFinishedListener listener) {
        final AccountManagerFuture<Bundle> future = accountManager.getAuthToken(
                account, authTokenType, null, this, null, null);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Bundle bnd = future.getResult();
                    final String authtoken = bnd.getString(AccountManager.KEY_AUTHTOKEN);

                    if(DEBUG) Log.d(TAG, "Authtoken is " + authtoken);

                    oldReader.setToken(authtoken);

                    if(listener != null){
                        listener.authFinished();
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        }).start();
    }

    private void startAuthActivity() {
        Intent authActivity = new Intent(this, AuthActivity.class);
        authActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(authActivity);
        finish();
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
       mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            selectItem(position);
        }
    }

    /**
     *  Change content in the main window
     */
    private void selectItem(int position) {
        if (DEBUG) Log.d(TAG,"NavDrawer click: " + position);

        actionBarTitle.setText("je tu: " + position);
    }

}
