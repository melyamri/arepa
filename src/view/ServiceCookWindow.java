
package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.EventListener;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.TimeUnit;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;

import controller.*;

import model.ActorStruct;
import model.Ingredient;
import model.ObservableResCook;
import model.ObservableRestaurant;
import model.Plate;

import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Ventana del servicio de la cocina que contiene los métodos para actualizarse.
 * @author Grupo 9
 *
 */
public class ServiceCookWindow extends TheWindow{
  /**
   * Botones del panel principal
   */
	private JButton[] action_buttons;
/**
 * Panel de pedidos
 */
  private JPanel order_panel;
/**
 * Panel de ingredientes
 */
  private JPanel ingredient_panel;
/**
 * Botones de pedido
 */
  private JButton[] order_buttons;
/**
 * Botón de ingrediente
 */
  private JButton ingredient_button;
  /**
   * Botón terminar servicio
   */
  private JButton end_service_button;
  /**
   * Tabla de ingredientes
   */
  private JTable ingredient_table;
  /**
   * Tabla de pedidos
   */
  private JTable order_table;
  /**
   * Temporizador que actualiza la ventana cada 5 segundos
   */
  private Timer timer;

  public Timer getTimer() {
	return timer;
}
  /**
   * Constructor de la ventana. Inicializa todos sus valores.
   * @param controller - controlador de la ventana
   */
public ServiceCookWindow(TheWindowController controller) {
	  	super(ActorStruct.COOK, controller);
		super.setController(controller);
		getTitleLabel().setText("Bienvenido, cocinero");

		
		createPanels();
		
		//Botón comenzar servicio
		end_service_button = new RestaurantButton("Terminar Servicio", ActorStruct.COOK);
		end_service_button.setActionCommand("end_service_button");
		end_service_button.setBounds((int)(getWindowWidth()/2 - 160),(int)(getWindowHeight()-100), 320, 60);
		getContentPane().add(end_service_button);
				
		createButtons();
		//createPanelHeaders();
		
		setController(controller);
		controller.setView(this);
		((ServiceCookWindowController)controller).load();
		((ServiceCookWindowController)controller).initTables(); 
		timer = new Timer (5*1000, controller);
		timer.setActionCommand("timer");
		timer.start();
		
		this.repaint();
  }
  @Override
  public void addMenuButtons (ActorStruct actor){
		this.action_buttons = new RestaurantButton[4];
		
		this.action_buttons[0] = new RestaurantButton("Usuarios", actor);
		this.action_buttons[0].setActionCommand("users");
		getMenuPanel().add(this.action_buttons[0]);
		
		this.action_buttons[1] = new RestaurantButton("Historial", actor);
		this.action_buttons[1].setActionCommand("history");
		getMenuPanel().add(this.action_buttons[1]);
		
	}
	/**
	 * Crea los paneles de la ventana
	 */
	public void createPanels(){
		createOrderPanel();
		createIngredientPanel();
	}
	/**
	 * Crea el panel de pedidos.
	 */
	public void createOrderPanel(){
		order_panel = new JPanel();
		order_panel.setBackground(new Color(176, 224, 230));
		order_panel.setBorder(new LineBorder(new Color(153, 180, 209), 10));
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
		((DefaultTableModel)order_table.getModel()).setColumnCount(3);
		((DefaultTableModel)order_table.getModel()).setRowCount(0);
		((DefaultTableModel)order_table.getModel()).setColumnIdentifiers(new String[] {"Plato", "Mesa", "Tiempo en cola"});
		order_table.getColumnModel().getColumn(0).setPreferredWidth((int)(order_table.getWidth()/2));
		order_table.getColumnModel().getColumn(1).setPreferredWidth((int)(order_table.getWidth()/5 + 8));
		order_table.getColumnModel().getColumn(2).setPreferredWidth((int)(order_table.getWidth()/4));
		order_table.setFillsViewportHeight(true);
		order_table.setBackground(new Color(176, 224, 230));
		
		JScrollPane order_jpane = new JScrollPane(order_table);
		order_jpane.setBounds(10, 10,(int) (order_panel.getWidth()- 20), (int) (order_panel.getHeight()- 20));
		order_jpane.setHorizontalScrollBar(null);
		order_jpane.getViewport().setBackground(new Color(176, 224, 230));
		order_panel.add(order_jpane);
		getContentPane().add(order_panel);
	}
	/**
	 * Crea el panel de ingredientes.
	 */
	public void createIngredientPanel(){
		ingredient_panel = new JPanel();
		ingredient_panel.setBackground(new Color(176, 224, 230));
		ingredient_panel.setBorder(new LineBorder(SystemColor.activeCaption, 10));
		ingredient_panel.setBounds((int)(getTitleLabel().getBounds().getMaxX()- 400) , 170, 400, 350);
		ingredient_panel.setLayout(null);
		
		ingredient_table = new JTable();
		ingredient_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		ingredient_table.setBounds(0, 0,(int) (ingredient_panel.getWidth()), (int) (ingredient_panel.getHeight()));
		ingredient_table.setModel(new DefaultTableModel()
		{
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});

		ingredient_table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ingredient_table.getTableHeader().setReorderingAllowed(false);
		ingredient_table.getTableHeader().setResizingAllowed(false);
		((DefaultTableCellRenderer)ingredient_table.getDefaultRenderer(String.class)).setHorizontalAlignment(SwingConstants.CENTER);
		((DefaultTableModel)ingredient_table.getModel()).setColumnCount(3);
		((DefaultTableModel)ingredient_table.getModel()).setRowCount(0);
		((DefaultTableModel)ingredient_table.getModel()).setColumnIdentifiers(new String[] {"Ingrediente", "Cantidad", "Bloqueado"});
		ingredient_table.getColumnModel().getColumn(0).setPreferredWidth((int)(ingredient_table.getWidth()/2));
		ingredient_table.getColumnModel().getColumn(1).setPreferredWidth((int)(ingredient_table.getWidth()/5));
		ingredient_table.getColumnModel().getColumn(2).setPreferredWidth((int)(ingredient_table.getWidth()/4));
		ingredient_table.setFillsViewportHeight(true);
		ingredient_table.setBackground(new Color(176, 224, 230));
		
		
		JScrollPane ingredient_jpane = new JScrollPane(ingredient_table);
		ingredient_jpane.setBounds(10, 10,(int) (ingredient_panel.getWidth()- 20), (int) (ingredient_panel.getHeight()- 20));
		ingredient_jpane.setHorizontalScrollBar(null);
		ingredient_jpane.getViewport().setBackground(new Color(176, 224, 230));
		ingredient_panel.add(ingredient_jpane);
		getContentPane().add(ingredient_panel);
	}
	/**
	 * Crea los botones de la ventana
	 */
	public void createButtons(){
		order_buttons = new RestaurantButton[2];
		
		order_buttons[0] = new RestaurantButton("Cocinado", ActorStruct.COOK);
		order_buttons[0].setActionCommand("cooked_button");
		order_buttons[0].setLocation((int)(order_panel.getLocation().getX() + order_panel.getWidth()/2 - order_buttons[0].getWidth() - 15), (int)(order_panel.getLocation().getY() + order_panel.getHeight() + 15));
		getContentPane().add(order_buttons[0]);
		
		order_buttons[1] = new RestaurantButton("Información", ActorStruct.COOK);
		order_buttons[1].setActionCommand("info_button");
		order_buttons[1].setLocation((int)(order_buttons[0].getLocation().getX() + order_buttons[0].getWidth() + 30), (int)(order_panel.getLocation().getY() + order_panel.getHeight() + 15));
		getContentPane().add(order_buttons[1]);
		
		
		ingredient_button = new RestaurantButton("Bloquear", ActorStruct.COOK);
		ingredient_button.setActionCommand("block_button");
		ingredient_button.setLocation((int)(ingredient_panel.getLocation().getX() + ingredient_panel.getWidth()/2 - ingredient_button.getWidth()/2), (int)(ingredient_panel.getLocation().getY() + ingredient_panel.getHeight() + 15));
		getContentPane().add(ingredient_button);
		
	}

@Override
public void setController(EventListener controller) {
	action_buttons[0].addActionListener((ActionListener)controller);
	action_buttons[1].addActionListener((ActionListener)controller);
	order_buttons[0].addActionListener((ActionListener)controller);
	order_buttons[1].addActionListener((ActionListener)controller);
	ingredient_button.addActionListener((ActionListener)controller);
	end_service_button.addActionListener((ActionListener)controller);
	order_table.getSelectionModel().addListSelectionListener((ListSelectionListener) controller);
	ingredient_table.getSelectionModel().addListSelectionListener((ListSelectionListener) controller);
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
	return ingredient_panel;
}

public void setIngredientPanel(JPanel ingredient_panel) {
	this.ingredient_panel = ingredient_panel;
}

public JButton[] getOrderButtons() {
	return order_buttons;
}

public void setOrderButtons(JButton[] order_buttons) {
	this.order_buttons = order_buttons;
}

public JButton getIngredientButton() {
	return ingredient_button;
}

public void setIngredientButton(JButton ingredient_button) {
	this.ingredient_button = ingredient_button;
}

public JButton getEndServiceButton() {
	return end_service_button;
}

public void setEndServiceButton(JButton end_service_button) {
	this.end_service_button = end_service_button;
}

public JTable getIngredientTable() {
	return ingredient_table;
}

public void setIngredientTable(JTable ingredient_table) {
	this.ingredient_table = ingredient_table;
}

public JTable getOrderTable() {
	return order_table;
}

public void setOrderTable(JTable order_table) {
	this.order_table = order_table;
}



@Override
public void update(Observable arg0, Object arg1) {
	String s = (String) arg1;
	switch (s){
	case "ingredient_unblock":
	case "ingredient_block":
	case "cook_plate":
	case "update":
		updateOrderTable((ObservableRestaurant) arg0);
		updateIngredientTable((ObservableRestaurant) arg0);
		break;
	}
	
}
/**
 * Actualiza la tabla de pedidos
 * @param r - ObservableRestaurant
 */
public void updateOrderTable(ObservableRestaurant r){
	((DefaultTableModel)order_table.getModel()).setRowCount(0);
	ArrayList <Plate> plates = (ArrayList<Plate>) ((ObservableResCook)r).getOrderedPlates();
	ArrayList <String> tables = r.getDatabase().getOrderedPlatesTableID();
	ArrayList <Timestamp> dates = r.getDatabase().getOrderedPlatesDate();
	if (plates.size()> 0){
		for(int i = 0; i < plates.size(); i++){
		 ((DefaultTableModel)order_table.getModel()).setRowCount(order_table.getModel().getRowCount() + 1);
		 order_table.setValueAt(plates.get(i).getName(), i, 0);
		 order_table.setValueAt(tables.get(i), i, 1);
		 long time = GregorianCalendar.getInstance().getTimeInMillis() - (dates.get(i).getTime());
		 String time_s = "";
		 long time_seconds = TimeUnit.MILLISECONDS.toSeconds(time);
		 long time_minutes = TimeUnit.MILLISECONDS.toMinutes(time);
		 long time_hours = TimeUnit.MILLISECONDS.toHours(time);
		 if (time_hours == 0 && time_minutes == 0) time_s = time_seconds + " segundos";
		 else if (time_hours == 0) time_s = time_minutes + " min " + time_seconds%60 + " sec" ;
		 else if (time_hours < 24) time_s = time_hours + " h " + time_minutes%60 + " min ";
		 else time_s = "más de 1 día";
		 order_table.setValueAt(time_s, i, 2);
		}
	}
}
/**
 * Actualiza la tabla de ingredientes
 * @param r - ObservableRestaurant
 */
public void updateIngredientTable(ObservableRestaurant r){
	((DefaultTableModel)ingredient_table.getModel()).setRowCount(0);
	HashMap<String, Ingredient> ing = ((ObservableResCook)r).getIngredients();
	List<Ingredient> ingredients = new ArrayList<Ingredient>((Collection<Ingredient>) ing.values());

		for(int i = 0; i < ingredients.size(); i++){
			((DefaultTableModel)ingredient_table.getModel()).setRowCount(ingredient_table.getModel().getRowCount() + 1);
			ingredient_table.setValueAt(ingredients.get(i).getName(), i, 0);
			ingredient_table.setValueAt(ingredients.get(i).getAmount(), i, 1);
			if (ingredients.get(i).isBlocked())
				ingredient_table.setValueAt("Sí", i, 2);
			else ingredient_table.setValueAt("No", i, 2);
		}

}
}
