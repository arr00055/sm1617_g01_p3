package sm1617ej2;

/**Created on 13/11/2016.
 * @author Alejandro Romo Rivero.
 */

/**
 * La clase mensajes sera la encargada de formatear correctamente los mensajes del protocolo tal y como indica la notacion ABNF para
 * nuestro servicio de control de stock. Para ello nuestra clase contara con unas constantes necesarias para el correcto formateo del
 * mensaje de protocolo. Y con un switch dentro del constructor de nuestra clase que sera el encargado de seleccionar la cabecera
 * correspondiente en funcion del tipo de mensaje o peticion que necesitemos realizar. De forma que, si necesitamos Introducir un
 * nuevo producto se usara la header correspondiente INTRO, MODIF para modificar un producto y ELIMI para eliminar un producto.
 * Finalmente, se crea una clase tobyteArray(), la cual devuelve un array de byte con la informacion adecuada del mensaje del
 * protocolo, para ello introduce en una cadena la cabecera formateada adecuadamente junto con los datos que son llamados de
 * la clase datos donde se cuentan con los metodos y el formateo adecuado para los datos necesarios.
 */
public class mensajes {
    public static final String MODIF = "MODIF";
    public static final String ELIMI = "ELIMI";
    public static final String INTRO = "INTRO";
    public static final String CRLF = "\r\n";
    public static final String SP = " ";

    protected String header = " ";
    protected datos  data   = null;

    /**
     * Constructor de la clase mensajes.
     * Cuando se llame al constructor de la clase, se colocara el tipo de cabecera con sus datos asociados que sera enviado al servidor.
     * Para realizar este control se hara a partir de un switch en funcion del valor del tipo. De esta forma cuando llamemos al constructor
     * de esta clase se generara el mensaje del protocolo adecuado.
     * @param tipo
     * @param data
     */
    public mensajes(int tipo, datos data) {
        switch (tipo) {
            case 1:
                header = INTRO;
                break;
            case 2:
                header = MODIF;
                break;
            case 3:
                header = ELIMI;
                break;
        }//Fin del Switch.
        this.data = data;
       }//Fin del constructor de la clase mensajes.

    /**
     * Metodo tobyteArray()
     * @return m.getBytes()
     */
    public byte[] tobyteArray(){
        String m = header+SP+data.toString()+CRLF;
        return m.getBytes();
    }//Fin de la clase tobyteArray.
}//Fin de la clase mensajes.
