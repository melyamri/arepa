
package view;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.EventListener;
import java.util.Observable;

import javax.swing.*;

import model.Actor;
import model.ActorStruct;
import model.SexStruct;
import controller.FirstWindowController;
import controller.ProfileWindowController;
import controller.StartCookWindowController;
import controller.TheWindowController;
/**
 * Esta clase contiene la ventana de los datos del usuario y los métodos para que estén actualizados
 * @author Grupo 9
 *
 */
public class ProfileWindow extends TheWindow{
	
	 /**
	   *  Array de etiquetas.
	   */
  private JLabel[] profile_labels;

  /**
   * Botón que va a la página anterior
   */
  private JButton back_button;

  /**
   * Campos del usuario que contiene sus datos
   */
  private JTextField[] profile_text;
  /**
   * Campos para la contraseña
   */
  private JPasswordField[] passwordField;
  /**
   * Desplegable con el Tipo de usuario
   */
  private JComboBox<String> typeactor_box;
  /**
   * Botón de guardar
   */
  private JButton save_button;
  /**
   * Desplegable con el género
   */
  private JComboBox<String> sex_box;
  /**
   * Constructor por defecto.
   * @param actor - usuario del perfil.
   * @param modifying_actor - usuario que está modificando dicho perfil.
   * @param controller - controlador de la ventana
   */
  public ProfileWindow(Actor actor, Actor modifying_actor, TheWindowController controller) {
	  super(actor.getActorClass(), controller);
	  super.setController(controller);
	  paintProfileWindow(actor.getActorClass());
	  this.getTitleLabel().setText("Perfil");
	  
	  ((ProfileWindowController)controller).setModifyingActor(modifying_actor);
	  
	  setController(controller);
	  controller.setView(this);
	  
	  //Only cook user can edit type of user
	  if(!actor.getActorClass().equals(ActorStruct.COOK)){
		  typeactor_box.setEnabled(false);
		  if (actor.getClass().equals(ActorStruct.WAITER)) 
			  typeactor_box.setSelectedIndex(1);
		  else typeactor_box.setSelectedIndex(2);
	  } 
	  //If actor is already registered, it can't 
	  if(!modifying_actor.getSurname().equals("anonymous")){
		  profile_text[0].setEditable(false);
		 ((ProfileWindowController) controller).initProfile();
	  }

	  repaint();
	 
  }
  /**
   * Pinta la ventana del usuario.
   * @param actor - tipo de actor que utiliza la aplicación
   */
  public void paintProfileWindow(ActorStruct actor)
  {
	  	int y_location = (int) this.getMenuPanel().getLocation().getY();
	  	y_location += this.getMenuPanel().getHeight();
	  	y_location += 70;
	  	this.profile_labels = new JLabel[10];
		 profile_labels[0] = new JLabel("Usuario:");
		 profile_labels[0].setBounds(getWidth()/7, y_location, getWidth()/8, 25);
		 profile_labels[0].setFont(new Font("Trebuchet MS", Font.PLAIN, 22));
		 getContentPane().add(profile_labels[0]);
		 
		 profile_labels[1] = new JLabel("Email:");
		 profile_labels[1].setBounds(getWidth()/7, y_location + 70,getWidth()/8 , 25);
		 profile_labels[1].setFont(new Font("Trebuchet MS", Font.PLAIN, 22));
		 getContentPane().add(profile_labels[1]);
		 
		 profile_labels[2] = new JLabel("Contrase\u00F1a:");
		 profile_labels[2].setBounds(getWidth()/7, y_location + 140, getWidth()/8, 25);
		 profile_labels[2].setFont(new Font("Trebuchet MS", Font.PLAIN, 22));
		 getContentPane().add(profile_labels[2]);
		 
		 profile_labels[3] = new JLabel("Repite contrase\u00F1a:");
		 profile_labels[3].setBounds(getWidth()/7, y_location + 210, getWidth()/6, 24);
		 profile_labels[3].setFont(new Font("Trebuchet MS", Font.PLAIN, 22));
		 getContentPane().add(profile_labels[3]);
		 
		 profile_labels[4] = new JLabel("Dirección:");
		 profile_labels[4].setBounds(getWidth()/7, y_location + 280, getWidth()/8, 20);
		 profile_labels[4].setFont(new Font("Trebuchet MS", Font.PLAIN, 22));
		 getContentPane().add(profile_labels[4]);
		 
		 profile_labels[5] = new JLabel("Nombre:");
		 profile_labels[5].setBounds(getWidth()/2, y_location, getWidth()/8, 36);
		 profile_labels[5].setFont(new Font("Trebuchet MS", Font.PLAIN, 22));
		 getContentPane().add(profile_labels[5]);
		 
		 profile_labels[6] = new JLabel("Apellidos:");
		 profile_labels[6].setBounds(getWidth()/2, y_location + 70, getWidth()/8, 28);
		 profile_labels[6].setFont(new Font("Trebuchet MS", Font.PLAIN, 22));
		 getContentPane().add(profile_labels[6]);
		 
		 profile_labels[7] = new JLabel("Sexo:");
		 profile_labels[7].setBounds(getWidth()/2, y_location + 140 , getWidth()/8, 25);
		 profile_labels[7].setFont(new Font("Trebuchet MS", Font.PLAIN, 22));
		 getContentPane().add(profile_labels[7]);
		 
		 profile_labels[8] = new JLabel("Teléfono:");
		 profile_labels[8].setBounds(getWidth()/2, y_location + 210, getWidth()/5, 24);
		 profile_labels[8].setFont(new Font("Trebuchet MS", Font.PLAIN, 22));
		 getContentPane().add(profile_labels[8]);
		 
		 profile_labels[9] = new JLabel("Tipo de usuario:");
		 profile_labels[9].setBounds(getWidth()/2, y_location + 280, getWidth()/7, 28);
		 profile_labels[9].setFont(new Font("Trebuchet MS", Font.PLAIN, 22));
		 getContentPane().add(profile_labels[9]);
		 
		 this.profile_text = new JTextField[6];
		 profile_text[0] = new JTextField();
		 profile_text[0].setBounds(getWidth()/3, y_location, getWidth()/7, 30);
		 getContentPane().add(profile_text[0]);
		 profile_text[0].setColumns(10);
		 
		 profile_text[1] = new JTextField();
		 profile_text[1].setBounds(getWidth()/3, y_location + 70, getWidth()/7, 30);
		 getContentPane().add(profile_text[1]);
		 profile_text[1].setColumns(10);
		 
		 this.passwordField = new JPasswordField[2];
		 passwordField[0] = new JPasswordField();
		 passwordField[0].setBounds(getWidth()/3, y_location + 140, getWidth()/7, 30);
		 getContentPane().add( passwordField[0]);
		 
		 passwordField[1] = new JPasswordField();
		 passwordField[1].setBounds(getWidth()/3, y_location + 210, getWidth()/7, 30);
		 getContentPane().add( passwordField[1]);
		 
		 profile_text[2] = new JTextField();
		 profile_text[2].setBounds(getWidth()/3, y_location + 280, getWidth()/7, 30);
		 getContentPane().add(profile_text[2]);
		 profile_text[2].setColumns(10);
		 
		 profile_text[3] = new JTextField();
		 profile_text[3].setBounds(getWidth()/2 + 250, y_location, getWidth()/7, 30);
		 getContentPane().add(profile_text[3]);
		 profile_text[3].setColumns(10);
		 
		 profile_text[4] = new JTextField();
		 profile_text[4].setBounds(getWidth()/2 + 250, y_location +70, getWidth()/7, 30);
		 getContentPane().add(profile_text[4]);
		 profile_text[4].setColumns(10);
		 
		 sex_box = new JComboBox(new String[]{"Hombre", "Mujer"});
		 sex_box.setBounds(getWidth()/2 + 250, y_location + 140, getWidth()/7, 30);
		 sex_box.setFont(new Font("Trebuchet MS", Font.PLAIN, 22));
		 getContentPane().add(sex_box);
		 
		 profile_text[5] = new JTextField(9);
		 profile_text[5].setColumns(9);
		 profile_text[5].setBounds(getWidth()/2 + 250, y_location + 210, getWidth()/7, 30);
		 //profile_text[5].setEnabled(false);
		 getContentPane().add(profile_text[5]);

		 
		 
		 typeactor_box = new JComboBox(new String[]{"Cocinero", "Camarero", "Cliente"});
		 typeactor_box.setBounds(getWidth()/2 + 250, y_location + 280, getWidth()/7, 30);
		 typeactor_box.setFont(new Font("Trebuchet MS", Font.PLAIN, 22));
		 getContentPane().add(typeactor_box);
		 
		 save_button = new RestaurantButton("Guardar", actor);
		// save_button.setBounds(getWidth()/2 - 100, 675, 163, 73);
		 save_button.setActionCommand("save");
		 save_button.setLocation(getWidth()/2 - 100, y_location + 400);
		 getContentPane().add(save_button);
		 
	
		getContentPane().setLayout(null);
  }
  
  public JComboBox<String> getTypeactorBox() {
	return typeactor_box;
}

public void setTypeactorBox(JComboBox<String> box) {
	this.typeactor_box = typeactor_box;
}

public JComboBox<String> getSexBox() {
	return sex_box;
}

public void setSexBox(JComboBox<String> box) {
	this.sex_box = box;
}
@Override
public void addMenuButtons(ActorStruct actor){

		back_button = new RestaurantButton("Volver", actor);
		back_button.setActionCommand("back");
		getMenuPanel().add(back_button);
  }

@Override
public void setController(EventListener controller) {
	save_button.addActionListener((ActionListener)controller);
	back_button.addActionListener((ActionListener)controller);
	
}


public JTextField[] getProfileText() {
	return profile_text;
}




public JPasswordField[] getPasswordField() {
	return passwordField;
}


@Override
public void update(Observable arg0, Object arg1) {
	// TODO Auto-generated method stub
	
}


}

