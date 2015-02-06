
package model;

import java.util.HashMap;
import java.util.List;
/**
 * Interfaz para permitir un acceso a los datos de ResCook sin poder modificarlos.
 * @author Grupo 9
 *
 */
public interface ObservableResCook {
	/**
	 * Determina si ha empezado el servicio de mesas 
	 * @return true si ha empezado el servicio, false en caso contrario.
	 */
 public boolean getServiceStarted() ;
  /**
   * Devuelve todas las mesas del restaurante.
   * @return Lista de mesas
   */
 public HashMap<String, Station> getAllStations() ;
 /**
  * Devuelve una lista de todos los platos de los pedidos.
  * 
  */
 public List<Plate> getOrderedPlates() ;
 /**
  * Devuelve una lista con los ingredientes que hay en  del restaurante.
  * 
  */
 public HashMap<String, Ingredient> getIngredients() ;
 /**
  * Devuelve una mesa por su id.
  * @param id el ID de la mesa
  * 
  */
 public Station getAllStation(String id) ;
 /**
  * Devuelve un plato de un pedido por su nombre
  * @param name Nombre del plato
  * 
  */
 public Plate getOrderedPlate(String name) ;
 /**
  * Devuelve un ingrediente por su nombre
  * @param name Nombre del ingrediente
  * 
  */
 public Ingredient getIngredient(String name) ;
 /**
  * Devuelve un plato de un pedido por su posición
  * @param position posición del plato.
  * @return
  */
 public Plate getOrderedPlate(int position);

}
