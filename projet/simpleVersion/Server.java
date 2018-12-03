import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketImpl;

public class Server {
	public static void main(String[] args) {
		System.out.println("server here");
		Server s = new Server(4444);
	}
	
	ServerSocket socket;
	Server(int port){
		try {
			socket = new ServerSocket(port);
		}
		catch(IOException e){
			System.out.println("error server couldn start");
			System.exit(-1);
		}
		System.out.println("listening: " + socket.getInetAddress().toString() + " - port " + port);
		while (this.nextChat());
	}
	
	public boolean nextChat(){
		boolean cont = true;
		Socket clientSocket = null;
		try {
			System.out.println("Waiting to connect");
			clientSocket = socket.accept();
			System.out.println("Chat started");
			U.chat2(clientSocket, true);
		}
		catch(IOException e){
			System.out.println("Failure to connect");
		}
		return cont;
	}
}
