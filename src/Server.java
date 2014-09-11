import java.io.BufferedReader;
import java.io.Console;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import sun.security.ssl.Debug;

public class Server extends Thread {
	private static ServerSocket serverSocket = null;
	public static final int SERVERPORT = 4444;

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

			while (!Thread.currentThread().isInterrupted()) {

				try {

					String read = input.readLine();
					System.out.println(read);

				} catch (IOException e) {
					e.printStackTrace();
				}
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