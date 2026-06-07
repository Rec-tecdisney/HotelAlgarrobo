package com.example.hotelalgarrobo;

import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


public class ResultsActivity extends AppCompatActivity {

    // Variables para sostener las fechas en el "puente"
    private String fechaEntradaPasada = "";
    private String fechaSalidaPasada = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        // RECIBIMOS las fechas que vienen de MainActivity
        fechaEntradaPasada = getIntent().getStringExtra("f_entrada");
        fechaSalidaPasada = getIntent().getStringExtra("f_salida");

        // Enlazamos las 4 tarjetas
        CardView c1 = findViewById(R.id.cardSuite);
        CardView c2 = findViewById(R.id.cardHabitación_Doble);
        CardView c3 = findViewById(R.id.cardHabitación_Estándar);
        CardView c4 = findViewById(R.id.cardCabaña_Familiar);

        // Programamos los clics agregando el tipo de habitación
        c1.setOnClickListener(v -> enviarADetalle("Suite de Lujo", "$250.000", "La Suite ofrece el máximo nivel de lujo con una vista panorámica espectacular...", "suite"));
        c2.setOnClickListener(v -> enviarADetalle("Habitación Doble", "$180.000", "Perfecta para viajes de negocios o parejas.", "doble"));
        c3.setOnClickListener(v -> enviarADetalle("Habitación Estandar", "$120.000", "Amplio espacio con cama matrimonial, que da una paz y tranquilidad, ademas de una excelente vita panoramica", "estandar"));
        c4.setOnClickListener(v -> enviarADetalle("Habitación Familiar", "$80.000", "La mejor opción para pasar en familia.", "familiar"));
    }

    // Método optimizado: ahora también reenvía las fechas a la siguiente pantalla
    private void enviarADetalle(String nombre, String precio, String info, String tipo) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("h_nombre", nombre);
        intent.putExtra("h_precio", precio);
        intent.putExtra("h_desc", info);
        intent.putExtra("h_tipo", tipo);

        //Volvemos a empacar las fechas para que sigan su viaje
        intent.putExtra("f_entrada", fechaEntradaPasada);
        intent.putExtra("f_salida", fechaSalidaPasada);

        startActivity(intent);
    }
}