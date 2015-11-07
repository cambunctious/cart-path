package isu.cartpath;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

class DatabaseHelper extends SQLiteOpenHelper {

    static final class Contract {

        private Contract() {}

        private static final String PRIMARY_KEY = " PRIMARY KEY";
        private static final String TEXT_TYPE = " TEXT";
        private static final String INTEGER_TYPE = " INTEGER";

        public static abstract class Category {
            public static final String TABLE_NAME = "Category";
            public static final String COLUMN_NAME_NAME = "name";
        }

        public static abstract class Item implements BaseColumns {
            public static final String TABLE_NAME = "Item";
            public static final String COLUMN_NAME_NAME = "name";
            public static final String COLUMN_NAME_IN_CART = "inCart";
            public static final String COLUMN_NAME_AISLE = "aisle";

            static final String SQL_CREATE_TABLE =
                    "CREATE TABLE " + TABLE_NAME + "(" +
                            _ID + INTEGER_TYPE + PRIMARY_KEY + "," +
                            COLUMN_NAME_NAME + TEXT_TYPE + "," +
                            COLUMN_NAME_IN_CART + INTEGER_TYPE + "," +
                            COLUMN_NAME_AISLE + INTEGER_TYPE + ")";

            static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        }

        public static abstract class Store implements BaseColumns {
            public static final String TABLE_NAME = "Store";
            public static final String COLUMN_NAME_NAME = "name";

            static final String SQL_CREATE_TABLE =
                    "CREATE TABLE " + TABLE_NAME + "(" +
                            _ID + INTEGER_TYPE + PRIMARY_KEY + "," +
                            COLUMN_NAME_NAME + TEXT_TYPE + ")";

            static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        }

        public static abstract class StoreCategory implements BaseColumns {
            public static final String TABLE_NAME = "StoreCategory";
            public static final String COLUMN_NAME_STORE = "store";
            public static final String COLUMN_NAME_CATEGORY = "category";
            public static final String COLUMN_NAME_AISLE = "aisle";

            static final String SQL_CREATE_TABLE =
                    "CREATE TABLE " + TABLE_NAME + " (" +
                            _ID + INTEGER_TYPE + PRIMARY_KEY + "," +
                            COLUMN_NAME_STORE + INTEGER_TYPE +
                            " REFERENCES " + Store.TABLE_NAME + "(" + _ID + ")," +
                            COLUMN_NAME_CATEGORY + INTEGER_TYPE +
                            " REFERENCES " + Category.TABLE_NAME + "(" + Category.COLUMN_NAME_NAME + ")," +
                            COLUMN_NAME_AISLE + INTEGER_TYPE + ")";

            static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        }

    }

    private final Context context;

    static DatabaseHelper instance;

    private static final int DATABASE_VERSION = 6;
    private static final String DATABASE_NAME = "user.db";

    public static DatabaseHelper getInstance(Context context) {
        if(instance == null)
            instance = new DatabaseHelper(context);
        return instance;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Contract.Item.SQL_CREATE_TABLE);
        db.execSQL(Contract.Store.SQL_CREATE_TABLE);
        db.execSQL(Contract.StoreCategory.SQL_CREATE_TABLE);

