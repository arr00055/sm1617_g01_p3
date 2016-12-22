package es.ujaen.git.practica3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class Informacion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion);
        ImageButton botonregreso = (ImageButton) findViewById(R.id.botonatras);

        botonregreso.setOnClickListener(new View.OnClickListener() {
            public void onClick(View View){
                Toast.makeText(getApplicationContext(), "Volviendo atrás.", Toast.LENGTH_SHORT).show();
                Intent e = new Intent(Informacion.this, Servicio.class);
                startActivity(e);//Realizar la transición intent con identificador e.
            }
        });
    }

}
