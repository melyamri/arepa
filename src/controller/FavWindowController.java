
package controller;

import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import model.*;
import view.*;
/**
 * Controlador de la ventana FavWindow
 * @author 
 *
 */
public class FavWindowController extends TheWindowController{

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		JTable order_table = getWindow().getFavTable();
		
		switch(e.getActionCommand()){
			case "back":{
				getWindow().dispose();
				setWindow(null);
				break;
			}
			case "delete":{
				if (order_table.getSelectedRow() != -1){
					getRestaurant().getStation().getActor().removeFavourite((String)order_table.getValueAt(order_table.getSelectedRow(), 0));
					getRestaurant().getDatabase().updateActor(getRestaurant().getStation().getActor());
					this.init();
				}
				else showErrorPane("Debe seleccionar un plato", "Error");					
				break;
			}
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * Llama al método de inicializar la ventana que inicializa la tabla de favoritos
	 */
	public void init(){
		getWindow().init(getRestaurant());
	}
	
	@Override
	public FavWindow getWindow(){
		return (FavWindow) super.getWindow();
	}
	
	@Override
	public ResClient getRestaurant(){
		return (ResClient)super.getRestaurant();
	}
}
