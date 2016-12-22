package sm1617ej2;
/**Created on 13/11/2016.
 * @author Alejandro Romo Rivero.
 */

/**La clase datos sera la encargada de modelar los datos para el servicio de "Control de Stock" de la aplicacion, esta servicio
 * requiere de los campos: [Nombre del producto, proveedor, valor actual y valor minimo] y seran los que modele esta clase.
 * La clase es declarada como public siendo una pieza de codigo visible en todos los niveles.
 * Para facilitar la busqueda en BD y para evitar conflictos por nombres o proveedores se introduce el campo identificador, ademas de los
 * campos que son necesarios para modelar el control de Stock.
 */
public class datos{
    //Atributos de la clase datos.
    public static final int Valor_Inicial=0;//Valor que coloco para iniciar los enteros.
    //Con el modificador protected solo las clases que se encuentren en el mismo paquete pueden ver y acceder a estos atributos.
    protected String mNombreproducto="";
    protected String mProveedor="";
    protected int    mValoractual=Valor_Inicial;
    protected int    mValorminimo=Valor_Inicial;
    protected int    mIdentificador=Valor_Inicial;

    /**
     *Constructor de la clase datos.
     * @param nombreproducto Cadena que se le pasa al constructor de la clase datos con el nombre del producto.
     * @param proveedor      Cadena que se pasa al constructor de la clase datos con el nombre del proveedor asociado al producto.
     * @param valoractual    Entero que se pasa al constructor de la clase datos con el valor actual del producto en stock.
     * @param valorminimo    Entero que se pasa al constructor de la clase datos con el valor minimo del producto que se debe tener.
     * @param identificador  Entero que se pasa al constructor de la clase datos con el identificador que se generara para cada producto.
     * Una vez se le pasan estos datos al constructor cuando se le llama en el codigo, guarda aqui los valores que se le han pasado
     * entre () y los almacena en las variables que estan declaradas en la clase y con los metodos de la clase se podra acceder a estos
     * valores cuando lo necesita.
     */
    public datos(String nombreproducto,String proveedor,int valoractual, int valorminimo, int identificador){

        mNombreproducto=nombreproducto;
        mProveedor=proveedor;
        mValoractual=valoractual;
        mValorminimo=valorminimo;
        mIdentificador=identificador;

    }//Fin del constructor de la clase datos.

    //Los metodos get: Me permiten obtener el valor de los diferentes campos que se modelan en la clase datos.
    //Los metodos set: Me permiten introducir un determinado valor que pase al llamar al metodo entre sus () y que lo almacene en
    //la variable correspondiente a ese metodo set.

    /**
     *  Metodo getNombreproducto.
     *  @return mNombreproducto.
     */
    public String getNombreproducto(){
        return mNombreproducto;
    }

    /**
     *  Metodo setNombreproducto.
     */
    public void setNombreproducto(String nombreproducto){
        mNombreproducto=nombreproducto;
    }

    /**
     *  Metodo getProveedor.
     *  @return mProveedor.
     */
    public String getProveedor(){
        return mProveedor;
    }

    /**
     *  Metodo setProveedor.
     */
    public void setProveedor(String proveedor){
        mProveedor=proveedor;
    }

    /**
     *  Metodo getValoractual.
     *  @return mValoractual.
     */
    public int getValoractual(){
        return mValoractual;
    }

    /**
     *  Metodo setValoractual.
     */
    public void setValoractual(int valoractual){
        mValoractual=valoractual;
    }

    /**
     *  Metodo getValorminimo.
     *  @return mValorminimo.
     */
    public int getValorminimo(){
        return mValorminimo;
    }

    /**
     *  Metodo setValorminimo.
     */
    public void setValorminimo(int valorminimo){
        mValorminimo=valorminimo;
    }

    /**
     *  Metodo getIdentificador
     *  @return mIdentificador.
     */
    public int getIdentificador(){
        return mIdentificador;
    }

    /**
     *  Metodo setIdentificador
     */
    public void setIdentificador(int identificador){
        mIdentificador=identificador;
    }

    /**
     * Metodo toString()
     * Este metodo es el encargado de sacar como una cadena cada uno de los campos que debemos enviar en nuestro mensaje del protocolo,
     * correctamente formateado segun la notacion ABNF para nuestro mensaje de protocolo. La clase mensajes llamara a este metodo para
     * enviar cada correspondiente cabecera con los datos correctamente ordenados.
     * @return mNombreproducto+" "+mProveedor+" "+mIdentificador+" "+mValoractual+" "+mValorminimo;
     */
    @Override
    public String toString(){
        return mNombreproducto+" "+mProveedor+" "+mIdentificador+" "+mValoractual+" "+mValorminimo;
    }

}//Fin de la clase datos.
