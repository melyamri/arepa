
package model;
/**
 * Interfaz para permitir un acceso a los datos de ResClient sin poder modificarlos.
 * @author Grupo 9
 *
 */
public interface ObservableResClient {
	/**
	 * Devuelve el pedido actual.
	 * @return Order 
	 */
	public Order getOrder() ;

}
