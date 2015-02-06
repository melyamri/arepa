
package controller;

import java.awt.event.*;
import javax.swing.event.ListSelectionEvent;
import model.*;
import view.*;
/**
 * Controlador de la ventana EatWindow
 * @author
 *
 */
public class EatWindowController extends TheWindowController{

	
@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		switch(e.getActionCommand())
		{
		case "profile_button":{

			ProfileWindowController controller = new ProfileWindowController();
			controller.setRestaurant(getRestaurant());
			controller.setView(new ProfileWindow(restaurant.getStation().getActor(), restaurant.getStation().getActor(), controller));
			controller.setObserver();
			break;
		}
		case "order_button":{
			//OrderWindow
			getRestaurant().setStatus(StatusStruct.LOGGED);
			OrderWindowController controller = new OrderWindowController();
			controller.setRestaurant(getRestaurant());
			controller.setView(new OrderWindow(getRestaurant().getStation().getActor().getActorClass(), controller));
			controller.setObserver();
			getWindow().dispose();
			break;
		}
		case "account_button":
			//Panel de llamando a camarero 
			getRestaurant().setStatus(StatusStruct.PAYING);
			getWindow().createCallWaiterPanel(true);
			break;
		case "call_button":
			getRestaurant().getStation().setStatus(StatusStruct.CALLING);
			getRestaurant().getDatabase().updateStation(getRestaurant().getStation());
			getWindow().createCallWaiterPanel(false);
			break;
		case "timer":{
			System.out.println("Client Eat Timer Activated");
			Station s = getRestaurant().getDatabase().getStation(getRestaurant().getStation().getStationId());
			if (s == null){
				getWindow().getTimer().stop();
				System.exit(0);
			} else if (!s.getStatus().equals(StatusStruct.CALLING) && !s.getStatus().equals(StatusStruct.PAYING)){
					if (getWindow().getCallWaiterFrame() != null){
						if (getWindow().getCallButton().getText().equals("")){
							if (getWindow().getCallLabel().getText().equals("Llamando, espere por favor.")){
								getWindow().getCallLabel().setText("El pago ha sido tramitado.");
							} else if (getWindow().getCallLabel().getText().equals("El pago ha sido tramitado.")){
								getWindow().getCallLabel().setText("Gracias por su visita.");
							} else if (getWindow().getCallLabel().getText().equals("Gracias por su visita.")){
								getWindow().getCallLabel().setText("      ¡¡Hasta otra!!");
							} else{
								getWindow().destroyCallWaiterPanel();
							}
						}
					}else if (s.getStatus().equals(StatusStruct.INACTIVE)  || !getRestaurant().getDatabase().isServiceStarted()){
						LoginWindowController controller = new LoginWindowController();
						getRestaurant().getStation().setStatus(StatusStruct.INACTIVE);
						getRestaurant().getStation().setActor(null);
						getRestaurant().getStation().setOrder(null);
						getRestaurant().getDatabase().updateStation(getRestaurant().getStation());
						controller.setRestaurant(getRestaurant());
						controller.setView(new LoginWindow(ActorStruct.CLIENT, controller));
						controller.setObserver();
						getWindow().dispose();
						getWindow().getTimer().stop();
						setWindow(null);
					}
			}else if(getWindow().getCallWaiterFrame() != null){
				getRestaurant().getStation().setStatus(StatusStruct.EATING);
			}
			break;
		}
		}
		

		
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public ResClient getRestaurant(){
		return (ResClient) super.getRestaurant();
	}
	@Override
	public EatWindow getWindow(){
		return (EatWindow)super.getWindow();
	}
	@Override
	public void setView (TheWindow window){
		setWindow((EatWindow) window);
	}

}
