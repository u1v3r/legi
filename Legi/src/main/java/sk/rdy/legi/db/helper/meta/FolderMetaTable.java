package sk.rdy.legi.db.helper.meta;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by rdy on 12.7.2013.
 */
public class FolderMetaTable implements IMetaTable {

    public final static String TABLE_NAME = "folder";
    public final static String COLUMN_ID = "_id";

    private final static String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " ( " +
            COLUMN_ID + " text PRIMARY KEY, " +
            "name text NOT NULL, " +
            "sortId text NOT NULL )";

    @Override
    public void createTable(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void dropTable(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }
}
