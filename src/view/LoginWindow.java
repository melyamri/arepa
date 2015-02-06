
package view;

import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionListener;
import java.util.EventListener;
import java.util.Observable;

import javax.swing.*;

import model.ActorStruct;
import controller.HelpWindowController;
import controller.LoginWindowController;
import controller.TheWindowController;
/**
 * Crea la ventana del Login y lo mantiene actualizadao
 *
 */
public class LoginWindow extends TheWindow{
	/**
	 * Botón de Continuar
	 */
  private JButton yes_button;
  /**
   * Botón de volver
   */

  private JButton no_button;
  /**
   * Etiqueta de nombre de usuario
   */

  private JLabel user_label;
  /**
   * Etiqueta de contraseña
   */
  private JLabel password_label;
  /**
   * Campo de usuario
   */
  private JTextField user_text;
  /**
   * Campo de contraseña
   */
  private JPasswordField password_text;

  /**
   * LoginWindow number (1 --> ¿Registrado o no?2 2-->Registrado 3-->Anónimo 4--> COOK 5--> WAITER)
   */
  private int num_loginwindow;
  /**
   * Temporizador que actualiza la ventana cada 5 segundos
   */
  private Timer timer;
  
  public Timer getTimer() {
	return timer;
}
  /**
   * Constructor de la ventana
   * @param actor tipo de actor
   * @param controller controlador de la ventana
   */
public LoginWindow(ActorStruct actor, TheWindowController controller) {
	  super(actor, controller);
	  super.setController(controller);
	  
	  getTitleLabel().setText("Bienvenido");
	  
	  switch (actor){
	  case CLIENT:num_loginwindow = 1;break;
	  case COOK: num_loginwindow = 4;break;
	  case WAITER:num_loginwindow = 5;break;
	  }
	  
	  init(num_loginwindow, controller);
	  

	  controller.setView(this);
	  timer = new Timer (20*1000, controller);
	  timer.setActionCommand("timer");
	  timer.start();
	  repaint();
	  
  }
/**
 * Incializa la ventana de login
 * @param num - numero para saber (1 --> 'Registrado o no?2 2-->Registrado 3-->Anónimo 4--> COOK 5--> WAITER)
 * @param controller - controlador de la ventana
 * @return true si se ha ejecutado con éxito
 */
  public boolean init(int num, TheWindowController controller) {

	  if (getContentPane().getComponentCount()>3)
		  removeElements();
	  switch (num){
	  case 1: paintInitialLoginWindow(); break;
	  case 2: paintRegisteredLoginWindow(); break;
	  case 3: paintAnonymousLoginWindow(); break;
	  case 4: paintRegisteredLoginWindow();  break;
	  case 5: paintRegisteredLoginWindow();  break;
	  default: return false;
	  }
	  setController(controller);
	  
	  return true;
  }
  /**
   * Elimina los elementos del panel.
   */
  public void removeElements(){
	  if (user_label != null) getContentPane().remove(user_label);
	  if (password_label != null)  getContentPane().remove(password_label);
	  if (user_text != null) getContentPane().remove(user_text);
	  if (password_text != null)  getContentPane().remove(password_text);
	  if (yes_button != null) getContentPane().remove(yes_button);
	  if (no_button != null) getContentPane().remove(no_button);
	  
  }
  /**
   * Pinta la ventana inicial del login
   */
  public void paintInitialLoginWindow(){
	  
	  
	  user_label = new JLabel("<html><b>¿Eres un usuario registrado?</b></html>");
	  user_label.setBounds((int)getWindowWidth()/2-200, (int)(getWindowHeight()*3)/7, 400, 40);
	  user_label.setFont(new Font("Trebuchet MS", Font.PLAIN, 30));
	  getContentPane().add(user_label);
	  
	  user_text = null;
	  password_text = null;
	  
	  yes_button = new RestaurantButton("Sí", ActorStruct.CLIENT);
	  yes_button.setLocation((int)(getWindowWidth()/2 - yes_button.getWidth() - 30), (int)(getWindowHeight()*4/7));
	  yes_button.setActionCommand("yes_button");
	  getContentPane().add(yes_button);
		
	  no_button = new RestaurantButton("No", ActorStruct.CLIENT);
	  no_button.setLocation((int)(getWindowWidth()/2 + 30), (int)(getWindowHeight()*4/7));
	  no_button.setActionCommand("no_button");
	  getContentPane().add(no_button);
		
	  repaint();
	  
  }
  /**
   * Pinta la ventana para el usuario registrado
   */
  public void paintRegisteredLoginWindow(){
	  user_label = new JLabel ("<html><b>Usuario:</b></html>");
	  user_label.setBounds((int)getWindowWidth()/2-(user_label.getText().length()*7) + 15, (int)(getWindowHeight()*3)/7, user_label.getText().length()*4, 30);
	  user_label.setFont(new Font("Trebuchet MS", Font.PLAIN, 22));
	 // user_label.setHorizontalTextPosition(SwingConstants.RIGHT);
	  getContentPane().add(user_label);
		
	  password_label = new JLabel ("<html><b>Contraseña:</b></html>");
	  password_label.setBounds((int)getWindowWidth()/2-(user_label.getText().length()*7) + 15, (int)(getWindowHeight()*3)/7 + 30*2, password_label.getText().length()*4, 30);
	  password_label.setFont(new Font("Trebuchet MS", Font.PLAIN, 22));
	  //password_label.setHorizontalTextPosition(SwingConstants.RIGHT);
	  getContentPane().add(password_label);
	  
	  user_text = new JTextField ("");
	  user_text.setBounds((int) (user_label.getBounds().getLocation().getX() + user_label.getBounds().getWidth() + 30), (int)(getWindowHeight()*3)/7 - 5, user_label.getText().length()*8, 30);
	  user_text.setHorizontalAlignment(JLabel.CENTER);
	  getContentPane().add(user_text);
	
	  password_text = new JPasswordField ("");
	  password_text.setBounds((int) (user_label.getBounds().getLocation().getX() + user_label.getBounds().getWidth() + 30), (int)(getWindowHeight()*3)/7 - 5+ 30*2, user_label.getText().length()*8, 30);
	  password_text.setHorizontalAlignment(JLabel.CENTER);
	  getContentPane().add(password_text);
	  
	  if (num_loginwindow == 2){
		  yes_button = new RestaurantButton("Conectar", ActorStruct.CLIENT);
		  yes_button.setLocation((int)(getWindowWidth()/2 - yes_button.getWidth() - 30), (int)(getWindowHeight()*4/7));
		  yes_button.setActionCommand("yes_button");
		  getContentPane().add(yes_button);
			
		  no_button = new RestaurantButton("Volver", ActorStruct.CLIENT);
		  no_button.setLocation((int)(getWindowWidth()/2 + 30), (int)(getWindowHeight()*4/7));
		  no_button.setActionCommand("no_button");
		  getContentPane().add(no_button);
	  } else{
		  ActorStruct actor_struct = ActorStruct.COOK;
		  if (num_loginwindow == 5){
			  actor_struct = ActorStruct.WAITER;
		  }
		  yes_button = new RestaurantButton("Conectar", actor_struct);
		  yes_button.setLocation((int)(getWindowWidth()/2 - yes_button.getWidth()/2), (int)(getWindowHeight()*4/7));
		  yes_button.setActionCommand("yes_button");
		  getContentPane().add(yes_button);
			
		  no_button = null;
	  }
		
	  repaint();
  }
  /**
   * Pinta la ventana para el usuario anonimo.
   */
  public void paintAnonymousLoginWindow(){
	  user_label = new JLabel ("<html><b>Usuario:</b></html>");
	  user_label.setBounds((int)getWindowWidth()/2-(user_label.getText().length()*7) + 15, (int)(getWindowHeight()*3)/7 + 30, user_label.getText().length()*4, 30);
	  user_label.setFont(new Font("Trebuchet MS", Font.PLAIN, 22));
	 // user_label.setHorizontalTextPosition(SwingConstants.RIGHT);
	  getContentPane().add(user_label);
		
	  password_label = null;
	  
	  user_text = new JTextField ("");
	  user_text.setBounds((int) (user_label.getBounds().getLocation().getX() + user_label.getBounds().getWidth() + 30), (int)(getWindowHeight()*3)/7 + 30, user_label.getText().length()*8 - 5, 30);
	  user_text.setHorizontalAlignment(JLabel.CENTER);
	  getContentPane().add(user_text);
	
	  password_text = null;

	  yes_button = new RestaurantButton("Conectar", ActorStruct.CLIENT);
	  yes_button.setLocation((int)(getWindowWidth()/2 - yes_button.getWidth() - 30), (int)(getWindowHeight()*4/7));
	  yes_button.setActionCommand("yes_button");
	  getContentPane().add(yes_button);
			
	  no_button = new RestaurantButton("Volver", ActorStruct.CLIENT);
	  no_button.setLocation((int)(getWindowWidth()/2 + 30), (int)(getWindowHeight()*4/7));
	  no_button.setActionCommand("no_button");
	  getContentPane().add(no_button);
		
	  repaint();
  }

@Override
public void setController(EventListener controller) {
	
	yes_button.addActionListener((ActionListener)controller);
	if (no_button != null) no_button.addActionListener((ActionListener)controller);
	
}


public JButton getYesButton() {
	return yes_button;
}

public void setYesButton(JButton yes_button) {
	this.yes_button = yes_button;
}

public JButton getNoButton() {
	return no_button;
}

public void setNoButton(JButton no_button) {
	this.no_button = no_button;
}

public JLabel getUserLabel() {
	return user_label;
}

public void setUserLabel(JLabel user_label) {
	this.user_label = user_label;
}

public JLabel getPasswordLabel() {
	return password_label;
}

public void setPasswordLabel(JLabel password_label) {
	this.password_label = password_label;
}

public JTextField getUserText() {
	return user_text;
}

public void setUserText(JTextField user_text) {
	this.user_text = user_text;
}

public JPasswordField getPasswordText() {
	return password_text;
}

public void setPasswordText(JPasswordField password_text) {
	this.password_text = password_text;
}

public int getNumLoginWindow() {
	return num_loginwindow;
}

public void setNumLoginWindow(int num_loginwindow) {
	this.num_loginwindow = num_loginwindow;
}


@Override
public void update(Observable arg0, Object arg1) {
	// TODO Auto-generated method stub
	
}
}
