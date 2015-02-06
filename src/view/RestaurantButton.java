package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import model.ActorStruct;


/**
 *Clase utilizada para crear todos los botones de la aplicación. Tiene diferentes imágenes dependiendo del actor y el Estado.
 *@author Grupo 9
 */

public class RestaurantButton extends JButton {
	/**
	 * Imagen por defecto del botón
	 */
	 private ImageIcon button_main;
	 /**
	  * Imagen que aparece al pasar por encima al ratón
	  */
	 private ImageIcon button_rollover;
	 /**
	  * Imagen que aparece al pulsar el botón
	  */
	 private ImageIcon button_pressed;
	 
	 /**
	  * El constructor de botones. Los colores del botón dependen de la ActorStruct especificada por el actor.
		 * @param text - Texto mostrado en el botón.
		 * @param actor - Tipo de usuario.
		 */
	
	public RestaurantButton(String text, ActorStruct actor){
		
		switch (actor){
		case CLIENT :
			this.button_main = new ImageIcon(TheWindow.class.getResource("/imagenes/client.png"));
			this.button_rollover = new ImageIcon(TheWindow.class.getResource("/imagenes/client.png"));
			this.button_pressed = new ImageIcon(TheWindow.class.getResource("/imagenes/client_pressed.png"));
			break;
		case COOK: 
			this.button_main = new ImageIcon(TheWindow.class.getResource("/imagenes/cook.png"));
			this.button_rollover = new ImageIcon(TheWindow.class.getResource("/imagenes/cook.png"));
			this.button_pressed = new ImageIcon(TheWindow.class.getResource("/imagenes/cook_pressed.png"));
			break;
		case WAITER:
			this.button_main = new ImageIcon(TheWindow.class.getResource("/imagenes/waiter.png"));
			this.button_rollover = new ImageIcon(TheWindow.class.getResource("/imagenes/waiter.png"));
			this.button_pressed = new ImageIcon(TheWindow.class.getResource("/imagenes/waiter_pressed.png"));
			break;		
		}
		
		
		setIcon(button_main);
		setRolloverIcon(button_rollover);
		setPressedIcon(button_pressed);
		setContentAreaFilled(false);
		//setBorderPainted(false);
		setText(text);
		setHorizontalTextPosition(SwingConstants.CENTER);
		
		setBounds(0,0,150,50);
		//setPreferredSize(new Dimension(150,50));
		setFont(new Font("Trebuchet MS", Font.BOLD, 20));
		setForeground(new Color(80, 80, 80));
		setBackground(null);
	}


}
