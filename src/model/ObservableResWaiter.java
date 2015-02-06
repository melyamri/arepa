
package model;

import java.util.HashMap;
import java.util.List;
/**
 * Interfaz para permitir un acceso a los datos de ResWaiter sin poder modificarlos.
 * @author Grupo 9
 *
 */
public interface ObservableResWaiter {
	/**
	 * Devuelve una lista de las mesas cliente.
	 * 
	 */
	public HashMap<String, Station> getTables() ;
	/**
	 * Devuelve una lista de los platos cocinados listos para entregar en mesa.
	 * 
	 */
	public List<Plate> getFinishedPlates() ;
	/**
	 * Devuelve una mesa por su ID
	 * @param id ID de la mes
	 * @return Station
	 */
	public Station getTable(String id) ;
	/**
	 * 
	 * @param name
	 * @return
	 */
	public Plate getFinishedPlate(String name) ;

}
