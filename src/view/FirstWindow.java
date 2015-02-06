
package view;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.EventListener;
import java.util.GregorianCalendar;
import java.util.Observable;


import javax.swing.*;

import model.*;


import controller.LoginWindowController;
import controller.StartCookWindowController;
import controller.TheWindowController;


import controller.FirstWindowController;




/**
 * Esta clase crea la ventana inicial de la aplicación
 *
 */
public class FirstWindow extends TheWindow{
	/**
	   * Botón para conectar
	   */
  private JButton connect_button, end;

  /**
   * Etiqueta para la ip
   */
  private JLabel ip_label;

  /**
   * Etiqueta para la clase actor
   */
  private JLabel actorclass_label;

  /**
   * Campo para escribir la ip
   */
  private JTextField ip_text;

  /**
   * Desplegable para elegir el tipo de actor
   */
  private JComboBox actorclass_combobox;



  public FirstWindow(ActorStruct actor, TheWindowController controller) {
	  super(actor, controller);
	  super.setController(controller);
	  getTitleLabel().setText("Configuración inicial");
	  paintFirstWindow();
	  setController(controller);
  }

  
  /**
   * Constructor para la ventana incial.
   * @param actor - tipo de actor que abre la aplicación.
   * @param controller - controlador de la ventana
   */
  public void paintFirstWindow()
  {
	  ip_label = new JLabel("<html><b>Nombre/IP ordenador cocina</b></html>");
	  ip_label.setBounds((int)(getWindowWidth()/2) - 200, (int)(getWindowHeight()*3)/7, 400, 40);
	  ip_label.setFont(new Font("Trebuchet MS", Font.PLAIN, 22));
	  ip_label.setHorizontalAlignment(JLabel.CENTER);
	  getContentPane().add(ip_label);
	  
	  
	  ip_text = new JTextField ("");
	  ip_text.setBounds((int)getWindowWidth()/2-100, (int)(getWindowHeight()*3)/7 - 5+ 30*2, 200, 30);
	  ip_text.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
	  ip_text.setHorizontalAlignment(JLabel.CENTER);
	  getContentPane().add(ip_text);
	  
	  
	  actorclass_label = new JLabel("<html><b>Este ordenador es:</b></html>");
	  actorclass_label.setBounds((int)(getWindowWidth()/2-200), (int)((getWindowHeight()*3/7) + 95), 400, 40);
	  actorclass_label.setFont(new Font("Trebuchet MS", Font.PLAIN, 22));
	  actorclass_label.setHorizontalAlignment(JLabel.CENTER);
	  getContentPane().add(actorclass_label);
	  
	  
	  String[] items = new String[] {"Cliente", "Cocina", "Camarero"};
	  actorclass_combobox = new JComboBox(items);
	  actorclass_combobox.setSelectedIndex(0);
	  actorclass_combobox.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
	  actorclass_combobox.setBounds((int)getWindowWidth()/2-75, (int)((getWindowHeight()*3/7) + 145), 150, 30);
	  ((JLabel)actorclass_combobox.getRenderer()).setHorizontalAlignment(JLabel.CENTER);//.setHorizontalAlignment(JLabel.CENTER);	  
	  getContentPane().add(actorclass_combobox);
	  
	  connect_button = new RestaurantButton("Conectar", ActorStruct.COOK);
	  connect_button.setLocation((int)(getWindowWidth()/2 - connect_button.getWidth()/2), (int)((getWindowHeight()*3/7) + 195));
	  connect_button.setActionCommand("connect_button");
	  getContentPane().add(connect_button);
	  
	  repaint();

	  
  }
  @Override
  public void addMenuButtons (ActorStruct actor){
		
		end = new RestaurantButton("Desconectar", actor);
		end.setActionCommand("disconnect");

		getMenuPanel().add(end);
		
		
	}

@Override
public void setController(EventListener controller) {
	connect_button.addActionListener((ActionListener)controller);
	end.addActionListener((ActionListener)controller);
		
}


@Override
public void update(Observable arg0, Object arg1) {
	// TODO Auto-generated method stub
	
}


public JTextField getIpText() {
	return ip_text;
}



public void setIpText(JTextField ip_text) {
	this.ip_text = ip_text;
}



public JComboBox getActorclassCombobox() {
	return actorclass_combobox;
}



public void setActorclassCombobox(JComboBox actorclass_combobox) {
	this.actorclass_combobox = actorclass_combobox;
}


}
