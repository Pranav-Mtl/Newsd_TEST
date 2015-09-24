package com.android.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * Created by Balram on 5/19/2015.
 */
public class DBOperation implements Serializable {

    String DB_TABLE;
    int DB_VERSION = 1;
    static String[] DATABASE_CREATE;
    private Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;
    public static String TAG = "GCM DB";



    public DBOperation(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }



    public void createAndInitializeTables() {
        try {
            ChatPeopleBE mytable = new ChatPeopleBE();
            String[] tableCreateArray = { mytable.getDatabaseCreateQuery(),mytable.getDatabaseCreateQueryIndia(),mytable.getDatabaseCreateQueryWorld(),mytable.getDatabaseCreateQueryTrending(),mytable.getDatabaseCreateQueryHumour(),mytable.getDatabaseCreateQueryEconomy(),mytable.getDatabaseCreateQueryBussiness(),mytable.getDatabaseCreateQueryTech(),mytable.getDatabaseCreateQuerySport(),mytable.getDatabaseCreateQueryEntertainment(),mytable.getDatabaseCreateQueryLifestyle(),mytable.getDatabaseCreateQuerySpecial(),mytable.getDatabaseCreateQueryPeople()};
            DBOperation operation = new DBOperation(context, tableCreateArray);
            operation.open();
            operation.close();
            Log.i(TAG, "DB Created");
        } catch (Exception e) {
            Log.d(TAG, "Error creating table " + e.getMessage());
        }
    }

    public DBOperation(Context ctx, String[] query) {
        this.context = ctx;
        DATABASE_CREATE = query;
        DBHelper = new DatabaseHelper(context);
    }

    public Cursor getTableRow(String tablename, String[] dbFields,
                              String condition, String order, String limit) throws SQLException {
        DB_TABLE = tablename;
        Cursor mCursor = db.query(false, DB_TABLE, dbFields, condition, null,
                null, null, order, limit);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }



    public Cursor getDataFromTable() {

            DBOperation operationObj = new DBOperation(context);
            operationObj.open();
            ChatPeopleBE peopleTable = new ChatPeopleBE();
            String condition = "";

            String[] dbFields = {peopleTable.getNewdID(), peopleTable.getNewsTitle(),
                    peopleTable.getNewsContent(),
                    peopleTable.getNewsImage(), peopleTable.getNewsLink(), peopleTable.getNewsPublisher(), peopleTable.getNewsTag(), peopleTable.getNewsFollow(), peopleTable.getNewsBookmark()};
            Cursor cursor = operationObj.getTableRow(peopleTable.getTableName(),
                    dbFields, condition, peopleTable.getNewdID() + " DESC ",
                    null);
            operationObj.close();

            return cursor;


    }

    public Cursor getDataFromTableCategory(String tableName) {

        DBOperation operationObj = new DBOperation(context);
        operationObj.open();
        ChatPeopleBE peopleTable = new ChatPeopleBE();
        String condition = "";

        String[] dbFields = {peopleTable.getNewdID(), peopleTable.getNewsTitle(),
                peopleTable.getNewsContent(),
                peopleTable.getNewsImage(), peopleTable.getNewsLink(), peopleTable.getNewsPublisher(), peopleTable.getNewsTag(), peopleTable.getNewsFollow(), peopleTable.getNewsBookmark()};
        Cursor cursor = operationObj.getTableRow(tableName,
                dbFields, condition, peopleTable.getNewdID() + " DESC ",
                null);
        operationObj.close();

        return cursor;


    }

   /* public Cursor getData(){

        DBOperation operationObj = new DBOperation(context);
        operationObj.open();
        ChatPeopleBE peopleTable = new ChatPeopleBE();
        String condition = "";
            condition = peopleTable.getPERSON_ID();


        String[] dbFields = { peopleTable.getPERSON_ID()};
        Cursor res=operationObj.getTableRow(peopleTable.getTableName(),
                dbFields, condition, peopleTable.getPERSON_ID() + " ASC ",
                null);
      //  Cursor res = db.rawQuery("select DISTINCT device_id from people_table ", null );
        operationObj.close();
        return res;
    }*/

    public long insertTableData(String tablename, ContentValues values)
            throws SQLException {
        DB_TABLE = tablename;
        ContentValues contentValues = new ContentValues();
        Set<Map.Entry<String, Object>> s = values.valueSet();
        String new_val = "";
        for (Map.Entry<String, Object> entry : s) {
            new_val = values.getAsString(entry.getKey());
            contentValues.put(entry.getKey(), new_val);
        }
        return db.insert(DB_TABLE, null, contentValues);
    }
    public DBOperation open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void delete(String tablename) throws SQLException
    {
        System.out.println("TABLE DELETE"+tablename);
       db.execSQL("delete from " + tablename);
    }

    public void close() {
        DBHelper.close();
    }

