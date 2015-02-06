
package controller;

import java.awt.event.*;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;

import model.*;

import view.*;
/**
 * Controlador de la ventana HelpWindow
 * @author Grupo 9
 *
 */
public class HelpWindowController extends TheWindowController{

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()){
		case "info_button": HelpWindowController controller = new HelpWindowController();
							controller.setView(new HelpWindow("HelpWindow", ActorStruct.CLIENT, controller));
							;
							break;
		case "back_button": getWindow().dispose();
							setWindow(null); break;
		}
		
	}
	
	@Override
	public void setView (TheWindow window){
		setWindow((HelpWindow) window);
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
