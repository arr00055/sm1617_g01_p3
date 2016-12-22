package es.ujaen.git.practica3;
/**Esta es la clase de la actividad a la que pasamos una vez utilizamos el intent desde el fragmento tras pular el botón de enviar.
 * Cada uno de los parámetros siguientes son extraidos del Intent uno a uno.
 * @param usuario     el nick o nombre que introduce el usuario en la interfaz.
 * @param password    la contraseña que introduce el usuario en la interfaz.
 * @param direccionIp la dirección Ip que introduce el usuario en la interfaz.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

//La clase ConexActivity hereda de AppCompatActivity.
public class ConexActivity extends AppCompatActivity {
//Ciclo de vida onCreate cuando se crea la Actividad. Aquí es donde se inicializa la actividad.
//En Bundle savedInstanceState es donde se reciben los datos almacenados tras un recreado de la actividad.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);       //Se llama a la superclase primero.
        setContentView(R.layout.activity_conex);  //Pinto el layout correspondiente a la actividad ConexActivity.

        Bundle bundle = getIntent().getExtras();
        //Tomamos el Intent que va dirigido a esta actividad y sacamos los Extras donde están los datos que nos envía el fragmento.
        if (bundle != null){ //Si bundle es diferente de null se procede a extraer los String y los Int y se guardan en variables.
            String user = bundle.getString("usuario");     //Extraigo del bundle la cadena de String con id usuario a través del getString.
            String pass = bundle.getString("password");    //Extraigo del bundle la cadena de String con id password a través del getString.
            String ip   = bundle.getString("direccionIp"); //Extraigo del bundle la cadena de String con id direccionIp a través del getString.

            /**
             * Creo un objeto autentica de la clase Autenticacion al cual le paso los datos que he recibido del intent, luego se crea
             * un objeto conecta de la clase Conexion, para finalmente pasarle al objeto conecta el objeto autentica que contiene los
             * datos introducidos por el usuario y con execute(); envio datos directamente a doInBackground().
             */
            Autenticacion autentica = new Autenticacion(user,pass,ip);
            Conexion conecta = new Conexion();
            conecta.execute(autentica);//Con el uso de .execute lanzo el objeto a la hebra asincrona, en el onPreExecute.
        }//Fin del if.
    }//Fin del onCreate.

    /**
     * Creo una clase Conexion que con los datos que el usuario ha introducido en el fragmento y enviado durante el login, y a traves de una
     * tarea asincrona y con el uso de Sockets TCP me permitirá conectar con un servidor y recibir una respuesta de este.
     * AsynTask<Autenticacion, Integer, String>-> Se le pasa este primer valor como Autenticacion, el segundo valor como Integer para
     * la actualizacion de los datos y finalmente en el final de la hebra un tipo String.
     */
    public class Conexion extends AsyncTask<Autenticacion, Integer, String> {
        ProgressDialog pdia = null; //Se inicializa el ProgressDialog.
        public static final int SERVICE_PORT=6000; //Constante que contiene el puerto.

        /**
         * El metodo onPreExecute() se lanzara antes de que arranque la hebra asincrona, y se utilizara para lanzar el ProgressDialog.
         */
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pdia = new ProgressDialog(ConexActivity.this); //Creamos un nuevo ProgressDialog en pdia.
            pdia.setIndeterminate(true);
            pdia.setMessage("Autenticando, espere por favor."); //Mensaje que queremos sacar en el ProgressDialog.
            pdia.setProgressStyle(ProgressDialog.STYLE_SPINNER); //El tipo de estilo del ProgressDialog.
            pdia.setCancelable(false);
            pdia.show(); //Se muestra el pdia.
        }//Fin onPreExecute.

        /**
         * El metodo doInBackground es donde se lanza la hebra en segundo plano donde se realizara la comunicacion socket con el
         * servidor TCP, y que devolvera el resultado con los errores de autenticacion o el correspondiente Sesion-id, y el tiempo
         * de expiracion de la sesion. Como este metodo no puede actualizar la IU, se debe realizar desde el siguiente metodo que
         * sera en onPostExecute.
         * @param arg0
         * @return resultado
         */
        @Override
        protected String doInBackground(Autenticacion... arg0){
            Socket cliente   = null;//Creo e inicializo una variable cliente de tipo Socket.
            String respuesta = null;//Creo e inicializo una variable respuesta que sera la que obtenga del servidor de tipo Socket.
            String id        = null;//Creo e inicializo una variable id que sera en la que guarde el mensaje del servidor correspondiente a la autenticacion.
            try {

                String User = arg0[0].getUser();  //Saco de mi array arg0 el valor que se corresponde con el usuario.
                String Pass = arg0[0].getPass();  //Saco de mi array arg0 el valor que se corresponde con la password.
                String IP   = arg0[0].getIP();    //Saco de mi array arg0 el valor que se corresponde con la direccion IP.

                InetSocketAddress direccion = new InetSocketAddress(IP,SERVICE_PORT); //Creo el objeto direccion de tipo InetSocketAddress que contiene la direccon IP y el Puerto.

                //Se crea el socket TCP y me conecto al servidor con la direccion TCP y el puerto.
                cliente = new Socket();
                cliente.connect(direccion);
                //Se leen los datos del buffer de entrada bis y se escriben los datos en el buffer de salida os.
                BufferedReader bis = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                OutputStream os = cliente.getOutputStream();

                //Recibimos el mensaje de saludo del servidor.
                respuesta = bis.readLine();
                Log.d("Saludo", respuesta);//Log.Depuracion para el saludo inicial.

                //Enviamos al servidor "USER"+lo que introduzca en el campo Usuario del login.
                os.write(new String("USER "+User+"\r\n").getBytes());
                os.flush();
                respuesta = bis.readLine();//Recibimos el "OK" tras enviar el User al servidor.
                Log.d("Usuario", respuesta);//Log.Depuracion para la respuesta tras introducir el usuario.

                //Enviamos al servidor "PASS"+lo que introduzca en el campo Pass del login.
                os.write(new String("PASS "+Pass+"\r\n").getBytes());
                os.flush();
                id = bis.readLine();//Leo lo que me envia el servidor tras el PASS, y dado que aqui se hace la comprobacion del pass
                                    //y user, sera donde recibire la Sesionid+Fecha expira sesion o el error de autenticacion.
                Log.d("Pass", id);//Log.Depuracion para la respuesta tras introducir la contraseña.
                if (id.startsWith("ERROR")) {//Si el texto comienza con ERROR, es decir, no se ha autenticado correctamente.
                    os.write(new String("QUIT\r\n").getBytes());//Envio QUIT al servidor para cerrar conexion socket.
                    os.flush();
                    respuesta = bis.readLine();//Leo el mensaje de despedida del servidor.
                    Log.d("Quit", respuesta);//Log.Depuracion para la respuesta tras introducir el Quit.
                    bis.close();//Cierro el buffer de IN.
                    os.close();//Cierro el buffer de OUT.
                    cliente.close();//Cierro el cliente socket.
                    respuesta = "ERROR";//Coloco en respuesta el string "Error" para la comprobacion de este en el metodo tras la hebra.
                    return respuesta;//Devuelvo respuesta.
                }//Fin del if result.startsWith.

                //SESION-ID=SIDUSERMTIzNDU=&EXPIRES=2016-12-01-12-38-22
                String [] sesionid = null;//Creo e inicializo una variable sesionid que sera donde se guarde la respuesta con la sesion.
                String [] expira   = null;//Creo e inicializo una variable expira que sera donde se guarde el tiempo de sesion.
                String [] linea    = null;//Creo e inicializo la variable linea para realizar la separacion con split que me envia el
                                          //servidor y he guardado en la variable id.

                linea = id.split("&");         //Separo por &
                sesionid = linea[0].split("=");//Separo por = y guardo la primera linea de la separacion que contiene la sesionid.
                Log.d("Sesionid", sesionid[1]);
                expira  = linea[1].split("="); //Separo por = y guardo la segunda linea de la separacion que contiene la expira.
                Log.d("Expira", expira[1]);

                //Enviamos Quit al servidor y cierro la conexion socket con el servidor TCP.
                os.write(new String("QUIT\r\n").getBytes());
                os.flush();
                respuesta = bis.readLine();
                Log.d("Quit", respuesta);
                bis.close();
                os.close();
                cliente.close();

                //Preferencias donde guardo los datos, ya que estos se guardaran en un fichero de forma permanente tras cerrar la
                //aplicacion.
                SharedPreferences prefs = getSharedPreferences("DatosSesion", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("Sesion",sesionid[1]);
                editor.putString("Tiempoexpira",expira[1]);
                editor.commit();

            } catch (IOException err){ //Fin del try y captura de la excepción.
             err.printStackTrace();
             respuesta = "IOException: " + err.toString(); //Saco como respuesta el error que se ha producido.
            }//Fin del catch.
            return respuesta; //Devuelvo lo almacenado en la variable respuesta.
        }//Fin doInBackground.

        /**
         * metodo onProgressUpdate donde se puede ir actualizando algo en la IU desde la hebra asincrona.
         * @param values
         */
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        /**
         * metodo onPostExecute el cual recibe la respuesta de la hebra y en funcion del valor que contiene esta respuesta se
         * encargara de enviar al usuario a la actividad del servicio, o si se ha producido algun error lo envia a la actividad
         * del login.
         * @param respuesta
         */
        @Override
        protected void onPostExecute(String respuesta) {
            super.onPostExecute(respuesta);
            pdia.dismiss();//Cierro el pdia.
            if (respuesta != null) {
                if (respuesta.startsWith("OK")) { //Si la respuesta comienza con OK, es que la autenticacion fue correctamente.
                    Toast.makeText(getApplicationContext(), "Autenticacion completada, bienvenido.", Toast.LENGTH_SHORT).show();
                    Intent a = new Intent(ConexActivity.this, Servicio.class);
                    startActivity(a);//Realizar la transición intent con identificador a.
                }//Fin del if respuesta.startsWith.
                if(respuesta.startsWith("ERROR")){ //Si la respuesta comienza con ERROR, es que la autenticion no se ha realizado.
                    Toast.makeText(getApplicationContext(), "Clave y/o usuario incorrecto, inténtelo de nuevo.", Toast.LENGTH_SHORT).show();
                    Intent b = new Intent(ConexActivity.this, MainActivity.class);
                    startActivity(b);//Realizar la transición intent con identificador b.
                }//Fin del if respuesta.startsWith.
            }//Fin del if que comprueba que el result no es nulo.
        }//Fin onPostExecute.
    }//Fin AsynTask.
}//Fin del ConexActivity.
