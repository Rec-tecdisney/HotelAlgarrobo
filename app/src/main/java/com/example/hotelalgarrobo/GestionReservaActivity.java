package com.example.hotelalgarrobo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class GestionReservaActivity extends AppCompatActivity {

        private EditText etId, etCliente, etEntrada, etSalida, etPersonas;
        private Button btnCrear, btnBuscar, btnEditar, btnEliminar;
        private AdminSQLiteOpenHelper adminDB;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_gestion_reserva);

                // 1. Enlazar componentes de la interfaz
                etId = findViewById(R.id.etIdReserva);
                etCliente = findViewById(R.id.etNombreCliente);
                etEntrada = findViewById(R.id.etFechaEntrada);
                etSalida = findViewById(R.id.etFechaSalida);
                etPersonas = findViewById(R.id.etCantPersonas);

                btnCrear = findViewById(R.id.btnCrearReserva);
                btnBuscar = findViewById(R.id.btnBuscarReserva);
                btnEditar = findViewById(R.id.btnEditarReserva);
                btnEliminar = findViewById(R.id.btnEliminarReserva);

                // Instanciar el conector de la base de datos SQLite
                adminDB = new AdminSQLiteOpenHelper(this, "HotelDB", null, 1);

                // 2. C - CREATE (Crear Reserva)
                btnCrear.setOnClickListener(v -> crearReserva());

                // 3. R - READ (Consultar Reserva por ID)
                btnBuscar.setOnClickListener(v -> consultarReserva());

                // 4. U - UPDATE (Actualizar/Editar Reserva)
                btnEditar.setOnClickListener(v -> actualizarReserva());

                // 5. D - DELETE (Eliminar/Cancelar Reserva)
                btnEliminar.setOnClickListener(v -> eliminarReserva());
        }

        // --- MÉTODOS DEL CRUD ---

        // C: INSERT
        private void crearReserva() {
                SQLiteDatabase db = adminDB.getWritableDatabase();

                String cliente = etCliente.getText().toString().trim();
                String entrada = etEntrada.getText().toString().trim();
                String salida = etSalida.getText().toString().trim();
                String personas = etPersonas.getText().toString().trim();

                if (cliente.isEmpty() || entrada.isEmpty() || salida.isEmpty() || personas.isEmpty()) {
                        Toast.makeText(this, "Completa todos los campos para registrar", Toast.LENGTH_SHORT).show();
                        return;
                }

                ContentValues registro = new ContentValues();
                registro.put("cliente", cliente);
                registro.put("fechaEntrada", entrada);
                registro.put("fechaSalida", salida);
                registro.put("personas", Integer.parseInt(personas));

                long idGenerado = db.insert("reservas", null, registro);
                db.close();

                if (idGenerado != -1) {
                        Toast.makeText(this, "¡Reserva creada con exito! ID asignado: " + idGenerado, Toast.LENGTH_LONG).show();
                        limpiarCampos();
                } else {
                        Toast.makeText(this, "Error al guardar en la base de datos", Toast.LENGTH_SHORT).show();
                }
        }

        // R: SELECT
        private void consultarReserva() {
                SQLiteDatabase db = adminDB.getReadableDatabase();
                String idStr = etId.getText().toString().trim();

                if (idStr.isEmpty()) {
                        Toast.makeText(this, "Ingresa un ID de reserva para buscar", Toast.LENGTH_SHORT).show();
                        return;
                }

                Cursor fila = db.rawQuery("SELECT cliente, fechaEntrada, fechaSalida, personas FROM reservas WHERE id = " + idStr, null);

                if (fila.moveToFirst()) {
                        etCliente.setText(fila.getString(0));
                        etEntrada.setText(fila.getString(1));
                        etSalida.setText(fila.getString(2));
                        etPersonas.setText(fila.getString(3));
                        Toast.makeText(this, "Reserva encontrada", Toast.LENGTH_SHORT).show();
                } else {
                        Toast.makeText(this, "No existe una reserva con ese ID", Toast.LENGTH_SHORT).show();
                        limpiarCamposSinId();
                }
                fila.close();
                db.close();
        }

        // U: UPDATE
        private void actualizarReserva() {
                SQLiteDatabase db = adminDB.getWritableDatabase();
                String idStr = etId.getText().toString().trim();

                if (idStr.isEmpty()) {
                        Toast.makeText(this, "Ingresa el ID de la reserva a modificar", Toast.LENGTH_SHORT).show();
                        return;
                }

                ContentValues registro = new ContentValues();
                registro.put("cliente", etCliente.getText().toString().trim());
                registro.put("fechaEntrada", etEntrada.getText().toString().trim());
                registro.put("fechaSalida", etSalida.getText().toString().trim());
                registro.put("personas", etPersonas.getText().toString().trim());

                int cantidadAfectada = db.update("reservas", registro, "id=" + idStr, null);
                db.close();

                if (cantidadAfectada == 1) {
                        Toast.makeText(this, "Reserva actualizada correctamente", Toast.LENGTH_SHORT).show();
                        limpiarCampos();
                } else {
                        Toast.makeText(this, "No se encontro la reserva para actualizar", Toast.LENGTH_SHORT).show();
                }
        }

        // D: DELETE
        private void eliminarReserva() {
                SQLiteDatabase db = adminDB.getWritableDatabase();
                String idStr = etId.getText().toString().trim();

                if (idStr.isEmpty()) {
                        Toast.makeText(this, "Ingresa el ID de la reserva a eliminar", Toast.LENGTH_SHORT).show();
                        return;
                }

                int cantidadAfectada = db.delete("reservas", "id=" + idStr, null);
                db.close();

                if (cantidadAfectada == 1) {
                        Toast.makeText(this, "Reserva eliminada con exito", Toast.LENGTH_SHORT).show();
                        limpiarCampos();
                } else {
                        Toast.makeText(this, "No se encontro la reserva especificada", Toast.LENGTH_SHORT).show();
                }
        }

        // Métodos auxiliares
        private void limpiarCampos() {
                etId.setText("");
                limpiarCamposSinId();
        }

        private void limpiarCamposSinId() {
                etCliente.setText("");
                etEntrada.setText("");
                etSalida.setText("");
                etPersonas.setText("");
        }
}