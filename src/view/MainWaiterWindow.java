
package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import controller.*;

import model.*;

import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
/**
 * Ventana que sirve para controlar las mesas y los pedidos.
 * 
 * */
public class MainWaiterWindow extends TheWindow{
	  /**
	   * Array que contiene los botones del panel principal
	   * */
  private JButton[] action_buttons;
  /**
   * Panel de los pedidos.
   * */
  private JPanel order_panel;
  /**
   * Panel de las mesas.
   * */
  private JPanel tables_panel;
  /**
   * Botón del pedido.
   * */
  private JButton order_button;
  /**
   * Botones de Pagar y Activar.
   * */
  private JButton[] tables_buttons;
  /**
   * Botón para terminar el servicio.
   * */
  private JButton end_service_button;
  /**
   * Tabla de las mesas.
   * */
  private JTable tables_table;
  /**
   * Tabla de los pedidos.
   * */
  private JTable order_table;
  /**
   * Temporizador que actaliza la ventana cada 5 segundos
   */
  private Timer timer;

  public Timer getTimer() {
	return timer;
}
  /**
   * Constructor de HelpWindow. Inicializa sus valores.
   * @param controller controlador de la ventana
   * */
public MainWaiterWindow(TheWindowController controller) {
	  	super(ActorStruct.WAITER, controller);
		super.setController(controller);
		getTitleLabel().setText("Bienvenido, camarero");
				
		createPanels();

		createButtons();

		setController(controller);
		controller.setView(this);
		((MainWaiterWindowController)controller).load();
		((MainWaiterWindowController)controller).initTables();
		
		timer = new Timer (5*1000, controller);
		timer.setActionCommand("timer");
		timer.start();
		
		
		
		this.repaint();
  }
  
@Override
  public void addMenuButtons (ActorStruct actor){
		this.action_buttons = new RestaurantButton[4];
		
		this.action_buttons[0] = new RestaurantButton("Perfil", actor);
		this.action_buttons[0].setActionCommand("profile");
		getMenuPanel().add(this.action_buttons[0]);
		
		this.action_buttons[1] = new RestaurantButton("Desconectar", actor);
		this.action_buttons[1].setActionCommand("disconnect");
		getMenuPanel().add(this.action_buttons[1]);
		
	}
/**
 * Crea los paneles de las mesas y los pedidos.
 * */
	public void createPanels(){
		createOrderPanel();
		createTablesPanel();
	}
	 /**
	   * Crea el panel de pedidos.
	   * */
	public void createOrderPanel(){
		order_panel = new JPanel();
		//order_panel.setBackground(new Color(144, 238, 144));
		order_panel.setBorder(new LineBorder(new Color(102, 153, 102), 10));
		order_panel.setBounds(70, 170, 600, 350);
		order_panel.setLayout(null);
		
		order_table = new JTable();
		order_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		order_table.setBounds(0, 0,(int) (order_panel.getWidth()), (int) (order_panel.getHeight()));
		order_table.setModel(new DefaultTableModel()
		{
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});

		order_table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		order_table.getTableHeader().setReorderingAllowed(false);
		order_table.getTableHeader().setResizingAllowed(false);
		((DefaultTableCellRenderer)order_table.getDefaultRenderer(String.class)).setHorizontalAlignment(SwingConstants.CENTER);
		((DefaultTableModel)order_table.getModel()).setColumnCount(2);
		((DefaultTableModel)order_table.getModel()).setRowCount(0);
		((DefaultTableModel)order_table.getModel()).setColumnIdentifiers(new String[] {"Plato", "Mesa"});
		order_table.getColumnModel().getColumn(0).setPreferredWidth((int)(order_table.getWidth()/2));
		order_table.getColumnModel().getColumn(1).setPreferredWidth((int)(order_table.getWidth()/2));
		//order_table.getColumnModel().getColumn(2).setPreferredWidth((int)(order_table.getWidth()/4));
		order_table.setFillsViewportHeight(true);
		order_table.setBackground(new Color(152, 251, 152));
		
		JScrollPane order_jpane = new JScrollPane(order_table);
		order_jpane.setBounds(10, 10,(int) (order_panel.getWidth()- 20), (int) (order_panel.getHeight()- 20));
		order_jpane.setHorizontalScrollBar(null);
	//	order_jpane.getViewport().setBackground(new Color(176, 224, 230));
		order_panel.add(order_jpane);
		getContentPane().add(order_panel);
	}
	 /**
	   * Crea el panel de las mesas.
	   * 
	   */
	public void createTablesPanel(){
		tables_panel = new JPanel();
		tables_panel.setBackground(new Color(176, 224, 230));
		tables_panel.setBorder(new LineBorder(new Color(102, 153, 102), 10));
		tables_panel.setBounds((int)(getTitleLabel().getBounds().getMaxX()- 400) , 170, 400, 350);
		tables_panel.setLayout(null);
		
		tables_table = new JTable();
		tables_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tables_table.setBounds(0, 0,(int) (tables_panel.getWidth()), (int) (tables_panel.getHeight()));
		tables_table.setModel(new DefaultTableModel()
		{
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});

		tables_table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tables_table.getTableHeader().setReorderingAllowed(false);
		tables_table.getTableHeader().setResizingAllowed(false);
		((DefaultTableCellRenderer)tables_table.getDefaultRenderer(String.class)).setHorizontalAlignment(SwingConstants.CENTER);
		((DefaultTableModel)tables_table.getModel()).setColumnCount(2);
		((DefaultTableModel)tables_table.getModel()).setRowCount(0);
		((DefaultTableModel)tables_table.getModel()).setColumnIdentifiers(new String[] {"Mesa", "Estado"});
		tables_table.getColumnModel().getColumn(0).setPreferredWidth((int)(tables_table.getWidth()/2));
		tables_table.getColumnModel().getColumn(1).setPreferredWidth((int)(tables_table.getWidth()/2));
		//tables_table.getColumnModel().getColumn(2).setPreferredWidth((int)(tables_table.getWidth()/4));
		tables_table.setFillsViewportHeight(true);
		tables_table.setBackground(new Color(152, 251, 152));
		
		
		JScrollPane tables_jpane = new JScrollPane(tables_table);
		tables_jpane.setBounds(10, 10,(int) (tables_panel.getWidth()- 20), (int) (tables_panel.getHeight()- 20));
		tables_jpane.setHorizontalScrollBar(null);
		//tables_jpane.getViewport().setBackground(new Color(176, 224, 230));
		tables_panel.add(tables_jpane);
		getContentPane().add(tables_panel);
	}
	  /**
	   * Crea los botones y los añade al panel.
	   * */
	public void createButtons(){
		
		order_button = new RestaurantButton("Entregado", ActorStruct.WAITER);
		order_button.setActionCommand("served_button");
		order_button.setLocation((int)(order_panel.getLocation().getX() + order_panel.getWidth()/2 - order_button.getWidth()/2), (int)(order_panel.getLocation().getY() + order_panel.getHeight() + 15));
		getContentPane().add(order_button);
		
		tables_buttons = new RestaurantButton[2];
		
		tables_buttons[0] = new RestaurantButton("Pagar", ActorStruct.WAITER);
		tables_buttons[0].setActionCommand("pay_button");
		tables_buttons[0].setLocation((int)(tables_panel.getLocation().getX() + tables_panel.getWidth()/2 - tables_buttons[0].getWidth() - 15), (int)(tables_panel.getLocation().getY() + tables_panel.getHeight() + 15));
		getContentPane().add(tables_buttons[0]);
		
		tables_buttons[1] = new RestaurantButton("Activar", ActorStruct.WAITER);
		tables_buttons[1].setActionCommand("activate_button");
		tables_buttons[1].setLocation((int)(tables_buttons[0].getLocation().getX() + tables_buttons[0].getWidth() + 30), (int)(tables_panel.getLocation().getY() + tables_panel.getHeight() + 15));
		getContentPane().add(tables_buttons[1]);
		
	}

@Override
public void setController(EventListener controller) {
	action_buttons[0].addActionListener((ActionListener)controller);
	action_buttons[1].addActionListener((ActionListener)controller);
	order_button.addActionListener((ActionListener)controller);
	tables_buttons[0].addActionListener((ActionListener)controller);
	tables_buttons[1].addActionListener((ActionListener)controller);
	
	//end_service_button.addActionListener((ActionListener)controller);
}


public JButton[] getActionButtons() {
	return action_buttons;
}

public void setActionButtons(JButton[] action_buttons) {
	this.action_buttons = action_buttons;
}

public JPanel getOrderPanel() {
	return order_panel;
}

public void setOrderPanel(JPanel order_panel) {
	this.order_panel = order_panel;
}

public JPanel getIngredientPanel() {
	return tables_panel;
}

public void setIngredientPanel(JPanel ingredient_panel) {
	this.tables_panel = ingredient_panel;
}

public JButton getOrderButtons() {
	return order_button;
}

public void setOrderButtons(JButton order_button) {
	this.order_button = order_button;
}

public JButton[] getTablesButtons() {
	return tables_buttons;
}

public void setTablesButtons(JButton[] tables_buttons) {
	this.tables_buttons = tables_buttons;
}

public JButton getEndServiceButton() {
	return end_service_button;
}

public void setEndServiceButton(JButton end_service_button) {
	this.end_service_button = end_service_button;
}

public JTable getTablesTable() {
	return tables_table;
}

public void setTablesTable(JTable tables_table) {
	this.tables_table = tables_table;
}

public JTable getOrderTable() {
	return order_table;
}

public void setOrderTable(JTable order_table) {
	this.order_table = order_table;
}
/**
 * Actualiza la tabla de pedidos.
 * @param r el restaurante
 * */
public void updateOrderTable(ObservableRestaurant r){
	((DefaultTableModel)order_table.getModel()).setRowCount(0);
	ArrayList <Plate> plates = (ArrayList<Plate>) ((ObservableResWaiter)r).getFinishedPlates();
	ArrayList <String> tables = r.getDatabase().getFinishedPlatesTableID();
	if (plates.size()> 0){
		for(int i = 0; i < plates.size(); i++){
		 ((DefaultTableModel)order_table.getModel()).setRowCount(order_table.getModel().getRowCount() + 1);
		 order_table.setValueAt(plates.get(i).getName(), i, 0);
		 order_table.setValueAt(tables.get(i), i, 1);
		}
	}
}
/**
 * Actualiza la tabla de mesas.
 * @param r el restaurante
 * */
public void updateTablesTable(ObservableRestaurant r){
	((DefaultTableModel)tables_table.getModel()).setRowCount(0);
	HashMap <String, Station> s1 = r.getDatabase().getTables();
	ArrayList<Station> stations= new ArrayList<Station>((Collection) s1.values());
	if (stations.size()> 0){
		for(int i = 0; i < stations.size(); i++){
		 ((DefaultTableModel)tables_table.getModel()).setRowCount(tables_table.getModel().getRowCount() + 1);
		 tables_table.setValueAt(stations.get(i).getStationId(), i, 0);
		 tables_table.setValueAt(stations.get(i).getStatus(), i, 1);
		}
	}
}


@Override
public void update(Observable arg0, Object arg1) {
	String s = (String) arg1;
	switch (s){
	case "deliver_plate":
	case "load_finished_plates":
	case "load_tables":
	case "update":
		updateOrderTable((ObservableRestaurant) arg0);
		updateTablesTable((ObservableRestaurant) arg0);
		break;
	case "set_table_ai":
		updateTablesTable((ObservableRestaurant) arg0);
		break;
	}
	
}
}