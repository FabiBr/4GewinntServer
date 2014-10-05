package main.java;

import java.sql.*;
import java.util.ArrayList;



public class JdbcDB {

	private static final String datapath = "jdbc:sqlite:test.db";
	
	// usersTableKeys
	private static final String USER_TABLE = "users";

	private static final String USER_ID_KEY = "id";
	private static final String USER_NAME_KEY = "name";
	private static final String USER_PW_KEY = "pw";
	private static final String USER_GWON_KEY = "gamesWon";
	private static final String USER_GLOST_KEY = "gamesLost";
	private static final String USER_PREMIUM_KEY = "premium";

	// GamesTableKeys

	private static final String GAMES_TABLE = "games";

	private static final String GAMES_ID_KEY = "id";
	private static final String GAMES_FIELD_KEY = "field";
	private static final String GAMES_P1_KEY = "player1";
	private static final String GAMES_P2_KEY = "player2";
	private static final String GAMES_LASTPLAYER_KEY = "lastPlayer";

	Connection c = null;
	Statement stmt = null;

	public JdbcDB() {

		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection(datapath);
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS "
					+ USER_TABLE + " (" + USER_ID_KEY
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + USER_NAME_KEY
					+ " TEXT, " + USER_PW_KEY + " TEXT, " + USER_GWON_KEY
					+ " INTEGER, " + USER_GLOST_KEY + " INTEGER, "
					+ USER_PREMIUM_KEY + " INTEGER)";
			String CREATE_GAMES_TABLE = "CREATE TABLE IF NOT EXISTS "
					+ GAMES_TABLE + " (" + GAMES_ID_KEY
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + GAMES_FIELD_KEY
					+ " TEXT, " + GAMES_P1_KEY + " TEXT, " + GAMES_P2_KEY
					+ " TEXT, " + GAMES_LASTPLAYER_KEY + " TEXT)";
			stmt.executeUpdate(CREATE_USER_TABLE);
			stmt.executeUpdate(CREATE_GAMES_TABLE);
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Table created successfully");
	}
	
	public void insertNewGame(String field, String user1, String user2, String currentUser) throws SQLException {
		c = DriverManager.getConnection(datapath);
	    stmt = c.createStatement();
	      
	    String sql = "INSERT INTO " + GAMES_TABLE +  "(" + GAMES_FIELD_KEY + "," + GAMES_P1_KEY + ","
		  			+ GAMES_P2_KEY + "," + GAMES_LASTPLAYER_KEY + ")" + "VALUES ('" + field + "', '" + user1 + "', '" + user2 + "', '" + currentUser + "')";
	    System.out.println(sql);
	    stmt.executeUpdate(sql);
	    System.out.println("update succesful");
	    stmt.close();
	    c.close();
		
		
	}

	public void insertNewUser(String username, String userPw) throws SQLException {
		c = DriverManager.getConnection(datapath);
	    stmt = c.createStatement();
	      
	    String sql = "INSERT INTO USERS (" + USER_NAME_KEY + "," + USER_PW_KEY + ","
		  			+ USER_GWON_KEY + "," + USER_GLOST_KEY + "," + USER_PREMIUM_KEY + ")" + "VALUES ('" + username + "', '" + userPw + "', '0', '0', '0')";
	    System.out.println(sql);
	    stmt.executeUpdate(sql);
	    System.out.println("update succesful");
	    stmt.close();
	    c.close();
	}

	public String getPwByUsername(String username1) throws SQLException {
		c = DriverManager.getConnection(datapath);
	    stmt = c.createStatement();
	    
	    String sql = "SELECT " + USER_PW_KEY + " FROM " + USER_TABLE + " WHERE " + USER_NAME_KEY + "=" + "'" + username1 + "'";
	    ResultSet rs = stmt.executeQuery(sql);
	    
	    
	    boolean isEmpty = true;
	    while(rs.next()) {
	    	isEmpty = false;
	    }
	    if(isEmpty) {
	    	stmt.close();
		    c.close();
	    	return "";
	    } else {
	    	rs = stmt.executeQuery(sql);
	    	String pw = rs.getString(USER_PW_KEY);
	    	stmt.close();
		    c.close();
	    	return pw;
	    }
	}

