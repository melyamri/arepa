
package model;
/**
 * Simboliza una mesa o terminal, contine un usuario, un ID que identifica la clase como única, 
 * un status, un pedido(Si se ha realizado). Contiene los métodos necesarios 
 * @author Grupo 9
 *
 */
public class Station {
	/**
	 * Identificador de la Station
	 */
  private String station_id;
  /**
	 * Usuario logueado
	 */
  private Actor actor;
	/**
	 * Estado de la mesa
	 */
  private StatusStruct status;
  /**
	 * Pedido actual
	 */
  private Order order;
  
  /** 
   * Constructor de la clase Station con parámetros
   * @param id ide la mesa
   * @param actor usuario de la mesa
   * @param status estado de la mesa
   */
public Station (String id, Actor actor, StatusStruct status){
	  this.station_id = id;
	  this.actor = actor;
	  this.status = status;
  }
/**
 * Constructor de la clase Station sólo con el parámetro ID
 */
public Station (String id){
	this.station_id = id;
	
	this.status = StatusStruct.ACTIVE;
}

//Gets y Sets
public void setStationId(String name) {
	  this.station_id = name;
  }

public String getStationId() {
	  return this.station_id;
  }

public Actor getActor() {
	  return this.actor;
  }


public void setActor(Actor actor) {
	  this.actor = actor;
  }

public StatusStruct getStatus() {
	  return this.status;
  }


public void setStatus(StatusStruct status) {
	  this.status = status;
  }

public Order getOrder() {
	return order;
}

public void setOrder(Order order) {
	this.order = order;
}

}
