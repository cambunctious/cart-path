package isu.cartpath;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class ListReaderDbHelper extends SQLiteOpenHelper {

    private final CartPath app;

    private static final String PRIMARY_KEY = " PRIMARY KEY";
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ListReaderContract.ItemEntry.TABLE_NAME + " (" +
                    ListReaderContract.ItemEntry._ID + INTEGER_TYPE + PRIMARY_KEY + COMMA_SEP +
                    ListReaderContract.ItemEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    ListReaderContract.ItemEntry.COLUMN_NAME_IN_CART + INTEGER_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ListReaderContract.ItemEntry.TABLE_NAME;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ListReader.db";

    public ListReaderDbHelper(CartPath app) {
        super(app, DATABASE_NAME, null, DATABASE_VERSION);
        this.app = app;
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    String getName(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndex((ListReaderContract.ItemEntry.COLUMN_NAME_NAME)));
    }

    boolean getChecked(Cursor cursor) {
        return cursor.getInt(cursor.getColumnIndex((ListReaderContract.ItemEntry.COLUMN_NAME_IN_CART))) == 1;
    }

    public Cursor getItem(long id) {
        return app.db.query(
                ListReaderContract.ItemEntry.TABLE_NAME,
                null,
                ListReaderContract.ItemEntry._ID + "=" + Long.toString(id),
                null,
                null,
                null,
                null);
    }

    public Cursor getAllItems() {
        return app.db.query(
                ListReaderContract.ItemEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                ListReaderContract.ItemEntry._ID + " DESC");
    }

    public void deleteItem(long id) {
        app.db.delete(ListReaderContract.ItemEntry.TABLE_NAME, ListReaderContract.ItemEntry._ID + "=" + Long.toString(id), null);
    }
}