	public ArrayList<String[]> getAllUsers() throws SQLException {
		ArrayList<String[]> users = new ArrayList<>();
		c = DriverManager.getConnection(datapath);
	    stmt = c.createStatement();
	    
	    String sql = "SELECT * FROM " + USER_TABLE;
	    ResultSet rs = stmt.executeQuery(sql);
	    while ( rs.next() ) {
	    	
	    	String[] user = new String[rs.getMetaData().getColumnCount()+1];
	    	user[1] = String.valueOf(rs.getInt(USER_ID_KEY));
	    	user[2] = rs.getString(USER_NAME_KEY);
	    	user[3] = rs.getString(USER_PW_KEY);
	    	user[4] = String.valueOf(rs.getInt(USER_GWON_KEY));
	    	user[5] = String.valueOf(rs.getInt(USER_GLOST_KEY));
	    	user[6] = String.valueOf(rs.getInt(USER_PREMIUM_KEY));
	    	users.add(user);
	      }
	    stmt.close();
	    c.close();
		return users;
	}
	
	public void insertNewField(int id) {
		//TODO
	}
	
	public ArrayList<String[]> getAllGamesOfUser(String user) throws SQLException {
		ArrayList<String[]> games = new ArrayList<>();
		c = DriverManager.getConnection(datapath);
	    stmt = c.createStatement();
	    
	    String sql = "SELECT * FROM " + GAMES_TABLE + "WHERE" + GAMES_P1_KEY + "='" + user + "' OR " + GAMES_P2_KEY + "='" + user + "'";
	    ResultSet rs = stmt.executeQuery(sql);
	    while ( rs.next() ) {
	    	
	    	String[] game = new String[rs.getMetaData().getColumnCount()+1];
	    	game[1] = String.valueOf(rs.getInt(GAMES_ID_KEY));
	    	game[2] = rs.getString(GAMES_FIELD_KEY);
	    	game[3] = rs.getString(GAMES_P1_KEY);
	    	game[4] = rs.getString(GAMES_P2_KEY);
	    	game[5] = rs.getString(GAMES_LASTPLAYER_KEY);
	    	games.add(game);
	      }
	    stmt.close();
	    c.close();
		return games;
	}
}

/*
 * String CREATE_USER_TABLE = "create table if not exists" + USER_TABLE + " (" +
 * USER_ID_KEY + " integer primary key autoincrement, " + USER_NAME_KEY +
 * " text, " + USER_PW_KEY + " text, " + USER_GWON_KEY + " integer, " +
 * USER_GLOST_KEY + " integer, " + USER_PREMIUM_KEY + " integer)"; String
 * CREATE_GAMES_TABLE = "create table if not exists" + GAMES_TABLE + " (" +
 * GAMES_ID_KEY + " integer primary key autoincrement, " + GAMES_FIELD_KEY +
 * " text, " + GAMES_P1_KEY + " integer, " + GAMES_P2_KEY + " integer, " +
 * GAMES_LASTPLAYER_KEY + " integer)";
 */

/*
 * public static void main(String args[]) { Connection c = null; Statement stmt
 * = null; try { Class.forName("org.sqlite.JDBC"); c =
 * DriverManager.getConnection("jdbc:sqlite:test.db");
 * System.out.println("Opened database successfully");
 * 
 * stmt = c.createStatement(); String CREATE_USER_TABLE =
 * "create table if not exists" + USER_TABLE + " (" + USER_ID_KEY +
 * " integer primary key autoincrement, " + USER_NAME_KEY + " text, " +
 * USER_PW_KEY + " text, " + USER_GWON_KEY + " integer, " + USER_GLOST_KEY +
 * " integer, " + USER_PREMIUM_KEY + " integer)"; String CREATE_GAMES_TABLE =
 * "create table if not exists" + GAMES_TABLE + " (" + GAMES_ID_KEY +
 * " integer primary key autoincrement, " + GAMES_FIELD_KEY + " text, " +
 * GAMES_P1_KEY + " integer, " + GAMES_P2_KEY + " integer, " +
 * GAMES_LASTPLAYER_KEY + " integer)"; stmt.executeUpdate(CREATE_USER_TABLE);
 * stmt.executeUpdate(CREATE_GAMES_TABLE); stmt.close(); c.close(); } catch
 * (Exception e) { System.err.println(e.getClass().getName() + ": " +
 * e.getMessage()); System.exit(0); }
 * System.out.println("Table created successfully"); } }
 */
