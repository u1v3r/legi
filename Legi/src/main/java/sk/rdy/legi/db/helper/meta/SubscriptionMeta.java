package sk.rdy.legi.db.helper.meta;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by rdy on 16.7.2013.
 */
public class SubscriptionMeta implements IMetaTable {

    public final static String TABLE_NAME = "subscription";
    public final static String COLUMN_ID = "_id";

    private final static String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " ( " +
                    COLUMN_ID + " text PRIMARY KEY, " +
                    "title text NOT NULL, " +
                    "sortId text NOT NULL, " +
                    "firstItemMSec INTEGER NOT NULL, " +
                    "url text NOT NULL, " +
                    "htmlUrl text NOT NULL)";

    /**
     * Folder to Subscription join table
     */
    private final static String CREATE_JOIN_TABLE =
            "CREATE TABLE " + FolderMetaTable.TABLE_NAME + "_" + TABLE_NAME + " ( " +
                    "folderId text NOT NULL, " +
                    "subscriptionId text NOT NULL)";

    @Override
    public void createTable(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
        sqLiteDatabase.execSQL(CREATE_JOIN_TABLE);
    }

    @Override
    public void dropTable(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CREATE_JOIN_TABLE);
    }
}
