import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketImpl;

public class Server {
	public static void main(String[] args) throws IOException {
		System.out.println("server here");
		Server s = new Server(4444);
	}
	
	ServerSocket socket;
	Server(int port) throws IOException{
		try {
			socket = new ServerSocket(port);
		}
		catch(IOException e){
			System.out.println("error server couldn start");
			System.exit(-1);
		}
		System.out.println("listening: " + socket.getInetAddress().toString() + " - port " + port);
		BufferedReader keyboard =
				new BufferedReader(
					new InputStreamReader(System.in));
		while (this.nextChat(keyboard));
		keyboard.close();
	}
	
	public boolean nextChat(BufferedReader keyboard){
		boolean cont = true;
		Socket clientSocket = null;
		try {
			System.out.println("Waiting for new client...");
			clientSocket = socket.accept();
			System.out.println("Chat started");
			U.chat3(clientSocket, keyboard, "client", true);
		}
		catch(IOException e){
			e.printStackTrace();
			System.out.println("IO Fatal Error while handling a connection.");
		}
		return cont;
	}
}
