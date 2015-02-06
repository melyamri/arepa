
package view;

import java.awt.*;
import java.awt.List;
import java.util.*;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;

import model.*;
import controller.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;
/**
 * Esta clase crea la ventana de promociones y contiene los métodos para actualizar la ventana
 * @author Grupo 9
 *
 */
public class PromoWindow extends TheWindow{
	

	/**
	   * Botón volver
	   */
  private JButton back_button;

  /**
   * Botón que finaliza el pedido
   */
  private JButton endorder_button;

  /**
   * Botón para llamar al camarero
   */
  private JButton callwaiter_button;

  /**
   * Panel de las promociones
   */
  private JPanel promo_panel;
  /**
   * Tabla de promociones
   */
  private JTable promo_table;
  /**
   * Temporizador que actualiza la ventana cada 5 segundos
   */
  private Timer timer;
  
  public Timer getTimer() {
	return timer;
}


  /**
   * Crea la ventana de promociones dado un actor y un controlador
   * @param actor usuario
   * @param controller controlador de la ventana
   */
public PromoWindow(ActorStruct actor,TheWindowController controller) {
	  
		super(ActorStruct.CLIENT, controller);
		super.setController(controller);
		
		getTitleLabel().setText("Promociones disponibles");
		createPromoPanel();		
		setController(controller);
		controller.setView(this);
		((PromoWindowController)controller).load(); //TODO
		((PromoWindowController)controller).initTables();
		timer = new Timer (5*1000, controller);
		timer.setActionCommand("timer");
		timer.start();
		this.repaint();
}
  
  
 @Override
public void addMenuButtons(ActorStruct actor){
	this.back_button = new RestaurantButton("Volver", actor);
	this.back_button.setActionCommand("back");
	getMenuPanel().add(this.back_button);
	
}
  
