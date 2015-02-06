
package view;

import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;

import model.ActorStruct;
import model.Menu;
import model.ObservableResClient;
import model.ObservableRestaurant;
import model.Plate;
import model.ResClient;
import controller.OrderWindowController;
import controller.PromoWindowController;
import controller.TheWindowController;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;
/**
 * Clase que representa la ventana para realizar pedidos
 * @author Grupo 9
 *
 */
public class OrderWindow extends TheWindow{
  /**
   * Botones con los nombres de los paltos
   */
private List<JButton> plate_buttons;


  /**
   * Los botones que aparecen en la ventana
   */
  private JButton[] action_buttons;
  /**
   * Panel de pedido
   */
  private JPanel ordercart_panel;
  /**
   * Panel de platos
   */
  private JPanel plate_panel;
  /**
   * Desplegable para seleccionar filtro por Menús, platos o favoritos
   */
  private JComboBox[] filter_comboboxes;
/**
 * Check boxes para seleccionar casos especiales
 */
  private JCheckBox[] disease_checkboxes;

  /**
   * Número de página de navegación por platos
   */
  private int page;
  /**
   * Tabla del pedido
   */
  private JTable cart_table;
  /**
   * Botón de continuar
   */
  private JButton continue_button;
  /**
   * Botón de borrar
   */
  private JButton delete_button;
  /**
   * Botón de llamar al camarero
   */
  private JButton call_waiter_button;
  /**
   * Botón anterior
   */
  private JButton last;
  /**
   * Botón siguiente
   */
  private JButton next;
  /**
   * Dice si el usuario es anónimo
   */
  private boolean isAnonymous;
  private boolean painting; 
  /**
   * Temporizador que actualiza la ventana cada 5 segundos
   */
  Timer timer;
  
  public boolean isPainting() {
	return painting;
}
  /**
	 * Constructor de OrderWindow. Inicializa sus valores.
	 * @param actor tipo de usuario
	 * @param controller el controlador
	 */
public OrderWindow (ActorStruct actor, TheWindowController controller){
	  super(ActorStruct.CLIENT, controller);
	  super.setController(controller);
	  
	  getTitleLabel().setText("Bienvenido "+ controller.getRestaurant().getStation().getActor().getUser());
	  
	  this.page = 0;
	  createOrderPanel();
	  createPlatePanel();
	  createButtons();
	  createTopBoxes();
	  setController(controller);
	  controller.setView(this);
	  ((OrderWindowController)controller).load();
	  ((OrderWindowController)controller).initTables(); 
	  ((OrderWindowController)controller).initButtons("plates"); 
	  timer = new Timer (5*1000, controller);
	  timer.setActionCommand("timer");
	  timer.start();
		
	  repaint();
	  
	  
  }
  
