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
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MasterList extends AppCompatActivity {
    //private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_list);

        ListReaderDbHelper db = new ListReaderDbHelper(this);
        final ItemListAdapter adapter = new ItemListAdapter(this, db.getAllItems());
        ListView list = (ListView) findViewById(R.id.listView);
        list.setAdapter(adapter);

        final EditText editText = (EditText) findViewById(R.id.newItem);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_NEXT) {

                    String name = editText.getText().toString();

                    ListReaderDbHelper mDbHelper = new ListReaderDbHelper(getApplicationContext());
                    SQLiteDatabase db = mDbHelper.getWritableDatabase();

                    ContentValues values = new ContentValues();
                    values.put(ListReaderContract.ItemEntry.COLUMN_NAME_NAME, name);
                    values.put(ListReaderContract.ItemEntry.COLUMN_NAME_IN_CART, 0);

                    long newRowId = db.insert(ListReaderContract.ItemEntry.TABLE_NAME, null, values);

                    Cursor cursor = adapter.getCursor();

                    MatrixCursor extras = new MatrixCursor(cursor.getColumnNames());
                    extras.addRow(new String[]{Long.toString(newRowId), name, "0"});
                    Cursor[] cursors = { extras, cursor };
                    adapter.swapCursor(new MergeCursor(cursors));

                    editText.setText("");

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

    /*
      Iterates through master list to find item and removes the item from the ArrayList
     */
    public void removeItem(ArrayList<String> master, String item) {

    }

    /*
        Adds item to master list
     */
    public void addItem() {

    }
}
