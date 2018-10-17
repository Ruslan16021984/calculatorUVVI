package kalkull.ruslanann.kalkulator24.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by CARD on 23.02.2016.
 */
public class ToDoDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "new2.db";
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_TABLE = "todos";
    private static final String TABLE_TRANS = "trans";

    private static final String DATABASE_DATA1 = "datatrans";

   // поля таблицы
    public static final String COLUMN_ID = "_id";

    public static final String COLUMN_SUMMARY = "summary";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_DESCRIPTIONA = "descriptiona";
    public static final String COLUMN_DESCRIPTIONB = "descriptionb";
    public static final String COLUMN_DESCRIPTIONC = "descriptionc";
    public static final String COLUMN_DESCRIPTIOND = "descriptiond";

    //вторая колонка
    public static final String TRANS_ID = "_id";
    public static final String COLUMN_AMPER = "amper";
    public static final String COLUMN_GRADUS = "gradus";
    public static final String COLUMN_PROCENT = "procent";
    public static final String COLUMN_AMPERN = "ampern";
    public static final String COLUMN_VOLTN = "voltn";
    public static final String COLUMN_GRADUSN = "gradusn";
    public static final String COLUMN_FIRST = "firstn";

    // запрос на создание базы данных
    private static final String DATABASE_CREATE = "create table "
            + DATABASE_TABLE + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_SUMMARY + " text not null,"
            + COLUMN_DESCRIPTION + " text not null," + COLUMN_DESCRIPTIONA + " text not null,"+
            COLUMN_DESCRIPTIONB + " text not null," + COLUMN_DESCRIPTIONC + " text not null,"
            + COLUMN_DESCRIPTIOND + " text not null" + ");";

    private static final String DATABASE_CREATEI = "create table "
            + TABLE_TRANS + "(" + TRANS_ID
            + " integer primary key autoincrement, " + COLUMN_SUMMARY + " text not null,"
            + COLUMN_AMPER + " text not null," + COLUMN_GRADUS + " text not null,"+
            COLUMN_PROCENT + " text not null," + COLUMN_AMPERN + " text not null," +
            COLUMN_VOLTN + " text not null," + COLUMN_GRADUSN + " text not null," +
            COLUMN_FIRST + " text not null" + ");";



    


    public ToDoDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
        db.execSQL(DATABASE_CREATEI);
            }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        Log.w(ToDoDatabase.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS todos");
        db.execSQL("DROP TABLE IF EXISTS trans");
               onCreate(db);
    }
    /**
     * Создаёт новый элемент списка дел. Если создан успешно - возвращается
     * номер строки rowId, иначе -1
     */
    public long createNewTodo(String summery, String description, String descriptiona, String descriptionb,
                              String descriptionc, String descriptiond){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues initialValues = createContentValues(summery, description, descriptiona, descriptionb,
                descriptionc, descriptiond);
        long row = db.insert(DATABASE_TABLE, null, initialValues);
        db.close();
        return row;
    }
    public long createNewTrans(String summery, String amper, String gradus, String procent,
                               String ampern, String voltn,
                               String gradusn, String firstn) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues initialValues = createContentTrans(summery, amper, gradus, procent, ampern,
                voltn, gradusn, firstn);
        long row = db.insert(TABLE_TRANS, null, initialValues);
        db.close();
        return row;
    }

    /**
     * Обновляет список
     */



    public boolean updateTodo(long rowId, String summery, String description,
                              String descriptiona, String descriptionb,
                              String descriptionc, String descriptiond){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues updateValues = createContentValues(summery, description, descriptiona, descriptionb,
                descriptionc, descriptiond);
        return db.update(DATABASE_TABLE, updateValues, COLUMN_ID + "=" + rowId, null) > 0;

    }
    public boolean updateTrans(long rowId, String summery, String amper,
                               String gradus, String procent, String ampern, String voltn,
                               String gradusn, String first){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues updateValues = createContentTrans(summery, amper, gradus, procent, ampern, voltn,
                gradusn, first);
        return db.update(TABLE_TRANS, updateValues, TRANS_ID + "=" + rowId, null) > 0;

    }


    /**
     * Удаляет элемент списка
     */
    public void deleteTodo(long rowId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DATABASE_TABLE, COLUMN_ID + "=" + rowId, null);
        db.close();
    }
    public void deleteTans(long rowId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TRANS, TRANS_ID + "=" + rowId, null);
        db.close();
    }

    /**
     * Возвращает курсор со всеми элементами списка дел
     *
     * @return курсор с результатами всех записей
     */
    public Cursor getAllTodos() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.query(DATABASE_TABLE, new String[] { COLUMN_ID,
                        COLUMN_SUMMARY, COLUMN_DESCRIPTION, COLUMN_DESCRIPTIONA, COLUMN_DESCRIPTIONB,
                COLUMN_DESCRIPTIONC, COLUMN_DESCRIPTIOND}, null,
                null, null, null, null);
    }
    public Cursor getAllTrans() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.query(TABLE_TRANS, new String[] { TRANS_ID,
                        COLUMN_SUMMARY, COLUMN_AMPER, COLUMN_GRADUS, COLUMN_PROCENT, COLUMN_AMPERN,
                 COLUMN_VOLTN, COLUMN_GRADUSN, COLUMN_FIRST}, null,
                null, null, null, null);
    }

    /**
     * Возвращает курсор с указанной записи
     */
    public Cursor getTodo(long rowId){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCursor = db.query(true, DATABASE_TABLE, new String[]{COLUMN_ID, COLUMN_SUMMARY,
                        COLUMN_DESCRIPTION, COLUMN_DESCRIPTIONA, COLUMN_DESCRIPTIONB,
                        COLUMN_DESCRIPTIONC, COLUMN_DESCRIPTIOND}, COLUMN_ID + "=" + rowId, null,
                null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }
    public Cursor getTrans(long rowId){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCursor = db.query(true, TABLE_TRANS, new String[]{TRANS_ID,
                        COLUMN_SUMMARY, COLUMN_AMPER, COLUMN_GRADUS, COLUMN_PROCENT, COLUMN_AMPERN,
                COLUMN_VOLTN, COLUMN_GRADUSN, COLUMN_FIRST},
                COLUMN_ID + "=" + rowId, null,
                null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }

    /*
	 * Создаёт пару ключ-значение и записывает в базу
	 */
    private ContentValues createContentValues(String summary,
                                              String description, String descriptiona, String descriptionb,
                                              String descriptionc, String descriptiond) {
        ContentValues values = new ContentValues();

        values.put(COLUMN_SUMMARY, summary);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_DESCRIPTIONA, descriptiona);
        values.put(COLUMN_DESCRIPTIONB, descriptionb);
        values.put(COLUMN_DESCRIPTIONC, descriptionc);
        values.put(COLUMN_DESCRIPTIOND, descriptiond);
        return values;
    }
    private ContentValues createContentTrans(String summery, String amper, String gradus, String procent,
                                             String ampern, String voltn, String gradusn, String firstn) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_SUMMARY, summery);
        values.put(COLUMN_AMPER, amper);
        values.put(COLUMN_GRADUS, gradus);
        values.put(COLUMN_PROCENT, procent);
        values.put(COLUMN_AMPERN, ampern);
        values.put(COLUMN_VOLTN, voltn);
        values.put(COLUMN_GRADUSN, gradusn);
        values.put(COLUMN_FIRST, firstn);

        return values;
    }

}
