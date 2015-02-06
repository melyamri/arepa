package view;

import java.awt.event.ActionListener;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.EventListener;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Observable;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import model.*;
import controller.HistoryWindowController;
import controller.StartCookWindowController;
import controller.TheWindowController;
import controller.UsersCookWindowController;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.SystemColor;

/**
* Esta clase crea la ventana del historial de la aplicación y contiene los métodos para actualizar la ventana
*
*/
public class HistoryWindow extends TheWindow {
	/**
	 * Boton para volver atras
	 */
	private JButton back_button;
	/**
	 * Tabla que contiene los platos o los ingresos
	 */
	private JTable table;
	/**
	 * Panel que contiene la tabla
	 */
	private JPanel table_panel;
	/**
	 * panel desplazable para poder navegar por los contenidos de la tabla
	 */
	JScrollPane table_scrollPane;

	/**
	   * Constructor de HelpWindow. Inicializa sus valores.
	   * @param terminal "cook" si es el historial de la cocina, y "client" si es el historial de un cliente
	   * @param actor usuario requerido.
	   * @param controller controlador de la ventana.
	   * */
	public HistoryWindow(ActorStruct actor, String terminal, TheWindowController controller) {
		super(actor, controller);
		
		super.setController(controller);
		getMenuPanel().setBackground(new Color(244, 164, 96));
		getTitleLabel().setText("Historial de pedidos");
		
		if(terminal.equals("client")){
			createTablePanel(ActorStruct.CLIENT);		
		}else if(terminal.equals("cook")){
			createTablePanel(ActorStruct.COOK);
		}
			setController(controller);	
			controller.setView(this);
	
		((HistoryWindowController) controller).initHistory(terminal);
		repaint();
	}

	@Override
	public void addMenuButtons(ActorStruct actor) {
		back_button = new RestaurantButton("Volver", actor);
		back_button.setActionCommand("back");		
		getMenuPanel().add(back_button);
	}
	
	/**
	 * Método que crea una tabla del historial y lo añade al panel
	 * @param actor tipo de usuario
	 */
	private void createTablePanel(ActorStruct actor) {
		table_panel = new JPanel();
		table_panel.setOpaque(false);
		table_panel.setBackground(new Color(245, 222, 179));
		table_panel.setBounds(140, 170, getWidth()-280, 425);	
		
		table_panel.setLayout(null);
		if(actor.equals(ActorStruct.CLIENT)){
			table_panel.setBorder(new LineBorder(new Color(240, 230, 140), 10));		
		}else{
			table_panel.setBorder(new LineBorder(SystemColor.activeCaption, 10));	
		}
		
		table = new JTable();
		table.setModel(new DefaultTableModel()
		{
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});

		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);
		((DefaultTableCellRenderer)table.getDefaultRenderer(String.class)).setHorizontalAlignment(SwingConstants.CENTER);
		((DefaultTableModel)table.getModel()).setColumnCount(3);
		((DefaultTableModel)table.getModel()).setRowCount(0);
		if(actor.equals(ActorStruct.CLIENT)){
			((DefaultTableModel)table.getModel()).setColumnIdentifiers(new String[] {"Nombre", "Precio", "Fecha"});
		}else{
			((DefaultTableModel)table.getModel()).setColumnIdentifiers(new String[] {"Fecha", "Ventas", "Información"});
		}
		table.getColumnModel().getColumn(0).setPreferredWidth((int)(table.getWidth()/2));
		table.getColumnModel().getColumn(1).setPreferredWidth((int)(table.getWidth()/5));
		table.getColumnModel().getColumn(2).setPreferredWidth((int)(table.getWidth()/4));
		table.setFillsViewportHeight(true);
		table_panel.setLayout(null);
		if(actor.equals(ActorStruct.CLIENT)){
			table.setBackground(new Color(255, 250, 205));		
		}else{
			table.setBackground(new Color(176, 224, 230));	
		}
		
		
		table.setBounds(10, 10, table_panel.getWidth()-20, 425-20);		
		
		table_scrollPane = new JScrollPane(table);
		table_scrollPane.setBackground(null);
		table_scrollPane.setAutoscrolls(true);		
		//table_scrollPane.setBackground(new Color(240, 240, 240));
		table_scrollPane.setBounds(10, 10, table_panel.getWidth()-20, 425-20);		
		table_scrollPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		table_panel.add(table_scrollPane);		
		getContentPane().add(table_panel);
	}
	


	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	@Override
	public void setController(EventListener controller) {
		
		back_button.addActionListener((ActionListener)controller);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * Actualiza la ventana inicial dado un observador y un objeto.
	 * Este método llama a su vez a otro para actualizar la tabla de promociones.
	 * @param arg0 - Observable Restaurante
	 * @param r tipo de terminal, "cook" en caso de cocinero, y "client" en caso de cliente.
	 */
	public void init(ObservableRestaurant r, String terminal){
		((DefaultTableModel)table.getModel()).setRowCount(0);
		if(terminal.equals("client")){
			
			List<Order> list = r.getStation().getActor().getLastOrders();
			for(int i = 0; i < list.size(); i++){
				((DefaultTableModel)table.getModel()).setRowCount(table.getRowCount()+1);
				table.setValueAt(list.get(i).getOrderId(), table.getRowCount()-1, 0);
				table.setValueAt(list.get(i).getPrice(), table.getRowCount()-1, 1);
				table.setValueAt(list.get(i).getDate(), table.getRowCount()-1, 2);
			}
			
		}else if(terminal.equals("cook")){
			((DefaultTableModel)table.getModel()).setRowCount(15);
			table.setValueAt("<html><b>Meses</b></html>", 0, 0);
			table.setValueAt("Enero", 1, 0);
			table.setValueAt("Febrero", 2,  0);
			table.setValueAt("Marzo", 3, 0);
			table.setValueAt("Abril", 4,  0);
			table.setValueAt("Mayo", 5, 0);
			table.setValueAt("Junio", 6,  0);
			table.setValueAt("Julio", 7, 0);
			table.setValueAt("Agosto", 8,  0);
			table.setValueAt("Septiembre", 9, 0);
			table.setValueAt("Octubre", 10,  0);
			table.setValueAt("Noviembre", 11, 0);
			table.setValueAt("Diciembre", 12,  0);
			table.setValueAt("<html><b>Total año</b></html>", 13, 0);
			table.setValueAt("<html><b>Total año anterior</b></html>", 14, 0);
			
			float[] list = r.getDatabase().getKitchenHistory();
			for(int i = 1; i < list.length; i++){
				table.setValueAt(list[i], i, 1);
			}
			table.setValueAt((int)list[0], 13, 2);
			table.setValueAt((int)(list[0]-1), 14, 2);
			
		}
	}
}

