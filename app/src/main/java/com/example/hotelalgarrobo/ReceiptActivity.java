package com.example.hotelalgarrobo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ReceiptActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        // 1. Vincular los TextViews del recibo
        TextView tvHabitacion = findViewById(R.id.tvReciboHabitacion);
        TextView tvHuesped = findViewById(R.id.tvReciboHuesped);
        TextView tvContacto = findViewById(R.id.tvReciboContacto);
        TextView tvPersonas = findViewById(R.id.tvReciboPersonas);
        TextView tvTotal = findViewById(R.id.tvReciboTotal);
        Button btnInicio = findViewById(R.id.btnVolverInicio);

        // 2. LEER DESDE LA MEMORIA (SharedPreferences)
        SharedPreferences pref = getSharedPreferences("MisReservas", MODE_PRIVATE);

        String nombre = pref.getString("ultimo_nombre", "No registrado");
        String email = pref.getString("ultimo_email", "");
        String telefono = pref.getString("ultimo_telefono", "");
        String personas = pref.getString("cant_personas", "1");
        String habitacionInfo = pref.getString("resumen_habitacion", "No encontrada");
        String pagoInfo = pref.getString("resumen_pago", "Total: $0");

        // 3. Pintar los datos recuperados en la interfaz del voucher
        if (tvHabitacion != null) tvHabitacion.setText(habitacionInfo);
        if (tvHuesped != null) tvHuesped.setText("Huésped: " + nombre);
        if (tvContacto != null) tvContacto.setText("Email: " + email + "\nTel: " + telefono);
        if (tvPersonas != null) tvPersonas.setText("Cantidad de personas: " + personas);
        if (tvTotal != null) tvTotal.setText(pagoInfo);

        // 4. Botón para vaciar la pila y volver limpios a la pantalla de inicio
        if (btnInicio != null) {
            btnInicio.setOnClickListener(v -> {
                Intent intent = new Intent(ReceiptActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            });
        }
    }
}