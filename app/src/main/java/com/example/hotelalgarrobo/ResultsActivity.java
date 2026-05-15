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

        CardView c1 = findViewById(R.id.cardSuite);
        CardView c2 = findViewById(R.id.cardHabitación_Doble);
        CardView c3 = findViewById(R.id.cardHabitación_Estándar);
        CardView c4 = findViewById(R.id.cardCabaña_Familiar);

        // Agregamos el cuarto parámetro: el "tipo" para la imagen
        c1.setOnClickListener(v -> enviarADetalle("Suite de Lujo", "$250.000", "La Suite ofrece el máximo nivel de lujo...", "suite"));
        c2.setOnClickListener(v -> enviarADetalle("Habitación Doble", "$180.000", "Perfecta para viajes de negocios o parejas.", "doble"));
        c3.setOnClickListener(v -> enviarADetalle("Habitación Estandar", "$120.000", "Amplio espacio con cama matrimonial...", "estandar"));
        c4.setOnClickListener(v -> enviarADetalle("Habitación Familiar", "$80.000", "La mejor opción para pasar en familia.", "familiar"));
    }

    // Actualizamos el método para que reciba el String "tipo"
    private void enviarADetalle(String nombre, String precio, String info, String tipo) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("h_nombre", nombre);
        intent.putExtra("h_precio", precio);
        intent.putExtra("h_desc", info);
        intent.putExtra("h_tipo", tipo); // <--- ESTO ES LO QUE HACÍA FALTA
        startActivity(intent);
    }
}