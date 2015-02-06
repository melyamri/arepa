
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.*;
import view.*;
/**
 * Controladro abstracto para crear los dem�s controladores de cada venana. Tiene los m�todos principales de cada controlador.
 * Cada controlador hijo debe reimplementar todos los m�todos para escuchar los eventos
 * correspondientes a su ventana y tratarlos seg�n convenga.
 * 
 * @author Grupo 9
 * @implements ActionListener
 * @implements ListSelectionListener
 */
public  abstract class TheWindowController implements ActionListener, ListSelectionListener{
  /**
   * La ventana principal
   */
  protected TheWindow w;
  /**
   * El restaurante
   */
  protected Restaurant restaurant;

	/**
	 *M�todo que se encarga de tratar los eventos gen�ricos de tipo ActionEvent,
	 *se encarga de reaccionar a dichos eventos seg�n corresponda.
	 * 
	 * 
	 * @param e el ActionEvent
	 */
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()){
		case "help_button": HelpWindowController controller = new HelpWindowController();
							HelpWindow help_window = new HelpWindow(w.getClass().getSimpleName(), ActorStruct.CLIENT, controller);						
							controller.setView(help_window);
							
							break;
							
		case "cancel_call": getWindow().destroyCallWaiterPanel();
							if (!getWindow().getCallButton().getText().equals("")){
								if (getWindow().getTitleLabel().equals("Que aproveche")){
									getRestaurant().setStatus(StatusStruct.EATING);
								} else getRestaurant().setStatus(StatusStruct.ORDERING);
								getWindow().getCallButton().setText("");
							}
							break;
							
		}
		
	}
	/**
	 * Establece la ventana a la que corresponde el controlador
	 * @param window la ventana
	 */
	public void setView (TheWindow window){
		this.setWindow(window);
	}
	
	/**
	 * Establece el modelo Restaurant en el controlador
	 * @param r el restaurante
	 */
	public void setRestaurant (Restaurant r){
		this.restaurant = r;
	}
	/**
	 * Devuelve el restaurante, puede estar parametrizado con el tipo de Restaurant
	 * 
	 * @return Restaurant ResCook, ResCient o ResWaiter
	 */
	public Restaurant getRestaurant(){
		return this.restaurant;
	}
	/**
	 * A�ade el observador al modelo
	 */
	public void setObserver(){
		getRestaurant().addObserver(getWindow());
	}
	
	/**
	 * Devuelve la ventana que controla el controlador, puede estar parametrizado a cualquiera
	 * de las ventanas hija
	 */
	public TheWindow getWindow() {
		return w;
	}
	
	public void setWindow(TheWindow w) {
		this.w = w;
	}
	
	/**
	 * Muestra un mensaje de error con una determinada informaci�n
	 * @param message el mensaje
	 * @param title t�tulo del error
	 */
	protected static void showErrorPane(String message, String title) {
			JOptionPane pane = null;
		 	if (title == "Error") pane = new JOptionPane(message, JOptionPane.ERROR_MESSAGE);
		 	else if (title == "Hecho") pane = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE);
		   JDialog dialog = pane.createDialog(title);
		   dialog.setAlwaysOnTop(true);
		   dialog.setVisible(true);
		}
	/**
	 * M�todo que captura los eventos gen�ricos de tipo ListSelectionEvent,
	 * sirve para recoger eventos procedentes de las tablas y tratarlos seg�n convega
	 * @param e ListSelectionEvent
	 * 
	 */
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
