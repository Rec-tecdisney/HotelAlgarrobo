package com.example.hotelalgarrobo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnBuscar = findViewById(R.id.btnSearch);

        btnBuscar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 1. Creamos la "Intención" de saltar de la pantalla actual a la de resultados
                Intent intent = new Intent(MainActivity.this, ResultsActivity.class);
                startActivity(intent);

              }
        });
        
        Button btnFechas = findViewById(R.id.btnSelectDates);
        if (btnFechas != null) {
            btnFechas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    abrirCalendario();
                }
            });
        }
    }
    // Este es el método que "cura" el error rojo de la línea 37
    private void abrirCalendario() {
        final java.util.Calendar c = java.util.Calendar.getInstance();
        int anio = c.get(java.util.Calendar.YEAR);
        int mes = c.get(java.util.Calendar.MONTH);
        int dia = c.get(java.util.Calendar.DAY_OF_MONTH);

        android.app.DatePickerDialog datePickerDialog = new android.app.DatePickerDialog(this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    // Aquí se guarda la fecha seleccionada
                    String fecha = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                    Button btnFechas = findViewById(R.id.btnSelectDates);
                    if (btnFechas != null) {
                        btnFechas.setText("📅 " + fecha);
                    }
                }, anio, mes, dia);
        datePickerDialog.show();
    }
}
