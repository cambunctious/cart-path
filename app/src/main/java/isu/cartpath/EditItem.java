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
import android.widget.EditText;
import android.widget.TextView;

public class EditItem extends AppCompatActivity {

    private long id;
    private TextView itemName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        final CartPath app = (CartPath) getApplication();

        Intent intent = getIntent();
        id = intent.getLongExtra("id", -1);

        Cursor item = app.dbHelper.getItem(id);
        item.moveToFirst();

        itemName = (TextView) findViewById(R.id.item_name);
        itemName.setText(app.dbHelper.getName(item));
        item.close();
    }

    public void renameItem(View view) {
        new DialogFragment() {
            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View view = inflater.inflate(R.layout.dialog_rename, null);
                final EditText newItemName = (EditText) view.findViewById(R.id.item_name);
                newItemName.setText(itemName.getText());
                builder.setView(view)
                        .setTitle(R.string.rename_title)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int buttonId) {
                                String name = newItemName.getText().toString();
                                ((CartPath) getApplication()).db.execSQL("UPDATE " +
                                        ListReaderContract.Item.TABLE_NAME +
                                        " SET " + ListReaderContract.Item.COLUMN_NAME_NAME + "='" + name + "'" +
                                        " WHERE " + ListReaderContract.Item._ID + "=" + Long.toString(id));
                                itemName.setText(name);
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, null);
                return builder.create();
            }
        }.show(getFragmentManager(), "rename");
    }

    public void deleteItem(View view) {
        ((CartPath) getApplication()).dbHelper.deleteItem(id);
        finish();
    }
}
