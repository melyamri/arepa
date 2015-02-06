//Author : Pablo Mac-Veigh, 10/05/2013
package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;

import javax.swing.JOptionPane;

import controller.FirstWindowController;

import view.FirstWindow;
import view.TheWindow;
/**
 * Super clase que se extender� para las distintas terminales. Contiene los 
 * m�todos comunes de ambas
 * @author Grupo 9
 *
 */
public class Restaurant extends Observable implements ObservableRestaurant{
	  /**
	   * Mesa que est� ejecutando la aplicaci�n
	   */
	private Station station;
	/**
	  * Un HashMap con platos
	  */
	private HashMap<String, Plate> plates;
  /**
   * Un HashMap con las promos
   */
  private HashMap<String, Promo> promos;
  /**
   * Un HashMap con los men�s
   */
  private HashMap<String, Menu> menus;
  /**
   * La clase de interacci�n con la base de datos
   */
  private DatabaseIO database;

  /**
   * El constructor que inicializa la base de datos y los HashMap
   */
  public Restaurant(){ // Faltan cosas, y falta main
	  database = new DatabaseIO();
	  database.setDBName("[database name]");
	  database.setDBPassword("[password]");
	  database.setDBUser("[db user]");
	  this.plates = new HashMap<String, Plate> ();
	  this.promos = new HashMap<String, Promo> ();
	  this.menus = new HashMap<String, Menu> ();

  }
  /**Esta funci�n tratar� de logearse en la aplicaci�n
 * @param user Nombre de usuario
 * @param password Contrase�a de usuario
 * @return True si el longin tuvo exito, falso en caso contrario
 */
public boolean login(String user, String password) {
	  boolean succeeded = false;
	  this.station = database.getStation(station.getStationId());
		Actor a = database.login(user,password);
		if (a!= null){
			if ((a.getActorClass().equals(ActorStruct.COOK) && station.getStatus().equals(StatusStruct.COOK)) || (a.getActorClass().equals(ActorStruct.WAITER) && station.getStatus().equals(StatusStruct.WAITER)) || (station.getStatus().equals(StatusStruct.ACTIVE)) ){ 
				this.station.setActor(a);
				if (this.station.getActor() != null){
					succeeded = true;
					if (this.station.getStatus() == StatusStruct.ACTIVE) this.station.setStatus(StatusStruct.LOGGED);
					database.updateStation(this.station);
					this.informObservers("actor_login");
				}
		 	}
		}
	  return succeeded;
  }

/**
 * Este m�todo carga un usuario de la base de datos
* @param user el nombre de usuario
* @return el rol del usuario
*/
public Actor loadUser(String user) {
	  return database.getActor(user);
  }

/** Almacena el usuario en la base de datos, sea nuevo o no
* @param actor El Usuario a ser guardado
* @return true si pudo ser a�adido, falso en caso contrario(si existiese o cualquier otro motivo)
*/
public boolean storeUserProfile(Actor actor) {
	  if( database.updateActor(actor)){
		  this.informObservers("store_user");
		  return true;
	  }else return false;
  }

/** Registra un nuevo usuario en la base de datos
* @param actor El nuevo usuario
* @return Dice si hubo problemas al registrar el usuario o ya exist�a.
*/
public boolean registerUser(Actor actor) {	  
	  if(database.registerActor(actor)){
		  this.informObservers("register_user");
		  return true;
	  }else return false;
	  
  }
/**
 * Login de usuario an�nimo
 * @param name nombre con el que se registrar�
 * @return true si se pudo hacer, false si ya hay un usuario con ese nombre o no se pudo hacer
 */
public boolean anonymousLogin(String name) {
	this.station = database.getStation(station.getStationId());
	if (this.station.getStatus() == StatusStruct.ACTIVE){
		String sr = "anonymous_"+ name;
		Actor actor = new Actor(sr, ActorStruct.CLIENT, "", name, "anonymous", 0, "", SexStruct.MALE, "");
		database.registerActor(actor);
		this.station.setActor(database.anonymousLogin(actor.getUser()));
		this.station.setStatus(StatusStruct.LOGGED);
		database.updateStation(this.station);
		this.informObservers("anonymous_login");
		return true;
	} else return false;
}

public Station getStation() {
	return this.station;
}

public DatabaseIO getDatabase() {
	return this.database;
}

public HashMap<String, Plate> getPlates() {
	return this.plates;
}
@Override
public Plate getPlate(String name) {

	Plate p = plates.get(name);
	if(p != null) return p;
	else return null;
}
@Override
public HashMap<String, Promo> getPromos() {
	return this.promos;
}
@Override
public Promo getPromo(String name) {
	Promo p = promos.get(name);
	if(p != null) return p;
	else return null;
}
@Override
public HashMap<String, Menu> getMenus() {
	return this.menus;
}
@Override
public Menu getMenu(String name) {
	Menu m = menus.get(name);
	if(m != null) return m;
	else return null;
}
public void setStation(Station station) {
	this.station = station;
}
public void setPlates(HashMap<String, Plate> plates) {
	this.plates = plates;
}
public void setPromos(HashMap<String, Promo> promos) {
	this.promos = promos;
}
public void setMenus(HashMap<String, Menu> menus) {
	this.menus = menus;
}
public void setDatabase(DatabaseIO database) {
	this.database = database;
}
public boolean modifyUser(Actor actor) {
	
	return database.updateActor(actor); 
}
/**
 * Informa a los observadores de los cambios
 */
public void informObservers(String arg){
	this.setChanged();
	this.notifyObservers(arg);
}
/**
 * Modifica el estado del terminal
 * @param status tipo de estado
 */
public void setStatus(StatusStruct status) {
	  this.station.setStatus(status);
	  database.updateStation(station);
}
/**
 * M�todo main, m�todo que ejecuta toda la aplicaci�n
 * @param args
 */
public static void main(String[] args)
{

	//Restaurant r = new Restaurant();
	FirstWindowController controller = new FirstWindowController();
	TheWindow w = new FirstWindow(ActorStruct.COOK, controller);
	controller.setView(w);
	controller.setRestaurant(new Restaurant());
	
}
}
