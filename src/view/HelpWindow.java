
package view;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;

import model.ActorStruct;
import controller.HelpWindowController;
import controller.TheWindowController;
/**
 * Ventana que muestra la información de ayuda
 */
public class HelpWindow extends TheWindow{
	/**
	 * Etiqueta del título de ayuda.
	 * */
	private JLabel title_help_label;
	/**
	 * Etiqueta de la información.
	 * */
	private JLabel info_label;
	/**
	 * Botón para ver la información.
	 * */
	private RestaurantButton info_button;
	/**
	 * Botón para volver.
	 * */
	private RestaurantButton back_button;
	 /**
	   * Constructor de HelpWindow. Inicializa sus valores.
	   * @param class_name String con el nombre de la clase que ha solicitado la ayuda.
	   * @param actor usuario requerido.
	   * @param controller controlador de la ventana.
	   * */
  public HelpWindow(String class_name, ActorStruct actor, TheWindowController controller) {
		super(ActorStruct.CLIENT, controller);
		getHelpButton().setVisible(false);
		getTitleLabel().setText("Ayuda");
		
		info_button = new RestaurantButton("Información", ActorStruct.CLIENT);
		//info_button.setBounds(382, 684, 89, 23);
		info_button.setLocation((int)(getWindowWidth()/2 - info_button.getWidth() - 30), (int)(getWindowHeight() - 100));
		info_button.setActionCommand("info_button");
		getContentPane().add(info_button);
		
		back_button = new RestaurantButton("Volver", ActorStruct.CLIENT);
		//back_button.setBounds(529, 684, 89, 23);
		back_button.setLocation((int)(getWindowWidth()/2 + 30), (int)(getWindowHeight()  - 100));
		back_button.setActionCommand("back_button");
		getContentPane().add(back_button);
		
		init(class_name);
		//initHelpWindowInfo();
		setController(controller);
		title_help_label.setBounds((int)getWindowWidth()/5, 50, (int)getWindowWidth(), 30);
		info_label.setBounds((int)getWindowWidth()/5, 100, (int)getWindowWidth(), (int)getWindowHeight());
		repaint();
	}
  /**
   * Inicia la ventana de información de la clase que lo ha solicitado.
   * @param class_name String con el nombre de la clase que ha solicitado la ayuda.
   * */
  public void init(String class_name){
	  
	  switch (class_name){
	  case "FirstWindow": initFirstWindownfo();break;
	  case "LoginWindow": initLoginWindowInfo();break;
	  case "OrderWindow": initOrderWindowInfo();break;
	  case "ProfileWindow": initProfileWindowInfo();break;
	  case "FavWindow": initFavWindowInfo();break;
	  case "HistoryWindow": initHistoryWindowInfo();break;
	  case "EatWindow": initEatWindowInfo();break;
	  case "InfoWindow": initInfoWindowInfo();break;
	  case "PromoWindow": initPromoWindowInfo();break;
	  case "StartCookWindow": initStartCookWindowInfo();break;
	  case "MenuCookWindow": initMenuCookWindowInfo();break;
	  case "ServiceCookWindow": initServiceCookWindowInfo();break;
	  case "UsersCookWindow": initUsersCookWindowInfo();break;
	  case "MainWaiterWindow": initMainWaiterWindowInfo();break;
	  case "PayWaiterWindow": initPayWaiterWindowInfo();break;
	  case "HelpWindow": initHelpWindowInfo();break;
	  }
	  
  }
  /**
   * Crea una ventana de ayuda para PayWaiterWindow.
   * */
private void initPayWaiterWindowInfo() {
	title_help_label = new JLabel ("<html><b>Ventana de pedidos en proceso</b></html>");
	title_help_label.setFont(new Font("Trebuchet MS", Font.PLAIN, 22));
	getContentPane().add(title_help_label);
	
	info_label = new JLabel (	
	"<html><b>Pedidos</b>"+
	"<br><br>Muestra una relación de pedidos de una cierta mesa, así como las promociones y el total del pedido." +
	"<br><br><b>Pagado</b><br><br>" +
	"Pulse el botón pagado para saber si el cliente ha pagado."+
	"<br><br><b>Borrar</b><br><br>" +
	"Pulse el botón borrar selección para borrar toda la selección del pedido." +
	"<br><br><b>Volver</b><br><br>" +
	"Pulse el botón volver si desea volver a la página en la que estaba.<br></html>");
	
	
	info_label.setVerticalAlignment(JLabel.TOP);
	info_label.setFont(new Font("Trebuchet MS", Font.PLAIN, 16));
	getContentPane().add(info_label);
	
}
/**
 * Crea una ventana de ayuda para MainWaiterWindow.
 * */
private void initMainWaiterWindowInfo() {
	title_help_label = new JLabel ("<html><b>Ventana de ayuda de Camarero</b></html>");
	title_help_label.setBounds((int)getWindowWidth()/4, (int)(getWindowHeight()*2)/9, (int)getWindowWidth(), 30);
	title_help_label.setFont(new Font("Trebuchet MS", Font.PLAIN, 22));
	getContentPane().add(title_help_label);
	
	info_label = new JLabel (	
	"<html><b>Pedidos</b>"+
	"<br><br>Muestra una relación de platos pedidos por los clientes y la mesa que lo ha pedido."+ 
	"<br>También hay una relación de mesas y el estado de cada una de ellas:" +
	"<br><&#09 -Inactivo"+ // &#09 es el caracter tabulador en tabla ASCII
	"<br><&#09 -LLamando"+
	"<br><&#09 -Pidiendo"+
	"<br><&#09 -Esperando"+
	"<br><&#09 -Comiendo"+
	"<br>Y existe la posibilidad de activarlo"+
	"<br><br><b>Opciones de mesa</b><br><br>" +
	"Si se desean ver las opciones de una mesa introducir el número de la mesa deseada."+
	"<br><br><b>Pago</b><br><br>" +
	"Se puede ejecutar el pago de una determinada mesa" +
	"<br><br><b>Perfil</b><br><br>" +
	"Pulse el botón perfil si desea ver o modificar su perfil."+
	"<br><br><b>Desconectar</b><br><br>" +
	"Pulse desconectar si desea cerrar la sesión de usuario actual.<br></html>");
	
	info_label.setBounds((int)getWindowWidth()/4, (int)(getWindowHeight()*2)/9+55, (int)getWindowWidth(), (int)getWindowHeight());
	info_label.setVerticalAlignment(JLabel.TOP);
	info_label.setFont(new Font("Trebuchet MS", Font.PLAIN, 16));
	getContentPane().add(info_label);
	
}
/**
 * Crea una ventana de ayuda para UsersCookWindow.
 * */
private void initUsersCookWindowInfo() {
	title_help_label = new JLabel ("<html><b>Ventana de usuarios conectados al sistema</b></html>");
	title_help_label.setBounds((int)getWindowWidth()/4, (int)(getWindowHeight()*2)/9, (int)getWindowWidth(), 30);
	title_help_label.setFont(new Font("Trebuchet MS", Font.PLAIN, 22));
	getContentPane().add(title_help_label);
	
	info_label = new JLabel (	//Creo que el boton de ayuda está roto
	"<html><b>Usuarios</b>"+
	"<br><br>Muestra los usuarios conectados junto con:" +
	"<br><&#09 -Nombre"+
	"<br><&#09 -Mesa"+
	"<br><&#09 -Tipo"+
	"<br><&#09 -Opción de desconectarlo"+
	"<br><br><b>Modificar/Borrar usuario</b><br><br>" +
	"Si se desea modificar o eliminar un usuario introducir su nombre."+
	"<br><br><b>Volver</b><br><br>" +
	"Pulse el botón volver si desea volver a la página en la que estaba.<br></html>");
	
	info_label.setBounds((int)getWindowWidth()/4, (int)(getWindowHeight()*2)/9+55, (int)getWindowWidth(), (int)getWindowHeight());
	info_label.setVerticalAlignment(JLabel.TOP);
	info_label.setFont(new Font("Trebuchet MS", Font.PLAIN, 16));
	getContentPane().add(info_label);
	
}
/**
 * Crea una ventana de ayuda para ServiceCookWindow.
 * */
private void initServiceCookWindowInfo() {
	title_help_label = new JLabel ("<html><b>Ventana de ayuda de Cocinero</b></html>");
	title_help_label.setBounds((int)getWindowWidth()/4, (int)(getWindowHeight()*2)/9, (int)getWindowWidth(), 30);
	title_help_label.setFont(new Font("Trebuchet MS", Font.PLAIN, 22));
	getContentPane().add(title_help_label);
	
	info_label = new JLabel (	
	"<html><b>Pedidos</b>"+
	"<br><br>Muestra una relación de platos pedidos, así como la mesa que lo ha pedido y el tiempo en cola de los platos." +
	"<br><br><b>Eliminar pedido</b><br><br>" +
	"Si el plato ya ha sido entregado a la mesa correspondiente, se debe eliminar de la lista."+
	"<br><br><b>Ingredientes</b><br><br>" +
	"Existe una relación de ingredientes y de cantidad de cada uno de ellos." +	
	"<br><br><b>Usuarios</b><br><br>" +
	"Si se desea información de usuarios pulse el botón usuarios." +
	"<br><br><b>Historial</b><br><br>" +
	"Pulse el botón historial si desea ver un histórico de los últimos pedidos.<br></html>");
	
	info_label.setBounds((int)getWindowWidth()/4, (int)(getWindowHeight()*2)/9+55, (int)getWindowWidth(), (int)getWindowHeight());
	info_label.setVerticalAlignment(JLabel.TOP);
	info_label.setFont(new Font("Trebuchet MS", Font.PLAIN, 16));
	getContentPane().add(info_label);
	
}
/**
 * Crea una ventana de ayuda para MenuCookWindow.
 * */
private void initMenuCookWindowInfo() {
	title_help_label = new JLabel ("<html><b>Ventana de menús y promociones</b></html>");
	title_help_label.setBounds((int)getWindowWidth()/4, (int)(getWindowHeight()*2)/9, (int)getWindowWidth(), 30);
	title_help_label.setFont(new Font("Trebuchet MS", Font.PLAIN, 22));
	getContentPane().add(title_help_label);
	
	info_label = new JLabel (	
	"<html><b>Añadir menú</b>"+
	"<br><br>Si quiere añadir un menú introduzca:" +
	"<br><&#09 -Nombre del menú"+
	"<br><&#09 -Entrante"+
	"<br><&#09 -Primero"+
	"<br><&#09 -Segundo"+
	"<br><&#09 -Postre"+
	"<br><&#09 -Precio"+
	"<br><br>Pulse el botón añadir menú." +
	"<br><br><b>Añadir promoción</b><br><br>" +
	"Si desea añadir alguna promoción introduzca:"+
	"<br><&#09 -Nombre de la promoción."+
	"<br><&#09 -Plato al que se le aplica la promoción."+
	"<br><&#09 -Descuento aplicado en %."+
	"<br><br>Pulse el botón añadir descuento." +
	"<br><br><b>Borrar menú</b><br><br>" +
	"Si se desea borrar un menú seleccione borrar (X) dicho menú." +	
	"<br><br><b>Borrar promoción</b><br><br>" +
	"Si desea borrar una promoción seleccione borrar (X) dicha promoción.<br></html>");
	
	info_label.setBounds((int)getWindowWidth()/4, (int)(getWindowHeight()*2)/9+55, (int)getWindowWidth(), (int)getWindowHeight());
	info_label.setVerticalAlignment(JLabel.TOP);
	info_label.setFont(new Font("Trebuchet MS", Font.PLAIN, 16));
	getContentPane().add(info_label);
	
}
/**
 * Crea una ventana de ayuda para StartCookWindow.
 * */
private void initStartCookWindowInfo() {
	title_help_label = new JLabel ("<html><b>Ventana de platos e ingredientes</b></html>");
	title_help_label.setBounds((int)getWindowWidth()/4, (int)(getWindowHeight()*2)/9, (int)getWindowWidth(), 30);
	title_help_label.setFont(new Font("Trebuchet MS", Font.PLAIN, 22));
	getContentPane().add(title_help_label);
	
	info_label = new JLabel (	
	"<html><b>Plato</b>"+
	"<br>Pulse en algún plato si se desea borrar dicho plato del restaurante, porque no se disponga de él." +
	"<br><b>Ingrediente</b><br>" +
	"Pulse en algún ingrediente si se desea borrar dicho ingrediente, porque no se disponga de él.<br>(Existe una relación de cantidades relativas a cada ingrediente)."+
	"<br><b>Menú/Promo</b><br>" +
	"Si desea saber los menús o las promociones existente pulse el botón menú/Promo." +	
	"<br><b>Usuarios</b><br>" +
	"Si se desea información de usuarios pulse el botón usuarios." +
	"<br><b>Historial</b><br>" +
	"Pulse el botón historial si desea ver un histórico de los últimos pedidos." +
	"<br><b>Añadir plato</b><br>" +
	"Si desea añadir un plato inserte:" +
	"<br><&#09 -Nombre del plato"+
	"<br><&#09 -Ingredientes principales"+
	"<br><&#09 -Resto de ingredientes"+
	"<br><&#09 -Procedencia"+
	"<br><&#09 -Precio"+
	"<br><&#09 -Información adicional"+
	"Pulse el botón añadir plato." +
	"<br><b>Añadir ingrediente</b><br>" +
	"Si se desea añadir un ingrediente inserte:" +
	"<br><&#09 -Nombre"+
	"<br><&#09 -Cantidad "+
	"Pulse  añadir ingrediente." +
	"<br><b>Comenzar servicio</b><br>" +
	"Para comenzar el servicio pulse comenzar servicio" +
	"<br><b>Desconectar</b><br>" +
	"Pulse desconectar si desea cerrar la sesión de usuario actual.<br></html>");
	
	info_label.setBounds((int)getWindowWidth()/4, (int)(getWindowHeight()*2)/9+55, (int)getWindowWidth(), (int)getWindowHeight());
	info_label.setVerticalAlignment(JLabel.TOP);
	info_label.setFont(new Font("Trebuchet MS", Font.PLAIN, 16));
	getContentPane().add(info_label);
	
}
/**
 * Crea una ventana de ayuda para PromoWindow.
 * */
private void initPromoWindowInfo() {
	title_help_label = new JLabel ("<html><b>Ventana de selección de promociones</b></html>");
	title_help_label.setBounds((int)getWindowWidth()/4, (int)(getWindowHeight()*2)/9, (int)getWindowWidth(), 30);
	title_help_label.setFont(new Font("Trebuchet MS", Font.PLAIN, 22));
	getContentPane().add(title_help_label);
	
	info_label = new JLabel (	
	"<html><b>Promociones</b>"+
	"<br><br>Se muestran las promociones relativas al pedido realizado, entre las cuales se puede seleccionar la que más le interesa." +
	"<br><br><b>Finalizar pedido</b><br><br>" +
	"Si desea pedir la cuenta con las promociones elegidas."+
	"<br><br><b>Llamar camarero</b><br><br>" +
	"Si necesita ayuda pulse el botón llamar camarero." +	
	"<br><br><b>Volver</b><br><br>" +
	"Pulse el botón volver si desea volver a la página en la que estaba.<br></html>");
	
	info_label.setBounds((int)getWindowWidth()/4, (int)(getWindowHeight()*2)/9+55, (int)getWindowWidth(), (int)getWindowHeight());
	info_label.setVerticalAlignment(JLabel.TOP);
	info_label.setFont(new Font("Trebuchet MS", Font.PLAIN, 16));
	getContentPane().add(info_label);
	
}
/**
 * Crea una ventana de ayuda para EatWindow.
 * */
private void initEatWindowInfo() {
	title_help_label = new JLabel ("<html><b>Ventana de pedido procesado</b></html>");
	title_help_label.setBounds((int)getWindowWidth()/4, (int)(getWindowHeight()*2)/9, (int)getWindowWidth(), 30);
	title_help_label.setFont(new Font("Trebuchet MS", Font.PLAIN, 22));
	getContentPane().add(title_help_label);
	
	info_label = new JLabel (	
	"<html><b>Seleccionar</b>"+
	"<br><br>Si tiene más hambre pulse el botón &#8220Pedir más&#8221 ." + // &#8220 y &#8221 son los caracteres de comillas abierta y comillas cerradas respectivamente
	"<br><br>Si ha terminado pulse el botón &#8220La cuenta&#8221 para que te traigan la cuenta." +
	"<br><br>Si necesita ayuda llame al camarero dándole al botón &#8220Llamar camarero&#8221." +
	"<br><br><b>Perfil</b><br><br>" +
	"Pulse el botón perfil si desea ver o modificar su perfil."+
	"<br><br><b>Desconectar</b><br><br>" +
	"Pulse desconectar si desea cerrar la sesión de usuario actual.<br></html>");
	
	info_label.setBounds((int)getWindowWidth()/4, (int)(getWindowHeight()*2)/9+55, (int)getWindowWidth(), (int)getWindowHeight());
	info_label.setVerticalAlignment(JLabel.TOP);
	info_label.setFont(new Font("Trebuchet MS", Font.PLAIN, 16));
	getContentPane().add(info_label);
	
}
/**
 * Crea una ventana de ayuda para InfoWindow.
 * */
private void initInfoWindowInfo() {
	title_help_label = new JLabel ("<html><b>Ventana de información de plato</b></html>");
	title_help_label.setBounds((int)getWindowWidth()/4, (int)(getWindowHeight()*2)/9, (int)getWindowWidth(), 30);
	title_help_label.setFont(new Font("Trebuchet MS", Font.PLAIN, 22));
	getContentPane().add(title_help_label);
	
	info_label = new JLabel (	
	"<html><b>Información</b>"+
	"<br><br>Se muestra una imagen y una breve información sobre el plato en cuestión:" +
	"<br><&#09 -Ingredientes"+
	"<br><&#09 -Procedencia"+
	"<br><&#09 -Información adicional."+
	"<br><br><b>Favoritos</b><br><br>" +
	"Si desea añadir el plato a nuestra selección de platos favoritos."+
	"<br><br><b>Seleccionar</b><br><br>" +
	"Si desea pedir el plato pulse el botón seleccionar." +	
	"<br><br><b>Volver</b><br><br>" +
	"Pulse el botón volver si desea volver a la página en la que estaba.<br></html>");
	
	info_label.setBounds((int)getWindowWidth()/4, (int)(getWindowHeight()*2)/9+55, (int)getWindowWidth(), (int)getWindowHeight());
	info_label.setVerticalAlignment(JLabel.TOP);
	info_label.setFont(new Font("Trebuchet MS", Font.PLAIN, 16));
	getContentPane().add(info_label);
	
}
/**
 * Crea una ventana de ayuda para HistoryWindow.
 * */
private void initHistoryWindowInfo() {
	title_help_label = new JLabel ("<html><b>Ventana de historial</b></html>");
	title_help_label.setBounds((int)getWindowWidth()/4, (int)(getWindowHeight()*2)/9, (int)getWindowWidth(), 30);
	title_help_label.setFont(new Font("Trebuchet MS", Font.PLAIN, 22));
	getContentPane().add(title_help_label);
	
	info_label = new JLabel (	
	"<html><b>Historial</b>"+
	"<br><br>Muestra el historial de platos que has pedido en visitas anteriores o en la actual, así como sus precios relativos." +
	"<br><br><b>Volver</b><br><br>" +
	"Pulse el botón volver si desea volver a la página en la que estaba.<br></html>");
	
	info_label.setBounds((int)getWindowWidth()/4, (int)(getWindowHeight()*2)/9+55, (int)getWindowWidth(), (int)getWindowHeight());
	info_label.setVerticalAlignment(JLabel.TOP);
	info_label.setFont(new Font("Trebuchet MS", Font.PLAIN, 16));
	getContentPane().add(info_label);
	
}
/**
 * Crea una ventana de ayuda para FavWindow.
 * */
private void initFavWindowInfo() {
	title_help_label = new JLabel ("<html><b>Ventana de platos favoritos</b></html>");
	title_help_label.setBounds((int)getWindowWidth()/4, (int)(getWindowHeight()*2)/9, (int)getWindowWidth(), 30);
	title_help_label.setFont(new Font("Trebuchet MS", Font.PLAIN, 22));
	getContentPane().add(title_help_label);
	
	info_label = new JLabel (	
	"<html><b>Favoritos</b>"+
	"<br><br>Muestra la selección de tus platos preferidos, que has seleccionado en visitas anteriores o bien en la actual." +
	"<br><br><b>Borrar </b><br><br>" +
	"Si desea borrar platos favoritos de su lista, selecciónelo y después pulse borrar."+
	"<br><br><b>Volver</b><br><br>" +
	"Pulse el botón volver si desea volver a la página en la que estaba.<br></html>");
	
	info_label.setBounds((int)getWindowWidth()/4, (int)(getWindowHeight()*2)/9+55, (int)getWindowWidth(), (int)getWindowHeight());
	info_label.setVerticalAlignment(JLabel.TOP);
	info_label.setFont(new Font("Trebuchet MS", Font.PLAIN, 16));
	getContentPane().add(info_label);
	
}
/**
 * Crea una ventana de ayuda para ProfileWindow.
 * */
private void initProfileWindowInfo() {
	title_help_label = new JLabel ("<html><b>Ventana de perfil</b></html>");
	title_help_label.setBounds((int)getWindowWidth()/4, (int)(getWindowHeight()*2)/9, (int)getWindowWidth(), 30);
	title_help_label.setFont(new Font("Trebuchet MS", Font.PLAIN, 22));
	getContentPane().add(title_help_label);
	
	info_label = new JLabel (	
	"<html><b>Introducir/Modificar datos personales</b>"+
	"<br><br>Introduzca los diferentes datos personales que se le piden si aún no los ha insertado o modifíquelos." +
	"<br><br><b>Guardar</b><br><br>" +
	"Si ha modificado datos pulse el botón guardar."+
	"<br><br><b>Volver</b><br><br>" +
	"Pulse el botón volver si desea volver a la página en la que estaba.<br></html>");
	
	info_label.setBounds((int)getWindowWidth()/4, (int)(getWindowHeight()*2)/9+55, (int)getWindowWidth(), (int)getWindowHeight());
	info_label.setVerticalAlignment(JLabel.TOP);
	info_label.setFont(new Font("Trebuchet MS", Font.PLAIN, 16));
	getContentPane().add(info_label);
	
}
/**
 * Crea una ventana de ayuda para OrderWindow.
 * */
private void initOrderWindowInfo() {
	title_help_label = new JLabel ("<html><b>Ventana de realizar pedido</b></html>");
	title_help_label.setBounds((int)getWindowWidth()/4, (int)(getWindowHeight()*2)/9, (int)getWindowWidth(), 30);
	title_help_label.setFont(new Font("Trebuchet MS", Font.PLAIN, 22));
	getContentPane().add(title_help_label);
	
	info_label = new JLabel (	
	"<html><b>Filtrar</b>"+
	"<br>Seleccione entre las diferentes opciones de filtrado para buscar el plato que desea pedir:" +
	"<br><&#09 -Tipo de plato: plato, menú, favoritos"+
	"<br><&#09 -Ingrediente base: pollo, ternera, pasta..."+
	"<br><&#09 -Cocina vegetariana, sin lactosa, para diabéticos, sin gluten, alérgicos al pescado..."+
	"<br><br><b>Seleccionar plato</b><br>" +
	"Pulse sobre el botón del plato deseado y le aparecerá la información relativa a cada plato: ingredientes, procedencia e información adicional.<br>"+
	"Pulse seleccionar para añadirlo al pedido."+
	"<br><br><b>Eliminar plato</b><br>" +
	"En el caso de que ya no desee pedir un plato que haya seleccionado, con pulsar el botón X podrá eliminarlo de la lista de pedido."+
	"<br><br><b>Historial</b><br>" +
	"Pulse el botón historial si desea ver un histórico de sus últimos pedidos."+
	"<br><br><b>Favoritos</b><br>" +
	"Pulse el botón favoritos si desea ver o eliminar sus platos favoritos."+
	"<br><br><b>Perfil</b><br>" +
	"Pulse el botón perfil si desea ver o modificar su perfil."+
	"<br><br><b>Desconectar</b><br>" +
	"Pulse desconectar si desea cerrar la sesión de usuario actual."+
	"<br><br><b>Llamar camarero</b><br>" +
	"Pulse llamar camarero si desea ayuda del camarero.<br></html>");
	
	info_label.setBounds((int)getWindowWidth()/4, (int)(getWindowHeight()*2)/9+55, (int)getWindowWidth(), (int)getWindowHeight());
	info_label.setVerticalAlignment(JLabel.TOP);
	info_label.setFont(new Font("Trebuchet MS", Font.PLAIN, 16));
	getContentPane().add(info_label);
	
}
/**
 * Crea una ventana de ayuda para LoginWindow.
 * */
private void initLoginWindowInfo() {
	title_help_label = new JLabel ("<html><b>Ventana de login</b></html>");
	title_help_label.setBounds((int)getWindowWidth()/4, (int)(getWindowHeight()*2)/9, (int)getWindowWidth(), 30);
	title_help_label.setFont(new Font("Trebuchet MS", Font.PLAIN, 22));
	getContentPane().add(title_help_label);
	
	info_label = new JLabel (	
	"<html><b>Bienvenido, ¿ya tiene una cuenta?</b>"+
			"<br><&#09 <b>Si</b>"+
			"<br><br>Inserte el usuario y la contraseña y pulsa continuar." +
			"<br><&#09 <b>No</b>"+
			"<br><br>Indique el nombre del usuario anónimo para continuar con la aplicación.<br></html>");
	
	info_label.setBounds((int)getWindowWidth()/4, (int)(getWindowHeight()*2)/9+55, (int)getWindowWidth(), (int)getWindowHeight());
	info_label.setVerticalAlignment(JLabel.TOP);
	info_label.setFont(new Font("Trebuchet MS", Font.PLAIN, 16));
	getContentPane().add(info_label);
	
}
/**
 * Crea una ventana de ayuda para FirstWindow.
 * */
private void initFirstWindownfo() {
	title_help_label = new JLabel ("<html><b>Ventana de conexión al sistema</b></html>");
	title_help_label.setBounds((int)getWindowWidth()/4, (int)(getWindowHeight()*2)/9, (int)getWindowWidth(), 30);
	title_help_label.setFont(new Font("Trebuchet MS", Font.PLAIN, 22));
	getContentPane().add(title_help_label);
	
	info_label = new JLabel (	
	"<html><b>Nombre/IP del ordenador cocina</b>"+
	"<br><br>Introduzca el nombre o ip del ordenador cocina." +
	"<br><br><b>Este ordenador es:</b><br><br>" +
	"<br><&#09 -Cocina"+
	"<br><&#09 -Camarero"+
	"<br><&#09 -Cliente"+
	"<br><br><b>Conectar</b><br><br>" +
	"Si los datos introducidos son los correctos haga click para conectarse.<br></html>");
	
	info_label.setBounds((int)getWindowWidth()/4, (int)(getWindowHeight()*2)/9+55, (int)getWindowWidth(), (int)getWindowHeight());
	info_label.setVerticalAlignment(JLabel.TOP);
	info_label.setFont(new Font("Trebuchet MS", Font.PLAIN, 16));
	getContentPane().add(info_label);
	
}
/**
 * Crea una ventana de ayuda para HelpWindow.
 * */
public void initHelpWindowInfo(){
	
	title_help_label = new JLabel ("<html><b>Información acerca de AREPA</b></html>");
	title_help_label.setBounds((int)getWindowWidth()/4, (int)(getWindowHeight()*2)/9, (int)getWindowWidth(), 30);
	title_help_label.setFont(new Font("Trebuchet MS", Font.PLAIN, 22));
	getContentPane().add(title_help_label);
	
	info_label = new JLabel (	
	"<html>Aplicación hecha por el Grupo 9 de Ingeniería del Software (2012-2013) de la Facultad de Informática de la UCM. <br><br>" +
	"Los miembros del grupo son: <br>" +
	"Rodrigo Crespo Cepeda <br>" +
	"Meriem El Yamri <br>" +
	"Ismael Gonjal Montero <br>" +
	"Marina Jiménez Garrido<br>" +
	"Alexandra Pérez Bermejo<br>" +
	"Ángel Fernández-Villaba Paniagua<br>" +
	"Pablo Mac-Veigh García-Lastra<br>" +
	"David García Domínguez<br>" +
	"Jorge Alonso Fernández<br>" +
	"<br> Esperamos que disfrutes usando AREPA</html>");
	
	info_label.setBounds((int)getWindowWidth()/4, (int)(getWindowHeight()*2)/9+55, (int)getWindowWidth(), (int)getWindowHeight());
	info_label.setVerticalAlignment(JLabel.TOP);
	info_label.setFont(new Font("Trebuchet MS", Font.PLAIN, 16));
	getContentPane().add(info_label);
	
	info_button.setVisible(false);
	info_button.setEnabled(false);
	
	back_button.setLocation((int)(getWindowWidth()/2 - back_button.getWidth()/2), (int)(getWindowHeight()  - 100));
	
	
}
@Override
public void setController(EventListener controller) {
	super.setController(controller);
	info_button.addActionListener((ActionListener)controller);
	back_button.addActionListener((ActionListener)controller);
	
}


@Override
public void update(Observable arg0, Object arg1) {
	// TODO Auto-generated method stub
	
}



}