  public Timer getTimer() {
	return timer;
}

public JButton getLast() {
	return last;
}

public void setLast(JButton last) {
	this.last = last;
}

public JButton getNext() {
	return next;
}

public void setNext(JButton next) {
	this.next = next;
}
@Override
public void addMenuButtons(ActorStruct actor){
	  action_buttons = new RestaurantButton[4];
	  
	  	action_buttons[0]= new RestaurantButton("Favoritos", actor);
	  	action_buttons[0].setActionCommand("favourites");
		getMenuPanel().add(action_buttons[0]);
			
		action_buttons[1] = new RestaurantButton("Historial", actor);
		action_buttons[1].setActionCommand("history");
		getMenuPanel().add(action_buttons[1]);
		
		

		
		action_buttons[2] = new RestaurantButton("Perfil", actor);
		action_buttons[2].setActionCommand("profile");
		getMenuPanel().add(action_buttons[2]);
		
		action_buttons[3] = new RestaurantButton("Desconectar", actor);
		action_buttons[3].setActionCommand("disconnect");
		getMenuPanel().add(action_buttons[3]);
		

  }
/**
 * Crea el panel de pedidos y lo añade.
 */
  public void createOrderPanel(){
	  	ordercart_panel = new JPanel();
	  	ordercart_panel.setBorder(new LineBorder(new Color(240, 230, 140), 10));
		ordercart_panel.setBounds((int)(getTitleLabel().getBounds().getMaxX()- 400) , 170, 400, 400);
		ordercart_panel.setLayout(null);
		
		cart_table = new JTable();
		cart_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		cart_table.setBounds(0, 0,(int) (ordercart_panel.getWidth()), (int) (ordercart_panel.getHeight()));
		cart_table.setModel(new DefaultTableModel()
		{
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		
		((DefaultTableModel)cart_table.getModel()).setColumnCount(2);
		((DefaultTableModel)cart_table.getModel()).setRowCount(0);
		cart_table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		cart_table.getTableHeader().setReorderingAllowed(false);
		cart_table.getTableHeader().setResizingAllowed(false);
		((DefaultTableCellRenderer)cart_table.getDefaultRenderer(String.class)).setHorizontalAlignment(SwingConstants.CENTER);
		((DefaultTableModel)cart_table.getModel()).setColumnCount(2);
		((DefaultTableModel)cart_table.getModel()).setRowCount(0);
		((DefaultTableModel)cart_table.getModel()).setColumnIdentifiers(new String[] {"Menú", "Precio"});
		cart_table.getColumnModel().getColumn(0).setPreferredWidth((int)(cart_table.getWidth()/2));
		cart_table.getColumnModel().getColumn(1).setPreferredWidth((int)(cart_table.getWidth()/2));
		cart_table.setFillsViewportHeight(true);
		cart_table.setBackground(new Color(255, 250, 205));		
		
		JScrollPane ingredient_jpane = new JScrollPane(cart_table);
		ingredient_jpane.setBounds(10, 10,(int) (ordercart_panel.getWidth()- 20), (int) (ordercart_panel.getHeight()- 20));
		ingredient_jpane.setHorizontalScrollBar(null);
		ingredient_jpane.getViewport().setBackground(new Color(176, 224, 230));
		ordercart_panel.add(ingredient_jpane);
		getContentPane().add(ordercart_panel);
		
		
  }
 
  /**
   * Crea el panel de platos y lo añade.
   */
  public void createPlatePanel(){
	  	this.plate_panel = new JPanel();
	  	
	  	plate_panel.setBounds((int)(ordercart_panel.getLocation().getX() - 700), 270, 600, 300);
	  	plate_panel.setBackground(null);
		getContentPane().add(plate_panel);
		plate_panel.setLayout(new GridLayout(3,4));
		
		plate_buttons = new ArrayList<JButton>();
		for(int i = 0; i < 12; i++){
			RestaurantButton aux = new RestaurantButton("", ActorStruct.CLIENT);
			aux.setFont(new Font("Trebuchet MS", Font.PLAIN, 16));
			plate_buttons.add(aux);
  		}
		
		for(int i = 0; i < 12; i++){
			plate_buttons.get(i).setActionCommand("select");
			plate_panel.add(plate_buttons.get(i));
		}
		plate_panel.doLayout();
		getContentPane().add(plate_panel);

  }
  /**
   * Crea las cajas de los casos especiales y las añade a la ventana
   */
  public void createTopBoxes(){
 this.filter_comboboxes = new JComboBox[2];
	  
	  this.filter_comboboxes[0] = new JComboBox<String>();
	  filter_comboboxes[0].setBounds(plate_panel.getX(), plate_panel.getY() - 100, plate_panel.getWidth()/3 -50, 25);
	  ((JLabel)filter_comboboxes[0].getRenderer()).setHorizontalAlignment(JLabel.CENTER);
	  filter_comboboxes[0].setActionCommand("filter");
	  filter_comboboxes[0].addItem("Platos");
	  filter_comboboxes[0].addItem("Menús");
	  filter_comboboxes[0].addItem("Favoritos");
	  getContentPane().add(filter_comboboxes[0]);
	  
	  filter_comboboxes[1] = new JComboBox<String>();
	  ((JLabel)filter_comboboxes[1].getRenderer()).setHorizontalAlignment(JLabel.CENTER);
	  filter_comboboxes[1].setBounds(plate_panel.getX() + plate_panel.getWidth()/3 , plate_panel.getY() - 100, plate_panel.getWidth()/3 -50, 25);
	  filter_comboboxes[1].setActionCommand("filter");
	  filter_comboboxes[1].addItem("Ingrediente");
	  getContentPane().add(filter_comboboxes[1]);
	  
	  this.disease_checkboxes = new JCheckBox[8];
	  
	  disease_checkboxes[0] = new JCheckBox("Apto diabetes");
	  disease_checkboxes[0].setBackground(null);
	  disease_checkboxes[0].setActionCommand("filter");
	  disease_checkboxes[0].setBounds(plate_panel.getX() + 2*plate_panel.getWidth()/4 + 75, plate_panel.getY() - 100, 120, 23);
	  
	  getContentPane().add(disease_checkboxes[0]);
	  
	  disease_checkboxes[1] = new JCheckBox("Sin gluten");
	  disease_checkboxes[1].setBackground(null);
	  disease_checkboxes[1].setActionCommand("filter");
	  disease_checkboxes[1].setBounds(plate_panel.getX() + 3*plate_panel.getWidth()/4 + 50, plate_panel.getY() - 100, 120, 23);
	  getContentPane().add(disease_checkboxes[1]);
	 
	  disease_checkboxes[2] = new JCheckBox("Vegetariano");
	  disease_checkboxes[2].setBackground(null);
	  disease_checkboxes[2].setActionCommand("filter");
	  disease_checkboxes[2].setBounds(plate_panel.getX() + 2*plate_panel.getWidth()/4 + 75, plate_panel.getY() - 80, 120, 23);
	  getContentPane().add(disease_checkboxes[2]);
	  
	  disease_checkboxes[3] = new JCheckBox("Sin pescado");
	  disease_checkboxes[3].setBackground(null);
	  disease_checkboxes[3].setActionCommand("filter");
	  disease_checkboxes[3].setBounds(plate_panel.getX() + 3*plate_panel.getWidth()/4 + 50, plate_panel.getY() - 80, 120, 23);
	  getContentPane().add(disease_checkboxes[3] );
	  
	  disease_checkboxes[4] = new JCheckBox("Sin lactosa");
	  disease_checkboxes[4].setBackground(null);
	  disease_checkboxes[4].setActionCommand("filter");
	  disease_checkboxes[4].setBounds(plate_panel.getX() + 2*plate_panel.getWidth()/4 + 75, plate_panel.getY() - 60, 120, 23);
	  getContentPane().add(disease_checkboxes[4]);
	  
	  this.disease_checkboxes[5] = new JCheckBox("Sin frutos secos");
	  disease_checkboxes[5].setBackground(null);
	  disease_checkboxes[5].setActionCommand("filter");
	  disease_checkboxes[5].setBounds(plate_panel.getX() + 3*plate_panel.getWidth()/4 + 50, plate_panel.getY() - 60, 120, 23);
	  getContentPane().add(disease_checkboxes[5]);
	  
	  disease_checkboxes[6]  = new JCheckBox("Apto hipertenso");
	  disease_checkboxes[6].setBackground(null);
	  disease_checkboxes[6].setActionCommand("filter");
	  disease_checkboxes[6].setBounds(plate_panel.getX() + 2*plate_panel.getWidth()/4 + 75, plate_panel.getY() - 40, 120, 23);
	  getContentPane().add(disease_checkboxes[6]);
	  
	  disease_checkboxes[7] = new JCheckBox("Dietético");
	  disease_checkboxes[7].setBackground(null);
	  disease_checkboxes[7].setActionCommand("filter");
	  disease_checkboxes[7] .setBounds(plate_panel.getX() + 3*plate_panel.getWidth()/4 + 50, plate_panel.getY() - 40, 120, 23);
	  getContentPane().add(disease_checkboxes[7] );
	  
	  
	  
	  
	  
	  
	  
	  
	
	  
	  
	  
  }
  public List<JButton> getPlateButtons() {
	return plate_buttons;
}

public void setPlateButtons(List<JButton> plate_buttons) {
	this.plate_buttons = plate_buttons;
}

public JComboBox[] getFilterComboboxes() {
	return filter_comboboxes;
}

public void setFilterComboboxes(JComboBox[] filter_comboboxes) {
	this.filter_comboboxes = filter_comboboxes;
}

public JCheckBox[] getDiseaseCheckboxes() {
	return disease_checkboxes;
}

public void setDiseaseCheckboxes(JCheckBox[] disease_checkboxes) {
	this.disease_checkboxes = disease_checkboxes;
}

public int getPage() {
	return page;
}

public void setPage(int page) {
	this.page = page;
}

/**
 * Crea los botones y los añade al panel.
 */
public void createButtons(){
	 
	  this.continue_button = new RestaurantButton("Continuar", ActorStruct.CLIENT);
	  continue_button.setActionCommand("continue");
	  continue_button.setLocation((int)(ordercart_panel.getLocation().getX() + ordercart_panel.getWidth()/2 - continue_button.getWidth() - 15) , (int)(ordercart_panel.getLocation().getY()  + ordercart_panel.getHeight() + 20));
	  getContentPane().add(continue_button);
	  
	  this.delete_button = new RestaurantButton("Eliminar", ActorStruct.CLIENT);
	  delete_button.setActionCommand("delete");
	  delete_button.setLocation((int)(ordercart_panel.getLocation().getX() + ordercart_panel.getWidth()/2 + 15) , (int)(ordercart_panel.getLocation().getY()  + ordercart_panel.getHeight() + 20));
	  getContentPane().add(delete_button);
	  
	  last = new RestaurantButton("Anterior", ActorStruct.CLIENT);
	  last.setActionCommand("last");
	  last.setLocation(plate_panel.getX(), plate_panel.getY()+plate_panel.getHeight()+25);
	  getContentPane().add(last);
	  
	  next = new RestaurantButton("Siguiente", ActorStruct.CLIENT);
	  next.setActionCommand("next");
	  next.setLocation(plate_panel.getX() + 3 * plate_panel.getWidth()/4, plate_panel.getY()+plate_panel.getHeight()+25);
	  getContentPane().add(next);
	  
	  this.call_waiter_button = new RestaurantButton("Llamar al camarero", ActorStruct.CLIENT);
	  call_waiter_button.setActionCommand("call");
	  call_waiter_button.setBounds(last.getX() + last.getWidth(), getHelpButton().getY() + 25, 300, continue_button.getHeight());
	  getContentPane().add(call_waiter_button);
	  
	  
	  
	 
  }
  public JTable getCartTable(){
	  return this.cart_table;
  }
  
  public void setPlatesTable(JTable table){
	  this.cart_table = table;
  }
  

  
@Override
public void setController(EventListener controller) {
	this.action_buttons[0].addActionListener((ActionListener) controller);
	this.action_buttons[1].addActionListener((ActionListener) controller);
	this.action_buttons[2].addActionListener((ActionListener) controller);
	this.action_buttons[3].addActionListener((ActionListener) controller);
	this.continue_button.addActionListener((ActionListener) controller);
	this.delete_button.addActionListener((ActionListener) controller);
	this.call_waiter_button.addActionListener((ActionListener) controller);
	this.next.addActionListener((ActionListener) controller);
	this.last.addActionListener((ActionListener) controller); 
	for(int i = 0; i< plate_buttons.size(); i++){
		plate_buttons.get(i).addActionListener((ActionListener) controller);
	}
	
	this.filter_comboboxes[0].addActionListener((ActionListener) controller);
	this.filter_comboboxes[1].addActionListener((ActionListener) controller);
	
	for(int i = 0; i< this.disease_checkboxes.length; i++){
		disease_checkboxes[i].addActionListener((ActionListener) controller);
	}
	cart_table.getSelectionModel().addListSelectionListener((ListSelectionListener) controller);
	
}
/**
 * Inicializa todos los botones con los platos no bloqueados.
 * @param r el restaurante
 */
public void initButtons(ObservableRestaurant r){
	for (int i = 0; i < getPlateButtons().size(); i++){
		getPlateButtons().get(i).setVisible(true);
	}
	
	if(this.filter_comboboxes[0].getSelectedItem().equals("Platos")){
	///	this.filter_comboboxes[1].setEnabled(true);
		HashMap<String, Plate> p1 = r.getPlates();
		ArrayList<Plate> plates = new ArrayList<Plate>((Collection<Plate>)p1.values());
		if(getPage() == 0){
			getLast().setEnabled(false);
		}else{
			getLast().setEnabled(true);
		}
		
		if((plates.size() - getPage()) < 12){
			getNext().setEnabled(false);
		}else{
			getNext().setEnabled(true);
		}
		
		
		for(int i = getPage(); i < getPage()+12; i ++){
			if(i < plates.size())
				if(!plates.get(i).isBlocked())
				getPlateButtons().get(i).setText("<html><body>" + plates.get(plates.size() - i - 1).getName() + "</body></html>");
		}
		if(plates.size() - getPage() < getPlateButtons().size()){
			for (int i = plates.size() - getPage(); i < getPlateButtons().size(); i++){
				getPlateButtons().get(i).setVisible(false);
			}
			
			getNext().setEnabled(false);
		}else{
			for (int i = 0; i < getPlateButtons().size(); i++){
				getPlateButtons().get(i).setVisible(true);
			}
			getNext().setEnabled(true);
		}
	}else if (this.filter_comboboxes[0].getSelectedItem().equals("Menús")){

		HashMap<String, Menu> m1 = r.getMenus();
		ArrayList<Menu> menus = new ArrayList<Menu>((Collection<Menu>)m1.values());
		if(getPage() == 0){
			getLast().setEnabled(false);
		}else{
			getLast().setEnabled(true);
		}
		
		if((menus.size() - getPage()) < 12){
			getNext().setEnabled(false);
		}else{
			getNext().setEnabled(true);
		}
		
		for(int i = getPage(); i < getPage()+12; i ++){
			
			if(i < menus.size()){
				if (!menus.get(i).isBlocked())
					getPlateButtons().get(i).setText("<html><body>" + menus.get(menus.size() - i - 1).getName()+ "</body></html>");
			}
		}
		if(menus.size() - getPage() < getPlateButtons().size()){
			for (int i = menus.size() - getPage(); i < getPlateButtons().size(); i++){
				getPlateButtons().get(i).setVisible(false);
			}
			
			getNext().setEnabled(false);
		}else{
			for (int i = 0; i < getPlateButtons().size(); i++){
				getPlateButtons().get(i).setVisible(true);
			}
			getNext().setEnabled(true);
		}
	}else if(this.filter_comboboxes[0].getSelectedItem().equals("Favoritos")){
		HashMap<String, Plate> f1 = r.getStation().getActor().getFavourites();
		ArrayList<Plate> fav = new ArrayList<Plate>((Collection<Plate>)f1.values());
		if(page == 0){
			last.setEnabled(false);
		}else{
			last.setEnabled(true);
		}
		
		if((fav.size() - getPage()) < 12){
			next.setEnabled(false);
		}else{
			next.setEnabled(true);
		}
		
		
		for(int i = getPage(); i < getPage()+12; i ++){
			if(i < fav.size())
				if(!fav.get(i).isBlocked())
				getPlateButtons().get(i).setText("<html><body>" + fav.get(fav.size() - i - 1).getName() + "</body></html>" );
		}
		if(fav.size() - getPage() < getPlateButtons().size()){
			for (int i = fav.size() - getPage(); i < getPlateButtons().size(); i++){
				getPlateButtons().get(i).setVisible(false);
			}
			
			getNext().setEnabled(false);
		}else{
			for (int i = 0; i < getPlateButtons().size(); i++){
				getPlateButtons().get(i).setVisible(true);
			}
			getNext().setEnabled(true);
		}
	}
}

@Override
public void update(Observable arg0, Object arg1) {
	String s = (String) arg1;
	switch (s){
	case "remove_menu_plate":
	case "modify_order": 
		updateCartTable ((ObservableRestaurant) arg0);
		break;
	case "filter_menus":
	case "filter_plates":
		this.initButtons((ObservableRestaurant )arg0);
		break;
	}
	
	
	
}
/**
 * Actualiza la tabla del pedido.
 * @param r el restaurante
 */
public void updateCartTable(ObservableRestaurant r) {
	painting = true;
	((DefaultTableModel)cart_table.getModel()).setRowCount(0);
	ArrayList<Menu> menus  = (ArrayList<Menu>) ((ObservableResClient)r).getOrder().getMenus();
	
	if (menus.size()> 0){
		for(int i = 0; i < menus.size(); i++){
		 ((DefaultTableModel)cart_table.getModel()).setRowCount(cart_table.getModel().getRowCount() + 1);
		 cart_table.setValueAt(menus.get(i).getName(), i, 0);
		 cart_table.setValueAt(menus.get(i).getPrice(), i, 1);
			

		}
	}
	for (int i = 0; i< cart_table.getRowCount(); i++){
		if (i < r.getStation().getOrder().getMenuCount()){
			if (!(r.getStation().getOrder().getBlockMark() == 0) && !((i > ((r.getStation().getOrder().getBlockMark()/10)-1)))){
				cart_table.setValueAt("(Pedido anterior) " + cart_table.getValueAt(i, 0) , i, 0);
			}
			//else cart_table.getCellRenderer(i, 0).getTableCellRendererComponent(null, null, false, false, i, 0).setBackground(null);
		} else if (!(r.getStation().getOrder().getBlockMark() == 0) && !((i > (r.getStation().getOrder().getBlockMark()%10)+(r.getStation().getOrder().getMenuCount()-1)) && (i < (r.getStation().getOrder().getMenuCount() + r.getStation().getOrder().getPlateCount())))){
			cart_table.setValueAt("(Pedido anterior) " + cart_table.getValueAt(i, 0) , i, 0);
		} //else ;
	}
//	cart_table.clearSelection();
	painting = false;
	
}

public JButton[] getActionButtons() {
	return action_buttons;
}
}
