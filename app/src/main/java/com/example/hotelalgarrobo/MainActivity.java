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

        // 5. El botón BUSCAR ahora puede validar que las fechas existan y las envía
        btnBuscar.setOnClickListener(v -> {
            if (fechaEntrada.isEmpty() || fechaSalida.isEmpty()) {
                android.widget.Toast.makeText(this, "Por favor selecciona ambas fechas", android.widget.Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(MainActivity.this, ResultsActivity.class);

                // EXTRA: Empaquetamos las fechas para el viaje
                intent.putExtra("f_entrada", fechaEntrada);
                intent.putExtra("f_salida", fechaSalida);

                startActivity(intent);
            }
        });

        }

    // 6. El método del calendario (mantenlo como lo teníamos)
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
}