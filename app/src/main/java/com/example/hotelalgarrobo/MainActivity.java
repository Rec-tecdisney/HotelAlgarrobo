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

                // 2. ¡Damos el salto!
                startActivity(intent);
            }
        });
    }
}