package es.ujaen.git.practica3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**Clase servicio a la que ira el usuario directamente si se ha autenticado correctamente, o si sale de la aplicacion y vuelve a ella
 * tras autenticarse y aun no ha expirado el tiempo de sesion.
 */
public class Servicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicio);
       Button bt1 = (Button) findViewById(R.id.botonstock);
       Button bt2 = (Button) findViewById(R.id.botoninfo);

        bt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View View){
                Toast.makeText(getApplicationContext(), "Accediendo al control de stock.", Toast.LENGTH_SHORT).show();
                Intent c = new Intent(Servicio.this, Stock.class);
                startActivity(c);//Realizar la transición intent con identificador c.
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View View){
                Toast.makeText(getApplicationContext(), "Accediendo a información.", Toast.LENGTH_SHORT).show();
                Intent d = new Intent(Servicio.this, Informacion.class);
                startActivity(d);//Realizar la transición intent con identificador c.
            }
        });
    }
}
