
package model;

public class Ingredient {
 /**
  * Nombre del ingrediente
  */
  private String name;

  /**
   * Cantidad
   */
  private int amount;
  /**
   * Array de booleanos que indica si tiene un caso especial de DISEASES
   */
  private boolean[] disease;
  /**
   * Indica si el ingrediente est� bloqueado
   */
  private boolean blocked;
/**
 * Constructor del ingrediente. Inicializa sus valores.
 * @param name Nombre del ingrediente
 */
  public Ingredient(String name){
	  this.name = name;
	  this.disease = new boolean [8];
	  this.amount = 0;
  }
  
  /**
   * A�ade una cantidad de un determinado ingrediente
   * 
   * @param n cantidad a a�adir
   */
  public void addAmount(int n) {
	  this.amount += n;
  }

  /**
   * Resta una cantidad a un determinado ingrediente
   * @param n cantidad a restar
   */
  public void removeAmount(int n) {
	  this.amount -= n;
	  if (this.amount == 0){
		  this.blocked = true;
	  }
  }

  /**
   * A�ade la posici�n de un caso especial al array diseases del ingrediente.
   * @param n la posici�n donde hay que a�adir el caso especial
   * @see Diseases
   */
  public void addDisease(int n) {
	  this.disease[n] = true;
  }

  /**
   * Elimina un caso especial a un ingrediente
   * @param n la posici�n que hay que eliminar
   * @see Diseases
   */
  public void removeDisease(int n) {
	  this.disease[n] = false;
  }

  
  /**
   * Devuelve si el ingrediente tiene un caso especial
   * @param n posici�n del caso especial
   */
  public boolean hasDisease(int n) {
	  return this.disease[n];
  }

  //Gets & sets
  
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public boolean[] getDisease() {
		return disease;
	}
	
	public void setDisease(boolean[] disease) {
		this.disease = disease;
	}

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}




}
