package com.example.invenroryitems;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.invenroryitems.Data.InventoryNumberContract.*;

public class InventoryCursorAdapter extends CursorAdapter {
    public InventoryCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.inventory_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvDirectionOfWork = view.findViewById(R.id.directionOfWorkTextView);
        TextView tvStatus = view.findViewById(R.id.statusTextView);
        //TextView tvIssuseBy = view.findViewById(R.id.textViewIssuedBy);

        String directionOfWork = cursor.getString(cursor.getColumnIndexOrThrow(InventoryEntry.COLUMN_DIRECTION_OF_WORK));
        String status = cursor.getString(cursor.getColumnIndexOrThrow(InventoryEntry.COLUMN_ISSUED_BY));

        tvDirectionOfWork.setText(directionOfWork);
        tvStatus.setText(status);
    }
}
