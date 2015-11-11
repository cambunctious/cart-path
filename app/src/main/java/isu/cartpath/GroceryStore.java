package isu.cartpath;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

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
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_master_list,menu);
        return true;
    }



    public void createStore(View view)
    {

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
