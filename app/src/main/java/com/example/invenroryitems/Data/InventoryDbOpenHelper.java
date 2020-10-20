package com.example.invenroryitems.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.invenroryitems.Data.InventoryNumberContract.*;
import static com.example.invenroryitems.Data.InventoryNumberContract.InventoryEntry.*;

public class InventoryDbOpenHelper extends SQLiteOpenHelper {
    public InventoryDbOpenHelper(Context context) {
        super(context, DATABASE_NAME,
                null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_INVENTORY_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_DIRECTION_OF_WORK + " text,"
                + COLUMN_TECHNICS_INV + " text,"
                + COLUMN_STATUS + " text,"
                + COLUMN_ISSUED_BY + " text,"
                + COLUMN_COMMENT + " text" +  ")";

        db.execSQL(CREATE_INVENTORY_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        onCreate(db);
    }
}
