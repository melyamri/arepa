
package view;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;

import model.ActorStruct;
import controller.HelpWindowController;
import controller.TheWindowController;
/**
 * Ventana que muestra la informaci�n de ayuda
 */
public class HelpWindow extends TheWindow{
	/**
	 * Etiqueta del t�tulo de ayuda.
	 * */
	private JLabel title_help_label;
	/**
	 * Etiqueta de la informaci�n.
	 * */
	private JLabel info_label;
	/**
	 * Bot�n para ver la informaci�n.
	 * */
	private RestaurantButton info_button;
	/**
	 * Bot�n para volver.
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
		
		info_button = new RestaurantButton("Informaci�n", ActorStruct.CLIENT);
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
   * Inicia la ventana de informaci�n de la clase que lo ha solicitado.
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
	"<br><br>Muestra una relaci�n de pedidos de una cierta mesa, as� como las promociones y el total del pedido." +
	"<br><br><b>Pagado</b><br><br>" +
	"Pulse el bot�n pagado para saber si el cliente ha pagado."+
	"<br><br><b>Borrar</b><br><br>" +
	"Pulse el bot�n borrar selecci�n para borrar toda la selecci�n del pedido." +
	"<br><br><b>Volver</b><br><br>" +
	"Pulse el bot�n volver si desea volver a la p�gina en la que estaba.<br></html>");
	
	
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
	"<br><br>Muestra una relaci�n de platos pedidos por los clientes y la mesa que lo ha pedido."+ 
	"<br>Tambi�n hay una relaci�n de mesas y el estado de cada una de ellas:" +
	"<br><&#09 -Inactivo"+ // &#09 es el caracter tabulador en tabla ASCII
	"<br><&#09 -LLamando"+
	"<br><&#09 -Pidiendo"+
	"<br><&#09 -Esperando"+
	"<br><&#09 -Comiendo"+
	"<br>Y existe la posibilidad de activarlo"+
	"<br><br><b>Opciones de mesa</b><br><br>" +
	"Si se desean ver las opciones de una mesa introducir el n�mero de la mesa deseada."+
	"<br><br><b>Pago</b><br><br>" +
	"Se puede ejecutar el pago de una determinada mesa" +
	"<br><br><b>Perfil</b><br><br>" +
	"Pulse el bot�n perfil si desea ver o modificar su perfil."+
	"<br><br><b>Desconectar</b><br><br>" +
	"Pulse desconectar si desea cerrar la sesi�n de usuario actual.<br></html>");
	
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
	
	info_label = new JLabel (	//Creo que el boton de ayuda est� roto
	"<html><b>Usuarios</b>"+
	"<br><br>Muestra los usuarios conectados junto con:" +
	"<br><&#09 -Nombre"+
	"<br><&#09 -Mesa"+
	"<br><&#09 -Tipo"+
	"<br><&#09 -Opci�n de desconectarlo"+
	"<br><br><b>Modificar/Borrar usuario</b><br><br>" +
	"Si se desea modificar o eliminar un usuario introducir su nombre."+
	"<br><br><b>Volver</b><br><br>" +
	"Pulse el bot�n volver si desea volver a la p�gina en la que estaba.<br></html>");
	
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
	"<br><br>Muestra una relaci�n de platos pedidos, as� como la mesa que lo ha pedido y el tiempo en cola de los platos." +
	"<br><br><b>Eliminar pedido</b><br><br>" +
	"Si el plato ya ha sido entregado a la mesa correspondiente, se debe eliminar de la lista."+
	"<br><br><b>Ingredientes</b><br><br>" +
	"Existe una relaci�n de ingredientes y de cantidad de cada uno de ellos." +	
	"<br><br><b>Usuarios</b><br><br>" +
	"Si se desea informaci�n de usuarios pulse el bot�n usuarios." +
	"<br><br><b>Historial</b><br><br>" +
	"Pulse el bot�n historial si desea ver un hist�rico de los �ltimos pedidos.<br></html>");
	
	info_label.setBounds((int)getWindowWidth()/4, (int)(getWindowHeight()*2)/9+55, (int)getWindowWidth(), (int)getWindowHeight());
	info_label.setVerticalAlignment(JLabel.TOP);
	info_label.setFont(new Font("Trebuchet MS", Font.PLAIN, 16));
	getContentPane().add(info_label);
	
}
/**
 * Crea una ventana de ayuda para MenuCookWindow.
 * */
private void initMenuCookWindowInfo() {
	title_help_label = new JLabel ("<html><b>Ventana de men�s y promociones</b></html>");
	title_help_label.setBounds((int)getWindowWidth()/4, (int)(getWindowHeight()*2)/9, (int)getWindowWidth(), 30);
	title_help_label.setFont(new Font("Trebuchet MS", Font.PLAIN, 22));
	getContentPane().add(title_help_label);
	
	info_label = new JLabel (	
	"<html><b>A�adir men�</b>"+
	"<br><br>Si quiere a�adir un men� introduzca:" +
	"<br><&#09 -Nombre del men�"+
	"<br><&#09 -Entrante"+
	"<br><&#09 -Primero"+
	"<br><&#09 -Segundo"+
	"<br><&#09 -Postre"+
	"<br><&#09 -Precio"+
	"<br><br>Pulse el bot�n a�adir men�." +
	"<br><br><b>A�adir promoci�n</b><br><br>" +
	"Si desea a�adir alguna promoci�n introduzca:"+
	"<br><&#09 -Nombre de la promoci�n."+
	"<br><&#09 -Plato al que se le aplica la promoci�n."+
	"<br><&#09 -Descuento aplicado en %."+
	"<br><br>Pulse el bot�n a�adir descuento." +
	"<br><br><b>Borrar men�</b><br><br>" +
	"Si se desea borrar un men� seleccione borrar (X) dicho men�." +	
	"<br><br><b>Borrar promoci�n</b><br><br>" +
	"Si desea borrar una promoci�n seleccione borrar (X) dicha promoci�n.<br></html>");
	
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
	"<br>Pulse en alg�n plato si se desea borrar dicho plato del restaurante, porque no se disponga de �l." +
	"<br><b>Ingrediente</b><br>" +
	"Pulse en alg�n ingrediente si se desea borrar dicho ingrediente, porque no se disponga de �l.<br>(Existe una relaci�n de cantidades relativas a cada ingrediente)."+
	"<br><b>Men�/Promo</b><br>" +
	"Si desea saber los men�s o las promociones existente pulse el bot�n men�/Promo." +	
	"<br><b>Usuarios</b><br>" +
	"Si se desea informaci�n de usuarios pulse el bot�n usuarios." +
	"<br><b>Historial</b><br>" +
	"Pulse el bot�n historial si desea ver un hist�rico de los �ltimos pedidos." +
	"<br><b>A�adir plato</b><br>" +
	"Si desea a�adir un plato inserte:" +
	"<br><&#09 -Nombre del plato"+
	"<br><&#09 -Ingredientes principales"+
	"<br><&#09 -Resto de ingredientes"+
	"<br><&#09 -Procedencia"+
	"<br><&#09 -Precio"+
	"<br><&#09 -Informaci�n adicional"+
	"Pulse el bot�n a�adir plato." +
	"<br><b>A�adir ingrediente</b><br>" +
	"Si se desea a�adir un ingrediente inserte:" +
	"<br><&#09 -Nombre"+
	"<br><&#09 -Cantidad "+
	"Pulse  a�adir ingrediente." +
	"<br><b>Comenzar servicio</b><br>" +
	"Para comenzar el servicio pulse comenzar servicio" +
	"<br><b>Desconectar</b><br>" +
	"Pulse desconectar si desea cerrar la sesi�n de usuario actual.<br></html>");
	
