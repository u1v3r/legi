package sk.rdy.legi.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import sk.rdy.legi.db.helper.MySQLiteHelper;
import sk.rdy.legi.db.helper.meta.IMetaTable;
import sk.rdy.legi.db.model.IModel;
import sk.rdy.legi.db.model.annotation.IgnoreAttr;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rdy on 12.7.2013.
 */
public abstract class AbstractDao<T extends IModel, U extends IMetaTable> {

    private static final String TAG = AbstractDao.class.getSimpleName();
    private final String modelSimpleName;
    private final Class<U> tableMetaClass;
    private String tableColumnIdName;
    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String tableName;
    private String[] tableColumns;
    private Class<T> modelClass;
    private Field[] modelFields;

    /**
     *
     * @param context
     * @param modelClazz
     * @param tableMetaClazz
     */
    public AbstractDao(Context context, Class<T> modelClazz, Class<U> tableMetaClazz) {
        modelClass = modelClazz;
        dbHelper = new MySQLiteHelper(context);
        modelSimpleName = modelClazz.getSimpleName();
        tableName = modelSimpleName.toLowerCase();
        Field[] modelFieldsTmp = modelClazz.getDeclaredFields();


        tableMetaClass = tableMetaClazz;

        // Ignore annotated fields
        String modelFieldName;
        for (int i = 0; i < modelFieldsTmp.length ; i++){
            if(modelFieldsTmp[i].getAnnotation(IgnoreAttr.class) != null) continue;

            modelFields[i] = modelFieldsTmp[i];
            modelFieldName = modelFieldsTmp[i].getName();
            if(modelFieldName.equals("id")){
                modelFieldName = "_id";
            }
            tableColumns[i] = modelFieldName;
        }

        tableColumns = new String[modelFields.length];

        try {
            tableColumnIdName =
                    (String)tableMetaClazz.getDeclaredField("COLUMN_ID").get(null);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public T get(T model){
        openToRead();
        String id = model.getId();
        Cursor cursor =
                database.query(tableName,tableColumns, tableColumnIdName + " = ?",new String[]{id},null,null,null);

        if(cursor.getCount() != 1) return null;

        T data = cursorToModel(cursor);
        close();
        cursor.close();

        return data;
    }


    public T create(T model){
        openToWrite();

        try {
            database.insert(tableName, null, modelToContentValues(model));
            Cursor cursor = database.query(tableName,
                    tableColumns, tableColumnIdName + " = " + "'" + model.getId() + "'", null,
                    null, null, null);
            cursor.moveToFirst();
            T newComment = cursorToModel(cursor);
            cursor.close();
            return newComment;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            close();
        }

        return null;
    }



    public int delete(T model){
        openToWrite();
        String id = model.getId();
        int count = database.delete(tableName, tableColumnIdName
                + " = " + "'" + id + "'", null);

        close();

        return count;
    }

    public int update(T model){
        openToWrite();
        int count = 0;

        try {
            count = database.update(
                        tableName,
                        modelToContentValues(model),
                        tableColumnIdName + " = ?",
                        new String[]{model.getId()}
            );

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }finally {
            close();
        }


        return count;
    }

    public List<T> getAll(String where, String[] whereArgs){
        openToRead();
        List<T> models = new ArrayList<T>();

        Cursor cursor = database.query(tableName,
                tableColumns, where, whereArgs, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            T model = cursorToModel(cursor);
            models.add(model);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        close();
        return models;
    }

    public List<T> getAll() {
        return getAll(null,null);
    }

    protected void openToWrite() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    protected void openToRead() throws SQLException {
        database = dbHelper.getReadableDatabase();
    }

    protected void close() {
        dbHelper.close();
    }

    protected ContentValues modelToContentValues(T model)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        String fieldName;
        ContentValues values = new ContentValues();
        Method contentValuesPutMethod;
        Method modelGetMethod;

        for (Field field : modelFields){
            fieldName = field.getName();


            contentValuesPutMethod = values.getClass().getMethod("put",String.class,field.getType());
            Log.d(TAG,contentValuesPutMethod.getName());
            modelGetMethod = modelClass.getMethod("get" + upperFirstLetter(fieldName), null);
            Log.d(TAG, modelGetMethod.getName());
            if(fieldName.equals("id")){
                fieldName = "_id";
            }

            contentValuesPutMethod.invoke(values,fieldName, modelGetMethod.invoke(model,null));
        }

        return values;
    }

    protected T cursorToModel(Cursor cursor) {
        T model;
        Method cursorGetMethod, modelSetMethod;
        String fieldName;
        try {
            model = modelClass.newInstance();

            // set all model fields
            for(Field field : modelFields){
                fieldName = field.getName();

                // cursor get* method
                cursorGetMethod = cursor.getClass().getMethod(typeToGetMethod(field.getType()),int.class);

                // model set* method
                modelSetMethod = modelClass.getMethod(
                        "set" + upperFirstLetter(fieldName),
                        field.getType()
                );

                if(fieldName.equals("id")){
                    fieldName = "_id";
                }

                // set model field from cursor
                modelSetMethod.invoke(model,cursorGetMethod.invoke(cursor,cursor.getColumnIndex(fieldName)));
            }

            return model;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String upperFirstLetter(String text){
        return text.replaceFirst("[a-z]{1}", text.substring(0, 1).toUpperCase());
    }

    private String typeToGetMethod(Class<?> type) {

        String method;

        if(type.equals(Integer.TYPE)){
            method = "getInt";
        }else if(type.equals(Long.TYPE)){
            method = "getLong";
        }else if(type.equals(Double.TYPE)){
            method = "getDouble";
        }else if(type.equals(Float.TYPE)){
            method = "getFloat";
        }else if(type.equals(Short.TYPE)){
            method = "getShort";
        }else{
            method = "getString";
        }

        return method;
    }
}
