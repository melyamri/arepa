
package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.text.DateFormatSymbols;
import java.util.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

import view.*;

import model.*;
/**
 * Controlador de la ventana HistoryWindow
 * @author Grupo 9
 *
 */
public class HistoryWindowController extends TheWindowController{
	
	/**
	 * Llama al método de la ventana que inicializa la tabla del historial
	 * @param terminal "cook" para historial de cocina o "client" para el historial de cliente
	 */
	public void initHistory(String terminal){
		getWindow().init(getRestaurant(), terminal);
	}	
	@Override
	public void setView (TheWindow window){
		setWindow((HistoryWindow) window);
	}
	@Override
	public Restaurant getRestaurant(){
		return (Restaurant) super.getRestaurant();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		switch (e.getActionCommand()){
		case "back": getWindow().dispose();
				setWindow(null); break;
		}
		
	}
	@Override
	public HistoryWindow getWindow(){
		return (HistoryWindow) super.getWindow();
	}
	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
