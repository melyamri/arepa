
package model;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

/**
 * Clase que contiene todos los métodos de acceso a la base de datos. Se encarga de inicializarla o crearla en caso de
 * que no lo esté. Tiene todas las funciones de actualizar datos en la base de datos y las funciones de leer desde la
 * base de datos.
 * 
 * @author Grupo 9
 *
 */
public class DatabaseIO {
 
	/**
	 * Dirección IP del puesto de cocinero/puesto central
	 */
  private String cook_ip;
  /**
   * Nombre de la base de datos, por defecto es "db_arepa"
   */

  private String db_name;
/**
 * Nombre del usuario de la base de datos
 */
  private String db_user;
/**
 * Contraseña de la base de datos
 */
  private String db_password;

  /**
   * Driver para poder conectar con la base de datos 
   */
  private String driver = "com.mysql.jdbc.Driver";
  /**
   * Conexión o sesión específica con la base de datos
   */
  private java.sql.Connection connection = null;

  /**
   * Método neecesario para conectarse al Driver y poder usar MySQL. Se encarga de conectar con el servidor.
   */
  public void connectToServer() {
      try {
          Class.forName(driver);
          String server = "jdbc:mysql://"+cook_ip+"/";
          connection = DriverManager.getConnection(server, db_user, db_password);
      } catch (Exception e) {
          System.out.println("Error: Imposible realizar la conexion a BD.");
          e.printStackTrace();
      }
  }

  /**
   * Método para establecer la conexión con la base de datos.
   *
   * @return st Objeto para ejecutar una sentencia SQL
   */
  private Statement connectStatement() {
      Statement st = null;
      try {
          st = connection.createStatement();
      } catch (SQLException e) {
          System.out.println("Error: Conexión incorrecta.");
          e.printStackTrace();
      }
      return st;
  }
  
  /**
   * Métododo que inicializa Statement para poder utilizar sentencias SQL, y establece la base de datos 
   * que va a ser usada.
   * @return st Objeto para poder ejecutar sentencias SQL
   */
  private Statement initStatement(){
	  Statement st = connectStatement();
	  update(st,"USE " + db_name);
	  return st;
	
  }

  /**
   * Método para realizar consultas del tipo: SELECT * FROM tabla WHERE..."
   *
   * @param st Statement
   * @param cadena La consulta en concreto
   * @return rs ResultSet, Resultado de la ejecución de la consulta
   */
  private static ResultSet query(Statement st, String string) {
      ResultSet rs = null;
      try {

          rs = st.executeQuery(string);
      } catch (SQLException e) {
          System.out.println("Error con: " + string);
          System.out.println("SQLException: " + e.getMessage());
          e.printStackTrace();
      }
      return rs;
  }

  /**
   * Método para realizar consultas de actualización, creación o eliminación.
   *
   * @param st Statement
   * @param cadena La consulta en concreto
   * @returnrs ResultSet, Resultado de la ejecución del update
   */
  private static int update(Statement st, String string) {
      int rs = -1;
      try {
          rs = st.executeUpdate(string);
      } catch (SQLException e) {
          System.out.println("Error con: " + string);
          System.out.println("SQLException: " + e.getMessage());
          e.printStackTrace();
      }
      return rs;
  }

  /**
   * Método para cerrar la consulta, se debe ejecutar después de cada consulta.
   *
   * @param rs Resultado de la consulta o actualización
   */
  private static void closeQuery(ResultSet rs) {
      if (rs != null) {
          try {
              rs.close();
          } catch (Exception e) {
              System.out.print("Error: No es posible cerrar la consulta.");
          }
      }
  }

  /**
   * Método para cerrar la conexión.
   *
   * @param st Statement
   */
  private static void closeStatement(java.sql.Statement st) {
      if (st != null) {
          try {
              st.close();
          } catch (Exception e) {
              System.out.print("Error: No es posible cerrar la conexión.");
          }
      }
  }

  
  
  /**
   * Inicializa la conexión SQL, si la conexión se inicia correctamente, devuelve verdadero, sino devuelve falso.
   */
  public boolean initSQLConnection() {
	  System.out.println("INICIO DE EJECUCIÓN.");
      connectToServer();
     // Statement st = connection();
      return true;
  }

  /**
   * Elimina la base de datos
   */
  public boolean deleteSQLDatabase() {
	Statement st = connectStatement();
	if (update(st, "DROP DATABASE IF EXISTS " + db_name) == -1) return false;
	else return true;
  }