 /**
  * Crea el panel de promociones
  */
public void createPromoPanel(){
	  
	  promo_panel=new JPanel();
	  
	  promo_panel.setBorder(new LineBorder(new Color(240, 230, 140), 10));
	  promo_panel.setBounds(70 , 170, getWidth() - 140, 425);
	  promo_panel.setLayout(null);
		
	  promo_table = new JTable();
	  promo_table.setName("promo_table");
	  promo_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	  promo_table.setBounds(0, 0,(int) (promo_panel.getWidth()), (int) (promo_panel.getHeight()));
	  promo_table.setModel(new DefaultTableModel()
		{
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		((DefaultTableModel)promo_table.getModel()).setColumnCount(2);
		((DefaultTableModel)promo_table.getModel()).setRowCount(0);
		promo_table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		promo_table.getTableHeader().setReorderingAllowed(false);
		promo_table.getTableHeader().setResizingAllowed(false);
		((DefaultTableCellRenderer)promo_table.getDefaultRenderer(String.class)).setHorizontalAlignment(SwingConstants.CENTER);
      ((DefaultTableModel)promo_table.getModel()).setColumnCount(2);
	  ((DefaultTableModel)promo_table.getModel()).setRowCount(0);
	  ((DefaultTableModel)promo_table.getModel()).setColumnIdentifiers(new String[] {"Promoción", "Descuento"});
	  promo_table.getColumnModel().getColumn(0).setPreferredWidth((int)(promo_table.getWidth()/2));
	  promo_table.getColumnModel().getColumn(1).setPreferredWidth((int)(promo_table.getWidth()/2));
	  promo_table.setFillsViewportHeight(true);
	  promo_table.setBackground(new Color(255, 250, 205));	
	  
	  JScrollPane promo_jpane = new JScrollPane(promo_table);
	  promo_jpane.setBounds(10, 10,(int) (promo_table.getWidth()- 20), (int) (promo_table.getHeight()- 20));
	  promo_jpane.setHorizontalScrollBar(null);
	  promo_panel.add(promo_jpane);
	  getContentPane().add(promo_panel);
	  	  	  	  
	  this.callwaiter_button = new RestaurantButton("Llamar al camarero", ActorStruct.CLIENT);
	  this.callwaiter_button.setBounds(getWidth()/2 - 270, (int)(promo_panel.getLocation().getY() + promo_panel.getHeight() + 30), 250, callwaiter_button.getHeight());
	  this.callwaiter_button.setActionCommand("callwaiter");
	  getContentPane().add(this.callwaiter_button);
	  	  
	  this.endorder_button = new RestaurantButton("Finalizar pedido" , ActorStruct.CLIENT);
	  this.endorder_button.setBounds(getWidth()/2 + 20, (int)(promo_panel.getLocation().getY() + promo_panel.getHeight() + 30), 250, callwaiter_button.getHeight());
	  this.endorder_button.setActionCommand("endorder");
	  getContentPane().add(this.endorder_button);
	
	  
	
  }
  
  
  
  /**
   * Shows the window which indicates that the application is calling the waiter.
   */
  public boolean showCallWaiter() {
	  return true;
  }

@Override
public void setController(EventListener controller) {
	this.back_button.addActionListener((ActionListener) controller);
	this.endorder_button.addActionListener((ActionListener) controller);
	this.callwaiter_button.addActionListener((ActionListener) controller);
	this.promo_table.getSelectionModel().addListSelectionListener((ListSelectionListener) controller);
}



@Override
public void update(Observable arg0, Object arg1) {
	String s = (String) arg1;
	switch (s){
//	case "remove_promo":
	case "add_promo":
		updatePromoTable((ObservableRestaurant) arg0);
		break;

	}
	
}
/**
 * Actualiza la tabla de promociones dado un observador.
 * @param r - ObservableRestaurant
 */
public void updatePromoTable(ObservableRestaurant r){
	 if (getPromoTable().getSelectedRow() > -1);{
		 ((DefaultTableModel)promo_table.getModel()).setRowCount(0);
		  ArrayList<Promo>  promos = new ArrayList<Promo> ((Collection<Promo>) r.getPromos().values());
		  
		  for (int i = 0; i < promos.size(); i++){
			  if (((ObservableResClient)r).getOrder().hasPlate(promos.get(i).getPlate().getName())){
				  ((DefaultTableModel)getPromoTable().getModel()).setRowCount(getPromoTable().getModel().getRowCount() + 1);
				  if (((ObservableResClient)r).getOrder().getPromos().size()>0 && ((ObservableResClient)r).getOrder().getPromo(0).getName().equals(promos.get(i).getName()))
					  getPromoTable().setValueAt("(SELECCIONADO) " + promos.get(i).getName(), getPromoTable().getModel().getRowCount()-1, 0);
				  else getPromoTable().setValueAt(promos.get(i).getName(), getPromoTable().getModel().getRowCount()-1, 0);
				  getPromoTable().setValueAt(promos.get(i).getDiscount(), getPromoTable().getModel().getRowCount()-1, 1);
			  }
		  }
		  ((DefaultTableModel)getPromoTable().getModel()).setRowCount(getPromoTable().getModel().getRowCount() + 1);
		  getPromoTable().setValueAt("//////////////////", getPromoTable().getModel().getRowCount()-1, 0);
		  getPromoTable().setValueAt("//////////////////", getPromoTable().getModel().getRowCount()-1, 1);
		  ((DefaultTableModel)getPromoTable().getModel()).setRowCount(getPromoTable().getModel().getRowCount() + 1);
		  getPromoTable().setValueAt("//////////////////", getPromoTable().getModel().getRowCount()-1, 0);
		  getPromoTable().setValueAt("//////////////////", getPromoTable().getModel().getRowCount()-1, 1);
		  ((DefaultTableModel)getPromoTable().getModel()).setRowCount(getPromoTable().getModel().getRowCount() + 1);
		  getPromoTable().setValueAt("Pedido", getPromoTable().getModel().getRowCount()-1, 0);
		  getPromoTable().setValueAt(((ObservableResClient)r).getOrder().calculateGrossPrice(), getPromoTable().getModel().getRowCount()-1, 1);
		  ((DefaultTableModel)getPromoTable().getModel()).setRowCount(getPromoTable().getModel().getRowCount() + 1);
		  getPromoTable().setValueAt("Descuento", getPromoTable().getModel().getRowCount()-1, 0);
		  getPromoTable().setValueAt(((ObservableResClient)r).getOrder().calculateDiscount(), getPromoTable().getModel().getRowCount()-1, 1);
		  ((DefaultTableModel)getPromoTable().getModel()).setRowCount(getPromoTable().getModel().getRowCount() + 1);
		  getPromoTable().setValueAt("Total", getPromoTable().getModel().getRowCount()-1, 0);
		  getPromoTable().setValueAt(((ObservableResClient)r).getOrder().calculateNetPrice(), getPromoTable().getModel().getRowCount()-1, 1);
	 }
}

public JTable getPromoTable() {
	return promo_table;
}



public void setPromoTable(JTable promo_table) {
	this.promo_table = promo_table;
}
}
