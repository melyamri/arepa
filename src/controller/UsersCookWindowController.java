
package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.text.DateFormatSymbols;
import java.util.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;

import model.*;
import view.*;
/**
 * Controlador de la ventana UsersCookWindow
 * @author Grupo 9
 *
 */
public class UsersCookWindowController extends TheWindowController{

	@Override
	public void actionPerformed(ActionEvent e) {
		JTable user_table = getWindow().getUserTable();
		
		switch(e.getActionCommand()){
		case "back":{
			getWindow().dispose();
			getWindow().getTimer().stop();
			break;
		}
		case "delete":{
			if(getRestaurant().getDatabase().removeActor(getWindow().getUserText().getText())){
				showErrorPane("Se ha eliminado el usuario correctamente", "Hecho");
			}else{
				showErrorPane("Error al eliminar el usuario", "Error");
			}
			break;
		}
		case "modify":{
			ProfileWindowController controller = new ProfileWindowController();
			controller.setRestaurant(getRestaurant());
			controller.setView(new ProfileWindow(getRestaurant().getStation().getActor(), getRestaurant().getDatabase().getActor((String)getWindow().getUserText().getText()),controller ));
			controller.setObserver();
			break;
		}
		case "disconnect":{
			if (user_table.getSelectedRow() > -1){
				Station st = getRestaurant().getAllStation((String)user_table.getValueAt(user_table.getSelectedRow(), 1));
				if (!st.getActor().getActorClass().equals(ActorStruct.WAITER) && !st.getActor().getActorClass().equals(ActorStruct.COOK)){
					getRestaurant().disconnectUser(st);
					showErrorPane("Se ha desconectado el usuario correctamente", "Hecho");
				}
				else showErrorPane("No se puede desconectar a camareros ni a cocineros", "Error");
			} else showErrorPane("Debe seleccionar un usuario", "Error");
			break;
		}
		case "timer":{
			System.out.println("Cook Users Timer Activated");
			load();
			initTables();
			break;
		}
		}	
	} 	
	@Override
	public void setView (TheWindow window){
		this.setWindow((UsersCookWindow)window);
	}
	@Override
	public UsersCookWindow getWindow(){
		return (UsersCookWindow) super.getWindow();
	}
	@Override
	public ResCook getRestaurant(){
		return (ResCook)super.getRestaurant();
	}
	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * Carga todas las mesas existentes en el restaurante
	 */
	public void load() {
		getRestaurant().loadAllStations();		
	}
	/**
	 * Llama al método de la ventana que inicializa las tablas
	 */
	public void initTables() {
		int user_row = getWindow().getUserTable().getSelectedRow();
		getWindow().updateUserTable(getRestaurant());
		getWindow().getUserTable().getSelectionModel().setSelectionInterval(user_row, user_row);
	}
}
