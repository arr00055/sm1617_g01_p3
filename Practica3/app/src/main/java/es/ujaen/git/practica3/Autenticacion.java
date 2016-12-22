package es.ujaen.git.practica3;

/**La clase Autenticacion será la encargada de guardar los datos del usuario.
 * la clase es declarada como public siendo una pieza de código visible en todos los niveles.
 * Se ha cambiado esta clase quitando el puerto, el cual se ha introducido como una constante dentro de la clase que maneja la hebra
 * asincrona en la clase ConexActivity.
 */
public class Autenticacion{
    //Atributos de la clase Autenticacion.

    //Con el modificador protected solo las clases que se encuentren en el mismo paquete pueden ver y acceder a estos atributos.
    protected String mUser="";
    protected String mPass="";
    protected String mIP="";
    /**
     *Constructor de la clase Autenticacion.
     * @param user variable donde se guarda el nombre o nick del usuario.
     * @param pass variable donde se guarda la contraseña del usuario.
     * @param ip   variable donde se guarda la dirección Ip del usuario.
     */
    public Autenticacion(String user,String pass,String ip){

        mUser=user;
        mPass=pass;
        mIP=ip;

    }//Fin del constructor de la clase Autenticacion.
 /**
   *  Método getUser.
   *  @return mUser.
   */
    public String getUser(){
        return mUser;
    }
    /**
     *  Método setUser.
     */
    public void setUser(String user){
        mUser=user;
    }
    /**
     *  Método getPass.
     *  @return mPass.
     */
    public String getPass(){
        return mPass;
    }
    /**
     *  Método setPass.
     */
    public void setPass(String pass){
        mPass=pass;
    }
    /**
     *  Método getIP.
     *  @return mIP.
     */
    public String getIP(){
        return mIP;
    }
    /**
     *  Método setIP.
     */
    public void setIP(String ip){
        mIP=ip;
    }
}//Fin de la clase Autenticacion.
