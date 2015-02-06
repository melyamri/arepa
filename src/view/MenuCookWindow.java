
package view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;

import javax.swing.*;

import model.ActorStruct;
import model.Menu;
import model.ObservableResCook;
import model.ObservableRestaurant;
import model.Plate;
import model.Promo;
import controller.MenuCookWindowController;
import controller.StartCookWindowController;
import controller.TheWindowController;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Rectangle;
import java.awt.ComponentOrientation;
import java.awt.event.ActionListener;
/**
 * Ventana que controla el menu de cocina. 
 */
public class MenuCookWindow extends TheWindow{
	
	/**
	 *  Etiquetas que aparecen al crear un nuevo menú.
	 */
	private JLabel[] new_menu_labels = new JLabel[5];
	/**
	 * Etiquetas que aparecen al crear una nueva promoción
	 */
	private JLabel[] new_promo_labels;
	
	/**
	 * Paneles con el menu y la tabla del menu.
	 */
	private JPanel menu_panel;
	private JTable menu_table;
	/**
	 * Panel con las promociones.
	 */
	private JPanel promo_panel;
	private JTable promo_table;
	  
	/**
	 * Panel el cual contiene los nuevos menus y las nuevas promociones.
	 */
	private JPanel new_menu_panel;
	private JPanel new_promo_panel;
	/**
	 * Desplgable con el plato al que se aplica la promoción
	 */
	private JComboBox<String> new_promo_combobox;
	/**
	 * Desplgable con los platos del menú
	 */
	private JComboBox<String>[] new_menu_comboboxes;
	/**
	 * Campos de texto para los datos de los menús
	 */
	private JTextField[] new_menu_fields = new JTextField[4];
	/**
	 * Campos de texto para los datos de una nueva promoción
	 */
	private JTextField[] new_promo_fields;
	/**
	 * Botones de la ventana
	 */
	private RestaurantButton[] menu_buttons = new RestaurantButton[3];
	private RestaurantButton[] promo_buttons = new RestaurantButton[3];
	private RestaurantButton new_promo_button;
	private RestaurantButton new_menu_button;
	private JButton back_button;

	/**
	 * Constructor de MenuCookWindow. Inicializa sus valores.
	 * @param controller controlador de la ventana
	 */
	public MenuCookWindow(TheWindowController controller) {
		super(ActorStruct.COOK, controller);
		this.getTitleLabel().setText("Menús y promociones");
		this.getHelpButton().setVisible(true);
		
		this.createPanels();
		this.createButtons();

		this.setController(controller);	
		controller.setView(this);
		
		((MenuCookWindowController)controller).load();
		((MenuCookWindowController)controller).initTables(); 
		repaint();
		
	}

@Override
 public void addMenuButtons(ActorStruct actor) {
		this.back_button = new RestaurantButton("Volver",actor);
		this.back_button.setActionCommand("back_button");		
		getMenuPanel().add(back_button);		
	}
/**
 * Metodo que crea los paneles que componen la ventana.
 */
	private void createPanels(){
		createMenuJPanel();
		createPromoJPanel();
		createNewPromoJPanel();
		createNewMenuJPanel();
	}
	
