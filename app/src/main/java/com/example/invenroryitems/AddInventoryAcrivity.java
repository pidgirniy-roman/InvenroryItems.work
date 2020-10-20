package com.example.invenroryitems;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.invenroryitems.Data.InventoryNumberContract;

import static com.example.invenroryitems.Data.InventoryNumberContract.InventoryEntry.*;

public class AddInventoryAcrivity extends AppCompatActivity {

    private EditText editTextDirectionOfWork;
    private EditText editTextTechniksInv;
    private EditText editTextStatus;
    private EditText editTextIssuedBy;
    private EditText editTextComment;
//    private Spinner spinnerDirectionOfWork;
//    private int directionOfWork = 0;
//    private ArrayAdapter spinnerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_inventory_acrivity);

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
                insertInventory();
                return true;

            case R.id.delete_inventory:
                return true;

            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void insertInventory() {

        String directionOfWork = editTextDirectionOfWork.getText().toString().trim();
        String technicsInv = editTextTechniksInv.getText().toString().trim();
        String status = editTextStatus.getText().toString().trim();
        String issusedBy = editTextIssuedBy.getText().toString().trim();
        String comment = editTextComment.getText().toString().trim();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DIRECTION_OF_WORK, directionOfWork);
        contentValues.put(COLUMN_TECHNICS_INV, technicsInv);
        contentValues.put(COLUMN_STATUS, status);
        contentValues.put(COLUMN_ISSUED_BY, issusedBy);
        contentValues.put(COLUMN_COMMENT, comment);

        ContentResolver contentResolver = getContentResolver();
        Uri uri = contentResolver.insert(CONTENT_URI, contentValues);
        if (uri == null){
            Toast.makeText(this, "Insertion of data in the table failed for", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, "Data saved", Toast.LENGTH_LONG).show();
        }
    }
}