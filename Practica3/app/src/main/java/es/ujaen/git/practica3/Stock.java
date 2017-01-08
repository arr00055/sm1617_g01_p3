package es.ujaen.git.practica3;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Stock extends AppCompatActivity {
    //Inicializo el mHandler de tipo Handler.
    private static Handler mHandler = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);
        //Defino el boton.
        Button boton1 = (Button) findViewById(R.id.stock_boton);
        //Capturo el evento de pulsado boton de la actividad para extraer los datos colocados en los EditText.
        boton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View View){
                EditText nombre = (EditText) findViewById(R.id.stock_name);   //Extraigo EditText del nombre.
                EditText provee = (EditText) findViewById(R.id.stock_proveed);//Extraigo EditText del proveedor.
                EditText vactual = (EditText) findViewById(R.id.stock_vact);  //Extraigo EditText del valor actual.
                EditText vminimo = (EditText) findViewById(R.id.stock_vmin);  //Extraigo EditText del valor minimo.

                //Conversiones de los EditText a cadenas y enteros.
                //Cadena para el nombre del producto.
                String nombreproducto = nombre.getText().toString();
                //Cadena para el proveedor del producto.
                String proveedor      = provee.getText().toString();
                //Cadena y conversion a entero del valor actual del producto.
                String valoractual1   = vactual.getText().toString();
                int valoractual = Integer.parseInt(valoractual1);
                //Cadena y conversion a entero del valor minimo del producto.
                String valorminimo1   = vminimo.getText().toString();
                int valorminimo = Integer.parseInt(valorminimo1);
                //Creo un objeto de la clase datos que contiene y moldea los valores que caracterizan al producto.
                datos producto = new datos(nombreproducto,proveedor,valoractual,valorminimo);
                //Muestro cuando se pulsa el boton que los datos del producto ("Con su nombre") van a ser enviados.
                Toast.makeText(getApplicationContext(), "Enviando datos del producto: "+producto.getNombreproducto(), Toast.LENGTH_SHORT).show();
                //Lanzo el hilo de HebraConectar pasandole un objeto producto de tipo datos que contiene los valores de los EditText.
                new Thread(new HebraConectar(producto)).start();
            }//Fin onClick.
        }); //Fin del setOnClickListener.

        //Creo el Handler para comunicarme con la UI desde mi hilo de trabajo ("Thread").
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message inputMessage) {
                String respuesta = "";
                // Obtiene el mensaje de la hebra de conexión.
                switch (inputMessage.what) {
                    case 1:
                        respuesta = inputMessage.getData().getString("response");
                        //Aviso al usuario de que el producto ha sido enviado al servidor.
                        Toast.makeText(getApplicationContext(), "Producto enviado.", Toast.LENGTH_SHORT).show();
                        break;
                }//Fin del switch.
            }//Fin del handleMessage.
        };//Fin del Handler.
    }//Fin onCreate.

    //Creo el hilo HebraConectar.
    public class HebraConectar implements Runnable {

        String mDatos = null; //Inicialio los datos recolectados del EditText.

        //Constructor de la HebraConectar.
        public HebraConectar(datos data) {
            //Creo una variable mDatos que obtiene los datos en una cadena listos para enviarlos tal y como se declaro en la clase datos.
            mDatos = data.toString();
            //Toast.makeText(getApplicationContext(), "Data: "+mDatos, Toast.LENGTH_SHORT).show();
        }

        //Clase enviaRespuesta para poder moldear la respuesta desde el hilo para enviarla al Handler.
        protected void enviaRespuesta(String respuesta) {
            Message recibido = Message.obtain(mHandler, 1);
            Bundle datos = new Bundle();
            datos.putString("response", respuesta);
            recibido.setData(datos);
            recibido.sendToTarget();
        }

        @Override
        public void run() {
            String respuesta = "";
            Socket cliente;

            try {
                //Extraigos los datos guardados en preferencias en la clase de la autenticacion para realizar la conexion sin necesidad
                //de pedir mas datos.
                SharedPreferences prefs = getSharedPreferences("DatosSesion", Context.MODE_PRIVATE);
                String sesionid  = prefs.getString("Sesion","");
                String expires   = prefs.getString("Tiempoexpira","");
                String User      = prefs.getString("Usuario","");
                String Pass      = prefs.getString("Pass", "") ;
                String Ip        = prefs.getString("Ip","");
                InetSocketAddress direccion = new InetSocketAddress(Ip, 6000);
                //Se crea el socket TCP
                cliente = new Socket();
                //Se realiza la conexión al servidor
                cliente.connect(direccion);
                //Se leen los datos del buffer de entrada
                BufferedReader bis = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                OutputStream os = cliente.getOutputStream();
                respuesta = bis.readLine();
                enviaRespuesta(respuesta);

                os.write(new String("USER " +User+"\r\n").getBytes());
                os.flush();
                respuesta = bis.readLine();
                enviaRespuesta(respuesta);

                os.write(new String("PASS "+Pass+"\r\n").getBytes());
                os.flush();
                respuesta = bis.readLine();
                if (respuesta != null) {
                    if (respuesta.startsWith("OK"))
                        respuesta = "Autenticado correctamente";
                    else {
                        respuesta = "Error de autenticación";
                    }
                }
                enviaRespuesta(respuesta);
                //Envio al servidor el producto con los datos.
                    os.write(new String("Producto: " + mDatos + "\r\n").getBytes());
                    os.flush();
                    respuesta = bis.readLine();
                    enviaRespuesta(respuesta);

                os.write(new String("QUIT\r\n").getBytes());
                os.flush();
                respuesta = bis.readLine();
                enviaRespuesta(respuesta);
                bis.close();
                os.close();
                cliente.close();
            } catch (UnknownHostException e) {
                e.printStackTrace();
                respuesta = "UnknownHostException: " + e.toString();
            } catch (IOException e) {
                e.printStackTrace();
                respuesta = "IOException: " + e.toString();
            }
        }//Fin del Run
    }//Fin Hebraconectar
}//Fin clase Stock.
