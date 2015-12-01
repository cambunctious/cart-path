package isu.cartpath;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.HashMap;

public class EditStore extends AppCompatActivity
{

    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        id = intent.getLongExtra("id", -1);
        Store store = DatabaseHelper.getInstance(this).getStore(id);

        setContentView(R.layout.activity_edit_store);
        final TextView itemName = (TextView) findViewById(R.id.name);
        itemName.setText(store.name);
        DatabaseHelper db = DatabaseHelper.getInstance(this);
        Cursor storeCategories = db.getStoreCategories(store.name);
        int count = storeCategories.getCount();
        String[] categories = new String[count];
        final HashMap<String, Integer> aisles = new HashMap<>();
        for(int i=0; i<count; ++i) {
            storeCategories.moveToNext();
            String category = db.getCategory(storeCategories);
            aisles.put(category, db.getAisle(storeCategories));
            categories[i] = category;
        }
        storeCategories.close();
        ListView list = (ListView) findViewById(R.id.categories);
        list.setAdapter(new ArrayAdapter<String>(this, R.layout.category, categories) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.category, null);
                }
                TextView textview = (TextView) convertView.findViewById(R.id.category_name);
                String category = getItem(position);
                textview.setText(category);
                NumberPicker aislePicker = (NumberPicker) convertView.findViewById(R.id.aisle_picker);
                aislePicker.setMinValue(1);
                aislePicker.setMaxValue(20);
                aislePicker.setValue(aisles.get(category));
                return convertView;
            }
        });
    }

    public void renameStore(View view) {
        final TextView itemName = (TextView) findViewById(R.id.name);
        new DialogFragment() {
            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View view = View.inflate(getApplicationContext(), R.layout.dialog_rename, null);
                final EditText newItemName = (EditText) view.findViewById(R.id.item_name);
                newItemName.setText(itemName.getText());
                newItemName.setSelection(newItemName.getText().length());
                builder.setView(view)
                        .setTitle(R.string.rename_title)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int buttonId) {
                                String name = newItemName.getText().toString();
                                DatabaseHelper.getInstance(getActivity()).renameStore(id, name);
                                itemName.setText(name);
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, null);
                return builder.create();
            }
        }.show(getFragmentManager(), "rename");
    }
}
