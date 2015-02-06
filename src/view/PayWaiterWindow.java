
package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Label;
import java.awt.SystemColor;
import java.text.DateFormatSymbols;
import java.util.*;

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
 * Ventana que permite al camarero ver el pedido y proceder al pago y generación de factura
 */
public class PayWaiterWindow extends TheWindow{
	 /**
	   * Boton que te manda a la ventana anterior.
	   */
	  private JButton back_button;

	  /**
	   * Boton que presiona el camarero para proceder al pago del cliente
	   */
	  private JButton paid_button;

	  /**
	   * Botón de eliminar
	   */
	  private JButton delete_button;
	  /**
	   * Panel de pedidos.
	   */
  private JPanel order_panel;
  /**
   * Tabla con los pedidos realizados.
   */
  private JTable order_table;
  /**
   * Constructor de la ventana. Inicializa la ventana.
   * @param table mesa.
   * @param controller Controlador de la ventana.
   */
  public PayWaiterWindow(Station table, TheWindowController controller) {
	  	super(ActorStruct.WAITER, controller);
		super.setController(controller);
		getTitleLabel().setText("Pedido en proceso");

		createPanels();

		//loadOrder(table.getOrder()); //comentar para ver la ventana
			
		createButtons(table.getStatus());
		
		setController(controller);
		controller.setView(this);	
		((PayWaiterWindowController)controller).setTable(table);
		initPayWaiter(table);
		
		this.repaint();
  }
  @Override
  public void addMenuButtons (ActorStruct actor){		
		this.back_button = new RestaurantButton("Volver", actor);
		this.back_button.setActionCommand("back_button");
		getMenuPanel().add(this.back_button);
		
	}
	/**
	 * Crea el los paneles
	 */
	public void createPanels(){
		createOrderPanel();
	}
	/**
	 * Crea el panel de pedidos y le añade la tabla
	 */
	public void createOrderPanel(){
		order_panel = new JPanel();
		order_panel.setBorder(new LineBorder(new Color(102, 153, 102), 10));
		order_panel.setBounds(70, 170, getWidth()-140, 350);
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
		((DefaultTableModel)order_table.getModel()).setColumnIdentifiers(new String[] {"Nombre", "Precio/Descuento"});
		order_table.getColumnModel().getColumn(0).setPreferredWidth((int)(order_table.getWidth()/2));
		order_table.getColumnModel().getColumn(1).setPreferredWidth((int)(order_table.getWidth()/2));
		order_table.setFillsViewportHeight(true);
		order_table.setBackground(new Color(152, 251, 152));
		
		JScrollPane order_jpane = new JScrollPane(order_table);
		order_jpane.setBounds(10, 10,(int) (order_panel.getWidth()- 20), (int) (order_panel.getHeight()- 20));
		order_jpane.setHorizontalScrollBar(null);
		order_panel.add(order_jpane);
		getContentPane().add(order_panel);
	}
	/**
	 * Crea los botones de la ventana
	 * @param status estado de la mesa
	 */
	public void createButtons(StatusStruct status){
		delete_button = new RestaurantButton("Borrar selección", ActorStruct.WAITER);
		delete_button.setActionCommand("delete_button");
		delete_button.setSize(new Dimension(delete_button.getWidth()*2,delete_button.getHeight()));
		delete_button.setLocation((int)(order_panel.getLocation().getX() + order_panel.getWidth()/2 - delete_button.getWidth() - delete_button.getWidth()/2), (int)(order_panel.getLocation().getY() + order_panel.getHeight() + 15));
		getContentPane().add(delete_button);
		
		if (status.equals(StatusStruct.PAYING))
			paid_button= new RestaurantButton("Generar factura", ActorStruct.WAITER);
		else paid_button= new RestaurantButton("Guardar", ActorStruct.WAITER);
		paid_button.setActionCommand("paid_button");
		paid_button.setSize(new Dimension(paid_button.getWidth()*2,paid_button.getHeight()));
		paid_button.setLocation((int)(order_panel.getLocation().getX() + order_panel.getWidth()/2 + paid_button.getWidth()/2), (int)(order_panel.getLocation().getY() + order_panel.getHeight() + 15));
		getContentPane().add(paid_button);
		
	}

@Override
public void setController(EventListener controller) {
	delete_button.addActionListener((ActionListener)controller);
	paid_button.addActionListener((ActionListener)controller);
	back_button.addActionListener((ActionListener)controller);
	//end_service_button.addActionListener((ActionListener)controller);
}

public JPanel getOrderPanel() {
	return order_panel;
}

public void setOrderPanel(JPanel order_panel) {
	this.order_panel = order_panel;
}

public JTable getOrderTable() {
	return order_table;
}

public void setOrderTable(JTable order_table) {
	this.order_table = order_table;
}

public JButton getBackButton() {
	return back_button;
}

public void setBackButton(JButton back_button) {
	this.back_button = back_button;
}

public JButton getPaidButton() {
	return paid_button;
}

public void setPaidButton(JButton paid_button) {
	this.paid_button = paid_button;
}

public JButton getDeleteButton() {
	return delete_button;
}

public void setDeleteButton(JButton delete_button) {
	this.delete_button = delete_button;
}
/**
 * Inicializa la ventana de pago
 * @param s mesa que va a pagar
 */
public void initPayWaiter(Station s){
		((DefaultTableModel)order_table.getModel()).setRowCount(0);
		Order o = s.getOrder();
		if(o.getMenuCount() > 0 || o.getPlateCount() > 0) {
			List<Menu> list = ((ArrayList<Menu>)o.getMenus());
			for(int i = 0; i < o.getMenuCount(); i++){
				((DefaultTableModel)order_table.getModel()).setRowCount(order_table.getRowCount()+1);
				order_table.setValueAt(list.get(i).getName(), i, 0);
				order_table.setValueAt(list.get(i).getPrice(), i, 1);
			}
			for(int i = o.getMenuCount(); i < (o.getMenuCount() + o.getPlateCount()); i++){
				((DefaultTableModel)order_table.getModel()).setRowCount(order_table.getRowCount()+1);
				order_table.setValueAt(list.get(i).getName(), i, 0);
				order_table.setValueAt(list.get(i).getPrice(), i, 1);
			}
		}
		
		//Etiqueta promociones
		((DefaultTableModel)order_table.getModel()).setRowCount(order_table.getRowCount()+1);
		order_table.setValueAt("Promociones:", order_table.getRowCount() -1, 0);
		
		List<Promo> promos = ((ArrayList<Promo>)o.getPromos());
		for(int i = 0; i < promos.size(); i++){
			((DefaultTableModel)order_table.getModel()).setRowCount(order_table.getRowCount()+1);
			order_table.setValueAt(promos.get(i).getName(), order_table.getRowCount()-1, 0);
			order_table.setValueAt(promos.get(i).getDiscount(), order_table.getRowCount()-1, 1);
			
		}
		((DefaultTableModel)order_table.getModel()).setRowCount(order_table.getRowCount()+1);

		order_table.setValueAt("//////////////",order_table.getRowCount()-1, 0);
		order_table.setValueAt("//////////////", order_table.getRowCount()-1, 1);
		
		((DefaultTableModel)order_table.getModel()).setRowCount(order_table.getRowCount()+1);
		order_table.setValueAt("Pedido",order_table.getRowCount()-1, 0);
		order_table.setValueAt(o.calculateGrossPrice(), order_table.getRowCount()-1, 1);
		
		((DefaultTableModel)order_table.getModel()).setRowCount(order_table.getRowCount()+1);
		order_table.setValueAt("Descuento",order_table.getRowCount()-1, 0);
		
	
		order_table.setValueAt(o.calculateDiscount(), order_table.getRowCount()-1, 1);
		
		((DefaultTableModel)order_table.getModel()).setRowCount(order_table.getRowCount()+1);
		order_table.setValueAt("Total",order_table.getRowCount() -1, 0);
		order_table.setValueAt(o.calculateNetPrice(), order_table.getRowCount()-1, 1);
		
		
	
}	
@Override
public void update(Observable arg0, Object arg1) {

}


}