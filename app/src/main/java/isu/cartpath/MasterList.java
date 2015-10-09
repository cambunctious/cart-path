package isu.cartpath;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
public class MasterList extends AppCompatActivity
{
    private ArrayList<String>masterList;
    private ArrayAdapter<String>adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_list);
        masterList.add("test");
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,masterList);
        ListView list = (ListView)findViewById(R.id.listView);
        list.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_master_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
      Iterates through master list to find item and removes the item from the ArrayList
     */
    public void removeItem(ArrayList<String>master,String item)
    {

    }

    /*
        Adds item to master list
     */
    public void addItem()
    {

    }
}
