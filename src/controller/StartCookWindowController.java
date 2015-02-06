
package controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.DateFormatSymbols;
import java.util.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

import model.*;
import view.*;
/**
 * Controlador de la ventana StartCookWindow
 * @author Grupo 9
 *
 */
public class StartCookWindowController extends TheWindowController{
	@Override
	public void actionPerformed(ActionEvent e) {
		
		JTable plate_table = getWindow().getPlateTable();
		JTable ingredient_table = getWindow().getIngredientTable();
		
		super.actionPerformed(e);
		
		switch (e.getActionCommand()){
		case "start": {
			getRestaurant().startService();
			ServiceCookWindowController controller = new ServiceCookWindowController();
			controller.setRestaurant(getRestaurant());
			controller.setView(new ServiceCookWindow(controller));
			controller.setObserver();
			getWindow().dispose();
			break;
		}
		
		case "menu_promo":{
			MenuCookWindowController controller = new MenuCookWindowController();
			controller.setRestaurant(getRestaurant());
			controller.setView(new MenuCookWindow(controller));
			controller.setObserver();

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
		
		case "new_plate":{

			String name, location, info, price, image;
			
			
			name = getWindow().getNewPlateFields()[0].getText();
			location = getWindow().getNewPlateFields()[1].getText();
			info = getWindow().getAdditional_info().getText();
			price = getWindow().getNewPlateFields()[2].getText();
			image = getWindow().getImageButton().getName();
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
			
			if(name.equals("") || location.equals("") || info.equals("") || price.equals("") || image.equals("Imagen")){
				showErrorPane("Rellene todos los campos", "Error");
			}else{
				
				Plate plate = new Plate(name, location, info, Float.parseFloat(price), image);
				for (int i = 0; i < getWindow().getIngredientBoxes().length ; i++){
					if (!getWindow().getIngredientBoxes()[i].getItemAt(getWindow().getIngredientBoxes()[i].getSelectedIndex()).equals(""))
						plate.addIngredient(getRestaurant().getIngredient(getWindow().getIngredientBoxes()[i].getItemAt(getWindow().getIngredientBoxes()[i].getSelectedIndex())));		
					
				}
				plate.checkAllDiseases();
				if(getRestaurant().addPlate(plate)){
					showErrorPane("Se ha añadido/modificado el plato correctamente", "Hecho");
				}else{
					showErrorPane("Error al añadir el plato", "Error");
				}
			}
			
			
			break;			
		}
		case "add_ingredient":{
			getWindow().getPlateTable().getSelectionModel().clearSelection();
			getWindow().getIngredientTable().getSelectionModel().clearSelection();
			getWindow().getNewIngredientFields()[0].setText("");
			getWindow().getNewIngredientFields()[0].setEditable(true);
			getWindow().getNewIngredientFields()[1].setText("");
			JCheckBox [] dis = getWindow().getDiseases();
			for (int i = 0; i<dis.length; i++){
				dis[i].setSelected(false);
			}
			getWindow().getNewPlatePanel().setVisible(false);
			getWindow().getNewIngredientPanel().setVisible(true);
			break;			
		}
		
		case "new_ingredient":{
			String name, amount;
			
			name = getWindow().getNewIngredientFields()[0].getText();
			amount = getWindow().getNewIngredientFields()[1].getText();
			
			try{
				if (Integer.parseInt(amount)<=0){
					showErrorPane("El campo cantidad debe ser mayor que 0", "Error");
					break;
				}
			}
			catch (Exception ex){
				showErrorPane("El campo cantidad es incorrecto", "Error");
				break;
			}
			
			if(name.equals("") || amount.equals("")){
				showErrorPane("Rellene todos los campos", "Error");
			}else{
				Ingredient ingredient = new Ingredient(name);
				ingredient.addAmount(Integer.parseInt(amount));
				
				//Adds the diseases checked to the ingredient
				for(int i = 0; i < getWindow().getDiseases().length ; i++){
					if (getWindow().getDiseases()[i].isSelected()){
						ingredient.addDisease(i);
					}else{
						ingredient.removeDisease(i);
					}
				}
				if(getRestaurant().addIngredient(ingredient)){
					showErrorPane("Se ha añadido/modificado el ingrediente correctamente", "Hecho");
				}else{
					showErrorPane("Error al añadir el ingrediente", "Error");
				}
			}
			
			break;		
		}
		case "add_plate":{
			getWindow().getImageButton().setName("");
			getWindow().getImageButton().setText("Imagen");
			getWindow().getImageButton().setBackground(UIManager.getColor( "Button.background" ));
			getWindow().getPlateTable().getSelectionModel().clearSelection();
			getWindow().getIngredientTable().getSelectionModel().clearSelection();
			getWindow().getNewPlateFields()[0].setText("");
			getWindow().getNewPlateFields()[0].setEditable(true);
			getWindow().getNewPlateFields()[1].setText("");
			getWindow().getAdditional_info().setText("");
			getWindow().getNewPlateFields()[2].setText("");
			JComboBox<String>[] boxes = getWindow().getIngredientBoxes();
			for (int i = 0; i< boxes.length; i++){
				boxes[i].setSelectedIndex(0);
			}
			getWindow().getNewIngredientPanel().setVisible(false);
			getWindow().getNewPlatePanel().setVisible(true);

			break;			
		}
		
		case "delete_plate":{
			if(plate_table.getSelectedRow() == -1){			
				showErrorPane("Debe seleccionar un plato", "Error");
			}else{
				String p = (String)plate_table.getValueAt(plate_table.getSelectedRow(), 0);
				if(getRestaurant().removePlate(p)){
					showErrorPane("Se ha eliminado el plato correctamente", "Hecho");
				}else{
					showErrorPane("Error al eliminar el plato", "Error");
				}
			}
			break;			
		}
		case "delete_ingredient":{

			if(ingredient_table.getSelectedRow() == -1){
				showErrorPane("Debe seleccionar un ingrediente", "Error");
			}else{
				
				String ing = (String)ingredient_table.getValueAt(ingredient_table.getSelectedRow(), 0);
				if(getRestaurant().removeIngredient(ing)){
					showErrorPane("Se ha borrado el ingrediente correctamente", "Hecho");
				}else{
					showErrorPane("Error al eliminar el ingrediente", "Error");
				}
			}
			break;
			
		}
		case "image":{
			JFileChooser image_chooser = new JFileChooser();
			JDialog dialog = new JDialog();
			int opcion = image_chooser.showOpenDialog(getWindow());
			
			if (opcion == JFileChooser.APPROVE_OPTION) {
                String pathArchivo = image_chooser.getSelectedFile().getPath();
               ((JButton) e.getSource()).setName(pathArchivo);
               ((JButton) e.getSource()).setText(image_chooser.getSelectedFile().getName());
               ((JButton) e.getSource()).setBackground(Color.GRAY);
			
			}
			break;
		}
		}
		
		
	}
	
	/**
	 * Carga los platos e ingredientes existentes en el restaurante
	 */
	public void load(){
		getRestaurant().loadPlates();
		getRestaurant().loadIngredients();
	}
	/**
	 * Llama al método de la ventana que inicializa las tablas
	 */
	public void initTables(){
		getWindow().updatePlateTable(getRestaurant());
		getWindow().updateIngredientTable(getRestaurant());
	
	}
	@Override	
	public void setView (TheWindow window){
		setWindow((StartCookWindow) window);
	}
	@Override
	public StartCookWindow getWindow(){
		return (StartCookWindow) super.getWindow();
	}
	@Override
	public ResCook getRestaurant(){
		return (ResCook) super.getRestaurant();
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		String source = "";
		if (this.getWindow().getIngredientTable().getSelectionModel() == e.getSource())
			source = "ingredient_table";
		else if (this.getWindow().getPlateTable().getSelectionModel() == e.getSource())
			source = "plate_table";
		
		switch(source){
		case "plate_table":
			if (getWindow().getPlateTable().getSelectedRow() > -1){
				load();
				getWindow().getImageButton().setBackground(Color.GRAY);
				getWindow().getNewIngredientPanel().setVisible(false);
				getWindow().getNewPlatePanel().setVisible(true);
				Plate p = getRestaurant().getPlate((String)this.getWindow().getPlateTable().getValueAt(this.getWindow().getPlateTable().getSelectedRow(), 0));
				
				getWindow().getNewPlateFields()[0].setText(p.getName());
				getWindow().getNewPlateFields()[0].setEditable(false);
				
				getWindow().getNewPlateFields()[1].setText(p.getLocation());
				getWindow().getNewPlateFields()[2].setText(Float.toString(p.getPrice()));
				getWindow().getAdditional_info().setText(p.getInfo());
				ArrayList <Ingredient> ing = new ArrayList<Ingredient> ((Collection<Ingredient>)p.getIngredients().values());
				ArrayList <Ingredient> all_ing = new ArrayList<Ingredient> ((Collection<Ingredient>)getRestaurant().getIngredients().values());
				for (int i = 0; i< ing.size(); i++){
					getWindow().getIngredientBoxes()[i].setSelectedIndex(all_ing.indexOf(getRestaurant().getIngredient(ing.get(i).getName()))+1);
				}
				for (int i = ing.size(); i<8; i++){
					getWindow().getIngredientBoxes()[i].setSelectedIndex(0);
				}
				getWindow().getImageButton().setText(p.getImageName());
				getWindow().getIngredientTable().getSelectionModel().clearSelection();
			}
			break;
		case "ingredient_table":
			if (getWindow().getIngredientTable().getSelectedRow() > -1){
				getWindow().getNewPlatePanel().setVisible(false);
				getWindow().getNewIngredientPanel().setVisible(true);
				Ingredient ing = getRestaurant().getIngredient((String) this.getWindow().getIngredientTable().getValueAt(this.getWindow().getIngredientTable().getSelectedRow(), 0));
				getWindow().getNewIngredientFields()[0].setText(ing.getName());
				getWindow().getNewIngredientFields()[0].setEditable(false);
				getWindow().getNewIngredientFields()[1].setText(Integer.toString(ing.getAmount()));
				JCheckBox [] dis = getWindow().getDiseases();
				for (int i = 0; i<dis.length; i++){
					dis[i].setSelected(ing.hasDisease(i));
				}
				getWindow().getPlateTable().getSelectionModel().clearSelection();
			}

			break;
		}
		
		
	}
}