        addStore(db, "Dummy Store", new String[] {
                "Dairy",
                "Produce"
        }, new int[] {
                1,
                2
        });
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Contract.Item.SQL_DROP_TABLE);
        db.execSQL(Contract.Store.SQL_DROP_TABLE);
        db.execSQL(Contract.StoreCategory.SQL_DROP_TABLE);
        onCreate(db);
    }

    String getName(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndex((Contract.Item.COLUMN_NAME_NAME)));
    }

    boolean getChecked(Cursor cursor) {
        return cursor.getInt(cursor.getColumnIndex((Contract.Item.COLUMN_NAME_IN_CART))) == 1;
    }

    public Cursor getItem(long id) {
        return getReadableDatabase().query(
                Contract.Item.TABLE_NAME,
                null,
                Contract.Item._ID + "=" + Long.toString(id),
                null,
                null,
                null,
                null);
    }

    public Cursor getAllItems() {
        return getReadableDatabase().query(
                Contract.Item.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                Contract.Item._ID + " DESC");
    }

    public void deleteItem(long id) {
        getWritableDatabase().delete(Contract.Item.TABLE_NAME, Contract.Item._ID + "=" + Long.toString(id), null);
    }

    public void addItem(String name) {
        String aisle = selectAisle(name);
        ContentValues values = new ContentValues();
        values.put(Contract.Item.COLUMN_NAME_NAME, name);
        values.put(Contract.Item.COLUMN_NAME_IN_CART, 0);
        values.put(Contract.Item.COLUMN_NAME_AISLE, aisle);
        getWritableDatabase().insert(Contract.Item.TABLE_NAME, null, values);
    }

    public void addStore(SQLiteDatabase db, String storeName, String[] categories, int[] aisles) {
        ContentValues values = new ContentValues();
        values.put(Contract.Store.COLUMN_NAME_NAME, storeName);
        db.insert(Contract.Store.TABLE_NAME, null, values);

        for(int i = 0; i < categories.length; ++i) {
            values.clear();
            values.put(Contract.StoreCategory.COLUMN_NAME_STORE, storeName);
            values.put(Contract.StoreCategory.COLUMN_NAME_CATEGORY, categories[i]);
            values.put(Contract.StoreCategory.COLUMN_NAME_AISLE, aisles[i]);
            db.insert(Contract.StoreCategory.TABLE_NAME, null, values);
        }
    }

    public String[] getCategoriesArray() {
        Cursor c = getAllCategories();
        int size = c.getCount();
        String[] result = new String[size];
        for(int i = 0; i < size; ++i) {
            c.moveToPosition(i);
            result[i] = c.getString(c.getColumnIndex(Contract.Category.COLUMN_NAME_NAME));
        }
        return result;
    }

    public Cursor getAllCategories() {
        return getReadableDatabase().query(
                Contract.Category.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                Contract.Category.COLUMN_NAME_NAME + " DESC");
    }

    public Cursor getAllAisles() {
        return getReadableDatabase().query(
                true,
                Contract.Item.TABLE_NAME,
                new String[] { Contract.Item.COLUMN_NAME_AISLE + " AS " + Contract.Item._ID },
                null,
                null,
                null,
                null,
                Contract.StoreCategory.COLUMN_NAME_AISLE + " DESC",
                null);
    }

    public Cursor getAisle(String aisle) {
        return getReadableDatabase().query(
                Contract.Item.TABLE_NAME,
                null,
                Contract.Item.COLUMN_NAME_AISLE + "=" + aisle,
                null,
                null,
                null,
                Contract.Item._ID + " DESC");
    }

    public void renameItem(long id, String name) {
        String aisle = selectAisle(name);
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE " +
                Contract.Item.TABLE_NAME +
                " SET " + Contract.Item.COLUMN_NAME_NAME + "='" + name + "'," +
                    Contract.Item.COLUMN_NAME_AISLE + "=" + aisle +
                " WHERE " + Contract.Item._ID + "=" + Long.toString(id));
    }

    public void deleteItemsInCart() {
        getWritableDatabase().delete(Contract.Item.TABLE_NAME, Contract.Item.COLUMN_NAME_IN_CART + "=1", null);
    }

    public void setItemInCart(long id, boolean checked) {
        getWritableDatabase().execSQL("UPDATE " +
                Contract.Item.TABLE_NAME +
                " SET " + Contract.Item.COLUMN_NAME_IN_CART + "=" + (checked ? "1" : "0") +
                " WHERE " + Contract.Item._ID + "=" + Long.toString(id));
    }

    public String selectAisle(String name) {
        Cursor likeItems = GroceryKnowledge.getInstance(context).likeItems(name);
        String aisle = "-1";
        if(likeItems.moveToFirst()) {
            String category = GroceryKnowledge.getInstance(context).getItemName(likeItems);
            Cursor aisleCursor = getReadableDatabase().query(
                    Contract.StoreCategory.TABLE_NAME,
                    null,
                    Contract.StoreCategory.COLUMN_NAME_CATEGORY + "='" + category + "'",
                    null,
                    null,
                    null,
                    null);
            if(aisleCursor.moveToFirst()) {
                aisle = aisleCursor.getString(aisleCursor.getColumnIndex(Contract.StoreCategory.COLUMN_NAME_AISLE));
            }
            aisleCursor.close();
        }
        likeItems.close();
        return aisle;
    }
}
