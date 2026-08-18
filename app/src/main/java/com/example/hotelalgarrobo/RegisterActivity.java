package com.example.hotelalgarrobo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText etUsuario = findViewById(R.id.etRegistroUsuario);
        EditText etCorreo = findViewById(R.id.etRegistroCorreo);
        EditText etPassword = findViewById(R.id.etRegistroPassword);
        Button btnRegistrar = findViewById(R.id.btnGuardarRegistro);

        if (btnRegistrar != null) {
            btnRegistrar.setOnClickListener(v -> {
                String usuario = etUsuario.getText().toString().trim();
                String correo = etCorreo.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                // 1. Validación básica de campos vacíos
                if (usuario.isEmpty() || correo.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 🚨 NUEVA VALIDACIÓN DE QA: Contraseña Segura (Regex)
                // Explicación del patrón:
                // (?=.*[0-9]) -> Al menos un número
                // (?=.*[A-Z]) -> Al menos una mayúscula
                // (?=.*[@#$%^&+=*!_]) -> Al menos un carácter especial
                // .{8,} -> Mínimo 8 caracteres de largo
                String patronPassword = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=*!_]).{8,}$";

                if (!password.matches(patronPassword)) {
                    Toast.makeText(RegisterActivity.this,
                            "La contraseña debe tener al menos 8 caracteres, una mayúscula, un número y un carácter especial (@#$%^*!_)",
                            Toast.LENGTH_LONG).show();
                    return; // Detiene el registro si no es segura
                }

                // 💾 Si pasa el filtro, se guarda de forma segura
                SharedPreferences preferencias = getSharedPreferences("DatosUsuario", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferencias.edit();

                editor.putString("usuario", usuario);
                editor.putString("correo", correo);
                editor.putString("password", password);
                editor.apply();

                Toast.makeText(RegisterActivity.this, "¡Usuario registrado con éxito!", Toast.LENGTH_SHORT).show();
                finish();
            });
        }
    }
}