
package view;

import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;

import model.ActorStruct;
import model.Ingredient;
import model.Menu;
import model.ObservableRestaurant;
import model.Plate;

import controller.InfoWindowController;
import controller.TheWindowController;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.InputStream;
/**
 * Ventana que muestra toda la informacion sobre los platos y los menus.
 *
 */
public class InfoWindow extends TheWindow{
	
	/**
	   * Etiquetas con los diferentes campos.
	   */
	  private JLabel[] plate_labels;
	  /**
	   * Etiquetas con la informacion de los campos.
	   */
	  private JLabel[] text_labels;
	  /**
	   * Etiqueta con la imagen del plato.
	   */
	  private JLabel image;
	  /**
	   * Boton que guarda el plato en la lista de favoritos .
	   */
	  private RestaurantButton favourite_button;
	  /**
	   * Boton que añade un plato al pedido
	   */
	  private RestaurantButton choose_button;
	  /**
	   * Boton que te manda a la ventana anterior.
	   */
	  private RestaurantButton back_button;
	
	  /**
	   * Constructor de la ventana. Inicializa la ventana.
	   * @param p Plato del que se muestra la información
	   * @param controller Controlador de la ventana.
	   */
	public InfoWindow(Plate p, TheWindowController controller) {
		  
		super(ActorStruct.CLIENT, controller);
		super.setController(controller);
		
		getTitleLabel().setText("Información del plato");
		
		createComponents(p);
		createFavouriteButton();
		createChooseButton();
		
		setController(controller);
		controller.setView(this);
		((InfoWindowController)controller).initImage();
		((InfoWindowController)controller).checkAnonymous();
		repaint();
		
	}
	/**
	 * Constructor de la ventana. Inicializa la ventana.
	 * @param m Menu del que se muestra la información
	 * @param controller Controlador de la ventana.
	 */
	public InfoWindow(Menu m, TheWindowController controller) {
		super(ActorStruct.CLIENT, controller);
		super.setController(controller);
		
		getTitleLabel().setText("Información del menú");
		
		createComponents(m);
		createFavouriteButton();
		this.favourite_button.setVisible(false);
		createChooseButton();
		
		setController(controller);
		controller.setView(this);
		
		repaint();
	}
	/**
	 * Crea los componentes que se van a mostrar para mostrar la informacion de los platos.
	 * @param p Plato requerido.
	 */
	public void createComponents(Plate p){
		//create label ingredients
				plate_labels = new JLabel[4];
				text_labels = new JLabel[3];
				//create label plate's name
				image = new JLabel("");
				image.setOpaque(true);
				image.setBounds(100, (int)getTitleLabel().getLocation().getY() + 150, getWidth()/3, getWidth()/3);
				getContentPane().add(image);
				
				
				//debe de mostrar la imagen del plato que hemos seleccionado en la ventana anterior
				
				plate_labels[0] = new JLabel(p.getName());
				plate_labels[0].setFont(new Font("Tahoma", Font.BOLD, 30));
				plate_labels[0].setBounds((int)(image.getLocation().getX() + image.getWidth() + 200), (int)getTitleLabel().getLocation().getY() + 150, 282, 40);
				getContentPane().add(plate_labels[0]);
				
				plate_labels[1] = new JLabel("Ingredientes:");
				plate_labels[1].setFont(new Font("Tahoma", Font.BOLD, 16));
				plate_labels[1].setBounds((int)(image.getLocation().getX() + image.getWidth() + 200), (int)getTitleLabel().getLocation().getY() + 190, 135, 25);
				getContentPane().add(plate_labels[1]);
				//debe de mostrar los ingredientes del plato que hemos seleccionado en la ventana anterior
				String s ="";
				ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>((Collection<Ingredient>)p.getIngredients().values());
				for (int i = 0; i < p.getIngredients().size() ; i++){
					s += (ingredients.get(i).getName());
					if ( (i<p.getIngredients().size()-1) && (ingredients.get(i+1)!=null)) s+= ", ";
				}
				//s += ((ArrayList<Ingredient>)p.getIngredients().values()).get(p.getIngredientCount()-1).getName();
				
				text_labels[0] = new JLabel(s);
				text_labels[0].setBounds((int)(image.getLocation().getX() + image.getWidth() + 200), (int)getTitleLabel().getLocation().getY() + 230, 264, 50);
				getContentPane().add(text_labels[0]);
				
				//create label origin
				plate_labels[2] = new JLabel("Procedencia:");
				plate_labels[2].setFont(new Font("Tahoma", Font.BOLD, 16));
				plate_labels[2].setBounds((int)(image.getLocation().getX() + image.getWidth() + 200), (int)getTitleLabel().getLocation().getY() + 270, 117, 16);
				getContentPane().add(plate_labels[2]);
				//debe de mostrar la procedencia del plato que hemos seleccionado en la ventana anterior
				
				text_labels[1] = new JLabel(p.getLocation());
				text_labels[1].setBounds((int)(image.getLocation().getX() + image.getWidth() + 200), (int)getTitleLabel().getLocation().getY() + 310, 264, 27);
				getContentPane().add(text_labels[1]);
				
				//create label additional info
				plate_labels[3] = new JLabel("Informaci\u00F3n adicional:");
				plate_labels[3].setFont(new Font("Tahoma", Font.BOLD, 16));
				plate_labels[3].setOpaque(false);
				plate_labels[3].setBounds((int)(image.getLocation().getX() + image.getWidth() + 200), (int)getTitleLabel().getLocation().getY() + 350, 224, 16);
				getContentPane().add(plate_labels[3]);
				//debe de mostrar la info del plato que hemos seleccionado en la ventana anterior
				
				text_labels[2] = new JLabel("<html><body>" + p.getInfo() + "</body></html>");
				text_labels[2].setOpaque(true);
				text_labels[2].setBackground(null);
				text_labels[2].setBounds((int)(image.getLocation().getX() + image.getWidth() + 200), (int)getTitleLabel().getLocation().getY() + 390, 351, 145);
				getContentPane().add(text_labels[2]);
				
				
				//debe de mostrar el nombre del plato que hemos seleccionado en la ventana anterior
				
				
				
	}
	/**
	 * Actualiza la imagen del plato cogiéndola de la base de datos
	 * @param r el restaurante
	 */
	public void updateImage(ObservableRestaurant r){
		
		String name = plate_labels[0].getText();
		BufferedImage img = r.getDatabase().getImage( name);
		ImageIcon image_icon= new ImageIcon(img);
		image_icon = new ImageIcon(image_icon.getImage().getScaledInstance(image.getWidth(), image.getHeight(), Image.SCALE_DEFAULT));
		image.setIcon(image_icon);
	}
	/**
	 * Crea los componentes que se van a mostrar para mostrar la informacion de los menus.
	 * @param m Menu requerido.
	 */
	public void createComponents(Menu m){
		//create label ingredients
				plate_labels = new JLabel[4];
				text_labels = new JLabel[3];
				//create label plate's name
				image = new JLabel("");
				image.setBounds(100, (int)getTitleLabel().getLocation().getY() + 150, getWidth()/3, getWidth()/3);
				image.setOpaque(true);
				ImageIcon image_icon= new ImageIcon(InfoWindow.class.getResource("/imagenes/menu.png"));
				image_icon = new ImageIcon(image_icon.getImage().getScaledInstance(image.getWidth(), image.getHeight(), Image.SCALE_DEFAULT));
				image.setIcon(image_icon);	
				getContentPane().add(image);
				//debe de mostrar la imagen del plato que hemos seleccionado en la ventana anterior
				
				plate_labels[0] = new JLabel(m.getName());
				plate_labels[0].setFont(new Font("Tahoma", Font.BOLD, 30));
				plate_labels[0].setBounds((int)(image.getLocation().getX() + image.getWidth() + 200), (int)getTitleLabel().getLocation().getY() + 150, 282, 40);
				getContentPane().add(plate_labels[0]);
				
				plate_labels[1] = new JLabel("Platos:");
				plate_labels[1].setFont(new Font("Tahoma", Font.BOLD, 16));
				plate_labels[1].setBounds((int)(image.getLocation().getX() + image.getWidth() + 200), (int)getTitleLabel().getLocation().getY() + 190, 135, 25);
				getContentPane().add(plate_labels[1]);
				//debe de mostrar los ingredientes del plato que hemos seleccionado en la ventana anterior
				String s ="<html>";
				ArrayList<Plate> plates = new ArrayList<Plate>((Collection<Plate>)m.getPlates().values());
				for (int i = 0; i < m.getPlates().size() ; i++){
					s += "- " + (plates.get(i).getName());
					if ( (i<m.getPlates().size()-1) && (plates.get(i+1)!=null)) s+= "<br><br>";
				}
				s+= "</html>";
				//s += ((ArrayList<Ingredient>)p.getIngredients().values()).get(p.getIngredientCount()-1).getName();
				
				text_labels[0] = new JLabel(s);
				text_labels[0].setBounds((int)(image.getLocation().getX() + image.getWidth() + 200), (int)getTitleLabel().getLocation().getY() + 230, 264, 50);
				getContentPane().add(text_labels[0]);
				
				//create label origin
				plate_labels[2] = new JLabel("");
				plate_labels[2].setFont(new Font("Tahoma", Font.BOLD, 16));
				plate_labels[2].setBounds((int)(image.getLocation().getX() + image.getWidth() + 200), (int)getTitleLabel().getLocation().getY() + 270, 117, 16);
				getContentPane().add(plate_labels[2]);
				plate_labels[2].setVisible(false);
				//debe de mostrar la procedencia del plato que hemos seleccionado en la ventana anterior
				
				text_labels[1] = new JLabel("");
				text_labels[1].setBounds((int)(image.getLocation().getX() + image.getWidth() + 200), (int)getTitleLabel().getLocation().getY() + 310, 264, 27);
				getContentPane().add(text_labels[1]);
				text_labels[1].setVisible(false);
				
				//create label additional info
				plate_labels[3] = new JLabel("");
				plate_labels[3].setFont(new Font("Tahoma", Font.BOLD, 16));
				plate_labels[3].setBounds((int)(image.getLocation().getX() + image.getWidth() + 200), (int)getTitleLabel().getLocation().getY() + 350, 224, 16);
				getContentPane().add(plate_labels[3]);
				plate_labels[3].setVisible(false);
				
				//debe de mostrar la info del plato que hemos seleccionado en la ventana anterior
				
				text_labels[2] = new JLabel("");
				text_labels[2].setOpaque(true);
				text_labels[2].setBounds((int)(image.getLocation().getX() + image.getWidth() + 200), (int)getTitleLabel().getLocation().getY() + 390, 351, 145);
				getContentPane().add(text_labels[2]);
				text_labels[2].setVisible(false);
				
				
				//debe de mostrar el nombre del plato que hemos seleccionado en la ventana anterior
				
				
				
	}
	
	
	public JLabel[] getTextLabels() {
		return text_labels;
	}
	public void setTextLabels(JLabel[] text_labels) {
		this.text_labels = text_labels;
	}
	public void setImage(JLabel image) {
		this.image = image;
	}
	/**
	 * Crea el boton favourite_button y lo añade al panel.
	 */
	private void createFavouriteButton() {
		this.favourite_button = new RestaurantButton("Favorito", ActorStruct.CLIENT);
		this.favourite_button.setActionCommand("favourite");
		this.favourite_button.setLocation(getWidth()/2 + 300, (int) (text_labels[2].getLocation().getY() + 150));
		getContentPane().add(favourite_button);
		
		}
		
