
package controller;
/**
 * Controlador de la ventana MenuCookWindow
 * @author Grupo 9
 *
 */
import java.awt.event.ActionEvent;
import java.util.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

import view.*;
import model.*;

public class MenuCookWindowController extends TheWindowController{

	@Override
	public void actionPerformed(ActionEvent e) {
		ResCook restaurant = (ResCook)getRestaurant();
		
		super.actionPerformed(e);

		switch ( e.getActionCommand() ){
			
		
			case "add_menu":{
				getWindow().getMenuJTable().getSelectionModel().clearSelection();
				getWindow().getPromoJTable().getSelectionModel().clearSelection();
				getWindow().getNewMenuTextFields()[0].setText("");
				getWindow().getNewMenuTextFields()[0].setEditable(true);
				getWindow().getNewMenuTextFields()[1].setText("");
				JComboBox<String>[] boxes = getWindow().getNewMenuComboBoxes();
				for (int i = 0; i< boxes.length; i++){
					boxes[i].setSelectedIndex(0);
				}
				getWindow().getNewPromoJPanel().setVisible(false);
				getWindow().getNewMenuJPanel().setVisible(true);
				break;
			}
			case "delete_menu":{
				if(restaurant.removeMenu((String)getWindow().getMenuJTable().getValueAt(getWindow().getMenuJTable().getSelectedRow(),0))){
					showErrorPane("El menú se borró correctamente", "Hecho");
				}else{
					showErrorPane("El menú no pudo ser borrado", "Error");
				}
				
				break;
			}
			case "add_promo":{
				getWindow().getMenuJTable().getSelectionModel().clearSelection();
				getWindow().getPromoJTable().getSelectionModel().clearSelection();
				getWindow().getNewPromoTextFields()[0].setText("");
				getWindow().getNewPromoTextFields()[0].setEditable(true);
				getWindow().getNewPromoTextFields()[1].setText("");
				JComboBox<String> box = getWindow().getNewPromoComboBox();
				
				box.setSelectedIndex(0);
				
				getWindow().getNewPromoJPanel().setVisible(true);
				getWindow().getNewMenuJPanel().setVisible(false);
				break;
			}
			case "delete_promo":{
										
				if(restaurant.removePromo((String)  getWindow().getPromoJTable().getValueAt( getWindow().getPromoJTable().getSelectedRow(),0))){
					showErrorPane("La promoción se borró correctamente", "Hecho");
				}else{
					showErrorPane("La promoción no pudo ser borrada", "Error");
				}
				
				break;
			}
			case "back_button":{
				this.getWindow().dispose();
				//controller.setView(new HistoryWindow(null, controller));
				break;	
			}
			case "new_promo":{
				String nombre, descuento;
				Plate plato;
				
				nombre =  this.getWindow().getNewPromoTextFields()[0].getText();
				descuento =  this.getWindow().getNewPromoTextFields()[1].getText();
				plato =  getRestaurant().getPlate((String)this.getWindow().getNewPromoComboBox().getSelectedItem());
				
				try{
					if (Integer.parseInt(descuento)<=0){
						showErrorPane("El campo descuento debe ser mayor que 0", "Error");
						break;
					}
				}
				catch (Exception ex){
					showErrorPane("El campo descuento es incorrecto", "Error");
					break;
				}
				if(nombre.equals("")|| descuento.equals("")){
					showErrorPane("Debe rellenar todos los campos", "Error");
				}else{
					Promo promo = new Promo(nombre, Integer.parseInt(descuento),plato);
					
					if(restaurant.addPromo(promo)){
						showErrorPane("Se ha añadido/modificado la promoción correctamente", "Hecho");
					}else{
						showErrorPane("Error al añadir la promoción", "Error");
					}
				}
				break;
			}
			case "new_menu":{
				String nombre, price;
				
				nombre = getWindow().getNewMenuTextFields()[0].getText();
				price =  getWindow().getNewMenuTextFields()[1].getText();
				try{
					if (Float.parseFloat(price)<0){
						showErrorPane("El campo precio es incorrecto", "Error");
						break;
					}
				}
				catch (Exception ex){
					showErrorPane("El campo precio es incorrecto", "Error");
					break;
				}
				
				if(nombre.equals("") || price.equals("")){
					showErrorPane("Debe rellenar todos los campos", "Error");
				}else{
					JComboBox<String>[] boxes = getWindow().getNewMenuComboBoxes();
					Float precio = Float.parseFloat(price);
					Menu menu = new Menu(nombre,precio);
					for (int i = 0; i<boxes.length; i++){
						if (boxes[i].getSelectedIndex() > 0) menu.addPlate(getRestaurant().getPlate(boxes[i].getItemAt(boxes[i].getSelectedIndex())));
					}
					
					if(restaurant.addMenu(menu)){
						showErrorPane("Se ha añadido/modificado el menú correctamente", "Hecho");
					}else{
						showErrorPane("Error al añadir el menú", "Error");;
					}
				}
			}
		break;
		}
		
	}
	/**
	 * Carga los menús y las promociones del restaurante
	 */
	public void load(){
		getRestaurant().loadMenus();
		getRestaurant().loadPromos();
	}
	/**
	 * Llama al método de la ventana que inicializa las tablas
	 */
	public void initTables(){
		
		getWindow().updateMenuTable(getRestaurant());
		getWindow().updatePromoTable(getRestaurant());
		
			
		JComboBox<String> [] menu_box = getWindow().getNewMenuComboBoxes();
		JComboBox<String> promo_box = getWindow().getNewPromoComboBox();
		HashMap<String, Plate> plist = getRestaurant().getPlates();
		List<Plate> plates = new ArrayList<Plate>((Collection<Plate>) plist.values());
		for (int i = 0; i<menu_box.length; i++){
			menu_box[i].removeAllItems();
			menu_box[i].insertItemAt("", 0);
			menu_box[i].setSelectedIndex(0);
			if (i == 0) {
				promo_box.removeAllItems();
				promo_box.insertItemAt("", 0);
				promo_box.setSelectedIndex(0);
			}
			
			for (int j = 0; j<plates.size(); j++){
				menu_box[i].insertItemAt(plates.get(j).getName(), j+1);
				if (i==1)promo_box.insertItemAt(plates.get(j).getName(), j+1);
			}
		}
		
		
	}
	
