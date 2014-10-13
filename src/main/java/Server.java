package main.java;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import org.json.*;

public class Server extends Thread {

	private static ServerSocket serverSocket = null;
	public static final int SERVERPORT = 1939;
	private static JdbcDB myDb = new JdbcDB();

	private static final String INSERT_NEWUSER_KEY = "newUserInsert";
	private static final String INSERT_GAME_KEY = "newGameInsert";
	private static final String INCREMENT_USER_WIN_KEY = "userWins";
	private static final String PASSWORD_CHECK_KEY = "checkPw";
	private static final String GET_ALL_USERS_KEY = "allUsersGet";
	private static final String GET_GAMES_KEY = "myGamesGet";
	private static final String GET_GAME_BY_ID_KEY = "gameById";
	private static final String MAKE_TURN_KEY = "fieldUpdate";

	public static void main(String[] args) throws IOException {

		Thread server = new Thread(new ServerThread());
		server.start();

	}

	static class ServerThread implements Runnable {

		public void run() {
			Socket client = null;
			try {
				serverSocket = new ServerSocket(SERVERPORT);
				System.out.println("server is listening");
			} catch (IOException e) {
				e.printStackTrace();
			}
			while (!Thread.currentThread().isInterrupted()) {

				try {

					client = serverSocket.accept();

					CommunicationThread commThread = new CommunicationThread(
							client);
					new Thread(commThread).start();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	static class CommunicationThread implements Runnable {

		private Socket clientSocket;

		private BufferedReader input;
		private PrintWriter output;

		public CommunicationThread(Socket clientSocket) {

			this.clientSocket = clientSocket;

			try {

				this.input = new BufferedReader(new InputStreamReader(
						this.clientSocket.getInputStream()));

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void run() {

			// while (!Thread.currentThread().isInterrupted()) {

			try {
				String read = input.readLine();
				String callback = handleConnection(read);
				output = new PrintWriter(
						new BufferedWriter(new OutputStreamWriter(
								clientSocket.getOutputStream())), true);
				output.println(callback);
				output.flush();
				System.out.println(read);

			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// }
		}

		private String handleConnection(String read) throws SQLException {
			StringTokenizer tok = new StringTokenizer(read);
			ArrayList<String> data = new ArrayList<String>();
			while (tok.hasMoreElements()) {
				data.add(tok.nextToken());
			}
			String key = data.get(0);

			switch (key) {
			case INSERT_NEWUSER_KEY:
				String username = data.get(1);
				String pw = data.get(2);
				myDb.insertNewUser(username, pw);
				return "User succesfully created";

			case INSERT_GAME_KEY:
				String field = data.get(1);
				String user1 = data.get(2);
				String user2 = data.get(3);
				String currentUser = data.get(4);
				myDb.insertNewGame(field, user1, user2, currentUser);
				return "game created";

			case INCREMENT_USER_WIN_KEY:

				return null;

			case GET_GAMES_KEY:
				String user = data.get(1);
				ArrayList<String[]> allGamesData = myDb.getAllGamesOfUser(user);
				JSONArray gamesList = new JSONArray(allGamesData);
				String gamesOut = gamesList.toString();
				System.out.println(gamesOut);
				return gamesOut;

			case GET_GAME_BY_ID_KEY:
				String id = data.get(1);
				ArrayList<String> gameData = myDb.getGameById(id);
				JSONArray thisData = new JSONArray(gameData);

				return thisData.toString();

			case GET_ALL_USERS_KEY:
				ArrayList<String[]> allUsersData = myDb.getAllUsers();
				JSONArray list = new JSONArray(allUsersData);
				String usersOut = list.toString();
				System.out.println(usersOut);
				return usersOut;

			case PASSWORD_CHECK_KEY:

				String username1 = data.get(1);
				ArrayList<String> userData = myDb.getPwByUsername(username1);
				if (userData != null) {
					String savedPw = userData.get(2);
					if (savedPw.equals(data.get(2))) {
						JSONArray newUserData = new JSONArray(userData);
						return newUserData.toString();
					}
				}
				return "0";

			case MAKE_TURN_KEY:
				String thisId = data.get(1);
				String thisField = data.get(2);
				myDb.makeTurn(thisId, thisField);
				return "";

			default:
				return null;
			}
		}
	}

	/*
	 * private Database db = new Database();
	 * 
	 * public void run() { ServerSocket sSocket = null;
	 * 
	 * try { sSocket = new ServerSocket(4444);
	 * System.out.println("server is listening"); } catch (IOException e1) { //
	 * TODO Auto-generated catch block e1.printStackTrace(); } while (true) {
	 * Socket client = null;
	 * 
	 * try { //System.out.println("Client connected and handled"); client =
	 * sSocket.accept(); handleConnection(client);
	 * System.out.println("Client connected and handled"); } catch (IOException
	 * e) { e.printStackTrace(); } finally { if (client != null) try {
	 * client.close(); } catch (IOException e) { } } }
	 * 
	 * }
	 * 
	 * /*public static void startListening() throws IOException { (new
	 * Server()).start(); }
	 * 
	 * public static void handleConnection(Socket client) throws IOException {
	 * Scanner in = new Scanner(client.getInputStream()); PrintWriter out = new
	 * PrintWriter(client.getOutputStream(), true); String sqlQuery =
	 * in.nextLine(); System.out.println("something happened");
	 * System.out.println(sqlQuery);
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * }
	 * 
	 * public static void main(String[] args) throws IOException { (new
	 * Server()).start(); }
	 */

}
