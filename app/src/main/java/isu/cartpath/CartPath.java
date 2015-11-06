package isu.cartpath;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;


public class CartPath extends Application {
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    SQLiteDatabase knownDB;

    @Override
    public void onCreate() {
        super.onCreate();
        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();
        knownDB = new GroceryKnowledge(this).getReadableDatabase();
    }
}
