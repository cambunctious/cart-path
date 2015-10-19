package isu.cartpath;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;

class ItemListAdapter extends SimpleCursorAdapter {
    private final LayoutInflater inflater;
    private static final int layout = R.layout.list_item;

    public ItemListAdapter(Context context, Cursor cursor) {
        super(context,
                layout,
                cursor,
                new String[]{ListReaderContract.ItemEntry.COLUMN_NAME_NAME},
                new int[]{R.id.itemName},
                0);
        this.inflater=LayoutInflater.from(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return inflater.inflate(layout, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);
        CheckBox chk = (CheckBox)view.findViewById(R.id.cartCheck);
        chk.setChecked(cursor.getInt(cursor.getColumnIndex((ListReaderContract.ItemEntry.COLUMN_NAME_IN_CART))) == 1);
        EditText editText = (EditText)view.findViewById(R.id.itemName);
        editText.setText(cursor.getString(cursor.getColumnIndex((ListReaderContract.ItemEntry.COLUMN_NAME_NAME))));
    }
}
