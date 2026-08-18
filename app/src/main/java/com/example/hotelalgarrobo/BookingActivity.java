package com.example.hotelalgarrobo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BookingActivity extends AppCompatActivity {

    // 1. DECLARAMOS LAS VARIABLES (Agregamos inputPersonas aquí)
    EditText inputNombre, inputEmail, inputTelefono, inputPersonas;
    TextView tvTitulo, tvTotalPagar;
    Button btnConfirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        // 2. VINCULAMOS LOS OBJETOS CON EL XML (Agregamos la vinculación de etPersonas)
        tvTitulo = findViewById(R.id.tvTituloReserva);
        tvTotalPagar = findViewById(R.id.tvTotalPagar);
        inputNombre = findViewById(R.id.etNombre);
        inputEmail = findViewById(R.id.etEmail);
        inputTelefono = findViewById(R.id.etTelefono);
        inputPersonas = findViewById(R.id.etPersonas); // <--- NUEVO VÍNCULO
        btnConfirmar = findViewById(R.id.btnConfirmarReserva);

        // 3. Recibir los datos de la habitación y las FECHAS del viaje
        String nombreHabitacion = getIntent().getStringExtra("h_nombre_reserva");
        String precioHabitacion = getIntent().getStringExtra("h_precio_reserva");
        String fechaEntrada = getIntent().getStringExtra("f_entrada");
        String fechaSalida = getIntent().getStringExtra("f_salida");

        // Recalcular el total si el usuario cambia el número de personas
        if (inputPersonas != null) {
            inputPersonas.addTextChangedListener(new android.text.TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    calcularPrecioTotal(fechaEntrada, fechaSalida, precioHabitacion);
                }

                @Override
                public void afterTextChanged(android.text.Editable s) {}
            });
        }

        if (nombreHabitacion != null && tvTitulo != null) {
            if (fechaEntrada != null && !fechaEntrada.isEmpty() && fechaSalida != null && !fechaSalida.isEmpty()) {
                tvTitulo.setText("Reservando: " + nombreHabitacion + "\nEstadía: " + fechaEntrada + " al " + fechaSalida);
                calcularPrecioTotal(fechaEntrada, fechaSalida, precioHabitacion);
            } else {
                tvTitulo.setText("Reservando: " + nombreHabitacion);
            }
        }

        // 4. Programar el clic del botón confirmar
        if (btnConfirmar != null) {
            btnConfirmar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    confirmarReserva();
                }
            });
        }
    }

    // 5.  Lógica de negocio con cobro por huésped adicional
    private void calcularPrecioTotal(String entradaStr, String salidaStr, String precioStr) {
        // A. CONTROL DE ARRANQUE (QA UX): Si las fechas están vacías, no hacemos nada ni alertamos
        if (entradaStr == null || salidaStr == null || entradaStr.trim().isEmpty() || salidaStr.trim().isEmpty()) {
            if (tvTotalPagar != null) {
                tvTotalPagar.setText("Total a Pagar: " + (precioStr != null ? precioStr : "$0") + " (Por noche)");
                tvTotalPagar.setTextColor(android.graphics.Color.BLACK); // Color neutral por defecto
            }
            if (btnConfirmar != null) {
                btnConfirmar.setEnabled(false); // Bloqueado hasta que elija fechas válidas
            }
            return;
        }

        if (tvTotalPagar == null || precioStr == null) return;

        try {
            SimpleDateFormat formato = new SimpleDateFormat("d/M/yyyy", Locale.getDefault());
            Date fechaIn = formato.parse(entradaStr);
            Date fechaOut = formato.parse(salidaStr);

            if (fechaIn != null && fechaOut != null) {

                // 🚨 ESCENARIO DE QA: VALIDACIÓN DE FECHA INVERSA (Solo si ya hay fechas cargadas)
                if (!fechaOut.after(fechaIn)) {
                    tvTotalPagar.setText("Error: La fecha de salida debe ser posterior a la de entrada.");
                    tvTotalPagar.setTextColor(android.graphics.Color.RED); // Alerta visual
                    if (btnConfirmar != null) {
                        btnConfirmar.setEnabled(false); // Bloquea el paso
                    }
                    return;
                }

                // Si pasan la validación, restauramos la interfaz a su estado activo
                tvTotalPagar.setTextColor(android.graphics.Color.BLACK);
                if (btnConfirmar != null) {
                    btnConfirmar.setEnabled(true);
                }

                // B. Cálculo de los días de estadía
                long diferenciaMilisegundos = fechaOut.getTime() - fechaIn.getTime();
                long diasEstadia = diferenciaMilisegundos / (24 * 60 * 60 * 1000);

                // C. Limpieza del precio por noche
                String precioLimpio = precioStr.replace("$", "").replace(".", "").trim();
                int precioPorNoche = Integer.parseInt(precioLimpio);

                // D. Leer cantidad de personas
                int cantidadPersonas = 1;
                if (inputPersonas != null && !inputPersonas.getText().toString().trim().isEmpty()) {
                    cantidadPersonas = Integer.parseInt(inputPersonas.getText().toString().trim());
                }

                // E. Aplicar regla de negocio (Huéspedes adicionales)
                long costoBaseTotal = diasEstadia * precioPorNoche;
                long costoAdicionalTotal = 0;
                int TARIFA_PERSONA_EXTRA = 40000;

                if (cantidadPersonas > 2) {
                    int personasExtras = cantidadPersonas - 2;
                    costoAdicionalTotal = (long) personasExtras * TARIFA_PERSONA_EXTRA * diasEstadia;
                }

                long costoFinal = costoBaseTotal + costoAdicionalTotal;

                // F. Formatear respuesta visual
                java.text.DecimalFormat formateador = new java.text.DecimalFormat("#,###");
                String costoFormateado = formateador.format(costoFinal);

                String mensajeResumen = "Estadía total: " + diasEstadia + " noche(s)\n";
                if (costoAdicionalTotal > 0) {
                    mensajeResumen += "Incluye cargo por personas extras\n";
                }
                mensajeResumen += "Total a Pagar: $" + costoFormateado;

                tvTotalPagar.setText(mensajeResumen);
            }
        } catch (Exception e) {
            tvTotalPagar.setText("Total a Pagar: " + precioStr + " (Por noche)");
        }
    }
    // 5. ACTUALIZAMOS EL MÉTODO DE CONFIRMACIÓN
    private void confirmarReserva() {
        String nombre = inputNombre.getText().toString().trim();
        String email = inputEmail.getText().toString().trim();
        String telefono = inputTelefono.getText().toString().trim();
        String personas = inputPersonas.getText().toString().trim();

        String resumenHabi = tvTitulo.getText().toString();
        String resumenPago = tvTotalPagar.getText().toString();

        // FILTROS DE VALIDACIÓN
        if (nombre.isEmpty()) {
            inputNombre.setError("Escribe tu nombre");
            inputNombre.requestFocus();
        } else if (email.isEmpty()) {
            inputEmail.setError("Escribe tu correo");
            inputEmail.requestFocus();
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            inputEmail.setError("Correo no válido");
            inputEmail.requestFocus();
        } else if (telefono.isEmpty()) {
            inputTelefono.setError("Escribe tu teléfono");
            inputTelefono.requestFocus();
        }
        // Validamos que no quede vacío ni coloquen cero personas
        else if (personas.isEmpty() || personas.equals("0")) {
            inputPersonas.setError("Mínimo 1 persona");
            inputPersonas.requestFocus();
        }
        // SI PASA TODOS LOS FILTROS, GUARDAMOS (OPCIÓN 1)
        else {
            android.content.SharedPreferences pref = getSharedPreferences("MisReservas", MODE_PRIVATE);
            android.content.SharedPreferences.Editor editor = pref.edit();

            // Almacenamos toda la información de la reserva de manera persistente
            editor.putString("ultimo_nombre", nombre);
            editor.putString("ultimo_email", email);
            editor.putString("ultimo_telefono", telefono);
            editor.putString("cant_personas", personas);
            editor.putString("resumen_habitacion", resumenHabi);
            editor.putString("resumen_pago", resumenPago);

            editor.apply(); // Guarda localmente

            android.widget.Toast.makeText(this, "¡Reserva Guardada con Éxito!", android.widget.Toast.LENGTH_LONG).show();

            // --- CONEXIÓN CON OPCIÓN 2: LANZAR EL RECIBO ---
            Intent intent = new Intent(BookingActivity.this, ReceiptActivity.class);
            startActivity(intent);

            finish();
        }
    }
}
