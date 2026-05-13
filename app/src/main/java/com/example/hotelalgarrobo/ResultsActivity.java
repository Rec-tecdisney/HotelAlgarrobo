package com.example.hotelalgarrobo;

import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        // Enlazamos las 4 tarjetas
        CardView c1 = findViewById(R.id.cardSuite);
        CardView c2 = findViewById(R.id.cardHabitación_Doble);
        CardView c3 = findViewById(R.id.cardHabitación_Estándar);
        CardView c4 = findViewById(R.id.cardCabaña_Familiar);

        // Programamos los clics para cada una
        c1.setOnClickListener(v -> enviarADetalle("Suite de Lujo", "$250.000", "Vista a la llanura y máxima exclusividad."));
        c2.setOnClickListener(v -> enviarADetalle("Habitación Estándar", "$120.000", "Perfecta para viajes de negocios o parejas."));
        c3.setOnClickListener(v -> enviarADetalle("Habitación Familiar", "$350.000", "Amplio espacio con capacidad para 5 personas."));
        c4.setOnClickListener(v -> enviarADetalle("Habitación Económica", "$80.000", "La mejor opción para viajeros frecuentes."));
    }

    // Método para evitar repetir código (Estándar de calidad)
    private void enviarADetalle(String nombre, String precio, String info) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("h_nombre", nombre);
        intent.putExtra("h_precio", precio);
        intent.putExtra("h_desc", info);
        startActivity(intent);
    }
}