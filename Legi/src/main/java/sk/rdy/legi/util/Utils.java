package sk.rdy.legi.util;

import android.app.ActionBar;
import android.app.Activity;
import android.widget.Toast;
import sk.rdy.legi.R;

/**
 * Created by rdy on 10.7.2013.
 */
public class Utils {

    public static void showMessage(final Activity context, final String msg) {
        if (msg == null || msg.trim().equals(""))
            return;

        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context.getBaseContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void setCustomTitleBarLayout(ActionBar actionBar){
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_HOME);
        actionBar.setDisplayOptions(0,ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setCustomView(R.layout.action_bar);
    }
}
