
package controller;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;

import model.*;
import view.*;
/**
 * Controlador de la ventana LoginWindow
 * @author Grupo 9
 *
 */
public class LoginWindowController extends TheWindowController{

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		switch (e.getActionCommand()){
		case "yes_button": {
					switch (getWindow().getNumLoginWindow()){
					case 1: {					
							getWindow().setNumLoginWindow(2);
							getWindow().init(2, this);
							break;
							}
					case 2:	{
							OrderWindowController controller = new OrderWindowController();
							String user = getWindow().getUserText().getText();
							String password = new String(getWindow().getPasswordText().getPassword());
							if (user.equals("") || getWindow().getPasswordText().getPassword().length == 0)
								showErrorPane("Por favor, introduzca todos los datos", "Error");
							else if (getRestaurant().getDatabase().isServiceStarted()){
								if (getRestaurant().login(user, password)){
									getRestaurant().getStation().setOrder(new Order(0, 0));
									getRestaurant().setStatus(StatusStruct.LOGGED);
									getWindow().getTimer().stop();
									controller.setRestaurant(getRestaurant());
									controller.setView(new OrderWindow(getRestaurant().getStation().getActor().getActorClass(),controller));
									controller.setObserver();
									getWindow().dispose();
								}
								else showErrorPane("Datos incorrectos", "Error");
							} else showErrorPane("Servicio no iniciado", "Error");
							break;}
					case 3: {
							OrderWindowController controller = new OrderWindowController();
							String user = getWindow().getUserText().getText();
							if (user.equals("")) showErrorPane("Por favor, introduzca todos los datos", "Error");
							else if (getRestaurant().getDatabase().isServiceStarted()){
								if (getRestaurant().anonymousLogin(user)){
									getRestaurant().getStation().setOrder(new Order(0, 0));
									getRestaurant().setStatus(StatusStruct.LOGGED);
									getWindow().getTimer().stop();
									controller.setRestaurant(getRestaurant());
									controller.setView(new OrderWindow(getRestaurant().getStation().getActor().getActorClass(),controller));
									controller.setObserver();
									getWindow().dispose();
								}	else showErrorPane("Datos incorrectos", "Error");
							}else showErrorPane("Servicio no iniciado", "Error");
							break;}
					case 4: {
							StartCookWindowController controller = new StartCookWindowController();
							String user = getWindow().getUserText().getText();
							String password = new String(getWindow().getPasswordText().getPassword());
							if (user.equals("") || getWindow().getPasswordText().getPassword().length == 0) JOptionPane.showMessageDialog(null, "Por favor, introduzca todos los datos");
							else{
								if (getRestaurant().login(user,password)){
									getWindow().getTimer().stop();
										controller.setRestaurant(getRestaurant());
										controller.setView(new StartCookWindow(controller));
										controller.setObserver();
										getWindow().dispose();
									}
									else showErrorPane("Datos incorrectos", "Error");
							}
							break;}
					case 5: {
							MainWaiterWindowController controller = new MainWaiterWindowController();
							String user = getWindow().getUserText().getText();
							String password = new String(getWindow().getPasswordText().getPassword());
							if (user.equals("") || getWindow().getPasswordText().getPassword().length == 0) JOptionPane.showMessageDialog(null, "Por favor, introduzca todos los datos");
							else if (getRestaurant().getDatabase().isServiceStarted()){
								if (getRestaurant().login(user,password)){
									getWindow().getTimer().stop();
									controller.setRestaurant(getRestaurant());
									controller.setView(new MainWaiterWindow(controller));
									controller.setObserver();
									getWindow().dispose();
								} else showErrorPane("Datos incorrectos", "Error");
							}else showErrorPane("Servicio no iniciado", "Error");
							break;}
					default: break;
					}
					break;
		}
			
		case "no_button":{
					switch (((LoginWindow) getWindow()).getNumLoginWindow()){
					case 1: {
						((LoginWindow) getWindow()).setNumLoginWindow(3);
						((LoginWindow) getWindow()).init(3, this);
						break;
					}
					case 2:	{
						((LoginWindow) getWindow()).setNumLoginWindow(1);
						((LoginWindow) getWindow()).init(1, this);
						break;
					}
					case 3:{
						((LoginWindow) getWindow()).setNumLoginWindow(1);
						((LoginWindow) getWindow()).init(1, this);
						break;
					}
					default: break;
					}
					break;
		}
							
		case "timer":{
			System.out.println("Client Login Timer Activated");
			Station s = getRestaurant().getDatabase().getStation(getRestaurant().getStation().getStationId());
			if (s == null){
				//showErrorPane("Cierre de sistema", "Error");
				getWindow().getTimer().stop();
				System.exit(0);
			}			
			break;
		}
		}
		
	}
	@Override
	public LoginWindow getWindow (){
		return (LoginWindow)super.getWindow();
	}
	@Override
	public void setView (TheWindow window){
		setWindow((LoginWindow) window);
	}
	@Override
	public void setRestaurant (Restaurant r){
		super.setRestaurant(r);
	}
	@Override
	public Restaurant getRestaurant (){
		return super.getRestaurant();
	}
	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
