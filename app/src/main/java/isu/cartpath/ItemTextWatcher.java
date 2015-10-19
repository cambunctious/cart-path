package isu.cartpath;

import android.text.Editable;
import android.text.TextWatcher;

class ItemTextWatcher implements TextWatcher {
    private ItemListAdapter itemListAdapter;

    public ItemTextWatcher(ItemListAdapter itemListAdapter) {
        this.itemListAdapter = itemListAdapter;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        /*
        ListReaderDbHelper mDbHelper = new ListReaderDbHelper(itemListAdapter.context);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(ListReaderContract.ItemEntry.COLUMN_NAME_NAME, title);

        // Which row to update, based on the ID
        String selection = ListReaderContract.ItemEntry.COLUMN_NAME_ITEM_ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(rowId)};

        int count = db.update(
                ListReaderContract.ItemEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
                */
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
