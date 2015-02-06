
package view;
import java.util.Observer;
import java.util.EventListener;
/**
 * 	Interfaz que implementan todas las vistas para controlar los eventos. 
 * Contiene el método que añade los escuchadores de eventos.
 * @author Grupo 9
 *
 */
public interface ViewInterface extends Observer{
	/**
	 * Método para fijar el controlador de cada ventana, en él se deben añadir los
	 * correspondientes escuchadores a los botones de la ventana y las tablas en caso de 
	 * que sean interactivas.
	 * @param controller el controlador de la ventana
	 */
	void setController(EventListener controller) ;

}
