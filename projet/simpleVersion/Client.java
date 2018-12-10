import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
	public static void main(String[] args) {
		new Client(args[1], args[2]);
	}
	
	Client(String addr_str, String port_str){
		System.out.println("Starting Chatty Client.");
		BufferedReader keyboard =
				new BufferedReader(
					new InputStreamReader(System.in));
		int port = Integer.parseInt(port_str);
		this.chat(addr_str, port, keyboard);
		System.out.println("Chatty Client terminated.");
	}
	
	private void chat(String addr_str, int port, BufferedReader keyboard) {
		try{
			Socket socket = new Socket(addr_str, port);
			System.out.println("client started chat");
			U.chat(socket, keyboard, "server", false);
			socket.close();
		}
		catch(IOException e){
			System.out.println("Fatal Error: Unexpected IO Error.");
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
