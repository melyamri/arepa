
package view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;

import javax.swing.*;

import model.*;
import controller.*;

import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.Color;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
/**
 * Esta clase contiene la ventana para empezar a cocinar y los métodos para que esté actualizada
 *
 */
public class StartCookWindow extends TheWindow{
  


	/**
	   * Array de botones del panel principal
	   */
private JButton[] action_buttons;

/**
 * Panel de platos.
 */
private JPanel plate_panel;

/**
 * Panel de ingredientes.
 */
private JPanel ingredient_panel;


/**
 * Etiquetas de nuevo plato.
 */
private JLabel[] new_plate_labels;

/**
 * Botón de nuevo plato.
 */
private JButton new_plate_button;

/**
 * Etiquetas de nuevo ingrediente.
 */
private JLabel[] new_ingredient_labels;

/**
 * Botón de nuevo ingrediente.
 */
private JButton new_ingredient_button;
  /**
   * Área de texto de información adicional del plato
   */
private JTextArea additional_info;

  /**
   * Botón comenzar servicio
   */
private JButton start_service_button;
/**
 * Campos para crear los datos de un nuevo plato.
 */
private JTextField[] new_plate_fields;
/**
 * Campo para crear los datos de un nuevo ingrediente.
 */
private JTextField[] new_ingredient_fields;
/**
 * Tabla de ingredientes
 */
private JTable ingredient_table;
/**
 * Tabla de platos
 */
private JTable plate_table;
/**
 * Panel de nuevo plato  
 */
private JPanel new_plate_panel;
/**
 * Panel de nuevo ingrediente
 */
private JPanel new_ingredient_panel;
/**
 * Desplegables con la lista de ingredientes
 */
private JComboBox<String>[] ingredient_boxes;
/**
 * Check boxes de los casos especiales
 */
private JCheckBox[] diseases;
/**
 * Botones para añadir y eliminar plato
 */
private JButton[] plate_buttons;
/**
 * Botones para añadir y eliminar ingrediente
 */
private JButton[] ingredient_buttons;
/**
 * Botón para seleccionar una imagen del directorio local
 */
private JButton image_button;


