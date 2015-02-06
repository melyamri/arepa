
package controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

import model.*;
import view.*;
/**
 * Controlador de la ventana PromoWindow
 * @author Grupo 9
 *
 */
public class PromoWindowController extends TheWindowController{

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		
		switch(e.getActionCommand()){
			case "back":{
				if (getRestaurant().getOrder().getPromos().size()>0)
					getRestaurant().getOrder().removePromo(getRestaurant().getOrder().getPromo(0).getName());
				OrderWindowController controller = new OrderWindowController();
				controller.setRestaurant(getRestaurant());
				controller.setView(new OrderWindow(ActorStruct.CLIENT, controller));
				controller.setObserver();
				getWindow().dispose();
				setWindow(null);
				break;
			}
			case "endorder":{
				getWindow().getTimer().stop();
				getRestaurant().storeOrder(); 
				getRestaurant().setStatus(StatusStruct.EATING);
				EatWindowController controller = new EatWindowController();
				controller.setRestaurant(getRestaurant());
				controller.setView(new EatWindow(ActorStruct.CLIENT, controller));
				controller.setObserver();
				getWindow().dispose();
				setWindow(null);
				break;
			}
			case "callwaiter":{
				getRestaurant().getStation().setStatus(StatusStruct.CALLING);
				getRestaurant().getDatabase().updateStation(getRestaurant().getStation());
				getWindow().createCallWaiterPanel(false);
				break;
			}
			case "timer":{
				System.out.println("Client Promo Timer Activated");
				Station s = getRestaurant().getDatabase().getStation(getRestaurant().getStation().getStationId());
				if (s == null){
					getWindow().getTimer().stop();
					System.exit(0);
				} else if (!s.getStatus().equals(StatusStruct.CALLING)){
					System.out.println( getWindow().getClass().getName());
						if (getWindow().getCallWaiterFrame() != null)
							getWindow().destroyCallWaiterPanel(); 
						if (s.getStatus().equals(StatusStruct.INACTIVE)  || !getRestaurant().getDatabase().isServiceStarted()){
							showErrorPane("Has sido desconectado. Contacta con un camarero para más información", "Hecho");
						LoginWindowController controller = new LoginWindowController();
						getRestaurant().getStation().setStatus(StatusStruct.INACTIVE);
						getRestaurant().getStation().setActor(null);
						getRestaurant().getStation().setOrder(null);
						getRestaurant().getDatabase().updateStation(getRestaurant().getStation());
						controller.setRestaurant(getRestaurant());
						controller.setView(new LoginWindow(ActorStruct.CLIENT, controller));
						controller.setObserver();
						getWindow().dispose();
						getWindow().getTimer().stop();
						setWindow(null);
						}
				}else if(getWindow().getCallWaiterFrame() != null){
					getRestaurant().getStation().setStatus(StatusStruct.LOGGED);
				}
				break;
			}
		}
		
	}
	/**
	 * Carga las promociones existentes en el restaurante
	 */
	public void load(){
		 getRestaurant().loadPromos();
	}
	/**
	 * Llama al método de la venta que inicializa la tabla
	 */
	public void initTables(){
		getWindow().updatePromoTable(getRestaurant());
	
	}	
	@Override
	public PromoWindow getWindow(){
		return (PromoWindow)super.getWindow();
	}
	@Override
	public void setView (TheWindow window){
		setWindow((PromoWindow) window);
	}
	@Override
	public ResClient getRestaurant(){
		return (ResClient)super.getRestaurant();
	}
	@Override
	public void valueChanged(ListSelectionEvent e) {

			String source = "";
			if (this.getWindow().getPromoTable().getModel().getRowCount()>0 &&  this.getWindow().getPromoTable().getSelectionModel() == e.getSource())
				source = "promo_table";
			
			switch(source){
			case "promo_table":
				if (getWindow().getPromoTable().getSelectedRow() > -1){
					int row = getWindow().getPromoTable().getSelectedRow();
					getWindow().getPromoTable().getSelectionModel().clearSelection();
					String p_st = (String)this.getWindow().getPromoTable().getValueAt(row,0);
					if ( !p_st.equals("//////////////////") && !p_st.equals("Pedido") && !p_st.equals("Descuento") && !p_st.equals("Total")){
						Promo p = getRestaurant().getPromo((String)this.getWindow().getPromoTable().getValueAt(row, 0));
						if (getRestaurant().getOrder().getPromos().size() > 0)
							getRestaurant().removePromo(getRestaurant().getOrder().getPromo(0).getName());
						getRestaurant().addPromo(p);
					} else if (getRestaurant().getOrder().getPromos().size() > 0)
						getRestaurant().removePromo(getRestaurant().getOrder().getPromo(0).getName());
				//	getWindow().getPromoTable().getSelectionModel().clearSelection();
				}
				break;
			
			}
	
		
		}
}
