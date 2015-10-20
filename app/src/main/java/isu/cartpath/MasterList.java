package isu.cartpath;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MergeCursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MasterList extends AppCompatActivity {

    CartPath app;
    ItemListAdapter listAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_list);

        app = (CartPath) getApplication();

        ListView list = (ListView) findViewById(R.id.listView);
        listAdapter = new ItemListAdapter(this, app.dbHelper);
        list.setAdapter(listAdapter);

        final EditText editText = (EditText) findViewById(R.id.newItem);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    addItem();
                    handled = true;
                }
                return handled;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_master_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addItem() {
        final EditText editText = (EditText) findViewById(R.id.newItem);
        String name = editText.getText().toString();

        SQLiteDatabase db = app.dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ListReaderContract.ItemEntry.COLUMN_NAME_NAME, name);
        values.put(ListReaderContract.ItemEntry.COLUMN_NAME_IN_CART, 0);

        long newRowId = db.insert(ListReaderContract.ItemEntry.TABLE_NAME, null, values);

        Cursor cursor = listAdapter.getCursor();

        MatrixCursor extras = new MatrixCursor(cursor.getColumnNames());
        extras.addRow(new String[]{Long.toString(newRowId), name, "0"});
        Cursor[] cursors = {extras, cursor};
        listAdapter.swapCursor(new MergeCursor(cursors));

        editText.setText("");
    }

    public void addButton(View view) {
        addItem();
    }

    public void clearChecked(View view) {
        SQLiteDatabase db = app.dbHelper.getWritableDatabase();
        db.delete(ListReaderContract.ItemEntry.TABLE_NAME, ListReaderContract.ItemEntry.COLUMN_NAME_IN_CART + "=1", null);
        listAdapter.resetCursor();
    }

    public void checkItem(View view) {
        boolean checked = ((CheckBox)view).isChecked();
        ListView list = (ListView) findViewById(R.id.listView);
        int position = list.getPositionForView(view);
        long id = list.getAdapter().getItemId(position);
        SQLiteDatabase db = app.dbHelper.getWritableDatabase();
        db.execSQL("UPDATE " +
                ListReaderContract.ItemEntry.TABLE_NAME +
                " SET " + ListReaderContract.ItemEntry.COLUMN_NAME_IN_CART + "=" + (checked ? "1" : "0") +
                " WHERE " + ListReaderContract.ItemEntry._ID + "=" + Long.toString(id));
    }
}
