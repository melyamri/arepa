
package controller;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;

import view.*;
import model.*;
/**
 * Controladro de la venta ServiceCookWindow
 * @author Grupo 9
 *
 */
public class ServiceCookWindowController extends TheWindowController{

	@Override
	public void actionPerformed(ActionEvent e) {
		
		JTable order_table = getWindow().getOrderTable();
		JTable ingredient_table = getWindow().getIngredientTable();
		
		super.actionPerformed(e);
		switch (e.getActionCommand()){
		case "cooked_button": {
			if (order_table.getSelectedRow() != -1){
				getRestaurant().cookPlate(getRestaurant().getOrderedPlate(order_table.getSelectedRow()), getRestaurant().getAllStation((String)order_table.getValueAt(order_table.getSelectedRow(), 1)));
				
				
		}	
			else showErrorPane("Debe seleccionar un plato", "Error");
			break;
		} 
		
		case "info_button":{
			if (order_table.getSelectedRow() != -1){
				InfoWindowController controller = new InfoWindowController();
				controller.setRestaurant(getRestaurant());
				controller.setView(new InfoWindow(getRestaurant().getPlate((String)order_table.getValueAt(order_table.getSelectedRow(), 0)), controller));
				controller.setObserver();
			} else showErrorPane("Debe seleccionar un plato", "Error");	
			break;			
		}
		
		case "block_button":{
			if (ingredient_table.getSelectedRow() != -1){
				if ((String)(ingredient_table.getValueAt(ingredient_table.getSelectedRow(), 2)) == "Sí"){
					getRestaurant().unBlockIngredient((String)ingredient_table.getValueAt(ingredient_table.getSelectedRow(), 0));
				} else getRestaurant().blockIngredient((String)ingredient_table.getValueAt(ingredient_table.getSelectedRow(), 0));
			} else showErrorPane("Debe seleccionar un ingrediente", "Error");
			break;			
		}
		
		case "end_service_button":{
			if (getRestaurant().endService()){
				StartCookWindowController controller = new StartCookWindowController();
				controller.setRestaurant(getRestaurant());
				controller.setView(new StartCookWindow(controller));
				controller.setObserver();
				getWindow().getTimer().stop();
				getWindow().dispose();
				setWindow(null);
			}
			break;			
		}
		
		case "users":{
			UsersCookWindowController controller = new UsersCookWindowController();
			controller.setRestaurant(getRestaurant());
			controller.setView(new UsersCookWindow(controller));
			controller.setObserver();
			break;				
		}
		
		case "history":{
			HistoryWindowController controller = new HistoryWindowController();
			controller.setRestaurant(getRestaurant());
			controller.setView(new HistoryWindow(ActorStruct.COOK, "cook", controller));
			controller.setObserver();
			break;			
		}
		
		case "timer":{
			System.out.println("Cook Service Timer Activated");
			load();
			initTables();
			break;
		}
		
		}
	}
	
	/**
	 * Carga los platos pedidos, los ingredientes y las mesas del restaurante
	 */
	public void load(){
		getRestaurant().loadOrderedPlates();
		getRestaurant().loadIngredients();
		getRestaurant().loadAllStations();
	}
	/**
	 * Llama al método de la ventana que inicializa las tablas
	 */
	public void initTables(){
			
		int order_row = getWindow().getOrderTable().getSelectedRow();
		int ingredient_row = getWindow().getIngredientTable().getSelectedRow();
		getWindow().updateOrderTable(getRestaurant());
		getWindow().updateIngredientTable(getRestaurant());
		getWindow().getOrderTable().getSelectionModel().setSelectionInterval(order_row, order_row);
		getWindow().getIngredientTable().getSelectionModel().setSelectionInterval(ingredient_row, ingredient_row);
			
	}
	@Override
	public void setView (TheWindow window){
		setWindow((ServiceCookWindow) window);
	}
	@Override
	public ServiceCookWindow getWindow(){
		return (ServiceCookWindow) super.getWindow();
	}
	@Override
	public ResCook getRestaurant(){
		return (ResCook)super.getRestaurant();
	}
	@Override
	public void valueChanged(ListSelectionEvent e) {
		String source = "";
		if (this.getWindow().getIngredientTable().getSelectionModel() == e.getSource())
			source = "ingredient_table";
		else if (this.getWindow().getOrderTable().getSelectionModel() == e.getSource())
			source = "order_table";
		
		switch(source){
		case "order_table":
			if (getWindow().getOrderTable().getSelectedRow() > -1){
				getWindow().getIngredientTable().getSelectionModel().clearSelection();
			}
			break;
		case "ingredient_table":
			if (getWindow().getIngredientTable().getSelectedRow() > -1){
				getWindow().getOrderTable().getSelectionModel().clearSelection();
			}
			break;
		
		}
	}
}
