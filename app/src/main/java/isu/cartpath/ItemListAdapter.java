package isu.cartpath;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import java.util.ArrayList;

public class ItemListAdapter extends ArrayAdapter<Item> {
    Context context;
    private ArrayList<Item> items;
    //int layoutResourceId;

    public ItemListAdapter(Context context, int layoutResourceId, ArrayList<Item> items) {
        super(context, layoutResourceId, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_item, parent, false);
        CheckBox chk = (CheckBox)rowView.findViewById(R.id.cartCheck);
        Item item = items.get(position);
        chk.setText(item.name);
        chk.setChecked(item.inCart);
        return rowView;
    }
}
