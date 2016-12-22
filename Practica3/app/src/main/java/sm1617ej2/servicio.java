package sm1617ej2;

import java.io.*;
import java.net.*;

/**Created on 13/11/2016.
 * @author Alejandro Romo Rivero.
 */

/**
 * La clase servicio sera la clase abstracta donde se implementan los metodos basicos para conseguir dar el servicio de control de
 * stock. Esta clase seria la que emplearia el servidor y cuenta con metodos para la Autenticacion del usuario, para recibir su
 * peticion y para cerrar el la conexion cuando expire el tiempo de conexion o cuando el usuario decida cerrar la conexion.
 */
public abstract class servicio {

    static ServerSocket mServidor = null; //Se inicializa la variable del mServidor del tipo ServerSocket.
    public static int mConexiones = 0;    //Se inicializa la variable Conexiones de tipo entero, para el numero de conexiones.
    final int PUERTO=6000;                //Puerto en el que estara escuchando nuestro servidor TCP por conexiones.
    Socket mSocket;                       //Variable mSocket tipo Socket.
    String inputData = null;              //Cadena para los datos de entrada.
    String outputData = "";               //Cadena para los datos de salida.
    String parametro = "";                //Cadena para los parametros.
    String tempUser = "";                 //Cadena para la temporizacion del usuario.

    /**
     * Metodo Principal de la clase.
     * @param args
     */
    abstract void main(String[] args);

    /**
     * Metodo Login para la autenticacion del usuario, me devolvera un Booleano para comprobar si ha podido autenticarse.
     * @param user
     * @param pass
     * @return autenticado
     */
    abstract public Boolean Login(String user,String pass);

    /**
      * Metodo close utilizado para cerrar la conexion.
      * @return cerrando
     */
    abstract public String close();

    /**
     * Metodo peticion utilizado para recibir la peticion del usuario, se sacara la informacion del mensaje del protocolo y se procesara
     * su peticion. Se respondera con una cadena para responder que la peticion ha sido recibida.
     * @return respuesta
     */
    abstract public String peticion();
}//Fin clase abstracta servicio.
