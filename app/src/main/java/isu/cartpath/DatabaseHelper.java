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

        public static abstract class Item implements BaseColumns {
            public static final String TABLE_NAME = "Item";
            public static final String COLUMN_NAME_NAME = "name";
            public static final String COLUMN_NAME_IN_CART = "inCart";
            public static final String COLUMN_NAME_AISLE = "aisle";
            public static final String COLUMN_NAME_CATEGORY = "category";

            static final String SQL_CREATE_TABLE =
                    "CREATE TABLE " + TABLE_NAME + "(" +
                            _ID + INTEGER_TYPE + PRIMARY_KEY + "," +
                            COLUMN_NAME_NAME + TEXT_TYPE + "," +
                            COLUMN_NAME_IN_CART + INTEGER_TYPE + "," +
                            COLUMN_NAME_AISLE + INTEGER_TYPE + "," +
                            COLUMN_NAME_CATEGORY + TEXT_TYPE + ")";

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
                            " REFERENCES " + GroceryKnowledge.Contract.Category.TABLE_NAME + "(" + GroceryKnowledge.Contract.Category.COLUMN_NAME_NAME + ")," +
                            COLUMN_NAME_AISLE + INTEGER_TYPE + ")";

            static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        }

    }

    private final Context context;

    private static DatabaseHelper instance;

    private static final int DATABASE_VERSION = 1;
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
                "Produce",
                "Baked Goods",
                "Baking",
                "Beverages",
                "Canned Foods",
                "Cheese",
                "Condiments",
                "Dairy",
                "Frozen Foods",
                "Meats",
                "Produce",
                "Refrigerated Items",
                "Seafood",
                "Snacks",
                "Spices & Herbs"
        }, new int[] {
                5,
                12,
                13,
                4,
                15,
                6,
                7,
                8,
                7,
                9,
                10,
                11,
                2,
                3,
                14,
                14
        });
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Contract.Item.SQL_DROP_TABLE);
        db.execSQL(Contract.Store.SQL_DROP_TABLE);
        db.execSQL(Contract.StoreCategory.SQL_DROP_TABLE);
        onCreate(db);
    }

    public long createStore() {
        ContentValues values = new ContentValues();
        values.put(Contract.Store.COLUMN_NAME_NAME, "New Store");
        return getWritableDatabase().insert(
                Contract.Store.TABLE_NAME,
                null,
                values
        );
    }

    String getName(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndex((Contract.Item.COLUMN_NAME_NAME)));
    }


    public int getAisle(Cursor c) {
        return c.getInt(c.getColumnIndex(Contract.StoreCategory.COLUMN_NAME_AISLE));
    }

    public String getCategory(Cursor c) {
        return c.getString(c.getColumnIndex(Contract.StoreCategory.COLUMN_NAME_CATEGORY));
    }

    boolean getChecked(Cursor cursor) {
        return cursor.getInt(cursor.getColumnIndex((Contract.Item.COLUMN_NAME_IN_CART))) == 1;
    }

    public Cursor getItem(long id) {
        return getReadableDatabase().query(
                Contract.Item.TABLE_NAME,
                null,
                Contract.Item._ID + "=?",
                new String[] {Long.toString(id)},
                null,
                null,
                null);
    }

    public Cursor getAllItemsByAisle() {
        return getReadableDatabase().query(
                Contract.Item.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                Contract.Item.COLUMN_NAME_IN_CART + " ASC," +
                Contract.Item.COLUMN_NAME_AISLE + " ASC," +
                Contract.Item._ID + " DESC");
    }

    public Cursor getAllItemsByCategory() {
        return getReadableDatabase().query(
                Contract.Item.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                Contract.Item.COLUMN_NAME_IN_CART + " ASC," +
                Contract.Item.COLUMN_NAME_CATEGORY + " ASC," +
                Contract.Item._ID + " DESC");
    }

    public void deleteItem(long id) {
        getWritableDatabase().delete(
                Contract.Item.TABLE_NAME,
                Contract.Item._ID + "=?",
                new String[] {Long.toString(id)});
    }

    public void addItem(String name) {
        String category = getCategoryForItem(name);
        String aisle = getAisleForCategory(category);
        ContentValues values = new ContentValues();
        values.put(Contract.Item.COLUMN_NAME_NAME, name);
        values.put(Contract.Item.COLUMN_NAME_IN_CART, 0);
        values.put(Contract.Item.COLUMN_NAME_AISLE, aisle);
        values.put(Contract.Item.COLUMN_NAME_CATEGORY, category);
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

    public Cursor getStoreCategories(String store) {
        return getReadableDatabase().query(
                Contract.StoreCategory.TABLE_NAME,
                new String[] {Contract.StoreCategory.COLUMN_NAME_CATEGORY, Contract.StoreCategory.COLUMN_NAME_AISLE},
                Contract.StoreCategory.COLUMN_NAME_STORE + "=?",
                new String[] {store},
                null,
                null,
                Contract.StoreCategory.COLUMN_NAME_CATEGORY);
    }

    public void renameStore(long id, String name) {
        ContentValues values = new ContentValues();
        values.put(Contract.Store.COLUMN_NAME_NAME, name);
        getWritableDatabase().update(
                Contract.Store.TABLE_NAME,
                values,
                Contract.Store._ID + "=?",
                new String[] {Long.toString(id)}
        );
    }

    public void deleteStore(long id) {
        getWritableDatabase().delete(
                Contract.Store.TABLE_NAME,
                Contract.Store._ID + "=" + Long.toString(id),
                null);
    }

    public Cursor getStores() {
        return getReadableDatabase().query(
                Contract.Store.TABLE_NAME,
                new String[] {Contract.Store._ID, Contract.Store.COLUMN_NAME_NAME},
                null,
                null,
                null,
                null,
                Contract.Store.COLUMN_NAME_NAME);
    }

    public Store getStore(long id) {
        Cursor c = getReadableDatabase().query(
                Contract.Store.TABLE_NAME,
                new String[] {Contract.Store._ID, Contract.Store.COLUMN_NAME_NAME},
                Contract.Store._ID + "=?",
                new String[] {Long.toString(id)},
                null,
                null,
                null);
        c.moveToFirst();
        Store store = getStore(c);
        c.close();
        return store;
    }

    public Store getStore(Cursor c) {
        long id = c.getLong(c.getColumnIndex(Contract.Store._ID));
        String name = c.getString(c.getColumnIndex(Contract.Store.COLUMN_NAME_NAME));
        return new Store(id, name);
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
                Contract.Item.COLUMN_NAME_AISLE + "=?",
                new String[] {aisle},
                null,
                null,
                Contract.Item._ID + " DESC");
    }

    public void renameItem(long id, String name) {
        String aisle = getAisleForCategory(name);
        ContentValues values = new ContentValues();
        values.put(Contract.Item.COLUMN_NAME_NAME, name);
        values.put(Contract.Item.COLUMN_NAME_AISLE, aisle);
        getWritableDatabase().update(
                Contract.Item.TABLE_NAME,
                values,
                Contract.Item._ID + "=?",
                new String[] {Long.toString(id)}
        );
    }

    public void deleteItemsInCart() {
        getWritableDatabase().delete(Contract.Item.TABLE_NAME, Contract.Item.COLUMN_NAME_IN_CART + "=1", null);
    }

    public void setItemInCart(long id, boolean checked) {
        ContentValues values = new ContentValues();
        values.put(Contract.Item.COLUMN_NAME_IN_CART, checked);
        getWritableDatabase().update(
                Contract.Item.TABLE_NAME,
                values,
                Contract.Item._ID + "=?",
                new String[] {Long.toString(id)}
        );
    }

    public String getCategoryForItem(String name) {
        Cursor likeItems = GroceryKnowledge.getInstance(context).likeItems(name);
        String category = null;
        if(likeItems.moveToFirst()) {
            category = GroceryKnowledge.getInstance(context).getItemName(likeItems);
        }
        likeItems.close();
        return category;
    }

    public String getAisleForCategory(String category) {
        String aisle = "-1";
        if(category == null)
            return aisle;
        Cursor aisleCursor = getReadableDatabase().query(
                Contract.StoreCategory.TABLE_NAME,
                null,
                Contract.StoreCategory.COLUMN_NAME_CATEGORY + "=?",
                new String[] {category},
                null,
                null,
                null);
        if(aisleCursor.moveToFirst()) {
            aisle = aisleCursor.getString(aisleCursor.getColumnIndex(Contract.StoreCategory.COLUMN_NAME_AISLE));
        }
        aisleCursor.close();
        return aisle;
    }
}