	/**
	 * Crea un panel con la tabla de las promociones.
	 */
	public void createPromoJPanel(){
		promo_panel = new JPanel();
		promo_panel.setBackground(new Color(176, 224, 230));
		promo_panel.setBorder(new LineBorder(SystemColor.activeCaption, 10));
		promo_panel.setBounds((int)(getTitleLabel().getBounds().getMaxX()- 400 ) , 170, 400, 425);
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

		promo_table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		promo_table.getTableHeader().setReorderingAllowed(false);
		promo_table.getTableHeader().setResizingAllowed(false);
		((DefaultTableCellRenderer)promo_table.getDefaultRenderer(String.class)).setHorizontalAlignment(SwingConstants.CENTER);
		((DefaultTableModel)promo_table.getModel()).setColumnCount(3);
		((DefaultTableModel)promo_table.getModel()).setRowCount(0);
		((DefaultTableModel)promo_table.getModel()).setColumnIdentifiers(new String[] {"Promo", "Descuento", "Plato"});
		promo_table.getColumnModel().getColumn(0).setPreferredWidth((int)(promo_table.getWidth()/3));
		promo_table.getColumnModel().getColumn(1).setPreferredWidth((int)(promo_table.getWidth()/3));
		promo_table.getColumnModel().getColumn(2).setPreferredWidth((int)(promo_table.getWidth()/3));
		promo_table.setFillsViewportHeight(true);
		promo_table.setBackground(new Color(176, 224, 230));
		
		
		JScrollPane promo_jpane = new JScrollPane(promo_table);
		promo_jpane.setBounds(10, 10,(int) (promo_panel.getWidth()- 20), (int) (promo_panel.getHeight()- 20));
		promo_jpane.setHorizontalScrollBar(null);
		promo_jpane.getViewport().setBackground(new Color(176, 224, 230));
		promo_panel.add(promo_jpane);
		getContentPane().add(promo_panel);
	}
	/**
	 * The Jpanel that contains the table with the menus
	 */
	public void createMenuJPanel(){
		menu_panel = new JPanel();
		menu_panel.setBackground(new Color(176, 224, 230));
		menu_panel.setBorder(new LineBorder(new Color(153, 180, 209), 10));
		menu_panel.setBounds(70 , 170, 400, 425);
		menu_panel.setLayout(null);
		
		menu_table = new JTable();
		menu_table.setName("menu_table");
		menu_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		menu_table.setBounds(0, 0,(int) (menu_panel.getWidth()), (int) (menu_panel.getHeight()));
		menu_table.setModel(new DefaultTableModel()
		{
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});

		menu_table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		menu_table.getTableHeader().setReorderingAllowed(false);
		menu_table.getTableHeader().setResizingAllowed(false);
		((DefaultTableCellRenderer)menu_table.getDefaultRenderer(String.class)).setHorizontalAlignment(SwingConstants.CENTER);
		((DefaultTableModel)menu_table.getModel()).setColumnCount(2);
		((DefaultTableModel)menu_table.getModel()).setRowCount(0);
		((DefaultTableModel)menu_table.getModel()).setColumnIdentifiers(new String[] {"Menú", "Bloqueado"});
		menu_table.getColumnModel().getColumn(0).setPreferredWidth((int)(menu_table.getWidth()/2));
		menu_table.getColumnModel().getColumn(1).setPreferredWidth((int)(menu_table.getWidth()/2));
		menu_table.setFillsViewportHeight(true);
		menu_table.setBackground(new Color(176, 224, 230));
		
		JScrollPane menu_jpane = new JScrollPane(menu_table);
		menu_jpane.setBounds(10, 10,(int) (menu_panel.getWidth()- 20), (int) (menu_panel.getHeight()- 20));
		menu_jpane.setHorizontalScrollBar(null);
		menu_jpane.getViewport().setBackground(new Color(176, 224, 230));
		menu_panel.add(menu_jpane);
		
		getContentPane().add(menu_panel);
		
	}
	
