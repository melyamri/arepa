
package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Timer;

import com.sun.jmx.snmp.Timestamp;

/**
 * La orden que contendrá los platos, el precio total, la fecha y menús. Se identificará por 
 * un ID numérico. 
 * 
 * Esta clase medirá el tiempo que lleva activa la orden, permite calcular los precios y añadir o
 * quitar un objeto de cualquiera de las clases que instancia.
 * @author Grupo 9
 */
public class Order {
	 /**
	   * The identificator into the database
	   */
  private int order_id;


  /**
   * Contiene las promos asociadas a esta orden, Su máximo es 4
   */
  private List<Promo> promos;

  /**
   * Precio final para el pedido.Ya aplicados descuentos e impuestos
   */
  private float price;
  /**
   * Una lista con los menus pedidos. El máximo será de 4 menús y 7 platos.
   * Están ordenados en la lista siguiendo el criterio: 1º Menus, 2º platos.
   */
  private List<Menu> menus;

  /**
   * Cuenta los platos en la lista. Se aplica un máximo de 7.
   */
  private int plate_count;
  /**
   * Hora y fecha del pedido
   */
  private Date date;
  /**
   * Cuenta los menús en la lista. Se aplica un máximo de 4.
   */
  private int menu_count;

  /**
   * Guarda el número de menus y platos para evitar que sean borrados cuando
   * el menú ya está pedido
   */
  private int block_mark;

  /**
   * Constructor que inicializa los valores y las listas
   * @param order_id id del pedido
   * @param price precio del pedido
   */
  public Order(int order_id, float price) {
	  this.order_id = order_id;
	  this.promos = new ArrayList<Promo>();
	  this.price = price;
	  this.menus = new ArrayList<Menu>();
	  this.plate_count = 0;
	  this.menu_count = 0;
	  this.block_mark = 0;
  }

  /**
   * Obtiene un entero que representa en segundos el tiempo activo de la orden actual
   */
  public long getTimeActive() {
	  Calendar cal = Calendar.getInstance();
	  Calendar order_cal = new GregorianCalendar();
	  order_cal.setTime(this.date);

	  return (cal.getTime().getTime() - order_cal.getTime().getTime())/60000;
  }

  /**
   * Añade un plato a la orden.
   * @param plate el plato
   * @returns true si todo se hizo correctamente, false en caso contrario
   */
  public boolean addPlate(Plate plate) {
	  if (plate != null){
		  if(plate_count<7){
			 //Añadir el plato
			  Menu aux_menu = new Menu(plate.getImageName(),plate.getPrice());
			  aux_menu.addPlate(plate);
			  menus.add((int)(menu_count + plate_count), aux_menu);
			  this.plate_count++;
			  return true;
		  }
		  else return false;
	  } else return false;
  }

  /**
   * Añade un menú a la orden
   * @param menu el menú
   * return true si se hizo correctamente, false en caso contrario
   */
  public  boolean addMenu(Menu menu) {
	  if (menu != null){
		  if(menu_count<4){
			  menus.add(menu_count,menu);
			  this.menu_count++;
			  return true;
		  }
		  else return false;
	  } else return false;
  }
  /**
   * Añade una promo a la lista
   * @param promo una promoción válida
   * @return true si se hizo correctamente, false en caso contrario
   */
  public boolean  addPromo(Promo promo) {
	  if (promo!= null){
		  if(promos.size()<4){
			  int i = 0;
			  while(i<promos.size()){
				  if(promos.get(i).getName().equals(promo)){
					  return false;
				  }
				  i++;
			  }
			  promos.add(promo);
			  return true;
		  } else return false;
	  }else return false;
  }

  /**
   * Elimina una promo presente en la lista. Si no se pudo hacer o no se encontró
   * se devuelve false, en caso contrario, true.
   */
  public  boolean removePromo(String promo) {
	  int i=0;
	  while(i<promos.size()){
		  if(promos.get(i).getName().equals(promo)){
			  promos.remove(i);
			  return true;
		  }
		  i++;
	  }	  
	  return false;
  }

  /**
   * Elimina un menu presente en la lista. Si no se pudo hacer o no se encontró
   * se devuelve false, en caso contrario, true. 
   */
  public boolean remove(String name) {
	  
	  int i=0;
	  while(i<menus.size()){
		  if(menus.get(i).getName().equals(name)){
			  menus.remove(i);
			  if (i < menu_count){
				 menu_count--;
			  } else plate_count --;
			  return true;
		  }
		  i++;
	  }
	  return false;
	  
  }
  /**
   * Elimina un menu o plato presente en la lista. Se identifica por su índice.
   * Si no se pudo hacer o no se encontró, se devuelve false, en caso contrario,
   * true.
   */
  public boolean remove(int position) {
	  
	 if (position < menus.size()) {
		 menus.remove(position);
		 if (position < menu_count){
			 menu_count--;
		 } else plate_count --;
		 return true;
	 } else return false;
  }
  /**
   * Calcula el precio bruto
   */
  public float calculateGrossPrice() {
	  float gross = 0;
	  for (int i = 0; i<menus.size(); i++){
		  gross += menus.get(i).getPrice();
	  }
	  return gross;
  }

  /**
   * Calcula el precio neto
   */
  public  float calculateNetPrice() {
	  
	  setPrice(calculateGrossPrice() - calculateDiscount());
	  return (getPrice());  
  }

  /**
   * Calcula el descuento que se aplicará sobre el precio final,
   * NO EL PRECIO FINAL
   */
  public float calculateDiscount() {
	  float disc = 0;
	    if (promos.size() > 0)
	    	disc = promos.get(0).getPlate().getPrice() * promos.get(0).getDiscount() / 100 ;
	  return disc;
  }

public int getOrderId() {
	return order_id;
}

public void setOrderId(int order_id) {
	this.order_id = order_id;
}

public List<Promo> getPromos() {
	return promos;
}

public void setPromos(List<Promo> promos) {
	this.promos = promos;
}

public float getPrice() {
	return price;
}

public void setPrice(float price) {
	this.price = price;
}

public Date getDate() {
	return date;
}

public void setDate(Date date) {
	this.date = date;
}

public List<Menu> getMenus() {
	return menus;
}

public void setMenus(List<Menu> menus) {
	this.menus = menus;
}

public int getPlateCount() {
	return plate_count;
}

public void setPlateCount(int plate_count) {
	this.plate_count = plate_count;
}

public int getMenuCount() {
	return menu_count;
}

public void setMenuCount(int menu_count) {
	this.menu_count = menu_count;
}

public int getBlockMark() {
	return block_mark;
}

public void setBlockMark(int block_mark) {
	this.block_mark = block_mark;
}

public Menu getMenu(int i) {
	return this.menus.get(i);
}

public Promo getPromo(int i) {
	return this.promos.get(i);
}
/**
 * @return true si el plato identificado por el parámetro está en la lista
 */
public boolean hasPlate(String name) {
	boolean ret = false;
	if (plate_count > 0){
		for (int i = menu_count; i < (menu_count + plate_count); i++){
			ArrayList<Plate>  plates = new ArrayList<Plate> ((Collection<Plate>) getMenu(i).getPlates().values());
			if (plates.get(0).getName().equals(name))ret = true;
		}
	}
	return ret;
}

}
