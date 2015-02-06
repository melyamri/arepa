
package controller;

import java.awt.event.ActionEvent;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import view.*;
import model.*;
/**
 * Controlador de la ventana MainWaiterWindow
 * @author Grupo 9
 *
 */
public class MainWaiterWindowController extends TheWindowController{

	@Override
	public void actionPerformed(ActionEvent e) {
		ResWaiter restaurant = (ResWaiter)getRestaurant();
		JTable order_table = getWindow().getOrderTable();
		int order_row = order_table.getSelectedRow();
		JTable tables_table = getWindow().getTablesTable();
		int tables_row = tables_table.getSelectedRow();
		
		super.actionPerformed(e);
		switch (e.getActionCommand()){
		case "served_button": {
			if (order_row != -1)
				getRestaurant().deliverPlate(getRestaurant().getFinishedPlate(order_row), (String)order_table.getValueAt(order_row, 1));
			else showErrorPane("Debe seleccionar un plato", "Error");
			break;
		} 
		
		case "pay_button":{
			if (tables_row != -1){
				PayWaiterWindowController controller = new PayWaiterWindowController();
				controller.setRestaurant(getRestaurant());
				controller.setView(new PayWaiterWindow(getRestaurant().getTable((String)tables_table.getValueAt(tables_row, 0)), controller));
				controller.setObserver();
			} else showErrorPane("Debe seleccionar una mesa", "Error");	
			break;			
		}
		
		case "activate_button":{
			if (tables_row != -1){
				getRestaurant().setTableActiveInactive((String)tables_table.getValueAt(tables_table.getSelectedRow(), 0), false);
			} else showErrorPane("Debe seleccionar una mesa", "Error");
			break;			
		}
		
		case "profile":{
			ProfileWindowController controller = new ProfileWindowController();
			controller.setRestaurant(getRestaurant());
			controller.setView(new ProfileWindow(restaurant.getStation().getActor(), restaurant.getStation().getActor(), controller));
			break;			
		}
		
		case "disconnect":{
			
			Object[] options = { "Sí", "No" };
			JOptionPane pane = new JOptionPane("¿Está seguro de que quiere salir?", JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION,  null,
		            options, options[1]);
		    JDialog dialog = pane.createDialog("");
		    dialog.setAlwaysOnTop(true);
		    dialog.setVisible(true);
		    int n =0;
		    switch((String)pane.getValue()){
		    case "Sí": n = 1; break;
		    case "No": n = 0; break;
		    }
		    if(n == 1){ // Afirmative
		    	getRestaurant().disconnect();
		    }			
			break;
		}
		
		case "timer":{
			System.out.println("Waiter Timer Activated");
			if (!getRestaurant().getDatabase().isServiceStarted()){
				LoginWindowController controller = new LoginWindowController();
				getRestaurant().getStation().setActor(null);
				getRestaurant().getStation().setOrder(null);
				getRestaurant().getDatabase().updateStation(getRestaurant().getStation());
				controller.setRestaurant(getRestaurant());
				controller.setView(new LoginWindow(ActorStruct.WAITER, controller));
				controller.setObserver();
				getWindow().dispose();
				getWindow().getTimer().stop();
				setWindow(null);
			}else{
				load();
				initTables();
				getWindow().getOrderTable().getSelectionModel().setSelectionInterval(order_row, order_row);
				getWindow().getTablesTable().getSelectionModel().setSelectionInterval(tables_row, tables_row);
			}
			break;
		}
		}
	}
	/**
	 * Método que carga los platos finalizados y las mesas del restaurante
	 */
	public void load(){
		getRestaurant().loadFinishedPlates();
		getRestaurant().loadTables();
	}
	/**
	 * Método que llama a los métodos de la ventana que inicializan las dos tablas
	 */
	public void initTables(){
			getWindow().updateOrderTable(getRestaurant());
			getWindow().updateTablesTable(getRestaurant());
		
		}
	@Override
	public void setView (TheWindow window){
		setWindow((MainWaiterWindow) window);
	}
	@Override
	public MainWaiterWindow getWindow(){
		return (MainWaiterWindow) super.getWindow();
	}
	public ResWaiter getRestaurant(){
		return (ResWaiter) super.getRestaurant();
	}
	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
