package isu.cartpath;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class StoresActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stores);
        ListView storeList = (ListView) findViewById(R.id.stores);
        registerForContextMenu(storeList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ListView storeList = (ListView) findViewById(R.id.stores);
        DatabaseHelper db = DatabaseHelper.getInstance(this);
        Cursor storesCursor = db.getStores();
        ArrayList<Store> stores = new ArrayList<>();
        if(storesCursor.moveToFirst()) {
            do {
                stores.add(db.getStore(storesCursor));
            } while(storesCursor.moveToNext());
        }
        storeList.setAdapter(new ArrayAdapter<Store>(this, R.layout.category, stores){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if(convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.store, null);
                    TextView textview = (TextView)convertView.findViewById(R.id.name);
                    textview.setText(getItem(position).name);
                }
                return convertView;
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.store_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        ListView storeList = (ListView) findViewById(R.id.stores);
        @SuppressWarnings("unchecked")
        ArrayAdapter<Store> adapter = (ArrayAdapter<Store>) storeList.getAdapter();
        Store store = adapter.getItem(info.position);
        switch (item.getItemId()) {
            case R.id.delete:
                DatabaseHelper.getInstance(this).deleteStore(store.id);
                return true;
            case R.id.edit:
                Intent intent = new Intent(this, EditStore.class);
                intent.putExtra("id", store.id);
                startActivity(intent);
        }
        return false;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_store, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cart_path_view:
                Intent cartPathIntent = new Intent(this, CartPath.class);
                startActivity(cartPathIntent);
                return true;
            case R.id.aisle_view:
                return true;
        }
        return false;
    }

    public void createStore(View view) {
        long id = DatabaseHelper.getInstance(this).createStore();
        Intent intent = new Intent(this, EditStore.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}
