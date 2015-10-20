package isu.cartpath;

import android.app.Application;


public class CartPath extends Application {
    ListReaderDbHelper dbHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        dbHelper = new ListReaderDbHelper(this);
    }
}
