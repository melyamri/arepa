
package view;
import java.util.Observer;
import java.util.EventListener;
/**
 * 	Interfaz que implementan todas las vistas para controlar los eventos. 
 * Contiene el m�todo que a�ade los escuchadores de eventos.
 * @author Grupo 9
 *
 */
public interface ViewInterface extends Observer{
	/**
	 * M�todo para fijar el controlador de cada ventana, en �l se deben a�adir los
	 * correspondientes escuchadores a los botones de la ventana y las tablas en caso de 
	 * que sean interactivas.
	 * @param controller el controlador de la ventana
	 */
	void setController(EventListener controller) ;

}
