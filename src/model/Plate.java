
package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
/**
 * Clase que representa un Plato. Un plato contiene al menos un ingrediente.
 * @author Grupo 9
 *
 */
public class Plate {
  /**
   * Nombre del plato
   */
  private String name;

  /**
   * Lista de ingredientes que contiene el plato
   */
  private HashMap<String, Ingredient> ingredients;

  /**
   * Origen del plato
   */
  private String location;

  /**
   * Información adicional sobre el plato
   */
  private String info;

  /**
   * Precio del plato
   */
  private float price;

  /**
   * Indica si el plato está bloqueado
   */
  private boolean blocked;

  /**
   * Nombre de la imagen del plato
   */
  private String image_name;
  
  /**
   * Array de booleanos que indica si tiene un caso especial de DISEASES
   */
  private boolean[] disease;

  /**
   * Constructor del plato, inicializa sus atributos.
   * @param name Nombre 
   * @param location Origen
   * @param info Información adicional
   * @param price Precio
   * @param image_name Nombre de la imagen
   */
  public Plate(String name, String location, String info, float price, String image_name){
	this.name = name;
	this.location = location;
	this.info = info;
	this.price = price;
	this.image_name = image_name;
	this.ingredients = new HashMap<String,Ingredient>();
	this.disease = new boolean[8];
  }
  /**
   * Añade un ingrediente al plato.
   * @param i el ingrediente
   * @return true si se ha añadido correctamente, false en caso contrario.
   */
  public boolean addIngredient(Ingredient i) {
	  
	  if (!ingredients.containsKey(
			  i.getName()) && 
			  ingredients.size() < 8){
		ingredients.put(i.getName(), i);
		this.checkAllDiseases();
		return true;
	  } else return false;
  }

	  /**
	   * Elimina un ingrediente del plato
	   * @param name Nombre del ingrediente
	   * @return true si se ha eliminado el ingrediente correctamente, false en caso contrario
	   */
	  public boolean removeIngredient(String name) {
		if (ingredients.containsKey(name)){
			ingredients.remove(name);
			return true;
		} else return false;
	  }

	  /**
	   * Añade la posición de un caso especial al array diseases del plato.
	   * @param n la posición donde hay que añadir el caso especial
	   * @see Diseases
	   */
	  public boolean addDisease(int n) {
		  if(disease.length > n)
		  {
			  this.disease[n] = true;
			  return true;
		  }
		  else {
			  return false;
		  }
	  }

	  /**
	   * Actualiza los casos especiales del platos con los casos especiales de los ingredientes
	   * que contiene.
	   */
	  public void checkAllDiseases() {
		  ArrayList<Ingredient> ingredient_list = new ArrayList<Ingredient>((Collection<Ingredient>)ingredients.values());
		  
		  for (int i = 0; i<ingredients.size(); i++){
			  for (int j = 0; j<disease.length; j++){
				 if (ingredient_list.get(i).hasDisease(j)) addDisease(j);
			  }
		  }
		  
	  }

	  /**
	   * Comprueba si el plato tiene un determinado caso especial
	   * @param n posición del caso especial
	   */
	  public boolean hasDisease(int n) {
		 return this.disease[n];
	  }
	
	  //Gets & sets
	  public final String getName() {
	    return name;
	  }

	  public void setName(String value) {
	    name = value;
	  }

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
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

	public String getImageName() {
		return image_name;
	}

	public void setImageName(String image_name) {
		this.image_name = image_name;
	}

	public boolean[] getDisease() {
		return disease;
	}

	public void setDisease(boolean[] disease) {
		this.disease = disease;
	}
	public HashMap<String, Ingredient> getIngredients() {
		return ingredients;
	}
	public void setIngredients(HashMap<String, Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	}