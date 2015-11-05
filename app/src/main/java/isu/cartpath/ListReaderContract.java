package isu.cartpath;

import android.provider.BaseColumns;

final class ListReaderContract {

    public ListReaderContract() {}

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
    }

    public static abstract class Store implements BaseColumns {
        public static final String TABLE_NAME = "Store";
        public static final String COLUMN_NAME_NAME = "name";
    }

    public static abstract class StoreCategory implements BaseColumns {
        public static final String TABLE_NAME = "StoreCategory";
        public static final String COLUMN_NAME_STORE = "store";
        public static final String COLUMN_NAME_CATEGORY = "category";
        public static final String COLUMN_NAME_AISLE = "aisle";
    }

}


