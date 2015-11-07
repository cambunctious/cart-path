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
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

class ListAdapter extends SimpleCursorAdapter implements StickyListHeadersAdapter {

    private final LayoutInflater inflater;
    private static final int layout = R.layout.list_item;
    private Context context;

    public ListAdapter(Context context, CartPath app) {
        super(context,
                layout,
                DatabaseHelper.getInstance(context).getAllItems(),
                new String[]{DatabaseHelper.Contract.Item.COLUMN_NAME_NAME},
                new int[]{R.id.item_name},
                0);
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public void bindView(@NonNull View view, Context c, @NonNull Cursor cursor) {
        super.bindView(view, c, cursor);
        CheckBox chk = (CheckBox)view.findViewById(R.id.in_cart_check);
        DatabaseHelper db = DatabaseHelper.getInstance(context);
        boolean checked = db.getChecked(cursor);
        chk.setChecked(checked);
        TextView itemName = (TextView)view.findViewById(R.id.item_name);
        String name = db.getName(cursor);
        itemName.setText(name);
    }

    public void resetCursor() {
        swapCursor(DatabaseHelper.getInstance(context).getAllItems())
                .close();
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = inflater.inflate(R.layout.aisle_header, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.aisle_title);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        //set header text as first char in name
        Cursor item = (Cursor) getItem(position);
        String aisle = item.getString(item.getColumnIndex(DatabaseHelper.Contract.Item.COLUMN_NAME_AISLE));
        String text;
        if(aisle.equals("-1"))
            text = "Other";
        else
            text = "Aisle " + aisle;
        holder.text.setText(text);
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        Cursor item = (Cursor) getItem(position);
        String aisle = item.getString(item.getColumnIndex(DatabaseHelper.Contract.Item.COLUMN_NAME_AISLE));
        return Long.parseLong(aisle);
    }

    class HeaderViewHolder {
        TextView text;
    }
}
