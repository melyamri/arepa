
package model;
import view.*;

import java.util.HashMap;
import java.util.List;
/**
 * Interfaz para permitir un acceso a los datos de Restaurant sin poder modificarlos.
 * @author Grupo 9
 *
 */
public interface ObservableRestaurant {
	/**
	 * Devuelve el puesto desde el cual se est� ejecutando la aplicaci�n.
	 * @return Station
	 */
  public Station getStation() ;
  /**
   * Devuelve la base de datos.
   * @return DatabaseIO
   */
  public DatabaseIO getDatabase() ;
  /**
   * Devuelve una lista de los platos que existen en el restaurante.
   * 
   */
  public HashMap<String, Plate> getPlates() ;
  /**
   * Devuelve un plato por su nombre
   * @param name Nombre del plato
   * @return Plate
   */
  public Plate getPlate(String name) ;
  /**
   * Devuelve una lista de las promociones existentes en el restaurante.
   * 
   */
  public HashMap<String, Promo> getPromos() ;
  /**
   * Devuelve una promoci�n por su nombre
   * @param name Nombre de la promoci�n
   * @return Promo
   */
  public Promo getPromo(String name) ;
  /**
   * Devuelve la lista de men�s existentes en el restaurante.
   * 
   */
  public HashMap<String, Menu> getMenus() ;
  /**
   * Devuelve un men� por su nombre
   * @param name Nombre del men�
   * @return Menu
   */
  public Menu getMenu(String name) ;

}
