package com.example.hotelalgarrobo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // 1. Variables globales para las fechas
    private String fechaEntrada = "";
    private String fechaSalida = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 2. Referencias a los botones de Entrada y Salida
        Button btnIn = findViewById(R.id.btnCheckIn);
        Button btnOut = findViewById(R.id.btnCheckOut);
        Button btnBuscar = findViewById(R.id.btnSearch);

        // Referencias a los botones de categorías que definiste en el XML
        View btnHoteles = findViewById(R.id.btnCategoriaHoteles);
        View btnRios = findViewById(R.id.btnCategoriaRios);
        View btnMontana = findViewById(R.id.btnCategoriaMontaña);

        // 3. Lógica para Check-in
        btnIn.setOnClickListener(v -> abrirCalendario(fecha -> {
            fechaEntrada = fecha;
            btnIn.setText("Entrada: " + fecha);
        }));

        // 4. Lógica para Check-out
        btnOut.setOnClickListener(v -> abrirCalendario(fecha -> {
            fechaSalida = fecha;
            btnOut.setText("Salida: " + fecha);
        }));

        // 5. El botón BUSCAR redirige a la pantalla del CRUD (GestionReservaActivity)
        btnBuscar.setOnClickListener(v -> {
            if (fechaEntrada.isEmpty() || fechaSalida.isEmpty()) {
                Toast.makeText(this, "Por favor selecciona ambas fechas", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(MainActivity.this, GestionReservaActivity.class);

                // Empaquetamos las fechas seleccionadas para enviarlas al CRUD
                intent.putExtra("f_entrada", fechaEntrada);
                intent.putExtra("f_salida", fechaSalida);

                startActivity(intent);
            }
        });

        // Control de calidad para los mensajes de las categorías populares
        if (btnHoteles != null) {
            btnHoteles.setOnClickListener(v -> {
                Toast.makeText(this, "La sección de Consultar eventos se encuentra en construcción", Toast.LENGTH_SHORT).show();
            });
        }

        if (btnRios != null) {
            btnRios.setOnClickListener(v -> {
                Toast.makeText(this, "La categoría Ríos se encuentra en construcción", Toast.LENGTH_SHORT).show();
            });
        }

        if (btnMontana != null) {
            btnMontana.setOnClickListener(v -> {
                Toast.makeText(this, "La categoría Montaña se encuentra en construcción", Toast.LENGTH_SHORT).show();
            });
        }

        // Referenciar el nuevo botón de promociones
        Button btnIrARegistro = findViewById(R.id.btnIrARegistro);

        // Programar la navegación hacia la pantalla de registro
        if (btnIrARegistro != null) {
            btnIrARegistro.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            });
        }
    } // Cierre del onCreate

    private void abrirCalendario(OnDateSelectedListener listener) {
        final java.util.Calendar c = java.util.Calendar.getInstance();
        int anio = c.get(java.util.Calendar.YEAR);
        int mes = c.get(java.util.Calendar.MONTH);
        int dia = c.get(java.util.Calendar.DAY_OF_MONTH);

        new android.app.DatePickerDialog(this, (view, year, month, day) -> {
            String fecha = day + "/" + (month + 1) + "/" + year;
            listener.onDateSelected(fecha);
        }, anio, mes, dia).show();
    }

    interface OnDateSelectedListener {
        void onDateSelected(String fecha);
    }
} // Cierre de la clase MainActivity