	@Override
	public MenuCookWindow getWindow(){
		return (MenuCookWindow) super.getWindow();
	}
	@Override
	public void setView (TheWindow window){
		setWindow((MenuCookWindow) window);
	}
	@Override
	public ResCook getRestaurant(){
		return (ResCook) super.getRestaurant();
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		String source = "";
		if (this.getWindow().getMenuJTable().getSelectionModel() == e.getSource())
			source = "menu_table";
		else if (this.getWindow().getPromoJTable().getSelectionModel() == e.getSource())
			source = "promo_table";
		
		switch(source){
		case "menu_table":
			if (getWindow().getMenuJTable().getSelectedRow() > -1){
				getWindow().getNewMenuJPanel().setVisible(true);
				getWindow().getNewPromoJPanel().setVisible(false);
				Menu m = restaurant.getMenu((String)this.getWindow().getMenuJTable().getValueAt(this.getWindow().getMenuJTable().getSelectedRow(), 0));
					
				getWindow().getNewMenuTextFields()[0].setText(m.getName());
				getWindow().getNewMenuTextFields()[0].setEditable(false);
				getWindow().getNewMenuTextFields()[1].setText(((Float) m.getPrice()).toString());
		
				ArrayList <Plate> plat = new ArrayList<Plate> ((Collection<Plate>)m.getPlates().values());
				
				ArrayList <Plate> all_plat = new ArrayList<Plate> ((Collection<Plate>)getRestaurant().getPlates().values());
				for (int i = 0; i< plat.size(); i++){
					getWindow().getNewMenuComboBoxes()[i].setSelectedIndex(all_plat.indexOf(getRestaurant().getPlate(plat.get(i).getName()))+1);
				}
				for (int i = plat.size(); i<8; i++){
					getWindow().getNewMenuComboBoxes()[i].setSelectedIndex(0);
				}
				getWindow().getPromoJTable().getSelectionModel().clearSelection();
				
			}
			
			break;
		case "promo_table":
			if (getWindow().getPromoJTable().getSelectedRow() > -1){
				getWindow().getNewMenuJPanel().setVisible(false);
				getWindow().getNewPromoJPanel().setVisible(true);
				Promo promo = restaurant.getPromo((String)this.getWindow().getPromoJTable().getValueAt(this.getWindow().getPromoJTable().getSelectedRow(), 0));			
				
				getWindow().getNewPromoTextFields()[0].setText(promo.getName());
				getWindow().getNewPromoTextFields()[0].setEditable(false);
				getWindow().getNewPromoTextFields()[1].setText(((Integer)promo.getDiscount()).toString());
				
				getWindow().getNewPromoComboBox().setSelectedItem(promo.getPlate().getName());
				
				getWindow().getMenuJTable().getSelectionModel().clearSelection();
			}
			break;
		}
	}
}
