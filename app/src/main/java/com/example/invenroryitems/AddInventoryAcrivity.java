package com.example.invenroryitems;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.invenroryitems.Data.InventoryNumberContract.InventoryEntry.*;

public class AddInventoryAcrivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int EDIT_INVENTORY_LOADER = 111;
    Uri currentInventoryUri;

    private EditText editTextDirectionOfWork;
    private EditText editTextTechniksInv;
    private EditText editTextStatus;
    private EditText editTextIssuedBy;
    private EditText editTextComment;
//    private Spinner spinnerDirectionOfWork;
//    private int directionOfWork = 0;
//    private ArrayAdapter spinnerAdapter;


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        if (currentInventoryUri == null){
            MenuItem menuItem = menu.findItem(R.id.delete_inventory);
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_inventory_acrivity);

        Intent intent = getIntent();
        currentInventoryUri = intent.getData();

        if (currentInventoryUri == null) {
            setTitle("Add a Inventory");
            invalidateOptionsMenu();
        } else {
            setTitle("Edit the Inventory");
            getSupportLoaderManager().initLoader(EDIT_INVENTORY_LOADER, null, this);
        }

        editTextDirectionOfWork = findViewById(R.id.editTextDirectionOfWork);
        editTextTechniksInv = findViewById(R.id.editTextTechnicsInv);
        editTextStatus = findViewById(R.id.editTextStatus);
        editTextIssuedBy = findViewById(R.id.editTextIssuedBy);
        editTextComment = findViewById(R.id.editTextComment);
//        spinnerDirectionOfWork = findViewById(R.id.spinnerDirectionOfWork);
//
//
//        spinnerAdapter = ArrayAdapter.createFromResource(this,
//                R.array.direction_of_work_list, android.R.layout.simple_spinner_item);
//        spinnerAdapter.setDropDownViewResource(
//                android.R.layout.simple_spinner_dropdown_item);
//        spinnerDirectionOfWork.setAdapter(spinnerAdapter);
//
//        /**
//         * Логика для выбора элементов спинера
//         */
//        spinnerDirectionOfWork.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String selectedDirection = (String) parent.getItemAtPosition(position);
//                if (!TextUtils.isEmpty(selectedDirection)) {
//                    if (selectedDirection.equals("TT")) {
//                        directionOfWork = SPINNER_TT;
//                    } else if (selectedDirection.equals("LO")) {
//                        directionOfWork = SPINNER_LO;
//                    } else if (selectedDirection.equals("CO")) {
//                        directionOfWork = SPINNER_CO;
//                    } else if (selectedDirection.equals("RM")) {
//                        directionOfWork = SPINNER_RM;
//                    } else if (selectedDirection.equals("RD")) {
//                        directionOfWork = SPINNER_RD;
//                    } else if (selectedDirection.equals("DF")) {
//                        directionOfWork = SPINNER_DF;
//                    } else {
//                        directionOfWork = SPINNER_UNKNOWN;
//                    }
//                }
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                directionOfWork = 0;
//            }
//        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_inventory_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_inventory:
                saveInventory();
                return true;

            case R.id.delete_inventory:
                showDeleteInventoryDialog();
                return true;

            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveInventory() {

        String directionOfWork = editTextDirectionOfWork.getText().toString().trim();
        String technicsInv = editTextTechniksInv.getText().toString().trim();
        String status = editTextStatus.getText().toString().trim();
        String issusedBy = editTextIssuedBy.getText().toString().trim();
        String comment = editTextComment.getText().toString().trim();

        if (TextUtils.isEmpty(directionOfWork)) {
            Toast.makeText(this, "Введите ТТ/ЛО/ЦО-сотрудник", Toast.LENGTH_LONG).show();
            return;
        } else if (TextUtils.isEmpty(technicsInv)) {
            Toast.makeText(this, "Введите ИНВ № Техники", Toast.LENGTH_LONG).show();
            return;
        } else if (TextUtils.isEmpty(issusedBy)) {
            Toast.makeText(this, "Введите Дату выдачи", Toast.LENGTH_LONG).show();
            return;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DIRECTION_OF_WORK, directionOfWork);
        contentValues.put(COLUMN_TECHNICS_INV, technicsInv);
        contentValues.put(COLUMN_STATUS, status);
        contentValues.put(COLUMN_ISSUED_BY, issusedBy);
        contentValues.put(COLUMN_COMMENT, comment);

        if (currentInventoryUri == null) {
            ContentResolver contentResolver = getContentResolver();
            Uri uri = contentResolver.insert(CONTENT_URI, contentValues);
            if (uri == null) {
                Toast.makeText(this, "Insertion of data in the table failed for",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Data saved", Toast.LENGTH_LONG).show();
            }
        } else {
            int rowsChange = getContentResolver().update(currentInventoryUri, contentValues, null, null);
            if (rowsChange == 0) {
                Toast.makeText(this,
                        "Saving of data in the table failed", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this,
                        "Inventory updated", Toast.LENGTH_LONG).show();
            }
        }


    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String[] prijection = {
                _ID,
                COLUMN_DIRECTION_OF_WORK,
                COLUMN_TECHNICS_INV,
                COLUMN_STATUS,
                COLUMN_ISSUED_BY,
                COLUMN_COMMENT
        };
        return new CursorLoader(
                this,
                currentInventoryUri,
                prijection,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        if (cursor.moveToFirst()) {
            int directionOfWorkColumnInvex = cursor.getColumnIndex(COLUMN_DIRECTION_OF_WORK);
            int technicsInvColumnInvex = cursor.getColumnIndex(COLUMN_TECHNICS_INV);
            int statusColumnInvex = cursor.getColumnIndex(COLUMN_STATUS);
            int issuseByColumnInvex = cursor.getColumnIndex(COLUMN_ISSUED_BY);
            int commentColumnInvex = cursor.getColumnIndex(COLUMN_COMMENT);

            String directionOfWork = cursor.getString(directionOfWorkColumnInvex);
            String technicsInv = cursor.getString(technicsInvColumnInvex);
            String status = cursor.getString(statusColumnInvex);
            String issuseBy = cursor.getString(issuseByColumnInvex);
            String comment = cursor.getString(commentColumnInvex);

            editTextDirectionOfWork.setText(directionOfWork);
            editTextTechniksInv.setText(technicsInv);
            editTextStatus.setText(status);
            editTextIssuedBy.setText(issuseBy);
            editTextComment.setText(comment);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
    }

    private void showDeleteInventoryDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Вы действительно хотите удалить ");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteInventory();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (dialogInterface != null){
                    dialogInterface.dismiss();
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteInventory(){
        if (currentInventoryUri != null){
            int rowsDeleted = getContentResolver().delete(currentInventoryUri, null, null);

            if (rowsDeleted == 0){
                Toast.makeText(this,
                        "Deleting of data from the table failed", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this,
                        "Inventory is deleted", Toast.LENGTH_LONG).show();
            }
            finish();
        }
    }
}