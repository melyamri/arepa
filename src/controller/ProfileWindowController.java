
package controller;

import java.awt.event.ActionEvent;
import java.text.*;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;

import model.*;
import view.*;
/**
 * Controlador de la ventana ProfileWindow
 * @author Grupo 9
 *
 */
public class ProfileWindowController extends TheWindowController{
	/**
	 * Actor que modifica el perfil
	 */
	Actor modifying_actor;
	
	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		switch (e.getActionCommand()){
		
		case "back": getWindow().dispose();
							setWindow(null); break;
		case "save":	{	
			String user, email, passwd, repasswd, address, name, surname, sex, phone, actortype;
			user = getWindow().getProfileText()[0].getText();
			email = getWindow().getProfileText()[1].getText();
			passwd = new String(getWindow().getPasswordField()[0].getPassword());
			repasswd = new String(getWindow().getPasswordField()[1].getPassword());
			address = getWindow().getProfileText()[2].getText();
			name = getWindow().getProfileText()[3].getText();
			surname = getWindow().getProfileText()[4].getText();
			
			
			SexStruct s = null;
			if(getWindow().getSexBox().getSelectedIndex() == 0)
			 s = SexStruct.MALE;
			 else s = SexStruct.FEMALE;
			
			int phone_int = 0;
			phone = getWindow().getProfileText()[5].getText(); 
			try{
			phone_int = Integer.parseInt(phone);
			if (!surname.equalsIgnoreCase("anonymous")){
				Actor actor = null;
				switch(getWindow().getTypeactorBox().getSelectedIndex()){
					case 0: actor = new Actor(user, ActorStruct.COOK, passwd, name, surname, phone_int, address, s, email);break;
					case 1: actor = new Actor(user, ActorStruct.WAITER, passwd, name, surname, phone_int, address, s, email);break;
					case 2: actor = new Actor(user, ActorStruct.CLIENT, passwd, name, surname, phone_int, address, s, email); break;
				}
				
				if(user.equals("") || email.equals("") || passwd.equals("") || repasswd.equals("")	
						|| address.equals("") || name.equals("") || surname.equals("") || phone.equals("")){
					showErrorPane("Rellene todos los campos", "Error");	
				}else{
					if (passwd.equals(repasswd)){
						if(phone.length() == 9){
							if(restaurant.getStation().getActor().getSurname().equals("anonymous")){
								
								if (getRestaurant().registerUser(actor)){
									getRestaurant().getDatabase().removeActor(modifying_actor.getUser());
									showErrorPane("Usuario registrado correctamente", "Hecho");
									getRestaurant().getStation().setActor(actor);
									getRestaurant().getDatabase().updateStation(getRestaurant().getStation());
			
								}else{
									showErrorPane("Error al registrar el usuario", "Error");
								}
							}else{
								if (getRestaurant().modifyUser(actor)){
									showErrorPane("Usuario modificado correctamente", "Hecho");
								}else{
									showErrorPane("Error al modificar el usuario", "Error");
								}
							}
						}else {
							showErrorPane("El teléfono que ha introducido no tiene el formato correcto", "Error");
						}
					
					}else{
						showErrorPane("Las contraseñas no coinciden", "Error");
					}
				
				}
			} else	showErrorPane("El apellido no es válido", "Error");
								
			}catch (NumberFormatException ex){
				showErrorPane("El teléfono que ha introducido no tiene el formato correcto", "Error");
			}
			break;
		}
		}
	}
	
	@Override
	public void setView (TheWindow window){
		setWindow((ProfileWindow) window);
	}
	
	
	
	public void setModifyingActor(Actor actor) {
		this.modifying_actor = actor;
	}

	@Override
	public ProfileWindow getWindow(){
		return (ProfileWindow)super.getWindow();
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	public void initProfile() {
		getWindow().getProfileText()[0].setText(modifying_actor.getUser());
		getWindow().getProfileText()[1].setText(modifying_actor.getEmail());
		getWindow().getProfileText()[2].setText(modifying_actor.getStreet());
		getWindow().getProfileText()[3].setText(modifying_actor.getName());
		getWindow().getProfileText()[4].setText(modifying_actor.getSurname());
		if (modifying_actor.getSex() == SexStruct.MALE)
			getWindow().getSexBox().setSelectedIndex(0);
		else getWindow().getSexBox().setSelectedIndex(1);
		if (modifying_actor.getActorClass() == ActorStruct.COOK)
			getWindow().getTypeactorBox().setSelectedIndex(0);
		else if (modifying_actor.getActorClass() == ActorStruct.WAITER)
				getWindow().getTypeactorBox().setSelectedIndex(2);
			else  getWindow().getTypeactorBox().setSelectedIndex(1);
	}

}

