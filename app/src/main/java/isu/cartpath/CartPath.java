package isu.cartpath;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;


public class CartPath extends Application {
    ListReaderDbHelper dbHelper;
    SQLiteDatabase db;

    @Override
    public void onCreate() {
        super.onCreate();
        dbHelper = new ListReaderDbHelper(this);
        db = dbHelper.getWritableDatabase();
    }
}
