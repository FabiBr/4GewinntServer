import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server extends Thread {
	
	private Database db = new Database();

	public void run() {
		try {
			ServerSocket sSocket = new ServerSocket(4444);
			while (true) {
				Socket client = null;

				try {
					client = sSocket.accept();
					handleConnection(client);
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (client != null)
						try {
							client.close();
						} catch (IOException e) {
						}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void startListening() throws IOException {
		(new Server()).start();
	}

	public static void handleConnection(Socket client) throws IOException {
		Scanner in = new Scanner(client.getInputStream());
		PrintWriter out = new PrintWriter(client.getOutputStream(), true);
		
		
		
		
		
		
	}

	public static void main(String[] args) throws IOException {
		startListening();
	}

}