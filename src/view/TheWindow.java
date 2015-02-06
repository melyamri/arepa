
package view;

import javax.swing.*;

import com.sun.media.sound.Toolkit;
import model.*;
import controller.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

/**
 * Ventana principal de la aplicación, todas las demás ventanas heredan de ella. Tiene las medidas de
 * todas las ventanas, el botón de ayuda y el logo, así como el constructor del panel principal.
 * Cada una de las ventanas hijas debe reimplementar algunos métodos según los botones que utilice.
 * @author Grupo 9
 *
 */
public abstract class TheWindow extends JFrame implements ViewInterface{
	
	/**
	   * Altura de la ventana.
	   */
	  private double window_height;
	  /**
	   * Ancho de la ventana.
	   */
	  private double window_width;

	  /**
	   * Logo de la aplicacion.
	   */
	  private ImageIcon logo;
	  /**
	   * Panel principal.
	   */
	  private JPanel menu_panel;
	  /**
	   * Boton que te muestra la ventana de ayuda.
	   */
	  private JButton help_button;
	  /**
	   * Etiqueta que muestra el titulo de la pantalla.
	   */
	  private JLabel title_label;
	  
	  /**
	   * Ventana de llamada al camarero
	   */
	  private JFrame callwaiter_frame;
	  /**
	   * Botón de llamar al camarero
	   */
	  private JButton call_button;
	  /**
	   * Etiqueta de llamar al camarero
	   */
	  private JLabel call_label;


	  /**
	   * Constructor de la ventana. Inicializa la ventana.
	   * @param actor tipo de usuario que usa la ventana.
	   * @param controller Controlador de la ventana.
	   */
	public TheWindow(ActorStruct actor, TheWindowController controller) {
		Dimension a = getToolkit().getScreenSize(); // Screen size
		window_height = a.getHeight();
		window_width = a.getWidth();
		
		title_label = new JLabel("Window without title");
		
		JLabel label = new JLabel();
		this.logo = new ImageIcon(TheWindow.class.getResource("/imagenes/logo.png"));
		label.setBounds(70, 20, 150, 140);
		logo = new ImageIcon(logo.getImage().getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_DEFAULT));
		label.setIcon(logo);
		
		getContentPane().add(label);
		
		getContentPane().setLayout(null);
		switch (actor){
		case CLIENT:	getContentPane().setBackground(new Color(244, 164, 96)); break;
		case COOK:		getContentPane().setBackground(new Color(70, 150, 180)); break;
		case WAITER:	getContentPane().setBackground(new Color(46, 139, 87)); break;
			
		}
		call_button = new RestaurantButton("", ActorStruct.CLIENT);	
		createHelpButton(actor);
				
		setAlwaysOnTop(true);
		setUndecorated(true);
				
		setSize(a);
		
		setResizable(false);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setVisible(true);
		
