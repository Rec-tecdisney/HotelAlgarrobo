package com.example.hotelalgarrobo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    // 1. Declarar las variables para los textos (fuera del onCreate)
    TextView tvNombre, tvPrecio, tvDescripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail2); // El layout que tienes en la imagen

        // 2. Vincular con los IDs del XML
        // Revisa que estos IDs (tvNombreDetalle, etc) existan en tu activity_detail2.xml
        tvNombre = findViewById(R.id.tvNombreDetalle);
        tvPrecio = findViewById(R.id.tvPrecioDetalle);
        tvDescripcion = findViewById(R.id.tvDescripcionDetalle);

        // 3. RECIBIR LOS DATOS (La parte clave)
        String nombre = getIntent().getStringExtra("h_nombre");
        String precio = getIntent().getStringExtra("h_precio");
        String desc = getIntent().getStringExtra("h_desc");

        // 4. Mostrar los datos si no son nulos
        if (nombre != null) {
            tvNombre.setText(nombre);
            tvPrecio.setText(precio);
            tvDescripcion.setText(desc);

        // Tu código del botón de reserva que ya tienes (Línea 17 en tu imagen)
            Button miBoton = findViewById(R.id.btnReservarAhora);

            if (miBoton != null) {
                miBoton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 1. Log de confirmación
                        android.widget.Toast.makeText(DetailActivity.this, "Iniciando Salto...", android.widget.Toast.LENGTH_SHORT).show();

                        // 2. Definir el Intent con el contexto explícito
                        Intent intent = new Intent(v.getContext(), BookingActivity.class);

                        // 3. Pasar el dato (asegúrate que la variable 'nombre' tenga algo)
                        intent.putExtra("h_nombre_reserva", nombre);

                        // 4. EJECUTAR EL SALTO
                        v.getContext().startActivity(intent);
                    }
                });
            }
        }
    }

}