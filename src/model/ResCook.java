
package model;

import java.security.AllPermission;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import view.TheWindow;
/**
 * Esta clase guarda un usuario cocinero e implementa todos los métodos necesarios 
 * para que pueda administrar la aplicación
 * @author Grupo 9
 *
 */
public class ResCook extends Restaurant implements ObservableResCook{
	  /**
	   * Indica si el servicio ha empezado
	   */
  private boolean service_started;

  /**
   * Indica qué mesa ha hecho la orden
   */
  private HashMap<String, Station> stations;

  /**
   * Indica qué platos han sido ordenados 
   */
  private List<Plate> ordered_plates;

  /**
   * Indica los ingredientes que tiene el plato
   */
  private HashMap<String, Ingredient> ingredients;

  /**
   * Constructor de rescook, necesita un objeto de su supertipo
   */
  public ResCook(Restaurant restaurant) {
	  setDatabase(restaurant.getDatabase());
	  init();
	// TODO Auto-generated constructor stub
}

  /**
   * Carga los ingredientes que tiene el restaurante
   */
  public boolean loadIngredients() {
	  informObservers("load_ingredients");
	  setIngredients(getDatabase().getIngredients());
	  return true;
  }

  /**
   * Carga los platos ordenados
   */
  public boolean loadPlates() {
	 informObservers("load_plates");
	  setPlates(getDatabase().getPlates());
	  return true;
  }

  /**
   * Carga los menús disponibles en el restaurante
   */
  public boolean loadMenus() {
	  informObservers("load_menus");
	  setMenus(getDatabase().getMenus());
	  return true;
  }

  /**
   * Carga las promociones disponibles en el restaurante
   */
  public boolean loadPromos() {
	  informObservers("load_promos");
	  setPromos(getDatabase().getPromos());
	  return true;
  }

  /**
   * Carga los platos pedidos
   */
  public boolean loadOrderedPlates() {
	  informObservers("load_ordered_plates");
	  setOrderedPlates(getDatabase().getOrderedPlates());
	  return true;
  }
  
  /**
   * Carga todas las estaciones de clientes
   * @return
   */
  public boolean loadAllStations() {
	setStations(getDatabase().getStations());
	return true;
  }
  
  /**
   * Carga el historial de la cocina devolviendo un array con  los meses y años 
   * en el historial.
   */
  public float[] getKitchenHistory() {
	  return getDatabase().getKitchenHistory();
  }

  /**
   * Desconecta al usuario del programa, desconectando a los demás terminales
   */
  public boolean disconnect() {
	  loadAllStations();
	  ArrayList <Station> st = new ArrayList<Station> ((Collection <Station>)stations.values());
	  for (int i = 0; i< st.size(); i++){
		  getDatabase().removeStation(st.get(i).getStationId());
	  }
	  getDatabase().removeStation(getStation().getStationId());
	  System.exit(0);
	  return false;
  }

  /**
   * Establece que un plato ha sido cocinado y está listo para entregar
   */
  public boolean cookPlate(Plate plate, Station station) {
		for (int i = 0; i < plate.getIngredients().size(); i++ ){
			Ingredient ing = getIngredients().get((new ArrayList<Ingredient>((Collection<Ingredient>) plate.getIngredients().values()).get(i).getName()));
			ing.removeAmount(1);
			if (ing.isBlocked()) getDatabase().setIngredientBlocked(ing.getName());
			getDatabase().updateIngredient(ing);
		}
	  if(getDatabase().storeFinishedPlate(plate, station.getStationId())){
		  
		  loadOrderedPlates();
		  informObservers("cook_plate");
		  return true;
	  }else{
		  return false;
	  }
	
  }

  
  /**
   * Bloquea un ingrediente
   * @param name nombre del ingrediente
   */
  public boolean blockIngredient(String name) {
	  getIngredient(name).setBlocked(true);
	  informObservers("ingredient_block");
	  return getDatabase().setIngredientBlocked(name);
  }
  
  /**
   * Desbloquea un ingrediente
   * @param name nombre del ingrediente
   */
  public boolean unBlockIngredient(String name) {
	  getIngredient(name).setBlocked(false);
	  informObservers("ingredient_unblock");
	  return getDatabase().setIngredientUnBlocked(name);
  }

  /**
   * Borra un usuario
   * @param user nombre de usuario
   */
  public boolean deleteUser(String user) {
	  informObservers("user_delete");
	  return getDatabase().removeActor (user);
  }

  /**
   * Añade un ingrediente al plato
   */
  public boolean addIngredient(Ingredient i) {
	  if (!ingredients.containsKey(i.getName())){
		  ingredients.put(i.getName(), i);
		  getDatabase().storeIngredient(i);
		  informObservers("add_ingredient");
		  return true;
	  } else {
		  if (getDatabase().updateIngredient(i)){
			  ingredients.put(i.getName(), i);
			  informObservers("add_ingredient");
			  return true;
		  }else return false;
	  } 
  }

  /**
   * Elimina un ingrediente de la base de datos.
   */
  public boolean removeIngredient(String name) {
	  if (ingredients.containsKey(name)){
		  ingredients.remove(name);
		  getDatabase().removeIngredient(name);
		  informObservers("remove_ingredient");
		  return true;
	  } else return false;
  }

