package com.example.parcial;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mydatabase.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_PRODUCTS = "products";
    private static final String KEY_ID = "id";
    private static final String USERNAME_COLUMN = "username";
    private static final String PASSWORD_COLUMN = "password";
    private static final String PRODUCT_COLUMN = "product";
    private static final String QUANTITY_COLUMN = "quantity";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_PRODUCTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USERNAME_COLUMN + " TEXT, "
                + PASSWORD_COLUMN + " TEXT, "
                + PRODUCT_COLUMN + " TEXT, "
                + QUANTITY_COLUMN + " INTEGER)";
        db.execSQL(createTable);

        String insertProduct = "INSERT INTO " + TABLE_PRODUCTS + "("
                + USERNAME_COLUMN + ", "
                + PASSWORD_COLUMN + ", "
                + PRODUCT_COLUMN + ", "
                + QUANTITY_COLUMN + ") VALUES ('admin', 'password', 'product1', 10)";
        db.execSQL(insertProduct);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }

    public boolean createProduct(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERNAME_COLUMN, username);
        contentValues.put(PASSWORD_COLUMN, password);
        long result = db.insert(TABLE_PRODUCTS, null, contentValues);
        return result != -1;
    }
    public boolean updateProduct(int id, String username, String password, String product, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERNAME_COLUMN, username);
        contentValues.put(PASSWORD_COLUMN, password);
        contentValues.put(PRODUCT_COLUMN, product);
        contentValues.put(QUANTITY_COLUMN, quantity);
        int result = db.update(TABLE_PRODUCTS, contentValues, KEY_ID + "=?", new String[]{String.valueOf(id)});
        return result > 0;
    }
    public boolean deleteProduct(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_PRODUCTS, KEY_ID + "=?", new String[]{String.valueOf(id)});
        return result > 0;
    }
    public Cursor getAllProducts() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {KEY_ID, USERNAME_COLUMN, PASSWORD_COLUMN, PRODUCT_COLUMN, QUANTITY_COLUMN};
        Cursor cursor = db.query(TABLE_PRODUCTS, columns, null, null, null, null, null);
        return cursor;
    }
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Consultar el usuario en la base de datos
        String[] columns = { KEY_ID };
        String selection = USERNAME_COLUMN + " = ?" + " AND " + PASSWORD_COLUMN + " = ?";
        String[] selectionArgs = { username, password };
        Cursor cursor = db.query(TABLE_PRODUCTS, columns, selection, selectionArgs, null, null, null);

        // Verificar si se encontró algún usuario
        boolean isValidUser = (cursor.getCount() > 0);

        // Cerrar la conexión con la base de datos
        cursor.close();
        db.close();

        return isValidUser;
    }
    public void insertUser(String username, String password,String product,String quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERNAME_COLUMN, username);
        values.put(PASSWORD_COLUMN , password);
        values.put(PRODUCT_COLUMN , product);
        values.put(QUANTITY_COLUMN , quantity);
        db.insert(TABLE_PRODUCTS, null, values);
        db.close();
    }
    public Cursor getProductById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {KEY_ID, USERNAME_COLUMN, PASSWORD_COLUMN, PRODUCT_COLUMN, QUANTITY_COLUMN};
        String selection = KEY_ID + "=?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.query(TABLE_PRODUCTS, columns, selection, selectionArgs, null, null, null);
        return cursor;
    }


}
