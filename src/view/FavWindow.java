
package view;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.EventListener;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import model.Actor;
import model.ActorStruct;
import model.ObservableRestaurant;
import model.Order;
import model.Plate;

import controller.FavWindowController;
import controller.StartCookWindowController;
import controller.TheWindowController;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
/**
 * Ventana donde se muestran los platos favoritos de un usuario.
 */
public class FavWindow extends TheWindow {
	  /**
	   * Botón encargado de borrar los platos favoritos.
	   */
  private JButton delete_button;

  /**
   * Botón encargado de borrar los platos favoritos.
   */
  private JButton back_button;

  /**
   * Panel de la ventana.
   */
  private JPanel fav_panel;
  /**
   * Tabla que contiene los platos favoritos.
   */
  private JTable fav_table;  

  /**
   * Constructor de la ventana. Inicializa la ventana.
   * @param a usuario que usa la ventana.
   * @param controller Controlador de la ventana.
   */
  public FavWindow(Actor a, TheWindowController controller) {
	  super(ActorStruct.CLIENT,controller);
	  super.setController(controller);
	  
	  getTitleLabel().setText("Tus platos favoritos");
	  
	  createFavPanel();
	  setController(controller);
	  controller.setView(this);
	  ((FavWindowController)controller).init();
	
	  this.repaint();
	

  }
  /**
   * Añade el nombre y el precio de los platos en la tabla de platos favoritos.
   * @param a usuario que usa la ventana.
   */
  public void loadFavourites(Actor a){
	  for (int i = 0; i<a.getFavourites().size(); i++){
		  getFavTable().setValueAt(((Plate)a.getFavourites().values().toArray()[i]).getName(), i, 0);
		  getFavTable().setValueAt(((Plate)a.getFavourites().values().toArray()[i]).getPrice(), i, 1);
	  }
  }
  @Override
  public void addMenuButtons(ActorStruct actor){
	  this.back_button = new RestaurantButton("Volver", actor);
	  this.back_button.setActionCommand("back");
	  getMenuPanel().add(this.back_button);
	  
  }
  
  /**
   * Crea el panel de la ventana y le añade la tabla.
   */
  public void createFavPanel(){
	  
	  fav_panel=new JPanel();
	  
	 // fav_panel.setBackground(new Color(176, 224, 230));
	  fav_panel.setBorder(new LineBorder(new Color(240, 230, 140), 10));
	  fav_panel.setBounds(70 , 170, getWidth() - 140, 425);
	  fav_panel.setLayout(null);
		
	  fav_table = new JTable();
	  fav_table.setName("fav_table");
	  fav_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	  fav_table.setBounds(0, 0,(int) (fav_panel.getWidth()), (int) (fav_panel.getHeight()));
	  fav_table.setModel(new DefaultTableModel());
      ((DefaultTableModel)fav_table.getModel()).setColumnCount(2);
	  ((DefaultTableModel)fav_table.getModel()).setRowCount(0);
	  ((DefaultTableModel)fav_table.getModel()).setColumnIdentifiers(new String[] {"Plato", "Precio"});
	  fav_table.getColumnModel().getColumn(0).setPreferredWidth((int)(fav_table.getWidth()/2));
	  fav_table.getColumnModel().getColumn(1).setPreferredWidth((int)(fav_table.getWidth()/2));
	  fav_table.setFillsViewportHeight(true);
	  fav_table.setBackground(new Color(255, 250, 205));
	
	  JScrollPane fav_jpane = new JScrollPane(fav_table);
	  fav_jpane.setBounds(10, 10,(int) (fav_table.getWidth()- 20), (int) (fav_table.getHeight()- 20));
	  fav_jpane.setHorizontalScrollBar(null);
	  fav_panel.add(fav_jpane);
	  getContentPane().add(fav_panel);
	  
	  this.delete_button = new RestaurantButton("Borrar", ActorStruct.CLIENT);	  
	  this.delete_button.setLocation((int)(getWidth()/2 - delete_button.getWidth()/2), (int)( fav_panel.getLocation().getY() + fav_panel.getHeight() + 30));
	  getContentPane().add(this.delete_button);
	  this.delete_button.setActionCommand("delete");
	  

	  
	
  }


  @Override
public void setController(EventListener controller) {
	// TODO Auto-generated method stub
	this.back_button.addActionListener((ActionListener) controller);
	this.delete_button.addActionListener((ActionListener) controller);
}
	

@Override
public void update(Observable o, Object arg) {
	// TODO Auto-generated method stub
	
}

/**
 * Inicializa la tabla de favoritos y actualiza la tabla en caso de que se informe de un cambio.
 * 
 * @param r Restaurante
 */
public void init(ObservableRestaurant r){
	((DefaultTableModel)fav_table.getModel()).setRowCount(0);

		
		HashMap<String, Plate> hs = r.getStation().getActor().getFavourites();
		List<Plate> list = new ArrayList<Plate>((Collection<Plate>)hs.values());
		for(int i = 0; i < list.size(); i++){
			((DefaultTableModel)fav_table.getModel()).setRowCount(fav_table.getRowCount()+1);
			fav_table.setValueAt(list.get(i).getName(), fav_table.getRowCount()-1, 0);
			fav_table.setValueAt(list.get(i).getPrice(), fav_table.getRowCount()-1, 1);
		}
}

public JTable getFavTable() {
	return fav_table;
}

public void setFavTable(JTable fav_table) {
	this.fav_table = fav_table;
}
}
