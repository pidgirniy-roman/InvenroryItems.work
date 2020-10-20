package com.example.invenroryitems.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.example.invenroryitems.Data.InventoryNumberContract.*;


public class InventoryContentProvider extends ContentProvider {

    InventoryDbOpenHelper dbOpenHelper;

    private static final int INVENTORY = 111;
    private static final int INVENTORY_ID = 222;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        /**
         * для работы со всей таблицей
         */
        uriMatcher.addURI(InventoryNumberContract.AUTHORITY,
                InventoryNumberContract.PATH_INVENTORY, INVENTORY);

        /**
         * Для работы с одной строкой таблицы
         */
        uriMatcher.addURI(InventoryNumberContract.AUTHORITY,
                InventoryNumberContract.PATH_INVENTORY + "/#", INVENTORY_ID);
    }

    @Override
    public boolean onCreate() {
        dbOpenHelper = new InventoryDbOpenHelper(getContext());
        return true;
    }

    /**
     * Uri - Unified Resource Identifier
     * content://com.example.invenroryitems/inventory
     */

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor cursor;

        int match = uriMatcher.match(uri);

        switch (match) {
            case INVENTORY:
                cursor = db.query(InventoryEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;

            case INVENTORY_ID:
                selection = InventoryEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(InventoryEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;

            default:
                Toast.makeText(getContext(), "Incorrect URI", Toast.LENGTH_LONG).show();
                throw new IllegalArgumentException("Can`t query incorrect URI " + uri);
        }

        //даем знать для чего используется этот cursor для обращения со всей таблицей или с одной строкой
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        String direction = values.getAsString(InventoryEntry.COLUMN_DIRECTION_OF_WORK);
        if (direction == null){
            throw new IllegalArgumentException("You have input direction");
        }

        String technics = values.getAsString(InventoryEntry.COLUMN_TECHNICS_INV);
        if (technics == null){
            throw new IllegalArgumentException("You have input technics");
        }

        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);

        switch (match) {
            case INVENTORY:
                Long id = db.insert(InventoryEntry.TABLE_NAME, null, values);
                if (id == -1) {
                    Log.e("insertMethod", "Insertion of data in the table failed for " + uri);
                    return null;
                }

                getContext().getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);

            default:
                throw new IllegalArgumentException("Insertion of data in the table failed for" + uri);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        int matcher = uriMatcher.match(uri);
        int rowsDeleted;

        switch (matcher){
            case INVENTORY:
                rowsDeleted = db.delete(InventoryEntry.TABLE_NAME, selection, selectionArgs);
                break;

            case INVENTORY_ID:
                selection = InventoryEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = db.delete(InventoryEntry.TABLE_NAME, selection, selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Can`t delete this URI " + uri);
        }
        if (rowsDeleted != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        if (values.containsKey(InventoryEntry.COLUMN_DIRECTION_OF_WORK)){
            String direction = values.getAsString(InventoryEntry.COLUMN_DIRECTION_OF_WORK);
            if (direction == null){
                throw new IllegalArgumentException("You have input direction");
            }
        }

        if (values.containsKey(InventoryEntry.COLUMN_TECHNICS_INV)){
            String technics = values.getAsString(InventoryEntry.COLUMN_TECHNICS_INV);
            if (technics == null){
                throw new IllegalArgumentException("You have input technics");
            }
        }

        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        int matcher = uriMatcher.match(uri);
        int rowsUpdated;

        switch (matcher){
            case INVENTORY:
                rowsUpdated = db.update(InventoryEntry.TABLE_NAME, values, selection, selectionArgs);
                break;

            case INVENTORY_ID:
                selection = InventoryEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                rowsUpdated = db.update(InventoryEntry.TABLE_NAME, values, selection, selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Can`t query incorrect URI " + uri);
        }
        if (rowsUpdated != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public String getType(Uri uri) {

        int matcher = uriMatcher.match(uri);

        switch (matcher){
            case INVENTORY:
                return InventoryEntry.CONTENT_MULTIPLE_ITEMS;
            case INVENTORY_ID:
                return InventoryEntry.CONTENT_SINGL_ITEM;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

}
