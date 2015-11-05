package isu.cartpath;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class AisleAdapter extends SimpleCursorAdapter {

    private final LayoutInflater inflater;
    private static final int layout = R.layout.aisle;
    private final CartPath app;

    public AisleAdapter(Context context, CartPath app) {
        super(context,
                layout,
                app.dbHelper.getAllAisles(),
                new String[]{ ListReaderContract.Item._ID },
                new int[]{ R.id.aisle_title },
                0);
        this.inflater = LayoutInflater.from(context);
        this.app = app;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return inflater.inflate(layout, null);
    }

    @Override
    public void bindView(@NonNull View view, Context c, @NonNull Cursor cursor) {
        super.bindView(view, c, cursor);
        String aisle = cursor.getString(cursor.getColumnIndex(ListReaderContract.Item._ID));
        ListView list = (ListView) view.findViewById(R.id.items);
        ItemListAdapter listAdapter = new ItemListAdapter(c, app, aisle);
        list.setAdapter(listAdapter);
    }

    @Override
    public void setViewText(@NonNull TextView v, String text) {
        text = "Aisle " + text;
        super.setViewText(v, text);
    }

    public void resetCursor() {
        swapCursor(app.dbHelper.getAllAisles())
                .close();
    }
}
