package isu.cartpath;

import android.provider.BaseColumns;

final class ListReaderContract {

    public ListReaderContract() {}

    public static abstract class ItemEntry implements BaseColumns {
        public static final String TABLE_NAME = "item";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_IN_CART = "inCart";
    }
}


