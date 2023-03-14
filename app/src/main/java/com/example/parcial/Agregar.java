package com.example.parcial;



import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Agregar extends AppCompatActivity {
    private EditText editTextNombre;
    private EditText editTextPassword;
    private EditText editTextProduct;
    private EditText editTextQuantity;
    private Button buttonAgregar;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);

        editTextNombre = findViewById(R.id.edit_name);
        editTextPassword = findViewById(R.id.edit_password);
        editTextQuantity = findViewById(R.id.edit_phone);
        editTextProduct = findViewById(R.id.edit_email);

        buttonAgregar = findViewById(R.id.button_save);


        databaseHelper = new DatabaseHelper(this);

        buttonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextNombre.getText().toString();
                String password = editTextPassword.getText().toString();
                String product = editTextPassword.getText().toString();
                String quantity = editTextPassword.getText().toString();
                databaseHelper.insertUser(username, password, product , quantity);
                Toast.makeText(Agregar.this, "Producto cargado , precio $5000", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}

