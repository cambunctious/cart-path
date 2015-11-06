package isu.cartpath;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DatabaseHelper extends SQLiteOpenHelper {

    private final CartPath app;

    private static final int DATABASE_VERSION = 6;
    private static final String DATABASE_NAME = "user.db";

    public DatabaseHelper(CartPath app) {
        super(app, DATABASE_NAME, null, DATABASE_VERSION);
        this.app = app;
        //GroceryKnowledge initDB = new GroceryKnowledge(app);
        //initDB.getWritableDatabase();
        //initDB.close();
        //String FIRST_DB_PATH = app.getDatabasePath(initDB.getDatabaseName()).toString();
        //SQL_ATTACH = "attach database '" + FIRST_DB_PATH + "' as '" + DatabaseContract.FIRST_DB_ALIAS + "';";
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseContract.Item.SQL_CREATE_TABLE);
        db.execSQL(DatabaseContract.Store.SQL_CREATE_TABLE);
        db.execSQL(DatabaseContract.StoreCategory.SQL_CREATE_TABLE);

        // dummy
        addStore(db, "Dummy Store", new String[] {
                "Dairy",
                "Produce"
        }, new int[] {
                1,
                2
        });
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DatabaseContract.Item.SQL_DROP_TABLE);
        db.execSQL(DatabaseContract.Store.SQL_DROP_TABLE);
        db.execSQL(DatabaseContract.StoreCategory.SQL_DROP_TABLE);
        onCreate(db);
    }

    String getName(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndex((DatabaseContract.Item.COLUMN_NAME_NAME)));
    }

    boolean getChecked(Cursor cursor) {
        return cursor.getInt(cursor.getColumnIndex((DatabaseContract.Item.COLUMN_NAME_IN_CART))) == 1;
    }

    public Cursor getItem(long id) {
        return app.db.query(
                DatabaseContract.Item.TABLE_NAME,
                null,
                DatabaseContract.Item._ID + "=" + Long.toString(id),
                null,
                null,
                null,
                null);
    }

    public Cursor getAllItems() {
        return app.db.query(
                DatabaseContract.Item.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                DatabaseContract.Item._ID + " DESC");
    }

    public void deleteItem(long id) {
        app.db.delete(DatabaseContract.Item.TABLE_NAME, DatabaseContract.Item._ID + "=" + Long.toString(id), null);
    }

    public void addItem(String name) {
        Cursor knownItems = app.knownDB.query(
                DatabaseContract.KnownItem.TABLE_NAME,
                null,
                DatabaseContract.KnownItem.COLUMN_NAME_NAME + " LIKE '%" + name + "%'",
                null,
                null,
                null,
                null);
        String aisle = "-1";
        if(knownItems.moveToFirst()) {
            String category = knownItems.getString(knownItems.getColumnIndex(DatabaseContract.KnownItem.COLUMN_NAME_CATEGORY));
            Cursor aisleCursor = app.db.query(
                    DatabaseContract.StoreCategory.TABLE_NAME,
                    null,
                    DatabaseContract.StoreCategory.COLUMN_NAME_CATEGORY + "='" + category + "'",
                    null,
                    null,
                    null,
                    null);
            if(aisleCursor.moveToFirst()) {
                aisle = aisleCursor.getString(aisleCursor.getColumnIndex(DatabaseContract.StoreCategory.COLUMN_NAME_AISLE));
            }
        }
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.Item.COLUMN_NAME_NAME, name);
        values.put(DatabaseContract.Item.COLUMN_NAME_IN_CART, 0);
        values.put(DatabaseContract.Item.COLUMN_NAME_AISLE, aisle);
        app.db.insert(DatabaseContract.Item.TABLE_NAME, null, values);
    }

    public void addStore(SQLiteDatabase db, String storeName, String[] categories, int[] aisles) {
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.Store.COLUMN_NAME_NAME, storeName);
        db.insert(DatabaseContract.Store.TABLE_NAME, null, values);

        for(int i = 0; i < categories.length; ++i) {
            values.clear();
            values.put(DatabaseContract.StoreCategory.COLUMN_NAME_STORE, storeName);
            values.put(DatabaseContract.StoreCategory.COLUMN_NAME_CATEGORY, categories[i]);
            values.put(DatabaseContract.StoreCategory.COLUMN_NAME_AISLE, aisles[i]);
            db.insert(DatabaseContract.StoreCategory.TABLE_NAME, null, values);
        }
    }

    public String[] getCategoriesArray() {
        Cursor c = getAllCategories();
        int size = c.getCount();
        String[] result = new String[size];
        for(int i = 0; i < size; ++i) {
            c.moveToPosition(i);
            result[i] = c.getString(c.getColumnIndex(DatabaseContract.Category.COLUMN_NAME_NAME));
        }
        return result;
    }

    public Cursor getAllCategories() {
        return app.db.query(
                DatabaseContract.Category.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                DatabaseContract.Category.COLUMN_NAME_NAME + " DESC");
    }

    public Cursor getAllAisles() {
        return app.db.query(
                true,
                DatabaseContract.Item.TABLE_NAME,
                new String[] { DatabaseContract.Item.COLUMN_NAME_AISLE + " AS " + DatabaseContract.Item._ID },
                null,
                null,
                null,
                null,
                DatabaseContract.StoreCategory.COLUMN_NAME_AISLE + " DESC",
                null);
    }

    public Cursor getAisle(String aisle) {
        return app.db.query(
                DatabaseContract.Item.TABLE_NAME,
                null,
                DatabaseContract.Item.COLUMN_NAME_AISLE + "=" + aisle,
                null,
                null,
                null,
                DatabaseContract.Item._ID + " DESC");
    }
}
