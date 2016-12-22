package es.ujaen.git.practica3;
/**Esta es la clase de nuestra MainActivity, es la actividad que se carga con la aplicación y en la
 * que se contiene el fragmento dinámico.
 */
//Importamos los paquetes de compatibilidad para poder hacer uso de la clase Fragment y sus métodos.
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//La clase MainActivity hereda de AppCompatActivity.
public class MainActivity extends AppCompatActivity {
//Ciclo de vida onCreate cuando se crea la Actividad. Aquí es donde se inicializa la actividad.
//En Bundle savedInstanceState es donde se reciben los datos almacenados tras un recreado de la actividad.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);     //Se llama a la superclase primero.
        setContentView(R.layout.activity_main); //Pinto el layout relacionado con mi MainActivity.
        //Leo mi preferencia prefs, y si sesionid alberga algo salta el if y compruebo el tiempo, en caso de estar dentro del rango
        //de la Autenticacion lo lanzo a la actividad del servicio, si no, dejo que siga como siempre.
        SharedPreferences prefs = getSharedPreferences("DatosSesion", Context.MODE_PRIVATE);
        String sesionid = prefs.getString("Sesion","");
        String expires = prefs.getString("Tiempoexpira","");

        if (sesionid.length()>0){
            //El usuario ya tiene algo guardado.
            long fecha = System.currentTimeMillis()-3600000;
            SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd-H-m-s");
            try{
                Date iniciosesion = dt1.parse(expires);
                long t=iniciosesion.getTime();
                    if(t > fecha){ //Mientras no haya expirado el tiempo de sesion.
                        Toast.makeText(getApplicationContext(), "Bienvenido de nuevo, tu sesión aun no ha expirado.", Toast.LENGTH_SHORT).show(); //Muestro un mensaje al usuario, para avisarle de que aun sigue autenticado.
                        Intent a = new Intent(this, Servicio.class);
                        startActivity(a);//Realizar la transición intent con identificador i.
                   }//Fin if comprobación de si aun no paso tiempo de sesion.
                   }catch(ParseException p){
                                            }//Fin del Catch.
        }//Fin if que comprueba sesionid.

        FragmentManager fm = getSupportFragmentManager();//Obtener la instancia del administrador de fragmentos.
        FragmentTransaction ft = fm.beginTransaction();  //Creo una nueva transacción.
        Fragment f=fm.findFragmentById(R.id.main_frame); //Busco el fragmento por su Id.

        if(f==null)//Compruebo si el fragmento encontrado por la Id es null.
        {
            AuthFragment au = AuthFragment.newInstance("","","");//Creo una nueva instancia au de tipo AuthFragment.
            ft.add(R.id.main_frame, au);//El método add() recibe dos parámetros. El primero es el identificador del contenedor donde insertaremos el fragmento. El segundo parámetro es la instancia del fragmento que queremos añadir.
            ft.addToBackStack(null);
        }//Fin del if.
        ft.commit();//Se Confirman los cambios.

    }//Fin del onCreate.


}//Fin del MainActivity.