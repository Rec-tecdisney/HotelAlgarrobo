package com.example.hotelalgarrobo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    TextView tvNombre, tvPrecio, tvDescripcion;
    android.widget.ImageView imgHabitacion;

    // Variables para sostener las fechas en el segundo puente
    private String fechaEntradaPasada = "";
    private String fechaSalidaPasada = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail3);

        // 1. Vinculación de componentes
        tvNombre = findViewById(R.id.tvNombreDetalle);
        tvPrecio = findViewById(R.id.tvPrecioDetalle);
        tvDescripcion = findViewById(R.id.tvDescripcionDetalle);
        imgHabitacion = findViewById(R.id.imgDetalle);
        Button miBoton = findViewById(R.id.btnReservarAhora);

        // 2. Recepción de datos de la habitación
        String nombre = getIntent().getStringExtra("h_nombre");
        String precio = getIntent().getStringExtra("h_precio");
        String desc = getIntent().getStringExtra("h_desc");
        String tipo = getIntent().getStringExtra("h_tipo");

        // EXTRA: RECIBIMOS las fechas en este segundo puente
        fechaEntradaPasada = getIntent().getStringExtra("f_entrada");
        fechaSalidaPasada = getIntent().getStringExtra("f_salida");

        // 3. Lógica para mostrar la información e imágenes
        if (nombre != null) {
            tvNombre.setText(nombre);
            tvPrecio.setText(precio);
            tvDescripcion.setText(desc);

            if (tipo != null && imgHabitacion != null) {
                if (tipo.equals("suite")) {
                    imgHabitacion.setImageResource(R.drawable.suite1);
                }
                else if (tipo.equals("familiar")) {
                    imgHabitacion.setImageResource(R.drawable.habitacion_familiar);
                }
                else if (tipo.equals("doble")) {
                    imgHabitacion.setImageResource(R.drawable.habitacion_2);
                }
                else if (tipo.equals("estandar")) {
                    imgHabitacion.setImageResource(R.drawable.habitacion_3);
                }
            }
        }

        // 4. Botón de reserva (Modificado para enviar nombre, FECHAS Y PRECIO)
        if (miBoton != null) {
            miBoton.setOnClickListener(v -> {
                Intent intent = new Intent(DetailActivity.this, BookingActivity.class);
                intent.putExtra("h_nombre_reserva", nombre);

                // EXTRA: Enviamos el precio dinámico (ej: "$120.000")
                intent.putExtra("h_precio_reserva", precio);

                // EXTRA: Enviamos las fechas al destino final
                intent.putExtra("f_entrada", fechaEntradaPasada);
                intent.putExtra("f_salida", fechaSalidaPasada);

                startActivity(intent);
            });
        }
    }
}
