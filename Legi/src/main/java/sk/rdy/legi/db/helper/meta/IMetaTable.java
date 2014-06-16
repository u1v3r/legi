package sk.rdy.legi.db.helper.meta;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by rdy on 12.7.2013.
 */
public interface IMetaTable {

    void createTable(SQLiteDatabase sqLiteDatabase);
    void dropTable(SQLiteDatabase sqLiteDatabase);
}
