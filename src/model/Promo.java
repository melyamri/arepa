
package model;
/**
 * Esta clase representa las promociones que se aplican sobre los platos.
 * @author Grupo 9
 *
 */
public class Promo {
	  /**
	   * El nombre de la promoción
	   */
  private String name;
  /**
   * El porcentaje de descuento aplicado al plato. No puede ser superior 
   * a 100 ni inferior a 0.
   */
  private int discount;

  /**
   * El plato al cual el descuento se aplica
   */
  private Plate plate;

  /**
   * Constructor de la clase promo
   * @param name nombre de la promoción
   * @param discount porcentaje del descuento
   */
  public Promo(String name, int discount, Plate plate) {
	  this.name = name;
	  this.discount = discount;
	  this.plate = plate;
  }

  public String getName() {
	  return this.name;
  }

  public void setName(String name) {
	  this.name = name;
  }

  public int getDiscount() {
	  return this.discount;
  }

  public void setDiscount(int discount) {
	  this.discount = discount;
  }

  public Plate getPlate() {
	  return plate;
  }

  public void setPlate(Plate plate) {
	  this.plate = plate;
  }

}
