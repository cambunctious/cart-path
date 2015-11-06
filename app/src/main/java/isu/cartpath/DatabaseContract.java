package isu.cartpath;

import android.provider.BaseColumns;

final class DatabaseContract {

    private DatabaseContract() {}

    private static final String PRIMARY_KEY = " PRIMARY KEY";
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";

    public static abstract class Category {
        public static final String TABLE_NAME = "Category";
        public static final String COLUMN_NAME_NAME = "name";
    }

    public static abstract class KnownItem implements BaseColumns {
        public static final String TABLE_NAME = "Item";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_CATEGORY = "category";
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


