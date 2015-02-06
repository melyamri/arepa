
package model;

import java.util.*;
/**
 * Clase que representa un Menú. Los menús contienen al menos un plato.
 * @author Grupo 9
 *
 */
public class Menu {
  /**
   * Nombre del menú
   */
  private String name;

  /**
   * Precio del menú
   */
  private float price;

  /**
   * Indica si el menú está bloqueado
   */
  private boolean blocked;

  /**
   * 
   * Array de booleanos que indica si tiene un caso especial de DISEASES
   */
  private boolean[] disease;

  /**
   * Lista de platos que contiene el menú
   */
  private HashMap<String,Plate> plates;

  /**
   * Constructor del menú. Inicializa sus atributos.
   * @param name Nombre del menú
   * @param price Precio del menú
   */
  
  public Menu(String name, float price){
	  this.name = name;
	  this.plates = new HashMap <String,Plate>();
	  this.price=price;
	  disease = new boolean[8];
  }
  
  
  /**
   * Añade un plato al menú. Devuelve true si se ha añadido el plato correctamente, false en
   * caso contrario.
   * @param p el plato
   */
  public boolean addPlate(Plate p){
	  
	  if (!plates.containsKey(p.getName()) && plates.size()< 8){
	  	plates.put(p.getName(), p);
	  	this.checkAllDiseases();
	  	return true;
	  } else return false;
	  
	  
	  
  }

   /**
   * Elimina un plato del menú, devuelve true si se ha eliminado el plato correctamente,
   * false en caso contrario
   * @param name Nombre del plato
   */
  public boolean removePlate(String name){
	  if (plates.containsKey(name)){
		  plates.remove(name);
		  return true;
	  } else return false;
	  	
  }

  /**
   * Añade la posición de un caso especial al array diseases del menú.
   * @param n la posición donde hay que añadir el caso especial
   * @see Diseases
   */ 
  public void addDisease(int n){
	this.disease[n]= true;  
  }

  /**
   * Actualiza todos los casos especiales de un plato.
   */   
  private void checkAllDiseases(){
	  ArrayList<Plate> plate_list = new ArrayList<Plate>((Collection<Plate>)plates.values());
	  
	  for (int i = 0; i<plates.size(); i++){
		  for (int j = 0; j<disease.length; j++){
			 if (plate_list.get(i).hasDisease(j)) addDisease(j);
		  }
	  }
  }

  /**
   *Comprueba si el menú tiene un caso especial.
   *@param n posición del caso especial
   */
  public boolean hasDisease(int n){
	  return this.disease[n];
  }

  //Gets y sets

public String getName() {
	return name;
}


public void setName(String name) {
	this.name = name;
}


public float getPrice() {
	return price;
}


public void setPrice(float price) {
	this.price = price;
}


public boolean isBlocked() {
	return blocked;
}


public void setBlocked(boolean blocked) {
	this.blocked = blocked;
}


public boolean[] getDisease() {
	return disease;
}


public void setDisease(boolean[] disease) {
	this.disease = disease;
}


public HashMap<String, Plate> getPlates() {
	return plates;
}


public void setPlates(HashMap<String, Plate> plates) {
	this.plates = plates;
}

}