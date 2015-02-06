
package model;

import java.util.*;

import javax.swing.JOptionPane;
/**
 * Representa el restaurante para un terminal cliente. Esta clase implementa todos los
 * métodos necesarios para interactuar con la aplicación siendo un cliente
 * @author Grupo 9
 *
 */
public class ResClient extends Restaurant implements ObservableResClient {
  
	/**
	 * Constructor que inicializa la terminal.
	 * @param r un restaurante válido
	 */
	  public ResClient(Restaurant r){
		  setDatabase(r.getDatabase());
		  init();
	  }

	  /**
	   * Carga los platos ordenados
	   */
	  public boolean loadPlates() {
		  setPlates(getDatabase().getPlates());
		  return true;
	  }

	  /**
	   * Carga los menus disponibles en la aplicación
	   */
	  public boolean loadMenus() {
		  setMenus(getDatabase().getMenus());
		  return true;
	  }

	  /**
	   * Carga las promociones dispomibles en la aplicación
	   */
	  public boolean loadPromos() {
		  setPromos(getDatabase().getPromos());
		  return true;
	  }
	  /**
	   * Filtra los platos por ingredientes y casos especiales, y los muestra en orderWindow,
	   * eliminando todos los que no cumplen las especificaciones
	   */
  public boolean filterPlates(String ingredient, boolean[] diseases) {
	  informObservers("filter_plates");
	  setPlates(getDatabase().getFilteredPlates(ingredient,diseases));
	  return true;
  }
  /**
   * Filtra los menús por una serie de casos especiales alimenticios
   * @param diseases casos especiales
   */
  public boolean filterMenus(boolean[] diseases) {
	  informObservers("filter_menus");
	  setMenus(getDatabase().getFilteredMenus(diseases));
	  return true;
  }
  /**
   * Añade un plato a la orden actual
   * @param p  plato a añadir 
   */
  public boolean addPlateToOrder(Plate p){
	  this.getStation().getOrder().addPlate(p);
	  informObservers("modify_order");
	  return true;
	  
  }
  /**
   * Añade un menú a la orden actual
   * @param m menú a añadir
   */
  public boolean addMenuToOrder(Menu m){
	  this.getStation().getOrder().addMenu(m);
	  informObservers("modify_order");
	  return true;
	  
  }
  
  /**
   * Añade un plato a la orden
   * @param m menú de un único plato
   */
  public boolean addMenu(Menu m){
	  this.getStation().getOrder().addMenu(m);
	  informObservers("modify_order");
	  return true;
	  
  }
  /**
   * Elimina un plato o menú de la orden
   */
  public boolean remove(String name){
	  this.getStation().getOrder().remove(name);
	  informObservers("modify_order");
	  return true;
	  
  }
  
  /**
   * Almacena un pedido realizado
   */
  public boolean storeOrder(){
	 int id = 0;
	 int block_mark = getStation().getOrder().getBlockMark();
	 if (getOrder().getBlockMark() == 0){
		 /*
		  * Block menu & plate -> block mark = block_menu*10 + block_plate
		  * Example: 2 menus and 5 plates -> Block_mark = 25
		  */
		 int block_menu = getStation().getOrder().getMenuCount()* 10;
		 int block_plate = getStation().getOrder().getPlateCount();
		 getStation().getOrder().setBlockMark(block_menu + block_plate);
		 
		 id = getDatabase().storeOrder(getStation().getOrder());
		 getStation().getOrder().setOrderId(id);
	 } else {
		 int block_menu = getStation().getOrder().getMenuCount()* 10;
		 int block_plate = getStation().getOrder().getPlateCount();
		 getStation().getOrder().setBlockMark(block_menu + block_plate);
		 getDatabase().updateOrder(getStation().getOrder());
	 }
	 for (int i = (block_mark/10); i < getStation().getOrder().getMenuCount(); i++){
		 Menu m = getStation().getOrder().getMenu(i);
		 ArrayList <Plate> plates = new ArrayList<Plate>((Collection<Plate>) m.getPlates().values());
		 for (int j = 0; j < plates.size(); j++)
			 getDatabase().storeOrderedPlate(plates.get(j), getStation());
	 }
	 for (int i = (getStation().getOrder().getMenuCount() + (block_mark%10)) ; i < ( getStation().getOrder().getPlateCount() + getStation().getOrder().getMenuCount()); i++){
		 Menu m = getStation().getOrder().getMenu(i);
		 ArrayList <Plate> plates = new ArrayList<Plate>((Collection<Plate>) m.getPlates().values());
		 getDatabase().storeOrderedPlate(plates.get(0), getStation());
	 }
	 getDatabase().updateStation(getStation());
	 informObservers("store_order");
	 if (id!=-1) return true;
	 else return false;
  }
  /**
   * Desconecta la sesión actual del usuario activo y muestra un mensaje de despedida
   * @return
   */
  public boolean disconnect(){
	  if (getOrder().getBlockMark() == 0){
		//  JOptionPane.showMessageDialog(null, "Gracias por su visita, ¡hasta otra!");
		  getStation().setStatus(StatusStruct.INACTIVE);
		  getDatabase().updateStation(getStation());
		  if (getStation().getActor().getSurname().equalsIgnoreCase("anonymous"))
			  getDatabase().removeActor(getStation().getActor().getUser());
		
		  //System.exit(0);
	  } else JOptionPane.showMessageDialog(null, "Para salir, pulse el botón de pagar en la ventana posterior a la selección de promociones.");
	  return false;
	  
  }
  /**
   * Inicializa el restaurante, notificando que está activa cuando un
   * usuario se conecta
   */
  public boolean init(){	 
	  setStation(new Station(""));
	  //TODO getStation().setStatus(StatusStruct.INACTIVE);
	  getStation().setStatus(StatusStruct.INACTIVE);
	  getStation().setStationId(getDatabase().storeStation(getStation()));
	   return true;
  }

  public ResClient getResClient(){
	  return this;
  }

  public void setResClient(Order order){
	  getStation().setOrder(order);
  }

@Override
public Order getOrder() {
	return getStation().getOrder();
}
/**
 * Informa a los observadores de cambios producidos.
 */
public void informObservers(String arg){
	this.setChanged();
	this.notifyObservers(arg);
}
/**
 * Añade una promoción a la orden
 * @param p la promo
 */
public void addPromo(Promo p) {
	  this.getStation().getOrder().addPromo(p);
	  informObservers("add_promo");
	
}
/**
 * Elimina una promoción de la orden
 * @param name nombre de la promoción
 */
public void removePromo(String name) {
	  this.getStation().getOrder().removePromo(name);
	  informObservers("remove_promo");
	
}
/**
 * Elimina un plato del pedido identificado de la posición
 */
public void removePlateFromOrder(int position) {
	this.getStation().getOrder().remove(position);
	//this.getDatabase().updateOrder(getOrder());
	informObservers("remove_menu_plate");
}
}
