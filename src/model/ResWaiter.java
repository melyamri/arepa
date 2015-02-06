package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

import view.TheWindow;
/**
 * Esta clase será utilizada en el terminal que utilicen los camareros, e implementa todos los
 * métodos que necesita un camarero para utilizar la aplicación
 * @author Grupo 9
 *
 */
public class ResWaiter extends Restaurant implements ObservableResWaiter{
	/**
	   * Lista que contiene las mesas del restaurante
	   */
  private HashMap<String, Station> tables;
  /**
   * Lista que contiene los platos finalizados y listos para llevar.
   */
  private List<Plate> finished_plates;
  /**
   * El constructor de la clase. Necesita un objeto de su super clase para inicializarla
   * @param restaurant un objeto de tipo restaurant
   */
  public ResWaiter(Restaurant restaurant) {
	  setDatabase(restaurant.getDatabase());
	  init();
}

  /**
   * Carga la lista con los platos finalizados
   */
  public boolean loadFinishedPlates() {
	  this.finished_plates = new ArrayList<Plate>();
	  informObservers("load_finished_plates");
	  return this.finished_plates.addAll(getDatabase().getFinishedPlates());
  }

  /**
   * Carga una lista con las mesas actuales
   */
  public boolean loadTables() {
	  this.tables = new HashMap<String, Station>();
	  informObservers("load_tables");
	  this.setTables(getDatabase().getTables());
	  return true;
  }

  /**
   * Carga una lista con los pedidos activos
   */
  public Order loadOrder(String station) {
	  loadTables();
	  return this.tables.get(station).getOrder();
  }

  /**
   * Desconecta al usuario camarero
   */
  public boolean disconnect(){
	 
	  getDatabase().removeStation(getStation().getStationId());
	  System.exit(0);
	  return true;
	  
  }
  /**
   * Marca como entregado un plato finalizado que corresponde a una mesa
   */
  public boolean deliverPlate(Plate p, String table_id) {
	  if (this.getDatabase().deliverPlate(p, table_id)){
		  loadFinishedPlates();
		  informObservers("deliver_plate");
		  return true;
	  }else return false;
  }

  /**
   * Convierte una mesa en activa o inactiva. La inactividad se pede forzar por el parámetro force_inactive
   * 
   */
  public boolean setTableActiveInactive(String table, boolean force_inactive) {
	  boolean succeed = false;
	  if (force_inactive){ //Fuerza el estado inactivo de una mesa
		  this.setStatus(table, StatusStruct.INACTIVE);
		  succeed = true;
	  }else if (this.tables.get(table).getStatus().equals(StatusStruct.ACTIVE)){ // Si está activa la desactiva
		  this.setStatus(table, StatusStruct.INACTIVE);
		  succeed = true;
	  }else if (this.tables.get(table).getStatus().equals(StatusStruct.INACTIVE)){ // Si está desactivada la activa.
		  this.setStatus(table, StatusStruct.ACTIVE);
		  succeed = true;
	  }
	  informObservers("set_table_ai");
	  return succeed; // Si está en un estado que no sea activa o inactiva devuelve false (Inactivar una mesa cuando está siendo atendida?)
  }

  /**
   * Indica si la mesa que estaba llamando ha sido atendida por el camarero
   */
  public boolean setTableAttended(String table) {
	  boolean succeed = false;
	  if (this.tables.get(table).getStatus().equals(StatusStruct.CALLING)){ // Si la mesa no está llamando, está atendida.
		  this.tables.get(table).setStatus(StatusStruct.ORDERING);
		  succeed = true;
	  } else if (this.tables.get(table).getStatus().equals(StatusStruct.PAYING)){
		  this.tables.get(table).setStatus(StatusStruct.EATING);
	  }
	  informObservers("set_table_attended");
	  return succeed;
  }

  /**
   * Inicializa los valores de la clase
   */
  public boolean init() {
	  
	  setStation(new Station(""));
	  getStation().setStatus(StatusStruct.WAITER);
	  getStation().setStationId(
			  getDatabase().storeStation(getStation()));
	  boolean succeed = false;
	  this.tables = new HashMap <String, Station>();
	  succeed = this.loadTables();
	  this.finished_plates = new ArrayList <Plate>();
	  succeed = succeed & loadFinishedPlates();
	  return succeed;
  }


public Station getTable(String id) {
	return tables.get(id);
}

public Station getTable (int station_position){
	return tables.get(station_position);
}


@Override
public Plate getFinishedPlate(String name) {
	Plate plate = null;//variable de return
	loadFinishedPlates();
	boolean found = false;
	int index = 0;//iterador
	while (!found & index<finished_plates.size()){
		if (finished_plates.get(index).getName().equals(name)){ // aqui un equals?
			plate = finished_plates.get(index);
			found = true; //cambio de condicion del bucle
		}
		index++;
	}
	return plate;
}

public void setStatus(String station, StatusStruct status){
	tables.get(station).setStatus(status);
	getDatabase().updateStation(tables.get(station));
}
public Plate getFinishedPlate(int plate_position){
	return finished_plates.get(plate_position);
}

@Override
public HashMap<String, Station> getTables() {
	return tables;
}

@Override
public List<Plate> getFinishedPlates() {
	return finished_plates;
}

public void setFinishedPlates(List<Plate> finished_plates) {
	this.finished_plates = finished_plates;
}

public void setTables(HashMap<String, Station> tables) {
	this.tables = tables;
}
/**
 * Notifica a los observadores que ha habido cambios en la clase
 */
public void informObservers(String arg){
	this.setChanged();
	this.notifyObservers(arg);
}
/**
 * Guarda el pedido en el historial del cocinero.
 * @param order el pedido
 */
public void storeOrderToHistory(Order order) {
	float[] history = getDatabase().getKitchenHistory();
	int year = Calendar.getInstance().get(Calendar.YEAR);
	int month = Calendar.getInstance().get(Calendar.MONTH);
	if ((int)history[0]==year){
		JOptionPane.showMessageDialog(null, history[month+1]);
		history[month+1] += order.getPrice();
		JOptionPane.showMessageDialog(null, history[month+1]);
		history[13] += order.getPrice();
		getDatabase().updateKitchenHistory(history);
	} else{
		history[0] = year;
		for (int i = 1; i<=12; i++)
			history[i] = 0;
		history[month+1] = order.getPrice();
		history[14] = history[13];
		history[13] = order.getPrice();
		getDatabase().storeKitchenHistory(history);
	}
	
	
}
}