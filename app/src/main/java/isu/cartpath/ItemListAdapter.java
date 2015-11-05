package isu.cartpath;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

class ItemListAdapter extends SimpleCursorAdapter {

    private final LayoutInflater inflater;
    private static final int layout = R.layout.list_item;
    private final CartPath app;
    private final String aisle;

    public ItemListAdapter(Context context, CartPath app, String aisle) {
        super(context,
                layout,
                app.dbHelper.getAllItems(),
                new String[]{ListReaderContract.Item.COLUMN_NAME_NAME},
                new int[]{R.id.item_name},
                0);
        this.inflater = LayoutInflater.from(context);
        this.app = app;
        this.aisle = aisle;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return inflater.inflate(layout, null);
    }

    @Override
    public void bindView(@NonNull View view, Context c, @NonNull Cursor cursor) {
        super.bindView(view, c, cursor);
        CheckBox chk = (CheckBox)view.findViewById(R.id.in_cart_check);
        boolean checked = app.dbHelper.getChecked(cursor);
        chk.setChecked(checked);
        TextView itemName = (TextView)view.findViewById(R.id.item_name);
        itemName.setText(app.dbHelper.getName(cursor));
    }

    public void resetCursor() {
        swapCursor(app.dbHelper.getAisle(aisle))
                .close();
    }
}
