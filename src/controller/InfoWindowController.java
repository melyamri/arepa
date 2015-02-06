
package controller;

import java.awt.event.ActionEvent;
import java.util.EventListener;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import view.*;
import model.*;
/**
 * Controlador de la ventana InfoWindow
 * @author Grupo 9
 *
 */
public class InfoWindowController extends TheWindowController{

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
		case "back": getWindow().dispose(); 
					setWindow(null); break;
					
		case "choose":	if(getWindow().getFavouriteButton().isVisible()){
							Plate p = getRestaurant().getPlate(getWindow().getPlateLabels()[0].getText());
							((ResClient)getRestaurant()).addPlateToOrder(p);
						}else{
							Menu m = getRestaurant().getMenu(getWindow().getPlateLabels()[0].getText());
							((ResClient)getRestaurant()).addMenuToOrder(m);
						}
						
						getWindow().dispose();
						setWindow(null);
						break;
		
		case "favourite": Actor actor = getRestaurant().getStation().getActor();
						Plate plate = getRestaurant().getPlate(getWindow().getPlateLabels()[0].getText());
							if(actor.addFavourite(plate)){
								showErrorPane("Se ha añadido el plato a tus favoritos", "Hecho");
							}else{
								showErrorPane("Error al guardar el plato en favoritos", "Error");
							}
							break;
		}
		
	}
	/**
	 * Llama al método de la ventana que actualiza la imagen del plato
	 */
	public void initImage(){
		getWindow().updateImage(getRestaurant());
	}
	@Override
	public void setView (TheWindow window){
		this.setWindow((InfoWindow) window);
	}
	@Override
	public InfoWindow getWindow(){
		return (InfoWindow) super.getWindow();
	}
	@Override
	public ResClient getRestaurant(){
		return (ResClient)super.getRestaurant();
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void checkAnonymous() {
		if (getRestaurant().getStation().getActor().getSurname().equalsIgnoreCase("anonymous")){
			getWindow().getFavouriteButton().setEnabled(false);
		}
		
	}
}