    private class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, "newsdmedianew.db", null, DB_VERSION);
        }


        public void onCreate(SQLiteDatabase db) {
            try {
                for (String s : DATABASE_CREATE) {
                    db.execSQL(s);
                }
            } catch (Exception e) {
            }
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
            onCreate(db);
        }
    }


    public void createTablesIndia() {
        try {
            ChatPeopleBE mytable = new ChatPeopleBE();
            String[] tableCreateArray = { mytable.getDatabaseCreateQueryIndia() };
            DBOperation operation = new DBOperation(context, tableCreateArray);
            operation.open();
            operation.close();
            Log.i(TAG, "DB Created");
        } catch (Exception e) {
            Log.d(TAG, "Error creating table " + e.getMessage());
        }
    }

    public void createTablesWorld() {
        try {
            ChatPeopleBE mytable = new ChatPeopleBE();
            String[] tableCreateArray = { mytable.getDatabaseCreateQueryWorld() };
            DBOperation operation = new DBOperation(context, tableCreateArray);
            operation.open();
            operation.close();
            Log.i(TAG, "DB Created");
        } catch (Exception e) {
            Log.d(TAG, "Error creating table " + e.getMessage());
        }
    }

    public void createTablesTrending() {
        try {
            ChatPeopleBE mytable = new ChatPeopleBE();
            String[] tableCreateArray = { mytable.getDatabaseCreateQueryTrending() };
            DBOperation operation = new DBOperation(context, tableCreateArray);
            operation.open();
            operation.close();
            Log.i(TAG, "DB Created");
        } catch (Exception e) {
            Log.d(TAG, "Error creating table " + e.getMessage());
        }
    }

    public void createTablesHumour() {
        try {
            ChatPeopleBE mytable = new ChatPeopleBE();
            String[] tableCreateArray = { mytable.getDatabaseCreateQueryHumour() };
            DBOperation operation = new DBOperation(context, tableCreateArray);
            operation.open();
            operation.close();
            Log.i(TAG, "DB Created");
        } catch (Exception e) {
            Log.d(TAG, "Error creating table " + e.getMessage());
        }
    }


    public void createTablesEconomy() {
        try {
            ChatPeopleBE mytable = new ChatPeopleBE();
            String[] tableCreateArray = { mytable.getDatabaseCreateQueryEconomy() };
            DBOperation operation = new DBOperation(context, tableCreateArray);
            operation.open();
            operation.close();
            Log.i(TAG, "DB Created");
        } catch (Exception e) {
            Log.d(TAG, "Error creating table " + e.getMessage());
        }
    }

    public void createTablesBussiness() {
        try {
            ChatPeopleBE mytable = new ChatPeopleBE();
            String[] tableCreateArray = { mytable.getDatabaseCreateQueryBussiness() };
            DBOperation operation = new DBOperation(context, tableCreateArray);
            operation.open();
            operation.close();
            Log.i(TAG, "DB Created");
        } catch (Exception e) {
            Log.d(TAG, "Error creating table " + e.getMessage());
        }
    }

    public void createTablesTech() {
        try {
            ChatPeopleBE mytable = new ChatPeopleBE();
            String[] tableCreateArray = { mytable.getDatabaseCreateQueryTech() };
            DBOperation operation = new DBOperation(context, tableCreateArray);
            operation.open();
            operation.close();
            Log.i(TAG, "DB Created");
        } catch (Exception e) {
            Log.d(TAG, "Error creating table " + e.getMessage());
        }
    }


    public void createTablesSports() {
        try {
            ChatPeopleBE mytable = new ChatPeopleBE();
            String[] tableCreateArray = { mytable.getDatabaseCreateQuerySport() };
            DBOperation operation = new DBOperation(context, tableCreateArray);
            operation.open();
            operation.close();
            Log.i(TAG, "DB Created");
        } catch (Exception e) {
            Log.d(TAG, "Error creating table " + e.getMessage());
        }
    }


    public void createTablesEntertainment() {
        try {
            ChatPeopleBE mytable = new ChatPeopleBE();
            String[] tableCreateArray = { mytable.getDatabaseCreateQueryEntertainment() };
            DBOperation operation = new DBOperation(context, tableCreateArray);
            operation.open();
            operation.close();
            Log.i(TAG, "DB Created");
        } catch (Exception e) {
            Log.d(TAG, "Error creating table " + e.getMessage());
        }
    }

    public void createTablesLifeStyle() {
        try {
            ChatPeopleBE mytable = new ChatPeopleBE();
            String[] tableCreateArray = { mytable.getDatabaseCreateQueryLifestyle() };
            DBOperation operation = new DBOperation(context, tableCreateArray);
            operation.open();
            operation.close();
            Log.i(TAG, "DB Created");
        } catch (Exception e) {
            Log.d(TAG, "Error creating table " + e.getMessage());
        }
    }

    public void createTablesSpecial() {
        try {
            ChatPeopleBE mytable = new ChatPeopleBE();
            String[] tableCreateArray = { mytable.getDatabaseCreateQuerySpecial() };
            DBOperation operation = new DBOperation(context, tableCreateArray);
            operation.open();
            operation.close();
            Log.i(TAG, "DB Created");
        } catch (Exception e) {
            Log.d(TAG, "Error creating table " + e.getMessage());
        }
    }
}
