package isu.cartpath;

import android.content.Context;
import android.database.Cursor;
import android.provider.BaseColumns;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class GroceryKnowledge extends SQLiteAssetHelper {

    static class Contract {

        public static abstract class KnownItem implements BaseColumns {
            public static final String TABLE_NAME = "Item";
            public static final String COLUMN_NAME_NAME = "name";
            public static final String COLUMN_NAME_CATEGORY = "category";
        }
    }

	static GroceryKnowledge instance;

	private static final String DATABASE_NAME = "cartpath.db";
	private static final int DATABASE_VERSION = 1;

	public static GroceryKnowledge getInstance(Context context) {
		if(instance == null)
			instance = new GroceryKnowledge(context);
		return instance;
	}

	private GroceryKnowledge(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public Cursor likeItems(String name) {
		return getReadableDatabase().query(
				Contract.KnownItem.TABLE_NAME,
				null,
				Contract.KnownItem.COLUMN_NAME_NAME + " LIKE '%" + name + "%'",
				null,
				null,
				null,
				null);
	}

    public String getItemName(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndex(Contract.KnownItem.COLUMN_NAME_CATEGORY));
    }
}