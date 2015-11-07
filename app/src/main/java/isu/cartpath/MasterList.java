package isu.cartpath;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.*;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class MasterList extends AppCompatActivity {

    private ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_list);

        CartPath app = (CartPath) getApplication();

        final StickyListHeadersListView list = (StickyListHeadersListView) findViewById(R.id.list);
        listAdapter = new ListAdapter(this, app);
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

        registerForContextMenu(list);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        listAdapter.resetCursor();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.item_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.delete:
                deleteItem(info.id);
        }
        return true;
    }

    private void deleteItem(long id) {
        CartPath app = (CartPath) getApplication();
        DatabaseHelper.getInstance(app).deleteItem(id);
        listAdapter.resetCursor();
    }

    private void addItem() {
        final EditText editText = (EditText) findViewById(R.id.newItem);
        String name = editText.getText().toString();
        CartPath app = (CartPath) getApplication();
        DatabaseHelper.getInstance(app).addItem(name);
        editText.setText("");
        listAdapter.resetCursor();
    }

    public void addButton(View view) {
        addItem();
    }

    public void clearChecked(View view) {
        DatabaseHelper.getInstance(this).deleteItemsInCart();
        listAdapter.resetCursor();
    }

    public void checkItem(View view) {
        boolean checked = ((CheckBox)view).isChecked();
        StickyListHeadersListView list = (StickyListHeadersListView) findViewById(R.id.list);
        int position = list.getPositionForView(view);
        long id = list.getAdapter().getItemId(position);
        DatabaseHelper.getInstance(this).setItemInCart(id, checked);
        listAdapter.resetCursor();
    }

    public void openItem(View view) {
        StickyListHeadersListView list = (StickyListHeadersListView) findViewById(R.id.list);
        int position = list.getPositionForView(view);
        long id = list.getAdapter().getItemId(position);
        Intent intent = new Intent(this, EditItem.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}
