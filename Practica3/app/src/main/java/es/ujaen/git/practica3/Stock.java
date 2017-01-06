package es.ujaen.git.practica3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Stock extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);
        Button boton1 = (Button) findViewById(R.id.stock_boton);

        boton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View View){
                EditText nombre = (EditText) findViewById(R.id.stock_name);
                EditText provee = (EditText) findViewById(R.id.stock_proveed);
                EditText vactual = (EditText) findViewById(R.id.stock_vact);
                EditText vminimo = (EditText) findViewById(R.id.stock_vmin);

                String nombreproducto = nombre.getText().toString();
                String proveedor      = provee.getText().toString();
                String valoractual1    = vactual.getText().toString();
                int valoractual = Integer.parseInt(valoractual1);
                String valorminimo1    = vminimo.getText().toString();
                int valorminimo = Integer.parseInt(valorminimo1);
                Toast.makeText(getApplicationContext(), "Enviando datos del producto.", Toast.LENGTH_SHORT).show();
                datos valores = new datos(nombreproducto,proveedor,valoractual,valorminimo);
            }//Fin onClick.
        });
    }//Fin onCreate.
}//Fin clase Stock.
