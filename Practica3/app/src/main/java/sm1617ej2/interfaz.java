package sm1617ej2;

/**Created on 13/11/2016.
 * @author Alejandro Romo Rivero.
 */

/**
 * Creacion de la interfaz: Como es una interfaz es un conjunto abstracto de forma que cuando implementemos en una clase la interfaz se nos
 * exigira crear estos metodos e inicializarlos, sacando los valores que se les pasan a los metodos y luego realizando cada una de las
 * tareas para la que estan pensado los metodos. Y finalmente tras el proceso que se debe realizar en cada metodo se debera devolver
 * por medio del return el valor o cadena que debe devolver cada metodo.
 */
public interface interfaz {

    /**
     * Metodo Modificar: Permitira al usuario modificar el producto deseado. Y me devolvera un Booleano para saber si se ha modificado.
     * @param nombreproducto
     * @param proveedor
     * @param identificador
     * @param valoractual
     * @param valorminimo
     * @return modificacion
     */
    public Boolean Modificar(String nombreproducto,String proveedor,int identificador,int valoractual,int valorminimo);

    /**
     * Metodo Eliminar: Permitira al usuario eliminar el producto deseado. Me devolvera un Booleano para saber si se ha eliminado el
     * producto.
     * @param nombreproducto
     * @param proveedor
     * @param identificador
     * @param valoractual
     * @param valorminimo
     * @return eliminado
     */
    public Boolean Eliminar(String nombreproducto,String proveedor,int identificador,int valoractual,int valorminimo);

    /**
     * Metodo Introducir: Permitira al usuario introducir un nuevo producto. Me devolvera un Booleano para saber si se ha introducido el
     * producto.
     * @param nombreproducto
     * @param proveedor
     * @param identificador
     * @param valoractual
     * @param valorminimo
     * @return introducido
     */
    public Boolean Introducir(String nombreproducto,String proveedor,int identificador,int valoractual,int valorminimo);

}//Cierro la interfaz.