 /**
  * Constructor de la ventana. Inicializa sus valores.
  * @param controller controlador de la ventana
  */
  public StartCookWindow(TheWindowController controller) {
		super(ActorStruct.COOK, controller);
		
		super.setController(controller);
		getTitleLabel().setText("Bienvenido, cocinero");

		createPanels();
		createNewPlate();
		createNewIngredient();
		createButtons();

		
		//Botón comenzar servicio
		start_service_button = new RestaurantButton("Comenzar Servicio", ActorStruct.COOK);
		start_service_button.setActionCommand("start");
		start_service_button.setBounds((int)(getWindowWidth()/2 - 160),(int)(getWindowHeight()-100), 320, 60);
		getContentPane().add(start_service_button);

		setController(controller);
		controller.setView(this);
		((StartCookWindowController)controller).load();
		((StartCookWindowController)controller).initTables(); 
		
		this.repaint();
	}
	public void addMenuButtons (ActorStruct actor){
	
		this.action_buttons = new RestaurantButton[4];
		this.action_buttons[0] = new RestaurantButton("Menú/Promo", actor);
		action_buttons[0].setActionCommand("menu_promo");
		getMenuPanel().add(this.action_buttons[0]);
		
		this.action_buttons[1] = new RestaurantButton("Usuarios", actor);
		action_buttons[1].setActionCommand("users");
		getMenuPanel().add(this.action_buttons[1]);
		
		this.action_buttons[2] = new RestaurantButton("Historial", actor);
		action_buttons[2].setActionCommand("history");
		getMenuPanel().add(this.action_buttons[2]);
		
		this.action_buttons[3] = new RestaurantButton("Desconectar", actor);
		action_buttons[3].setActionCommand("disconnect");
		getMenuPanel().add(this.action_buttons[3]);
		
		
	}
	/**
	 * Crea los paneles de la ventana
	 */
	public void createPanels(){
		createIngredientPanel();
		createPlatePanel();
	}
	/**
	 * Crea el panel de ingredientes
	 */
	public void createIngredientPanel(){
		ingredient_panel = new JPanel();
		ingredient_panel.setBackground(new Color(176, 224, 230));
		ingredient_panel.setBorder(new LineBorder(SystemColor.activeCaption, 10));
		ingredient_panel.setBounds((int)(getTitleLabel().getBounds().getMaxX()- 400) , 170, 400, 425);
		ingredient_panel.setLayout(null);

		ingredient_table = new JTable();
		ingredient_table.setName("ingredient_table");
		ingredient_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		ingredient_table.setBounds(0, 0,(int) (ingredient_panel.getWidth()), (int) (ingredient_panel.getHeight()));
		ingredient_table.setModel(new DefaultTableModel()
		{
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		((DefaultTableModel)ingredient_table.getModel()).setColumnCount(2);
		((DefaultTableModel)ingredient_table.getModel()).setRowCount(0);
		ingredient_table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ingredient_table.getTableHeader().setReorderingAllowed(false);
		ingredient_table.getTableHeader().setResizingAllowed(false);
		((DefaultTableCellRenderer)ingredient_table.getDefaultRenderer(String.class)).setHorizontalAlignment(SwingConstants.CENTER);
		((DefaultTableModel)ingredient_table.getModel()).setColumnIdentifiers(new String[] {"Ingrediente", "Cantidad"});
		ingredient_table.getColumnModel().getColumn(0).setPreferredWidth((int)(ingredient_table.getWidth()/2));
		ingredient_table.getColumnModel().getColumn(1).setPreferredWidth((int)(ingredient_table.getWidth()/2));
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
	 * Crea el panel de platos
	 */
	public void createPlatePanel(){
		plate_panel = new JPanel();
		plate_panel.setBackground(new Color(176, 224, 230));
		plate_panel.setBorder(new LineBorder(new Color(153, 180, 209), 10));
		plate_panel.setBounds(70 , 170, 400, 425);
		plate_panel.setLayout(null);
		
		plate_table = new JTable();
		plate_table.setName("plate_table");
		plate_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		plate_table.setBounds(0, 0,(int) (plate_panel.getWidth()), (int) (plate_panel.getHeight()));
		plate_table.setModel(new DefaultTableModel()
		{
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});

		plate_table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		plate_table.getTableHeader().setReorderingAllowed(false);
		plate_table.getTableHeader().setResizingAllowed(false);
		((DefaultTableCellRenderer)plate_table.getDefaultRenderer(String.class)).setHorizontalAlignment(SwingConstants.CENTER);
		((DefaultTableModel)plate_table.getModel()).setColumnCount(2);
		((DefaultTableModel)plate_table.getModel()).setRowCount(0);
		((DefaultTableModel)plate_table.getModel()).setColumnIdentifiers(new String[] {"Plato", "Bloqueado"});

		plate_table.getColumnModel().getColumn(0).setPreferredWidth((int)(plate_table.getWidth()/2));
		plate_table.getColumnModel().getColumn(1).setPreferredWidth((int)(plate_table.getWidth()/2));
		plate_table.setFillsViewportHeight(true);
		plate_table.setBackground(new Color(176, 224, 230));
		
		JScrollPane plate_jpane = new JScrollPane(plate_table);
		plate_jpane.setBounds(10, 10,(int) (plate_panel.getWidth()- 20), (int) (plate_panel.getHeight()- 20));
		plate_jpane.setHorizontalScrollBar(null);
		plate_jpane.getViewport().setBackground(new Color(176, 224, 230));
		plate_panel.add(plate_jpane);
		getContentPane().add(plate_panel);
	}
	/**
	 * Crea el panel de nuevo plato
	 */
	public void createNewPlate(){
		this.new_plate_panel = new JPanel();
		new_plate_panel.setBounds((int)(plate_panel.getWidth() + plate_panel.getLocation().getX()), (int)plate_panel.getLocation().getY(), (int)(ingredient_panel.getLocation().getX() -( plate_panel.getWidth() + plate_panel.getLocation().getX())), plate_panel.getHeight());
		
		new_plate_panel.setBackground(null);
		TitledBorder border = new TitledBorder("Añadir/Modificar plato");
		border.setTitleColor(Color.BLACK);
		border.setTitleFont(new Font("Trebuchet MS", Font.BOLD, 14));
		new_plate_panel.setBorder(border);
		this.new_plate_labels = new JLabel[5];
		
		new_plate_labels[0]= new JLabel("Nombre:");
		new_plate_labels[0].setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		new_plate_labels[0].setForeground(SystemColor.desktop);
		new_plate_labels[0].setBounds(31, 33, 65, 22);
		new_plate_panel.add(new_plate_labels[0]);
		
		new_plate_labels[1] = new JLabel("Ingredientes:");
		new_plate_labels[1].setForeground(Color.BLACK);
		new_plate_labels[1].setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		new_plate_labels[1].setBounds(31, 260, 90, 22);
		new_plate_panel.add(new_plate_labels[1]);
		
		new_plate_labels[2] = new JLabel("Procedencia:");
		new_plate_labels[2].setForeground(Color.BLACK);
		new_plate_labels[2].setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		new_plate_labels[2].setBounds(31, 97, 90, 22);
		new_plate_panel.add(new_plate_labels[2]);
		
		new_plate_labels[3] = new JLabel("Precio:");
		new_plate_labels[3].setForeground(Color.BLACK);
		new_plate_labels[3].setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		new_plate_labels[3].setBounds(178, 97, 65, 22);
		new_plate_panel.add(new_plate_labels[3]);
		
		
		new_plate_labels[4] = new JLabel("Informaci\u00F3n adicional:");
		new_plate_labels[4].setForeground(Color.BLACK);
		new_plate_labels[4].setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		new_plate_labels[4].setBounds(31, 161, 150, 22);
		new_plate_panel.add(new_plate_labels[4]);
		
		
		
		this.new_plate_fields = new JTextField[4];
		new_plate_fields[0] = new JTextField();
		new_plate_fields[0].setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		new_plate_fields[0].setBounds(24, 66, 299, 20);
		new_plate_panel.add(new_plate_fields[0]);
		new_plate_fields[0].setColumns(10);
		
		new_plate_fields[1] = new JTextField();
		new_plate_fields[1].setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		new_plate_fields[1].setColumns(10);
		new_plate_fields[1].setBounds(24, 130, 144, 20);
		new_plate_panel.add(new_plate_fields[1]);
		
		new_plate_fields[2] = new JTextField();
		new_plate_fields[2].setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		new_plate_fields[2].setColumns(10);
		new_plate_fields[2].setBounds(178, 130, 145, 20);
		new_plate_panel.add(new_plate_fields[2]);
		
		new_plate_fields[3] = new JTextField();
		new_plate_fields[3].setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		new_plate_fields[3].setColumns(10);
		new_plate_fields[3].setBounds(649, 324, 133, 25);
		new_plate_panel.add(new_plate_fields[3]);
		new_plate_panel.setLayout(null);
		
		
		additional_info = new JTextArea();
		additional_info.setSize(new Dimension(299,56));
		additional_info.setLineWrap(true);
		additional_info.setVisible(true);
		additional_info.setWrapStyleWord(true);
		//additional_info.setEditable(false);
		
		
		//additional_info.setBounds(24, 193, 299, 56);
		JScrollPane scroll = new JScrollPane(additional_info);
		scroll.setBounds(24, 193, 299, 56);
		new_plate_panel.add(scroll);
		
		
		
		
		this.ingredient_boxes = new JComboBox[8];
		
		ingredient_boxes[0] = new JComboBox<String>();
		ingredient_boxes[0].setBounds(24, 290, 93, 20);
		new_plate_panel.add(ingredient_boxes[0]);

		ingredient_boxes[1] = new JComboBox<String>();
		ingredient_boxes[1].setBounds(127, 290, 93, 20);
		new_plate_panel.add(ingredient_boxes[1]);
		
		ingredient_boxes[2] = new JComboBox<String>();
		ingredient_boxes[2].setBounds(230, 290, 93, 20);
		new_plate_panel.add(ingredient_boxes[2]);
		
		ingredient_boxes[3] = new JComboBox<String>();
		ingredient_boxes[3].setBounds(24, 321, 93, 20);
		new_plate_panel.add(ingredient_boxes[3]);
		
		ingredient_boxes[4] = new JComboBox<String>();
		ingredient_boxes[4].setBounds(127, 321, 93, 20);
		new_plate_panel.add(ingredient_boxes[4]);
		
		ingredient_boxes[5] = new JComboBox<String>();
		ingredient_boxes[5].setBounds(230, 321, 93, 20);
		new_plate_panel.add(ingredient_boxes[5]);
		
		ingredient_boxes[6] = new JComboBox<String>();
		ingredient_boxes[6].setBounds(24, 352, 93, 20);
		new_plate_panel.add(ingredient_boxes[6]);
		
		ingredient_boxes[7] = new JComboBox<String>();
		ingredient_boxes[7].setBounds(127, 352, 93, 20);
		new_plate_panel.add(ingredient_boxes[7]);
		
		new_plate_button = new RestaurantButton("Añadir", ActorStruct.COOK);
		new_plate_button.setActionCommand("new_plate");
		new_plate_button.setBounds(197, 391, 126, 23);
		new_plate_panel.add(new_plate_button);
		
		image_button = new JButton("Imagen");
		
		image_button.setForeground(Color.BLACK);
		image_button.setActionCommand("image");
		image_button.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		image_button.setBounds(31, ingredient_boxes[6].getY() + 30, 150, 22);
		
		new_plate_panel.add(image_button);
		new_plate_panel.setVisible(false);
		
		getContentPane().add(new_plate_panel);
	}
	/**
	 * Crea el panel de nuevo ingrediente
	 */
	public void createNewIngredient(){
		this.new_ingredient_panel = new JPanel();
		new_ingredient_panel.setBounds((int)(plate_panel.getWidth() + plate_panel.getLocation().getX()), (int)plate_panel.getLocation().getY(), (int)(ingredient_panel.getLocation().getX() -( plate_panel.getWidth() + plate_panel.getLocation().getX())), plate_panel.getHeight());
		new_ingredient_panel.setBackground(null);
		
		TitledBorder border = new TitledBorder("Añadir/Modificar ingrediente");
		border.setTitleColor(Color.BLACK);
		border.setTitleFont(new Font("Trebuchet MS", Font.BOLD, 14));
		new_ingredient_panel.setBorder(border);
		
		this.new_ingredient_labels = new JLabel[3];
		
		
		new_ingredient_labels[0] = new JLabel("Nombre:");
		new_ingredient_labels[0].setForeground(Color.BLACK);
		new_ingredient_labels[0].setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		new_ingredient_labels[0].setBounds(31, 33, 65, 22);
		new_ingredient_panel.add(new_ingredient_labels[0]);
		
		
		new_ingredient_labels[1] = new JLabel("Cantidad:");
		new_ingredient_labels[1].setForeground(Color.BLACK);
		new_ingredient_labels[1].setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		new_ingredient_labels[1].setBounds(31, 97, 65, 22);
		new_ingredient_panel.add(new_ingredient_labels[1]);
		
		new_ingredient_labels[2] = new JLabel("Adicional:");
		new_ingredient_labels[2].setForeground(Color.BLACK);
		new_ingredient_labels[2].setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		new_ingredient_labels[2].setBounds(31, 161, 150, 22);
		new_ingredient_panel.add(new_ingredient_labels[2]);
		
		
		
		this.new_ingredient_fields = new JTextField[2];

		new_ingredient_fields[0] = new JTextField();
		new_ingredient_fields[0].setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		new_ingredient_fields[0].setColumns(10);
		new_ingredient_fields[0].setBounds(24, 66, 299, 20);
		new_ingredient_panel.add(new_ingredient_fields[0]);
		
		new_ingredient_fields[1] = new JTextField();
		new_ingredient_fields[1].setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		new_ingredient_fields[1].setColumns(10);
		new_ingredient_fields[1].setBounds(24, 130, 299, 20);
		new_ingredient_panel.add(new_ingredient_fields[1]);
		new_ingredient_panel.setLayout(null);
		
		diseases = new JCheckBox[8];
		
		diseases[0] = new JCheckBox("Diabetes");
		diseases[0].setBackground(null);
		diseases[0].setBounds(24, 200, 100, 23);
		new_ingredient_panel.add(diseases[0]);
		
		diseases[1] = new JCheckBox("Gluten");
		diseases[1].setBackground(null);
		diseases[1].setBounds(150, 200, 100, 23);
		new_ingredient_panel.add(diseases[1]);
		
		diseases[2] = new JCheckBox("No vegetariano");
		diseases[2].setBackground(null);
		diseases[2].setBounds(24, 250, 100, 23);
		new_ingredient_panel.add(diseases[2]);
		
		diseases[3] = new JCheckBox("Pescado");
		diseases[3].setBackground(null);
		diseases[3].setBounds(150, 250, 100, 23);
		new_ingredient_panel.add(diseases[3]);
		
		diseases[4] = new JCheckBox("Lactosa");
		diseases[4].setBackground(null);
		diseases[4].setBounds(24, 300, 130, 23);
		new_ingredient_panel.add(diseases[4]);
		
		diseases[5] = new JCheckBox("Frutos secos");
		diseases[5].setBackground(null);
		diseases[5].setBounds(150, 300, 130, 23);
		new_ingredient_panel.add(diseases[5]);
		
		diseases[6] = new JCheckBox("Hipertensión");
		diseases[6].setBackground(null);
		diseases[6].setBounds(24, 350, 130, 23);
		new_ingredient_panel.add(diseases[6]);
		
		diseases[7] = new JCheckBox("No dietético");
		diseases[7].setBackground(null);
		diseases[7].setBounds(150, 350, 130, 23);
		new_ingredient_panel.add(diseases[7]);
		
		
		new_ingredient_button = new RestaurantButton("Añadir", ActorStruct.COOK);
		new_ingredient_button.setBounds(197, 391, 126, 23);
		new_ingredient_button.setActionCommand("new_ingredient");
		new_ingredient_panel.add(new_ingredient_button);
		
		new_ingredient_panel.setVisible(false);
		
		
		getContentPane().add(new_ingredient_panel);
	}
	/**
	 * Crea los botones de la ventana
	 */
	public void createButtons(){
		plate_buttons = new RestaurantButton[2];
		
		plate_buttons[0] = new RestaurantButton("Añadir", ActorStruct.COOK);
		plate_buttons[0].setLocation((int)(plate_panel.getLocation().getX() + plate_panel.getWidth()/2 - plate_buttons[0].getWidth() - 15), (int)(plate_panel.getLocation().getY() + plate_panel.getHeight() + 15));
		plate_buttons[0].setActionCommand("add_plate");
		getContentPane().add(plate_buttons[0]);
		
		plate_buttons[1] = new RestaurantButton("Eliminar", ActorStruct.COOK);
		plate_buttons[1].setActionCommand("delete_plate");
		plate_buttons[1].setLocation((int)(plate_buttons[0].getLocation().getX() + plate_buttons[0].getWidth() + 30), (int)(plate_panel.getLocation().getY() + plate_panel.getHeight() + 15));
		getContentPane().add(plate_buttons[1]);
		
		ingredient_buttons = new RestaurantButton[2];
		
		ingredient_buttons[0] = new RestaurantButton("Añadir", ActorStruct.COOK);
		ingredient_buttons[0].setActionCommand("add_ingredient");
		ingredient_buttons[0].setLocation((int)(ingredient_panel.getLocation().getX() + ingredient_panel.getWidth()/2 - ingredient_buttons[0].getWidth() - 15), (int)(ingredient_panel.getLocation().getY() + ingredient_panel.getHeight() + 15));
		getContentPane().add(ingredient_buttons[0]);
		
		ingredient_buttons[1] = new RestaurantButton("Eliminar", ActorStruct.COOK);
		ingredient_buttons[1].setActionCommand("delete_ingredient");
		ingredient_buttons[1].setLocation((int)(ingredient_buttons[0].getLocation().getX() + ingredient_buttons[0].getWidth() + 30), (int)(ingredient_panel.getLocation().getY() + ingredient_panel.getHeight() + 15));
		getContentPane().add(ingredient_buttons[1]);
		

		
	}


public JTextArea getAdditional_info() {
		return additional_info;
	}
	public void setAdditionalInfo(JTextArea info) {
		this.additional_info = info;
	}
	public JTextField[] getNewPlateFields() {
		return new_plate_fields;
	}
	public void setNewPlateFields(JTextField[] fields) {
		this.new_plate_fields = new_plate_fields;
	}
	public JTextField[] getNewIngredientFields() {
		return new_ingredient_fields;
	}
	public void setNewIngredientFields(JTextField[] fields) {
		this.new_ingredient_fields = fields;
	}
	public JTable getIngredientTable() {
		return ingredient_table;
	}
	public void setIngredientTable(JTable table) {
		this.ingredient_table = table;
	}
	public JTable getPlateTable() {
		return plate_table;
	}
	public void setPlateTable(JTable table) {
		this.plate_table = table;
	}
	public JPanel getNewPlatePanel() {
		return new_plate_panel;
	}
	public void setNewPlatePanel(JPanel panel) {
		this.new_plate_panel = panel;
	}
	public JPanel getNewIngredientPanel() {
		return new_ingredient_panel;
	}
	public void setNewIngredientPanel(JPanel panel) {
		this.new_ingredient_panel = panel;
	}
	public JComboBox<String>[] getIngredientBoxes() {
		return ingredient_boxes;
	}
	public void setIngredientBoxes(JComboBox<String>[] boxes) {
		this.ingredient_boxes = boxes;
	}
	public JCheckBox[] getDiseases() {
		return diseases;
	}
	public void setDiseases(JCheckBox[] diseases) {
		this.diseases = diseases;
	}

@Override
public void setController(EventListener controller) {
	this.action_buttons[0].addActionListener((ActionListener) controller);
	this.action_buttons[1].addActionListener((ActionListener) controller);
	this.action_buttons[2].addActionListener((ActionListener) controller);
	this.action_buttons[3].addActionListener((ActionListener) controller);
	
	this.plate_buttons[0].addActionListener((ActionListener) controller);
	this.plate_buttons[1].addActionListener((ActionListener) controller);
	
	this.ingredient_buttons[0].addActionListener((ActionListener) controller);
	this.ingredient_buttons[1].addActionListener((ActionListener) controller);
	
	this.new_ingredient_button.addActionListener((ActionListener) controller);
	this.new_plate_button.addActionListener((ActionListener) controller);
	
	this.start_service_button.addActionListener((ActionListener) controller);
	
	this.plate_table.getSelectionModel().addListSelectionListener((ListSelectionListener) controller);
	this.ingredient_table.getSelectionModel().addListSelectionListener((ListSelectionListener) controller);
	this.image_button.addActionListener((ActionListener) controller);
	
}


public JButton getImageButton() {
	return image_button;
}
public void setImageButton(JButton image_button) {
	this.image_button = image_button;
}
@Override
public void update(Observable arg0, Object arg1) {
	String s = (String) arg1;
	switch (s){
	case "add_ingredient":
	case "remove_ingredient":
		updateIngredientTable((ObservableRestaurant) arg0);
		break;
	case "add_plate":
	case "remove_plate":
		updatePlateTable((ObservableRestaurant) arg0);
		updateIngredientTable((ObservableRestaurant) arg0);
		break;
	}
	
}
/**
 * Inicializa la tabla de platos y la actualiza
 * @param r el observable del restaurante
 */
public void updatePlateTable(ObservableRestaurant r){
	((DefaultTableModel)plate_table.getModel()).setRowCount(0);
	HashMap<String, Plate> list = r.getPlates();
	List<Plate> plates = new ArrayList<Plate>((Collection<Plate>) list.values());
	if (plates.size()> 0){
		for(int i = 0; i < plates.size(); i++){
		 ((DefaultTableModel)plate_table.getModel()).setRowCount(plate_table.getModel().getRowCount() + 1);
			plate_table.setValueAt(plates.get(i).getName(), i, 0);
			if(plates.get(i).isBlocked())
				plate_table.setValueAt("Sí", i, 1);
			else
				plate_table.setValueAt("No", i, 1);
			

		}
	}
}

/**
 * Inicializa la tabla de ingredientes y la actualiza
 * @param r el observable del restaurante
 */
public void updateIngredientTable(ObservableRestaurant r){
	((DefaultTableModel)ingredient_table.getModel()).setRowCount(0);
	HashMap<String, Ingredient> ing = ((ObservableResCook)r).getIngredients();
	List<Ingredient> ingredients = new ArrayList<Ingredient>((Collection<Ingredient>) ing.values());
//	JOptionPane.showMessageDialog(null, ingredients.get(0).getName());
	//if (ingredients.size()>0){
		for(int i = 0; i < ingredients.size(); i++){
			((DefaultTableModel)ingredient_table.getModel()).setRowCount(ingredient_table.getModel().getRowCount() + 1);
			ingredient_table.setValueAt(ingredients.get(i).getName(), i, 0);
			ingredient_table.setValueAt(ingredients.get(i).getAmount(), i, 1);
		}
//	}
		
for (int i = 0; i<ingredient_boxes.length; i++){
	ingredient_boxes[i].removeAllItems();
	ingredient_boxes[i].insertItemAt("", 0);
	ingredient_boxes[i].setSelectedIndex(0);
	for (int j = 0; j<ingredients.size(); j++){
		ingredient_boxes[i].insertItemAt(ingredients.get(j).getName(), j+1);
	}
}
}

}