	/**
	 * Crea el panel que contiene una tabla con los menus.
	 */
	public void createNewPromoJPanel(){
		//panel creation
		this.new_promo_panel = new JPanel();
		new_promo_panel.setBounds((int)(menu_panel.getWidth() + menu_panel.getLocation().getX()), (int)menu_panel.getLocation().getY(), (int)(promo_panel.getLocation().getX() -( menu_panel.getWidth() + menu_panel.getLocation().getX())), menu_panel.getHeight());
		new_promo_panel.setBackground(null);
		TitledBorder border = new TitledBorder("Añadir/Modificar promo");
		border.setTitleColor(Color.BLACK);
		border.setTitleFont(new Font("Trebuchet MS", Font.BOLD, 14));
		new_promo_panel.setBorder(border);
		this.new_promo_labels = new JLabel[3];
		
		//Etiqueta nombre
		new_promo_labels[0] = new JLabel("Nombre:");
		new_promo_labels[0].setForeground(Color.BLACK);
		new_promo_labels[0].setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		new_promo_labels[0].setBounds(31, 33, 65, 22);
		new_promo_panel.add(new_promo_labels[0]);
		
		//Etiqueta plato
		new_promo_labels[1] = new JLabel("Plato");
		new_promo_labels[1].setForeground(Color.BLACK);
		new_promo_labels[1].setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		new_promo_labels[1].setBounds(31, 97, 65, 22);
		new_promo_panel.add(new_promo_labels[1]);
		
		//Etiqueta Descuento
		new_promo_labels[2] = new JLabel("Descuento");
		new_promo_labels[2].setForeground(Color.BLACK);
		new_promo_labels[2].setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		new_promo_labels[2].setBounds(31, 161, 150, 22);
		new_promo_panel.add(new_promo_labels[2]);
		
		//Etiqueta %
		new_promo_labels[2] = new JLabel("%");
		new_promo_labels[2].setForeground(Color.BLACK);
		new_promo_labels[2].setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		new_promo_labels[2].setBounds(211, 188, 150, 22);
		new_promo_panel.add(new_promo_labels[2]);
		
		
		this.new_promo_fields = new JTextField[2];
		//TextField Nombre
		new_promo_fields[0] = new JTextField();
		new_promo_fields[0].setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		new_promo_fields[0].setColumns(10);
		new_promo_fields[0].setBounds(24, 66, 299, 20);
		new_promo_panel.add(new_promo_fields[0]);
		
		//Textfield Descuento
		new_promo_fields[1] = new JTextField();
		new_promo_fields[1].setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		new_promo_fields[1].setColumns(10);
		new_promo_fields[1].setBounds(24, 190, 179, 20);
		new_promo_panel.add(new_promo_fields[1]);
		
		new_promo_panel.setLayout(null);
		
		//Combobox Plato
		new_promo_combobox = new JComboBox<String>();
		new_promo_combobox.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		new_promo_combobox.setBounds(22, 131, 299, 20);
		new_promo_panel.add(new_promo_combobox);
		

		//Button añadir menu
		new_promo_button = new RestaurantButton("Añadir", ActorStruct.COOK);
		new_promo_button.setBounds(197, 391, 126, 23);
		new_promo_button.setActionCommand("new_promo");
		new_promo_panel.add(new_promo_button);
		getContentPane().add(this.new_promo_panel);
		
		new_promo_panel.setVisible(false);
	}
	