		addTitleLabel();
		createMenuPanel(actor);
	}
	
	/**
	 * Crea el panel de llamada al camarero al que luego pueden llamar el resto de ventanas
	 * 
	 * @param paying indica si la llamada es para efectuar el pago de un pedido
	 */
	public void createCallWaiterPanel(boolean paying){
		this.setEnabled(false);
		this.callwaiter_frame = new JFrame();
		callwaiter_frame.setBounds((int)(getWindowWidth()/4), (int)(getWindowHeight()/4), (int)(getWindowWidth()/2), (int)(getWindowHeight()/4));
	//	callwaiter_panel.setVisible(true);
		//callwaiter_panel.setAlwaysOnTop(true);
		callwaiter_frame.setUndecorated(true);
		callwaiter_frame.setAlwaysOnTop(true);
				
		//callwaiter_frame.getContentPane().setBackground(new Color(244, 164, 96));
		callwaiter_frame.getContentPane().setLayout(null);
		callwaiter_frame.setResizable(false);
		callwaiter_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		callwaiter_frame.setVisible(true);
		
		
		JPanel callwaiter_panel = new JPanel();
		callwaiter_panel.setLayout(null);
		callwaiter_panel.setBackground(new Color(255, 199, 138));
		callwaiter_panel.setBounds(0, 0, callwaiter_frame.getWidth(), callwaiter_frame.getHeight());
		callwaiter_panel.setBorder(new LineBorder(Color.black));
		callwaiter_frame.add(callwaiter_panel);
		
		
		call_label = new JLabel("Llamando, espere por favor.");
		call_label.setFont(new Font("Trebuchet MS", Font.BOLD, 30));
		call_label.setBounds(callwaiter_panel.getWidth()/4, 0, callwaiter_panel.getWidth(), 40);
		callwaiter_panel.add(call_label);
		
		if (!paying){
			call_button.setText("Cancelar");
			call_button.setLocation(callwaiter_panel.getWidth()/2 - call_button.getWidth()/2, callwaiter_panel.getHeight()*2/3);
			call_button.setActionCommand("cancel_call");
			callwaiter_panel.add(call_button);
		}
	}
	
	/**
	 * Elimina el panel de llamar a camarero
	 */
	public void destroyCallWaiterPanel(){	
		callwaiter_frame.dispose();
		callwaiter_frame = null;
		this.setEnabled(true);
		this.setVisible(true);
		}
	
	public JFrame getCallWaiterFrame() {
		return callwaiter_frame;
	}


	/**
	 * Crea el boton de ayuda que se va a usar en cada ventana.
	 * @param actor tipo de usuario que usa la ventana.
	 */
	public void createHelpButton(ActorStruct actor){
		help_button = new JButton("");
		help_button.setSize(100, 100);
		help_button.setLocation(20, (int)(window_height - 120));
		
		 ImageIcon button_main = null;
		 ImageIcon button_rollover = null;
		 ImageIcon button_pressed = null;
		
		switch (actor){
		case CLIENT :
			button_main = new ImageIcon(TheWindow.class.getResource("/imagenes/help_client.png"));
			button_rollover = new ImageIcon(TheWindow.class.getResource("/imagenes/help_client_rollover.png"));
			button_pressed = new ImageIcon(TheWindow.class.getResource("/imagenes/help_client_pressed.png"));
			break;
		case COOK: 
			button_main = new ImageIcon(TheWindow.class.getResource("/imagenes/help_cook.png"));
			button_rollover = new ImageIcon(TheWindow.class.getResource("/imagenes/help_cook_rollover.png"));
			button_pressed = new ImageIcon(TheWindow.class.getResource("/imagenes/help_cook_pressed.png"));
			break;
		case WAITER:
			button_main = new ImageIcon(TheWindow.class.getResource("/imagenes/help_waiter.png"));
			button_rollover = new ImageIcon(TheWindow.class.getResource("/imagenes/help_waiter_rollover.png"));
			button_pressed = new ImageIcon(TheWindow.class.getResource("/imagenes/help_waiter_pressed.png"));
			break;
			
		}
		
		help_button.setIcon(button_main);
		help_button.setRolloverIcon(button_rollover);
		help_button.setPressedIcon(button_pressed);
		help_button.setContentAreaFilled(false);
		help_button.setBorderPainted(false);
		help_button.setBackground(null);
		help_button.setActionCommand("help_button");
		
		
		getContentPane().add(help_button);
		
		
		
	}
	/**
	 * Crea la etiqueta que contendra el titulo de la ventana.
	 */
	public void addTitleLabel() {
		title_label.setForeground(new Color(255, 255, 255));
		title_label.setFont(new Font("Trebuchet MS", Font.BOLD, 34));
		title_label.setBounds((int)(window_width - (50 + title_label.getText().length()*35)), 20, title_label.getText().length()*35, 60);
		title_label.setHorizontalAlignment(JLabel.RIGHT);
		getContentPane().add(title_label);
		title_label.repaint();
		
	}
	
	
	/**
	 * Crea el menú principal y llama al método de insertar los botones en él
	 * 
	 * @param actor tipo de usuario 
	 */
	public void createMenuPanel(ActorStruct actor) {
		menu_panel = new JPanel();
		menu_panel.setBackground(null);		
		getContentPane().add(menu_panel);
		menu_panel.setLayout(null);
		addMenuButtons(actor);
		repaintMenuPanel();

	}
	
	/**
	 * Método para dibujar los botones en el panel principal,
	 * cada una de las ventanas hija debe implementar este métodos según los botones que tenga
	 */
	public void addMenuButtons (ActorStruct actor){
		
	}
	/**
	  * Establece la posicion del panel y sus botones.
	  */
	public void repaintMenuPanel(){
		menu_panel.setLocation((int)(title_label.getBounds().getMaxX()) - (155*menu_panel.getComponentCount()), 80);
		menu_panel.setSize(155*menu_panel.getComponentCount(), 50);
		for (int i =0; i<menu_panel.getComponentCount();i++){
			menu_panel.getComponent(i).setBounds(i*155, 0, 155, 50);	
		}
		
	}


@Override
public abstract void update(Observable arg0, Object arg1);

@Override
public void setController(EventListener controller) {
	help_button.addActionListener((ActionListener)controller);
	call_button.addActionListener((ActionListener)controller);
}


//Gets & Sets
public double getWindowHeight() {
	return window_height;
}


public void setWindowHeight(double window_height) {
	this.window_height = window_height;
}


public double getWindowWidth() {
	return window_width;
}


public void setWindowWidth(double window_width) {
	this.window_width = window_width;
}


public ImageIcon getLogo() {
	return logo;
}


public void setLogo(ImageIcon logo) {
	this.logo = logo;
}


public JPanel getMenuPanel() {
	return menu_panel;
}


public void setMenuPanel(JPanel menu_panel) {
	this.menu_panel = menu_panel;
}


public JButton getHelpButton() {
	return help_button;
}


public void setHelpButton(JButton help_button) {
	this.help_button = help_button;
}


public JLabel getTitleLabel() {
	return title_label;
}


public void setTitleLabel(JLabel title_label) {
	this.title_label = title_label;
}

public JLabel getCallLabel() {
	return call_label;
}


public void setCallLabel(JLabel call_label) {
	this.call_label = call_label;
}

public JButton getCallButton() {
	return call_button;
}


public void setCallButton(JButton call_button) {
	this.call_button = call_button;
}
}
