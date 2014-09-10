import java.sql.*;

public class Database {
	
	public static void main(String[] args)
	{
		Connection c = null;
		Statement s = null;
		try
		{
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:database.db");
			c.setAutoCommit(false);
			s = c.createStatement();
			
			s.executeUpdate("CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, value TEXT);");
			s.executeUpdate("INSERT INTO users (value) VALUES ('test1');");
			s.executeUpdate("INSERT INTO users (value) VALUES ('test2');");
			c.commit();
			
			ResultSet r = s.executeQuery("SELECT * FROM users");
			while (r.next())
			{
				int id = r.getInt("id");
				String v = r.getString("value");
				System.out.println(id + ": " + v);
			}
			r.close();
			
			s.close();
			c.close();
		}
		catch (Exception e)
		{
			System.out.println(e + "");
		}
	}
	
	
	
	
	

}
