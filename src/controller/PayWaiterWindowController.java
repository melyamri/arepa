
package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

import model.*;
import view.*;
/**
 * Controlador de la ventana PayWaiterWindow
 * @author Grupo 9
 */
public class PayWaiterWindowController extends TheWindowController{
	/**
	 * Mesa del camarero
	 */
	Station table;
	
	@Override
	public void actionPerformed(ActionEvent e) {
		ResWaiter restaurant = (ResWaiter)getRestaurant();
		JTable order_table = getWindow().getOrderTable();
		int order_row = order_table.getSelectedRow();
		
		super.actionPerformed(e);
		switch (e.getActionCommand()){
		case "paid_button": {
				if (table.getStatus().equals(StatusStruct.PAYING)){
					table.setStatus(StatusStruct.INACTIVE);
					getRestaurant().storeOrderToHistory(table.getOrder());
					if(getRestaurant().getDatabase().updateStation(table)){
						showErrorPane("Pago procesado, mesa desconectada", "Hecho");
					} else showErrorPane("Hubo un error al procesar el pago", "Error");
				}else {
					if(getRestaurant().getDatabase().updateStation(table)){
						showErrorPane("Guardado con éxito", "Hecho");
					} else showErrorPane("Hubo un error al guardar", "Error");
				}
			break;
		} 
		
		case "delete_button":{
			if (order_row > -1){
				if ((table.getOrder().getMenuCount() > 0 || table.getOrder().getPlateCount() > 0) && order_row < (table.getOrder().getMenuCount() + table.getOrder().getPlateCount()))
					table.getOrder().remove((String)order_table.getValueAt(order_row, 0));
				else if (table.getOrder().getPromos().size() > 0 && (order_row > (table.getOrder().getMenuCount() + table.getOrder().getPlateCount()+1)) && (order_row < (table.getOrder().getMenuCount() + table.getOrder().getPlateCount() + 1 + table.getOrder().getPromos().size()) ))
					table.getOrder().removePromo((String)order_table.getValueAt(order_row, 0));
				else showErrorPane("Debe seleccionar un elemento válido", "Error");
			} else showErrorPane("Debe seleccionar un elemento", "Error");	
			getRestaurant().getDatabase().updateOrder(table.getOrder());
			getWindow().initPayWaiter(table);
			break;			
		}
		
		case "back_button":{
			getWindow().dispose();
			setWindow(null);
			break;			
		}
		
		case "profile":{
			ProfileWindowController controller = new ProfileWindowController();
			controller.setView(new ProfileWindow(restaurant.getStation().getActor(), restaurant.getStation().getActor(), controller));
			break;			
		}
		
		case "disconnect":{
			Object[] options = { "Yes", "No" };
			JFrame frame = new JFrame();
			frame.setAlwaysOnTop(true);
		    int n = JOptionPane.showOptionDialog(frame,
		            "¿Está seguro de que quiere salir?", "",
		            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
		            options, options[1]);
		    
		    if(n == JOptionPane.OK_OPTION){ // Afirmative
		    	getRestaurant().disconnect();
		    }			
			break;
		}
		
		}
	}
	public Station getTable() {
		return table;
	}
	public void setTable(Station table) {
		this.table = table;
	}
	@Override
	public void setView (TheWindow window){
		setWindow((PayWaiterWindow) window);
	}
	@Override
	public ResWaiter getRestaurant(){
		return (ResWaiter) super.getRestaurant();
	}
	@Override
	public PayWaiterWindow getWindow(){
		return (PayWaiterWindow) super.getWindow();
	}
	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