	/**
	 * Crea el boton choose_button y lo añade al panel.
	 */
	private void createChooseButton(){
		choose_button = new RestaurantButton("Seleccionar", ActorStruct.CLIENT);
		this.choose_button.setActionCommand("choose");
		this.choose_button.setLocation(getWidth()/2 - 100, (int)(favourite_button.getLocation().getY() + favourite_button.getHeight() + 50));
		getContentPane().add(choose_button);
		
		}
		
	
	@Override
	public void addMenuButtons(ActorStruct actor){
	//create back button
	//vuelve a la pagina anterior

	this.back_button = new RestaurantButton("Volver", actor);
	this.back_button.setActionCommand("back");	
	getMenuPanel().add(back_button);
}
@Override
public void setController(EventListener controller) {
	super.setController(controller);
	favourite_button.addActionListener((ActionListener)controller);
	choose_button.addActionListener((ActionListener)controller);
	back_button.addActionListener((ActionListener)controller);
	
}


public JLabel[] getPlateLabels() {
	return plate_labels;
}
public void setPlate_labels(JLabel[] labels) {
	this.plate_labels = labels;
}
@Override
public void update(Observable arg0, Object arg1) {
	// TODO Auto-generated method stub
	
}
public RestaurantButton getFavouriteButton() {
	return favourite_button;
}
}