	info_label.setBounds((int)getWindowWidth()/4, (int)(getWindowHeight()*2)/9+55, (int)getWindowWidth(), (int)getWindowHeight());
	info_label.setVerticalAlignment(JLabel.TOP);
	info_label.setFont(new Font("Trebuchet MS", Font.PLAIN, 16));
	getContentPane().add(info_label);
	
}
/**
 * Crea una ventana de ayuda para PromoWindow.
 * */
private void initPromoWindowInfo() {
	title_help_label = new JLabel ("<html><b>Ventana de selecci�n de promociones</b></html>");
	title_help_label.setBounds((int)getWindowWidth()/4, (int)(getWindowHeight()*2)/9, (int)getWindowWidth(), 30);
	title_help_label.setFont(new Font("Trebuchet MS", Font.PLAIN, 22));
	getContentPane().add(title_help_label);
	
	info_label = new JLabel (	
	"<html><b>Promociones</b>"+
	"<br><br>Se muestran las promociones relativas al pedido realizado, entre las cuales se puede seleccionar la que m�s le interesa." +
	"<br><br><b>Finalizar pedido</b><br><br>" +
	"Si desea pedir la cuenta con las promociones elegidas."+
	"<br><br><b>Llamar camarero</b><br><br>" +
	"Si necesita ayuda pulse el bot�n llamar camarero." +	
	"<br><br><b>Volver</b><br><br>" +
	"Pulse el bot�n volver si desea volver a la p�gina en la que estaba.<br></html>");
	
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
	"<br><br>Si tiene m�s hambre pulse el bot�n &#8220Pedir m�s&#8221 ." + // &#8220 y &#8221 son los caracteres de comillas abierta y comillas cerradas respectivamente
	"<br><br>Si ha terminado pulse el bot�n &#8220La cuenta&#8221 para que te traigan la cuenta." +
	"<br><br>Si necesita ayuda llame al camarero d�ndole al bot�n &#8220Llamar camarero&#8221." +
	"<br><br><b>Perfil</b><br><br>" +
	"Pulse el bot�n perfil si desea ver o modificar su perfil."+
	"<br><br><b>Desconectar</b><br><br>" +
	"Pulse desconectar si desea cerrar la sesi�n de usuario actual.<br></html>");
	
	info_label.setBounds((int)getWindowWidth()/4, (int)(getWindowHeight()*2)/9+55, (int)getWindowWidth(), (int)getWindowHeight());
	info_label.setVerticalAlignment(JLabel.TOP);
	info_label.setFont(new Font("Trebuchet MS", Font.PLAIN, 16));
	getContentPane().add(info_label);
	
}
/**
 * Crea una ventana de ayuda para InfoWindow.
 * */
