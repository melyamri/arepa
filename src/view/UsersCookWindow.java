
package view;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import model.Actor;
import model.ActorStruct;
import model.ObservableRestaurant;
import model.ResCook;
import model.Station;
import controller.MenuCookWindowController;
import controller.TheWindowController;
import controller.UsersCookWindowController;
import java.awt.Font;
import javax.swing.table.*;
/**
 * Esta clase crea la ventana de usuarios para el cocinero y contiene los métodos que actualiza la ventana.
 * @author Grupo 9
 *
 */
public class UsersCookWindow extends TheWindow{
	/**
	   * Botón para volver a otra página
	   */
  private JButton back_button;

  /**
   * Panel del usuarios
   */
  private JPanel user_panel;
  /**
   * Tabla de usuarios
   */
  private JTable user_table;


  /**
   * Etiqueta de usuario
   */
  private JLabel user_label;

  /**
   * Campo de texto de nombre de usuario
   */
  private JTextField user_text;

  /**
   * Array de botones que contiene: modifica, borra y desconecta
   */
  private JButton[] user_buttons;
  /**
   * Temporizador que actualiza la ventana cada 5 segundos
   */
  Timer timer;



  public Timer getTimer() {
	return timer;
}
/**
 * Constructor de la clas, inicializa sus atributos
 * @param controller controlador de la ventana
 */
public UsersCookWindow(TheWindowController controller){
	  super(ActorStruct.COOK, controller);
	  super.setController(controller);
	  getTitleLabel().setText("Usuarios conectados");
	  createUserPanel();
	  createUserOptions();
		setController(controller);
		controller.setView(this);
		((UsersCookWindowController)controller).load();
		((UsersCookWindowController)controller).initTables(); 
		timer = new Timer (5*1000, controller);
		timer.setActionCommand("timer");
		timer.start();
		
		this.repaint();
		
  }
/**
 * Crea el panel de usuarios
 */
  public void createUserPanel(){
		user_panel = new JPanel();
		user_panel.setBackground(new Color(176, 224, 230));
		user_panel.setBorder(new LineBorder(new Color(153, 180, 209), 10));
		user_panel.setBounds(70 , 170, getWidth() - 140, 425);
		user_panel.setLayout(null);
		
		user_table = new JTable();
		user_table.setName("user_table");
		user_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		user_table.setBounds(0, 0,(int) (user_panel.getWidth()), (int) (user_panel.getHeight()));
		user_table.setModel(new DefaultTableModel()
		{
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
	
		user_table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		user_table.getTableHeader().setReorderingAllowed(false);
		user_table.getTableHeader().setResizingAllowed(false);
		((DefaultTableCellRenderer)user_table.getDefaultRenderer(String.class)).setHorizontalAlignment(SwingConstants.CENTER);
		((DefaultTableModel)user_table.getModel()).setColumnCount(3);
		((DefaultTableModel)user_table.getModel()).setRowCount(0);
		((DefaultTableModel)user_table.getModel()).setColumnIdentifiers(new String[] {"Nombre", "Mesa", "Tipo"});
		user_table.getColumnModel().getColumn(0).setPreferredWidth((int)(user_table.getWidth()/3));
		user_table.getColumnModel().getColumn(1).setPreferredWidth((int)(user_table.getWidth()/3));
		user_table.getColumnModel().getColumn(2).setPreferredWidth((int)(user_table.getWidth()/3));
		user_table.setFillsViewportHeight(true);
		user_table.setBackground(new Color(176, 224, 230));
		
		JScrollPane user_jpane = new JScrollPane(user_table);
		user_jpane.setBounds(10, 10,(int) (user_table.getWidth()- 20), (int) (user_table.getHeight()- 20));
		user_jpane.setHorizontalScrollBar(null);
		user_panel.add(user_jpane);
		getContentPane().add(user_panel);
		
		
	}
  @Override
  public void addMenuButtons (ActorStruct actor){
		
		back_button = new RestaurantButton("Volver", actor);
		back_button.setActionCommand("back");
		getMenuPanel().add(this.back_button);
		
		
	}
  /**
   * Crea en la ventana las opciones de usuario.
   */
  public void createUserOptions(){
	  	user_label = new JLabel("Usuario:");
	  	user_label.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
	  	user_label.setBounds(145, 622, 69, 18);
		getContentPane().add(user_label);

		this.user_buttons = new RestaurantButton[3];
		
		user_buttons[0] = new RestaurantButton("Modificar", ActorStruct.COOK);
		user_buttons[0].setActionCommand("modify");
		user_buttons[0].setBounds(145, 682, user_panel.getWidth()/6, 23);
		getContentPane().add(user_buttons[0]);
		
		user_buttons[1] = new RestaurantButton("Borrar", ActorStruct.COOK);
		user_buttons[1].setActionCommand("delete");
		user_buttons[1].setBounds(user_buttons[0].getWidth() + 150, 682, user_panel.getWidth()/6, 23);
		getContentPane().add(user_buttons[1]);
		
		user_buttons[2] = new RestaurantButton("Desconectar", ActorStruct.COOK);
		user_buttons[2].setFont(new Font("Trebuchet MS", Font.BOLD, 18));
		user_buttons[2].setActionCommand("disconnect");
		user_buttons[2].setLocation(user_panel.getWidth() - user_panel.getWidth()/6, 620);
		getContentPane().add(user_buttons[2]);
		
		user_text = new JTextField();
		user_text.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		user_text.setBounds(145, 651, 2* user_panel.getWidth()/6 + 5, 20);
		getContentPane().add(user_text);
		user_text.setColumns(10);
  }
@Override
public void setController(EventListener controller) {
	this.back_button.addActionListener((ActionListener) controller);
	user_buttons[0].addActionListener((ActionListener) controller);
	user_buttons[1].addActionListener((ActionListener) controller);
	user_buttons[2].addActionListener((ActionListener) controller);

	
	
}
	public JPanel getUserPanel() {
		return user_panel;
	}

	public void setUserPanel(JPanel panel) {
		this.user_panel = panel;
	}

	public JTable getUserTable() {
		return user_table;
	}

	public void setUserTable(JTable table) {
		this.user_table = table;
	}

	public JTextField getUserText() {
		return user_text;
	}

	public void setUserText(JTextField text) {
		this.user_text = text;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		String s = (String) arg1;
		switch (s){
		case "change_status":
			updateUserTable((ObservableRestaurant) arg0);
			break;
		case "connect":
			updateUserTable((ObservableRestaurant) arg0);
			break;
		}
		
	}
	/**
	 * Actualiza la tabla de usuarios de la ventana.
	 * @param r - ObservableRestaurant 
	 */
	public void updateUserTable(ObservableRestaurant r){
		((DefaultTableModel)user_table.getModel()).setRowCount(0);
		HashMap<String, Station> list = ((ResCook)r).getAllStations();
		List<Station> stations = new ArrayList<Station>((Collection<Station>) list.values());
		if (stations.size()> 0){
			String s = "Cocinero";
			ActorStruct as = ActorStruct.COOK;
			int count = 0;
			for (int j = 0; j < 3; j++){
				for(int i = 0; i < stations.size(); i++){
					if (stations.get(i).getActor()!= null && stations.get(i).getActor().getActorClass() == as){
						((DefaultTableModel)user_table.getModel()).setRowCount(count+1);
						 user_table.setValueAt(stations.get(i).getActor().getUser(), count, 0);
						 user_table.setValueAt(stations.get(i).getStationId(), count, 1);	
						 user_table.setValueAt(s, count, 2);
						 count++;
					}
				}
				if (j == 0) {
					as = ActorStruct.WAITER;
					s = "Camarero";
				}
				if (j == 1) {
					as = ActorStruct.CLIENT;
					s = "Cliente";
				}
			}
		}
	}
}
