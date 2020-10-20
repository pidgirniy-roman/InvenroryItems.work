package com.example.invenroryitems.Data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class InventoryNumberContract {

    private InventoryNumberContract(){
    }

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "inventory";

    public static final String SCHEME = "content://";
    public static final String AUTHORITY = "com.example.invenroryitems";
    public static final String PATH_INVENTORY = "inventar"; //TABLE_NAME

    public static final Uri BASE_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY);



    public static final class InventoryEntry implements BaseColumns {
        public static final String TABLE_NAME = "inventar";

        /**
         * CULUMN_DIRECTION_OF_WORK - подразделение ТТ/ЛО/ЦО/РМ/РД/ДФ-сотрудник
         * CULUMN_NOTEBOOK_INV - Инвентарный номер выдаваемой техники
         * CULUMN_STATUS - Статус готовности техники(коплекта техники)
         * CULUMN_ISSUED_BY - дата выдачи техники(коплекта техники)
         * CULUMN_COMMENT - коментарий по технике(коплекта техники)
         */
        public static final String _ID  = BaseColumns._ID;
        public static final String COLUMN_DIRECTION_OF_WORK = "direction";
        public static final String COLUMN_TECHNICS_INV = "notebook";
        public static final String COLUMN_STATUS = "status";
        public static final String COLUMN_ISSUED_BY = "issuedBy";
        public static final String COLUMN_COMMENT = "comment";

//        public static final int SPINNER_UNKNOWN = 0;
//        public static final int SPINNER_TT = 1;
//        public static final int SPINNER_LO = 2;
//        public static final int SPINNER_CO = 3;
//        public static final int SPINNER_RM = 4;
//        public static final int SPINNER_RD = 5;
//        public static final int SPINNER_DF = 6;

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_INVENTORY);
        public static final String CONTENT_MULTIPLE_ITEMS = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY + "/" + PATH_INVENTORY;
        public static final String CONTENT_SINGL_ITEM = ContentResolver.ANY_CURSOR_ITEM_TYPE + "/" + AUTHORITY + "/" + PATH_INVENTORY;


    }
}
