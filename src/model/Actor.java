
package model;


import java.util.*;
/**
 * Clase que encapsula el usuario registrado, contiene sus datos personales, y permite hacer cambios sobre sus platos favoritos
 * y el pedido que est� realizando. 
 * @author 
 *
 */
public class Actor {
  /**
   * Alias de usuario
   */
  private String user;

  /**
   * Tipo de actor, puede ser CLIENT, COOK o WAITER
   */
  private ActorStruct actor_class;

  /**
   * Contrase�a de usuario
   */
  private String password;

  /**
   * Nombre del usuario
   */
  private String name;

  /**
   * Apellidos del Usuario
   */
  private String surname;

  /**
   * Fecha de nacimiento del usuario
   */
  private int phone;

  /**
   * Direcci�n del usuario
   */
  private String street;

  /**
   * G�nero, puede ser MALE o FEMALE
   */
  private SexStruct sex;

  /**
   * Email del usuario
   */
  private String email;

  /**
   * Lista de platos favoritos, el m�ximo es 5
   */
  private HashMap<String,Plate> favourites;

  /**
   * Lista de los �ltimos pedidos, el m�ximo es 6
   */
  private List<Order> lastorders;
  
  /**
   * Contructor de la clase, inicializa todos los par�metros del usuario.
   * 
   * @param user Alias 
   * @param actor_class Tipo de Actor
   * @param password Contrase�a
   * @param name Nombre
   * @param surname Apellidos
   * @param birth Fecha de Nacimiento
   * @param street Direcci�n
   * @param sex G�nero
   * @param email Email
   */
  public Actor(String user, ActorStruct actor_class, String password, String name, String surname, int phone, String street, SexStruct sex, String email){
	this.user = user;
	this.actor_class = actor_class;
	this.password = password;
	this.name = name;
	this.surname = surname;
	this.phone = phone;
	this.street = street;
	this.sex = sex;
	this.email = email;
	this.favourites = new HashMap<String,Plate>();
	this.lastorders = new ArrayList<Order>();
  }

  /**
   * A�ade un plato a la lista de favoritos del usuario. Si la lista est� llena (<5) devuelve false, en caso contrario
   * devuelve true.
   * @param p Plato a a�adir
   */
  public boolean addFavourite(Plate p) {
	  
	  if (!favourites.containsKey(p.getName()) && favourites.size() < 5){
		  favourites.put(p.getName(), p);
		  return true;
	  } else return false;
  }

  /**
   * Elimina un plato de la lista de favoritos del usuario. Si el plato no existe, devuelve false, en caso contrario
   * devuelve true.
   * @param name Nombre del plato
   */
  public boolean removeFavourite(String name) {
	  if (favourites.containsKey(name)){
		  favourites.remove(name);
		  return true;
	  } else return false;
  }

  /**
   * A�ade un pedido a la lista de �ltimos pedidos del usuario, en caso de que la lista est� llena (<6), se borra la
   * m�s antigua y se a�ade la reciente.
   * @param o Pedido a a�adir
   */
  public boolean addOrder(Order o) {
	  
	  if(lastorders.size()<6){
		  lastorders.add(o);
		  
	  }else{
		  lastorders.remove(0);
		  lastorders.add(o);
	  }
	  return true;
	  
  }

  /**
   * Elimina un pedido de la lista de �ltimos pedidos. Si el pedido no existe, devuelve false, en caso contrario
   * devuelve true.
   * @param id ID del pedido
   */
  public boolean removeOrder(int id) {
	  	  
	  int i = 0;
	  while(i<6){
		  if(lastorders.get(i).getOrderId()==id){
			  lastorders.remove(i);
			  return true;
		  }
		  i++;
	  }
	  return false;
  }

  //Gets & sets
public String getUser() {
	return user;
}

public void setUser(String user) {
	this.user = user;
}

public ActorStruct getActorClass() {
	return actor_class;
}

public void setActorClass(ActorStruct actor_class) {
	this.actor_class = actor_class;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getSurname() {
	return surname;
}

public void setSurname(String surname) {
	this.surname = surname;
}

public int getPhone() {
	return phone;
}

public void setPhone(int phone) {
	this.phone = phone;
}

public String getStreet() {
	return street;
}

public void setStreet(String street) {
	this.street = street;
}

public SexStruct getSex() {
	return sex;
}

public void setSex(SexStruct sex) {
	this.sex = sex;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public HashMap<String, Plate> getFavourites() {
	return favourites;
}

public void setFavourites(HashMap<String, Plate> favourites) {
	this.favourites = favourites;
}

public List<Order> getLastOrders() {
	return lastorders;
}

public void setLastOrders(List<Order> lastorders) {
	this.lastorders = lastorders;
}
  
  

}
