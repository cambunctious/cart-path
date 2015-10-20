package isu.cartpath;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

class ItemListAdapter extends SimpleCursorAdapter {
    private final LayoutInflater inflater;
    private static final int layout = R.layout.list_item;
    //private Context context;
    private ListReaderDbHelper dbHelper;

    public ItemListAdapter(Context context, ListReaderDbHelper dbHelper) {
        super(context,
                layout,
                defaultCursor(dbHelper),
                new String[]{ListReaderContract.ItemEntry.COLUMN_NAME_NAME},
                new int[]{R.id.itemName},
                0);
        this.inflater = LayoutInflater.from(context);
        //this.context = context;
        this.dbHelper = dbHelper;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return inflater.inflate(layout, null);
    }

    @Override
    public void bindView(View view, Context c, Cursor cursor) {
        super.bindView(view, c, cursor);
        CheckBox chk = (CheckBox)view.findViewById(R.id.cartCheck);
        chk.setChecked(cursor.getInt(cursor.getColumnIndex((ListReaderContract.ItemEntry.COLUMN_NAME_IN_CART))) == 1);
        EditText editText = (EditText)view.findViewById(R.id.itemName);
        editText.setText(cursor.getString(cursor.getColumnIndex((ListReaderContract.ItemEntry.COLUMN_NAME_NAME))));
        final ItemListAdapter adapter = this;
        final int position = cursor.getPosition();
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    long id = adapter.getItemId(position);
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    Cursor c = db.query(
                            ListReaderContract.ItemEntry.TABLE_NAME,
                            null,
                            ListReaderContract.ItemEntry._ID + "=" + Long.toString(id),
                            null,
                            null,
                            null,
                            null
                    );
                    db.execSQL("UPDATE " +
                            ListReaderContract.ItemEntry.TABLE_NAME +
                            " SET " + ListReaderContract.ItemEntry.COLUMN_NAME_NAME + "='" + ((EditText) v).getText().toString() + "'" +
                            " WHERE " + ListReaderContract.ItemEntry._ID + "=" + Long.toString(id));
                    handled = true;
                }
                return handled;
            }
        });
    }

    public void resetCursor() {
        swapCursor(defaultCursor(dbHelper));
    }

    public static Cursor defaultCursor(ListReaderDbHelper dbHelper) {
        return dbHelper.getAllItems();
    }
}
