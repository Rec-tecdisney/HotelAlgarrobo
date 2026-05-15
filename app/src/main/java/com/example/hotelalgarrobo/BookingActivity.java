package com.example.hotelalgarrobo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Patterns;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BookingActivity extends AppCompatActivity {

    // 1. Declaramos las variables correctamente
    EditText inputNombre, inputEmail, inputTelefono;
    TextView tvTitulo;
    Button btnConfirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        // 2. Vinculamos los objetos con el XML
        tvTitulo = findViewById(R.id.tvTituloReserva);
        inputNombre = findViewById(R.id.etNombre);
        inputEmail = findViewById(R.id.etEmail);
        inputTelefono = findViewById(R.id.etTelefono);
        btnConfirmar = findViewById(R.id.btnConfirmarReserva);

        // 3. Recibir el nombre de la habitación
        String nombreHabitacion = getIntent().getStringExtra("h_nombre_reserva");
        if (nombreHabitacion != null && tvTitulo != null) {
            tvTitulo.setText("Reservando: " + nombreHabitacion);
        }

        // 4. Programar el clic del botón
        if (btnConfirmar != null) {
            btnConfirmar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    confirmarReserva(); // Ahora sí lo encontrará
                }
            });
        }
    } // <--- AQUÍ CIERRA EL ONCREATE

    // 5. EL MÉTODO DEBE IR AQUÍ AFUERA
    private void confirmarReserva() {
        String nombre = inputNombre.getText().toString().trim();
        String email = inputEmail.getText().toString().trim();
        String telefono = inputTelefono.getText().toString().trim();

        // FILTRO 1: ¿Está vacío el nombre?
        if (nombre.isEmpty()) {
            inputNombre.setError("Escribe tu nombre");
            inputNombre.requestFocus();
        }
        // FILTRO 2: ¿Está vacío el correo?
        else if (email.isEmpty()) {
            inputEmail.setError("Escribe tu correo");
            inputEmail.requestFocus();
        }
        // FILTRO 3: ¿El correo tiene formato real (incluye @ y .)?
        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            inputEmail.setError("Correo no válido (falta @ o formato .com)");
            inputEmail.requestFocus();
        }
        // FILTRO 4: ¿Está vacío el teléfono?
        else if (telefono.isEmpty()) {
            inputTelefono.setError("Escribe tu teléfono");
            inputTelefono.requestFocus();
        }
        // SI PASA TODOS LOS FILTROS:
        else {
            android.widget.Toast.makeText(this, "¡Reserva exitosa para " + nombre + "!", android.widget.Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