	/**
	 * Crea el panel para añadir una promoción
	 */
	public void createNewMenuJPanel(){
		this.new_menu_panel = new JPanel();
		new_menu_panel.setVisible(false);
		
		TitledBorder border = new TitledBorder("Añadir/Modificar menu");
		new_menu_panel.setBounds((int)(menu_panel.getWidth() + menu_panel.getLocation().getX()), (int)menu_panel.getLocation().getY(), (int)(promo_panel.getLocation().getX() -( menu_panel.getWidth() + menu_panel.getLocation().getX())), menu_panel.getHeight());
		new_menu_panel.setBackground(null);
		border.setTitleColor(Color.BLACK);
		border.setTitleFont(new Font("Trebuchet MS", Font.BOLD, 14));
		new_menu_panel.setBorder(border);
		this.new_menu_panel.setLayout(null);
		this.new_menu_labels = new JLabel[6];
		
		
		
		//Button añadir menu
		new_menu_button = new RestaurantButton("Añadir", ActorStruct.COOK);
		new_menu_button.setBounds(197, 391, 126, 23);
		new_menu_button.setActionCommand("new_menu");
		new_menu_panel.add(new_menu_button);
		
		
		this.new_menu_labels = new JLabel[3];
		
		this.new_menu_labels[0] = new JLabel("Nombre:");
		new_menu_labels[0].setBounds(25, 30, 99, 20);
		new_menu_labels[0].setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		new_menu_panel.add(new_menu_labels[0]);
		
		
		new_menu_labels[1] = new JLabel("Precio:");
		new_menu_labels[1].setBounds(new_menu_labels[0].getWidth() + 100, 30, 99, 20);
		new_menu_labels[1].setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		new_menu_panel.add(new_menu_labels[1]);
		
		this.new_menu_fields = new JTextField[2];
		
		new_menu_fields[0] = new JTextField();
		new_menu_fields[0].setBounds(25, (int)(new_menu_labels[0].getLocation().getY() + 25), 126, 23);
		new_menu_fields[0].setFont(new Font("Tahoma", Font.PLAIN, 14));
		new_menu_fields[0].setColumns(10);
		new_menu_panel.add(new_menu_fields[0]);
		
		new_menu_fields[1] = new JTextField();
		new_menu_fields[1].setBounds((int)new_menu_labels[1].getLocation().getX(), (int)(new_menu_labels[1].getLocation().getY() + 25), 126, 23);
		new_menu_fields[1].setFont(new Font("Tahoma", Font.PLAIN, 14));
		new_menu_fields[1].setColumns(10);
		new_menu_panel.add(new_menu_fields[1]);
		
		new_menu_labels[2] = new JLabel("Platos:");
		new_menu_labels[2].setBounds(25, (int)(new_menu_labels[0].getLocation().getY() + 75), 126, 23);
		new_menu_labels[2].setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		new_menu_panel.add(new_menu_labels[2]);
		
		this.new_menu_comboboxes = new JComboBox[8];
		
		new_menu_comboboxes[0] = new JComboBox<String>();
		new_menu_comboboxes[0].setBounds(25, (int)(new_menu_labels[0].getLocation().getY() + 100), 126, 23);
		new_menu_panel.add(new_menu_comboboxes[0]);
		
		new_menu_comboboxes[1] = new JComboBox<String>();
		new_menu_comboboxes[1].setBounds((int)new_menu_labels[1].getLocation().getX(), (int)(new_menu_labels[1].getLocation().getY() + 100), 126, 23);
		new_menu_panel.add(new_menu_comboboxes[1]);
		
		new_menu_comboboxes[2] = new JComboBox<String>();
		new_menu_comboboxes[2].setBounds(25, (int)(new_menu_labels[0].getLocation().getY() + 125), 126, 23);
		new_menu_panel.add(new_menu_comboboxes[2]);
		
		new_menu_comboboxes[3] = new JComboBox<String>();
		new_menu_comboboxes[3].setBounds((int)new_menu_labels[1].getLocation().getX(), (int)(new_menu_labels[1].getLocation().getY() + 125), 126, 23);
		new_menu_panel.add(new_menu_comboboxes[3]);
		
		new_menu_comboboxes[4] = new JComboBox<String>();
		new_menu_comboboxes[4].setBounds(25, (int)(new_menu_labels[0].getLocation().getY() + 150), 126, 23);
		new_menu_panel.add(new_menu_comboboxes[4]);
		
		new_menu_comboboxes[5] = new JComboBox<String>();
		new_menu_comboboxes[5].setBounds((int)new_menu_labels[1].getLocation().getX(), (int)(new_menu_labels[1].getLocation().getY() + 150), 126, 23);
		new_menu_panel.add(new_menu_comboboxes[5]);
		
		new_menu_comboboxes[6] = new JComboBox<String>();
		new_menu_comboboxes[6].setBounds(25, (int)(new_menu_labels[0].getLocation().getY() + 175), 126, 23);
		new_menu_panel.add(new_menu_comboboxes[6]);
		
		new_menu_comboboxes[7] = new JComboBox<String>();
		new_menu_comboboxes[7].setBounds((int)new_menu_labels[1].getLocation().getX(), (int)(new_menu_labels[1].getLocation().getY() + 175), 126, 23);
		new_menu_panel.add(new_menu_comboboxes[7]);
		
		
		getContentPane().add(this.new_menu_panel);
	}
	/**
	 * Crea los diferentes botones de la ventana
	 */
	public void createButtons(){
	
		
		menu_buttons[0] = new RestaurantButton("Añadir", ActorStruct.COOK);
		menu_buttons[0].setLocation((int)(menu_panel.getLocation().getX() + menu_panel.getWidth()/2 - menu_buttons[0].getWidth() - 15), (int)(menu_panel.getLocation().getY() + menu_panel.getHeight() + 15));
		menu_buttons[0].setActionCommand("add_menu");
		getContentPane().add(menu_buttons[0]);
		
		menu_buttons[1] = new RestaurantButton("Eliminar", ActorStruct.COOK);
		menu_buttons[1].setActionCommand("delete_menu");
		menu_buttons[1].setLocation((int)(menu_buttons[0].getLocation().getX() + menu_buttons[0].getWidth() + 30), (int)(menu_panel.getLocation().getY() + menu_panel.getHeight() + 15));
		getContentPane().add(menu_buttons[1]);
		
		
		
		promo_buttons[0] = new RestaurantButton("Añadir", ActorStruct.COOK);
		promo_buttons[0].setActionCommand("add_promo");
		promo_buttons[0].setLocation((int)(promo_panel.getLocation().getX() + promo_panel.getWidth()/2 - promo_buttons[0].getWidth() - 15), (int)(promo_panel.getLocation().getY() + promo_panel.getHeight() + 15));
		getContentPane().add(promo_buttons[0]);
		
		promo_buttons[1] = new RestaurantButton("Eliminar", ActorStruct.COOK);
		promo_buttons[1].setActionCommand("delete_promo");
		promo_buttons[1].setLocation((int)(promo_buttons[0].getLocation().getX() + promo_buttons[0].getWidth() + 30), (int)(promo_panel.getLocation().getY() + promo_panel.getHeight() + 15));
		getContentPane().add(promo_buttons[1]);
		
	
		
	}
	