private void initInfoWindowInfo() {
	title_help_label = new JLabel ("<html><b>Ventana de informaci�n de plato</b></html>");
	title_help_label.setBounds((int)getWindowWidth()/4, (int)(getWindowHeight()*2)/9, (int)getWindowWidth(), 30);
	title_help_label.setFont(new Font("Trebuchet MS", Font.PLAIN, 22));
	getContentPane().add(title_help_label);
	
	info_label = new JLabel (	
	"<html><b>Informaci�n</b>"+
	"<br><br>Se muestra una imagen y una breve informaci�n sobre el plato en cuesti�n:" +
	"<br><&#09 -Ingredientes"+
	"<br><&#09 -Procedencia"+
	"<br><&#09 -Informaci�n adicional."+
	"<br><br><b>Favoritos</b><br><br>" +
	"Si desea a�adir el plato a nuestra selecci�n de platos favoritos."+
	"<br><br><b>Seleccionar</b><br><br>" +
	"Si desea pedir el plato pulse el bot�n seleccionar." +	
	"<br><br><b>Volver</b><br><br>" +
	"Pulse el bot�n volver si desea volver a la p�gina en la que estaba.<br></html>");
	
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
	"<br><br>Muestra el historial de platos que has pedido en visitas anteriores o en la actual, as� como sus precios relativos." +
	"<br><br><b>Volver</b><br><br>" +
	"Pulse el bot�n volver si desea volver a la p�gina en la que estaba.<br></html>");
	
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
	"<br><br>Muestra la selecci�n de tus platos preferidos, que has seleccionado en visitas anteriores o bien en la actual." +
	"<br><br><b>Borrar </b><br><br>" +
	"Si desea borrar platos favoritos de su lista, selecci�nelo y despu�s pulse borrar."+
	"<br><br><b>Volver</b><br><br>" +
	"Pulse el bot�n volver si desea volver a la p�gina en la que estaba.<br></html>");
	
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
	"<br><br>Introduzca los diferentes datos personales que se le piden si a�n no los ha insertado o modif�quelos." +
	"<br><br><b>Guardar</b><br><br>" +
	"Si ha modificado datos pulse el bot�n guardar."+
	"<br><br><b>Volver</b><br><br>" +
	"Pulse el bot�n volver si desea volver a la p�gina en la que estaba.<br></html>");
	
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
	"<br><&#09 -Tipo de plato: plato, men�, favoritos"+
	"<br><&#09 -Ingrediente base: pollo, ternera, pasta..."+
	"<br><&#09 -Cocina vegetariana, sin lactosa, para diab�ticos, sin gluten, al�rgicos al pescado..."+
	"<br><br><b>Seleccionar plato</b><br>" +
	"Pulse sobre el bot�n del plato deseado y le aparecer� la informaci�n relativa a cada plato: ingredientes, procedencia e informaci�n adicional.<br>"+
	"Pulse seleccionar para a�adirlo al pedido."+
	"<br><br><b>Eliminar plato</b><br>" +
	"En el caso de que ya no desee pedir un plato que haya seleccionado, con pulsar el bot�n X podr� eliminarlo de la lista de pedido."+
	"<br><br><b>Historial</b><br>" +
	"Pulse el bot�n historial si desea ver un hist�rico de sus �ltimos pedidos."+
	"<br><br><b>Favoritos</b><br>" +
	"Pulse el bot�n favoritos si desea ver o eliminar sus platos favoritos."+
	"<br><br><b>Perfil</b><br>" +
	"Pulse el bot�n perfil si desea ver o modificar su perfil."+
	"<br><br><b>Desconectar</b><br>" +
	"Pulse desconectar si desea cerrar la sesi�n de usuario actual."+
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
	"<html><b>Bienvenido, �ya tiene una cuenta?</b>"+
			"<br><&#09 <b>Si</b>"+
			"<br><br>Inserte el usuario y la contrase�a y pulsa continuar." +
			"<br><&#09 <b>No</b>"+
			"<br><br>Indique el nombre del usuario an�nimo para continuar con la aplicaci�n.<br></html>");
	
	info_label.setBounds((int)getWindowWidth()/4, (int)(getWindowHeight()*2)/9+55, (int)getWindowWidth(), (int)getWindowHeight());
	info_label.setVerticalAlignment(JLabel.TOP);
	info_label.setFont(new Font("Trebuchet MS", Font.PLAIN, 16));
	getContentPane().add(info_label);
	
}
/**
 * Crea una ventana de ayuda para FirstWindow.
 * */
