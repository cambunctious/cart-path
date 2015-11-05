package isu.cartpath;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class ListReaderDbHelper extends SQLiteOpenHelper {

    private final CartPath app;

    private static final String PRIMARY_KEY = " PRIMARY KEY";
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    //private static String SQL_ATTACH;
    private static final String SQL_CREATE_TABLE_ITEM =
            "CREATE TABLE " + ListReaderContract.Item.TABLE_NAME + "(" +
                ListReaderContract.Item._ID + INTEGER_TYPE + PRIMARY_KEY + "," +
                ListReaderContract.Item.COLUMN_NAME_NAME + TEXT_TYPE + "," +
                ListReaderContract.Item.COLUMN_NAME_IN_CART + INTEGER_TYPE + "," +
                ListReaderContract.Item.COLUMN_NAME_AISLE + INTEGER_TYPE + ")";
    private static final String SQL_CREATE_TABLE_STORE =
            "CREATE TABLE " + ListReaderContract.Store.TABLE_NAME + "(" +
                ListReaderContract.Store._ID + INTEGER_TYPE + PRIMARY_KEY + "," +
                ListReaderContract.Store.COLUMN_NAME_NAME + TEXT_TYPE + ")";
    private static final String SQL_CREATE_TABLE_STORECATEGORY =
            "CREATE TABLE " + ListReaderContract.StoreCategory.TABLE_NAME + " (" +
                ListReaderContract.StoreCategory._ID + INTEGER_TYPE + PRIMARY_KEY + "," +
                ListReaderContract.StoreCategory.COLUMN_NAME_STORE + INTEGER_TYPE +
                    " REFERENCES " + ListReaderContract.Store.TABLE_NAME + "(" + ListReaderContract.Store._ID + ")," +
                ListReaderContract.StoreCategory.COLUMN_NAME_CATEGORY + INTEGER_TYPE +
                    " REFERENCES " + ListReaderContract.Category.TABLE_NAME + "(" + ListReaderContract.Category.COLUMN_NAME_NAME + ")," +
                ListReaderContract.StoreCategory.COLUMN_NAME_AISLE + INTEGER_TYPE + ")";
    private static final String SQL_DROP_TABLE_ITEM =
            "DROP TABLE IF EXISTS " + ListReaderContract.Item.TABLE_NAME;
    private static final String SQL_DROP_TABLE_STORE =
            "DROP TABLE IF EXISTS " + ListReaderContract.Store.TABLE_NAME;
    private static final String SQL_DROP_TABLE_STORECATGORY =
            "DROP TABLE IF EXISTS " + ListReaderContract.StoreCategory.TABLE_NAME;

    private static final int DATABASE_VERSION = 6;
    private static final String DATABASE_NAME = "user.db";

    public ListReaderDbHelper(CartPath app) {
        super(app, DATABASE_NAME, null, DATABASE_VERSION);
        this.app = app;
        //MyDatabase initDB = new MyDatabase(app);
        //initDB.getWritableDatabase();
        //initDB.close();
        //String FIRST_DB_PATH = app.getDatabasePath(initDB.getDatabaseName()).toString();
        //SQL_ATTACH = "attach database '" + FIRST_DB_PATH + "' as '" + ListReaderContract.FIRST_DB_ALIAS + "';";
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_ITEM);
        db.execSQL(SQL_CREATE_TABLE_STORE);
        db.execSQL(SQL_CREATE_TABLE_STORECATEGORY);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_TABLE_ITEM);
        db.execSQL(SQL_DROP_TABLE_STORE);
        db.execSQL(SQL_DROP_TABLE_STORECATGORY);
        onCreate(db);
    }

    String getName(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndex((ListReaderContract.Item.COLUMN_NAME_NAME)));
    }

    boolean getChecked(Cursor cursor) {
        return cursor.getInt(cursor.getColumnIndex((ListReaderContract.Item.COLUMN_NAME_IN_CART))) == 1;
    }

    public Cursor getItem(long id) {
        return app.db.query(
                ListReaderContract.Item.TABLE_NAME,
                null,
                ListReaderContract.Item._ID + "=" + Long.toString(id),
                null,
                null,
                null,
                null);
    }

    public Cursor getAllItems() {
        return app.db.query(
                ListReaderContract.Item.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                ListReaderContract.Item._ID + " DESC");
    }

    public void deleteItem(long id) {
        app.db.delete(ListReaderContract.Item.TABLE_NAME, ListReaderContract.Item._ID + "=" + Long.toString(id), null);
    }

    public void addItem(String name) {
        Cursor knownItems = app.knownDB.query(
                ListReaderContract.KnownItem.TABLE_NAME,
                null,
                ListReaderContract.KnownItem.COLUMN_NAME_NAME + " LIKE '%" + name + "%'",
                null,
                null,
                null,
                null);
        String aisle = "-1";
        if(knownItems.moveToFirst()) {
            String category = knownItems.getString(knownItems.getColumnIndex(ListReaderContract.KnownItem.COLUMN_NAME_CATEGORY));
            Cursor aisleCursor = app.db.query(
                    ListReaderContract.StoreCategory.TABLE_NAME,
                    null,
                    ListReaderContract.StoreCategory.COLUMN_NAME_CATEGORY + "='" + category + "'",
                    null,
                    null,
                    null,
                    null);
            if(aisleCursor.moveToFirst()) {
                aisle = aisleCursor.getString(aisleCursor.getColumnIndex(ListReaderContract.StoreCategory.COLUMN_NAME_AISLE));
            }
        }
        ContentValues values = new ContentValues();
        values.put(ListReaderContract.Item.COLUMN_NAME_NAME, name);
        values.put(ListReaderContract.Item.COLUMN_NAME_IN_CART, 0);
        values.put(ListReaderContract.Item.COLUMN_NAME_AISLE, aisle);
        app.db.insert(ListReaderContract.Item.TABLE_NAME, null, values);
    }

    public Cursor getAllCategories() {
        return app.db.query(
                ListReaderContract.Category.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                ListReaderContract.Category.COLUMN_NAME_NAME + " DESC");
    }

    public Cursor getAllAisles() {
        return app.db.query(
                true,
                ListReaderContract.Item.TABLE_NAME,
                new String[] { ListReaderContract.Item.COLUMN_NAME_AISLE + " AS " + ListReaderContract.Item._ID },
                null,
                null,
                null,
                null,
                ListReaderContract.StoreCategory.COLUMN_NAME_AISLE + " DESC",
                null);
    }

    public Cursor getAisle(String aisle) {
        return app.db.query(
                ListReaderContract.Item.TABLE_NAME,
                null,
                ListReaderContract.Item.COLUMN_NAME_AISLE + "=" + aisle,
                null,
                null,
                null,
                ListReaderContract.Item._ID + " DESC");
    }
}
