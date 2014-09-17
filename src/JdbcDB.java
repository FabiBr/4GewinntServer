import java.sql.*;


public class JdbcDB {

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
			c = DriverManager.getConnection("jdbc:sqlite:test.db");
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
					+ " TEXT, " + GAMES_P1_KEY + " INTEGER, " + GAMES_P2_KEY
					+ " INTEGER, " + GAMES_LASTPLAYER_KEY + " INTEGER)";
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
	
	
	public void insertNewUser(String username, String userPw) throws SQLException {

	      stmt = c.createStatement();
	      
	      String sql = "INSERT INTO USERS (" + USER_NAME_KEY + "," + USER_PW_KEY + ","
		  			+ USER_GWON_KEY + "," + USER_GLOST_KEY + "," + USER_PREMIUM_KEY + ")" + "VALUES (" + username + ", " + userPw + ", 0, 0, 0)";
	      stmt.executeUpdate(sql);
	}
	
	
	
	
	
	
	
	
}


/*String CREATE_USER_TABLE = "create table if not exists"
		+ USER_TABLE + " (" + USER_ID_KEY
		+ " integer primary key autoincrement, " + USER_NAME_KEY
		+ " text, " + USER_PW_KEY + " text, " + USER_GWON_KEY
		+ " integer, " + USER_GLOST_KEY + " integer, "
		+ USER_PREMIUM_KEY + " integer)";
String CREATE_GAMES_TABLE = "create table if not exists"
		+ GAMES_TABLE + " (" + GAMES_ID_KEY
		+ " integer primary key autoincrement, " + GAMES_FIELD_KEY
		+ " text, " + GAMES_P1_KEY + " integer, " + GAMES_P2_KEY
		+ " integer, " + GAMES_LASTPLAYER_KEY + " integer)";*/





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
