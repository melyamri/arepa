
package controller;

import java.awt.event.ActionEvent;
import java.util.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;

import model.*;
import view.*;
/**
 * Controlador de la ventana OrderWindow
 * @author Grupo 9
 *
 */
public class OrderWindowController extends TheWindowController{

	@Override
	public void actionPerformed(ActionEvent e) {

		JTable cart_table = getWindow().getCartTable();
		String filtro = "";
		switch((String)getWindow().getFilterComboboxes()[0].getSelectedItem()){
		case "Platos": filtro = "plates"; break;
		case "Menús": filtro = "menus"; break;
		case "Favoritos": filtro = "favourites"; break;
		}
		super.actionPerformed(e);
		
		switch (e.getActionCommand()){
		case "profile": {
			ProfileWindowController controller = new ProfileWindowController();
			controller.setRestaurant(getRestaurant());
			controller.setView(new ProfileWindow(getRestaurant().getStation().getActor(),getRestaurant().getStation().getActor(), controller));
			controller.setObserver();
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
		case "history":{
			HistoryWindowController controller = new HistoryWindowController();
			controller.setRestaurant(getRestaurant());
			controller.setView(new HistoryWindow(ActorStruct.CLIENT, "client", controller)); 
			controller.setObserver();
			break;
		}
		case "favourites":{
			FavWindowController controller = new FavWindowController();
			controller.setRestaurant(getRestaurant());
			controller.setView(new FavWindow (getRestaurant().getStation().getActor(), controller));
			controller.setObserver();
			break;
		}
		case "continue":{
			if(getRestaurant().getOrder().getMenuCount() > 0 || getRestaurant().getOrder().getPlateCount() > 0 ){
				getWindow().getTimer().stop();
				PromoWindowController controller = new PromoWindowController();
				controller.setRestaurant(getRestaurant());
				controller.setView(new PromoWindow(ActorStruct.CLIENT, controller));
				controller.setObserver();
				getWindow().dispose();
			}else{
				showErrorPane("Debe seleccionar platos", "Error");
			}
			break;
		}
		case "delete":{
				if (getWindow().getCartTable().getSelectedRow() < (getRestaurant().getStation().getOrder().getPlateCount() +getRestaurant().getStation().getOrder().getMenuCount())){
					if (getWindow().getCartTable().getSelectedRow() < getRestaurant().getStation().getOrder().getMenuCount()){
						if ((getRestaurant().getStation().getOrder().getBlockMark() == 0) || ((getWindow().getCartTable().getSelectedRow() > (getRestaurant().getStation().getOrder().getBlockMark()/10)-1) && (getWindow().getCartTable().getSelectedRow() < getRestaurant().getStation().getOrder().getMenuCount()))){
							getRestaurant().removePlateFromOrder(getWindow().getCartTable().getSelectedRow());
						}

					} else{
						if ((getRestaurant().getStation().getOrder().getBlockMark() == 0) || ((getWindow().getCartTable().getSelectedRow() > (getRestaurant().getStation().getOrder().getBlockMark()%10)+(getRestaurant().getStation().getOrder().getMenuCount()-1)) && (getWindow().getCartTable().getSelectedRow() < (getRestaurant().getStation().getOrder().getMenuCount() + getRestaurant().getStation().getOrder().getPlateCount())))){
							getRestaurant().removePlateFromOrder(getWindow().getCartTable().getSelectedRow());
						}
					}
				}
			break;
			//}
		}
		case "select":{
			InfoWindowController controller = new InfoWindowController();
			controller.setRestaurant(getRestaurant());
			RestaurantButton b = (RestaurantButton)e.getSource();
				if (!getWindow().getFilterComboboxes()[0].getSelectedItem().equals("Menús")){
					Plate p = getRestaurant().getPlate(b.getText().substring(12, b.getText().length()-14));
					controller.setView(new InfoWindow(p, controller));
				} else {
					Menu m = getRestaurant().getMenu(b.getText().substring(12, b.getText().length()-14));
					controller.setView(new InfoWindow(m, controller));
				}
			controller.setObserver();
			break;
		}
		case "filter":{
			String type, ingredient;
			boolean[] diseases = new boolean[8];
			getWindow().setPage(0);
			type = (String) getWindow().getFilterComboboxes()[0].getSelectedItem();
			if (type.equals("Menús") && getWindow().getFilterComboboxes()[1].isEnabled()){
				getWindow().getFilterComboboxes()[1].setSelectedIndex(0);
				getWindow().getFilterComboboxes()[1].setEnabled(false);
			} else if (!type.equals("Menús") && !getWindow().getFilterComboboxes()[1].isEnabled() )
				getWindow().getFilterComboboxes()[1].setEnabled(true);
			ingredient = (String) getWindow().getFilterComboboxes()[1].getSelectedItem();
			
			for(int i= 0; i < 8; i++){
				if(getWindow().getDiseaseCheckboxes()[i].isSelected()){
					diseases[i] = true;
				}else{
					diseases[i] = false;
				}
			}
			if (!type.equals("Menús")){
				if (!ingredient.equals("Ingrediente"))
					getRestaurant().filterPlates(ingredient, diseases);
				else {
					getRestaurant().filterPlates("NOTHING", diseases);
				}
			} 	else getRestaurant().filterMenus(diseases);
			getWindow().initButtons(getRestaurant());
			
			break;
		}
		case "last":{
			
			getWindow().setPage(getWindow().getPage()-12);
			initButtons(filtro);
			
			break;
		}
		case "next":{
			getWindow().setPage(getWindow().getPage()+12);
			initButtons(filtro);
			break;
		}
		case "call":{
			getRestaurant().getStation().setStatus(StatusStruct.CALLING);
			getRestaurant().getDatabase().updateStation(getRestaurant().getStation());
			getWindow().createCallWaiterPanel(false);
			break;
		}
		
		case "timer":{
			System.out.println("Client Order Timer Activated");
			Station s = getRestaurant().getDatabase().getStation(getRestaurant().getStation().getStationId());
			if (s == null){
				getWindow().getTimer().stop();
				System.exit(0);
			} else if (!s.getStatus().equals(StatusStruct.CALLING)){
					if(getWindow().getActionButtons()[2].getText().equals("Registrarse") && !getRestaurant().getStation().getActor().getSurname().equalsIgnoreCase("anonymous")){
						this.load();
						getWindow().getTitleLabel().setText("Bienvenido " + getRestaurant().getStation().getActor().getUser());
					}
					if (getWindow().getCallWaiterFrame() != null)
						getWindow().destroyCallWaiterPanel();
					if (s.getStatus().equals(StatusStruct.INACTIVE) || !getRestaurant().getDatabase().isServiceStarted()){
						showErrorPane("Has sido desconectado", "Error");
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
	 * Carga los platos existentes en el restaurante así como los menús. También inicializa el actor en la mesa
	 * Finalmente carga los ingredientes que hhay en el restaurante
	 */
	public void load(){
		getRestaurant().getStation().setActor(getRestaurant().getDatabase().getActor(getRestaurant().getStation().getActor().getUser()));
		getRestaurant().loadPlates();
		getRestaurant().loadMenus();
		boolean isAnonymous = false;
		 if (getRestaurant().getStation().getActor().getSurname().equals("anonymous")) isAnonymous = true;
		 	if (isAnonymous){
				getWindow().getActionButtons()[0].setEnabled(false);
				getWindow().getActionButtons()[1].setEnabled(false);
				getWindow().getFilterComboboxes()[0].removeItemAt(2);
				getWindow().getActionButtons()[2].setText("Registrarse");
			}else{
				getWindow().getActionButtons()[0].setEnabled(true);
				getWindow().getActionButtons()[1].setEnabled(true);
				getWindow().getFilterComboboxes()[0].addItem("Favoritos");
				getWindow().getActionButtons()[2].setText("Perfil");
			}
		 	
		 	//Load ingredients
		 	HashMap<String, Ingredient> i1 = getRestaurant().getDatabase().getIngredients();
			List<Ingredient> ingredients = new  ArrayList<Ingredient>((Collection) i1.values());
			for(int i = 0; i < ingredients.size(); i++){
				getWindow().getFilterComboboxes()[1].addItem(ingredients.get(i).getName());
			}
	}
	/**
	 * Llama al método de la ventana que inicializa la tabla
	 */
	public void initTables(){
			
		getWindow().updateCartTable(getRestaurant());

	}
	/**
	 * Llama al método de la ventana que inicializa los botones 
	 * @param filtro tipo de filtrado (Menus, Platos o Favoritos)
	 */
	public void initButtons(String filtro){
			HashMap<String, Plate> p1 = getRestaurant().getPlates();
			ArrayList<Plate> plates = new ArrayList<Plate>((Collection<Plate>)p1.values());
			if(getWindow().getPage() == 0){
				getWindow().getLast().setEnabled(false);
			}else{
				getWindow().getLast().setEnabled(true);
			}
			
			if((plates.size() - getWindow().getPage()) < 12){
				getWindow().getNext().setEnabled(false);
			}else{
				getWindow().getNext().setEnabled(true);
			}
			
			
			for(int i = getWindow().getPage(); i < getWindow().getPage()+12; i ++){
				if(i < plates.size()){
						getWindow().getPlateButtons().get(i-getWindow().getPage()).setEnabled(true);
						getWindow().getPlateButtons().get(i-getWindow().getPage()).setText("<html><body>" + plates.get(i).getName() + "</body></html>");
						if(plates.get(i).isBlocked()){
						getWindow().getPlateButtons().get(i-getWindow().getPage()).setEnabled(false);
						}
				}
			}
			if(plates.size() - getWindow().getPage() < getWindow().getPlateButtons().size()){
				for (int i = plates.size() - getWindow().getPage(); i < getWindow().getPlateButtons().size(); i++){
					getWindow().getPlateButtons().get(i).setVisible(false);
				}
				
				getWindow().getNext().setEnabled(false);
			}else{
				for (int i = 0; i < getWindow().getPlateButtons().size(); i++){
					getWindow().getPlateButtons().get(i).setVisible(true);
				}
				getWindow().getNext().setEnabled(true);
			}
	}
	@Override
	public OrderWindow getWindow(){
		return (OrderWindow) super.getWindow();
	}
	@Override
	public ResClient getRestaurant(){
		return (ResClient) super.getRestaurant();
	}
	@Override
	public void setView (TheWindow window){
		setWindow((OrderWindow) window);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		String source = "";
		if (this.getWindow().getCartTable().getSelectedRow()>(-1) &&  this.getWindow().getCartTable().getSelectionModel() == e.getSource())
			source = "cart_table";
		
		switch(source){
		case "cart_table":
		if ((getWindow().getCartTable().getSelectedRow() < getRestaurant().getStation().getOrder().getMenuCount())){
			if (!(getRestaurant().getStation().getOrder().getBlockMark() == 0) && !((getWindow().getCartTable().getSelectedRow() > ((getRestaurant().getStation().getOrder().getBlockMark()/10)-1)))){
				getWindow().getCartTable().clearSelection();
			}
		} else if (!(getRestaurant().getStation().getOrder().getBlockMark() == 0) && !((getWindow().getCartTable().getSelectedRow() > (getRestaurant().getStation().getOrder().getBlockMark()%10)+(getRestaurant().getStation().getOrder().getMenuCount()-1)) && (getWindow().getCartTable().getSelectedRow() < (getRestaurant().getStation().getOrder().getMenuCount() + getRestaurant().getStation().getOrder().getPlateCount())))){
			getWindow().getCartTable().clearSelection();
		}

		}
	}
}
