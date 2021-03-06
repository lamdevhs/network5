import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;

public class Server {
	public static void main(String[] args) throws NoSuchAlgorithmException{
		if (args[1].compareTo("-k") == 0) {
			// this option means a key has to be created
			// and saved in the filepath given as last parameter
			String key_filepath = args[3];
			KeyMaker.saveKey(key_filepath, KeyMaker.newKey());
			new Server(args[2], key_filepath);
		}
		else {
			// the absence of the option -k means the
			// AES key is already in the file, no need to create it
			new Server(args[1], args[2]);
		}
		
	}
	
	ServerSocket socket;
	Server(String port_str, String key_filepath){
		System.out.println("Starting Chatty Server.");
		int port;
		Security security = null;
		try {
			security = new Security(key_filepath);
			port = Integer.parseInt(port_str);
			socket = new ServerSocket(port);
			System.out.println("Listening: " + socket.getInetAddress().toString() + " - port " + port);
		}
		catch(IOException e){
			System.out.println("Fatal Error: server couldn't be started.");
			e.printStackTrace();
			System.exit(-1);
		}
		BufferedReader keyboard =
				new BufferedReader(
					new InputStreamReader(System.in));
		while (true) this.chat(security, keyboard);
	}
	
	public void chat(Security security, BufferedReader keyboard){
		Socket clientSocket = null;
		try {
			System.out.print("Waiting for client to connect... ");
			clientSocket = socket.accept();
			System.out.println("Connection with client established.");
			U.chat(security, clientSocket, keyboard, "client", true);
		}
		catch(IOException e){
			System.out.println("Fatal Error: Unexpected IO Error.");
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
