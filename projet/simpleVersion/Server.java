import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketImpl;

public class Server {
	public static void main(String[] args){
		Server s = new Server(args[1]);
	}
	
	ServerSocket socket;
	Server(String port_str){
		System.out.println("Starting Chatty Server.");
		int port;
		try {
			port = Integer.parseInt(port_str);
			socket = new ServerSocket(port);
			System.out.println("Listening: " + socket.getInetAddress().toString() + " - port " + port);
		}
		catch(IOException e){
			System.out.println("Fatal Error: server couldn't be started.");
			System.exit(-1);
		}
		BufferedReader keyboard =
				new BufferedReader(
					new InputStreamReader(System.in));
		while (true) this.chat(keyboard);
		//keyboard.close();
	}
	
	public void chat(BufferedReader keyboard){
		Socket clientSocket = null;
		try {
			System.out.print("Waiting for client to connect... ");
			clientSocket = socket.accept();
			System.out.println("Connection with client established.");
			U.chat(clientSocket, keyboard, "client", true);
		}
		catch(IOException e){
			System.out.println("Fatal Error: Unexpected IO Error.");
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
