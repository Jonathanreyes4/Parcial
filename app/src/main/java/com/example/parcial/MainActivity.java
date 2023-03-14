package com.example.parcial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameEditText = findViewById(R.id.editTextUsername);
        passwordEditText = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.buttonLogin);

        databaseHelper = new DatabaseHelper(this);

        // Crear un usuario en la base de datos
        databaseHelper.createProduct("admin", "password");

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Verificar las credenciales del usuario
                boolean isValidUser = databaseHelper.checkUser(username, password);

                if (isValidUser) {
                    // El usuario ha iniciado sesión correctamente
                    Intent intent = new Intent(MainActivity.this, Agregar.class);
                    startActivity(intent);
                    Toast.makeText(MainActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                } else {
                    // El usuario no ha iniciado sesión correctamente. Borrar los campos de usuario y contraseña.
                    usernameEditText.setText("");
                    passwordEditText.setText("");
                    Toast.makeText(MainActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void viewData() {
        DatabaseHelper db = new DatabaseHelper(this);
        SQLiteDatabase database = db.getWritableDatabase();

        String query = "SELECT * FROM products";
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String product = cursor.getString(1);
                int quantity = cursor.getInt(2);
                String username = cursor.getString(3);
                String password = cursor.getString(4);

                Log.d("MainActivity", "Product: " + product + ", Quantity: " + quantity + ", Username: " + username + ", Password: " + password);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
    }

}