	public JComboBox<String>[] getNewMenuComboBoxes(){
		return this.new_menu_comboboxes;
	}
	public JTextField[] getNewMenuTextFields(){
		return this.new_menu_fields;
	}
	public JComboBox<String> getNewPromoComboBox(){
		return this.new_promo_combobox;
	}
	public JTextField[] getNewPromoTextFields(){
		return this.new_promo_fields;
	}
	public JTable getPromoJTable(){
		return this.promo_table;
	}
	public JTable getMenuJTable(){
		return this.menu_table;
	}
	public void setMenuJTable(JTable table){
		this.menu_table = table;
	}
	public void setPromoJTable(JTable table){
		this.promo_table = table;
	} 
	public JPanel getNewMenuJPanel(){
		return this.new_menu_panel;
	}
	public JPanel getNewPromoJPanel(){
		return this.new_promo_panel;
	}

	@Override
	public void setController(EventListener controller) {
		super.setController(controller);
		
		this.menu_buttons[0].addActionListener((ActionListener) controller);
		this.menu_buttons[1].addActionListener((ActionListener) controller);
		
		
		this.promo_buttons[0].addActionListener((ActionListener) controller);
		this.promo_buttons[1].addActionListener((ActionListener) controller);
		
		
		this.new_promo_button.addActionListener((ActionListener) controller);
		
		this.new_menu_button.addActionListener((ActionListener) controller);
		
		this.back_button.addActionListener((ActionListener) controller);
		
		this.menu_table.getSelectionModel().addListSelectionListener((ListSelectionListener) controller);
		
		this.promo_table.getSelectionModel().addListSelectionListener((ListSelectionListener) controller);
	}

	/**
	 * Actualiza la tabla de menús.
	 * @param r el restaurante
	 */
	public void updateMenuTable(ObservableRestaurant r){

		((DefaultTableModel)menu_table.getModel()).setRowCount(0);
		HashMap<String, Menu> list = r.getMenus();
		List<Menu> menus = new ArrayList<Menu>((Collection<Menu>) list.values());
		if (menus.size()> 0){
			for(int i = 0; i < menus.size(); i++){
			 ((DefaultTableModel)menu_table.getModel()).setRowCount(menu_table.getModel().getRowCount() + 1);
			 menu_table.setValueAt(menus.get(i).getName(), i, 0);
				if(menus.get(i).isBlocked())
					menu_table.setValueAt("Sí", i, 1);
				else
					menu_table.setValueAt("No", i, 1);
			}
		}
	}
	/**
	 * Actualiza la tabla de promociones.
	 * @param r
	 */
	public void updatePromoTable(ObservableRestaurant r){

		((DefaultTableModel)promo_table.getModel()).setRowCount(0);
		HashMap<String, Promo> prom = r.getPromos();
		List<Promo> promos = new ArrayList<Promo>((Collection<Promo>) prom.values());
	//	JOptionPane.showMessageDialog(null, ingredients.get(0).getName());
		//if (ingredients.size()>0){
		for(int i = 0; i < promos.size(); i++){
			((DefaultTableModel)promo_table.getModel()).setRowCount(promo_table.getModel().getRowCount() + 1);
			promo_table.setValueAt(promos.get(i).getName(), i, 0);
			promo_table.setValueAt(promos.get(i).getDiscount(), i, 1);
			promo_table.setValueAt(promos.get(i).getPlate().getName(), i, 2);
		}
	}
	

	@Override
	public void update(Observable arg0, Object arg1) {
		
		String s = (String) arg1;
		switch (s){
		case "add_menu":
		case "remove_menu":
			updateMenuTable((ObservableRestaurant) arg0);
			break;
		case "add_promo":
		case "remove_promo":
			updatePromoTable((ObservableRestaurant) arg0);
			break;
		}
		
	}
}