  /**
   * Añade un plato a la lista de platos
   * @param p plato
   */
  public boolean addPlate(Plate p) {
	  if (!getPlates().containsKey(p.getName())){
		  getPlates().put(p.getName(), p);
		  getDatabase().storePlate(p);
		  informObservers("add_plate");
		  return true;
	  } else   if (getDatabase().updatePlate( p)){
		  getPlates().put(p.getName(), p);
		  informObservers("add_plate");
		  return true;
	  }else return false;
  }

  /**
   * Quita un plato de la lista
   * @param name nombre del plato
   */
  public boolean removePlate(String name) {
	  if (getPlates().containsKey(name)){
		  getPlates().remove(name);
		  getDatabase().removePlate(name);
		 	informObservers("remove_plate");
		  return true;
	  } else return false;
  }

  /**
   * Añade un menu a la lista
   * @param m el menú
   */
  public boolean addMenu(Menu m) {
	  if (!getMenus().containsKey(m.getName())){
		  getMenus().put(m.getName(), m);
		  getDatabase().storeMenu(m);
		  informObservers("add_menu");
		  return true;
	  } else if (getDatabase().updateMenu(m)){
		  getMenus().put(m.getName(), m);
		  informObservers("add_menu");
		  return true;
	  }else return false;
  }

  /**
   * Quita un menu a la lista
   * @param name el nombre del menú
   */
  public boolean removeMenu(String name) {
	  if (getMenus().containsKey(name)){
		  getMenus().remove(name);
		  getDatabase().removeMenu(name);
		  informObservers("remove_menu");
		  return true;
	  } else return false;
  }

  /**
   * Añade una promocion a un plato
   * @param p promo
   */
  public boolean addPromo(Promo p) {
	  if (!getPromos().containsKey(p.getName())){
		  getPromos().put(p.getName(), p);
		  getDatabase().storePromo(p);
		  informObservers("add_promo");
		  return true;
	  } else if (getDatabase().updatePromo(p)){
		  getPromos().put(p.getName(), p);
		  informObservers("add_promo");
		  return true;
	  }else return false;
  }

  /**
   * quita una promocion de un plato
   * @param name nombre de la promoción
   */
  public boolean removePromo(String name) {
	  if (getPromos().containsKey(name)){
		  getPromos().remove(name);
		  getDatabase().removePromo(name);
		  informObservers("remove_promo");
		  return true;
	  } else return false;	  
  }

  /**
   * Inicia el servicio
   */
  public boolean startService() {
	  setServiceStarted(true);
	  getDatabase().startService();
	 // informObservers("start_service");
	  return true;
  }

  /**
   * Termina el servicio
   */
  public boolean endService() {
	  setServiceStarted(false);
	  getDatabase().endService();
	  return true;
  }

  /**
   * inicializa los valores del restaurante a esta clase
   */
  public boolean init() {
	  setStation(new Station(""));
	  getStation().setStatus(StatusStruct.COOK);
	  getStation().setStationId(
			  getDatabase().storeStation(getStation()));
	  boolean succeed = false;
	  this.stations = new HashMap <String, Station>();
	  succeed = loadAllStations();
	  this.ordered_plates = new ArrayList <Plate>();
	  succeed = succeed & loadOrderedPlates();
	  this.ingredients = new HashMap <String, Ingredient>();
	//  this.ingredients = getDatabase().getIngredients();
	  succeed = succeed & loadIngredients();
	  this.service_started = false;
	  getDatabase().initService();
	  return succeed;
  }


@Override
public boolean getServiceStarted() {
	
	return service_started;
}

@Override
public HashMap<String, Station> getAllStations() {
	
	return stations;
}

@Override
public List<Plate> getOrderedPlates() {
	
	return ordered_plates;
}

@Override
public HashMap<String, Ingredient> getIngredients() {
	
	return ingredients;
}

@Override
public Plate getOrderedPlate(String name) {
int cont = 0;
	
	while(cont < ordered_plates.size())
	{
		if(ordered_plates.get(cont).getName().equalsIgnoreCase(name))
		{
			return ordered_plates.get(cont);
		}
	}
	return null;
}

@Override
public Ingredient getIngredient(String name) {
	return ingredients.get(name);
}

@Override
public Plate getOrderedPlate(int position) {
	return ordered_plates.get(position);
}

public boolean isServiceStarted() {
	return service_started;
}

public void setServiceStarted(boolean service_started) {
	this.service_started = service_started;
}

public HashMap<String, Station> getStations() {
	return stations;
}

public void setStations(HashMap<String, Station> stations) {
	this.stations = stations;
}

public void setOrderedPlates(List<Plate> ordered_plates) {
	this.ordered_plates = ordered_plates;
}

public void setIngredients(HashMap<String, Ingredient> ingredients) {
	this.ingredients = ingredients;
}

public Station getAllStation (String id){
	return this.stations.get(id);
}
/**
 * Notifica a los observadores de los cambios ocurridos
 */
public void informObservers(String arg){
	this.setChanged();
	this.notifyObservers(arg);
}
/**
 * Desconecta al usuario cocinero
 */
public boolean disconnectUser(Station st) {
	boolean rt = false;
	if (st.getStatus()!= StatusStruct.COOK && st.getStatus()!= StatusStruct.WAITER){
		st.setStatus(StatusStruct.INACTIVE);
		getDatabase().updateStation(st);
		rt = true;
	} else if (st.getStatus() == StatusStruct.WAITER){
		getDatabase().removeStation(st.getStationId());
		rt = true;
	}
	return rt;	
}

}
