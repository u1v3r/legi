package sk.rdy.legi.db.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import sk.rdy.legi.db.helper.meta.FolderMetaTable;
import sk.rdy.legi.db.helper.meta.IMetaTable;
import sk.rdy.legi.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rdy on 12.7.2013.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "legiDb";
    private static final int DATABASE_VERSION = 6;
    private static final String TAG = MySQLiteHelper.class.getSimpleName();

    private static List<IMetaTable> tables = new ArrayList<IMetaTable>();
    static {
        tables.add(new FolderMetaTable());
    }

    public MySQLiteHelper(Context context){
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        if(Constants.DEBUG) Log.d(TAG, "creating database tables");

        for (IMetaTable table : tables){
            table.createTable(sqLiteDatabase);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        if(Constants.DEBUG) Log.w(TAG,"Upgrading database from version " + oldVersion + " to "
                  + newVersion + ", which will destroy all old data");

        for (IMetaTable table : tables){
            table.dropTable(sqLiteDatabase);
        }

        onCreate(sqLiteDatabase);
    }
}
