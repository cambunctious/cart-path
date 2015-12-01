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

class ItemListAdapter extends SimpleCursorAdapter implements StickyListHeadersAdapter {

    private final LayoutInflater inflater;
    private static final int layout = R.layout.list_item;
    private final MasterList context;

    public ItemListAdapter(MasterList context, CartPath app) {
        super(context,
                layout,
                getCursor(context),
                new String[]{DatabaseHelper.Contract.Item.COLUMN_NAME_NAME},
                new int[]{R.id.item_name},
                0);
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    private static Cursor getCursor(MasterList context) {
        DatabaseHelper db = DatabaseHelper.getInstance(context);
        switch(context.groceryView) {
            case Aisle:
                return db.getAllItemsByAisle();
            case Category:
                return db.getAllItemsByCategory();
            default:
                return null;
        }
    }

    @Override
    public void bindView(@NonNull View view, Context c, @NonNull Cursor cursor) {
        super.bindView(view, c, cursor);
        CheckBox chk = (CheckBox)view.findViewById(R.id.in_cart_check);
        DatabaseHelper db = DatabaseHelper.getInstance(c);
        boolean checked = db.getChecked(cursor);
        chk.setChecked(checked);
        TextView itemName = (TextView)view.findViewById(R.id.item_name);
        String name = db.getName(cursor);
        itemName.setText(name);
    }

    public void resetCursor() {
        changeCursor(getCursor(context));
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
        Cursor item = (Cursor) getItem(position);
        boolean inCart = item.getInt(item.getColumnIndex(DatabaseHelper.Contract.Item.COLUMN_NAME_IN_CART)) == 1;
        String text;
        if(inCart)
            text = "Items in Cart";
        else {
            switch (context.groceryView) {
                case Aisle:
                    String aisle = item.getString(item.getColumnIndex(DatabaseHelper.Contract.Item.COLUMN_NAME_AISLE));
                    if(aisle.equals("-1"))
                        text = "Unknown Aisle";
                    else
                        text = "Aisle " + aisle;
                    break;
                case Category:
                    text = item.getString(item.getColumnIndex(DatabaseHelper.Contract.Item.COLUMN_NAME_CATEGORY));
                    break;
                default:
                    text = null;
            }
        }
        holder.text.setText(text);
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        Cursor item = (Cursor) getItem(position);
        if(item.getInt(item.getColumnIndex(DatabaseHelper.Contract.Item.COLUMN_NAME_IN_CART)) == 1)
            return -2;
        switch (context.groceryView) {
            case Aisle:
                return item.getLong(item.getColumnIndex(DatabaseHelper.Contract.Item.COLUMN_NAME_AISLE));
            case Category:
                return item.getString(item.getColumnIndex(DatabaseHelper.Contract.Item.COLUMN_NAME_CATEGORY)).hashCode();
            default:
                return -1; // unreachable
        }
    }

    class HeaderViewHolder {
        TextView text;
    }
}
