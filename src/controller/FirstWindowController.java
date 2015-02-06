
package controller;

import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import model.*;
import view.*;
/**
 * Controlador de la ventana FirstWindow
 * @author Grupo 9
 *
 */
public class FirstWindowController extends TheWindowController{
	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		switch (e.getActionCommand()){
		case "disconnect": System.exit(0);
		case "connect_button":
			try {
				if (!getWindow().getIpText().getText().equals("")){
					getRestaurant().getDatabase().setCookIP(getWindow().getIpText().getText());
					getRestaurant().getDatabase().setDBName("db_arepa");
					getRestaurant().getDatabase().setDBPassword("GrUp0NuevE#@AREPA#@IS2012.2013");
					getRestaurant().getDatabase().setDBUser("db_arepa_us3r");
					
					if (getRestaurant().getDatabase().initSQLConnection()){
						getRestaurant().getDatabase().createSQLTables();
						getRestaurant().getDatabase().createAdmins();
						LoginWindowController controller = new LoginWindowController();
						switch ((String)getWindow().getActorclassCombobox().getSelectedItem()){
						case "Camarero":
							restaurant = new ResWaiter(restaurant);
							controller.setView(new LoginWindow(ActorStruct.WAITER,controller));
							controller.setRestaurant(restaurant);
							getWindow().dispose();
							break;
						case "Cliente":
							restaurant = new ResClient(restaurant);
							controller.setRestaurant(restaurant);
							controller.setView(new LoginWindow(ActorStruct.CLIENT,controller));
							
							getWindow().dispose();
							break;
						case "Cocina":
							restaurant = new ResCook(restaurant);
							controller.setRestaurant(restaurant);
							controller.setView(new LoginWindow(ActorStruct.COOK,controller));
							getWindow().dispose();
							break;
						
						default: 
							showErrorPane("Debe seleccionar un tipo de ordenador", "Error");
						}
					} else showErrorPane("La dirección IP dada no es accesible", "Error");
				} else showErrorPane("Por favor introduzca una dirección", "Error");
			} catch (Exception e1) {
				showErrorPane("La dirección IP dada no es accesible", "Error");
			}
			break;
		}
		
	}
	@Override
	public void setView (TheWindow window){
		setWindow((FirstWindow) window);
	}
	@Override
	public FirstWindow getWindow(){
		return (FirstWindow)super.getWindow();
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
