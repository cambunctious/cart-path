package isu.cartpath;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.ListAdapter;

import java.util.ArrayList;


public class GroceryStore extends AppCompatActivity
{
    private ArrayList<String> categories;
    private DatabaseHelper helper;
    private long id;
    private TextView storeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_store);
        CartPath app = (CartPath) getApplication();


        Intent intent = getIntent();
        String[] categories = GroceryKnowledge.getInstance(this).getCategoriesArray();
        ListView list = (ListView) findViewById(R.id.stores);
        list.setAdapter(new ArrayAdapter<String>(this, R.layout.category, categories){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if(convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.category, null);
                    TextView textview = (TextView)convertView.findViewById(R.id.category_name);
                    textview.setText(getItem(position));
                }
                return convertView;
            }
        });


    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_edit_store, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.cart_path_view)
        {
            Intent cartPathIntent = new Intent(this, CartPath.class);
            startActivity(cartPathIntent);
            return true;
        }
        if (id == R.id.aisle_view)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public void createStore(View view)
    {
        final EditText editText = (EditText) findViewById(R.id.newStore);

        //helper.addItem();
    }

    public void editStore(View view)
    {

        /*
        new DialogFragment() {
            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View view = View.inflate(getApplicationContext(), R.layout.dialog_rename, null);
                final EditText newItemName = (EditText) view.findViewById(R.id.item_name);
                newItemName.setText(storeName.getText());
                newItemName.setSelection(newItemName.getText().length());
                builder.setView(view)
                        .setTitle(R.string.rename_title)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int buttonId) {
                                String name = newItemName.getText().toString();
                                DatabaseHelper.getInstance(getActivity()).renameItem(id, name);
                                storeName.setText(name);
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, null);
                return builder.create();
            }
        }.show(getFragmentManager(), "rename");
        */
    }

    public void deleteStore(View view)
    {
        DatabaseHelper.getInstance(this).deleteItem(id);
        finish();
    }

    public void clearChecked(View view) {
    }
}
