
package view;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.EventListener;
import java.util.Observable;

import javax.swing.*;

import model.ActorStruct;
import controller.EatWindowController;
import controller.FavWindowController;
import controller.FirstWindowController;
import controller.TheWindowController;
/**
 *Ventana que te permite elegir varias opciones a la hora de acabar la comida: 
 *pedir más, efectuar el pago o llamar al camarero.
 */
public class EatWindow extends TheWindow{
	
	
	/**
	   * Array que contiene los botones del menu principal.
	   */
  private JButton[] action_buttons;

  /**
   * Array que contiene las etiquetas (Titulo, pedir más, pedir cuenta y llamar camarero).
   */
  private JLabel[] eat_labels;
  /**
   * Variable que almacena un contador para actualizar la ventana cada 5 segundos.
   * */
  private Timer timer;
	
  /**
   * Constructor de EatWindow. Inicializa sus valores.
   * @param actor Actor requerido.
   * @param controller el controlador de la ventana
   * */
  public EatWindow(ActorStruct actor, TheWindowController controller) {
	  super(actor, controller);
	  super.setController(controller);
	  getTitleLabel().setText("Que aproveche");
	  paintEatWindow();
	  
	  setController(controller);
	  controller.setView(this);
	  
	  timer = new Timer (5*1000, controller);
	  timer.setActionCommand("timer");
	  timer.start();
	  repaint();
  }
  
  public Timer getTimer() {
	return timer;
}

  /**
   * Añade los botones al panel principal.
   * @param actor tipo de usuario.
   * */
public void addMenuButtons(ActorStruct actor){
	  action_buttons = new RestaurantButton[5];
	  action_buttons[0] = new RestaurantButton("Perfil", ActorStruct.CLIENT);
	  action_buttons[0].setActionCommand("profile_button");
	  getMenuPanel().add(action_buttons[0]);
	  

	  /*action_buttons[1] = new RestaurantButton("Desconectar", ActorStruct.CLIENT);
	  action_buttons[1].setActionCommand("disconnect_button");
	  getMenuPanel().add(action_buttons[1]);*/
  }
  
/**
 * Añade las etiquetas y los botones de la ventana. Inicializa el contenido.
 * */
  public void paintEatWindow()
  {  
	  eat_labels = new JLabel[5];
	  
	  eat_labels[0]= new JLabel("Disfrute su comida");
	  eat_labels[0].setBounds(getWidth()/2 - 170, getHeight()/4 , 340, 50);
	  eat_labels[0].setFont(new Font("Trebuchet MS", Font.PLAIN, 40));
	  eat_labels[0].setHorizontalAlignment(JLabel.CENTER);
	  getContentPane().add(eat_labels[0]);
	  
	  eat_labels[1] = new JLabel("¿Tiene más hambre?");
	  eat_labels[1].setBounds(getWidth()/2 - 300, getHeight()/3 + 50 ,300, 50);
	  eat_labels[1].setFont(new Font("Trebuchet MS", Font.PLAIN, 30));
	  eat_labels[1].setHorizontalAlignment(JLabel.CENTER);
	  getContentPane().add(eat_labels[1]);
	  
	  eat_labels[2] = new JLabel("¿Ha terminado?");
	  eat_labels[2].setBounds(getWidth()/2 - 300, getHeight()/3 + 150, 300, 42);
	  eat_labels[2].setFont(new Font("Trebuchet MS", Font.PLAIN, 30));
	  eat_labels[2].setHorizontalAlignment(JLabel.CENTER);
	  getContentPane().add(eat_labels[2]);
	  
	  eat_labels[3] = new JLabel("¿Necesita ayuda?");
	  eat_labels[3].setBounds(getWidth()/2 - 300, getHeight()/3 + 250, 300, 36);
	  eat_labels[3].setFont(new Font("Trebuchet MS", Font.PLAIN, 30));
	  eat_labels[3].setHorizontalAlignment(JLabel.CENTER);
	  getContentPane().add(eat_labels[3]);
	  
	  action_buttons[2] = new RestaurantButton("Pedir más", ActorStruct.CLIENT);
	  action_buttons[2].setBounds(getWidth()/2 + 50, getHeight()/3 + 50 , 220, 60);
	  action_buttons[2].setActionCommand("order_button");
	  getContentPane().add(action_buttons[2]);
	  
	  action_buttons[3] = new RestaurantButton("La cuenta", ActorStruct.CLIENT);
	  action_buttons[3].setBounds(getWidth()/2 + 50, getHeight()/3 + 150 , 220, 60);
	  action_buttons[3].setActionCommand("account_button");
	  getContentPane().add(action_buttons[3]);
	  
	  action_buttons[4] = new RestaurantButton("Llamar al camarero", ActorStruct.CLIENT);
	  action_buttons[4].setBounds(getWidth()/2 + 50, getHeight()/3 + 250 , 220, 60);
	  action_buttons[4].setActionCommand("call_button");
	  getContentPane().add(action_buttons[4]);
	
  }
  
  


@Override
public void setController(EventListener controller) {
	
	
	  action_buttons[0].addActionListener((ActionListener)controller);
	  //action_buttons[1].addActionListener((ActionListener)controller);
	  action_buttons[2].addActionListener((ActionListener)controller);
	  action_buttons[3].addActionListener((ActionListener)controller);
	  action_buttons[4].addActionListener((ActionListener)controller);
	
	
}

@Override
public void update(Observable arg0, Object arg1) {
	// TODO Auto-generated method stub
	
}

}