  /**
   * Crea todas las tablas de la base de datos necesarias para almacenar los objetos de la aplicación.
   *  
   */
  public boolean createSQLTables()  {
	  int ret;
	  Statement st = connectStatement();
	  
	  ret = update(st,"CREATE DATABASE IF NOT EXISTS " + db_name);
	  
	  st = initStatement();
	  
	  if (ret != -1) ret = update(st, "CREATE TABLE IF NOT EXISTS ingredients" +
	  		" (`Name` VARCHAR(20) NOT NULL," +
	  		" `Amount` int(10) NOT NULL," +
	  		" `Blocked` tinyint(1) NOT NULL," +
	  		" `Diseases` tinyint NOT NULL," +
	  		" PRIMARY KEY (`Name`))"
	  		);
	  
	  if (ret != -1) ret = update(st, "CREATE TABLE IF NOT EXISTS images" +
		  		" (`ImageID` VARCHAR(50) NOT NULL," +
		  		" `Img` LONGBLOB NOT NULL," +
		  		" PRIMARY KEY (`ImageID`))"
			);
	  
	  if (ret != -1) ret = update(st, "CREATE TABLE IF NOT EXISTS plates" +
	  		" (`Name` VARCHAR(50) NOT NULL," +
	  		" `Location` VARCHAR(20) NOT NULL," +
	  		" `Info` VARCHAR(500) NOT NULL," +
	  		" `Price` float NOT NULL," +
	  		" `Blocked` tinyint(1) NOT NULL," +
	  		" `Diseases` tinyint NOT NULL," +
	  		" `Image_Name` VARCHAR(50) NOT NULL," +
	  		" `Ingredient_1` VARCHAR(20)," +
	  		" `Ingredient_2` VARCHAR(20)," +
	  		" `Ingredient_3` VARCHAR(20)," +
	  		" `Ingredient_4` VARCHAR(20)," +
	  		" `Ingredient_5` VARCHAR(20)," +
	  		" `Ingredient_6` VARCHAR(20)," +
	  		" `Ingredient_7` VARCHAR(20)," +
	  		" `Ingredient_8` VARCHAR(20)," +
	  		" PRIMARY KEY (`Name`)," +
	  		" FOREIGN KEY (Image_Name) REFERENCES images(ImageID) ON DELETE RESTRICT ON UPDATE CASCADE," +
	  		" FOREIGN KEY (Ingredient_1) REFERENCES ingredients(Name) ON DELETE SET NULL ON UPDATE CASCADE," +
	  		" FOREIGN KEY (Ingredient_2) REFERENCES ingredients(Name) ON DELETE SET NULL ON UPDATE CASCADE," +
	  		" FOREIGN KEY (Ingredient_3) REFERENCES ingredients(Name) ON DELETE SET NULL ON UPDATE CASCADE," +
	  		" FOREIGN KEY (Ingredient_4) REFERENCES ingredients(Name) ON DELETE SET NULL ON UPDATE CASCADE," +
	  		" FOREIGN KEY (Ingredient_5) REFERENCES ingredients(Name) ON DELETE SET NULL ON UPDATE CASCADE," +
	  		" FOREIGN KEY (Ingredient_6) REFERENCES ingredients(Name) ON DELETE SET NULL ON UPDATE CASCADE," +
	  		" FOREIGN KEY (Ingredient_7) REFERENCES ingredients(Name) ON DELETE SET NULL ON UPDATE CASCADE," +
	  		" FOREIGN KEY (Ingredient_8) REFERENCES ingredients(Name) ON DELETE SET NULL ON UPDATE CASCADE)"	  
			  );
	  
	  if (ret != -1) ret = update(st, "CREATE TABLE IF NOT EXISTS menus" +
		  		" (`Name` VARCHAR(50) NOT NULL," +
		  		" `Price` float NOT NULL," +
		  		" `Blocked` tinyint(1) NOT NULL," +
		  		" `Diseases` tinyint NOT NULL," +
		  		" `Plate_1` VARCHAR(50)," +
		  		" `Plate_2` VARCHAR(50)," +
		  		" `Plate_3` VARCHAR(50)," +
		  		" `Plate_4` VARCHAR(50)," +
		  		" `Plate_5` VARCHAR(50)," +
		  		" `Plate_6` VARCHAR(50)," +
		  		" `Plate_7` VARCHAR(50)," +
		  		" `Plate_8` VARCHAR(50)," +
		  		" PRIMARY KEY (`Name`)," +
		  		" FOREIGN KEY (Plate_1) REFERENCES plates(Name) ON DELETE SET NULL ON UPDATE CASCADE," +
		  		" FOREIGN KEY (Plate_2) REFERENCES plates(Name) ON DELETE SET NULL ON UPDATE CASCADE," +
		  		" FOREIGN KEY (Plate_3) REFERENCES plates(Name) ON DELETE SET NULL ON UPDATE CASCADE," +
		  		" FOREIGN KEY (Plate_4) REFERENCES plates(Name) ON DELETE SET NULL ON UPDATE CASCADE," +
		  		" FOREIGN KEY (Plate_5) REFERENCES plates(Name) ON DELETE SET NULL ON UPDATE CASCADE," +
		  		" FOREIGN KEY (Plate_6) REFERENCES plates(Name) ON DELETE SET NULL ON UPDATE CASCADE," +
		  		" FOREIGN KEY (Plate_7) REFERENCES plates(Name) ON DELETE SET NULL ON UPDATE CASCADE," +
		  		" FOREIGN KEY (Plate_8) REFERENCES plates(Name) ON DELETE SET NULL ON UPDATE CASCADE)"			  
				  );
	  if (ret != -1) ret = update(st, "CREATE TABLE IF NOT EXISTS promos" +
		  		" (`Name` VARCHAR(50) NOT NULL," +
		  		" `Discount` int(3) NOT NULL," +
		  		" `Plate` VARCHAR(50) NOT NULL," +
		  		" PRIMARY KEY (`Name`)," +
		  		" FOREIGN KEY (Plate) REFERENCES plates(Name) ON DELETE CASCADE ON UPDATE CASCADE)"
			  	  );
	  if (ret != -1) ret = update(st, "CREATE TABLE IF NOT EXISTS orders" +
		  		" (`OrderID` int(10) NOT NULL AUTO_INCREMENT," +
		  		" `Price` float NOT NULL," +
		  		" `Date` DATETIME NOT NULL," +
		  		" `Block_Mark` int(1) NOT NULL," +
		  		" `Menu_Count` int(1) NOT NULL," +
		  		" `Plate_Count` int(1) NOT NULL," +
		  		" `Menu_1` VARCHAR(50)," +
		  		" `Menu_2` VARCHAR(50)," +
		  		" `Menu_3` VARCHAR(50)," +
		  		" `Menu_4` VARCHAR(50)," +
		  		" `Plate_1` VARCHAR(50)," +
		  		" `Plate_2` VARCHAR(50)," +
		  		" `Plate_3` VARCHAR(50)," +
		  		" `Plate_4` VARCHAR(50)," +
		  		" `Plate_5` VARCHAR(50)," +
		  		" `Plate_6` VARCHAR(50)," +
		  		" `Plate_7` VARCHAR(50)," +
		  		" `Promo_1` VARCHAR(50)," +
		  		" `Promo_2` VARCHAR(50)," +
		  		" `Promo_3` VARCHAR(50)," +
		  		" `Promo_4` VARCHAR(50)," +
		  		" PRIMARY KEY (`OrderID`))"		  		
				  );
	  
	  if (ret != -1) ret = update(st, "CREATE TABLE IF NOT EXISTS actors" +
		  		" (`User` VARCHAR(30) NOT NULL," +
		  		" `Class` VARCHAR(6) NOT NULL," +
		  		" `Password` VARCHAR(16) NOT NULL," +
		  		" `Name` VARCHAR(20) NOT NULL," +
		  		" `Surname` VARCHAR(20) NOT NULL," +
		  		" `Phone` int(9) NOT NULL," +
		  		" `Street` VARCHAR(30) NOT NULL," +
		  		" `Sex` VARCHAR(6) NOT NULL," +
		  		" `Email` VARCHAR(30) NOT NULL," +
		  		" `Favourite_1` VARCHAR(50)," +
		  		" `Favourite_2` VARCHAR(50)," +
		  		" `Favourite_3` VARCHAR(50)," +
		  		" `Favourite_4` VARCHAR(50)," +
		  		" `Favourite_5` VARCHAR(50)," +
		  		" `Order_1` int(10)," +
		  		" `Order_2` int(10)," +
		  		" `Order_3` int(10)," +
		  		" `Order_4` int(10)," +
		  		" `Order_5` int(10)," +
		  		" `Order_6` int(10)," +
		  		" PRIMARY KEY (`User`)," +
		  		" FOREIGN KEY (Favourite_1) REFERENCES plates(Name) ON DELETE SET NULL ON UPDATE CASCADE," +
		  		" FOREIGN KEY (Favourite_2) REFERENCES plates(Name) ON DELETE SET NULL ON UPDATE CASCADE," +
		  		" FOREIGN KEY (Favourite_3) REFERENCES plates(Name) ON DELETE SET NULL ON UPDATE CASCADE," +
		  		" FOREIGN KEY (Favourite_4) REFERENCES plates(Name) ON DELETE SET NULL ON UPDATE CASCADE," +
		  		" FOREIGN KEY (Favourite_5) REFERENCES plates(Name) ON DELETE SET NULL ON UPDATE CASCADE)" 		  		  
				  );
	  
	  if (ret != -1) ret = update(st, "CREATE TABLE IF NOT EXISTS stations" +
		  		" (`StationID` VARCHAR(10) NOT NULL," +
		  		" `Status` VARCHAR(10) NOT NULL," +
		  		" `Actor` VARCHAR(30)," +
		  		" `IDOrder` int(10)," +
		  		" PRIMARY KEY (`StationID`)," +
		  		" FOREIGN KEY (Actor) REFERENCES actors(User) ON DELETE SET NULL ON UPDATE CASCADE," +
		  		" FOREIGN KEY (IDOrder) REFERENCES orders(OrderID) ON DELETE SET NULL ON UPDATE CASCADE)"    
				  );
	  if (ret != -1) ret = update(st, "CREATE TABLE IF NOT EXISTS orderedplates" +
		  		" (`Name` VARCHAR(50) NOT NULL," +
		  		" `Date` TIMESTAMP NOT NULL," +
		  		" `TableID` VARCHAR(10) NOT NULL," +
		  		" FOREIGN KEY (Name) REFERENCES plates(Name) ON DELETE CASCADE ON UPDATE CASCADE," +
		  		" FOREIGN KEY (TableID) REFERENCES stations(StationID) ON DELETE CASCADE ON UPDATE CASCADE)" 			  
				  );
	  if (ret != -1) ret = update(st, "CREATE TABLE IF NOT EXISTS finishedplates" +
		  		" (`Name` VARCHAR(50) NOT NULL," +
		  		" `Date` TIMESTAMP NOT NULL," +
		  		" `TableID` VARCHAR(10) NOT NULL," +
		  		" FOREIGN KEY (Name) REFERENCES plates(Name) ON DELETE CASCADE ON UPDATE CASCADE," +
		  		" FOREIGN KEY (TableID) REFERENCES stations(StationID) ON DELETE CASCADE ON UPDATE CASCADE)" 			  
				  );
	  if (ret != -1) ret = update(st, "CREATE TABLE IF NOT EXISTS kitchenhistory" +
		  		" (`Year` int(4) NOT NULL," +
		  		" `January` float NOT NULL," +
		  		" `February` float NOT NULL," +
		  		" `March` float NOT NULL," +
		  		" `April` float NOT NULL," +
		  		" `May` float NOT NULL," +
		  		" `June` float NOT NULL," +
		  		" `July` float NOT NULL," +
		  		" `August` float NOT NULL," +
		  		" `September` float NOT NULL," +
		  		" `October` float NOT NULL," +
		  		" `November` float NOT NULL," +
		  		" `December` float NOT NULL," +
		  		" `ThisYear` float NOT NULL," +
		  		" `LastYear` float NOT NULL," +
		  		" PRIMARY KEY (`Year`))"
			  	   );

	  if (ret != -1) ret = update(st, "CREATE TABLE IF NOT EXISTS service" +
		  		" (`Started` tinyint(1) NOT NULL," +
		  		" PRIMARY KEY (`Started`))"
			  	   );

	  
	  closeStatement(st);
	  
	  if (ret == -1) return false;
	  else return true;
  }

/**
 * Método que usa el camarero para establecer que un plato ya ha sido entregado en la mesa que lo ha pedido.
 * @param p Plato a entregar
 * @param table_id ID de la mesa
 * @return true si se ha podido hacer correctamente la entrega
 */
public boolean deliverPlate(Plate p, String table_id) {
	Statement st = initStatement();
	int rt = update(st, 
			"DELETE FROM finishedplates WHERE " +
			"Name = '" + p.getName() + 
			"' AND TableID = '" + table_id + "' LIMIT 1" 
			);
	closeStatement(st);
	if (rt != -1) return true;
	else return false;
}

/**
 * Devuelve todas las mesas existentes en la base de datos.
 */
public HashMap<String, Station> getTables() {
		Statement st = initStatement();
		ResultSet rs = query (st, "SELECT * FROM stations WHERE (NOT Status = 'COOK') AND (NOT Status = 'WAITER')");
		
		HashMap <String, Station> stations = new HashMap<String,Station>();

		try {
			while (rs.next()) {
				if (rs.getString("StationID")!= null){
					Actor a = getActor(rs.getString("Actor"));
					Station s = new Station(rs.getString("StationID"), a, StatusStruct.valueOf(rs.getString("Status")));
					s.setOrder(getOrder(rs.getInt("IDOrder")));
					stations.put(s.getStationId(), s);
				}
			} 
			closeQuery(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		closeStatement(st);
		return stations;
}

/**
 * Añade un usuario a la base de datos. Devuelve true si se ha añadido correctamente, false en caso contrario.
 * @param actor el usuario
 * 
 */
public boolean registerActor(Actor actor) {
	Statement st = initStatement();
	int rt = -1;
	Actor a = getActor(actor.getUser());
	if (a==null){
			rt = update(st, 
				"INSERT INTO actors " +
				"(User, Class, Password, Name, Surname, Phone, Street, Sex, Email)" +
				"VALUES ('"+actor.getUser()+
				"', '"+ actor.getActorClass().toString()+
				"', '"+ actor.getPassword() +
				"', '"+ actor.getName() +
				"', '"+ actor.getSurname() +
				"', '"+ actor.getPhone() +
				"', '"+ actor.getStreet() +
				"', '"+ actor.getSex().toString() +
				"', '"+ actor.getEmail() +"')"
				);
		closeStatement(st);
	} else rt = -1;
	if (rt != -1) return true;
	else return false;
}
/**
 * Actualiza un determinado usuario en la base de datos
 * @param actor el usuario
 * @return true si se ha actualizado correctamente, false en caso contrario
 */
public boolean updateActor(Actor actor) {
	Statement st = initStatement();
	String s_fav = "";
	ArrayList<Plate> fav_list = (ArrayList<Plate>) ((Collection<Plate>)actor.getFavourites().values());
	for (int i = 0; i< fav_list.size() ; i++){
		s_fav += ", Favourite_" + (i+1) + " = '" + fav_list.get(i) + "'";
	}
	String s_ord = "";
	for (int i = 0; i< actor.getLastOrders().size() ; i++){
		s_ord += ", Order_" + (i+1) + " = '" + actor.getLastOrders().get(i) + "'";
	}
	int rt = update(st, 
			"UPDATE actors SET" +
			" Class = '"+ actor.getActorClass().toString() +
			"', Password = '"+ actor.getPassword() +
			"', Name = '"	+ actor.getName() +
			"', Surname = '"+ actor.getSurname() +
			"', Phone = '"+ actor.getPhone() +
			"', Street = '"+ actor.getStreet() +
			"', Sex = '"+ actor.getSex().toString() +
			"', Email = '"+ actor.getEmail() +
			s_fav +
			s_ord +
			"' WHERE User = '" + actor.getUser() + "'"
			);
	closeStatement(st);
	if (rt != -1) return true;
	else return false;
}


/**
 * Busca un pedido en la base de datos por su ID, devuelve el pedido si existe, en caso
 * contrario, devuelve null.
 * @param order_id el ID del pedido
 * 
 */

public Order getOrder (int order_id){
	Statement st = initStatement();
	HashMap <String,Menu> menus= getMenus();
	HashMap <String,Plate> plates= getPlates();
	HashMap <String,Promo> promos= getPromos();
	ResultSet rs = query (st, "SELECT * FROM orders WHERE orders.OrderID = '" + order_id + "'");	
	
	Order o = null;
	try {
		if(rs.next()){
			o = new Order(order_id, rs.getFloat("Price"));
			o.setDate(rs.getTimestamp("Date"));
			o.setBlockMark(rs.getInt("Block_Mark"));
			int menu_count = rs.getInt("Menu_Count");
			int plate_count = rs.getInt("Plate_Count");
			
			for (int i = 0; i<menu_count; i++){
				o.addMenu(menus.get(rs.getString("Menu_" + (i+1))));
			}
			
			for (int i = 0; i<plate_count; i++){
				o.addPlate(plates.get(rs.getString("Plate_" + (i+1))));
			}
			
			for (int i = 1; i<=4; i++){
				o.addPromo(promos.get(rs.getString("Promo_" + (i))));
			}
		}
			
			closeQuery(rs); 
	} catch (SQLException e) {
		e.printStackTrace();
		return null;
	}
	closeStatement(st);
	return o;
}
/**
 * Comprueba el estado del servicio en la base de datos en la base de datos
 * @return true si ha comenzado el servicio, false en caso contrario
 */
public boolean isServiceStarted(){
	Statement st = initStatement();
	ResultSet rs = query (st, "SELECT Started FROM service");
	try {
		if (rs.next()){
			return rs.getBoolean("Started");
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
	closeQuery(rs);
	return false;
}
/**
 * Comienza el servicio en la base de datos
 * @return true si se ha comenzado correctamentem false en caso contrario
 */
public boolean startService(){
	Statement st = initStatement();
	
	int rs = update (st, "UPDATE service SET Started = '1'");
	closeStatement(st);
	if (rs == 0) return true;
	else return false;
	
}

/**
 * Termina el servicio en la base de datos
 * @return true si se ha finalizado correctamente, false en caso contrario
 */
public boolean endService(){
	Statement st = initStatement();
	int rs = update (st, "UPDATE service SET Started = '0'");
	closeStatement(st);
	if (rs == 0) return true;
	else return false;
}
/**
 * Inicializa el servicio en la base de datos
 * @return true si se ha inicializado correctamente, false en caso contrario
 */
public boolean initService(){
	Statement st = initStatement();
	int rs=-1;
	if (!isServiceStarted()){
		rs = update(st, 
					"INSERT IGNORE INTO service" +
					"(Started)" +
					" VALUES (0)"
					);
	} else endService();
	closeStatement(st);
	if (rs == 0) return true;
	else return false;
}
/**
 * Busca un usuario en la base de datos por su alias de usuario. Devuelve el actor si lo encuentra,
 * en caso contrario devuelve null.
 * @param user el alias de usuario
 * 
 */
public Actor getActor(String user) {
	Statement st = initStatement();
	HashMap <String,Plate> plates= getPlates();
	ResultSet rs = query (st, "SELECT * FROM actors WHERE actors.User = '" + user + "'");	
	
	Actor a = null;
	try {
		if (rs.next()){
			if (rs.getString("Surname") != "Anonymous"){
				a = new Actor(rs.getString("User"), ActorStruct.valueOf(rs.getString("Class")), rs.getString("Password"),
					rs.getString("Name"), rs.getString("Surname"), rs.getInt("Phone"),
					rs.getString("Street"),	SexStruct.valueOf(rs.getString("Sex")), rs.getString("Email"));
			} else a = new Actor (rs.getString("User"), ActorStruct.valueOf(rs.getString("Class")), "",
					rs.getString("Name"), "Anonymous", 0, "", SexStruct.MALE, "");
			
			for (int i = 1; i<=5; i++){
				Plate p = plates.get(rs.getString("Favourite_" + i));
				if (p != null) a.addFavourite(p);
			}
			
			ArrayList <String> o_s = new ArrayList <String> ();
			
			for (int i = 1; i<=6; i++){
				o_s.add(rs.getString("Order_" + i));
			}
			
			String s = "";
			 
			for (int i = 0; i < o_s.size() ; i++){
				//JOptionPane.showMessageDialog(null, o_s.get(i-1));
				if (o_s.get(i) != null){
					s += " orders.OrderID = '" + o_s.get(i) + "'";
					if (i < o_s.size()-1 && o_s.get(i+1)!=null) s+= " OR" ;
				}
			}
			
			if (!s.equals("")){
				rs = query (st, "SELECT orders.Price, orders.Date FROM orders WHERE" + s );
				
				int i = 0;
				while (rs.next()){
					Order o = new Order(0, 0);
					o.setOrderId(Integer.parseInt(o_s.get(i)));
					o.setPrice(rs.getFloat("Price"));
					o.setDate(rs.getDate("Date"));
					a.addOrder(o);
					i++;
				}
			}
		}
		closeQuery(rs);
	} catch (SQLException e) {
		e.printStackTrace();
		return null;
	}
	closeStatement(st);
	return a;
}

/**
 * Inicia sesión de un determinado usuario. Comprueba que el usuario exista en la base de datos, y que
 * su contraseña sea la correcta. En ese caso, devuelve el actor, en caso contrario, devuelve null.
 * @param user el alias de usuario
 * @param password la contraseña
 * 
 */
public Actor login(String user, String password) {
	Statement st = initStatement();
	ResultSet rs = query (st, "SELECT * FROM actors WHERE actors.User = '" + user + "' AND actors.Password = '" + password + "'");
	
	Actor a = null;
	try {
		if(rs.next()){
			if (rs.getString("User") != null){
				a = new Actor (rs.getString("User"), ActorStruct.valueOf(rs.getString("Class")), rs.getString("Password"), rs.getString("Name"), rs.getString("Surname"), rs.getInt("Phone"), rs.getString("Street"), SexStruct.valueOf(rs.getString("Sex")), rs.getString("Email") );
			}
		}
		closeQuery(rs);
	} catch (SQLException e) {
		e.printStackTrace();
	}
	closeStatement(st);
	return a;
}

/**
 * Inicia sesión de un  usuario anónimo.  Crea un usuario anónimo temporal que se va a eliminar cuando
 * cierre sesión. Devuelve el usuario anónimo creado.
 * @param user el alias de usuario
 *
 * 
 */
public Actor anonymousLogin(String user) {
	Statement st = initStatement();
	ResultSet rs = query (st, "SELECT * FROM actors WHERE actors.User = '" + user + "' AND actors.Surname = 'anonymous'");
	
	Actor a = null;
	try {
		rs.next();
		if (rs.getString("User") != null){
			a = new Actor (rs.getString("User"), ActorStruct.valueOf(rs.getString("Class")), rs.getString("Password"), rs.getString("Name"), rs.getString("Surname"), rs.getInt("Phone"), rs.getString("Street"), SexStruct.valueOf(rs.getString("Sex")), rs.getString("Email") );
		}
		closeQuery(rs);
	} catch (SQLException e) {
		e.printStackTrace();
	}
	closeStatement(st);
	return a;
}

/**
 * Devuelve una lista de todos los ingredientes existentes en la base de datos. 
 * 
 */
public HashMap<String, Ingredient> getIngredients() {
	Statement st = initStatement();
	ResultSet rs = query (st, "SELECT * FROM ingredients");
	
	HashMap <String, Ingredient> ingredients = new HashMap<String, Ingredient>();
	try {
		while (rs.next()) {
			if (rs.getString("Name")!= null){
				Ingredient i = new Ingredient (rs.getString("Name"));
				i.setAmount(rs.getInt("Amount"));
				i.setBlocked(rs.getBoolean("Blocked"));
				Byte b = rs.getByte("Diseases");
			    boolean bs[] = new boolean[8];
			    bs[0] = ((b & 0x01) != 0);
			    bs[1] = ((b & 0x02) != 0);
			    bs[2] = ((b & 0x04) != 0);
			    bs[3] = ((b & 0x08) != 0);
			    bs[4] = ((b & 0x10) != 0);
			    bs[5] = ((b & 0x20) != 0);
			    bs[6] = ((b & 0x40) != 0);
			    bs[7] = ((b & 0x80) == 0);	
				i.setDisease(bs);
				ingredients.put(i.getName(), i);
			}
		} ;
		closeQuery(rs);
	} catch (SQLException e) {
		e.printStackTrace();
	}
	closeStatement(st);
	return ingredients;
}

/**
 * Devuelve una lista con todos los platos existentes en la base de datos.
 * 
 */
public HashMap<String, Plate> getPlates() {
	Statement st = initStatement();
	ResultSet rs = query (st, "SELECT * FROM plates");

	HashMap <String, Plate> plates = new HashMap<String,Plate>();
	HashMap <String, Ingredient> ingredients = getIngredients();
	try {
		while (rs.next()){
			if (rs.getString("Name")!= null){
				Plate p = new Plate (rs.getString("Name"), rs.getString("Location"), rs.getString("Info"), rs.getFloat("Price"), rs.getString("Image_Name"));
				p.setBlocked(rs.getBoolean("Blocked"));
				Byte b = rs.getByte("Diseases");
			    boolean bs[] = new boolean[8];
			    bs[0] = ((b & 0x01) != 0);
			    bs[1] = ((b & 0x02) != 0);
			    bs[2] = ((b & 0x04) != 0);
			    bs[3] = ((b & 0x08) != 0);
			    bs[4] = ((b & 0x10) != 0);
			    bs[5] = ((b & 0x20) != 0);
			    bs[6] = ((b & 0x40) != 0);
			    bs[7] = ((b & 0x80) == 0);	
				p.setDisease(bs);
				for (int i = 1; i<=8; i++){
					Ingredient j = ingredients.get(rs.getString("Ingredient_" + i));
					if (j != null) p.addIngredient(j);
				}
				
				plates.put(p.getName(), p);
			}
		} 
		closeQuery(rs);
	} catch (SQLException e) {
		e.printStackTrace();
	}
	closeStatement(st);
	return plates;
}

/**
 * Devuelve una lista con todos los menús existentes en la base de datos.
 * 
 */
public HashMap<String, Menu> getMenus() {
	Statement st = initStatement();
	ResultSet rs = query (st, "SELECT * FROM menus");

	HashMap <String, Menu> menus = new HashMap<String,Menu>();
	HashMap <String, Plate> plates = getPlates();
	try {
		while (rs.next()) {
			if (rs.getString("Name")!= null){
				Menu m = new Menu (rs.getString("Name"), rs.getFloat("Price"));
				m.setBlocked(rs.getBoolean("Blocked"));
				Byte b = rs.getByte("Diseases");
			    boolean bs[] = new boolean[8];
			    bs[0] = ((b & 0x01) != 0);
			    bs[1] = ((b & 0x02) != 0);
			    bs[2] = ((b & 0x04) != 0);
			    bs[3] = ((b & 0x08) != 0);
			    bs[4] = ((b & 0x10) != 0);
			    bs[5] = ((b & 0x20) != 0);
			    bs[6] = ((b & 0x40) != 0);
			    bs[7] = ((b & 0x80) == 0);	
				m.setDisease(bs);
				for (int i = 1; i<=8; i++){
					Plate p = plates.get(rs.getString("Plate_" + i));
					if (p != null) m.addPlate(p);
				}
				
				menus.put(m.getName(), m);
			}
		} 
		closeQuery(rs);
	} catch (SQLException e) {
		e.printStackTrace();
	}
	closeStatement(st);
	return menus;
}

/**
 * 
 * Devuelve una lista con todas las promociones existentes en la base de datos.
 */
public HashMap<String, Promo> getPromos() {
	Statement st = initStatement();
	ResultSet rs = query (st, "SELECT * FROM promos");

	HashMap <String, Promo> promos = new HashMap<String,Promo>();
	HashMap <String, Plate> plates = getPlates();
	try {
		while (rs.next()) {
			if (rs.getString("Name")!= null){
				Plate p = plates.get(rs.getString("Plate"));
				Promo pr = new Promo (rs.getString("Name"), rs.getInt("Discount"), p);				
				promos.put(pr.getName(), pr);
			}
		} 
		closeQuery(rs);
	} catch (SQLException e) {
		e.printStackTrace();
	}
	closeStatement(st);
	return promos;
}

/**
 * Devuelve una lista con todos los platos incluidos en los pedidos en un momento dado.
 * 
 */
public ArrayList<Plate> getOrderedPlates() {
	Statement st = initStatement();
	ResultSet rs = query (st, "SELECT orderedplates.Name FROM orderedplates  ORDER BY orderedplates.Date ");
	
	ArrayList<Plate> ordered_plates = new ArrayList<Plate>();
	HashMap <String, Plate> plates = getPlates();
	try {
		while (rs.next()) {
			if (rs.getString("Name")!= null){
				Plate p = plates.get(rs.getString("Name"));			
				ordered_plates.add(p);
			}
		} 
		closeQuery(rs);
	} catch (SQLException e) {
		e.printStackTrace();
	}
	closeStatement(st);
	return ordered_plates;
}

/**
 * Devuelve los ID's de las mesas que han hecho pedidos. Se debe usar en conjunto con el anterior método.
 * 
 */
public ArrayList<String> getOrderedPlatesTableID() {
	Statement st = initStatement();
	ResultSet rs = query (st, "SELECT orderedplates.TableID FROM orderedplates  ORDER BY orderedplates.Date ");
	
	ArrayList<String> ordered_plates_tableid = new ArrayList<String>();
	//HashMap <String, Plate> plates = getPlates();
	try {
		while (rs.next()) {
			if (rs.getString("TableID")!= null){	
				ordered_plates_tableid.add(rs.getString("TableID"));
			}
		} 
		closeQuery(rs);
	} catch (SQLException e) {
		e.printStackTrace();
	}
	closeStatement(st);
	return ordered_plates_tableid;
}

/**
 * Devuelve las fechas y horas de los pedidos realizados. Se debe usar en conjunto con los dos anteriores.
 * 
 */
public ArrayList<Timestamp> getOrderedPlatesDate() {
	Statement st = initStatement();
	ResultSet rs = query (st, "SELECT orderedplates.Date FROM orderedplates  ORDER BY orderedplates.Date ");
	
	ArrayList<Timestamp> ordered_plates_date = new ArrayList<Timestamp>();
	//HashMap <String, Plate> plates = getPlates();
	try {
		while (rs.next()) {
			if (rs.getString("Date")!= null){	
				ordered_plates_date.add(rs.getTimestamp("Date"));
			}
		} 
		closeQuery(rs);
	} catch (SQLException e) {
		e.printStackTrace();
	}
	closeStatement(st);
	return ordered_plates_date;
}

/**
 * Devuelve una lista de todos los platos ya cocinados y listos para entregar a las mesas.
 * 
 */
public ArrayList<Plate> getFinishedPlates() {
	Statement st = initStatement();
	ResultSet rs = query (st, "SELECT finishedplates.Name FROM finishedplates ORDER BY finishedplates.Date");
	
	ArrayList<Plate> finished_plates = new ArrayList<Plate>();
	HashMap <String, Plate> plates = getPlates();
	try {
		while (rs.next()) {
			if (rs.getString("Name")!= null){
				Plate p = plates.get(rs.getString("Name"));			
				finished_plates.add(p);
			}
		} 
		closeQuery(rs);
	} catch (SQLException e) {
		e.printStackTrace();
	}
	closeStatement(st);
	return finished_plates;
}

/**
 * Devuelve una lista con los ID's de las mesas cuyos platos están ya cocinados.
 * Se debe usar en conjunto con el método anterior.
 * 
 */
public ArrayList<String> getFinishedPlatesTableID() {
	Statement st = initStatement();
	ResultSet rs = query (st, "SELECT finishedplates.TableID FROM finishedplates ORDER BY finishedplates.Date");
	
	ArrayList<String> finished_plates_tableid = new ArrayList<String>();
	//HashMap <String, Plate> plates = getPlates();
	try {
		while (rs.next()) {
			if (rs.getString("TableID")!= null){	
				finished_plates_tableid.add(rs.getString("TableID"));
			}
		} 
		closeQuery(rs);
	} catch (SQLException e) {
		e.printStackTrace();
	}
	closeStatement(st);
	return finished_plates_tableid;
}

/**
 * Devuelve una lista con las fechas y horas de los platos ya cocinados.
 * Se debe usar en conjunto con los dos métodos anteriores.
 * 
 */
public ArrayList<Timestamp> getFinishedPlatesDate() {
	Statement st = initStatement();
	ResultSet rs = query (st, "SELECT finishedplates.Date FROM finishedplates ORDER BY finishedplates.Date");
	
	ArrayList<Timestamp> finished_plates_date = new ArrayList<Timestamp>();
	//HashMap <String, Plate> plates = getPlates();
	try {
		while (rs.next()) {
			if (rs.getString("Date")!= null){	
				finished_plates_date.add(rs.getTimestamp("Date"));
			}
		} 
		closeQuery(rs);
	} catch (SQLException e) {
		e.printStackTrace();
	}
	closeStatement(st);
	return finished_plates_date;
}

/**
 * Devuelve un conjunto de enteros que representan los ingresos del restaurante.
 *
 */
public float[] getKitchenHistory() {
	Statement st = initStatement();
	int year = GregorianCalendar.getInstance().get(Calendar.YEAR);
	ResultSet rs = query (st, "SELECT * FROM kitchenhistory WHERE Year = '" + year + "'");
	
	float [] kitchenhistory= new float [15];
	//HashMap <String, Plate> plates = getPlates();
	kitchenhistory[0] = -1;
	try {
		
		if (rs.next()){
			kitchenhistory[0] = rs.getFloat("Year");
			kitchenhistory[1] = rs.getFloat("January");
			kitchenhistory[2] = rs.getFloat("February");
			kitchenhistory[3] = rs.getFloat("March");
			kitchenhistory[4] = rs.getFloat("April");
			kitchenhistory[5] = rs.getFloat("May");
			kitchenhistory[6] = rs.getFloat("June");
			kitchenhistory[7] = rs.getFloat("July");
			kitchenhistory[8] = rs.getFloat("August");
			kitchenhistory[9] = rs.getFloat("September");
			kitchenhistory[10] = rs.getFloat("October");
			kitchenhistory[11] = rs.getFloat("November");
			kitchenhistory[12] = rs.getFloat("December");
			kitchenhistory[13] = rs.getFloat("ThisYear");
			kitchenhistory[14] = rs.getFloat("LastYear");
		}
		closeQuery(rs);
	} catch (SQLException e) {
		e.printStackTrace();
	}
	closeStatement(st);
	return kitchenhistory;
}
/**
 * Guarda un plato de un pedido en la base de datos
 * @param plate el plato
 * @param station la mesa que lo ha pedido
 * @return true si se ha guardado el plato correctamente, false en caso contrario
 */
public boolean storeOrderedPlate(Plate plate, Station station) {
	Statement st = initStatement();
	
	int rt = update(st, 
			"INSERT IGNORE INTO orderedplates" +
			"(Name, Date, TableID)" +
			" VALUES ('"+plate.getName()+
			"', NOW()" +
			", '"+ station.getStationId() +"')"
			);
	closeStatement(st);
	if (rt != -1) return true;
	else return false;
	
}
/**
 * Guarda un plato cocinado en la base de datos
 * @param plate el plato
 * @param table_id el id de la mesa
 * @return true si se ha guardado correctamente, false en caso contrario
 */
public boolean storeFinishedPlate(Plate plate, String table_id) {
	Statement st = initStatement();
	
	int rt = update(st, 
			"INSERT IGNORE INTO finishedplates " +
			"(Name, Date, TableID)" +
			"VALUES ('"+plate.getName()+
			"',  NOW()"+
			", '"+ table_id +"')"
			);
	
	if (rt != -1) rt =  update(st, 
			"DELETE FROM orderedplates WHERE " +
			"Name = '" + plate.getName() + 
			"' AND TableID = '" + table_id + "' LIMIT 1" 
			);
	
	closeStatement(st);
	if (rt != -1) return true;
	else return false;
}
/**
 * Elimina un usuario de la base de datos
 * @param user el alias de usuario
 * @return true si se ha eliminado correctamente el usuario, false en caso contrario.
 */
public boolean removeActor(String user) {
	Statement st = initStatement();
	int rt = update(st, 
			"DELETE FROM actors WHERE " +
			"User = '" + user + "'" 
			);
	closeStatement(st);
	if (rt != -1) return true;
	else return false;
}

/**
 * Guarda un nuevo ingrediente en la base de datos
 * @param i el ingrediente
 * @return true si se ha guardado correctamente, false en caso contrario.
 */
public boolean storeIngredient(Ingredient i){
	
	Statement st = initStatement();
	boolean[] b = i.getDisease();
	int diseases = 0;
	for (int j = 0; j<b.length; j++){
		if (b[j]) diseases += Math.pow(2, j);
	}
	diseases -= 128;
	//JOptionPane.showMessageDialog(null, diseases);
	int blocked = 0;
	if (i.isBlocked()) blocked = 1;
	int rt;
	rt = update(st, 
			"INSERT IGNORE INTO ingredients " +
			"(Name, Amount, Blocked, Diseases)" +
			"VALUES ('"+i.getName()+
			"', '"+ i.getAmount()+
			"', '"+ blocked +
			"', '"+ diseases +"')"
			);
	closeStatement(st);
	
	if (rt != -1) return true;
	else return false;
	
}

/**
 * Actualiza un ingrediente existente en la base de datos.
 * @param i el ingrediente
 * @return true si se ha actualizado correctamente, false en caso contrario
 */
public boolean updateIngredient (Ingredient i) {
	Statement st = initStatement();
	boolean[] b = i.getDisease();
	int diseases = 0;
	for (int j = 0; j<b.length; j++){
		if (b[j]) diseases += Math.pow(2, j);
	}
	diseases -= 128;
	int rt = update(st, 
			"UPDATE ingredients SET" +
			" Amount = '"+ i.getAmount() +
//			" Blocked = '"+ i.isBlocked() +
			"', Diseases = '"	+ diseases +
			"' WHERE Name = '" + i.getName() + "'"
			);
	closeStatement(st);
	if (rt != -1) return true;
	else return false;
}

/**
 * Elimina un ingrediente de la base de datos
 * @param name Nombre del ingrediente
 * @return true si el ingrediente existe, false en caso contrario
 */
public boolean removeIngredient(String name) {
	Statement st = initStatement();
	int rt = update(st, 
			"DELETE FROM ingredients WHERE " +
			"Name = '" + name + "'" 
			);
	closeStatement(st);
	if (rt != -1) return true;
	else return false;
}

/**
 * Guarda un plato nuevo en la base de datos.
 * @param p el plato
 * @return true si se ha guardado correctamente el plato, false en caso contrario.
 */
public boolean storePlate(Plate p) {
	Statement st = initStatement();
	File image = new File(p.getImageName());
	PreparedStatement psmnt;
	int rt = -1;
	try {
		psmnt = connection.prepareStatement
				("INSERT IGNORE INTO images (ImageID, Img) "+ "VALUES(?,?)");
		psmnt.setString(1, p.getName());
		FileInputStream fis = new FileInputStream(image);
		psmnt.setBinaryStream(2, (InputStream)fis, (int)(image.length()));
		rt = psmnt.executeUpdate();
	} catch (Exception e) {
		e.printStackTrace();
	}
	String ingredient_textstring = "";
	String ingredient_string = "";
	if (p.getIngredients().size() > 0){
		ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>((Collection<Ingredient>)p.getIngredients().values());
		for (int i = 0; i<ingredients.size(); i++){
			ingredient_string += ("', '"+ ingredients.get(i).getName());
			ingredient_textstring += (", Ingredient_" + (i+1) + "");
		}
	} 
	ingredient_string += "'";
	boolean[] b = p.getDisease();
	int diseases = 0;
	for (int j = 0; j<b.length; j++){
		if (b[j]) diseases += Math.pow(2, j);
	}
	diseases -= 128;
	int blocked = 0;
	if (p.isBlocked()) blocked = 1;
	if (rt!=-1)rt = update(st, 
			"INSERT IGNORE INTO plates " +
			"(Name, Location, Info, Price, Blocked, Diseases, Image_Name" + ingredient_textstring + ")" +
			" VALUES ('"+p.getName()+
			"', '"+ p.getLocation()+
			"', '"+ p.getInfo() +
			"', '"+ p.getPrice()+
			"', '"+ blocked +
			"', '"+ diseases +
			"', '"+ p.getName() +
			ingredient_string +")"
			);
	closeStatement(st);
	if (rt != -1) return true;
	else return false;
}

/**
 * Actualiza un plato existente en la base de datos.
 * @param p el plato
 * @return true si se ha actualizado correctamente, false en caso contrario
 */
public boolean updatePlate (Plate p) { 
	Statement st = initStatement();
	boolean[] b = p.getDisease();
	int diseases = 0;
	for (int j = 0; j<b.length; j++){
		if (b[j]) diseases += Math.pow(2, j);
	}
	diseases -= 128;
	File image = new File(p.getImageName());

	int rt = update(st, 
			"DELETE FROM images WHERE " +
			"ImageID = '" + p.getImageName() + "'" 
			);
	
	PreparedStatement psmnt;
	
	try {
		psmnt = connection.prepareStatement
				("INSERT IGNORE INTO images (ImageID, Img) "+ "VALUES(?,?)");
		psmnt.setString(1, p.getName());
		FileInputStream fis = new FileInputStream(image);
		psmnt.setBinaryStream(2, (InputStream)fis, (int)(image.length()));
		psmnt.executeUpdate();
	} catch (Exception e) {
		e.printStackTrace();
	}
	ArrayList<Ingredient> ing_list =new ArrayList<Ingredient>((Collection<Ingredient>)p.getIngredients().values());
	String st_ing = "";
	for (int i = 0; i<ing_list.size(); i++){
		st_ing += ", Ingredient_" + (i+1) + " = '" + ing_list.get(i).getName() + "'";
	}
	
	rt = update(st, 
			"UPDATE plates SET" +
			" Location = '"+ p.getLocation() +
			"', Info = '"	+ p.getInfo() +
			"', Price = '"	+ p.getPrice() +
			"', Diseases = '"	+ diseases +
	//		"', Blocked = '"	+ p.isBlocked() +
			"', Image_Name = '"	+ p.getName() + "'" +
			st_ing +
			" WHERE Name = '" + p.getName() + "'"
			);
	closeStatement(st);
	if (rt != -1) return true;
	else return false;
}

/**
 * Elimina un plato de la base de datos.
 * @param name Nombre del plato
 * @return true si se ha eliminado correctamente, false en caso contrario.
 */
public boolean removePlate(String name) {
	Statement st = initStatement();
	int rt = update(st, 
			"DELETE FROM plates WHERE " +
			"Name = '" + name + "'" 
			);
	closeStatement(st);
	if (rt != -1) return true;
	else return false;
}
/**
 * Guarda un nuevo menú en la base de datos.
 * @param m el menú
 * @return true si se ha guardado correctamente, false en caso contrario
 */
public boolean storeMenu(Menu m) {
	Statement st = initStatement();
	String plate_textstring = "";
	String plate_string = "";
	if (m.getPlates().size() > 0){
		Collection<Plate> plates2 = m.getPlates().values();
		ArrayList<Plate> plates = new ArrayList<Plate>(plates2);
	for (int i = 0; i<plates.size(); i++){
		plate_string += ("', '"+ plates.get(i).getName());
		plate_textstring += (", Plate_" + (i+1) + "");
	}
	
	}
	boolean[] b = m.getDisease();
	int diseases = 0;
	for (int j = 0; j<b.length; j++){
		if (b[j]) diseases += Math.pow(2, j);
	}
	diseases -= 128;
	int blocked = 0;
	if (m.isBlocked()) blocked = 1;
	int rt = update(st, 
			"INSERT IGNORE INTO menus " +
			"(Name, Price, Blocked, Diseases" + plate_textstring + ")" +
			" VALUES ('"+m.getName()+
			"', '"+ m.getPrice()+
			"', '"+ blocked+
			"', '"+ diseases+
			plate_string +"')"
			);
	closeStatement(st);
	if (rt != -1) return true;
	else return false;
}
/**
 * Actualiza un menú existente en la base de datos
 * @param m el menú
 * @return true si se ha actualizado correctamente, false en caso contrario.
 */
public boolean updateMenu (Menu m) {
	Statement st = initStatement();
	boolean[] b = m.getDisease();
	int diseases = 0;
	for (int j = 0; j<b.length; j++){
		if (b[j]) diseases += Math.pow(2, j);
	}
	diseases -= 128;
	
	ArrayList<Plate> plat_list =new ArrayList<Plate> ((Collection<Plate>)m.getPlates().values());
	String st_plate = "";
	for (int i = 0; i<plat_list.size(); i++){
		st_plate += ", Plate_" + (i+1) + " = '" + plat_list.get(i).getName() + "'";
	}
	
	int rt = update(st, 
			"UPDATE menus SET" +
			" Price = '"+ m.getPrice() +
			"', Diseases = '"	+ diseases + "'" +
	//		"', Blocked = '"	+ m.isBlocked() +
			st_plate +
			" WHERE Name = '" + m.getName() + "'"
			);
	closeStatement(st);
	if (rt != -1) return true;
	else return false;
}

/**
 * Elimina un menú de la base de datos
 * @param name Nombre del menú
 * @return true si el menú existe, false en caso contrario.
 */
public boolean removeMenu(String name) {
	Statement st = initStatement();
	int rt = update(st, 
			"DELETE FROM menus WHERE " +
			"Name = '" + name + "'" 
			);
	closeStatement(st);
	if (rt != -1) return true;
	else return false;	
}

/**
 * Guarda una nueva promoción en la base de datos.
 * @param p la promoción
 * @return true si se ha guardado correctamente, false en caso contrario.
 */
public boolean storePromo(Promo p) {
	Statement st = initStatement();
	int rt = update(st, 
			"INSERT IGNORE INTO promos " +
			"(Name, Discount, Plate)" +
			"VALUES ('"+p.getName()+
			"', '"+ p.getDiscount()+
			"', '"+ p.getPlate().getName()+"')"
			);
	closeStatement(st);
	if (rt != -1) return true;
	else return false;	
}
/**
 * Actualiza una promoción existente en la base de datos.
 * @param p la promoción
 * @return true si se ha actualizado correctamente, false en caso contrario.
 */
public boolean updatePromo (Promo p) {
	Statement st = initStatement();
	
	int rt = update(st, 
			"UPDATE promos SET" +
			" Discount = '"+ p.getDiscount() +
			"', Plate = '"	+ p.getName() +
			"' WHERE Name = '" + p.getName() + "'"
			);
	closeStatement(st);
	if (rt != -1) return true;
	else return false;
}

/**
 * Guarda una nueva mesa en la base de datos.
 * @param s la mesa
 * @return true si se ha guardado correctamente, false en caso contrario.
 */
public String storeStation(Station s) {
	Statement st = initStatement();
	ResultSet rs;
	String str = s.getStatus().toString();
	int id = 1;
	if (str != "WAITER" && str != "COOK"){
		str = "CLIENT";
		rs = query (st, "SELECT * FROM stations");
		try {
			while (rs.next()){
				if (rs.getString("Status")!="WAITER" && rs.getString("Status")!="COOK"){
					if  (rs.getString("StationID").equals("0" + Integer.toString(id) + "_CLIENT")) id++;
				
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}else{
		rs = query (st, "SELECT * FROM stations WHERE Status = '"+ str + "'");
	
		try {
			while (rs.next()){
				id = Integer.parseInt(rs.getString("StationID").substring(0, 2));
				id++;
			}
			
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	String s_id = "";
	if (id < 10) s_id += "0" + id;
	else s_id += "" + id;
	s_id += "_" + str;
	int rt = update(st, 
			"INSERT IGNORE INTO stations " +
			"(StationID, Status)" +
			"VALUES ('"+  s_id+
			"', '"+ s.getStatus().toString()+"')"
			//"', '"+ s.getActor().getUser()+
		//	"', '"+ s.getOrder().getOrderId()+"')"
			);
	closeStatement(st);
	
	return  s_id;	
}

/**
 * Actualiza una mesa existente en la base de datos.
 * @param s la mesa
 * @return true si se ha actualizado la mesa correctamente, false en caso contrario.
 */
public boolean updateStation (Station s) {
	Statement st = initStatement();
	String idorderst = "";
	Order o = s.getOrder();
	if (o != null && o.getOrderId()!=0){
		idorderst+= ", IDOrder = '"	+ o.getOrderId() + "'";
	} else idorderst = "";
	
	String s_actor = "";
	if (!(s.getActor()==null))
		s_actor += "', Actor = '" + s.getActor().getUser() + "'";
	else s_actor += "', Actor = NULL";
	
	int rt = update(st, 
			"UPDATE stations SET" +
			" Status = '"+ s.getStatus() +
			s_actor +
			idorderst +
			" WHERE StationID = '" + s.getStationId() + "'"
			);
	closeStatement(st);
	if (rt != -1) return true;
	else return false;
}

/**
 * Elimina una promoción de la base de datos.
 * @param name Nombre de la promoción
 * @return true si la promoción existe, false en caso contrario
 */
public boolean removePromo(String name) {
	Statement st = initStatement();
	int rt = update(st, 
			"DELETE FROM promos WHERE " +
			"Name = '" + name + "'" 
			);
	closeStatement(st);
	if (rt != -1) return true;
	else return false;
}
/**
 * Elimina una mesa existente en la base de datos.
 * @param id ID de la mesa
 * @return true si se ha eliminado correctamente, false en caso contrario.
 */
public boolean removeStation(String id) {
	Statement st = initStatement();
	int rt = update(st, 
			"DELETE FROM stations WHERE " +
			"StationID = '" + id + "'" 
			);
	closeStatement(st);
	if (rt != -1) return true;
	else return false;
}
/**
 * Devuelve una lista de todas las mesas existentes en la base de datos, incluidos los puesto de camarero
 * y cocinero. 
 * 
 */
public HashMap<String, Station> getStations() {
	Statement st = initStatement();
	ResultSet rs = query (st, "SELECT * FROM stations");
	
	HashMap <String, Station> stations = new HashMap<String,Station>();
//	HashMap <String, Actor> actors = getActors();
	try {
		while (rs.next()) {
			if (rs.getString("StationID")!= null){
				Actor a = null;
				if (rs.getString("Actor")!=null) a = getActor(rs.getString("Actor"));
				Station s = new Station(rs.getString("StationID"), a, StatusStruct.valueOf(rs.getString("Status")));
				//if (rs.getInt("IDOrder")!= 0) s.setOrder(getOrder(rs.getInt("IDOrder")));
				stations.put(s.getStationId(), s);
			}
		} 
		closeQuery(rs);
	} catch (SQLException e) {
		e.printStackTrace();
	}
	closeStatement(st);
	return stations;
}

/**
 * Devuelve una lista de todas las mesas existentes en la base de datos, incluidos los puesto de camarero
 * y cocinero. 
 * 
 */
public Station getStation(String id) {
	Statement st = initStatement();
	ResultSet rs = query (st, "SELECT * FROM stations WHERE StationID = '" + id + "'");
	
	Station s = null;
	try {
		if (rs.next()) {
				Actor a = null;
				if (rs.getString("Actor")!=null) a = getActor(rs.getString("Actor"));
				s = new Station(id, a, StatusStruct.valueOf(rs.getString("Status")));
				if (rs.getInt("IDOrder")!= 0) s.setOrder(getOrder(rs.getInt("IDOrder")));
		} 
		closeQuery(rs);
	} catch (SQLException e) {
		e.printStackTrace();
	}
	closeStatement(st);
	return s;
}
public BufferedImage getImage(String name) {
	Statement st = initStatement();
	ResultSet rs = query (st, "SELECT Img FROM images WHERE ImageID = '" + name + "'");
	BufferedImage image = null;
	Blob blob = null;
	try {
		if(rs.next())
			blob = rs.getBlob("Img");
			InputStream binaryStream = blob.getBinaryStream(1, blob.length());
			 try {
				image = ImageIO.read(binaryStream);
			} catch (IOException e) {
				e.printStackTrace();
			}
	} catch (SQLException e) {
		e.printStackTrace();
	}
	
	closeStatement(st);
	return image;
}
/**
 * Crea los administradores por defecto del restaurante. Un usuario cocinero: "arepa_cook", y un usuario
 * camarero: "arepa_waiter", con sus respectivas contraseñas "cook" y "waiter".
 */
public void createAdmins(){
	Actor cook = new Actor("arepa_cook", ActorStruct.COOK, "cook", "Cook", "Admin", 0, "Street", SexStruct.MALE, "ayuda@arepa.com");
	Actor waiter = new Actor("arepa_waiter", ActorStruct.WAITER, "waiter", "Waiter", "Admin", 0, "Street", SexStruct.FEMALE, "ayuda@arepa.com");
	registerActor(cook);
	registerActor(waiter);
}

/**
 * Guarda un nuevo pedido en la base de datos.
 * @param order el pedido
 * @return el número de pedido
 */
public int storeOrder(Order order) {
	Statement st = initStatement();
	ArrayList<Menu> menus = (ArrayList<Menu>)order.getMenus();
	String menu_textstring = "";
	String menu_string = "";
	for (int i = 0; i<order.getMenuCount(); i++){
		menu_string += ("', '"+ menus.get(i).getName());
		menu_textstring += (", Menu_" + (i+1) + "");
	} 
	String plate_textstring = "";
	String plate_string = "";
	for (int i = 0; i<order.getPlateCount(); i++){
		plate_string += ("', '"+ new ArrayList<Plate>(((Collection<Plate>)menus.get(i+order.getMenuCount()).getPlates().values())).get(0).getName());
		plate_textstring += (", Plate_" + (i+1) + "");
	}
	ArrayList<Promo> promos = (ArrayList<Promo>)order.getPromos();
	String promo_textstring = "";
	String promo_string = "";
	for (int i = 0; i<promos.size(); i++){
		menu_string += ("', '"+ promos.get(i).getName());
		menu_textstring += (", Promo_" + (i+1) + "");
	}
	update(st, 
			"INSERT IGNORE INTO orders " +
			"(Price, Date, Block_Mark, Menu_Count, Plate_Count" + menu_textstring + plate_textstring + promo_textstring + ")" +
			"VALUES ('"+order.getPrice() +
			"', NOW()"+// GregorianCalendar.getInstance().getTime()+
			", '"+ order.getBlockMark()+
			"', '"+ order.getMenuCount()+
			"', '"+ order.getPlateCount()+
			menu_string +
			plate_string +
			promo_string + "')"
			);
	
	ResultSet set = query(st,"SELECT OrderID FROM orders ORDER BY OrderID DESC LIMIT 1");
	int ret = -1;
	try {
		if (set.next())ret = set.getInt("OrderID");
	} catch (SQLException e) {
		e.printStackTrace();
	}
	closeStatement(st);
	return ret;
}

/**
 * Actualiza un pedido existente en la base de datos.
 * @param o el pedido
 * @return true si se ha actualizado el pedido correctamente, false en caso contrario.
 */
public boolean updateOrder (Order o) {
	Statement st = initStatement();
	
	ArrayList<Menu> menus = (ArrayList<Menu>)o.getMenus();
	String menu_textstring = "";
	for (int i = 1; i<=4; i++){
		if (i <= o.getMenuCount()) menu_textstring += (", Menu_" + (i) + " = '" + menus.get(i-1).getName() + "'");
		else  menu_textstring += (", Menu_" + (i) + " = NULL");
	}
	
	String plate_textstring = "";
	for (int i = 1; i<= 7; i++){
		if (i <= o.getPlateCount()) plate_textstring += (", Plate_" + (i) + " = '" + new ArrayList<Plate>(((Collection<Plate>)menus.get(i+o.getMenuCount()-1).getPlates().values())).get(0).getName() + "'");
		else plate_textstring += (", Plate_" + (i) + " = NULL");
	}
	ArrayList<Promo> promos = (ArrayList<Promo>)o.getPromos();
	String promo_textstring = "";
	for (int i = 0; i<promos.size(); i++){
		if (promos.get(i) != null ) promo_textstring += (", Promo_" + (i+1) + " = '" +  promos.get(i).getName() + "'");
		else promo_textstring += (", Promo_" + (i+1) + " = NULL");
	}
	
	int rt = update(st, 
			"UPDATE orders SET" +
			" Price = '"+ o.getPrice() +
			"', Date = NOW()" +
			", Block_Mark = '"	+ o.getBlockMark() +
			"', Menu_Count = '"	+ o.getMenuCount() +
			"', Plate_Count = '"	+ o.getPlateCount() + "'"+
			menu_textstring +
			plate_textstring +
			promo_textstring +
			" WHERE OrderID = '" + o.getOrderId() + "'"
			);
	closeStatement(st);
	if (rt != -1) return true;
	else return false;
}

/**
 * Devuelve una lista de platos filtrados por ingrediente y extras(diseases)
 * @param ingredient filtro de ingrediente (nombre)
 * @param diseases filtro extras (diabetes, sin gluten, sin lactosa...)
 * 
 */

public HashMap<String, Plate> getFilteredPlates(String ingredient, boolean[] diseases) {
	Statement st = initStatement();
	
	
	String s_i = "("; 
	
	if (ingredient != null){ 
		for (int i = 1; i<= 8; i++){
			s_i += " (plates.Ingredient_" + i + " = '" + ingredient + "')";
			if (i != 8)  s_i += " OR";
		}
	}
	s_i += ") AND NOT(";
	
	String dis = "";
	boolean activated = false;
	for(int i = 0; i < 8; i++ ){
		if (diseases[i] == true){
			if (activated)dis += " OR (";
			else {
				dis += "(";
				activated = true;
			}
		}
		if (diseases[i] == true) dis += " CAST(Diseases+128 AS decimal) & CAST(" + Math.pow(2, i) + " AS decimal) = CAST(" + Math.pow(2,i)+ " AS DECIMAL)) ";
	}
	if (!activated) dis+= "1=2";
	if (ingredient.equals("NOTHING")) s_i = "NOT(";
		ResultSet rs = query (st, "SELECT * FROM plates WHERE " + s_i + ""+ dis + ")");
	
	HashMap <String, Plate> plates = new HashMap<String,Plate>();
	HashMap <String, Ingredient> ingredients = getIngredients();
	try {
		while (rs.next()){
			if (rs.getString("Name") != null){
				Plate p = new Plate (rs.getString("Name"), rs.getString("Location"), rs.getString("Info"), rs.getFloat("Price"), rs.getString("Image_Name"));
				p.setBlocked(rs.getBoolean("Blocked"));
				Byte b = rs.getByte("Diseases");
			    boolean bs[] = new boolean[8];
			    bs[0] = ((b & 0x01) != 0);
			    bs[1] = ((b & 0x02) != 0);
			    bs[2] = ((b & 0x04) != 0);
			    bs[3] = ((b & 0x08) != 0);
			    bs[4] = ((b & 0x16) != 0);
			    bs[5] = ((b & 0x32) != 0);
			    bs[6] = ((b & 0x64) != 0);
			    bs[7] = ((b & 0x128) != 0);	
				p.setDisease(bs);
				for (int i = 1; i<=8; i++){
					Ingredient j = ingredients.get(rs.getString("Ingredient_" + i));
					if (j != null) p.addIngredient(j);
				}
				
				plates.put(p.getName(), p);
			}
		} 
		closeQuery(rs);
	} catch (SQLException e) {
		e.printStackTrace();
	}
	closeStatement(st);
	return plates;
}

public HashMap<String, Menu> getFilteredMenus(boolean[] diseases) {
	Statement st = initStatement();
	
	String dis = "";
	boolean activated = false;
	for(int i = 0; i < 8; i++ ){
		if (diseases[i] == true){
			if (activated)dis += " OR (";
			else {
				dis += "(";
				activated = true;
			}
		}
		if (diseases[i] == true) dis += " CAST(Diseases+128 AS decimal) & CAST(" + Math.pow(2, i) + " AS decimal) = CAST(" + Math.pow(2,i)+ " AS DECIMAL)) ";
	}
	if (!activated) dis+= "1=2";
	//if (ingredient.equals("NOTHING")) s_i = "NOT(";
		ResultSet rs = query (st, "SELECT * FROM menus WHERE NOT(" + dis + ")");
	
	HashMap <String, Menu> menus = new HashMap<String,Menu>();
	HashMap <String, Plate> plates = getPlates();
	try {
		while (rs.next()){
			if (rs.getString("Name") != null){
				Menu m = new Menu (rs.getString("Name"), rs.getFloat("Price"));
				m.setBlocked(rs.getBoolean("Blocked"));
				Byte b = rs.getByte("Diseases");
			    boolean bs[] = new boolean[8];
			    bs[0] = ((b & 0x01) != 0);
			    bs[1] = ((b & 0x02) != 0);
			    bs[2] = ((b & 0x04) != 0);
			    bs[3] = ((b & 0x08) != 0);
			    bs[4] = ((b & 0x16) != 0);
			    bs[5] = ((b & 0x32) != 0);
			    bs[6] = ((b & 0x64) != 0);
			    bs[7] = ((b & 0x128) != 0);	
				m.setDisease(bs);
				for (int i = 1; i<=8; i++){
					Plate p = plates.get(rs.getString("Plate_" + i));
					if (p != null) m.addPlate(p);
				}
				
				menus.put(m.getName(), m);
			}
		} 
		closeQuery(rs);
	} catch (SQLException e) {
		e.printStackTrace();
	}
	closeStatement(st);
	return menus;
}


/**
 * Bloquea un ingrediente en la base de datos porque su cantidad es 0.
 * @param name Nombre del ingrediente
 * @return true si se ha bloqueado correctamente, false en caso contrario.
 */
public boolean setIngredientBlocked(String name) {
	Statement st = initStatement();
	int rt = update(st, 
			"UPDATE ingredients SET " +
			"Blocked = '1'" +
			" WHERE Name = '" + name + "'"
			);
	closeStatement(st);
	setPlatesBlocked (name);
	if (rt != -1) return true;
	else return false;
}

/**
 * Bloquea un plato en la base de datos. Un plato es bloqueado cuando uno de los ingredientes
 * que lo componen se bloquea.
 * @param name Nombre del plato
 * @return true si se ha bloqueado el plato correctamente, false en caso contrario.
 */
public boolean setPlatesBlocked(String name) {
	Statement st = initStatement();	
	String s = "";
	for (int i = 1; i<=8; i++){
		s += " Ingredient_" + i + " = '" + name + "'" ;
		if (i != 8) s+= " OR";
	}
	int rt = update(st, 
			"UPDATE plates SET " +
			"Blocked = '1'" +
			" WHERE" + s + ""
			);
	closeStatement(st);
	setMenusBlocked (name);
	if (rt != -1) return true;
	else return false;
}

/**
 * Bloquea un menú de la base de datos. Un menú se bloquea si contiene un plato que se ha bloqueado.
 * @param name Nombre del menú
 * @return true si se ha bloqueado correctamente, false en caso contrario.
 */
public boolean setMenusBlocked(String name) {
	Statement st = initStatement();	
	String s = "";
	for (int i = 1; i<=8; i++){
		s += " Plate_" + i + " = '" + name + "'" ;
		if (i != 8) s+= " OR";
	}
	int rt = update(st, 
			"UPDATE menus SET " +
			"Blocked = '1'" +
			" WHERE" + s + ""
			);
	closeStatement(st);
	if (rt != -1) return true;
	else return false;
}
/**
 * Desbloquea un ingrediente en la base de datos.
 * @param name Nombre del ingrediente
 * @return true si se ha desbloqueado correctamente, false en caso contrario.
 */
public boolean setIngredientUnBlocked(String name) {
	Statement st = initStatement();
	int rt = update(st, 
			"UPDATE ingredients SET" +
			" Blocked = '0'" +
			" WHERE Name = '" + name + "'"
			);
	closeStatement(st);
	setPlatesUnBlocked (name);
	if (rt != -1) return true;
	else return false;
}

/**
 * Desbloquea un plato en la base de datos.
 * @param name Nombre del plato
 * @return true si se ha desbloqueado el plato correctamente, false en caso contrario.
 */
public boolean setPlatesUnBlocked(String name) {
	Statement st = initStatement();	
	String s = "";
	for (int i = 1; i<=8; i++){
		s += " Ingredient_" + i + " = '" + name + "'" ;
		if (i != 8) s+= " OR";
	}
	int rt = update(st, 
			"UPDATE plates SET " +
			"Blocked = '0'" +
			" WHERE" + s + ""
			);
	closeStatement(st);
	setMenusUnBlocked (name);
	if (rt != -1) return true;
	else return false;
}

/**
 * Desbloquea un menú en la base de datos.
 * @param name Nombre del menú
 * @return true si se ha bloqueado el plato correctamente, false en caso contrario.
 */
public boolean setMenusUnBlocked(String name) {
	Statement st = initStatement();	
	String s = "";
	for (int i = 1; i<=8; i++){
		s += " Plate_" + i + " = '" + name + "'" ;
		if (i != 8) s+= " OR";
	}
	int rt = update(st, 
			"UPDATE menus SET " +
			"Blocked = '0'" +
			" WHERE" + s + ""
			);
	closeStatement(st);
	if (rt != -1) return true;
	else return false;
}
/**
 * Actualiza el historial de la cocina
 * @param history array de float que contienen el historial
 * @return true si se ha actualizado correctamente, false en caso contrario
 */
public boolean updateKitchenHistory(float[] history) {
	Statement st = initStatement();
	int year = (int) history[0];
	JOptionPane.showMessageDialog(null, year);
	int rt = update(st, 
			"UPDATE kitchenhistory SET" +
			" January = '"	+ history[1] + "'" +
			", February = '"	+ history[2] + "'" +
			", March = '"	+ history[3] + "'" +
			", April = '"	+ history[4] + "'" +
			", May = '"	+ history[5] + "'" +
			", June = '"	+ history[6] + "'" +
			", July = '"	+ history[7] + "'" +
			", August = '"	+ history[8] + "'" +
			", September = '"	+ history[9] + "'" +
			", October = '"	+ history[10] + "'" +
			", November = '"	+ history[11] + "'" +
			", December = '"	+ history[12] + "'" +
			", ThisYear = '"	+ history[13] + "'" +
			", LastYear = '"	+ history[14] + "'" +
			" WHERE Year = '" + year + "'"
			);
	closeStatement(st);
	JOptionPane.showMessageDialog(null, rt);
	if (rt != -1) return true;
	else return false;
	
}
/**
 * Guarda el historial de la cocina
 * @param history array que contiene float con el historial
 * @return true si se ha guardado correctamente, false en caso contrario
 */
public boolean storeKitchenHistory(float[] history) {
	Statement st = initStatement();
	int rt = update(st, 
			"INSERT INTO kitchenhistory " +
			"(Year, January, February, March, April, May, June, July, August, September, October, November, December, ThisYear, LastYear)" +
			" VALUES ('"+history[0]+
			"', '"+ history[1]+
			"', '"+ history[2]+
			"', '"+ history[3]+
			"', '"+ history[4]+
			"', '"+ history[5]+
			"', '"+ history[6]+
			"', '"+ history[7]+
			"', '"+ history[8]+
			"', '"+ history[9]+
			"', '"+ history[10]+
			"', '"+ history[11]+
			"', '"+ history[12]+
			"', '"+ history[13]+
			"', '"+ history[14] +"')"
			);
	closeStatement(st);
	if (rt != -1) return true;
	else return false;
	
}
//Gets & sets
public String getCookIP() {
	return cook_ip;
}

public void setCookIP(String cook_ip) {
	this.cook_ip = cook_ip;
}

public String getDBName() {
	return db_name;
}

public void setDBName(String db_name) {
	this.db_name = db_name;
}

public String getDBUser() {
	return db_user;
}

public void setDBUser(String db_user) {
	this.db_user = db_user;
}

public String getDBPassword() {
	return db_password;
}

public void setDBPassword(String db_password) {
	this.db_password = db_password;
}




}