private void initFirstWindownfo() {
	title_help_label = new JLabel ("<html><b>Ventana de conexi�n al sistema</b></html>");
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
	
	title_help_label = new JLabel ("<html><b>Informaci�n acerca de AREPA</b></html>");
	title_help_label.setBounds((int)getWindowWidth()/4, (int)(getWindowHeight()*2)/9, (int)getWindowWidth(), 30);
	title_help_label.setFont(new Font("Trebuchet MS", Font.PLAIN, 22));
	getContentPane().add(title_help_label);
	
	info_label = new JLabel (	
	"<html>Aplicaci�n hecha por el Grupo 9 de Ingenier�a del Software (2012-2013) de la Facultad de Inform�tica de la UCM. <br><br>" +
	"Los miembros del grupo son: <br>" +
	"Rodrigo Crespo Cepeda <br>" +
	"Meriem El Yamri <br>" +
	"Ismael Gonjal Montero <br>" +
	"Marina Jim�nez Garrido<br>" +
	"Alexandra P�rez Bermejo<br>" +
	"�ngel Fern�ndez-Villaba Paniagua<br>" +
	"Pablo Mac-Veigh Garc�a-Lastra<br>" +
	"David Garc�a Dom�nguez<br>" +
	"Jorge Alonso Fern�ndez<br>" +
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
